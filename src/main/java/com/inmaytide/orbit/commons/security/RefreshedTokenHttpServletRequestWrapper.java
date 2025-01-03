package com.inmaytide.orbit.commons.security;

import com.inmaytide.orbit.commons.constants.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * @author inmaytide
 * @since 2025/1/3
 */
public class RefreshedTokenHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final String accessToken;

    public RefreshedTokenHttpServletRequestWrapper(HttpServletRequest request, String accessToken) {
        super(request);
        this.accessToken = accessToken;
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (StringUtils.equals(name, Constants.HttpHeaderNames.AUTHORIZATION)) {
            return Collections.enumeration(Collections.singleton(Constants.HttpHeaderNames.AUTHORIZATION_PREFIX + accessToken));
        }
        return super.getHeaders(name);
    }

    @Override
    public String getHeader(String name) {
        if (StringUtils.equals(name, Constants.HttpHeaderNames.AUTHORIZATION)) {
            return Constants.HttpHeaderNames.AUTHORIZATION_PREFIX + accessToken;
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> headerNames = new HashSet<>();
        headerNames.add(Constants.HttpHeaderNames.AUTHORIZATION);
        java.util.Enumeration<String> originalHeaderNames = super.getHeaderNames();
        while (originalHeaderNames.hasMoreElements()) {
            headerNames.add(originalHeaderNames.nextElement());
        }
        return java.util.Collections.enumeration(headerNames);
    }
}
