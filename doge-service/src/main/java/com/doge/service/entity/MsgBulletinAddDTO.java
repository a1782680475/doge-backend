package com.doge.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公告添加信息
 *
 * @author shixinyu
 * @date 2023/10/1 15:12
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MsgBulletinAddDTO {
    private String title;
    private String content;
    private Integer sender;
}

