package com.doge.controller.system;

import com.doge.entity.vo.request.*;
import com.doge.entity.vo.response.AntPageVO;
import com.doge.entity.vo.response.SimpleResultVO;
import com.doge.entity.vo.response.SysUserRoleVO;
import com.doge.service.NoticeService;
import com.doge.service.entity.*;
import com.doge.annotation.Log;
import com.doge.entity.vo.response.SysUserVO;
import com.doge.service.SysUserRoleService;
import com.doge.service.SysUserService;
import com.doge.service.utils.RsaUtils;
import com.doge.utils.BeanUtils;
import com.doge.utils.PageUtils;
import com.doge.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author shixinyu
 * @date 2021-06-21 09:32
 */
@RestController
@RequestMapping("/sys/user")
@Api(value = "SysUserController", tags = "用户管理")
public class SysUserController {
    private SysUserService sysUserService;
    private SysUserRoleService sysUserRoleService;
    private NoticeService noticeService;

    SysUserController() {

    }

    @Autowired
    SysUserController(SysUserService userService, SysUserRoleService sysUserRoleService, NoticeService noticeService) {
        this.sysUserService = userService;
        this.sysUserRoleService = sysUserRoleService;
        this.noticeService = noticeService;
    }

    @PreAuthorize("@aps.hasPermission('sys:user')")
    @GetMapping("/pageList")
    @ApiOperation(value = "用户列表获取")
    public AntPageVO pageList(SysUserQueryVO sysUserQueryVO) {
        PageDTO pageDTO = PageUtils.createPageDTO(sysUserQueryVO);
        Map<String, String> sorter = PageUtils.createSorter(sysUserQueryVO);
        AntPageDTO<SysUserDTO> antPageDTO = sysUserService.getPageList(pageDTO, BeanUtils.map(sysUserQueryVO, SysUserQueryDTO.class), sorter);
        return new AntPageVO().build(antPageDTO, SysUserVO.class);
    }

    @GetMapping("/roleList")
    @ApiOperation(value = "指定用户所属角色列表获取")
    public List<SysUserRoleVO> roleList(Integer id) {
        return BeanUtils.mapAsList(sysUserRoleService.getListByUserId(id), SysUserRoleVO.class);
    }

    @PreAuthorize("@aps.hasPermission('sys:user:add')")
    @PostMapping("/add")
    @ApiOperation(value = "用户新增")
    @Log(title = "用户新增")
    public SimpleResultVO add(@RequestBody SysUserAddVO sysUserAddVO) {
        SimpleResultVO resultVO = new SimpleResultVO();
        SysUserAddDTO sysUserAddDTO = BeanUtils.map(sysUserAddVO, SysUserAddDTO.class);
        String errorMessage = sysUserService.addUser(sysUserAddDTO);
        if (errorMessage != null) {
            resultVO.isSuccess = false;
            resultVO.errorMessage = errorMessage;
        }
        return resultVO;
    }

    @PreAuthorize("@aps.hasPermission('sys:user:enable')")
    @PutMapping("/enable/{id}")
    @ApiOperation(value = "账户启用")
    @Log(title = "账户启用")
    public void enable(@PathVariable Integer id) {
        sysUserService.enable(id);
    }

    @PreAuthorize("@aps.hasPermission('sys:user:disable')")
    @PutMapping("/disable/{id}")
    @ApiOperation(value = "账户禁用")
    @Log(title = "账户禁用")
    public void disable(@PathVariable Integer id) {
        sysUserService.disable(id);
    }

    @PreAuthorize("@aps.hasPermission('sys:user:settingRole')")
    @PutMapping("/settingRole")
    @ApiOperation(value = "账户角色分配")
    @Log(title = "账户角色分配")
    public void settingRole(@RequestBody SysUserRoleSettingVO sysUserRoleSettingVO) {
        sysUserService.settingRoles(sysUserRoleSettingVO.getId(), sysUserRoleSettingVO.getRoleIds());
    }

    @PreAuthorize("@aps.hasPermission('sys:user:enable')")
    @PutMapping("/enableBatch")
    @ApiOperation(value = "账户批量启用")
    @Log(title = "账户批量启用")
    public void enableBatch(@RequestBody List<Integer> ids) {
        sysUserService.enableBatch(ids);
    }

    @PreAuthorize("@aps.hasPermission('sys:user:disable')")
    @PutMapping("/disableBatch")
    @ApiOperation(value = "账户批量禁用")
    @Log(title = "账户批量禁用")
    public void disableBatch(@RequestBody List<Integer> ids) {
        sysUserService.disableBatch(ids);
    }

    @PreAuthorize("@aps.hasPermission('sys:user:resetPassword')")
    @PostMapping("/resetPassword")
    @ApiOperation(value = "账户密码重置")
    @Log(title = "账户密码重置")
    public SimpleResultVO resetPassword(@RequestBody SysUserPasswordInfo sysUserPasswordInfo) {
        SimpleResultVO resultVO = new SimpleResultVO();
        int userId = sysUserPasswordInfo.getId();
        String errorMessage = sysUserService.changePassword(userId, sysUserPasswordInfo.getPassword(), PasswordChangeTypeEnum.RESET);
        if (errorMessage != null) {
            resultVO.isSuccess = false;
            resultVO.errorMessage = errorMessage;
        }
        return resultVO;
    }
}
