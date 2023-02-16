package com.doge.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * 权限封装类
 *
 * @author shixinyu
 * @date 2021-07-30 10:13
 */
public class GrantedAuthorityImpl implements GrantedAuthority {
    
    private static final long serialVersionUID = 1L;

    private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
