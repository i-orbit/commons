package com.inmaytide.orbit.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 通用工具类
 *
 * @author inmaytide
 * @since 2023/4/7
 */
public class CommonUtils {

    public static List<String> splitByCommas(String joined) {
        if (StringUtils.isEmpty(joined)) {
            return Collections.emptyList();
        }
        return Pattern.compile(",").splitAsStream(joined)
                .map(StringUtils::trim)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
    }

    public static <T> List<T> splitByCommas(String joined, Predicate<String> predicate, Function<String, T> mapper) {
        return splitByCommas(joined).stream()
                .filter(predicate)
                .map(mapper)
                .collect(Collectors.toList());
    }

    public static List<Long> splitToLongByCommas(String joined) {
        return splitByCommas(joined, NumberUtils::isCreatable, NumberUtils::createLong);
    }

}
