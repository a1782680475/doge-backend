package com.doge.security.filter;

import com.doge.security.handler.MyAuthenticationException;
import com.doge.utils.JwtTokenUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT认证处理过滤器
 *
 * @author shixinyu
 * @date 2021-06-10 11:58
 */
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestUrl = request.getRequestURI();
        final String loginUrl = "/auth/login";
        // 如果不为登录接口 则处理token
        if (!requestUrl.endsWith(loginUrl)) {
            // 获取token, 并检查登录状态
            String token = request.getHeader("Authorization");
            if (token != null) {
                if (JwtTokenUtils.isTokenExpired(token)) {
                    throw new MyAuthenticationException("认证失败，token无效或已过期");
                }
            }
        }
        chain.doFilter(request, response);
    }
}
