package com.doge.entity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 图片验证码信息类
 *
 * @author shixinyu
 * @date 2021-06-18 18:08
 */
@Data
@ApiModel
public class ImgCaptchaVO {
    /**
     * 用户标识
     */
    @ApiModelProperty(value="验证码用户标识",position = 1)
    private String captchaKey;
    /**
     * 验证码
     */
    @ApiModelProperty(value="验证码",position = 2)
    private String captchaCode;
}
