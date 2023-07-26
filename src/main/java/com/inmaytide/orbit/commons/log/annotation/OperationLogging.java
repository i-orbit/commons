package com.inmaytide.orbit.commons.log.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用户操作日志 AOP 注解
 * 添加在 Spring MVC Controller 层的接口方法上
 *
 * @author inmaytide
 * @since 2022/03/12
 * @see io.swagger.v3.oas.annotations.tags.Tag
 * @see io.swagger.v3.oas.annotations.Operation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OperationLogging {

    /**
     * 操作说明 <br/>
     * 为空时自动获取该方法上的 {@link Operation#summary()} 配置信息
     */
    String description() default "";

    /**
     * 操作对应业务说明 <br/>
     * 为空时自动获取对应 Controller 类上的 {@link Tag#name()} 配置信息
     */
    String business() default "";

    /**
     * 日志中是记录请求中的参数信息 <br/>
     */
    boolean retainArguments() default false;

    /**
     * 日志中是记录请求返回的内容 <br/>
     * 请求响应异常时强制保留返回内容，即错误信息
     */
    boolean retainResponseBody() default false;
}
