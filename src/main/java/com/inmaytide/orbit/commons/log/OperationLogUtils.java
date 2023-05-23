package com.inmaytide.orbit.commons.log;

import com.inmaytide.orbit.commons.consts.HttpHeaderNames;
import com.inmaytide.orbit.commons.consts.Marks;
import com.inmaytide.orbit.commons.consts.Platforms;
import com.inmaytide.orbit.commons.domain.GlobalUser;
import com.inmaytide.orbit.commons.log.annotation.OperationLogging;
import com.inmaytide.orbit.commons.log.domain.OperationLog;
import com.inmaytide.orbit.commons.security.SecurityUtils;
import com.inmaytide.orbit.commons.utils.HttpUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.time.Instant;

public final class OperationLogUtils {

    private OperationLogUtils() {

    }

    public static @NonNull OperationLog build(@Nullable GlobalUser operator) {
        OperationLog log = new OperationLog();
        if (operator != null) {
            log.setOperator(operator.getId());
        }
        log.setOperateTime(Instant.now());
        return log;
    }

    public static @NonNull OperationLog build(@NonNull HttpServletRequest request, @NonNull Method method) {
        OperationLogging annotation = method.getAnnotation(OperationLogging.class);
        GlobalUser user = SecurityUtils.getAuthorizedUser();
        OperationLog log = OperationLogUtils.build(user);
        log.setDescription(OperationLogUtils.getDescription(method, annotation));
        log.setBusiness(OperationLogUtils.getBusiness(method, annotation));
        log.setClientDescription(request.getHeader(HttpHeaderNames.USER_AGENT));
        log.setIpAddress(HttpUtils.getClientIpAddress(request));
        if (annotation.retainArguments()) {
            log.setArguments("");
        }
        log.setChain(request.getHeader(HttpHeaderNames.CALL_CHAIN));
        log.setPlatform(SecurityUtils.getPlatform().map(Platforms::name).orElse(Marks.NOT_APPLICABLE.getValue()));
        log.setUrl(request.getRequestURI());
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
        return api == null ? "" : StringUtils.join(api.name(), ",");
    }

}
