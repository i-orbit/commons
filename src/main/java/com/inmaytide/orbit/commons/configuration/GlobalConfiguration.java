package com.inmaytide.orbit.commons.configuration;

import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author inmaytide
 * @since 2023/4/4
 */
@Configuration
public class GlobalConfiguration {

    @Bean
    @Order(Integer.MIN_VALUE)
    public ApplicationContextHolder applicationContextHolder() {
        return new ApplicationContextHolder();
    }

}
