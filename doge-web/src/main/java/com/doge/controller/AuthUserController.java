package com.doge.controller;

import com.alibaba.fastjson.JSON;
import com.doge.entity.vo.request.FindPasswordVO;
import com.doge.entity.vo.request.FindPasswordVerifyCodeVO;
import com.doge.entity.vo.request.SysUserBindEmailInfoVO;
import com.doge.entity.vo.request.SysUserRegisterInfoVO;
import com.doge.entity.vo.response.*;
import com.doge.service.common.MessageResult;
import com.doge.service.entity.SendVerificationCodeForPasswordResultDTO;
import com.doge.service.entity.SimpleTokenResultDTO;
import com.doge.service.entity.SysUserRegisterInfoDTO;
import com.wf.captcha.ArithmeticCaptcha;
import com.doge.service.BulletinService;
import com.doge.service.MessageService;
import com.doge.service.RemindService;
import com.doge.service.SysUserService;
import com.doge.utils.AntMenuUtils;
import com.doge.utils.BeanUtils;
import com.doge.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户注册等相关业务
 *
 * @author shixinyu
 * @date 2021-06-09 08:36
 */
@RestController
@RequestMapping("/auth")
@Api(value = "AuthUserController", tags = "用户登录注册等相关业务")
public class AuthUserController {
    private SysUserService userService;
    private RemindService remindService;
    private MessageService messageService;
    private BulletinService bulletinService;
    private RedisTemplate redisTemplate;

    AuthUserController() {

    }

