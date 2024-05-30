package com.inmaytide.orbit.commons.domain;

import com.inmaytide.orbit.Version;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author inmaytide
 * @since 2024/5/29
 */
public class Permission implements Serializable {

    @Serial
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

    private List<String> roles;

    private List<String> authorities;

    private List<Long> organizations;

    private List<Long> areas;

    private List<Long> specifiedOrganizations;

    private List<Long> specifiedAreas;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
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

    public List<Long> getSpecifiedOrganizations() {
        return specifiedOrganizations;
    }

    public void setSpecifiedOrganizations(List<Long> specifiedOrganizations) {
        this.specifiedOrganizations = specifiedOrganizations;
    }

    public List<Long> getSpecifiedAreas() {
        return specifiedAreas;
    }

    public void setSpecifiedAreas(List<Long> specifiedAreas) {
        this.specifiedAreas = specifiedAreas;
    }
}
