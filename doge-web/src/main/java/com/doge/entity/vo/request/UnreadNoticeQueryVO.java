package com.doge.entity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 未读消息查询信息
 *
 * @author shixinyu
 * @date 2023/2/12 13:45
 */
@Data
@ApiModel
public class UnreadNoticeQueryVO {
    /**
     * 查询最大条目数
     */
    @ApiModelProperty(value = "最大条目数", position = 1)
    private Integer count;
}