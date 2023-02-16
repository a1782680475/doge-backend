package com.doge.entity;
import lombok.Data;

import java.util.Date;

/**
 * 业务日志
 *
 * @author shixinyu
 * @date 2021-08-30 16:32
 */
@Data
public class BusinessLog {
    private Integer id;
    private String title;
    private String requestUrl;
    private String requestMethod;
    private String requestParams;
    private Boolean status;
    private Date requestTime;
    private String response;
    private Date responseTime;
    private String targetClassName;
    private String targetMethodName;
    private String exception;
    private String operationUser;
    private String operationIp;
    private Date createTime;
    private Date updateTime;
}
