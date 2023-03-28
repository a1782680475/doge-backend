package com.doge.entity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 找回密码邮箱验证信息
 *
 * @author shixinyu
 * @date 2023/3/28 17:36
 */
@Data
@ApiModel
public class FindPasswordVerifyCodeVO {
    /**
     * token
     */
    @ApiModelProperty(value="token",position = 1)
    private String token;
    /**
     * 验证码
     */
    @ApiModelProperty(value="验证码",position = 2)
    private String code;
}
