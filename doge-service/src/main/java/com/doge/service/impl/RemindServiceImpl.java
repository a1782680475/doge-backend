package com.doge.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doge.dao.entity.NotifyRemindDO;
import com.doge.dao.entity.NotifySubscriptionConfigDO;
import com.doge.dao.entity.NotifySubscriptionDO;
import com.doge.dao.entity.NotifyUserRemindDO;
import com.doge.dao.mapper.NotifyRemindMapper;
import com.doge.dao.mapper.NotifySubscriptionConfigMapper;
import com.doge.dao.mapper.NotifySubscriptionMapper;
import com.doge.dao.mapper.NotifyUserRemindMapper;
import com.doge.dao.queryentity.NotifySubscriptionBO;
import com.doge.service.entity.AntPageDTO;
import com.doge.service.entity.NotifySubscriptionDTO;
import com.doge.service.entity.PageDTO;
import com.doge.service.RemindService;
import com.doge.service.bean.Notify;
import com.doge.service.bean.RemindProperties;
import com.doge.service.entity.UnreadNoticeQueryDTO;
import com.doge.utils.BeanUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 消息-提醒服务实现类
 *
 * @author shixinyu
 * @date 2021-10-11 10:38
 */
@Service
@NoArgsConstructor
public class RemindServiceImpl implements RemindService {
    private NotifySubscriptionConfigMapper notifySubscriptionConfigMapper;
    private NotifySubscriptionMapper notifySubscriptionMapper;
    private NotifyRemindMapper notifyRemindMapper;
    private NotifyUserRemindMapper notifyUserRemindMapper;
    private RemindProperties remindProperties;
    private Map<String, Boolean> subscriptionDefaultSetting;

    @Autowired
    RemindServiceImpl(
            NotifySubscriptionConfigMapper notifySubscriptionConfigMapper,
            NotifySubscriptionMapper notifySubscriptionMapper,
            NotifyRemindMapper notifyRemindMapper,
            NotifyUserRemindMapper notifyUserRemindMapper,
            RemindProperties remindProperties,
            Map<String, Boolean> subscriptionDefaultSetting
    ) {
        this.notifySubscriptionConfigMapper = notifySubscriptionConfigMapper;
        this.notifySubscriptionMapper = notifySubscriptionMapper;
        this.notifyUserRemindMapper = notifyUserRemindMapper;
        this.remindProperties = remindProperties;
        this.subscriptionDefaultSetting = subscriptionDefaultSetting;
    }

    @Override
    public Boolean subscriptionConfig(Integer userId, String config) {
        NotifySubscriptionConfigDO notifySubscriptionConfigDO = notifySubscriptionConfigMapper.getListByUserId(userId);
        Date now = new Date();
        if (notifySubscriptionConfigDO != null) {
            notifySubscriptionConfigDO.setConfig(config);
            notifySubscriptionConfigDO.setUpdateTime(now);
            notifySubscriptionConfigMapper.updateById(notifySubscriptionConfigDO);
        } else {
            notifySubscriptionConfigDO = new NotifySubscriptionConfigDO();
            notifySubscriptionConfigDO.setUserId(userId);
            notifySubscriptionConfigDO.setConfig(config);
            notifySubscriptionConfigDO.setCreateTime(now);
            notifySubscriptionConfigDO.setUpdateTime(now);
            notifySubscriptionConfigMapper.insert(notifySubscriptionConfigDO);
        }
        return true;
    }

    @Override
    public Boolean subscribe(Integer userId, String targetType, Integer target, String reason) {
        String[] shouldSubscribeActions = queryShouldSubscribeActions(userId, reason);
        //将所有该订阅动作每个新建一个订阅记录
        Date now = new Date();
        for (String action : shouldSubscribeActions) {
            NotifySubscriptionDO notifySubscriptionDO = new NotifySubscriptionDO();
            notifySubscriptionDO.setTargetType(targetType);
            notifySubscriptionDO.setTarget(target);
            notifySubscriptionDO.setAction(action);
            notifySubscriptionDO.setUserId(userId);
            notifySubscriptionDO.setCreateTime(now);
            notifySubscriptionDO.setUpdateTime(now);
            notifySubscriptionMapper.insert(notifySubscriptionDO);
        }
        return true;
    }

    /**
     * 用户应订阅动作获取
     *
     * @param userId 用户id
     * @param reason 原因
     * @return java.lang.String[]
     */
    private String[] queryShouldSubscribeActions(Integer userId, String reason) {
        //查询配置信息
        NotifySubscriptionConfigDO notifySubscriptionConfigDO = notifySubscriptionConfigMapper.getListByUserId(userId);
        String[] shouldSubscribeActions;
        //查询对应动作组
        String[] actions = remindProperties.getReasonAction().get(reason);
        //用户配置为空，取默认配置
        if (notifySubscriptionConfigDO == null) {
            shouldSubscribeActions = Arrays.stream(actions).filter(action -> subscriptionDefaultSetting.get(action)).toArray(String[]::new);
        } else {
            //用户配置不为空，取用户配置
            Map<String, Boolean> userConfig = (Map<String, Boolean>) JSON.parse(notifySubscriptionConfigDO.getConfig());
            shouldSubscribeActions = Arrays.stream(actions).filter(action -> userConfig.get(action)).toArray(String[]::new);
        }
        return shouldSubscribeActions;
    }

