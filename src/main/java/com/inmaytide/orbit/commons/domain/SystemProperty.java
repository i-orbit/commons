package com.inmaytide.orbit.commons.domain;

import com.inmaytide.orbit.commons.constants.Bool;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author inmaytide
 * @since 2023/5/26
 */
@Schema(
        title = "系统运行时属性配置",
        description = "可区分不同租户, 若租户没有单独配置取系统默认值. 系统默认值[租户ID=3721]仅超级管理员可修改"
)
public class SystemProperty implements Serializable {

    @Serial
    private static final long serialVersionUID = 6752525462362816880L;

    @Schema(title = "所属租户", description = "=3721时为系统全局")
    private Long tenantId;

    @Schema(title = "属性名称")
    private String name;

    @Schema(title = "属性描述")
    private String description;

    @Schema(title = "属性值")
    private String value;

    @Schema(title = "是否需要已授权", description = "仅前端查询时有效")
    private Bool authenticated;

    @Schema(title = "前端是否可见", description = "仅前端查询时有效")
    private Bool exposed;

    @Schema(title = "是否全局默认值", description = "仅租户为3721时有效")
    private Bool global;

    public static SystemProperty empty(Long tenantId, String name) {
        SystemProperty entity = new SystemProperty();
        entity.setTenantId(tenantId);
        entity.setName(name);
        entity.setAuthenticated(Bool.Y);
        entity.setExposed(Bool.N);
        return entity;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Bool getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Bool authenticated) {
        this.authenticated = authenticated;
    }

    public Bool getExposed() {
        return exposed;
    }

    public void setExposed(Bool exposed) {
        this.exposed = exposed;
    }

    public Bool getGlobal() {
        return global;
    }

    public void setGlobal(Bool global) {
        this.global = global;
    }
}
