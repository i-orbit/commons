package com.inmaytide.orbit.commons.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.inmaytide.orbit.commons.business.id.snowflake.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author inmaytide
 * @since 2023/4/4
 */
@Configuration
public class GlobalConfiguration {

    /**
     * 全局的唯一标识生成器
     */
    @Bean
    public IdentifierGenerator identifierGenerator(@Value("orbit.server.worker-id") Long workerId, @Value("orbit.server.data-center-id") Long dataCenterId) {
        return new SnowflakeIdGenerator(workerId, dataCenterId);
    }

}
