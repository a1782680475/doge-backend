package com.doge.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 业务日志表
 *
 * @author shixinyu
 * @date 2021-08-30 11:46
 */
@Data
@TableName(value = "business_log")
public class BusinessLogDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "title")
    private String title;
    @TableField(value = "request_url")
    private String requestUrl;
    @TableField(value = "request_method")
    private String requestMethod;
    @TableField(value = "request_params")
    private String requestParams;
    @TableField(value = "status")
    private Boolean status;
    @TableField(value = "request_time")
    private Date requestTime;
    @TableField(value = "response")
    private String response;
    @TableField(value = "response_time")
    private Date responseTime;
    @TableField(value = "target_class_name")
    private String targetClassName;
    @TableField(value = "target_method_name")
    private String targetMethodName;
    @TableField(value = "exception")
    private String exception;
    @TableField(value = "operation_user")
    private String operationUser;
    @TableField(value = "operation_ip")
    private String operationIp;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}
