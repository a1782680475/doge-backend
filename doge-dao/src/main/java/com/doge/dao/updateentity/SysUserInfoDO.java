package com.doge.dao.updateentity;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * 用户资料更新DO
 *
 * @author shixinyu
 * @date 2021-10-08 14:43
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysUserInfoDO {
    private String avatar;
    private String nickName;
    private String email;
    private String profile;
    private String country;
    private Integer provinceCode;
    private String province;
    private Integer cityCode;
    private String city;
    private String address;
    private String phone;
    private Date updateTime;
}
