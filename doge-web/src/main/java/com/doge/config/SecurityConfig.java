package com.doge.config;

import com.doge.security.filter.JwtLoginFilter;
import com.doge.security.filter.JwtTokenAuthenticationFilter;
import com.doge.security.handler.MyAccessDeniedHandler;
import com.doge.security.handler.MyAuthenticationFailureHandler;
import com.doge.security.handler.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.annotation.Resource;

/**
 * SpringSecurity配置类
 *
 * @author shixinyu
 * @date 2021-06-09 08:36
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
    private MyAccessDeniedHandler accessDeniedHandler;
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;
    private MyAuthenticationFailureHandler authenticationFailureHandler;
    private RedisTemplate redisTemplate;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;
    SecurityConfig() {
    }

    @Autowired
    SecurityConfig(UserDetailsService userDetailsService, MyAccessDeniedHandler accessDeniedHandler, MyAuthenticationSuccessHandler authenticationSuccessHandler, MyAuthenticationFailureHandler authenticationFailureHandler,RedisTemplate redisTemplate) {
        this.userDetailsService = userDetailsService;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 之所以不用configure,是因为为了顺便解决UsernameNotFoundException无法被捕获的问题
     *
     * @author shixinyu
     * @date 2021-06-15 11:37
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .authenticationProvider(daoAuthenticationProvider());
    }

    /**
     * 密码加密，必须为 @Bean ，否则报错
     * 作用：实例化密码加密规则，该规则首先会校验数据库中存储的密码是否符合其规则（经过 BCryptPasswordEncoder 加密的密码
     * 的字符串符合一定的规则）：
     * 1.若不符合，直接报错；
     * 2.若符合，则会将前端传递的密码经过 BCryptPasswordEncoder 加密，再和数据库中的密码进行比对，一样则通过
     * 所以，这里要求，我们存入进数据库的密码不能是明文，而必须是经过 BCryptPasswordEncoder 加密后，才能存入数据库
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                // 跨域预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 配置无需授权访问url
                .antMatchers(
                        "/swagger**/**",
                        "/webjars/**",
                        "/v3/**",
                        "/doc.html",
                        "/auth/captcha",
                        "/auth/findPassword/*",
                        "/auth/bindEmailVerify",
                        "/druid/**",
                        "/files/**"
                ).permitAll()
                // 剩余接口全部需要授权访问
                .anyRequest().authenticated();
        // 配置异常处理器
        http.exceptionHandling().authenticationEntryPoint(accessDeniedHandler);
        // JWT认证处理过滤器
        http.addFilterBefore(new JwtTokenAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class);
        // 账号登录认证流程过滤器
        http.addFilterBefore(new JwtLoginFilter(authenticationManager(),authenticationSuccessHandler,authenticationFailureHandler,applicationEventPublisher,redisTemplate), UsernamePasswordAuthenticationFilter.class);
    }
}