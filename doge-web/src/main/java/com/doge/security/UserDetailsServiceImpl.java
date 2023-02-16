package com.doge.security;

import com.doge.service.SysUserService;
import com.doge.service.entity.SysMenuDTO;
import com.doge.service.entity.SysRoleDTO;
import com.doge.service.entity.SysUserDTO;
import com.doge.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户登录处理
 *
 * @author shixinyu
 * @date 2021-06-09 10:54
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private SysUserService sysUserService;

    UserDetailsServiceImpl() {
    }

    @Autowired
    UserDetailsServiceImpl(SysUserService userService) {
        this.sysUserService = userService;
    }

    /**
     * 登录验证
     *
     * @param username 用户名
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserDTO userDTO = sysUserService.getUserByUsername(username);
        if (userDTO == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }
        List<SysMenuDTO> menus = sysUserService.getMenuList(userDTO.getId());
        List<GrantedAuthority> grantedAuthorities = menus.stream().map(menu -> menu.getPermission()).map(GrantedAuthorityImpl::new).collect(Collectors.toList());
        //角色也是一种特殊权限，别忘了加进去
        List<SysRoleDTO> roles = sysUserService.getRoleList(userDTO.getId());
        for (SysRoleDTO role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(SecurityUtils.DEFAULT_ROLE_PREFIX + role.getRoleCode()));
        }
        JwtUserDetails jwtUserDetails = new JwtUserDetails(username, userDTO.getPassword(), userDTO.getEnabled(), grantedAuthorities);
        jwtUserDetails.setId(userDTO.getId());
        return jwtUserDetails;
    }
}
