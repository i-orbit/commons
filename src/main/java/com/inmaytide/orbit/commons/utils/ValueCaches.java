package com.inmaytide.orbit.commons.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author inmaytide
 * @since 2022/9/16
 */
public class ValueCaches {

    private static ValueOperations<String, String> operations;

    private ValueCaches() {

    }

    private static ValueOperations<String, String> getOperations() {
        if (operations == null) {
            operations = ApplicationContextHolder.getInstance().getBean(StringRedisTemplate.class).opsForValue();
        }
        return operations;
    }

    public static String getCacheKey(String cacheName, String key) {
        return cacheName + "::" + key;
    }

    public static Optional<String> get(String cacheName, String key) {
        String value = getOperations().get(getCacheKey(cacheName, key));
        return Optional.ofNullable(value);
    }

    public static Optional<String> getAndDelete(String cacheName, String key) {
        String value = getOperations().getAndDelete(getCacheKey(cacheName, key));
        return Optional.ofNullable(value);
    }

    public static <T> Optional<T> getFor(String cacheName, String key, Class<T> cls) {
        final ObjectMapper mapper = ApplicationContextHolder.getInstance().getBean(ObjectMapper.class);
        return get(cacheName, key).map(value -> {
            try {
                return mapper.readValue(value, cls);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Set<String> keys(String cacheName) {
        Set<String> keys = getOperations().getOperations().keys(cacheName + "::*");
        return keys == null ? Collections.emptySet() : keys;
    }

    public static void put(String cacheName, String key, String value) {
        getOperations().set(getCacheKey(cacheName, key), value);
    }

    public static void put(String cacheName, String key, String value, long timeout, TimeUnit unit) {
        getOperations().set(getCacheKey(cacheName, key), value, timeout, unit);
    }

    public static void delete(String cacheName, String key) {
        getOperations().getOperations().delete(getCacheKey(cacheName, key));
    }

    public static void clear(String cacheName) {
        getOperations().getOperations().delete(keys(cacheName));
    }

    public static void expire(String cacheName, String key, long timeout, TimeUnit unit) {
        getOperations().getOperations().expire(getCacheKey(cacheName, key), timeout, unit);
    }

    public static Long getExpire(String cacheName, String key) {
        return getOperations().getOperations().getExpire(getCacheKey(cacheName, key));
    }
}
