package com.inmaytide.orbit.commons.domain;

import com.inmaytide.orbit.Version;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author inmaytide
 * @since 2024/5/29
 */
public class Permission implements Serializable {

    @Serial
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

    public static final String PERM_KEY_ORGANIZATION = "organization";

    public static final String PERM_KEY_AREA = "area";

    public static final String PERM_KEY_ROLE = "role";

    public static final String PERM_KEY_AUTHORITY = "authority";

    public static final String PERM_KEY_S_ORGANIZATION = "specified_organization";

    public static final String PERM_KEY_S_AREA = "specified_area";

    private List<String> roles;

    private List<String> authorities;

    private List<Long> organizations;

    private List<Long> areas;

    private List<Long> specifiedOrganizations;

    private List<Long> specifiedAreas;

    public List<String> getRoles() {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getAuthorities() {
        if (authorities == null) {
            authorities = new ArrayList<>();
        }
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public List<Long> getOrganizations() {
        if (organizations == null) {
            organizations = new ArrayList<>();
        }
        return organizations;
    }

    public void setOrganizations(List<Long> organizations) {
        this.organizations = organizations;
    }

    public List<Long> getAreas() {
        if (areas == null) {
            areas = new ArrayList<>();
        }
        return areas;
    }

    public void setAreas(List<Long> areas) {
        this.areas = areas;
    }

    public List<Long> getSpecifiedOrganizations() {
        if (specifiedOrganizations == null) {
            specifiedOrganizations = getOrganizations();
        }
        return specifiedOrganizations;
    }

    public void setSpecifiedOrganizations(List<Long> specifiedOrganizations) {
        this.specifiedOrganizations = specifiedOrganizations;
    }

    public List<Long> getSpecifiedAreas() {
        if (specifiedAreas == null) {
            specifiedAreas = getAreas();
        }
        return specifiedAreas;
    }

    public void setSpecifiedAreas(List<Long> specifiedAreas) {
        this.specifiedAreas = specifiedAreas;
    }

//    public boolean hasPermission(String permissionKey, Object value) {
//        if (Objects.equals(PERM_KEY_ORGANIZATION, permissionKey)) {
//            return getOrganizations().contains(value);
//        }
//
//
//        return getOrganizations().contains(organizationId);
//    }
}
