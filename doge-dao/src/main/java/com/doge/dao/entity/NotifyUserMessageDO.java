package com.doge.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户私信表
 *
 * @author shixinyu
 * @date 2021/12/9 20:46
 */
@Data
@TableName(value = "notify_user_message")
public class NotifyUserMessageDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "user_id")
    private Integer userId;
    @TableField(value = "content")
    private String content;
    @TableField(value = "sender")
    private Integer sender;
    @TableField(value = "is_read")
    private Boolean read;
    @TableField(value = "read_time")
    private Date readTime;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}
