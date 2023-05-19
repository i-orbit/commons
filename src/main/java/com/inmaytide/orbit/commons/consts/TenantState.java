package com.inmaytide.orbit.commons.consts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author inmaytide
 * @since 2023/5/19
 */
public enum TenantState {

    /**
     * 初始化状态, 用户创建后从未登录过
     */
    INITIALIZATION(10000, "初始化"),

    /**
     * 正常状态, 无功能限制
     */
    NORMAL(20000, "正常"),

    /**
     * License 已过期, 限制登录
     */
    EXPIRED(30000, "授权过期"),

    /**
     * 禁止该租户用户登录系统
     */
    DISABLED(40000, "禁用"),

    ;

    @EnumValue
    @JsonValue
    private final Integer value;

    private final String description;

    TenantState(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @JsonCreator
    public static TenantState ofValue(Integer value) {
        return Stream.of(values()).filter(e -> Objects.equals(value, e.getValue()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

}
