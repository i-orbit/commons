package com.inmaytide.orbit.commons.business;

import java.util.Optional;

/**
 * @author inmaytide
 * @since 2024/4/22
 */
public interface SystemPropertyService {

    Optional<String> getValue(Long tenant, String key);

    Optional<Integer> getIntValue(Long tenant, String key);

}
