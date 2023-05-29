package com.inmaytide.orbit.commons.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inmaytide.exception.translator.ThrowableTranslator;
import com.inmaytide.exception.web.HttpResponseException;
import com.inmaytide.exception.web.domain.DefaultResponse;
import com.inmaytide.orbit.commons.consts.Is;
import com.inmaytide.orbit.commons.log.annotation.OperationLogging;
import com.inmaytide.orbit.commons.log.domain.OperationLog;
import com.inmaytide.orbit.commons.provider.UserDetailsProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

/**
 * @author luomiao
 * @since 2021/6/7
 */
@Aspect
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class OperationLoggingAspect {

    protected final UserDetailsProvider userDetailsProvider;

    protected final StringRedisTemplate redisTemplate;

    protected final OperationLogMessageProducer producer;

    private final ObjectMapper objectMapper;

    private final ThrowableTranslator<HttpResponseException> throwableTranslator;

    public OperationLoggingAspect(UserDetailsProvider userDetailsProvider, StringRedisTemplate redisTemplate, OperationLogMessageProducer producer, ObjectMapper objectMapper, ThrowableTranslator<HttpResponseException> throwableTranslator) {
        this.userDetailsProvider = userDetailsProvider;
        this.redisTemplate = redisTemplate;
        this.producer = producer;
        this.objectMapper = objectMapper;
        this.throwableTranslator = throwableTranslator;
    }


    @AfterReturning(value = "@annotation(com.inmaytide.orbit.commons.log.annotation.OperationLogging)", returning = "returnVal")
    public void onSuccess(JoinPoint point, Object returnVal) throws Throwable {
        Method method = getMethod(point);
        OperationLogging annotation = method.getAnnotation(OperationLogging.class);
        OperationLog log = OperationLogUtils.build(getRequest(), method);
        log.setResult(Is.Y);
        if (annotation.retainResponseBody()) {
            log.setResponse(objectMapper.writeValueAsString(returnVal));
        }
        producer.produce(log);
    }

    @AfterThrowing(value = "@annotation(com.inmaytide.orbit.commons.log.annotation.OperationLogging)", throwing = "e")
    public void onFailed(JoinPoint point, Throwable e) {
        OperationLog log = OperationLogUtils.build(getRequest(), getMethod(point));
        log.setResult(Is.N);
        throwableTranslator.translate(e).ifPresent(ex -> log.setResponse(DefaultResponse.withException(ex).URL(getRequest().getRequestURI()).build().toString()));
        producer.produce(log);
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getRequest();
    }

    private Method getMethod(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        return signature.getMethod();
    }


}
