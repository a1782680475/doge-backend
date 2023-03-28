package com.doge.controller.user;

import com.doge.entity.vo.request.FindPasswordVerifyCodeVO;
import com.doge.entity.vo.request.SysUserBindEmailInfoVO;
import com.doge.entity.vo.request.SysUserInfoVO;
import com.doge.entity.vo.response.SimpleResultVO;
import com.doge.entity.vo.response.SimpleTokenResultVO;
import com.doge.entity.vo.response.SysUserSecurityInfoVO;
import com.doge.service.entity.SimpleTokenResultDTO;
import com.doge.service.entity.SysUserInfoDTO;
import com.doge.entity.vo.response.SysUserVO;
import com.doge.service.SysUserService;
import com.doge.utils.BeanUtils;
import com.doge.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 用户相关业务
 *
 * @author shixinyu
 * @date 2021-09-29 11:34
 */
@RestController
@RequestMapping("/user")
@Api(value = "UserController", tags = "用户相关业务")
public class UserController {
    SysUserService userService;

    @Autowired
    UserController(SysUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    @ApiOperation(value = "用户信息获取")
    public SysUserVO get() {
        int userId = SecurityUtils.getUserId();
        return BeanUtils.map(userService.getById(userId), SysUserVO.class);
    }

    @PutMapping("/info")
    @ApiOperation(value = "个人资料更新")
    public void info(@RequestBody SysUserInfoVO sysUserInfoVO) {
        SysUserInfoDTO sysUserInfoDTO = BeanUtils.map(sysUserInfoVO, SysUserInfoDTO.class);
        sysUserInfoDTO.setUpdateTime(new Date());
        int userId = SecurityUtils.getUserId();
        userService.updateInfo(userId, sysUserInfoDTO);
    }

    @GetMapping("/securityInfo")
    @ApiOperation(value = "用户安全绑定信息获取")
    public SysUserSecurityInfoVO securityInfo() {
        int userId = SecurityUtils.getUserId();
        return BeanUtils.map(userService.getById(userId), SysUserSecurityInfoVO.class);
    }

    @PutMapping("/bindEmailVerify/sendEmail/{email}")
    @ApiOperation(value = "用户账户绑定邮箱邮件发送")
    public SimpleResultVO sendBindEmailCode(@PathVariable String email) {
        SimpleResultVO resultVO = new SimpleResultVO();
        int userId = SecurityUtils.getUserId();
        String errorMessage = userService.sendBindEmailCode(userId, email);
        if (errorMessage != null) {
            resultVO.isSuccess = false;
            resultVO.errorMessage = errorMessage;
        }
        return resultVO;
    }

    @PutMapping("/changeEmail/sendVerificationCode")
    @ApiOperation(value = "用户账户绑定邮箱修改邮箱验证码发送")
    public SimpleResultVO sendVerificationCodeForChangeEmail() {
        SimpleResultVO resultVO = new SimpleResultVO();
        int userId = SecurityUtils.getUserId();
        String errorMessage = userService.sendVerificationCodeForChangeEmail(userId);
        if (errorMessage != null) {
            resultVO.isSuccess = false;
            resultVO.errorMessage = errorMessage;
        }
        return resultVO;
    }

    @PostMapping("/changeEmail/verificationCodeVerify")
    @ApiOperation(value = "用户账户绑定邮箱修改邮箱验证码验证")
    public SimpleResultVO verifyCodeForChangeEmail(@RequestBody String code) {
        SimpleResultVO resultVO = new SimpleResultVO();
        int userId = SecurityUtils.getUserId();
        String errorMessage = userService.verifyCodeForChangeEmail(userId, code);
        if (errorMessage != null) {
            resultVO.isSuccess = false;
            resultVO.errorMessage = errorMessage;
        }
        return resultVO;
    }

    @PutMapping("/changePassword/sendVerificationCode")
    @ApiOperation(value = "用户账户密码修改邮箱验证码发送")
    public SimpleResultVO sendVerificationCodeForChangePassword() {
        SimpleResultVO resultVO = new SimpleResultVO();
        int userId = SecurityUtils.getUserId();
        String errorMessage = userService.sendVerificationCodeForChangePassword(userId);
        if (errorMessage != null) {
            resultVO.isSuccess = false;
            resultVO.errorMessage = errorMessage;
        }
        return resultVO;
    }

    @PostMapping("/changePassword/verificationCodeVerify")
    @ApiOperation(value = "用户账户密码修改邮箱验证码验证")
    public SimpleResultVO verifyCodeForChangePassword(@RequestBody String code) {
        SimpleResultVO resultVO = new SimpleResultVO();
        int userId = SecurityUtils.getUserId();
        String errorMessage = userService.verifyCodeForChangePassword(userId, code);
        if (errorMessage != null) {
            resultVO.isSuccess = false;
            resultVO.errorMessage = errorMessage;
        }
        return resultVO;
    }

    @PostMapping("/changePassword")
    @ApiOperation(value = "用户账户密码修改")
    public SimpleResultVO changePassword(@RequestBody String password) {
        SimpleResultVO resultVO = new SimpleResultVO();
        int userId = SecurityUtils.getUserId();
        String errorMessage = userService.changePassword(userId, password);
        if (errorMessage != null) {
            resultVO.isSuccess = false;
            resultVO.errorMessage = errorMessage;
        }
        return resultVO;
    }

}
