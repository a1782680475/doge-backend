package com.doge.service.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业务处理结果消息
 *
 * @author shixinyu
 * @date 2021-06-09 10:28
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageResult<T> {
    /**
     * 业务是否成功
     */
    private Boolean success = true;
    /**
     * 附加消息
     */
    private T message;
}
