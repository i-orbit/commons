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
     * <br/>
     * 仅有拥有系统后台管理权限, 无任何业务功能(如: 组织管理、区域管理)权限
     * <ul>
     *     <li>租户管理</li>
     *     <li>系统功能配置</li>
     *     <li>通用数据字典管理</li>
     * </ul>
     */
    ROLE_S_ADMINISTRATOR,

    /**
     * 租户管理员<br/>
     * 拥有租户内最大权限
     */
    ROLE_T_ADMINISTRATOR,

    /**
     * 机器人<br/>
     * 拥有系统所有权限, 但仅用于微服务内部交互使用
     */
    ROLE_ROBOT

}
