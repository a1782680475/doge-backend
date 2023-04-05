package com.doge.entity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 用户添加VO
 *
 * @author shixinyu
 * @date 2023/4/5 18:51
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class SysUserAddVO {
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
}
