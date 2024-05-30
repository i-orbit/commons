package com.inmaytide.orbit.commons.constants;

/**
 * @author inmaytide
 * @since 2023/4/4
 */
public enum Bool {

    Y, N;

    public static Bool withValue(boolean value) {
        return value ? Y : N;
    }

}
