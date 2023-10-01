package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 公告VO
 *
 * @author shixinyu
 * @date 2021/12/29 16:01
 */
@Data
@ApiModel
public class NoticeBulletinVO {
    @ApiModelProperty(value="id",position = 1)
    private Integer id;
    @ApiModelProperty(value="消息标题",position = 2)
    private String title;
    @ApiModelProperty(value="消息内容",position = 3)
    private String content;
    @ApiModelProperty(value="是否已读",position = 4)
    private Boolean read;
    @ApiModelProperty(value="发送时间",position = 5)
    private Date sendTime;
}
