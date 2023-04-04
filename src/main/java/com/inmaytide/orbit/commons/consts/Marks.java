package com.inmaytide.orbit.commons.consts;

/**
 * 全局使用特殊标记
 *
 * @author inmaytide
 * @since 2023/3/31
 */
public enum Marks {

    /**
     * 标记一个令牌是否被强制注销
     */
    USER_FORCE_LOGOUT("__force-logout"),

    /**
     * 标记一个登录请求是否为免密码登录
     */
    LOGIN_WITHOUT_PASSWORD("__without-password"),

    /**
     * 标记一些存在但不适用当前情况的相关信息
     */
    NOT_APPLICABLE("N/A");

    private final String value;

    Marks(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
