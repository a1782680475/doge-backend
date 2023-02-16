package com.doge.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doge.dao.entity.NotifySubscriptionConfigDO;
import org.springframework.stereotype.Repository;

/**
 * 提醒配置Mapper
 *
 * @author shixinyu
 * @date 2021-10-11 10:11
 */
@Repository
public interface NotifySubscriptionConfigMapper extends BaseMapper<NotifySubscriptionConfigDO> {
    /**
     * 指定用户配置信息获取
     *
     * @param userId 用户id
     * @return com.doge.dao.entity.NotifySubscriptionConfigDO
     */
    NotifySubscriptionConfigDO getListByUserId(Integer userId);
}