    @Autowired
    AuthUserController(SysUserService userService, RedisTemplate redisTemplate, RemindService remindService, MessageService messageService,BulletinService bulletinService) {
        this.userService = userService;
        this.remindService = remindService;
        this.messageService = messageService;
        this.bulletinService = bulletinService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 用户注册
     *
     * @param userRegisterInfoVO 用户注册信息
     * @return java.lang.String
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public String register(SysUserRegisterInfoVO userRegisterInfoVO) {
        MessageResult<String> messageResult = userService.register(BeanUtils.map(userRegisterInfoVO, SysUserRegisterInfoDTO.class));
        return JSON.toJSONString(messageResult);
    }

    /**
     * 用户信息获取
     *
     * @param
     * @return java.lang.String
     */
    @GetMapping("/user")
    @ApiOperation(value = "用户信息获取")
    public AntAuthVO user() {
        int userId = SecurityUtils.getUserId();
        SysUserVO userVO = BeanUtils.map(userService.getById(userId), SysUserVO.class);
        List<SysMenuVO> menuVOList = BeanUtils.mapAsList(userService.getMenuList(userId), SysMenuVO.class);
        AntAuthVO antAuthVO = new AntAuthVO();
        antAuthVO.setUserId(userVO.getId());
        antAuthVO.setUsername(userVO.getUsername());
        antAuthVO.setNickName(userVO.getNickName());
        antAuthVO.setAvatar(userVO.getAvatar());
        antAuthVO.setMenus(AntMenuUtils.buildMenu(menuVOList.stream().filter(menu -> menu.getType() == 1 || menu.getType() == 2).collect(Collectors.toList())));
        antAuthVO.setAuthorities(menuVOList.stream().map(menu -> menu.getPermission()).collect(Collectors.toList()));
        List<SysRoleVO> roleVOList = BeanUtils.mapAsList(userService.getRoleList(userId), SysRoleVO.class);
        antAuthVO.setRoles(roleVOList.stream().map(role -> role.getRoleCode()).collect(Collectors.toList()));
        int unreadCount = remindService.getUnreadCount(userId) + messageService.getUnreadCount(userId) + bulletinService.getUnreadCount(userId);
        antAuthVO.setUnreadCount(unreadCount);
        //拉取推送的提醒。
        //增加消息拉取操作，因为提醒表的target不一定是用户id（比如我发布一篇文章，订阅文章被评论提醒的话，target就是文章id）,所以如果想查询某用户所订阅的提醒仍需联合订阅表查询，但订阅表从业务层面应该只在推送提醒时被用到，所以增加一个消息拉取操作用来直接关联用户与提醒。
        //这里暂时只处理提醒，公告的话因为没有订阅机制，加之用户特异性不强，当长时间未被拉取，再次拉取时可能会造成大量积压的公告被拉取也会造成性能问题，暂时先通过查询处理。
        remindService.pullRemind(userId);
        return antAuthVO;
    }

    /**
     * 用户菜单获取
     *
     * @param
     * @return java.lang.String
     */
    @GetMapping("/menu")
    @ApiOperation(value = "用户菜单获取")
    public List<AntMenuVO> menu() {
        int userId = SecurityUtils.getUserId();
        List<SysMenuVO> menuVOList = BeanUtils.mapAsList(userService.getMenuList(userId), SysMenuVO.class);
        return AntMenuUtils.buildMenu(menuVOList.stream().filter(menu -> menu.getType() == 1 || menu.getType() == 2).collect(Collectors.toList()));
    }

    /**
     * 账号登录图片验证码
     *
     * @return
     */
    @ApiOperation("获取验证码")
    @GetMapping(value = "/captcha")
    public ImgCaptchaVO getCaptcha() {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(120, 40);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // 保存到redis
        redisTemplate.opsForValue().set("doge:account:captcha_"+uuid, result, 60 * 5 * 1000L, TimeUnit.MILLISECONDS);
        // 验证码信息
        return new ImgCaptchaVO(uuid, captcha.toBase64());
    }

    @PostMapping("/bindEmailVerify")
    @ApiOperation(value = "用户绑定邮箱验证")
    public SimpleResultVO bindEmailVerify(@RequestBody SysUserBindEmailInfoVO sysUserBindEmailInfoVO) {
        SimpleResultVO resultVO = new SimpleResultVO();
        String errorMessage = userService.bindEmailVerify(sysUserBindEmailInfoVO.getEmail(), sysUserBindEmailInfoVO.getToken());
        if (errorMessage != null) {
            resultVO.isSuccess = false;
            resultVO.errorMessage = errorMessage;
        }
        return resultVO;
    }

    @PutMapping("/findPassword/sendVerificationCode")
    @ApiOperation(value = "用户账户密码找回邮箱验证码发送")
    public SendVerificationCodeForPasswordResultVO sendVerificationCodeForFindPassword(@RequestBody String username) {
        SendVerificationCodeForPasswordResultVO resultVO = new SendVerificationCodeForPasswordResultVO();
        SendVerificationCodeForPasswordResultDTO resultDTO = userService.sendVerificationCodeForFindPassword(username);
        if (resultDTO.getErrorMessage() != null) {
            resultVO.isSuccess = false;
            resultVO.errorMessage = resultDTO.getErrorMessage();
        } else {
            resultVO.setEmail(resultDTO.getEmail());
            resultVO.setToken(resultDTO.getToken());
        }
        return resultVO;
    }

    @PostMapping("/findPassword/verificationCodeVerify")
    @ApiOperation(value = "用户账户密码找回邮箱验证码验证")
    public SimpleTokenResultVO verifyCodeForFindPassword(@RequestBody FindPasswordVerifyCodeVO findPasswordVO) {
        SimpleTokenResultVO resultVO = new SimpleTokenResultVO();
        SimpleTokenResultDTO resultDTO = userService.verifyCodeForFindPassword(findPasswordVO.getToken(), findPasswordVO.getCode());
        if (resultDTO.getErrorMessage() != null) {
            resultVO.isSuccess = false;
            resultVO.errorMessage = resultDTO.getErrorMessage();
        }else {
            resultVO.setToken(resultDTO.getToken());
        }
        return resultVO;
    }

    @PostMapping("/findPassword/changePassword")
    @ApiOperation(value = "用户账户密码找回密码修改")
    public SimpleResultVO changePasswordForFindPassword(@RequestBody FindPasswordVO findPasswordVO) {
        SimpleResultVO resultVO = new SimpleResultVO();
        String errorMessage = userService.changePasswordForFindPassword(findPasswordVO.getToken(), findPasswordVO.getPassword());
        if (errorMessage != null) {
            resultVO.isSuccess = false;
            resultVO.errorMessage = errorMessage;
        }
        return resultVO;
    }

}
