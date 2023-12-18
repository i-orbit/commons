package com.inmaytide.orbit.commons.constants;

/**
 * @author inmaytide
 * @since 2023/5/19
 */
public enum TenantState {

    /**
     * 初始化状态
     */
    INITIALIZATION,

    /**
     * 正常状态, 无功能限制
     */
    NORMAL,

    /**
     * License 已过期, 限制登录
     */
    EXPIRED,

    /**
     * 禁止该租户用户登录系统
     */
    DISABLED,

    ;

}
