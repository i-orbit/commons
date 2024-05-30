package com.inmaytide.orbit.commons.utils;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * @author inmaytide
 * @since 2023/7/21
 */
public final class DatetimeUtils {

    public static final ZoneOffset ZONE_OFFSET_EAST8 = ZoneOffset.ofHours(8);

    public static final String PATTERN_DATE_WITHOUT_JOINER = "yyyyMMdd";

    private DatetimeUtils() {

    }

    public static Instant getEndOfDay(Instant seed) {
        return seed.atOffset(ZONE_OFFSET_EAST8)
                .toLocalDate().atTime(23, 59, 59)
                .toInstant(ZONE_OFFSET_EAST8);
    }

    public static Instant getEndOfMonth(Instant seed) {
        return seed.atOffset(ZONE_OFFSET_EAST8)
                .toLocalDate()
                .with(TemporalAdjusters.lastDayOfMonth())
                .atTime(23, 59, 59)
                .toInstant(ZONE_OFFSET_EAST8);
    }

    public static Instant getEndOfYear(Instant seed) {
        return seed.atOffset(ZONE_OFFSET_EAST8)
                .toLocalDate()
                .with(TemporalAdjusters.lastDayOfYear())
                .atStartOfDay()
                .toInstant(ZONE_OFFSET_EAST8);
    }

    public static Instant getStartOfDay(Instant seed) {
        return seed.atOffset(ZONE_OFFSET_EAST8)
                .toLocalDate().atStartOfDay()
                .toInstant(ZONE_OFFSET_EAST8);
    }

    public static Instant getStartOfMonth(Instant seed) {
        return seed.atOffset(ZONE_OFFSET_EAST8)
                .toLocalDate()
                .with(TemporalAdjusters.firstDayOfMonth())
                .atStartOfDay()
                .toInstant(ZONE_OFFSET_EAST8);
    }

    public static Instant getStartOfYear(Instant seed) {
        return seed.atOffset(ZONE_OFFSET_EAST8)
                .toLocalDate()
                .with(TemporalAdjusters.firstDayOfYear())
                .atStartOfDay()
                .toInstant(ZONE_OFFSET_EAST8);
    }

    public static String format(Instant time, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(time.atOffset(ZONE_OFFSET_EAST8));
    }

    public static String formatDateWithoutJoiner(Instant time) {
        return format(time, PATTERN_DATE_WITHOUT_JOINER);
    }

}
