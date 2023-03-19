package com.doge.entity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户绑定邮箱信息
 *
 * @author shixinyu
 * @date 2023/3/14 21:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class SysUserBindEmailInfoVO {
    @ApiModelProperty(value="绑定邮箱")
    private String email;
    @ApiModelProperty(value="验证token")
    private String token;
}
