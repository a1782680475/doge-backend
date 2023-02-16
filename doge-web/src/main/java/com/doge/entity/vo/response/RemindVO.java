package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 提醒VO
 *
 * @author shixinyu
 * @date 2021-10-14 11:43
 */
@Data
@ApiModel
public class RemindVO {
    @ApiModelProperty(value="id",position = 1)
    private Integer id;
    @ApiModelProperty(value="目标类型",position = 2)
    private String targetType;
    @ApiModelProperty(value="提醒标题",position = 3)
    private String title;
    @ApiModelProperty(value="提醒内容",position = 4)
    private String content;
    @ApiModelProperty(value="是否已读",position = 5)
    private Boolean read;
    @ApiModelProperty(value="发送时间",position = 6)
    private Date sendTime;
}
