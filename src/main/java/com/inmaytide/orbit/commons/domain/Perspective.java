package com.inmaytide.orbit.commons.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户当前视角
 * @author inmaytide
 * @since 2023/6/27
 */
public class Perspective implements Serializable {

    private List<Long> authorizedOrganizations = new ArrayList<>(0);

    private List<Long> authorizedAreas = new ArrayList<>(0);

    private List<Long> organizations = new ArrayList<>(0);

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
