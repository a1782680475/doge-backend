package com.doge.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * 用户添加信息
 *
 * @author shixinyu
 * @date 2023/4/5 19:21
 */
@AllArgsConstructor
@Data
public class SysUserAddDTO {
    private String username;
    private String password;
}
