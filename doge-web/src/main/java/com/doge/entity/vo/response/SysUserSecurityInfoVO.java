package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * SysUserSecurityInfoVO
 *
 * @author shixinyu
 * @date 2023/3/5 15:08
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class SysUserSecurityInfoVO {
    @ApiModelProperty(value = "用户绑定邮箱")
    private String accountEmail;
}
