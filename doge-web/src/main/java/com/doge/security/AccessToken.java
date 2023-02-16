package com.doge.security;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 鉴权令牌
 *
 * @author shixinyu
 * @date 2021-07-02 09:45
 */
@AllArgsConstructor
@Data
public class AccessToken{
    /**
     * 令牌
     */
    private String token;
    /**
     * 过期时间戳（毫秒）
     */
    private long expiration;
}
