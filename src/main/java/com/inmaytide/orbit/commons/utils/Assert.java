package com.inmaytide.orbit.commons.utils;

import com.inmaytide.exception.web.BadRequestException;
import com.inmaytide.exception.web.domain.ErrorCode;

/**
 * @author inmaytide
 * @since 2023/5/19
 */
public final class Assert {

    private Assert() {
    }

    public static void notNull(Object value, ErrorCode error) {
        if (value == null) {
            throw new BadRequestException(error);
        }
    }

}
