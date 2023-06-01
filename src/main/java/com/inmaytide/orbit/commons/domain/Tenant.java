package com.inmaytide.orbit.commons.domain;

import com.inmaytide.orbit.commons.consts.Is;
import com.inmaytide.orbit.commons.consts.TenantState;
import com.inmaytide.orbit.commons.domain.pattern.Entity;

/**
 * @author inmaytide
 * @since 2023/6/1
 */
public class Tenant extends Entity {

    private String name;

    private String alias;

    private TenantState state;

    private String logo;

    private Is menuSynced;

    private Integer operationLogRetentionTime;

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

    public TenantState getState() {
        return state;
    }

    public void setState(TenantState state) {
        this.state = state;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Is getMenuSynced() {
        return menuSynced;
    }

    public void setMenuSynced(Is menuSynced) {
        this.menuSynced = menuSynced;
    }

    public Integer getOperationLogRetentionTime() {
        return operationLogRetentionTime;
    }

    public void setOperationLogRetentionTime(Integer operationLogRetentionTime) {
        this.operationLogRetentionTime = operationLogRetentionTime;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }
}
