package com.doge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doge.dao.entity.NotifyUserBulletinDO;
import com.doge.dao.mapper.NotifyUserBulletinMapper;
import com.doge.dao.queryentity.NotifyBulletinBO;
import com.doge.service.NoticeBulletinService;
import com.doge.service.entity.AntPageDTO;
import com.doge.service.entity.NotifyBulletinDTO;
import com.doge.service.entity.PageDTO;
import com.doge.service.entity.UnreadNoticeQueryDTO;
import com.doge.utils.BeanUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户公告服务实现类
 *
 * @author shixinyu
 * @date 2021/12/29 16:46
 */
@Service
@NoArgsConstructor
public class NoticeBulletinServiceImpl implements NoticeBulletinService {
    private NotifyUserBulletinMapper notifyUserBulletinMapper;

    @Autowired
    NoticeBulletinServiceImpl(NotifyUserBulletinMapper notifyBulletinMessageMapper) {
        this.notifyUserBulletinMapper = notifyBulletinMessageMapper;
    }

    @Override
    public AntPageDTO<NotifyBulletinDTO> getListByPage(PageDTO pageDTO, Integer userId) {
        Page<NotifyBulletinBO> page = new Page<>(pageDTO.getCurrent(), pageDTO.getPageSize());
        IPage<NotifyBulletinBO> notifyBulletinPage = notifyUserBulletinMapper.selectListByPage(page, userId);
        return new AntPageDTO<NotifyBulletinBO>().init(notifyBulletinPage);
    }

    @Override
    public List<NotifyBulletinDTO> getUnreadBulletinList(UnreadNoticeQueryDTO unreadNoticeQuery, int userId) {
        List<NotifyBulletinDTO> notifyBulletinList = BeanUtils.mapAsList(notifyUserBulletinMapper.selectUnreadListByCount(unreadNoticeQuery.getCount(), userId), NotifyBulletinDTO.class);
        return notifyBulletinList;
    }

    @Override
    public Integer getUnreadCount(int userId) {
        return notifyUserBulletinMapper.getUnreadCount(userId);
    }

    @Override
    public Integer read(Integer id, int userId) {
        if (notifyUserBulletinMapper.selectByIdAndUserId(id, userId) == null) {
            Date now = new Date();
            NotifyUserBulletinDO notifyUserBulletinDO = new NotifyUserBulletinDO();
            notifyUserBulletinDO.setBulletinId(id);
            notifyUserBulletinDO.setUserId(userId);
            notifyUserBulletinDO.setRead(true);
            notifyUserBulletinDO.setReadTime(now);
            notifyUserBulletinDO.setCreateTime(now);
            notifyUserBulletinDO.setUpdateTime(now);
            return notifyUserBulletinMapper.insert(notifyUserBulletinDO);
        }
        return 0;
    }

    @Override
    public Boolean clearUnread(int userId) {
        List<NotifyBulletinBO> notifyUserBulletinList = notifyUserBulletinMapper.selectUnreadList(userId);
        List<NotifyUserBulletinDO> userBulletinList = new ArrayList();
        Date now = new Date();
        for (NotifyBulletinBO notifyUserBulletin : notifyUserBulletinList) {
            NotifyUserBulletinDO userBulletin = new NotifyUserBulletinDO();
            userBulletin.setBulletinId(notifyUserBulletin.getId());
            userBulletin.setUserId(userId);
            userBulletin.setRead(true);
            userBulletin.setReadTime(now);
            userBulletin.setCreateTime(now);
            userBulletin.setUpdateTime(now);
            userBulletinList.add(userBulletin);
        }
        if(notifyUserBulletinList.size()>0) {
            notifyUserBulletinMapper.insertBatchSomeColumn(userBulletinList);
        }
        return true;
    }
}
