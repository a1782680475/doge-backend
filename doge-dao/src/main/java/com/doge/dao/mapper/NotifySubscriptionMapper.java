package com.doge.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doge.dao.entity.NotifySubscriptionDO;
import com.doge.dao.queryentity.NotifySubscriptionBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订阅Mapper
 *
 * @author shixinyu
 * @date 2021-10-11 10:16
 */
@Repository
public interface NotifySubscriptionMapper extends BaseMapper<NotifySubscriptionDO> {
    /**
     * 指定用户指定动作订阅信息获取(首条)
     *
     * @param userId     用户id
     * @param targetType 目标类型
     * @param target     目标id
     * @param action     动作
     * @return com.doge.dao.entity.NotifySubscriptionDO
     */
    NotifySubscriptionDO getListByUserIdAndAction(@Param("userId") Integer userId, @Param("targetType") String targetType, @Param("target") Integer target, @Param("action") String action);

    /**
     * 指定动作订阅信息获取
     *
     * @param targetType 目标类型
     * @param target     目标id
     * @param action     动作
     * @return com.doge.dao.entity.NotifySubscriptionDO
     */
    List<NotifySubscriptionDO> getListByAction(@Param("targetType") String targetType, @Param("target") Integer target, @Param("action") String action);

    /**
     * 指定用户指定动作订阅删除
     *
     * @param userId 用户id
     * @param action 动作
     * @return java.lang.Boolean
     */
    Boolean deleteByUserIdAndAction(@Param("userId") Integer userId, @Param("action") String action);
}
