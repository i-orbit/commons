package com.inmaytide.orbit.commons.security;

import com.inmaytide.orbit.commons.configuration.GlobalProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * @author inmaytide
 * @since 2025/1/3
 */
public class OAuth2ResourceServerConfigurationAdapter {

    private final HandlerExceptionResolver exceptionResolver;

    private final GlobalProperties properties;

    public OAuth2ResourceServerConfigurationAdapter(HandlerExceptionResolver exceptionResolver, GlobalProperties properties) {
        this.exceptionResolver = exceptionResolver;
        this.properties = properties;
    }

    protected HttpSecurity configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new OAuth2AccessTokenRefreshFilter(), SecurityContextHolderFilter.class);
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.anonymous(AbstractHttpConfigurer::disable);
        http.headers(c -> c.httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable));
        http.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(c -> {
            c.accessDeniedHandler((req, res, ex) -> exceptionResolver.resolveException(req, res, null, ex));
            c.authenticationEntryPoint((req, res, ex) -> exceptionResolver.resolveException(req, res, null, ex));
        });
        http.oauth2ResourceServer(c -> {
            c.authenticationEntryPoint((req, res, ex) -> exceptionResolver.resolveException(req, res, null, ex));
            c.accessDeniedHandler((req, res, ex) -> exceptionResolver.resolveException(req, res, null, ex));
            c.bearerTokenResolver(new CustomizedBearerTokenResolver());
            c.opaqueToken(ot -> ot.introspector(new CustomizedOpaqueTokenIntrospector(properties)));
        });
        return http;
    }

}
