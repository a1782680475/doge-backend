package com.doge.service.impl;

import com.doge.service.NoticeService;
import com.doge.service.NoticeRemindService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 消息服务实现类
 *
 * @author shixinyu
 * @date 2023/5/3 8:48
 */
@Service
@NoArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private NoticeRemindService remindService;
    private static final String TARGET_TYPE_ACCOUNT = "account";
    private static final String ACTION_PASSWORD_RESET = "password_reset";
    private static final String REASON_PASSWORD_RESET = "account_password_reset";
    private static final String ACTION_PASSWORD_CHANGE = "password_change";
    private static final String REASON_PASSWORD_CHANGE = "account_password_change";
    private static final int SENDER = 0;

    @Autowired
    NoticeServiceImpl(NoticeRemindService remindService) {
        this.remindService = remindService;
    }

    @Override
    @Async
    public void sendResetPasswordRemind(Integer userId) {
        //这里虽然订阅者和触发者都是同一用户、触发动作还是同一个操作，但是订阅-推送的流程还是要走的。
        //还有一种情景是订阅和推送分属不同操作，比如我发布了一篇文章，我就需要订阅该文章的评论，每当有用户回复就推送给我。
        remindService.subscribe(userId, TARGET_TYPE_ACCOUNT, userId, REASON_PASSWORD_RESET);
        remindService.publish(TARGET_TYPE_ACCOUNT, userId, ACTION_PASSWORD_RESET, SENDER);
    }

    @Override
    @Async
    public void sendChangePasswordRemind(Integer userId) {
        remindService.subscribe(userId, TARGET_TYPE_ACCOUNT, userId, REASON_PASSWORD_CHANGE);
        remindService.publish(TARGET_TYPE_ACCOUNT, userId, ACTION_PASSWORD_CHANGE, SENDER);
    }
}
