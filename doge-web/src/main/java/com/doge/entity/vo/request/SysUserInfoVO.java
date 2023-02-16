package com.doge.entity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户资料
 *
 * @author shixinyu
 * @date 2021-10-08 14:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class SysUserInfoVO {
    @ApiModelProperty(value="用户头像")
    private String avatar;
    @ApiModelProperty(value="用户昵称")
    private String nickName;
    @ApiModelProperty(value="邮箱")
    private String email;
    @ApiModelProperty(value="个人简介")
    private String profile;
    @ApiModelProperty(value="国家")
    private String country;
    @ApiModelProperty(value = "省份Code")
    private Integer provinceCode;
    @ApiModelProperty(value = "省份")
    private String province;
    @ApiModelProperty(value = "地州市Code")
    private Integer cityCode;
    @ApiModelProperty(value = "地州市")
    private String city;
    @ApiModelProperty(value="地址")
    private String address;
    @ApiModelProperty(value="联系电话")
    private String phone;
}
