package com.doge.security.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doge.entity.vo.request.ImgCaptchaVO;
import com.doge.security.handler.MyAuthenticationException;
import com.doge.security.JwtAuthenticationToken;
import com.doge.utils.HttpRequestUtils;
import com.doge.service.utils.RsaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 账号登录认证流程过滤器
 *
 * @author shixinyu
 * @date 2021-06-09 15:36
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 传入认证相关处理器并设置之
     *
     * @param authManager    认证管理器
     * @param successHandler 认证成功处理器
     * @param failureHandler 认证失败处理器
     * @param eventPublisher
     * @return
     */
    public JwtLoginFilter(AuthenticationManager authManager,
                          AuthenticationSuccessHandler successHandler,
                          AuthenticationFailureHandler failureHandler,
                          ApplicationEventPublisher eventPublisher,
                          RedisTemplate redisTemplate) {
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", "POST"));
        setAuthenticationManager(authManager);
        setAuthenticationSuccessHandler(successHandler);
        setAuthenticationFailureHandler(failureHandler);
        setApplicationEventPublisher(eventPublisher);
        this.redisTemplate = redisTemplate;
    }

    /**
     * 认证处理逻辑
     *
     * @param request  请求
     * @param response 响应
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String body = HttpRequestUtils.getBody(request);
        JSONObject jsonObject = JSON.parseObject(body);
        //账户和密码
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String imgCaptcha = jsonObject.getString("imgCaptcha");
        ImgCaptchaVO imgCaptchaVO = JSON.parseObject(imgCaptcha, ImgCaptchaVO.class);
        String uuid = imgCaptchaVO.getCaptchaKey();
        String code = imgCaptchaVO.getCaptchaCode();
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        String redisCaptcha = redisTemplate.opsForValue().get(uuid).toString();
        //清除验证码
        redisTemplate.delete(uuid);
        if (StrUtil.isBlank(redisCaptcha)) {
            logger.error("验证码不存在或已过期");
            throw new MyAuthenticationException("验证码不存在或已过期");
        }
        if (!code.equalsIgnoreCase(redisCaptcha)) {
            logger.error("验证码错误");
            throw new MyAuthenticationException("验证码错误");
        }
        username = username.trim();
        password = RsaUtils.decrypt(password);
        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(username, password);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
