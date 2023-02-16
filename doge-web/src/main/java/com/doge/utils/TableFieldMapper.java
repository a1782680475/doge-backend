package com.doge.utils;

import java.util.HashMap;

/**
 * 数据库字段与类名映射类
 *
 * @author shixinyu
 * @date 2021-06-29 14:01
 */
public class TableFieldMapper {
    static HashMap<String,String> map = new HashMap<>();
    static
    {
        map.put("createTime","create_time");
    }
    static String get(String key){
        return map.getOrDefault(key,null);
    }
}
