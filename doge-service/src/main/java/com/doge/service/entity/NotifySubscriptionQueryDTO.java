package com.doge.service.entity;

import com.doge.dao.queryentity.NotifySubscriptionQueryDO;
import lombok.Data;

/**
 * 用户提醒消息
 *
 * @author shixinyu
 * @date 2021-10-14 10:00
 */
@Data
public class NotifySubscriptionQueryDTO extends NotifySubscriptionQueryDO {
    private String title;
    private String content;
}
