package com.doge.entity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户密码信息
 *
 * @author shixinyu
 * @date 2023/3/19 12:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class SysUserPasswordInfo {
    @ApiModelProperty(value="用户id",position = 1)
    private Integer id;
    @ApiModelProperty(value="密码（RSA加密）",position = 2)
    private String password;
}
