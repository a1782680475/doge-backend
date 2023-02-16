package com.doge.entity.vo.request;

import com.doge.entity.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 业务日志查询VO
 *
 * @author shixinyu
 * @date 2021-08-31 09:47
 */
@Data
public class SysBusinessLogQueryVO extends PageQuery {
    /**
     * 业务名称
     */
    @ApiModelProperty(value = "业务名称")
    private String title;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String operationUser;

    /**
     * 请求状态
     */
    @ApiModelProperty(value = "请求状态")
    private Boolean status;

    /**
     * 操作时间（开始）
     */
    @ApiModelProperty(value = "操作时间（开始）")
    @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss")
    private Date startTime;

    /**
     * 操作时间（结束）
     */
    @ApiModelProperty(value = "操作时间（结束）")
    @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm:ss")
    private Date endTime;
}
