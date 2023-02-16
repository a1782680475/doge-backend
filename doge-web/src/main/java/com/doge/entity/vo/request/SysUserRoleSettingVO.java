package com.doge.entity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户角色关联信息
 *
 * @author shixinyu
 * @date 2021-07-26 17:11
 */
@Data
@ApiModel
public class SysUserRoleSettingVO {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", position = 1)
    private Integer id;
    /**
     * 角色id集合
     */
    @ApiModelProperty(value = "角色id集合", position = 2)
    private List<Integer> roleIds;
}
