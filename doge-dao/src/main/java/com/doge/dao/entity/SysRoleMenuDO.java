package com.doge.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 角色菜单关系表
 *
 * @author shixinyu
 * @date 2021-07-29 14:39
 */
@Data
@NoArgsConstructor
@TableName(value = "sys_role_menu")
public class SysRoleMenuDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "role_id")
    private Integer roleId;
    @TableField(value = "menu_id")
    private Integer menuId;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}
