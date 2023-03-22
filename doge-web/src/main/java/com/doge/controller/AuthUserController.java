package com.doge.controller;

import com.alibaba.fastjson.JSON;
import com.doge.entity.vo.request.SysUserRegisterInfoVO;
import com.doge.entity.vo.response.*;
import com.doge.service.common.MessageResult;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
