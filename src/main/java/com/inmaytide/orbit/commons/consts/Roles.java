package com.inmaytide.orbit.commons.consts;

/**
 * 系统内置角色
 *
 * @author inmaytide
 * @since 2023/3/31
 */
public enum Roles {

    /**
     * 角色在Spring Security中的前缀字符串
     */
    ROLE_,
    /**
     * 超级管理员<br />
     * 拥有系统全局最大权限, 通过系统静态配置开启, 不允许通过其他方式分配该角色
     */
    S_ADMINISTRATOR,

    /**
     * 租户管理员<br/>
     * 有用租户内最大权限, 比超级管理员少一些系统全局配置的权限. 允许用户自行分配
     */
    T_ADMINISTRATOR,

    /**
     * 机器人<br/>
     * 权限等同于超级管理员, 但不允许系统外部使用
     */
    ROBOT

}
