package com.doge.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 基本Service
 *
 * @author shixinyu
 * @date 2021-06-09 10:45
 */
public interface BaseService<T> {
    /**
     * 根据 ID 查询
     *
     * @param id 主键id
     * @return T DTO
     */
    T getById(Serializable id);

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表
     * @return java.util.List<T> DTO集合
     */
    List<T> listByIds(Collection<? extends Serializable> idList);

    /**
     * 查询所有(慎用)
     *
     * @param
     * @return java.util.List<T> DTO集合
     */
    List<T> list();

    /**
     * 保存单条记录
     *
     * @param entity DTO对象
     * @return boolean
     */
    boolean save(T entity);

    /**
     * 保存或更新单条记录
     *
     * @param entity DTO对象
     * @return boolean
     */
    boolean saveOrUpdate(T entity);

    /**
     * 批量保存
     *
     * @param entityList DTO集合
     * @param batchSize  批量大小
     * @return boolean
     */
    boolean saveBatch(List<T> entityList, int batchSize);

    /**
     * 批量保存或更新
     *
     * @param entityList DTO集合
     * @return boolean
     */
    boolean saveOrUpdateBatch(Collection<T> entityList);

    /**
     * 批量保存或更新
     *
     * @param entityList DTO集合
     * @param batchSize  批量大小
     * @return boolean
     */
    boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);

    /**
     * 更新记录
     *
     * @param entity DTO对象
     * @return boolean
     */
    boolean update(T entity);

    /**
     * 批量更新
     *
     * @param entityList DTO集合
     * @return boolean
     */
    boolean updateBatch(List<T> entityList);

    /**
     * 批量更新
     *
     * @param entityList DTO集合
     * @param batchSize  批量大小
     * @return boolean
     */
    boolean updateBatch(List<T> entityList, int batchSize);

    /**
     * 根据ID删除一条记录
     *
     * @param id 主键
     * @return boolean
     */
    boolean removeById(Serializable id);

    /**
     * 批量删除记录
     *
     * @param idList 主键集合
     * @return boolean
     */
    boolean removeByIds(Collection<? extends Serializable> idList);
}
