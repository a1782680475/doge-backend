package com.doge.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表
 *
 * @author shixinyu
 * @date 2021-06-09 08:36
 */
@NoArgsConstructor
@Data
@TableName(value = "sys_role")
public class SysRoleDO implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "role_code")
    private String roleCode;
    @TableField(value = "role_name")
    private String roleName;
    @TableField(value = "remark")
    private String remark;
    @TableField(value = "create_by")
    private String createBy;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}
