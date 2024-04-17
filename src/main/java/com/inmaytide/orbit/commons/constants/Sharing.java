package com.inmaytide.orbit.commons.constants;

/**
 * 通用功能数据共享级别
 * <ul>
 *     <li>数据字典</li>
 *     <li>文档中心</li>
 * </ul>
 *
 * @author inmaytide
 * @since 2023/6/13
 */
public enum Sharing {

    /**
     * 创建人
     */
    PRIVATE,

    /**
     * 拥有数据所属区域权限
     */
    AREA,

    /**
     * 拥有数据所属组织
     */
    ORGANIZATION,

    /**
     * 租户人员
     */
    TENANT,

    /**
     * 所有人
     */
    GENERIC;


}
