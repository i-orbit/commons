package com.inmaytide.orbit.commons.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用户操作日志AOP注解
 * 添加在Spring MVC Controller层的接口方法上
 *
 * @author inmaytide
 * @since 2022/03/12
 * @see io.swagger.annotations.Api
 * @see io.swagger.annotations.ApiOperation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OperationLogging {

    /**
     * 操作说明 <br/>
     * 为空时自动获取该方法上的 {@link io.swagger.annotations.ApiOperation#value()} 配置信息
     */
    String description() default "";

    /**
     * 操作对应业务说明 <br/>
     * 为空时自动获取对应 Controller 类上的 {@link io.swagger.annotations.Api#tags()} 配置信息
     */
    String business() default "";

    /**
     * 日志中是记录请求中的参数信息 <br/>
     * TO-DO 目前除了登陆请求其他暂不支持
     */
    boolean retainArguments() default false;

    /**
     * 日志中是记录请求返回的内容 <br/>
     * 请求响应异常时强制保留返回内容，即错误信息
     */
    boolean retainResponseBody() default false;
}
