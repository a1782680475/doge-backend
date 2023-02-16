package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * SysRoleVO
 *
 * @author shixinyu
 * @date 2021-06-29 14:49
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class SysRoleVO {
    @ApiModelProperty(value = "角色ID")
    private Integer id;
    @ApiModelProperty(value = "角色编码")
    private String roleCode;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "角色备注")
    private String remark;
    @ApiModelProperty(value = "创建者")
    private String createBy;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
