package com.doge.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 行政区域省份信息表
 *
 * @author shixinyu
 * @date 2021-09-30 09:06
 */
@Data
@TableName(value = "province")
public class ProvinceDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "province_code")
    private Integer provinceCode;
    @TableField(value = "province_name")
    private String provinceName;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}
