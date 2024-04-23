package com.inmaytide.orbit.commons.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Supports retrieving access tokens from http headers, request parameters and cookies
 *
 * @author inmaytide
 * @see org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver
 * @since 2024/4/22
 */
public class CustomizedBearerTokenResolver implements BearerTokenResolver {

    private static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";

    private final DefaultBearerTokenResolver httpHeadersAndRequestParametersResolver;

    public CustomizedBearerTokenResolver() {
        httpHeadersAndRequestParametersResolver = new DefaultBearerTokenResolver();
        httpHeadersAndRequestParametersResolver.setAllowUriQueryParameter(true);
        httpHeadersAndRequestParametersResolver.setAllowFormEncodedBodyParameter(true);
    }

    @Override
    public String resolve(HttpServletRequest request) {
        String token = httpHeadersAndRequestParametersResolver.resolve(request);
        if (StringUtils.isNotBlank(token)) {
            return token;
        }
        if (request.getCookies() != null) {
            return Stream.of(request.getCookies())
                    .filter(e -> Objects.equals(e.getName(), ACCESS_TOKEN_COOKIE_NAME))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }
        return token;
    }

}
