package com.doge.dao.queryentity;

import lombok.Data;

import java.util.Date;

/**
 * 用户公告查询BO
 *
 * @author shixinyu
 * @date 2021/12/29 16:24
 */
@Data
public class NotifyBulletinBO {
    private Integer id;
    private String title;
    private String content;
    private Boolean read;
    private Date sendTime;
}
