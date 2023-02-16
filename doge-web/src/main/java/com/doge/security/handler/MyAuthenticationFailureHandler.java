package com.doge.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.doge.common.ResponseCode;
import com.doge.common.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 认证失败处理器
 *
 * @author shixinyu
 * @date 2021-06-09 15:58
 */
@Component
public class MyAuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler{
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        String errorMessage = exception.getMessage();
        ResponseEntity responseEntity = ResponseEntity.error(ResponseCode.UNAUTHORIZED,errorMessage);
        if (exception instanceof LockedException) {
            responseEntity.setErrorMessage("账户被锁定，请联系管理员!");
        } else if (exception instanceof CredentialsExpiredException) {
            responseEntity.setErrorMessage("密码过期，请联系管理员!");
        } else if (exception instanceof DisabledException) {
            responseEntity.setErrorMessage("账户被禁用，请联系管理员!");
        } else if (exception instanceof BadCredentialsException) {
            responseEntity.setErrorMessage("密码输入错误，请重新输入!");
        } else if (exception instanceof UsernameNotFoundException) {
            responseEntity.setErrorMessage("不存在的用户名!");
        } else if (exception instanceof InsufficientAuthenticationException) {
            responseEntity.setErrorMessage("身份验证异常!");
        }
        //封装异常描述信息
        String json = JSONObject.toJSONString(responseEntity);
        out.write(json);
        out.flush();
        out.close();
    }
}
