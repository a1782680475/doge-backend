package com.doge.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 提醒表
 *
 * @author shixinyu
 * @date 2021-10-11 09:42
 */
@Data
@TableName(value = "notify_remind")
public class NotifyRemindDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "target_type")
    private String targetType;
    @TableField(value = "target")
    private Integer target;
    @TableField(value = "action")
    private String action;
    @TableField(value = "sender")
    private Integer sender;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}
