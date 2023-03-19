package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 简单结果类
 *
 * @author shixinyu
 * @date 2023/3/6 18:56
 */
@Data
public class SimpleResultVO {
    @ApiModelProperty(value = "业务是否成功")
    public Boolean isSuccess = true;
    @ApiModelProperty(value = "错误信息")
    public String errorMessage;
}
