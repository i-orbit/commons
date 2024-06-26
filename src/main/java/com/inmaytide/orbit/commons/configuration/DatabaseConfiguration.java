package com.inmaytide.orbit.commons.configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.inmaytide.orbit.commons.business.id.snowflake.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author inmaytide
 * @since 2023/4/6
 */
@Configuration
@ConditionalOnClass(com.baomidou.mybatisplus.core.MybatisPlusVersion.class)
public class DatabaseConfiguration {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 全局的唯一标识生成器
     */
    @Bean
    public IdentifierGenerator identifierGenerator(@Value("${orbit.server.worker-id}") Long workerId, @Value("${orbit.server.data-center-id}") Long dataCenterId) {
        return new SnowflakeIdGenerator(workerId, dataCenterId);
    }


}
