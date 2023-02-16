package com.doge.service.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 未读消息查询信息
 *
 * @author shixinyu
 * @date 2023/2/12 13:52
 */
@ToString
@Getter
@Setter
public class UnreadNoticeQueryDTO {
    private Integer count;
}
