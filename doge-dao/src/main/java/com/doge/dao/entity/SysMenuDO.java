package com.doge.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 前台菜单表
 *
 * @author shixinyu
 * @date 2021-07-01 16:25
 */
@Data
@TableName(value = "sys_menu")
public class SysMenuDO implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "menu_name")
    private String menuName;
    @TableField(value = "is_frame",updateStrategy= FieldStrategy.IGNORED)
    private Boolean frame;
    @TableField(value = "path",updateStrategy= FieldStrategy.IGNORED)
    private String path;
    @TableField(value = "redirect",updateStrategy= FieldStrategy.IGNORED)
    private String redirect;
    @TableField(value = "pid",updateStrategy= FieldStrategy.IGNORED)
    private Integer pid;
    @TableField(value = "type",updateStrategy= FieldStrategy.IGNORED)
    private Short type;
    @TableField(value = "is_visible",updateStrategy= FieldStrategy.IGNORED)
    private Boolean visible;
    @TableField(value = "permission",updateStrategy= FieldStrategy.IGNORED)
    private String permission;
    @TableField(value = "is_cache",updateStrategy= FieldStrategy.IGNORED)
    private Boolean cache;
    @TableField(value = "icon",updateStrategy= FieldStrategy.IGNORED)
    private String icon;
    @TableField(value = "sort",updateStrategy= FieldStrategy.IGNORED)
    private Integer sort;
    @TableField(value = "create_by",updateStrategy= FieldStrategy.IGNORED)
    private String createBy;
    @TableField(value = "remark",updateStrategy= FieldStrategy.IGNORED)
    private String remark;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}
