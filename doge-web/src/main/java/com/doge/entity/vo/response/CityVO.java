package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 行政区域地州市信息
 *
 * @author shixinyu
 * @date 2021-09-30 10:16
 */
@Data
public class CityVO {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "区划编码")
    private Integer cityCode;
    @ApiModelProperty(value = "区域名称")
    private String cityName;
}
