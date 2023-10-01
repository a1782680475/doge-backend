package com.doge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doge.dao.mapper.NotifyUserMessageMapper;
import com.doge.dao.queryentity.NotifyMessageBO;
import com.doge.service.entity.AntPageDTO;
import com.doge.service.entity.NotifyMessageDTO;
import com.doge.service.entity.PageDTO;
import com.doge.service.NoticeMessageService;
import com.doge.service.entity.UnreadNoticeQueryDTO;
import com.doge.utils.BeanUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户私信服务实现类
 *
 * @author shixinyu
 * @date 2021/12/28 16:43
 */
@Service
@NoArgsConstructor
public class NoticeMessageServiceImpl implements NoticeMessageService {
    private NotifyUserMessageMapper notifyUserMessageMapper;
    @Autowired
    NoticeMessageServiceImpl(NotifyUserMessageMapper notifyUserMessageMapper){
        this.notifyUserMessageMapper = notifyUserMessageMapper;
    }
    @Override
    public AntPageDTO<NotifyMessageDTO> getListByPage(PageDTO pageDTO, Integer userId) {
        Page<NotifyMessageBO> page = new Page<>(pageDTO.getCurrent(),pageDTO.getPageSize());
        IPage<NotifyMessageBO> notifyMessagePage = notifyUserMessageMapper.selectListByPage(page,userId);
        return new AntPageDTO<NotifyMessageBO>().init(notifyMessagePage);
    }

    @Override
    public List<NotifyMessageDTO> getUnreadMessageList(UnreadNoticeQueryDTO unreadNoticeQuery, int userId) {
        List<NotifyMessageDTO> notifyMessageList = BeanUtils.mapAsList(notifyUserMessageMapper.selectUnreadListByCount(unreadNoticeQuery.getCount(), userId), NotifyMessageDTO.class);
        return notifyMessageList;
    }

    @Override
    public Integer getUnreadCount(int userId) {
        return notifyUserMessageMapper.getUnreadCount(userId);
    }

    @Override
    public Integer read(Integer id, int userId) {
        if(!notifyUserMessageMapper.selectById(id).getRead()) {
            return notifyUserMessageMapper.read(id, userId);
        }
        return 0;
    }

    @Override
    public Boolean clearUnread(int userId) {
        notifyUserMessageMapper.clearUnread(userId);
        return true;
    }
}
