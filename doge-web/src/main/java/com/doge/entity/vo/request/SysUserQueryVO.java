package com.doge.entity.vo.request;

import com.doge.entity.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户查询信息
 *
 * @author shixinyu
 * @date 2021-06-21 11:39
 */
@Data
@ApiModel
public class SysUserQueryVO extends PageQuery{
    /**
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    private String username;
    /**
     * 是否启用
     */
    @ApiModelProperty(value="是否启用")
    private Boolean enabled;
}
