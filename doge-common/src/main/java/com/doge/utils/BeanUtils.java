package com.doge.utils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象转换工具类
 *
 * @author shixinyu
 * @date 2021-06-09 08:36
 */
public class BeanUtils {

    /**
     * 默认字段工厂
     */
    private static final MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();

    /**
     * 默认字段实例
     */
    private static final MapperFacade MAPPER_FACADE = MAPPER_FACTORY.getMapperFacade();

    /**
     * 默认字段实例集合
     */
    private static Map<String, MapperFacade> CACHE_MAPPER_FACADE_MAP = new ConcurrentHashMap<>();

    /**
     * 映射实体（默认字段）
     *
     * @param source      源对象
     * @param targetClass 目标对象Class
     * @return 映射类对象
     */
    public static <E, T> E map(T source, Class<E> targetClass) {
        return MAPPER_FACADE.map(source, targetClass);
    }

    /**
     * 映射实体（自定义配置）
     *
     * @param source      源对象
     * @param targetClass 目标对象Class
     * @param configMap   自定义配置
     * @return 映射类对象
     */
    public static <E, T> E map(T source, Class<E> targetClass, Map<String, String> configMap) {
        MapperFacade mapperFacade = getMapperFacade(targetClass, source.getClass(), configMap);
        return mapperFacade.map(source, targetClass);
    }

    /**
     * 映射集合（默认字段）
     *
     * @param source      源集合
     * @param targetClass 目标集合Class
     * @return 映射类对象
     */
    public static <E, T> List<E> mapAsList(Collection<T> source,Class<E> targetClass) {
        return MAPPER_FACADE.mapAsList(source, targetClass);
    }

    /**
     * 获取自定义映射
     *
     * @param toClass   映射类
     * @param dataClass 数据映射类
     * @param configMap 自定义配置
     * @return 映射类对象
     */
    private static <E, T> MapperFacade getMapperFacade(Class<E> toClass, Class<T> dataClass, Map<String, String> configMap) {
        String mapKey = dataClass.getCanonicalName() + "_" + toClass.getCanonicalName();
        MapperFacade mapperFacade = CACHE_MAPPER_FACADE_MAP.get(mapKey);
        if (Objects.isNull(mapperFacade)) {
            MapperFactory factory = new DefaultMapperFactory.Builder().build();
            ClassMapBuilder classMapBuilder = factory.classMap(dataClass, toClass);
            configMap.forEach(classMapBuilder::field);
            classMapBuilder.byDefault().register();
            mapperFacade = factory.getMapperFacade();
            CACHE_MAPPER_FACADE_MAP.put(mapKey, mapperFacade);
        }
        return mapperFacade;
    }
}
