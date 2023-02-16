package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * UserVO
 *
 * @author shixinyu
 * @date 2021-06-15 14:30
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class SysUserVO {
    @ApiModelProperty(value = "用户ID")
    private Integer id;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "头像")
    private String avatar;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "简介")
    private String profile;
    @ApiModelProperty(value = "国家")
    private String country;
    @ApiModelProperty(value = "省份Code")
    private Integer provinceCode;
    @ApiModelProperty(value = "省份")
    private String province;
    @ApiModelProperty(value = "地州市Code")
    private Integer cityCode;
    @ApiModelProperty(value = "地州市")
    private String city;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "用户账户是否已启用")
    private Boolean enabled;
    @ApiModelProperty(value = "用户账户注册时间")
    private Date createTime;
}
