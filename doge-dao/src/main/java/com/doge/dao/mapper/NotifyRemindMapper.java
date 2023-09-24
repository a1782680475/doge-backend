package com.doge.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doge.dao.entity.NotifyRemindDO;
import com.doge.dao.queryentity.NotifySubscriptionBO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 提醒相关Mapper
 *
 * @author shixinyu
 * @date 2021-10-11 10:08
 */
@Repository
public interface NotifyRemindMapper extends BaseMapper<NotifyRemindDO> {
    /**
     * 用户订阅提醒获取
     *
     * @param userId 用户id
     * @return java.util.List<com.doge.dao.queryentity.NotifySubscriptionQueryDO>
     */
    List<NotifySubscriptionBO> getListByUserId(Integer userId);

    /**
     * 指定用户未拉取订阅提醒获取
     *
     * @param userId 用户id
     * @return java.util.List<com.doge.dao.queryentity.NotifySubscriptionQueryDO>
     */
    List<NotifySubscriptionBO> getNotPullListByUserId(Integer userId);

}
