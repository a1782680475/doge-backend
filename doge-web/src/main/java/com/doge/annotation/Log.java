package com.doge.annotation;

import java.lang.annotation.*;

/**
 * Log注解，操作日志记录
 *
 * @author shixinyu
 * @date 2021-08-30 14:51
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 业务名称
     */
    String title() default "";
}
