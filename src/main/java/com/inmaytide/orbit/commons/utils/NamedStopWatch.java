package com.inmaytide.orbit.commons.utils;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class NamedStopWatch {

    private final String name;

    private final StopWatch stopWatch;

    private final Map<String, BigDecimal> timelines;

    private long lastSplit;

    public NamedStopWatch(String name) {
        this.name = name;
        this.stopWatch = new StopWatch();
        this.timelines = new LinkedHashMap<>();
    }

    public static NamedStopWatch createStarted(String name) {
        NamedStopWatch res = new NamedStopWatch(name);
        res.start();
        return res;
    }

    public String getName() {
        return name;
    }

    public Map<String, BigDecimal> getTimelines() {
        return timelines;
    }

    public long getTime() {
        return stopWatch.getTime();
    }

    public void start() {
        stopWatch.start();
        lastSplit = 0;
    }

    public void split(String label) {
        long current = stopWatch.getTime();
        timelines.put(label, BigDecimal.valueOf(current - lastSplit).divide(BigDecimal.valueOf(1000d), 4, RoundingMode.HALF_UP));
        lastSplit = current;
    }

    public void stop() {
        stopWatch.stop();
    }

    public void stop(Logger logger) {
        stopWatch.stop();
        print(logger);
    }

    public void print(Logger logger) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        formatter.format("%n---------------------------------------- StopWatch(Name: %s) Report BEGIN ----------------------------------------%n", name);
        timelines.forEach((label, time) -> formatter.format("%-50s:  %4.4fs%n", label, time));
        formatter.format("%-50s:  %3.4fs%n", "TOTAL", BigDecimal.valueOf(getTime()).divide(BigDecimal.valueOf(1000d), 4, RoundingMode.HALF_UP));
        formatter.format("---------------------------------------- StopWatch(Name: %s) Report END ------------------------------------------", name);
        logger.debug(sb.toString());
    }
}
