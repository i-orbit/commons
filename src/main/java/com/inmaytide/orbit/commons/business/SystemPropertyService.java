package com.inmaytide.orbit.commons.business;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Optional;

/**
 * @author inmaytide
 * @since 2024/4/22
 */
public interface SystemPropertyService {

    Optional<String> getValue(String tenant, String key);

    default Optional<Integer> getIntValue(String tenant, String key) {
        return getValue(tenant, key).filter(NumberUtils::isCreatable).map(value -> NumberUtils.createBigDecimal(value).intValue());
    }

}
