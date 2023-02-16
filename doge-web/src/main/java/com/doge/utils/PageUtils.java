package com.doge.utils;

import com.alibaba.fastjson.JSON;
import com.doge.entity.PageQuery;
import com.doge.entity.SortEnum;
import com.doge.service.entity.PageDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Service层分页与筛选所需DTO对象生成工具类
 *
 * @author shixinyu
 * @date 2021-06-29 09:44
 */
public class PageUtils {

    /**
     * 分页DTO生成
     *
     * @param pageQuery
     * @return com.doge.service.entity.PageDTO
     */
    public static PageDTO createPageDTO(PageQuery pageQuery) {
        return new PageDTO(pageQuery.getCurrent(), pageQuery.getPageSize());
    }

    /**
     * 筛选Map生成
     *
     * @param pageQuery
     * @return java.util.HashMap<java.lang.String, java.lang.String>
     */
    public static Map<String, String> createSorter(PageQuery pageQuery) {
        if (pageQuery.getSorter() == null) {
            return null;
        }
        HashMap<String, String> map = JSON.parseObject(pageQuery.getSorter(), HashMap.class);
        HashMap<String, String> sorter = new HashMap<>(map.size());
        for (String key : map.keySet()) {
            sorter.put(key, SortEnum.valueOf(map.get(key).toUpperCase()).getSortName());
        }
        return sorter;
    }
}