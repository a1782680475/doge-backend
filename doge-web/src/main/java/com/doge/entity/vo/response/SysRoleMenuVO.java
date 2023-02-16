package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * SysRoleMenuVO
 *
 * @author shixinyu
 * @date 2021-07-29 14:57
 */
@Data
@ApiModel
public class SysRoleMenuVO {
    @ApiModelProperty(value = "roleId")
    private Integer roleId;
    @ApiModelProperty(value = "menuId")
    private Integer menuId;
}
