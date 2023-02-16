package com.doge.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 订阅表
 *
 * @author shixinyu
 * @date 2021-10-11 09:44
 */
@Data
@TableName(value = "notify_subscription")
public class NotifySubscriptionDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "target_type")
    private String targetType;
    @TableField(value = "target")
    private Integer target;
    @TableField(value = "action")
    private String action;
    @TableField(value = "user_id")
    private Integer userId;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}
