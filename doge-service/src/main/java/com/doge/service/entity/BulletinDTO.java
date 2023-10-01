package com.doge.service.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * 公告
 *
 * @author shixinyu
 * @date 2023/9/27 20:11
 */
@Data
public class BulletinDTO {
    private Integer id;
    private String title;
    private String content;
    private String createBy;
    private Date createTime;
}
