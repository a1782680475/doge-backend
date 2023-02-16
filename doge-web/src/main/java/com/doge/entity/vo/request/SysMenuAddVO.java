package com.doge.entity.vo.request;

import com.doge.entity.vo.response.SysMenuVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 菜单添加VO
 *
 * @author shixinyu
 * @date 2021-07-05 09:53
 */
@Data
public class SysMenuAddVO{
    @ApiModelProperty(value = "菜单名称")
    private String menuName;
    @ApiModelProperty(value = "是否外链")
    private Boolean frame;
    @ApiModelProperty(value = "请求地址")
    private String path;
    @ApiModelProperty(value = "重定向")
    private String redirect;
    @ApiModelProperty(value = "父级ID")
    private Integer pid;
    @ApiModelProperty(value = "类型:1目录,2菜单,3按钮")
    private Short type;
    @ApiModelProperty(value = "菜单状态:显示,隐藏")
    private Boolean visible;
    @ApiModelProperty(value = "权限标识")
    private String permission;
    @ApiModelProperty(value = "缓存")
    private Boolean cache;
    @ApiModelProperty(value = "图标")
    private String icon;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "子项")
    private List<SysMenuVO> children;
}
