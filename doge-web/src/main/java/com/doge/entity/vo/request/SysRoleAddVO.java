package com.doge.entity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 角色添加VO
 *
 * @author shixinyu
 * @date 2021-06-30 11:53
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class SysRoleAddVO {
    @ApiModelProperty(value = "角色编码")
    private String roleCode;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "角色备注")
    private String remarks;
}
