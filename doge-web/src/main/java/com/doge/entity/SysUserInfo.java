package com.doge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录信息（JWT存取）
 *
 * @author shixinyu
 * @date 2021-06-10 10:30
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysUserInfo {
    private Integer id;
    private String userName;
}
