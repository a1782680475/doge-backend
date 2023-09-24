package com.doge.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 订阅规则配置类
 *
 * @author shixinyu
 * @date 2021-10-12 10:55
 */
@Configuration
@PropertySource(value = {"classpath:/properties/subscriptionConfig.json"}, factory = JsonLoader.class)
public class SubscriptionConfiguration {
    @Bean(name="subscriptionSettingMapping")
    public Map<String, List<String>> settingMapping(Environment e){
        LinkedHashMap map = e.getProperty("subscriptionSettingMapping", LinkedHashMap.class);
        return map;
    }
    @Bean(name = "subscriptionDefaultSetting")
    public Map<String,Boolean> defaultSubscription(Environment e){
        LinkedHashMap map = e.getProperty("defaultSubscription", LinkedHashMap.class);
        return map;
    }
}
