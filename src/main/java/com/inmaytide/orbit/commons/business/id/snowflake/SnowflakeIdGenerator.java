package com.inmaytide.orbit.commons.business.id.snowflake;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

/**
 * @author inmaytide
 * @since 2023/4/4
 */
public class SnowflakeIdGenerator implements IdentifierGenerator {

    private final SnowflakeIdWorker worker;

    public SnowflakeIdGenerator(long workerId, long dataCenterId) {
        this.worker = new SnowflakeIdWorker(workerId, dataCenterId);
    }

    @Override
    public Long nextId(Object entity) {
        return worker.nextId();
    }

}
