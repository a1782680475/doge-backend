package com.doge.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 公告表
 *
 * @author shixinyu
 * @date 2021/12/29 15:56
 */
@Data
@TableName(value = "notify_bulletin")
public class NotifyBulletinDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "title")
    private String title;
    @TableField(value = "content")
    private String content;
    @TableField(value = "sender")
    private Integer sender;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}
