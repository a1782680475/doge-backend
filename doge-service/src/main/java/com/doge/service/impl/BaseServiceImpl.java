package com.doge.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.doge.service.BaseService;
import com.doge.utils.BeanUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * 基本Service实现类
 *
 * @author shixinyu
 * @date 2021-06-09 08:36
 */
public class BaseServiceImpl<M extends BaseMapper<S>, S, T> implements BaseService<T> {
    @Autowired(required = false)
    protected M baseMapper;
    final int DEFAULT_BATCH_SIZE = 1000;
    private Class<T> serviceEntityCls;
    protected Class<S> daoEntityClass = currentModelClass();
    protected Class<T> mapperClass = currentMapperClass();

    private Class<T> getServiceEntityClass() {
        if (serviceEntityCls == null) {
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            try {
                Class<T> cls = (Class<T>) type.getActualTypeArguments()[2];
                serviceEntityCls = cls;
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
        return serviceEntityCls;
    }

    protected Log log = LogFactory.getLog(getClass());


    /**
     * 根据 ID 查询
     *
     * @param id 主键id
     * @return T DTO
     */
    @Override
    public T getById(Serializable id) {
        var sa = getBaseMapper().selectById(id);
        return BeanUtils.map(getBaseMapper().selectById(id), getServiceEntityClass());
    }

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表
     * @return java.util.List<T> DTO集合
     */
    @Override
    public List<T> listByIds(Collection<? extends Serializable> idList) {
        List<S> list = getBaseMapper().selectBatchIds(idList);
        return BeanUtils.mapAsList(list, getServiceEntityClass());
    }

    /**
     * 查询所有(慎用)
     *
     * @param
     * @return java.util.List<T> DTO集合
     */
    @Override
    public List<T> list() {
        return BeanUtils.mapAsList(list(Wrappers.emptyWrapper()), getServiceEntityClass());
    }

    protected List<S> list(Wrapper<S> queryWrapper) {
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 保存单条记录
     *
     * @param entity DTO对象
     * @return boolean
     */
    @Override
    public boolean save(T entity) {
        return SqlHelper.retBool(getBaseMapper().insert(BeanUtils.map(entity, daoEntityClass)));
    }

    /**
     * 保存或更新单条记录
     *
     * @param entity DTO对象
     * @return boolean
     */
    @Override
    public boolean saveOrUpdate(T entity) {
        return saveOrUpdateBatch(Arrays.asList(entity),1);
    }

    /**
     * 批量保存
     *
     * @param entityList DTO集合
     * @param batchSize  批量大小
     * @return boolean
     */
    @Override
    public boolean saveBatch(List<T> entityList, int batchSize) {
        String sqlStatement = getSqlStatement(SqlMethod.INSERT_ONE);
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
    }


    /**
     * 批量保存或更新
     *
     * @param entityList DTO集合
     * @return boolean
     */
    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList) {
        return saveOrUpdateBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    /**
     * 批量保存或更新
     *
     * @param entityList DTO集合
     * @param batchSize  批量大小
     * @return boolean
     */
    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(daoEntityClass);
        Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
        String keyProperty = tableInfo.getKeyProperty();
        Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
        return SqlHelper.saveOrUpdateBatch(this.daoEntityClass, this.mapperClass, this.log, entityList, batchSize, (sqlSession, entity) -> {
            Object idVal = ReflectionKit.getFieldValue(entity, keyProperty);
            return StringUtils.checkValNull(idVal)
                    || CollectionUtils.isEmpty(sqlSession.selectList(getSqlStatement(SqlMethod.SELECT_BY_ID), entity));
        }, (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, entity);
            sqlSession.update(getSqlStatement(SqlMethod.UPDATE_BY_ID), param);
        });
    }

    /**
     * 更新记录
     *
     * @param entity DTO对象
     * @return boolean
     */
    @Override
    public boolean update(T entity) {
        return SqlHelper.retBool(getBaseMapper().updateById(BeanUtils.map(entity, daoEntityClass)));
    }


    /**
     * 批量更新
     *
     * @param entityList DTO集合
     * @return boolean
     */
    @Override
    public boolean updateBatch(List<T> entityList) {
        return updateBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    /**
     * 批量更新
     *
     * @param entityList DTO集合
     * @param batchSize  批量大小
     * @return boolean
     */
    @Override
    public boolean updateBatch(List<T> entityList, int batchSize) {
        return updateBatch(entityList, batchSize);
    }


    /**
     * 根据ID删除一条记录
     *
     * @param id 主键
     * @return boolean
     */
    @Override
    public boolean removeById(Serializable id) {
        return SqlHelper.retBool(getBaseMapper().deleteById(id));
    }

    /**
     * 批量删除记录
     *
     * @param idList 主键集合
     * @return boolean
     */
    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return false;
        }
        return SqlHelper.retBool(getBaseMapper().deleteBatchIds(idList));
    }


    protected String getSqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.getSqlStatement(mapperClass, sqlMethod);
    }

    protected Class<T> currentMapperClass() {
        return (Class<T>) this.getResolvableType().as(BaseServiceImpl.class).getGeneric(0).getType();
    }

    protected Class<S> currentModelClass() {
        return (Class<S>) this.getResolvableType().as(BaseServiceImpl.class).getGeneric(1).getType();
    }


    protected ResolvableType getResolvableType() {
        return ResolvableType.forClass(ClassUtils.getUserClass(getClass()));
    }

    protected <E> boolean executeBatch(Collection<E> list, int batchSize, BiConsumer<SqlSession, E> consumer) {
        return SqlHelper.executeBatch(this.daoEntityClass, this.log, list, batchSize, consumer);
    }


    public M getBaseMapper() {
        return baseMapper;
    }
}
