package com.doge.service;

import com.doge.service.entity.AntPageDTO;
import com.doge.service.entity.NotifyMessageDTO;
import com.doge.service.entity.PageDTO;
import com.doge.service.entity.UnreadNoticeQueryDTO;

import java.util.List;

/**
 * 消息-私信服务
 *
 * @author shixinyu
 * @date 2021/12/9 20:38
 */
public interface MessageService {
    /**
     * 分页获取用户私信
     *
     * @param pageDTO   分页
     * @param userId 用户id
     * @return AntPageDTO<NotifyMessageDTO>
     */
    AntPageDTO<NotifyMessageDTO> getListByPage(PageDTO pageDTO, Integer userId);

    /**
     * 获取用户未读私信
     *
     * @param unreadNoticeQuery 私信查询信息
     * @param userId 用户id
     * @return java.util.List<com.doge.service.entity.NotifyMessageDTO>
     */
    List<NotifyMessageDTO> getUnreadMessageList(UnreadNoticeQueryDTO unreadNoticeQuery, int userId);

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
     * @param id 提醒id
     * @param userId 用户id
     * @return java.lang.Integer
     */
    Integer read(Integer id, int userId);

    /**
     * 将所有未读私信标记为已读
     *
     * @param userId 用户id
     * @return java.lang.Boolean
     */
    Boolean clearUnread(int userId);
}
