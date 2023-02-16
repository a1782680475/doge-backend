package com.doge.service.entity;

import lombok.Data;

import java.util.Date;

/**
 * 行政区域地州市信息
 *
 * @author shixinyu
 * @date 2021-09-30 09:49
 */
@Data
public class CityDTO {
    private Integer id;
    private Integer cityCode;
    private Integer provinceCode;
    private String cityName;
    private Date createTime;
    private Date updateTime;
}
