package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * SysMenuVO
 *
 * @author shixinyu
 * @date 2021-06-29 14:49
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class SysMenuVO {
    @ApiModelProperty(value = "菜单ID")
    private Integer id;
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
    @ApiModelProperty(value = "创建人")
    private String createBy;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "当前子级最大排序（新增子级时排序字段自动填充用）")
    private Integer childMaxSort;
    @ApiModelProperty(value = "子项")
    private List<SysMenuVO> children;
}
