package com.doge.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doge.dao.entity.NotifyUserMessageDO;
import com.doge.dao.queryentity.NotifyMessageBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户私信Mapper
 *
 * @author shixinyu
 * @date 2021/12/29 14:19
 */
public interface NotifyUserMessageMapper extends BaseMapper<NotifyUserMessageDO> {
    /**
     * 分页获取用户私信消息
     *
     * @param page   分页
     * @param userId 用户id
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.doge.dao.queryentity.NotifyMessageBO>
     */
    IPage<NotifyMessageBO> selectListByPage(Page<NotifyMessageBO> page, @Param("userId") Integer userId);

    /**
     * 获取用户未读私信
     *
     * @param count  查询最大条目数
     * @param userId 用户id
     * @return java.util.List<com.doge.dao.queryentity.NotifyMessageBO>
     */
    List<NotifyMessageBO> selectUnreadListByCount(@Param("count") Integer count, @Param("userId") Integer userId);

    /**
     * 用户用户未读私信数获取
     *
     * @param userId 用户id
     * @return java.lang.Integer
     */
    Integer getUnreadCount(int userId);

    /**
     * 将未读私信标记为已读
     *
     * @param id     提醒id
     * @param userId 用户id
     * @return java.lang.Integer
     */
    Integer read(@Param("id") Integer id, @Param("userId") int userId);

    /**
     * 将所有未读私信标记为已读
     *
     * @param userId 用户id
     * @return java.lang.Integer
     */
    void clearUnread(int userId);
}
