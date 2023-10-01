package com.doge.entity.vo.response;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 公告VO
 *
 * @author shixinyu
 * @date 2023/9/27 21:03
 */
@Data
@ApiModel
public class BulletinVO {
    @ApiModelProperty(value="id",position = 1)
    private Integer id;
    @ApiModelProperty(value="消息标题",position = 2)
    private String title;
    @ApiModelProperty(value="消息内容",position = 3)
    private String content;
    @ApiModelProperty(value="创建者",position = 4)
    private String createBy;
    @ApiModelProperty(value="创建时间",position = 5)
    private Date createTime;
}
