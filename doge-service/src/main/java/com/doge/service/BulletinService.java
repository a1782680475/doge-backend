package com.doge.service;

import com.doge.service.entity.AntPageDTO;
import com.doge.service.entity.NotifyBulletinDTO;
import com.doge.service.entity.PageDTO;
import com.doge.service.entity.UnreadNoticeQueryDTO;

import java.util.List;

/**
 * 消息-公告服务
 *
 * @author shixinyu
 * @date 2021/12/29 16:16
 */
public interface BulletinService {
    /**
     * 分页获取用户公告
     *
     * @param pageDTO   分页
     * @param userId 用户id
     * @return com.doge.service.entity.AntPageDTO<com.doge.service.entity.NotifyBulletinDTO>
     */
    AntPageDTO<NotifyBulletinDTO> getListByPage(PageDTO pageDTO, Integer userId);

    /**
     * 获取用户未读公告
     *
     * @param unreadNoticeQuery 公告查询信息
     * @param userId 用户id
     * @return com.doge.service.entity.AntPageDTO<com.doge.service.entity.NotifyBulletinDTO>
     */
    List<NotifyBulletinDTO> getUnreadBulletinList(UnreadNoticeQueryDTO unreadNoticeQuery, int userId);

    /**
     * 用户用户未读公告数获取
     *
     * @param userId 用户id
     * @return java.lang.Integer
     */
    Integer getUnreadCount(int userId);

    /**
     * 将未读公告标记为已读
     *
     * @param id 提醒id
     * @param userId 用户id
     * @return java.lang.Integer
     */
    Integer read(Integer id, int userId);

    /**
     * 将所有未读公告标记为已读
     *
     * @param userId 用户id
     * @return java.lang.Boolean
     */
    Boolean clearUnread(int userId);
}
