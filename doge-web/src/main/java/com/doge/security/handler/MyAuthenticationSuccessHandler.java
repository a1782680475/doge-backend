package com.doge.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.doge.common.ResponseEntity;
import com.doge.security.AccessToken;
import com.doge.utils.JwtTokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功处理器
 *
 * @author shixinyu
 * @date 2021-06-09 15:55
 */
@Component
public class MyAuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 生成并返回token给客户端，后续访问携带此token
        AccessToken token = JwtTokenUtils.generateToken(authentication);
        response.setContentType("application/json; charset=utf-8");
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setData(token);
        responseEntity.setErrorMessage("登录成功");
        String json = JSONObject.toJSONString(responseEntity);
        response.getWriter().print(json);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
