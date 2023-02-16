package com.doge.service.entity;

import com.doge.dao.queryentity.NotifySubscriptionBO;
import lombok.Data;

/**
 * 用户提醒消息
 *
 * @author shixinyu
 * @date 2021-10-14 10:00
 */
@Data
public class NotifySubscriptionDTO extends NotifySubscriptionBO {
    private String title;
    private String content;
}
