package com.doge.utils;

import com.doge.entity.SysUserInfo;
import com.doge.security.JwtUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Security相关操作
 *
 * @author shixinyu
 * @date 2021-06-09 16:43
 */
public class SecurityUtils {
    /**
     * 默认角色权限前缀
     */
    public static final String DEFAULT_ROLE_PREFIX = "ROLE_";

    /**
     * 超管角色标识
     */
    public static final String ROLE_ADMIN = "admin";

    /**
     * 获取用户id
     *
     * @return
     */
    public static Integer getUserId() {
        Integer userId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            userId = getUserInfo(authentication).getId();
        }
        return userId;
    }

    /**
     * 获取用户名
     *
     * @return
     */
    public static String getUserName() {
        String userName = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            userName = getUserInfo(authentication).getUserName();
        }
        return userName;
    }

    /**
     * 获取用户权限
     *
     * @return
     */
    public static Set<String> getAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authorityListToSet(authentication.getAuthorities());
    }

    /**
     * 获取用户角色
     *
     * @return
     */
    public static Set<String> getRoles() {
        Set<String> authorities = getAuthorities();
        return authorities.stream()
                .filter(grantedAuthority -> grantedAuthority.startsWith(DEFAULT_ROLE_PREFIX))
                .collect(Collectors.toSet());
    }

    /**
     * 权限集合转换
     *
     * @author shixinyu
     * @date 2021-07-31 16:12
     */
    public static Set<String> authorityListToSet(Collection<? extends GrantedAuthority> userAuthorities) {
        Set<String> set = new HashSet<>(userAuthorities.size());
        for (GrantedAuthority authority : userAuthorities) {
            set.add(authority.getAuthority());
        }
        return set;
    }

    /**
     * 获取用户认证信息
     *
     * @return
     */
    public static SysUserInfo getUserInfo(Authentication authentication) {
        SysUserInfo userInfo = null;
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal != null) {
                if (principal instanceof JwtUserDetails) {
                    userInfo = new SysUserInfo();
                    userInfo.setId(((JwtUserDetails) principal).getId());
                    userInfo.setUserName(((JwtUserDetails) principal).getUsername());
                } else if (principal instanceof SysUserInfo) {
                    userInfo = ((SysUserInfo) principal);
                }
            }
        }
        return userInfo;
    }

}