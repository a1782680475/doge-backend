package com.doge.common;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局返回参数封装
 *
 * @author shixinyu
 * @date 2021-06-16 16:14
 */
@ControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice {

    public GlobalResponseHandler() {
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        String returnTypeName = methodParameter.getParameterType().getName();
        ResponseHandler annotation = methodParameter.getMethod().getAnnotation(ResponseHandler.class);
        boolean handle = annotation == null || annotation.handler();
        return handle && !"org.springframework.http.ResponseEntity".equals(returnTypeName) && !"com.doge.common.ResponseEntity".equals(returnTypeName);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(o instanceof ResponseEntity){
            return o;
        }
        else {
            ResponseEntity<Object> response = ResponseEntity.ok(o);
            return response;
        }
    }
}
