package com.doge.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doge.dao.entity.NotifyUserRemindDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户提醒Mapper
 *
 * @author shixinyu
 * @date 2021-10-11 10:14
 */
@Repository
public interface NotifyUserRemindMapper extends BaseMapper<NotifyUserRemindDO> {
    /**
     * 分页获取用户提醒
     *
     * @param page   分页
     * @param userId 用户id
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.doge.dao.entity.NotifyUserRemindDO>
     */
    IPage<NotifyUserRemindDO> selectListByPage(Page<NotifyUserRemindDO> page, @Param("userId")Integer userId);

    /**
     * 获取用户未读提醒
     *
     * @param count 查询最大条目数
     * @param userId 用户id
     * @return java.util.List<com.doge.service.entity.NotifySubscriptionDTO>
     */
    List<NotifyUserRemindDO> selectUnreadListByCount(@Param("count")Integer count, @Param("userId")Integer userId);

    /**
     * 用户用户未读提醒数获取
     *
     * @param userId 用户id
     * @return java.lang.Integer
     */
    Integer getUnreadCount(Integer userId);

    /**
     * 将未读提醒标记为已读
     *
     * @param id 提醒id
     * @param userId 用户id
     * @return java.lang.Integer
     */
    Integer read(@Param("id")Integer id, @Param("userId")Integer userId);

    /**
     * 将所有未读提醒标记为已读
     *
     * @param userId 用户id
     * @return java.lang.Integer
     */
    Integer clearUnread(@Param("userId") int userId);
}
