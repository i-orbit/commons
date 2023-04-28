package com.inmaytide.orbit.commons.consts;

/**
 * 全局使用的 Http Headers 名称配置
 *
 * @author inmaytide
 * @since 2023/3/31
 */
public enum HttpHeaderNames {

    AUTHORIZATION("Authorization"),

    AUTHORIZATION_PREFIX("Bearer ");

    private final String value;

    HttpHeaderNames(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
