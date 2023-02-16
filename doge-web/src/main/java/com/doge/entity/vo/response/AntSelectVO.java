package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ant选择器
 *
 * @author shixinyu
 * @date 2021-07-26 10:43
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AntSelectVO {
    @ApiModelProperty(value="value",position = 1)
    private Integer value;
    @ApiModelProperty(value="title",position = 2)
    private String title;
}
