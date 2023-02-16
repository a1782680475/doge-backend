package com.doge.service.entity;

import lombok.Data;

import java.util.Date;

/**
 * 行政区域县区信息
 *
 * @author shixinyu
 * @date 2021-09-30 09:49
 */
@Data
public class AreaDTO {
    private Integer id;
    private Integer areaCode;
    private Integer cityCode;
    private String areaName;
    private Date createTime;
    private Date updateTime;
}
