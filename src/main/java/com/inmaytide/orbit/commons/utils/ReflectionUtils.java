package com.inmaytide.orbit.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author inmaytide
 * @since 2023/8/1
 */
public final class ReflectionUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ReflectionUtils.class);

    private ReflectionUtils() {

    }

    public static Set<Class<?>> findClasses(Collection<String> packageNames, Class<?> assignableFrom) throws IOException {
        return findClasses(packageNames, assignableFrom, true);
    }

    public static Set<Class<?>> findClasses(Collection<String> packageNames, Class<?> assignableFrom, boolean includeInterfacesAndAbstractClasses) throws IOException {
        if (packageNames == null || packageNames.isEmpty() || assignableFrom == null) {
            LOG.warn("The method \"findClasses\" was passed invalid parameters, and no results were found");
            return Collections.emptySet();
        }
        Set<Class<?>> classes = new HashSet<>();
        for (String packageName : packageNames) {
            classes.addAll(findClasses(packageName, assignableFrom, includeInterfacesAndAbstractClasses));
        }
        return classes;
    }

    public static Set<Class<?>> findClasses(String packageName, Class<?> assignableFrom, boolean includeInterfacesAndAbstractClasses) throws IOException {
        if (StringUtils.isBlank(packageName) || assignableFrom == null) {
            LOG.warn("The method \"findClasses\" was passed invalid parameters, and no results were found.");
            return Collections.emptySet();
        }
        LOG.debug("Start scanning class files under the \"{}\" package", packageName);
        ResourceLoader resourceLoader = ApplicationContextHolder.getInstance().getBean(ResourceLoader.class);
        Set<Class<?>> classes = new HashSet<>();
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
        Resource[] resources = resolver.getResources("classpath*:" + packageName.replaceAll("[.]", "/") + "/**/*.class");
        LOG.debug("Found a total of {} class files under the \"{}\" package", resources.length, packageName);
        for (Resource resource : resources) {
            MetadataReader reader = metadataReaderFactory.getMetadataReader(resource);
            LOG.trace("Try to load class \"{}\"", reader.getClassMetadata().getClassName());
            try {
                Class<?> cls = ClassUtils.forName(reader.getClassMetadata().getClassName(), ClassUtils.getDefaultClassLoader());
                if (ClassUtils.isAssignable(cls, assignableFrom)) {
                    classes.add(cls);
                }
            } catch (NoClassDefFoundError | ClassNotFoundException | UnsupportedClassVersionError e) {
                LOG.trace("Failed to load class \"{}\"", reader.getClassMetadata().getClassName(), e);
            }
        }
        // 如果不包含接口和抽象类
        if (!includeInterfacesAndAbstractClasses) {
            classes = classes.stream().filter(cls -> !cls.isInterface() && !Modifier.isAbstract(cls.getModifiers())).collect(Collectors.toSet());
        }
        LOG.debug("Found a total of {} classes assignable from \"{}\" under the \"{}\" package and loaded successfully.", assignableFrom.getName(), classes.size(), packageName);
        return classes;
    }

}
