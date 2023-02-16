package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 行政区域县区信息
 *
 * @author shixinyu
 * @date 2021-09-30 10:15
 */
@Data
@ApiModel
public class AreaVO{
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "区划编码")
    private Integer areaCode;
    @ApiModelProperty(value = "区域名称")
    private String areaName;
}
