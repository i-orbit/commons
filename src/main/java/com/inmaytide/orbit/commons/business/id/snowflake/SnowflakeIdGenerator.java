package com.inmaytide.orbit.commons.business.id.snowflake;

import com.inmaytide.orbit.commons.business.id.IdGenerator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author inmaytide
 * @since 2023/4/4
 */
public class SnowflakeIdGenerator implements IdGenerator<Long> {

    private final SnowflakeIdWorker worker;

    public SnowflakeIdGenerator(long workerId, long dataCenterId) {
        this.worker = new SnowflakeIdWorker(workerId, dataCenterId);
    }

    @Override
    public Long generate() {
        return worker.nextId();
    }

    @Override
    public List<Long> batchGenerate(int number) {
        return IntStream.range(0, number).mapToObj(i -> generate()).collect(Collectors.toList());
    }

}
