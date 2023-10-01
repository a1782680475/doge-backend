package com.doge.service;

import com.doge.service.entity.AntPageDTO;
import com.doge.service.entity.NotifySubscriptionDTO;
import com.doge.service.entity.PageDTO;
import com.doge.service.entity.UnreadNoticeQueryDTO;

import java.util.List;
import java.util.Map;

/**
 * 消息-提醒服务
 *
 * @author shixinyu
 * @date 2021-10-11 08:45
 */
public interface RemindService {

    /**
     * 订阅配置获取
     *
     * @param userId 用户Id
     * @return java.util.Map<java.lang.String, java.lang.Boolean>
     */
    Map<String, Boolean> getSubscriptionConfig(Integer userId);

    /**
     * 订阅配置保存（所有）
     *
     * @param userId 用户id
     * @param config 配置（JSON格式，如：
     *               {
     *               "product": {
     *               "comment": true,
     *               "like": true,
     *               "collection": true
     *               },
     *               "article": {
     *               "comment": true,
     *               "like": true,
     *               }
     *               }）
     * @return java.lang.Boolean
     */
    Boolean saveSubscriptionConfig(Integer userId, String config);

    /**
     * 订阅配置保存（单个）
     *
     * @param userId   用户Id
     * @param key      配置key
     * @param isEnabled 是否启用
     * @return java.lang.Boolean
     */
    Boolean setSubscriptionConfig(int userId, String key, Boolean isEnabled);

    /**
     * 订阅
     *
     * @param userId     用户id
     * @param targetType 目标类型
     * @param target     目标
     * @param reason     原因（啥意思？就是比如我发布了一篇文章，我就需要订阅该文章的评论，每当有用户回复就推送给我。发布文章就是“原因”，而该原因配置文件里做了配置，对应一组触发动作）
     * @return java.lang.Boolean
     */
    Boolean subscribe(Integer userId, String targetType, Integer target, String reason);

    /**
     * 提醒推送
     *
     * @param targetType 目标类型
     * @param target     目标
     * @param action     触发动作（多个动作/分割，如: comment/like）
     * @param sender     发送者
     * @return java.lang.Boolean
     */
    Boolean publish(String targetType, Integer target, String action, Integer sender);

    /**
     * 提醒拉取
     *
     * @param userId 用户id
     * @return java.lang.Boolean
     */
    Boolean pullRemind(Integer userId);

    /**
     * 分页获取用户提醒
     *
     * @param pageDTO 分页
     * @param userId  用户id
     * @return com.doge.service.entity.AntPageDTO<com.doge.service.entity.NotifySubscriptionDTO>
     */
    AntPageDTO<NotifySubscriptionDTO> getListByPage(PageDTO pageDTO, Integer userId);

    /**
     * 获取用户未读提醒
     *
     * @param unreadNoticeQuery 提醒查询信息
     * @param userId            用户id
     * @return java.util.List<com.doge.service.entity.NotifySubscriptionDTO>
     */
    List<NotifySubscriptionDTO> getUnreadRemindList(UnreadNoticeQueryDTO unreadNoticeQuery, int userId);

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
     * @param id     提醒id
     * @param userId 用户id
     * @return java.lang.Integer
     */
    Integer read(Integer id, Integer userId);

    /**
     * 将所有未读提醒标记为已读
     *
     * @param userId 用户id
     * @return java.lang.Boolean
     */
    Boolean clearUnread(int userId);
}
