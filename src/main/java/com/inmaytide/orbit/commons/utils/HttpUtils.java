package com.inmaytide.orbit.commons.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author inmaytide
 * @since 2023/3/31
 */
public final class HttpUtils {

    public final static List<String> HEADER_NAMES_FOR_CLIENT_ID = List.of("X-Client-address", "X-Forward-For", "x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR");

    public static String getIpAddress(String addresses) {
        if (StringUtils.isBlank(addresses)) {
            return addresses;
        }
        return Pattern.compile(",").splitAsStream(addresses).map(StringUtils::trim).findFirst().orElse(null);
    }

    public static String getClientIpAddress(HttpServletRequest request) {
        for (String name : HEADER_NAMES_FOR_CLIENT_ID) {
            String value = request.getHeader(name);
            if (StringUtils.isNotBlank(value) && !StringUtils.equalsIgnoreCase("unknown", value)) {
                return getIpAddress(value);
            }
        }
        return request.getRemoteHost();
    }

}
