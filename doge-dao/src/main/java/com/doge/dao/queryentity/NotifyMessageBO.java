package com.doge.dao.queryentity;

import lombok.Data;

import java.util.Date;

/**
 * 用户私信查询DO
 *
 * @author shixinyu
 * @date 2021/12/28 16:11
 */
@Data
public class NotifyMessageBO {
    private Integer id;
    private Integer senderId;
    private String senderAvatar;
    private String senderName;
    private String content;
    private Boolean read;
    private Date sendTime;
}
