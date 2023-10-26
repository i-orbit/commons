package com.inmaytide.orbit.commons.utils;

import com.inmaytide.exception.web.BadRequestException;
import com.inmaytide.exception.web.domain.ErrorCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author inmaytide
 * @since 2023/5/19
 */
public final class Assert {

    private Assert() {
    }

    public static void nonNull(Object value, ErrorCode error) {
        if (value == null) {
            throw new BadRequestException(error);
        }
    }

    public static void nonBlank(String value, ErrorCode error) {
        if (StringUtils.isBlank(value)) {
            throw new BadRequestException(error);
        }
    }

}
