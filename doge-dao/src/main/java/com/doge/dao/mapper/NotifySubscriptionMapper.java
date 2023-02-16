package com.doge.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doge.dao.entity.NotifySubscriptionDO;
import com.doge.dao.queryentity.NotifySubscriptionBO;
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
     * 用户订阅信息获取
     *
     * @param userId 用户id
     * @return java.util.List<com.doge.dao.queryentity.NotifySubscriptionQueryDO>
     */
    List<NotifySubscriptionBO> getListByUserId(Integer userId);
}
