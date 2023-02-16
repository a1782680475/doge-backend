package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户菜单VO
 *
 * @author shixinyu
 * @date 2021-08-25 09:40
 */
@Data
public class AntMenuVO {
    @ApiModelProperty(value = "路由")
    private String path;
    @ApiModelProperty(value = "菜单名称")
    private String name;
    @ApiModelProperty(value = "图标")
    private String icon;
    @ApiModelProperty(value = "子菜单")
    private List<AntMenuVO> children;
}
