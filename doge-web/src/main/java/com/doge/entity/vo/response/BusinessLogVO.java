package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * BusinessLogVO
 *
 * @author shixinyu
 * @date 2021-09-01 16:21
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class BusinessLogVO {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "业务名称")
    private String title;
    @ApiModelProperty(value = "请求URL")
    private String requestUrl;
    @ApiModelProperty(value = "请求方法")
    private String requestMethod;
    @ApiModelProperty(value = "请求参数")
    private String requestParams;
    @ApiModelProperty(value = "请求状态")
    private Boolean status;
    @ApiModelProperty(value = "请求时间")
    private Date requestTime;
    @ApiModelProperty(value = "返回结果")
    private String response;
    @ApiModelProperty(value = "响应时间")
    private Date responseTime;
    @ApiModelProperty(value = "请求目标处理类")
    private String targetClassName;
    @ApiModelProperty(value = "请求目标处理方法")
    private String targetMethodName;
    @ApiModelProperty(value = "异常信息")
    private String exception;
    @ApiModelProperty(value = "操作人")
    private String operationUser;
    @ApiModelProperty(value = "操作ip")
    private String operationIp;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
