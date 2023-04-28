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
        return ApplicationContextHolder.getInstance().getPropertyBoolValue("carrot.security.super-administrator.enable", false);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
