package com.inmaytide.orbit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serial;
import java.util.Objects;

/**
 *
 * @author inmaytide
 * @since 2023/3/11
 */
public class Version {

    /**
     * System version number <br />
     *
     * format: MajorVersion.MinorVersion.InternalVersion
     */
    private static final String VERSION = "1.0.0";

    public static String get() {
        return VERSION;
    }

    /**
     * 判断一个版本号主版本与系统当前版本是否匹配
     *
     * @throws MalformedVersionException 版本号为空或格式不正确
     */
    public static boolean matchMajorVersion(String version) throws MalformedVersionException {
        return Objects.equals(getMajorVersion(get()), getMajorVersion(version));
    }

    private static Integer getMajorVersion(String version) throws MalformedVersionException {
        if (StringUtils.isBlank(version)) {
            throw new MalformedVersionException("Blank version number");
        }
        try {
            return NumberUtils.createInteger(StringUtils.split(version, ".")[0]);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new MalformedVersionException(version);
        }
    }

    public static class MalformedVersionException extends RuntimeException {

        @Serial
        private static final long serialVersionUID = -6224724290615698073L;

        public MalformedVersionException(String message) {
            super(message);
        }

    }
}
