package com.inmaytide.orbit.commons.configuration;

import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 * @author inmaytide
 * @since 2023/4/12
 */
@Component
@ConditionalOnMissingBean({CommonProperties.class})
public class CommonProperties implements InitializingBean {

    public boolean isEnableSuperAdministrator() {
        return ApplicationContextHolder.getInstance().getPropertyBoolValue("orbit.security.super-administrator.enable", false);
    }

    public String getSuperAdministratorUsername() {
        return ApplicationContextHolder.getInstance().getProperty("orbit.security.super-administrator.username");
    }

    public boolean forcePasswordChangesWhenNecessary() {
        return ApplicationContextHolder.getInstance().getPropertyBoolValue("orbit.security.force-password-changes-when-necessary", true);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
