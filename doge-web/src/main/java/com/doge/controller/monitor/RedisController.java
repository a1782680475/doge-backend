package com.doge.controller.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

/**
 * Redis监控业务
 *
 * @author shixinyu
 * @date 2021-09-27 17:06
 */
@RestController
@RequestMapping("/monitor")
public class RedisController {
    private RedisTemplate redisTemplate;
    @Autowired
    public RedisController(RedisTemplate redisTemplate){
       this.redisTemplate = redisTemplate;
    }
    @PreAuthorize("@aps.hasPermission('monitor:redis')")
    @GetMapping("/redis")
    public Properties get() {
        Properties info = redisTemplate.getRequiredConnectionFactory().getConnection().info();
        return info;
    }
}
