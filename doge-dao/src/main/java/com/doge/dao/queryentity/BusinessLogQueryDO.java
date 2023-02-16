package com.doge.dao.queryentity;

import lombok.Data;

import java.util.Date;

/**
 * 业务日志查询DO
 *
 * @author shixinyu
 * @date 2021-08-31 09:47
 */
@Data
public class BusinessLogQueryDO {
    private String title;
    private String operationUser;
    private Boolean status;
    private Date startTime;
    private Date endTime;
}
