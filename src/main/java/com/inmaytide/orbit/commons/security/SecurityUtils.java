package com.inmaytide.orbit.commons.security;

import com.inmaytide.exception.web.UnauthorizedException;
import com.inmaytide.orbit.commons.consts.ParameterNames;
import com.inmaytide.orbit.commons.consts.Platforms;
import com.inmaytide.orbit.commons.consts.Roles;
import com.inmaytide.orbit.commons.domain.GlobalUser;
import com.inmaytide.orbit.commons.provider.UserDetailsProvider;
import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
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

    private static UserDetailsProvider userDetailsProvider;

    public static UserDetailsProvider getUserDetailsProvider() {
        if (userDetailsProvider == null) {
            userDetailsProvider = ApplicationContextHolder.getInstance().getBean(UserDetailsProvider.class);
        }
        return userDetailsProvider;
    }

    public static boolean isAuthorized() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    /**
     * 获取当前用户详细信息
     *
     * @return 用户详情
     * @throws UnauthorizedException If not authenticated
     */
    public static @NonNull GlobalUser getAuthorizedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException();
        }
        return getUserDetailsProvider().get(authentication.getName());
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

    /**
     * 判断当前用户是否是超级管理员
     *
     * @throws UnauthorizedException If not authenticated
     */
    public static boolean isSuperAdministrator() {
        return getAuthorizedUser().getRoles().contains(Roles.ROLE_S_ADMINISTRATOR.name());
    }

    /**
     * 判断当前用户是否是指定租户的租户管理员
     *
     * @throws UnauthorizedException If not authenticated
     */
    public static boolean isTenantAdministrator(Long tenantId) {
        return getAuthorizedUser().getRoles().contains(Roles.ROLE_T_ADMINISTRATOR.name())
                && Objects.equals(getAuthorizedUser().getTenantId(), tenantId);
    }

    /**
     * 判断当前用户是否是机器人
     *
     * @throws UnauthorizedException If not authenticated
     */
    public static boolean isRobot() {
        return getAuthorizedUser().getRoles().contains(Roles.ROLE_ROBOT.name());
    }
}
