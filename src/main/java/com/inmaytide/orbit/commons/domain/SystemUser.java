package com.inmaytide.orbit.commons.domain;

import com.inmaytide.orbit.commons.business.SystemPropertyService;
import com.inmaytide.orbit.commons.constants.Bool;
import com.inmaytide.orbit.commons.constants.Constants;
import com.inmaytide.orbit.commons.constants.UserState;
import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * @author inmaytide
 * @since 2023/3/31
 */
@Schema(title = "系统用户详细信息", description = "登录用的详细信息, 包含功能权限和数据权限信息")
public class SystemUser implements Serializable {

    @Serial
    private static final long serialVersionUID = -1609661989868009319L;

    @Schema(title = "唯一标识")
    private Long id;

    @Schema(title = "用户所属租户")
    private Long tenant;

    @Schema(title = "默认所属组织")
    private Long defaultUnderOrganization;

    @Schema(title = "所属组织唯一标识列表", description = "一个用户允许归属于多个组织")
    private List<Long> underOrganizations;

    @Schema(title = "用户姓名")
    private String name;

    @Schema(title = "用户登录名")
    private String loginName;

    @Schema(title = "手机号码")
    private String telephoneNumber;

    @Schema(title = "邮箱地址")
    private String email;

    @Schema(title = "员工编号")
    private String employeeId;

    @Schema(title = "系统语言标识")
    private String lang;

    @Schema(title = "用户头像存储地址")
    private String avatar;

    @Schema(title = "电子签名存储地址")
    private String signature;

    @Schema(title = "用户拥有角色编号")
    private List<String> roles;

    @Schema(title = "用户拥有功能权限编号")
    private List<String> authorities;

    @Schema(title = "用户当前视角(数据权限)")
    private Perspective perspective;

    @Schema(title = "用户状态")
    private UserState state;

    @Schema(title = "用户离岗时任务代理人")
    private Long proxy;

    @Schema(title = "密码到期时间")
    private Instant passwordExpireAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenant() {
        return tenant;
    }

    public void setTenant(Long tenant) {
        this.tenant = tenant;
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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public Long getProxy() {
        return proxy;
    }

    public void setProxy(Long proxy) {
        this.proxy = proxy;
    }

    public Instant getPasswordExpireAt() {
        return passwordExpireAt;
    }

    public void setPasswordExpireAt(Instant passwordExpireAt) {
        this.passwordExpireAt = passwordExpireAt;
    }

    public boolean getForceChangePassword() {
        Bool forceChangePasswordWhenNecessary = ApplicationContextHolder.getInstance()
                .getBean(SystemPropertyService.class)
                .getValue(getTenant(), Constants.SystemPropertyKeys.FORCE_CHANGE_PASSWORD_WHEN_NECESSARY)
                .map(Bool::valueOf)
                .orElse(Bool.N);
        if (forceChangePasswordWhenNecessary == Bool.Y) {
            return state == UserState.INITIALIZATION
                    || getPasswordExpireAt() == null
                    || getPasswordExpireAt().isBefore(Instant.now());
        }
        return false;
    }

}
