package com.inmaytide.orbit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serial;
import java.util.Objects;

/**
 * @author inmaytide
 * @since 2023/3/11
 */
public class Version {

    private static final int MAJOR = 2;

    private static final int MINOR = 0;

    private static final int PATCH = 0;

    /**
     * Global Serialization value for Spring Authorization Server classes.
     */
    public static final long SERIAL_VERSION_UID = get().hashCode();

    /**
     * System version number <br />
     * <p>
     * format: MajorVersion.MinorVersion.PatchVersion
     */
    public static String get() {
        return MAJOR + "." + MINOR + "." + PATCH;
    }


    /**
     * 判断一个版本号主版本与系统当前版本是否匹配
     *
     * @throws MalformedVersionException 版本号为空或格式不正确
     */
    public static boolean matchMajorVersion(String version) throws MalformedVersionException {
        return Objects.equals(MAJOR, getMajorVersion(version));
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
