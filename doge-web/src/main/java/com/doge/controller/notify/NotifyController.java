package com.doge.controller.notify;

import com.doge.entity.vo.request.SubscriptionConfigSettingVO;
import com.doge.entity.vo.response.*;
import com.doge.service.NoticeBulletinService;
import com.doge.service.entity.*;
import com.doge.entity.PageQuery;
import com.doge.entity.vo.request.UnreadNoticeQueryVO;
import com.doge.service.NoticeMessageService;
import com.doge.service.NoticeRemindService;
import com.doge.utils.BeanUtils;
import com.doge.utils.PageUtils;
import com.doge.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 通知业务
 *
 * @author shixinyu
 * @date 2021-10-13 15:58
 */
@RestController
@RequestMapping("/notify")
@Api(value = "NotifyController", tags = "通知管理")
@NoArgsConstructor
public class NotifyController {
    private NoticeRemindService remindService;
    private NoticeMessageService messageService;
    private NoticeBulletinService bulletinService;

    @Autowired
    NotifyController(NoticeRemindService remindService, NoticeMessageService messageService, NoticeBulletinService bulletinService) {
        this.remindService = remindService;
        this.messageService = messageService;
        this.bulletinService = bulletinService;
    }

    @GetMapping("/remind/remindPageList")
    @ApiOperation(value = "提醒列表获取（分页）")
    public AntPageVO remindPageList(PageQuery pageQuery) {
        int userId = SecurityUtils.getUserId();
        PageDTO pageDTO = PageUtils.createPageDTO(pageQuery);
        AntPageDTO<NotifySubscriptionDTO> antPageDTO = remindService.getListByPage(pageDTO, userId);
        return new AntPageVO().build(antPageDTO, NoticeRemindVO.class);
    }

    @GetMapping("/remind/unreadList")
    @ApiOperation(value = "指定数目的未读提醒列表获取")
    public List<NoticeRemindVO> unreadRemindList(UnreadNoticeQueryVO unreadNoticeQuery) {
        int userId = SecurityUtils.getUserId();
        UnreadNoticeQueryDTO unreadNoticeQueryDTO = BeanUtils.map(unreadNoticeQuery, UnreadNoticeQueryDTO.class);
        return BeanUtils.mapAsList(remindService.getUnreadRemindList(unreadNoticeQueryDTO, userId), NoticeRemindVO.class);
    }

    @GetMapping("/remind/unreadCount")
    @ApiOperation(value = "未读提醒数获取")
    public Integer unreadRemindCount() {
        int userId = SecurityUtils.getUserId();
        return remindService.getUnreadCount(userId);
    }

    @PutMapping("/remind/{id}")
    @ApiOperation(value = "将未读提醒标记为已读")
    public Integer remindRead(@PathVariable Integer id) {
        int userId = SecurityUtils.getUserId();
        return remindService.read(id, userId);
    }

    @PutMapping("/remind/clearUnread")
    @ApiOperation(value = "将所有未读提醒标记为已读")
    public Boolean clearRemindUnread() {
        int userId = SecurityUtils.getUserId();
        return remindService.clearUnread(userId);
    }

    @GetMapping("/message/messagePageList")
    @ApiOperation(value = "私信列表获取（分页）")
    public AntPageVO messagePageList(PageQuery pageQuery) {
        int userId = SecurityUtils.getUserId();
        PageDTO pageDTO = PageUtils.createPageDTO(pageQuery);
        AntPageDTO<NotifyMessageDTO> antPageDTO = messageService.getListByPage(pageDTO, userId);
        return new AntPageVO().build(antPageDTO, NoticeMessageVO.class);
    }

    @GetMapping("/message/unreadList")
    @ApiOperation(value = "指定数目的未读私信列表获取")
    public List<NoticeMessageVO> unreadMessageList(UnreadNoticeQueryVO unreadNoticeQuery) {
        int userId = SecurityUtils.getUserId();
        UnreadNoticeQueryDTO unreadNoticeQueryDTO = BeanUtils.map(unreadNoticeQuery, UnreadNoticeQueryDTO.class);
        return BeanUtils.mapAsList(messageService.getUnreadMessageList(unreadNoticeQueryDTO, userId), NoticeMessageVO.class);
    }

    @GetMapping("/message/unreadCount")
    @ApiOperation(value = "未读私信数获取")
    public Integer unreadMessageCount() {
        int userId = SecurityUtils.getUserId();
        return messageService.getUnreadCount(userId);
    }

    @PutMapping("/message/{id}")
    @ApiOperation(value = "将未读私信标记为已读")
    public Integer messageRead(@PathVariable Integer id) {
        int userId = SecurityUtils.getUserId();
        return messageService.read(id, userId);
    }

    @PutMapping("/message/clearUnread")
    @ApiOperation(value = "将所有未读私信标记为已读")
    public Boolean clearMessageUnread() {
        int userId = SecurityUtils.getUserId();
        return messageService.clearUnread(userId);
    }

    @GetMapping("/bulletin/bulletinPageList")
    @ApiOperation(value = "公告列表获取（分页）")
    public AntPageVO bulletinPageList(PageQuery pageQuery) {
        int userId = SecurityUtils.getUserId();
        PageDTO pageDTO = PageUtils.createPageDTO(pageQuery);
        AntPageDTO<NotifyBulletinDTO> antPageDTO = bulletinService.getListByPage(pageDTO, userId);
        return new AntPageVO().build(antPageDTO, NoticeBulletinVO.class);
    }

    @GetMapping("/bulletin/unreadList")
    @ApiOperation(value = "指定数目的未读公告列表获取")
    public List<NoticeBulletinVO> unreadBulletinList(UnreadNoticeQueryVO unreadNoticeQuery) {
        int userId = SecurityUtils.getUserId();
        UnreadNoticeQueryDTO unreadNoticeQueryDTO = BeanUtils.map(unreadNoticeQuery, UnreadNoticeQueryDTO.class);
        return BeanUtils.mapAsList(bulletinService.getUnreadBulletinList(unreadNoticeQueryDTO, userId), NoticeBulletinVO.class);
    }

    @GetMapping("/bulletin/unreadCount")
    @ApiOperation(value = "未读公告数获取")
    public Integer unreadBulletinCount() {
        int userId = SecurityUtils.getUserId();
        return bulletinService.getUnreadCount(userId);
    }

    @PutMapping("/bulletin/{id}")
    @ApiOperation(value = "将未读公告标记为已读")
    public Integer bulletinRead(@PathVariable Integer id) {
        int userId = SecurityUtils.getUserId();
        return bulletinService.read(id, userId);
    }

    @PutMapping("/bulletin/clearUnread")
    @ApiOperation(value = "将所有未读公告标记为已读")
    public Boolean clearBulletinUnread() {
        int userId = SecurityUtils.getUserId();
        return bulletinService.clearUnread(userId);
    }

    @GetMapping(value = "/subscriptionConfig")
    @ApiOperation("用户消息订阅配置获取")
    public Map<String, Boolean> geNotifySubscriptionConfig() {
        int userId = SecurityUtils.getUserId();
        return remindService.getSubscriptionConfig(userId);
    }

    @PutMapping(value = "/subscriptionConfig")
    @ApiOperation("用户消息订阅配置修改")
    public Boolean changeSubscriptionConfig(@RequestBody SubscriptionConfigSettingVO subscriptionConfigSetting) {
        int userId = SecurityUtils.getUserId();
        return remindService.setSubscriptionConfig(userId, subscriptionConfigSetting.getKey(), subscriptionConfigSetting.getIsEnabled());
    }
}
