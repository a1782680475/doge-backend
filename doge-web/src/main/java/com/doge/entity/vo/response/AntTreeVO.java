package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * AntTreeVO
 *
 * @author shixinyu
 * @date 2021-07-27 17:14
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class AntTreeVO {
    @ApiModelProperty(value = "key")
    private Integer key;
    @ApiModelProperty(value = "标题名称")
    private String title;
    @ApiModelProperty(value = "子项")
    private List<AntTreeVO> children;
}
