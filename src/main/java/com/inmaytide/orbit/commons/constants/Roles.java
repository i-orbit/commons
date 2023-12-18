package com.inmaytide.orbit.commons.constants;

/**
 * 系统内置角色
 *
 * @author inmaytide
 * @since 2023/3/31
 */
public enum Roles {

    /**
     * 超级管理员<br />
     * 拥有系统全局最大权限, 通过系统静态配置开启, 不允许通过其他方式分配该角色
     */
    ROLE_S_ADMINISTRATOR,

    /**
     * 租户管理员<br/>
     * 拥有租户内最大权限, 比超级管理员少一些系统全局配置的权限. 允许用户自行分配
     */
    ROLE_T_ADMINISTRATOR,

    /**
     * 机器人<br/>
     * 权限等同于超级管理员, 但仅用于微服务内部交互使用
     */
    ROLE_ROBOT

}
