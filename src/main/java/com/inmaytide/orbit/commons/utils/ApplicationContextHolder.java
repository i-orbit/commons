package com.inmaytide.orbit.commons.utils;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author inmaytide
 * @since 2023/4/7
 */
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
    public void setApplicationContext(ApplicationContext context) throws BeansException {
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

    public boolean getPropertyBoolValue(String s, boolean defaultValue) {
        return context.getEnvironment().getProperty(s, Boolean.class, defaultValue);
    }
}
