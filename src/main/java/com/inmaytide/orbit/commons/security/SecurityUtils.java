package com.inmaytide.orbit.commons.security;

import com.inmaytide.exception.web.UnauthorizedException;
import com.inmaytide.orbit.commons.domain.GlobalUser;
import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
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
     */
    public static GlobalUser getAuthorizedUser() {
        return getUserDetailsService().loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public static GlobalUser getAuthorizedUserAllowUnauthorized() {
        try {
            return getUserDetailsService().loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (Exception ignored) {
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
