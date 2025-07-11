package com.inmaytide.orbit.commons.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inmaytide.orbit.commons.constants.Constants;
import com.inmaytide.orbit.commons.constants.Platforms;
import com.inmaytide.orbit.commons.domain.SystemUser;
import com.inmaytide.orbit.commons.log.annotation.OperationLogging;
import com.inmaytide.orbit.commons.log.domain.OperationLog;
import com.inmaytide.orbit.commons.security.SecurityUtils;
import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
import com.inmaytide.orbit.commons.utils.HttpUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class OperationLogUtils {

    private OperationLogUtils() {

    }

    public static @NonNull OperationLog build(@Nullable SystemUser operator) {
        OperationLog log = new OperationLog();
        if (operator != null) {
            log.setOperateBy(operator.getId());
            log.setTenantId(operator.getTenant());
        }
        return log;
    }

    private static String getRequestArguments(JoinPoint point) {
        ObjectMapper objectMapper = ApplicationContextHolder.getInstance().getBean(ObjectMapper.class);
        Object[] arguments = point.getArgs();
        List<String> args = new ArrayList<>();
        for (Object argument : arguments) {
            if (argument instanceof HttpServletRequest || argument instanceof HttpServletResponse) {
                continue;
            }
            if (argument instanceof MultipartFile file) {
                args.add(file.getName());
            }
            try {
                args.add(objectMapper.writeValueAsString(argument));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return StringUtils.join(args, ", ");
    }

    public static @NonNull OperationLog build(@NonNull HttpServletRequest request, @NonNull JoinPoint point) {
        Method method = getMethod(point);
        OperationLogging annotation = method.getAnnotation(OperationLogging.class);
        SystemUser user = SecurityUtils.getAuthorizedUserAllowUnauthorized().orElse(null);
        OperationLog log = OperationLogUtils.build(user);
        log.setDescription(OperationLogUtils.getDescription(method, annotation));
        log.setBusiness(OperationLogUtils.getBusiness(method, annotation));
        log.setClientDescription(request.getHeader(Constants.HttpHeaderNames.USER_AGENT));
        log.setIpAddress(HttpUtils.getClientIpAddress(request));
        if (annotation.retainArguments()) {
            log.setArguments(getRequestArguments(point));
        }
        log.setChain(request.getHeader(Constants.HttpHeaderNames.CALL_CHAIN));
        log.setPlatform(SecurityUtils.getPlatform().map(Platforms::name).orElse(Constants.Markers.NOT_APPLICABLE));
        log.setPath(request.getRequestURI());
        log.setHttpMethod(request.getMethod());
        return log;
    }

    private static String getDescription(Method method, OperationLogging annotation) {
        if (StringUtils.isNotBlank(annotation.description())) {
            return annotation.description();
        }
        Operation api = method.getAnnotation(Operation.class);
        return api == null ? "" : api.summary();
    }

    private static String getBusiness(Method method, OperationLogging annotation) {
        if (StringUtils.isNotBlank(annotation.business())) {
            return annotation.business();
        }
        Tag api = method.getDeclaringClass().getAnnotation(Tag.class);
        return api == null ? "" : api.name();
    }

    public static Method getMethod(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        return signature.getMethod();
    }

}
