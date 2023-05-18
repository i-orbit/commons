package com.inmaytide.orbit.commons.consts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 系统用户状态
 *
 * @author inmaytide
 * @since 2023/3/31
 */
public enum UserState {

    /**
     * 初始化状态, 用户创建后从未登录过
     */
    INITIALIZATION(10000, "初始化"),
    /**
     * 正常状态
     */
    NORMAL(20000, "正常"),
    /**
     * 休假中<br/>
     * 当用户处理该状态时, 用户相关任务将自动转派给代理人
     */
    ON_HOLIDAY(30000, "休假"),
    /**
     * 不在岗(出差/外出学习/外出调研等)<br/>
     * 当用户处理该状态时. 用户相关任务将自动转派给代理人
     */
    OFF_DUTY(40000, "外出"),
    /**
     * 账号禁用, 无法登录系统
     */
    DISABLED(50000, "禁用"),

    /**
     * 账号锁定, 无法登录系统(一般用于暂时禁止登录)
     */
    LOCKED(60000, "禁用");;

    @EnumValue
    @JsonValue
    private final Integer value;

    private final String description;

    UserState(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @JsonCreator
    public static UserState ofValue(Integer value) {
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
