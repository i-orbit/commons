package com.inmaytide.orbit.commons.domain;

import com.inmaytide.orbit.Version;
import com.inmaytide.orbit.commons.constants.TenantStatus;
import com.inmaytide.orbit.commons.domain.pattern.Entity;

import java.io.Serial;

/**
 * @author inmaytide
 * @since 2023/6/1
 */
public class Tenant extends Entity {

    @Serial
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

    private String name;

    private String alias;

    private TenantStatus state;

    private String logo;

    private String license;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public TenantStatus getState() {
        return state;
    }

    public void setState(TenantStatus state) {
        this.state = state;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }
}
