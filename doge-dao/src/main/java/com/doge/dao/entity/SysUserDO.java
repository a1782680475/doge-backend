package com.doge.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录信息表
 *
 * @author shixinyu
 * @date 2021-06-09 08:36
 */
@Data
@NoArgsConstructor
@TableName(value = "sys_user")
public class SysUserDO implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "username")
    private String username;
    @TableField(value = "nickname")
    private String nickName;
    @TableField(value = "avatar")
    private String avatar;
    @TableField(value = "account_email")
    private String accountEmail;
    @TableField(value = "email")
    private String email;
    @TableField(value = "profile")
    private String profile;
    @TableField(value = "country")
    private String country;
    @TableField(value = "province_code")
    private Integer provinceCode;
    @TableField(value = "province")
    private String province;
    @TableField(value = "city_code")
    private Integer cityCode;
    @TableField(value = "city")
    private String city;
    @TableField(value = "address")
    private String address;
    @TableField(value = "phone")
    private String phone;
    @TableField(value = "password")
    private String password;
    @TableField(value = "is_enabled")
    private Boolean enabled;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}
