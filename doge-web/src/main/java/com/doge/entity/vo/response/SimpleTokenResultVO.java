package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 简单Token结果类
 *
 * @author shixinyu
 * @date 2023/3/28 17:17
 */
@Data
public class SimpleTokenResultVO extends SimpleResultVO {
    @ApiModelProperty(value = "Token")
    public String token;
}
