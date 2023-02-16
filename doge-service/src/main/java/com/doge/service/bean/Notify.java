package com.doge.service.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 消息模板类
 *
 * @author shixinyu
 * @date 2021-10-15 17:52
 */
@Data
@AllArgsConstructor
public class Notify {
    private String title;
    private String content;
}
