package com.inmaytide.orbit.commons.security;

import com.inmaytide.exception.web.servlet.DefaultHandlerExceptionResolver;
import com.inmaytide.orbit.commons.business.RefreshTokenService;
import com.inmaytide.orbit.commons.constants.Constants;
import com.inmaytide.orbit.commons.domain.Oauth2Token;
import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
import com.inmaytide.orbit.commons.utils.ValueCaches;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @author inmaytide
 * @since 2025/1/2
 */
public class OAuth2AccessTokenRefreshFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AccessTokenRefreshFilter.class);

    /**
     * access_token 离过期时间还有多少秒时自动刷新
     */
    private static final long REQUIRED_REFRESH_TOKEN_LT = 30;

    private static final long TOKEN_TEMPORARY_STORE_MILLISECONDS = 60 * 1000;

    private final BearerTokenResolver bearerTokenResolver = new CustomizedBearerTokenResolver();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String accessToken = bearerTokenResolver.resolve(request);
        if (StringUtils.isNotBlank(accessToken) && requireRenew(accessToken)) {
            log.debug("The access token needs to be refreshed");
            try {
                request = refreshAccessToken(request, response, accessToken);
            } catch (Exception e) {
                ApplicationContextHolder.getInstance().getBean(DefaultHandlerExceptionResolver.class).resolveException(request, response, null, e);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private HttpServletRequest refreshAccessToken(HttpServletRequest request, HttpServletResponse response, String accessToken) {
        String refreshedToken = ValueCaches.get(Constants.CacheNames.TOKEN_TEMPORARY_STORE, accessToken).orElse(null);
        if (refreshedToken != null) {
            log.debug("The access token is refreshed within {}ms and a new access token is read from the cache", TOKEN_TEMPORARY_STORE_MILLISECONDS);
        } else {
            log.debug("No new access token is read from the cache, request the remote api to refresh the access token");
            Optional<String> refreshToken = getRefreshToken(request);
            if (refreshToken.isEmpty()) {
                return request;
            }
            log.debug("Refresh Token value is \"{}\"", refreshToken.get());
            Oauth2Token token = ApplicationContextHolder.getInstance().getBean(RefreshTokenService.class).refreshToken(refreshToken.get());
            log.debug("The refresh result is {}", token);
            refreshedToken = token.getAccessToken();
            doCache(accessToken, token);
            setTokenCookies(response, token);
        }
        return new OAuth2RefreshedTokenHttpServletRequestWrapper(request, refreshedToken);
    }

    private void doCache(final String key, final Oauth2Token value) {
        // 将新token与旧token放入缓存绑定
        ValueCaches.put(Constants.CacheNames.TOKEN_TEMPORARY_STORE, key, value.getAccessToken(), TOKEN_TEMPORARY_STORE_MILLISECONDS, TimeUnit.MILLISECONDS);
    }

    private Cookie buildAccessTokenCookie(Oauth2Token token) {
        Cookie cookie = new Cookie(Constants.RequestParameters.ACCESS_TOKEN, token.getAccessToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    private Cookie buildRefreshTokenCookie(Oauth2Token token) {
        Cookie cookie = new Cookie(Constants.RequestParameters.REFRESH_TOKEN, token.getRefreshToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    protected void setTokenCookies(HttpServletResponse response, Oauth2Token token) {
        response.addCookie(buildAccessTokenCookie(token));
        response.addCookie(buildRefreshTokenCookie(token));
    }

    private Optional<String> getRefreshToken(HttpServletRequest request) {
        return Stream.of(request.getCookies())
                .filter(e -> Objects.equals(e.getName(), Constants.RequestParameters.REFRESH_TOKEN))
                .findFirst()
                .map(Cookie::getValue);
    }

    private boolean requireRenew(String accessToken) {
        Long expire = ValueCaches.getExpire(Constants.CacheNames.ACCESS_TOKEN_STORE, accessToken);
        log.debug("Access token \"{}\" will expired in {} seconds", accessToken, expire);
        expire = expire == null ? 0 : expire;
        return expire < REQUIRED_REFRESH_TOKEN_LT;
    }

}
