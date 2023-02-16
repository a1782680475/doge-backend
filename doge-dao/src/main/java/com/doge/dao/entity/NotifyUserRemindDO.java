package com.doge.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户提醒表
 *
 * @author shixinyu
 * @date 2021-10-11 09:53
 */
@Data
@TableName(value = "notify_user_remind")
public class NotifyUserRemindDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "user_id")
    private Integer userId;
    @TableField(value = "remind_id")
    private Integer remindId;
    @TableField(value = "is_read")
    private Boolean read;
    @TableField(value = "read_time")
    private Date readTime;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}
