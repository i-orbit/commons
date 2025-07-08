package com.inmaytide.orbit.commons.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inmaytide.exception.web.domain.DefaultResponse;
import com.inmaytide.exception.web.translator.HttpExceptionTranslatorDelegator;
import com.inmaytide.orbit.commons.business.SystemUserService;
import com.inmaytide.orbit.commons.constants.Bool;
import com.inmaytide.orbit.commons.log.annotation.OperationLogging;
import com.inmaytide.orbit.commons.log.domain.OperationLog;
import com.inmaytide.orbit.commons.utils.HttpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author luomiao
 * @since 2021/6/7
 */
@Aspect
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnBean(OperationLogMessageProducer.class)
public class OperationLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(OperationLoggingAspect.class);

    protected final SystemUserService systemUserService;

    protected final StringRedisTemplate redisTemplate;

    protected final OperationLogMessageProducer producer;

    private final ObjectMapper objectMapper;

    private final HttpExceptionTranslatorDelegator throwableTranslator;

    public OperationLoggingAspect(SystemUserService systemUserService, StringRedisTemplate redisTemplate, OperationLogMessageProducer producer, ObjectMapper objectMapper, HttpExceptionTranslatorDelegator throwableTranslator) {
        this.systemUserService = systemUserService;
        this.redisTemplate = redisTemplate;
        this.producer = producer;
        this.objectMapper = objectMapper;
        this.throwableTranslator = throwableTranslator;
    }

    private OperationLog build(JoinPoint point, Bool result) {
        OperationLog log = OperationLogUtils.build(HttpUtils.getRequest(), point);
        log.setResult(result);
        return log;
    }

    @AfterReturning(value = "@annotation(com.inmaytide.orbit.commons.log.annotation.OperationLogging)", returning = "returnVal")
    public void onSuccess(JoinPoint point, Object returnVal) throws Throwable {
        Method method = OperationLogUtils.getMethod(point);
        OperationLogging annotation = method.getAnnotation(OperationLogging.class);
        OperationLog log = build(point, Bool.Y);
        if (annotation.retainResponseBody()) {
            log.setResponse(objectMapper.writeValueAsString(returnVal));
        }
        producer.produce(log);
    }

    @AfterThrowing(value = "@annotation(com.inmaytide.orbit.commons.log.annotation.OperationLogging)", throwing = "e")
    public void onFailed(JoinPoint point, Throwable e) {
        OperationLog log = build(point, Bool.N);
        throwableTranslator.translate(e).ifPresent(ex -> log.setResponse(DefaultResponse.withException(ex).URL(HttpUtils.getRequest().getRequestURI()).build().toString()));
        producer.produce(log);
    }


}
