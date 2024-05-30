package com.inmaytide.orbit.commons.domain;

import com.inmaytide.orbit.Version;
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
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

    @Schema(title = "唯一标识")
    private Long id;

    @Schema(title = "用户所属租户")
    private Long tenant;

    @Schema(title = "默认所属组织唯一标识")
    private Long organizationId;

    @Schema(title = "默认所属组织名称")
    private String organizationName;

    @Schema(title = "所属组织列表", description = "一个用户允许归属于多个组织")
    private List<Organization> organizations;

    @Schema(title = "默认岗位唯一标识")
    private Long positionId;

    @Schema(title = "默认岗位名称")
    private String positionName;

    @Schema(title = "岗位列表", description = "同一用户允许兼任多个岗位")
    private List<Position> positions;

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

    @Schema(title = "用户权限信息")
    private Permission permission;

    @Schema(title = "用户状态")
    private UserState state;

    @Schema(title = "用户离岗时任务代理人")
    private Long proxy;

    @Schema(title = "密码到期时间")
    private Instant passwordExpireAt;

    @Schema(title = "用户角色列表")
    private List<Role> roles;

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

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
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

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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
