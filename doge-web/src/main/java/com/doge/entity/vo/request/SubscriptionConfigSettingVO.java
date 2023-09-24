package com.doge.entity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订阅配置设置VO
 *
 * @author shixinyu
 * @date 2023/9/21 21:29
 */
@Data
@ApiModel
public class SubscriptionConfigSettingVO {
    /**
     * key
     */
    @ApiModelProperty(value = "key")
    private String key;

    /**
     * 是否启用
     */
    @ApiModelProperty(value = "是否启用")
    private Boolean isEnabled;
}
