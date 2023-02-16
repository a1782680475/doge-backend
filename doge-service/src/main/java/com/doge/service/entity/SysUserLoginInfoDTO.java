package com.doge.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录信息
 *
 * @author shixinyu
 * @date 2021-06-09 09:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserLoginInfoDTO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}

