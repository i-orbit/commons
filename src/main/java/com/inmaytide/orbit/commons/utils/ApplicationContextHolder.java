package com.inmaytide.orbit.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author inmaytide
 * @since 2023/4/7
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContextHolder INSTANCE;

    private ApplicationContext context;

    private Binder binder;

    public ApplicationContextHolder() {
        if (INSTANCE != null) {
            throw new UnsupportedOperationException("\"ApplicationContextHolder\" has been initialized");
        }
        ApplicationContextHolder.INSTANCE = this;
    }

    public static ApplicationContextHolder getInstance() {
        return INSTANCE;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
        this.context = context;
    }

    public <T> T getBean(Class<T> cls) {
        return context.getBean(cls);
    }

    public Binder getBinder() {
        if (binder == null) {
            binder = Binder.get(context.getEnvironment());
        }
        return binder;
    }

    public <T> T getProperty(String key, Class<T> cls, T defaultValue) {
        return getBinder().bind(key, cls).orElse(defaultValue);
    }

    public <T> T getProperty(String key, Class<T> cls) {
        return getBinder().bind(key, cls).orElse(null);
    }

    public boolean getPropertyBoolValue(String key, boolean defaultValue) {
        return getProperty(key, Boolean.class, defaultValue);
    }

    public String getProperty(String key) {
        return Objects.toString(getProperty(key, Object.class), StringUtils.EMPTY);
    }
}
