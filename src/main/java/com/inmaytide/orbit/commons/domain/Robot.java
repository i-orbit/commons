package com.inmaytide.orbit.commons.domain;

import com.inmaytide.orbit.commons.consts.Roles;
import com.inmaytide.orbit.commons.consts.UserState;
import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;

/**
 * @author inmaytide
 * @since 2023/4/12
 */
public class Robot implements Serializable {

    @Serial
    private static final long serialVersionUID = 1136144481198643169L;

    public static final String ROBOT_GRANT_TYPE = "client_credentials";

    public static final String ROBOT_CLIENT_ID = "robot";

    private static volatile Robot INSTANCE;

    private Long id;

    private String name;

    private String username;

    private String password;

    private GlobalUser user;

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
                            || StringUtils.isBlank(INSTANCE.username)
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

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public GlobalUser toGlobalUser() {
        if (user == null) {
            user = new GlobalUser();
            user.setId(Robot.getInstance().getId());
            user.setName(Robot.getInstance().getName());
            user.setUsername(Robot.getInstance().getUsername());
            user.setState(UserState.NORMAL);
            user.setAuthorities(Collections.emptyList());
            user.setRoles(Collections.singletonList(Roles.ROBOT.name()));
        }
        return user;
    }

}
