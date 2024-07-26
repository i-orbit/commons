package com.inmaytide.orbit.commons.constants;

/**
 * 系统用户状态
 *
 * @author inmaytide
 * @since 2023/3/31
 */
public enum UserStatus {

    /**
     * 初始化状态, 用户创建后从未登录过
     */
    INITIALIZATION,

    /**
     * 正常状态
     */
    NORMAL,

    /**
     * 休假中<br/>
     * 当用户处理该状态时, 用户相关任务将自动转派给代理人
     */
    ON_HOLIDAY,

    /**
     * 不在岗(出差/外出学习/外出调研等)<br/>
     * 当用户处理该状态时. 用户相关任务将自动转派给代理人
     */
    OFF_DUTY,

    /**
     * 账号禁用, 无法登录系统
     */
    DISABLED,

    /**
     * 账号锁定, 无法登录系统(一般用于暂时禁止登录)
     */
    LOCKED;

}
