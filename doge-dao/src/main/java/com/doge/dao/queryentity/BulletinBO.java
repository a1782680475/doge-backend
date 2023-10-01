package com.doge.dao.queryentity;

import lombok.Data;

import java.util.Date;

/**
 * 公告查询BO
 *
 * @author shixinyu
 * @date 2023/9/27 20:26
 */
@Data
public class BulletinBO {
    private Integer id;
    private String title;
    private String content;
    private String createBy;
    private Date createTime;
}
