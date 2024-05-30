package com.inmaytide.orbit.commons.domain;

import com.inmaytide.orbit.Version;
import com.inmaytide.orbit.commons.constants.Roles;
import com.inmaytide.orbit.commons.constants.UserState;
import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

/**
 * @author inmaytide
 * @since 2023/4/12
 */
public class Robot implements Serializable {

    @Serial
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

    public static final String ROBOT_GRANT_TYPE = "client_credentials";

    public static final String ROBOT_CLIENT_ID = "robot";

    private static volatile Robot INSTANCE;

    private Long id;

    private String name;

    private String loginName;

    private String password;

    private SystemUser user;

    private Robot() {

    }

    public static Robot getInstance() {
        if (null == INSTANCE) {
            synchronized (Robot.class) {
                if (null == INSTANCE) {
                    INSTANCE = ApplicationContextHolder.getInstance()
                            .getBinder()
                            .bind("orbit.robot", Robot.class)
                            .orElseThrow(() -> new UnsupportedOperationException("There is no valid robot configuration in the spring context"));
                    if (INSTANCE.id == null
                            || StringUtils.isBlank(INSTANCE.name)
                            || StringUtils.isBlank(INSTANCE.loginName)
                            || StringUtils.isBlank(INSTANCE.password)) {
                        throw new UnsupportedOperationException("There is no valid robot configuration in the spring context");
                    }

                }
            }
        }
        return INSTANCE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientSecretBasicAuthentication() {
        String clientId = URLEncoder.encode(getLoginName(), StandardCharsets.UTF_8);
        String clientSecret = URLEncoder.encode(getPassword(), StandardCharsets.UTF_8);
        String authorization = clientId + ":" + clientSecret;
        return "Basic " + Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8));
    }

    public SystemUser toSystemUser() {
        if (user == null) {
            user = new SystemUser();
            user.setId(Robot.getInstance().getId());
            user.setName(Robot.getInstance().getName());
            user.setLoginName(Robot.getInstance().getLoginName());
            user.setState(UserState.NORMAL);
            Permission permission = new Permission();
            permission.setAuthorities(Collections.emptyList());
            permission.setRoles(Collections.singletonList(Roles.ROLE_ROBOT.name()));
            user.setPermission(permission);
        }
        return user;
    }

}
