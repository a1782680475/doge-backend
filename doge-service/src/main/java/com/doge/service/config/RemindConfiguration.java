package com.doge.service.config;

import com.doge.service.bean.Notify;
import com.doge.service.bean.RemindProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 提醒配置配置类
 *
 * @author shixinyu
 * @date 2021-10-12 10:55
 */
@Configuration
@PropertySource(value = {"classpath:/properties/remindProperties.json"}, factory = JsonLoader.class)
public class RemindConfiguration {
    @Bean
    public RemindProperties remind(Environment e) {
        LinkedHashMap map = e.getProperty("remind", LinkedHashMap.class);
        RemindProperties remindProperties = new RemindProperties();
        remindProperties.setTargetType((Map<String, String>) map.get("targetType"));
        remindProperties.setAction((Map<String, String>) map.get("action"));
        remindProperties.setReasonAction((Map<String, String[]>) map.get("reasonAction"));
        Map<String, Map<String, String>> templateMap = (Map<String, Map<String, String>>) map.get("template");
        Map<String, Notify> template = new LinkedHashMap<>();
        for (String key : templateMap.keySet()) {
            Map<String, String> notifyMap = templateMap.get(key);
            template.put(key,new Notify(notifyMap.get("title"),notifyMap.get("content")));
        }
        remindProperties.setTemplate(template);
        return remindProperties;
    }
}
