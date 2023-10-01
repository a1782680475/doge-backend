package com.doge.entity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公告添加VO
 *
 * @author shixinyu
 * @date 2023/10/1 15:12
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class MsgBulletinAddVO {
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "内容")
    private String content;
}

