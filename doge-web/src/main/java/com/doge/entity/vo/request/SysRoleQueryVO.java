package com.doge.entity.vo.request;

import com.doge.entity.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色查询信息
 *
 * @author shixinyu
 * @date 2021-06-29 14:30
 */
@Data
public class SysRoleQueryVO extends PageQuery {
    /**
     * 角色名称
     */
    @ApiModelProperty(value="角色名称")
    private String roleName;
}
