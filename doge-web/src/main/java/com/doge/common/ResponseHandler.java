package com.doge.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 全局参数 handler
 *
 * @author shixinyu
 * @date 2021-06-16 16:15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ResponseHandler {
    boolean handler() default true;
}