    @Override
    public Boolean createRemind(String targetType, Integer target, String action, Integer sender) {
        NotifyRemindDO notifyRemindDO = new NotifyRemindDO();
        Date now = new Date();
        notifyRemindDO.setTargetType(targetType);
        notifyRemindDO.setTarget(target);
        notifyRemindDO.setAction(action);
        notifyRemindDO.setSender(sender);
        notifyRemindDO.setCreateTime(now);
        notifyRemindDO.setUpdateTime(now);
        notifyRemindMapper.insert(notifyRemindDO);
        return true;
    }

    @Override
    public Boolean pullRemind(Integer userId) {
        //用户订阅信息获取
        List<NotifySubscriptionBO> notifySubscriptionList = notifySubscriptionMapper.getListByUserId(userId);
        //写入用户提醒
        for (NotifySubscriptionBO notifySubscription : notifySubscriptionList) {
            NotifyUserRemindDO notifyUserRemindDO = new NotifyUserRemindDO();
            notifyUserRemindDO.setUserId(notifySubscription.getUserId());
            notifyUserRemindDO.setRemindId(notifySubscription.getRemindId());
            notifyUserRemindDO.setRead(false);
            Date now = new Date();
            notifyUserRemindDO.setCreateTime(now);
            notifyUserRemindDO.setUpdateTime(now);
            notifyUserRemindMapper.insert(notifyUserRemindDO);
        }
        return true;
    }

    @Override
    public AntPageDTO<NotifySubscriptionDTO> getListByPage(PageDTO pageDTO, Integer userId) {
        Page<NotifyUserRemindDO> page = new Page<>(pageDTO.getCurrent(), pageDTO.getPageSize());
        IPage<NotifyUserRemindDO> notifyUserRemindDOPage = notifyUserRemindMapper.selectListByPage(page, userId);
        List<NotifySubscriptionDTO> notifySubscriptionQueryDTOList = BeanUtils.mapAsList(notifyUserRemindDOPage.getRecords(), NotifySubscriptionDTO.class);
        notifySubscriptionQueryDTOList = notifySubscriptionProcess(notifySubscriptionQueryDTOList);
        AntPageDTO antPageDTO = new AntPageDTO()
                .setSuccess(true)
                .setTotal(notifyUserRemindDOPage.getTotal())
                .setPageSize(notifyUserRemindDOPage.getPages())
                .setCurrent(notifyUserRemindDOPage.getCurrent())
                .setData(notifySubscriptionQueryDTOList);
        return antPageDTO;
    }

    @Override
    public List<NotifySubscriptionDTO> getUnreadRemindList(UnreadNoticeQueryDTO unreadNoticeQuery, int userId) {
        List<NotifySubscriptionDTO> notifySubscriptionQueryDTOList = BeanUtils.mapAsList(notifyUserRemindMapper.selectUnreadListByCount(unreadNoticeQuery.getCount(), userId), NotifySubscriptionDTO.class);
        notifySubscriptionQueryDTOList = notifySubscriptionProcess(notifySubscriptionQueryDTOList);
        return notifySubscriptionQueryDTOList;
    }

    @Override
    public Integer getUnreadCount(Integer userId) {
        return notifyUserRemindMapper.getUnreadCount(userId);
    }

    @Override
    public Integer read(Integer id, Integer userId) {
        if (!notifyUserRemindMapper.selectById(id).getRead()) {
            return notifyUserRemindMapper.read(id, userId);
        }
        return 0;
    }

    @Override
    public Boolean clearUnread(int userId) {
        notifyUserRemindMapper.clearUnread(userId);
        return true;
    }

    private List<NotifySubscriptionDTO> notifySubscriptionProcess(List<NotifySubscriptionDTO> notifySubscriptionQueryDTOList) {
        Map<String, Notify> template = remindProperties.getTemplate();
        for (NotifySubscriptionDTO notifySubscription : notifySubscriptionQueryDTOList) {
            Notify notify = template.get(notifySubscription.getAction());
            String title = notify.getTitle();
            String content = notify.getContent();
            if (notifySubscription.getSender() != null) {
                content = content.replace("${sender}", notifySubscription.getSender());
            }
            if (notifySubscription.getSendTime() != null) {
                content = content.replace("${sendTime}", DateUtil.format(notifySubscription.getSendTime(), "yyyy-MM-dd dd:mm:ss"));
            }
            notifySubscription.setTitle(title);
            notifySubscription.setContent(content);
        }
        return notifySubscriptionQueryDTOList;
    }
}
