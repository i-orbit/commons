package com.inmaytide.orbit.commons.business.id;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

/**
 * @author inmaytide
 * @since 2025/6/25
 */
public class CustomizedIdentifierGenerator implements IdentifierGenerator {

    @Override
    public Long nextId(Object entity) {
        return DefaultIdentifierGenerator.getInstance().nextId(entity);
    }

    @Override
    public String nextUUID(Object entity) {
        return UUIDv7.generateCompact();
    }

}
