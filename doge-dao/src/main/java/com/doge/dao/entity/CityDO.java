package com.doge.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 行政区域地州市信息表
 *
 * @author shixinyu
 * @date 2021-09-30 09:09
 */
@Data
@TableName(value = "city")
public class CityDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "city_code")
    private Integer cityCode;
    @TableField(value = "province_code")
    private Integer provinceCode;
    @TableField(value = "city_name")
    private String cityName;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}
