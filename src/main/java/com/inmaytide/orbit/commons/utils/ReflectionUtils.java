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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author inmaytide
 * @since 2023/8/1
 */
public final class ReflectionUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ReflectionUtils.class);

    private static MetadataReaderFactory metadataReaderFactory;

    private ReflectionUtils() {

    }

    private static MetadataReaderFactory getMetadataReaderFactory() {
        if (metadataReaderFactory == null) {
            metadataReaderFactory = new CachingMetadataReaderFactory();
        }
        return metadataReaderFactory;
    }

    public static Set<Class<?>> findClasses(Collection<String> packageNames, Class<?> assignableFrom) throws IOException {
        return findClasses(packageNames, assignableFrom, true, true);
    }


    public static Set<Class<?>> findClasses(Collection<String> packageNames, Class<?> assignableFrom, boolean includeInterfaces, boolean includeAbstractClasses) throws IOException {
        if (packageNames == null || packageNames.isEmpty() || assignableFrom == null) {
            LOG.warn("The method \"findClasses\" was passed invalid parameters, and no results were found");
            return Collections.emptySet();
        }
        Set<Class<?>> classes = new HashSet<>();
        for (String packageName : packageNames) {
            classes.addAll(findClasses(packageName, assignableFrom, includeInterfaces, includeAbstractClasses));
        }
        return classes;
    }

    public static Set<Class<?>> findClasses(String packageName, Class<?> assignableFrom, boolean includeInterfaces, boolean includeAbstractClasses) throws IOException {
        if (StringUtils.isBlank(packageName)) {
            LOG.warn("The method \"findClasses\" was passed invalid parameters, and no results were found.");
            return Collections.emptySet();
        }
        LOG.debug("Start scanning class files under the \"{}\" package", packageName);
        ResourceLoader resourceLoader = ApplicationContextHolder.getInstance().getBean(ResourceLoader.class);
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        Resource[] resources = resolver.getResources("classpath*:" + packageName.replaceAll("[.]", "/") + "/**/*.class");
        LOG.debug("Found a total of {} class files under the \"{}\" package", resources.length, packageName);
        Set<Class<?>> classes = Stream.of(resources).map(ReflectionUtils::loadClass)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(c -> assignableFrom == null || ClassUtils.isAssignable(c, assignableFrom))
                .collect(Collectors.toSet());
        // 如果不包含接口和抽象类
        if (!includeInterfaces) {
            classes = classes.stream().filter(cls -> !cls.isInterface()).collect(Collectors.toSet());
        }
        if (!includeAbstractClasses) {
            classes = classes.stream().filter(cls -> !Modifier.isAbstract(cls.getModifiers())).collect(Collectors.toSet());
        }
        LOG.debug("Found a total of {} classes assignable from \"{}\" under the \"{}\" package and loaded successfully.", assignableFrom.getName(), classes.size(), packageName);
        return classes;
    }

    public static Optional<Class<?>> loadClass(Resource resource) {
        MetadataReader reader;
        try {
            reader = getMetadataReaderFactory().getMetadataReader(resource);
        } catch (IOException e) {
            LOG.trace("Failed to obtain \"MetadataReader\" of resource{filename = {}}", resource.getFilename(), e);
            return Optional.empty();
        }
        try {
            LOG.trace("Try to load class \"{}\"", reader.getClassMetadata().getClassName());
            return Optional.of(ClassUtils.forName(reader.getClassMetadata().getClassName(), ClassUtils.getDefaultClassLoader()));
        } catch (NoClassDefFoundError | ClassNotFoundException | UnsupportedClassVersionError e) {
            LOG.error("Failed to load class \"{}\"", reader.getClassMetadata().getClassName(), e);
        }
        return Optional.empty();
    }

}
