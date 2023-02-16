package com.doge.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页查询基本类
 *
 * @author shixinyu
 * @date 2021-06-21 11:38
 */
@Data
@ApiModel
public class PageQuery {
    /**
     * 当前页
     */
    @ApiModelProperty(value="当前页")
    private Integer current = 0;
    /**
     * 每页大小
     */
    @ApiModelProperty(value="每页大小")
    private Integer pageSize = 0;
    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private String sorter;
}