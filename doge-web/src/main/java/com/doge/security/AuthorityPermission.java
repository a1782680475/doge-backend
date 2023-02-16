package com.doge.security;

import cn.hutool.core.util.StrUtil;
import com.doge.utils.SecurityUtils;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 自定义access表达式
 *
 * @author shixinyu
 * @date 2021-07-30 15:33
 */
@Component("aps")
public class AuthorityPermission {

    private enum preCheckState {
        // 跳过后面检查字节直接通过
        PASS(),
        // 跳过后面检查字节直接拒绝
        FAIL(),
        // 继续进行检查
        PROCEED()
    }

    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return boolean
     */
    public static boolean hasPermission(String permission) {
        if (preCheck() == preCheckState.PASS) {
            return true;
        }
        if (StrUtil.hasEmpty(permission)) {
            return false;
        }
        Set<String> authorities = SecurityUtils.getAuthorities();
        return authorities.contains("*") || authorities.contains(permission.trim());
    }

    /**
     * 验证用户是否具有某权限
     *
     * @param role 角色字符串
     * @return boolean
     */
    public boolean hasRole(String role) {
        if (preCheck() == preCheckState.PASS) {
            return true;
        }
        Set<String> roles = SecurityUtils.getRoles();
        return roles.contains(role.trim());
    }

    /**
     * 前置检查
     *
     * @param
     * @return com.doge.security.AuthorityPermission.preCheckState
     */
    private static preCheckState preCheck() {
        if (isAdmin()) {
            return preCheckState.PASS;
        }
        return preCheckState.PROCEED;
    }

    /**
     * 验证用户是否为管理员
     *
     * @return boolean
     */
    public static boolean isAdmin() {
        Set<String> roles = SecurityUtils.getRoles();
        return roles.contains(SecurityUtils.DEFAULT_ROLE_PREFIX + SecurityUtils.ROLE_ADMIN);
    }
}
