package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * SysUserRoleVO
 *
 * @author shixinyu
 * @date 2021-07-27 11:39
 */
@Data
@ApiModel
public class SysUserRoleVO {
    @ApiModelProperty(value = "userId")
    private Integer userId;
    @ApiModelProperty(value = "roleId")
    private Integer roleId;
}
