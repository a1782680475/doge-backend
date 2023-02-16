package com.doge.controller.user;

import com.doge.entity.vo.request.SysUserInfoVO;
import com.doge.service.entity.SysUserInfoDTO;
import com.doge.entity.vo.response.SysUserVO;
import com.doge.service.SysUserService;
import com.doge.utils.BeanUtils;
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

    /**
     * 用户信息获取
     *
     * @param
     * @return java.lang.String
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "用户信息获取")
    public SysUserVO get(@PathVariable Integer id) {
        return BeanUtils.map(userService.getById(id), SysUserVO.class);
    }

    /**
     * 个人资料更新
     *
     * @author shixinyu
     * @date 2021-10-08 14:15
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "个人资料更新")
    public void info(@PathVariable Integer id, @RequestBody SysUserInfoVO sysUserInfoVO) {
        SysUserInfoDTO sysUserInfoDTO = BeanUtils.map(sysUserInfoVO, SysUserInfoDTO.class);
        sysUserInfoDTO.setUpdateTime(new Date());
        userService.updateInfo(id, sysUserInfoDTO);
    }
}
