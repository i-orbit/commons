package com.inmaytide.orbit.commons.security;

import com.inmaytide.exception.web.UnauthorizedException;
import com.inmaytide.orbit.commons.business.SystemUserService;
import com.inmaytide.orbit.commons.constants.Constants;
import com.inmaytide.orbit.commons.constants.Platforms;
import com.inmaytide.orbit.commons.constants.Roles;
import com.inmaytide.orbit.commons.domain.SystemUser;
import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;

import java.util.Objects;
import java.util.Optional;

import static com.inmaytide.orbit.commons.constants.Constants.Markers.SPRING_SECURITY_ROLE_PREFIX;

/**
 * @author inmaytide
 * @since 2023/4/7
 */
public class SecurityUtils {

    private static SystemUserService systemUserService;

    public static SystemUserService getSystemUserService() {
        if (systemUserService == null) {
            systemUserService = ApplicationContextHolder.getInstance().getBean(SystemUserService.class);
        }
        return systemUserService;
    }

    public static boolean isAuthorized() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    public static Optional<String> getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication instanceof BearerTokenAuthentication bearerTokenAuthentication) {
            return Optional.of(bearerTokenAuthentication.getToken().getTokenValue());
        }
        return Optional.empty();
    }

    /**
     * 获取当前用户详细信息
     *
     * @return 用户详情
     * @throws UnauthorizedException If not authenticated
     */
    public static @NonNull SystemUser getAuthorizedUser() {
        return getSystemUserService().get(getAuthorizedUserId());
    }

    /**
     * 获取当前用户ID
     *
     * @return 用户详情
     * @throws UnauthorizedException If not authenticated
     */
    public static @NonNull Long getAuthorizedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && NumberUtils.isCreatable(authentication.getName())) {
            return NumberUtils.toLong(authentication.getName());
        }
        throw new UnauthorizedException();
    }

    /**
     * 获取当前登录用户详细信息
     *
     * @return 用户详情, 当用户未登录时返回空
     */
    public static Optional<SystemUser> getAuthorizedUserAllowUnauthorized() {
        try {
            return Optional.of(getAuthorizedUser());
        } catch (UnauthorizedException ignored) {
            return Optional.empty();
        }
    }

    public static @NonNull Optional<Platforms> getPlatform() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication instanceof BearerTokenAuthentication bearerTokenAuthentication) {
            String value = Objects.toString(bearerTokenAuthentication.getTokenAttributes().get(Constants.RequestParameters.PLATFORM), StringUtils.EMPTY);
            return Optional.ofNullable(StringUtils.isBlank(value) ? null : Platforms.valueOf(value));
        }
        return Optional.empty();
    }

    public static boolean hasRole(String roleCode) {
        if (!roleCode.startsWith(SPRING_SECURITY_ROLE_PREFIX)) {
            roleCode = SPRING_SECURITY_ROLE_PREFIX + roleCode;
        }
        return hasAuthority(roleCode);
    }

    public static boolean hasAuthority(String authorityCode) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && authentication.isAuthenticated()
                && authentication.getAuthorities().stream().anyMatch(authority -> Objects.equals(authorityCode, authority.getAuthority()));
    }

    /**
     * 判断当前用户是否是超级管理员
     */
    public static boolean isSuperAdministrator() {
        return hasRole(Roles.ROLE_S_ADMINISTRATOR.name());
    }

    /**
     * 判断当前用户是否是指定租户的租户管理员
     */
    public static boolean isTenantAdministrator(Long tenantId) {
        return hasRole(Roles.ROLE_T_ADMINISTRATOR.name());
    }

    /**
     * 判断当前用户是否是机器人
     */
    public static boolean isRobot() {
        return hasRole(Roles.ROLE_ROBOT.name());
    }
}
