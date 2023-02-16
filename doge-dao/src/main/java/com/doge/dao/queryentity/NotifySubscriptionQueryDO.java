package com.doge.dao.queryentity;

import lombok.Data;

import java.util.Date;

/**
 * 提醒订阅查询DO
 *
 * @author shixinyu
 * @date 2021-10-13 15:31
 */
@Data
public class NotifySubscriptionQueryDO {
    private Integer id;
    private String targetType;
    private Integer target;
    private String action;
    private Integer userId;
    private Integer remindId;
    private String sender;
    private Boolean read;
    private Date sendTime;
}
