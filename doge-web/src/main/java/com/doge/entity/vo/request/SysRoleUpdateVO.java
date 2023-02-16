package com.doge.entity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SysRoleVO
 *
 * @author shixinyu
 * @date 2021-06-30 11:53
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class SysRoleUpdateVO {
    @ApiModelProperty(value = "角色id")
    private String id;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "角色备注")
    private String remark;
}
