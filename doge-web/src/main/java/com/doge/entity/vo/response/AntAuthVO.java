package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Ant认证授权
 *
 * @author shixinyu
 * @date 2021-07-30 13:58
 */
@Data
@ApiModel
public class AntAuthVO {
    @ApiModelProperty(value="用户id")
    private Integer userId;
    @ApiModelProperty(value="用户名")
    private String username;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "头像")
    private String avatar;
    @ApiModelProperty(value="所属角色")
    private List<String> roles;
    @ApiModelProperty(value="未读消息数")
    private Integer unreadCount;
    @ApiModelProperty(value="目录")
    private List<AntMenuVO> menus;
    @ApiModelProperty(value="授权")
    private List<String> authorities;
}
