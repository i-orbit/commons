package com.inmaytide.orbit.commons.domain.dto.params;

import com.inmaytide.orbit.Version;
import com.inmaytide.orbit.commons.constants.Bool;
import com.inmaytide.orbit.commons.constants.Platforms;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author inmaytide
 * @since 2024/1/19
 */
public class LoginParameters implements Serializable {

    @Serial
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

    private String loginName;

    private String password;

    private Platforms platform;

    private Bool forcedReplacement;

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

    public Platforms getPlatform() {
        return platform;
    }

    public void setPlatform(Platforms platform) {
        this.platform = platform;
    }

    public Bool getForcedReplacement() {
        return forcedReplacement;
    }

    public void setForcedReplacement(Bool forcedReplacement) {
        this.forcedReplacement = forcedReplacement;
    }

}
