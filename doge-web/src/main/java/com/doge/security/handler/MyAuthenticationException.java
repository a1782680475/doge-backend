package com.doge.security.handler;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义认证异常（过滤器中的异常我们的统一异常处理无法捕获到，该类捕获其他未定义的认证异常，返回响应错误信息）
 *
 * @author shixinyu
 * @date 2021-06-19 09:49
 */
public class MyAuthenticationException extends AuthenticationException {
    /**
     * Constructs a new instance of AuthenticationException.
     * All fields are set to null.
     */
    public MyAuthenticationException(String msg) {
        super(msg);
    }
}

