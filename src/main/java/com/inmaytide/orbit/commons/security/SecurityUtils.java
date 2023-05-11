package com.inmaytide.orbit.commons.security;

import com.inmaytide.exception.web.UnauthorizedException;
import com.inmaytide.orbit.commons.domain.GlobalUser;
import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;

/**
 * @author inmaytide
 * @since 2023/4/7
 */
public class SecurityUtils {

    private static UserDetailsService userDetailsService;

    public static UserDetailsService getUserDetailsService() {
        if (userDetailsService == null) {
            userDetailsService = ApplicationContextHolder.getInstance().getBean(UserDetailsService.class);
        }
        return userDetailsService;
    }

    /**
     * 获取当前登录用户详细信息
     *
     * @return 用户详情
     * @throws UnauthorizedException 如果用户未登录
     */
    public static @NonNull GlobalUser getAuthorizedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException();
        }
        return getUserDetailsService().loadUserById(authentication.getName());
    }

    /**
     * 获取当前登录用户详细信息
     *
     * @return 用户详情, 当用户未登录时返回 null
     */
    public static @Nullable GlobalUser getAuthorizedUserAllowUnauthorized() {
        try {
            return getAuthorizedUser();
        } catch (UnauthorizedException ignored) {
            return null;
        }
    }

    public static String getTokenValue() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new UnauthorizedException();
        }
        if (authentication instanceof BearerTokenAuthentication bearerTokenAuthentication) {
            return bearerTokenAuthentication.getToken().getTokenValue();
        }
        throw new UnauthorizedException();
    }

}
