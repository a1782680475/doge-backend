package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * AntTreeSelectVO
 *
 * @author shixinyu
 * @date 2021-07-05 14:39
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class AntTreeSelectVO {
    @ApiModelProperty(value = "value")
    private Integer value;
    @ApiModelProperty(value = "标题名称")
    private String title;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "当前子级最大排序（新增子级时排序字段自动填充用）")
    private Integer childMaxSort;
    @ApiModelProperty(value = "子项")
    private List<AntTreeSelectVO> children;
}
