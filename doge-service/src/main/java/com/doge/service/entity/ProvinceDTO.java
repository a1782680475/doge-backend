package com.doge.service.entity;

import lombok.Data;

import java.util.Date;

/**
 * 行政区域省份信息
 *
 * @author shixinyu
 * @date 2021-09-30 09:50
 */
@Data
public class ProvinceDTO {
    private Integer id;
    private Integer provinceCode;
    private String provinceName;
    private Date createTime;
    private Date updateTime;
}
