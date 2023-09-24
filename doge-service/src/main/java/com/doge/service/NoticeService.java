package com.doge.service;

/**
 * 消息服务
 *
 * @author shixinyu
 * @date 2023/5/3 8:43
 */
public interface NoticeService {
    /**
     * 密码重置提醒发送
     * 
     * @param userId 用户Id
     * @return void
     */
    public void sendResetPasswordRemind(Integer userId);
    /**
     * 密码修改提醒发送
     *
     * @param userId 用户Id
     * @return void
     */
    public void sendChangePasswordRemind(Integer userId);
}
