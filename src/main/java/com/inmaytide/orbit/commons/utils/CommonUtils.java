package com.inmaytide.orbit.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 通用工具类
 *
 * @author inmaytide
 * @since 2023/4/7
 */
public final class CommonUtils {

    private CommonUtils() {

    }

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

    public static List<Integer> splitToIntegerByCommas(String joined) {
        return splitByCommas(joined, NumberUtils::isCreatable, NumberUtils::createInteger);
    }

    @SafeVarargs
    public static <E, M, R> List<R> map(Collection<E> entries, Function<M, R> converter, Function<E, M>... getters) {
        if (CollectionUtils.isEmpty(entries)) {
            return Collections.emptyList();
        }
        org.springframework.util.Assert.notNull(converter, "Converter can't be null");
        Assert.notEmpty(getters, "At least 1 elements in the \"getters\" parameter");
        return entries.stream().flatMap(e -> Stream.of(getters).map(g -> g.apply(e))).filter(Objects::nonNull).distinct().map(converter).collect(Collectors.toList());
    }

    @SafeVarargs
    public static <E, M> List<String> map(Collection<E> entries, Function<E, M>... getters) {
        return map(entries, Objects::toString, getters);
    }

    @SafeVarargs
    public static <E, M> String groupConcat(Collection<E> entries, String delimiter, boolean quote, Function<E, M>... getters) {
        return map(entries, getters).parallelStream().map(s -> quote ? org.springframework.util.StringUtils.quote(s) : s).collect(Collectors.joining(delimiter));
    }

    @SafeVarargs
    public static <E, M> String groupConcat(Collection<E> entries, boolean quote, Function<E, M>... getters) {
        return groupConcat(entries, ",", quote, getters);
    }

    @SafeVarargs
    public static <E, M> String groupConcat(Collection<E> entries, Function<E, M>... getters) {
        return groupConcat(entries, false, getters);
    }

}
