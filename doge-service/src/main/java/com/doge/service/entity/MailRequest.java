package com.doge.service.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 邮件发件类
 *
 * @author shixinyu
 * @date 2023/3/6 16:46
 */
@Data
public class MailRequest implements Serializable {
    /**
     * 接收人
     */
    private String sendTo;

    /**
     *  邮件主题
     */
    private String subject;

    /**
     *  邮件内容
     */
    private String text;

    /**
     *  附件路径
     */
    private String filePath;
}