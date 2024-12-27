package com.inmaytide.orbit.commons.security;


import com.inmaytide.orbit.commons.configuration.GlobalProperties;
import com.inmaytide.orbit.commons.domain.OrbitClientDetails;
import com.nimbusds.oauth2.sdk.ErrorObject;
import com.nimbusds.oauth2.sdk.TokenIntrospectionResponse;
import com.nimbusds.oauth2.sdk.TokenIntrospectionSuccessResponse;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.Audience;
import net.minidev.json.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.server.resource.introspection.BadOpaqueTokenException;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author inmaytide
 * @since 2023/5/16
 */
public class CustomizedOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private static final String AUTHORITY_PREFIX = "SCOPE_";

    private final static String CLAIMS_KEY_USERNAME = "username";

    private final static String CLAIMS_KEY_AUTHORITIES = "authorities";

    private final Log logger = LogFactory.getLog(this.getClass());

    private final WebClient client;

    private OrbitClientDetails clientDetails;

    public CustomizedOpaqueTokenIntrospector(GlobalProperties properties) {
        this.client = WebClient.create(properties.getAuthorizationServerURI());
    }

    private OrbitClientDetails getClientDetails() {
        if (clientDetails == null) {
            this.clientDetails = OrbitClientDetails.getInstance();
        }
        return this.clientDetails;
    }

    private MultiValueMap<String, String> getRequestBody(String token) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("token", token);
        body.add("client_id", getClientDetails().getClientId());
        body.add("client_secret", getClientDetails().getClientSecret());
        return body;
    }

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        TokenIntrospectionSuccessResponse introspectionSuccessResponse = client.post()
                .uri(getClientDetails().getIntrospectionUri())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(getRequestBody(token)))
                .retrieve()
                .toEntity(String.class)
                .map(this::toNimbusSuccess)
                .block(Duration.ofSeconds(5));
        if (introspectionSuccessResponse == null || !introspectionSuccessResponse.isActive()) {
            this.logger.trace("Did not validate token since it is inactive");
            throw new BadOpaqueTokenException("Provided token isn't active");
        }
        return convertClaimsSet(introspectionSuccessResponse);
    }

    private TokenIntrospectionSuccessResponse toNimbusSuccess(ResponseEntity<String> entity) {
        HTTPResponse httpResponse = adaptToNimbusResponse(entity);
        TokenIntrospectionResponse tokenIntrospectionResponse = parseNimbusResponse(httpResponse);
        return castToNimbusSuccess(tokenIntrospectionResponse);
    }

    private HTTPResponse adaptToNimbusResponse(ResponseEntity<String> entity) {
        MediaType contentType = entity.getHeaders().getContentType();

        if (contentType == null) {
            this.logger.trace("Did not receive Content-Type from introspection endpoint in response");

            throw new OAuth2IntrospectionException("Introspection endpoint response was invalid, as no Content-Type header was provided");
        }

        // Nimbus expects JSON, but does not appear to validate this header first.
        if (!contentType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
            this.logger.trace("Did not receive JSON-compatible Content-Type from introspection endpoint in response");
            throw new OAuth2IntrospectionException("Introspection endpoint response was invalid, as content type '" + contentType + "' is not compatible with JSON");
        }

        HTTPResponse response = new HTTPResponse(entity.getStatusCode().value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, contentType.toString());
        response.setContent(entity.getBody());

        if (response.getStatusCode() != HTTPResponse.SC_OK) {
            this.logger.trace("Introspection endpoint returned non-OK status code");

            throw new OAuth2IntrospectionException("Introspection endpoint responded with HTTP status code " + response.getStatusCode());
        }
        return response;
    }

    private TokenIntrospectionResponse parseNimbusResponse(HTTPResponse response) {
        try {
            return TokenIntrospectionResponse.parse(response);
        } catch (Exception ex) {
            throw new OAuth2IntrospectionException(ex.getMessage(), ex);
        }
    }

    private TokenIntrospectionSuccessResponse castToNimbusSuccess(TokenIntrospectionResponse introspectionResponse) {
        if (!introspectionResponse.indicatesSuccess()) {
            ErrorObject errorObject = introspectionResponse.toErrorResponse().getErrorObject();
            String message = "Token introspection failed with response " + errorObject.toJSONObject().toJSONString();
            this.logger.trace(message);
            throw new OAuth2IntrospectionException(message);
        }
        return (TokenIntrospectionSuccessResponse) introspectionResponse;
    }

    private OAuth2AuthenticatedPrincipal convertClaimsSet(TokenIntrospectionSuccessResponse response) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Map<String, Object> claims = response.toJSONObject();
        if (response.getAudience() != null) {
            List<String> audiences = new ArrayList<>();
            for (Audience audience : response.getAudience()) {
                audiences.add(audience.getValue());
            }
            claims.put(OAuth2TokenIntrospectionClaimNames.AUD, Collections.unmodifiableList(audiences));
        }
        if (response.getClientID() != null) {
            claims.put(OAuth2TokenIntrospectionClaimNames.CLIENT_ID, response.getClientID().getValue());
        }
        if (response.getExpirationTime() != null) {
            Instant exp = response.getExpirationTime().toInstant();
            claims.put(OAuth2TokenIntrospectionClaimNames.EXP, exp);
        }
        if (response.getIssueTime() != null) {
            Instant iat = response.getIssueTime().toInstant();
            claims.put(OAuth2TokenIntrospectionClaimNames.IAT, iat);
        }
        if (response.getIssuer() != null) {
            // RFC-7662 page 7 directs users to RFC-7519 for defining the values of these
            // issuer fields.
            // https://datatracker.ietf.org/doc/html/rfc7662#page-7
            //
            // RFC-7519 page 9 defines issuer fields as being 'case-sensitive' strings
            // containing
            // a 'StringOrURI', which is defined on page 5 as being any string, but
            // strings containing ':'
            // should be treated as valid URIs.
            // https://datatracker.ietf.org/doc/html/rfc7519#section-2
            //
            // It is not defined however as to whether-or-not normalized URIs should be
            // treated as the same literal
            // value. It only defines validation itself, so to avoid potential ambiguity
            // or unwanted side effects that
            // may be awkward to debug, we do not want to manipulate this value. Previous
            // versions of Spring Security
            // would *only* allow valid URLs, which is not what we wish to achieve here.
            claims.put(OAuth2TokenIntrospectionClaimNames.ISS, response.getIssuer().getValue());
        }
        if (response.getNotBeforeTime() != null) {
            claims.put(OAuth2TokenIntrospectionClaimNames.NBF, response.getNotBeforeTime().toInstant());
        }
        if (response.getScope() != null) {
            List<String> scopes = Collections.unmodifiableList(response.getScope().toStringList());
            claims.put(OAuth2TokenIntrospectionClaimNames.SCOPE, scopes);
            for (String scope : scopes) {
                authorities.add(new SimpleGrantedAuthority(AUTHORITY_PREFIX + scope));
            }
        }
        if (claims.get(CLAIMS_KEY_AUTHORITIES) != null) {
            if (claims.get(CLAIMS_KEY_AUTHORITIES) instanceof JSONArray array) {
                if (!array.isEmpty()) {
                    array.stream().filter(Objects::nonNull).map(Objects::toString).map(SimpleGrantedAuthority::new).forEach(authorities::add);
                }
            }
        }
        String name = Objects.toString(claims.get(CLAIMS_KEY_USERNAME), response.getClientID().getValue());
        authorities = authorities.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return new OAuth2IntrospectionAuthenticatedPrincipal(name, claims, authorities);
    }

}
