package com.inmaytide.orbit.commons.consts;

/**
 * 全局使用的缓存名称配置
 *
 * @author inmaytide
 * @since 2023/3/31
 */
public enum CacheNames {

    AUTHORIZATION_STORE("AUTHORIZATION_STORE"),

    ACCESS_TOKEN_STORE("ACCESS_TOKEN_STORE");

    private final String value;

    CacheNames(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
