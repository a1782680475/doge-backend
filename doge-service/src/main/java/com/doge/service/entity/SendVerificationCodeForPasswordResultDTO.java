package com.doge.service.entity;

import lombok.Data;

/**
 * 找回密码验证码发送结果
 *
 * @author shixinyu
 * @date 2023/3/28 18:24
 */
@Data
public class SendVerificationCodeForPasswordResultDTO extends SimpleTokenResultDTO {
    private String email ;
}
