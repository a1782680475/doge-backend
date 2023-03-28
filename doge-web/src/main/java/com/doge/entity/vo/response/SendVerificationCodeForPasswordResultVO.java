package com.doge.entity.vo.response;

import com.doge.service.entity.SendVerificationCodeForPasswordResultDTO;
import com.doge.service.entity.SimpleTokenResultDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 找回密码验证码发送结果
 *
 * @author shixinyu
 * @date 2023/3/28 18:24
 */
@Data
public class SendVerificationCodeForPasswordResultVO extends SimpleTokenResultVO{
    @ApiModelProperty(value = "邮箱")
    private String email;
}
