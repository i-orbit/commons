package com.inmaytide.orbit.commons.security;

import com.inmaytide.exception.web.UnauthorizedException;
import com.inmaytide.orbit.commons.consts.ParameterNames;
import com.inmaytide.orbit.commons.consts.Platforms;
import com.inmaytide.orbit.commons.domain.GlobalUser;
import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;

import java.util.Objects;
import java.util.Optional;

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
    public static Optional<GlobalUser> getAuthorizedUserAllowUnauthorized() {
        try {
            return Optional.of(getAuthorizedUser());
        } catch (UnauthorizedException ignored) {
            return Optional.empty();
        }
    }

    public static @NonNull Optional<Platforms> getPlatform() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication instanceof BearerTokenAuthentication bearerTokenAuthentication) {
            String value = Objects.toString(bearerTokenAuthentication.getTokenAttributes().get(ParameterNames.PLATFORM), StringUtils.EMPTY);
            return Optional.ofNullable(StringUtils.isBlank(value) ? null : Platforms.valueOf(value));
        }
        return Optional.empty();
    }

}
