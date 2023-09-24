package com.doge.service.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 提醒属性
 *
 * @author shixinyu
 * @date 2021-10-11 13:52
 */
@Data
public class RemindProperties {
    private Map<String, String> targetType;
    private Map<String, String> action;
    private Map<String, List<String>> reason;
    private Map<String, Notify> template;
}

