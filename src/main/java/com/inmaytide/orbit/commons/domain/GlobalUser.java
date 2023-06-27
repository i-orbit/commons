package com.inmaytide.orbit.commons.domain;

import com.inmaytide.orbit.commons.consts.UserState;

import java.io.Serializable;
import java.util.List;

/**
 * @author inmaytide
 * @since 2023/3/31
 */
public class GlobalUser implements Serializable {

    private Long id;

    private Long defaultUnderOrganization;

    private List<Long> underOrganizations;

    private String name;

    private String username;

    private String telephoneNumber;

    private String email;

    private String employeeId;

    private String lang;

    private String avatar;

    private String signature;

    private Long tenantId;

    private List<String> roles;

    private List<String> authorities;

    private Perspective perspective;

    private UserState state;

    private String stateName;

    private Long proxy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDefaultUnderOrganization() {
        return defaultUnderOrganization;
    }

    public void setDefaultUnderOrganization(Long defaultUnderOrganization) {
        this.defaultUnderOrganization = defaultUnderOrganization;
    }

    public List<Long> getUnderOrganizations() {
        return underOrganizations;
    }

    public void setUnderOrganizations(List<Long> underOrganizations) {
        this.underOrganizations = underOrganizations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

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

    public Perspective getPerspective() {
        return perspective;
    }

    public void setPerspective(Perspective perspective) {
        this.perspective = perspective;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Long getProxy() {
        return proxy;
    }

    public void setProxy(Long proxy) {
        this.proxy = proxy;
    }

}
