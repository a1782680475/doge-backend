package com.doge.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doge.dao.entity.*;
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
import java.util.stream.Collectors;

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
    private Map<String, List<String>> subscriptionSettingMapping;
    private Map<String, Boolean> subscriptionDefaultSetting;

    @Autowired
    RemindServiceImpl(
            NotifySubscriptionConfigMapper notifySubscriptionConfigMapper,
            NotifySubscriptionMapper notifySubscriptionMapper,
            NotifyRemindMapper notifyRemindMapper,
            NotifyUserRemindMapper notifyUserRemindMapper,
            RemindProperties remindProperties,
            Map<String, List<String>> subscriptionSettingMapping,
            Map<String, Boolean> subscriptionDefaultSetting
    ) {
        this.notifySubscriptionConfigMapper = notifySubscriptionConfigMapper;
        this.notifySubscriptionMapper = notifySubscriptionMapper;
        this.notifyRemindMapper = notifyRemindMapper;
        this.notifyUserRemindMapper = notifyUserRemindMapper;
        this.remindProperties = remindProperties;
        this.subscriptionSettingMapping = subscriptionSettingMapping;
        this.subscriptionDefaultSetting = subscriptionDefaultSetting;
    }

    @Override
    public Map<String, Boolean> getSubscriptionConfig(Integer userId) {
        Map<String, Boolean> subscriptionConfig = new HashMap(100);
        //查询配置信息
        NotifySubscriptionConfigDO notifySubscriptionConfigDO = notifySubscriptionConfigMapper.getConfigByUserId(userId);
        if (notifySubscriptionConfigDO == null) {
            return subscriptionDefaultSetting;
        }
        //迭代默认配置
        //用户未配置，取默认配置；否则取用户配置
        Map<String, Boolean> userConfig = (Map<String, Boolean>) JSON.parse(notifySubscriptionConfigDO.getConfig());
        for (String key : subscriptionDefaultSetting.keySet()) {
            if (userConfig.containsKey(key)) {
                subscriptionConfig.put(key, userConfig.get(key));
            } else {
                subscriptionConfig.put(key, subscriptionDefaultSetting.get(key));
            }
        }
        return subscriptionConfig;
    }

    @Override
    public Boolean saveSubscriptionConfig(Integer userId, String config) {
        NotifySubscriptionConfigDO notifySubscriptionConfigDO = notifySubscriptionConfigMapper.getConfigByUserId(userId);
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
    public Boolean setSubscriptionConfig(int userId, String key, Boolean isEnabled) {
        String configJson;
        List<String> actionList = subscriptionSettingMapping.get(key);
        NotifySubscriptionConfigDO notifySubscriptionConfigDO = notifySubscriptionConfigMapper.getConfigByUserId(userId);
        Date now = new Date();
        if (notifySubscriptionConfigDO != null) {
            Map<String, Boolean> userConfig = (Map<String, Boolean>) JSON.parse(notifySubscriptionConfigDO.getConfig());
            Boolean originEnabled = userConfig.get(key);
            userConfig.put(key, isEnabled);
            actionList.forEach(action -> userConfig.put(action, isEnabled));
            configJson = JSON.toJSONString(userConfig);
            notifySubscriptionConfigDO.setConfig(configJson);
            notifySubscriptionConfigDO.setUpdateTime(now);
            notifySubscriptionConfigMapper.updateById(notifySubscriptionConfigDO);
            //取消订阅
            if (originEnabled && !isEnabled) {
                actionList.forEach(action -> {
                    unSubscribe(userId, action);
                });
            }
        } else {
            Map<String, Boolean> configMap = new HashMap<>(100);
            subscriptionDefaultSetting.forEach((k, v) -> {
                configMap.put(k, v);
            });
            configMap.put(key, isEnabled);
            actionList.forEach(action -> configMap.put(action, isEnabled));
            configJson = JSON.toJSONString(configMap);
            notifySubscriptionConfigDO = new NotifySubscriptionConfigDO();
            notifySubscriptionConfigDO.setUserId(userId);
            notifySubscriptionConfigDO.setConfig(configJson);
            notifySubscriptionConfigDO.setCreateTime(now);
            notifySubscriptionConfigDO.setUpdateTime(now);
            notifySubscriptionConfigMapper.insert(notifySubscriptionConfigDO);
        }
        return true;
    }

    @Override
    public Boolean subscribe(Integer userId, String targetType, Integer target, String reason) {
        List<String> shouldSubscribeActionList = queryShouldSubscribeActions(userId, reason);
        //将所有该订阅动作每个新建一个订阅记录
        Date now = new Date();
        for (String action : shouldSubscribeActionList) {
            //检查订阅记录，避免重复订阅
            NotifySubscriptionDO notifySubscriptionDO = notifySubscriptionMapper.getListByUserIdAndAction(userId, targetType, target, action);
            if (notifySubscriptionDO == null) {
                notifySubscriptionDO = new NotifySubscriptionDO();
                notifySubscriptionDO.setTargetType(targetType);
                notifySubscriptionDO.setTarget(target);
                notifySubscriptionDO.setAction(action);
                notifySubscriptionDO.setUserId(userId);
                notifySubscriptionDO.setCreateTime(now);
                notifySubscriptionDO.setUpdateTime(now);
                notifySubscriptionMapper.insert(notifySubscriptionDO);
            }
        }
        return true;
    }

    public Boolean unSubscribe(Integer userId, String action) {
        return notifySubscriptionMapper.deleteByUserIdAndAction(userId, action);
    }

    /**
     * 用户应订阅动作获取
     *
     * @param userId 用户id
     * @param reason 原因
     * @return java.lang.String[]
     */
    private List<String> queryShouldSubscribeActions(Integer userId, String reason) {
        //查询配置信息
        NotifySubscriptionConfigDO notifySubscriptionConfigDO = notifySubscriptionConfigMapper.getConfigByUserId(userId);
        List<String> shouldSubscribeActionList;
        //查询对应动作组
        var actions = remindProperties.getReason().get(reason);
        if (notifySubscriptionConfigDO == null) {
            shouldSubscribeActionList = actions.stream().filter(action -> subscriptionDefaultSetting.get(action)).collect(Collectors.toList());
        } else {
            shouldSubscribeActionList = new ArrayList<>();
            //迭代默认配置
            //用户未配置，取默认配置；否则取用户配置
            Map<String, Boolean> userConfig = (Map<String, Boolean>) JSON.parse(notifySubscriptionConfigDO.getConfig());
            for (String action : actions) {
                if (userConfig.containsKey(action)) {
                    if (userConfig.get(action)) {
                        shouldSubscribeActionList.add(action);
                    }
                } else {
                    if (subscriptionDefaultSetting.containsKey(action)) {
                        if (subscriptionDefaultSetting.get(action)) {
                            shouldSubscribeActionList.add(action);
                        }
                    }
                }
            }
        }
        return shouldSubscribeActionList;
    }

    @Override
    public Boolean publish(String targetType, Integer target, String action, Integer sender) {
        //检查是否存在订阅者
        List<NotifySubscriptionDO> notifySubscriptionList = notifySubscriptionMapper.getListByAction(targetType, target, action);
        if (notifySubscriptionList.isEmpty()) {
            return false;
        }
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
        //用户未拉取的推送提醒获取
        List<NotifySubscriptionBO> notifySubscriptionList = notifyRemindMapper.getNotPullListByUserId(userId);
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
                content = content.replace("${sendTime}", DateUtil.format(notifySubscription.getSendTime(), "yyyy-MM-dd HH:mm:ss"));
            }
            notifySubscription.setTitle(title);
            notifySubscription.setContent(content);
        }
        return notifySubscriptionQueryDTOList;
    }
}
