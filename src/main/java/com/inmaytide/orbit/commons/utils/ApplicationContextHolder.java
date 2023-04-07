package com.inmaytide.orbit.commons.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author inmaytide
 * @since 2023/4/7
 */
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContextHolder INSTANCE;

    private ApplicationContext context;

    public ApplicationContextHolder() {
        if (INSTANCE != null) {
            throw new UnsupportedOperationException();
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
}
