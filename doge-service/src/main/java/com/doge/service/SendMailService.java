package com.doge.service;

import com.doge.service.entity.MailRequest;

/**
 * 邮件发送服务
 *
 * @author shixinyu
 * @date 2023/3/6 16:47
 */
public interface SendMailService {

    /**
     * 简单文本邮件
     *
     * @param mailRequest
     * @return
     */
    void sendSimpleMail(MailRequest mailRequest);


    /**
     * Html格式邮件,可带附件
     *
     * @param mailRequest
     * @return
     */
    void sendHtmlMail(MailRequest mailRequest);
}