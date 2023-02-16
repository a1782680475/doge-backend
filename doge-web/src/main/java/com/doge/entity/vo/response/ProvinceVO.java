package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 行政区域省份信息
 *
 * @author shixinyu
 * @date 2021-09-30 10:15
 */
@Data
public class ProvinceVO {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "区划编码")
    private Integer provinceCode;
    @ApiModelProperty(value = "区域名称")
    private String provinceName;
}
