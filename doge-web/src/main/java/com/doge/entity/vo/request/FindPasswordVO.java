package com.doge.entity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 找回密码重置密码信息
 *
 * @author shixinyu
 * @date 2023/3/28 19:37
 */
@Data
@ApiModel
public class FindPasswordVO {
    /**
     * token
     */
    @ApiModelProperty(value="token",position = 1)
    private String token;
    /**
     * 验证码
     */
    @ApiModelProperty(value="密码",position = 2)
    private String password;
}
