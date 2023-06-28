package com.inmaytide.orbit.commons.domain;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户当前视角
 *
 * @author inmaytide
 * @since 2023/6/27
 */
@Schema(title = "用户当前视角(数据权限)")
public class Perspective implements Serializable {

    @Schema(title = "用户拥有的所有组织权限")
    private List<Long> authorizedOrganizations = new ArrayList<>(0);

    @Schema(title = "用户拥有的所有区域权限")
    private List<Long> authorizedAreas = new ArrayList<>(0);

    @Schema(title = "用户当前选择的组织权限")
    private List<Long> organizations = new ArrayList<>(0);

    @Schema(title = "用户当前选择的区域权限")
    private List<Long> areas = new ArrayList<>(0);

    public List<Long> getAuthorizedOrganizations() {
        return authorizedOrganizations;
    }

    public void setAuthorizedOrganizations(List<Long> authorizedOrganizations) {
        this.authorizedOrganizations = authorizedOrganizations;
    }

    public List<Long> getAuthorizedAreas() {
        return authorizedAreas;
    }

    public void setAuthorizedAreas(List<Long> authorizedAreas) {
        this.authorizedAreas = authorizedAreas;
    }

    public List<Long> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Long> organizations) {
        this.organizations = organizations;
    }

    public List<Long> getAreas() {
        return areas;
    }

    public void setAreas(List<Long> areas) {
        this.areas = areas;
    }
}
