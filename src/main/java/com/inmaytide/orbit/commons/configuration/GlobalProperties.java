package com.inmaytide.orbit.commons.configuration;

import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
import com.inmaytide.orbit.commons.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author inmaytide
 * @since 2023/4/12
 */
@Component
@ConditionalOnMissingBean({GlobalProperties.class})
public class GlobalProperties implements InitializingBean {

    public boolean isEnableSuperAdministrator() {
        return ApplicationContextHolder.getInstance().getPropertyBoolValue("orbit.security.super-administrator.enable", false);
    }

    public List<String> getSuperAdministratorLoginNames() {
        String configuredValue = ApplicationContextHolder.getInstance().getProperty("orbit.security.super-administrator.login-names");
        return CommonUtils.splitByCommas(configuredValue);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
