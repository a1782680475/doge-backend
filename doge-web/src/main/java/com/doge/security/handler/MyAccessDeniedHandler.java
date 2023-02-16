package com.doge.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.doge.common.ResponseCode;
import com.doge.common.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 认证异常处理器
 *
 * @author shixinyu
 * @date 2021-06-09 08:36
 */
@Component
public class MyAccessDeniedHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        ResponseEntity respBean = new ResponseEntity();
        respBean.setErrorCode(ResponseCode.UNAUTHORIZED.getCode());
        respBean.setErrorMessage(authException.getMessage());
        //封装异常描述信息
        String json = JSONObject.toJSONString(respBean);
        out.write(json);
        out.flush();
        out.close();
    }
}
