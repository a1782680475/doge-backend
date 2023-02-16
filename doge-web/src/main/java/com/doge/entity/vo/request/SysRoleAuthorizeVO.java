package com.doge.entity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色授权信息
 *
 * @author shixinyu
 * @date 2021-07-29 15:00
 */
@Data
@ApiModel
public class SysRoleAuthorizeVO {
    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id", position = 1)
    private Integer id;
    /**
     * 页面权限id集合（菜单id）
     */
    @ApiModelProperty(value = "页面权限id集合", position = 2)
    private List<Integer> menuIds;
}
