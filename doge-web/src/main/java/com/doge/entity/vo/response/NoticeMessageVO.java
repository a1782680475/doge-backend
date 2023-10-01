package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 消息-私信VO
 *
 * @author shixinyu
 * @date 2021/12/9 20:34
 */
@Data
@ApiModel
public class NoticeMessageVO {
    @ApiModelProperty(value="id",position = 1)
    private Integer id;
    @ApiModelProperty(value="发送者id",position = 2)
    private String senderId;
    @ApiModelProperty(value="发送者头像",position = 3)
    private String senderAvatar;
    @ApiModelProperty(value="发送者昵称",position = 4)
    private String senderName;
    @ApiModelProperty(value="消息内容",position = 5)
    private String content;
    @ApiModelProperty(value="是否已读",position = 6)
    private Boolean read;
    @ApiModelProperty(value="发送时间",position = 7)
    private Date sendTime;
}
