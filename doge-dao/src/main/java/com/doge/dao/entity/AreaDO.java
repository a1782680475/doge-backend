package com.doge.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 行政区域县区信息表
 *
 * @author shixinyu
 * @date 2021-09-30 09:11
 */
@Data
@TableName(value = "area")
public class AreaDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "area_code")
    private Integer areaCode;
    @TableField(value = "city_code")
    private Integer cityCode;
    @TableField(value = "area_name")
    private String areaName;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}
