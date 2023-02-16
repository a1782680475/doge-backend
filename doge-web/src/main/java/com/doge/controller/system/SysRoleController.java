package com.doge.controller.system;

import com.doge.entity.vo.request.SysRoleAddVO;
import com.doge.entity.vo.request.SysRoleAuthorizeVO;
import com.doge.entity.vo.request.SysRoleQueryVO;
import com.doge.entity.vo.request.SysRoleUpdateVO;
import com.doge.entity.vo.response.AntPageVO;
import com.doge.entity.vo.response.SysRoleMenuVO;
import com.doge.entity.vo.response.SysRoleVO;
import com.doge.service.entity.AntPageDTO;
import com.doge.service.entity.PageDTO;
import com.doge.service.entity.SysRoleDTO;
import com.doge.service.entity.SysRoleQueryDTO;
import com.doge.annotation.Log;
import com.doge.entity.vo.response.AntSelectVO;
import com.doge.service.SysRoleMenuService;
import com.doge.service.SysRoleService;
import com.doge.utils.BeanUtils;
import com.doge.utils.PageUtils;
import com.doge.utils.SecurityUtils;
import com.doge.utils.AntSelectorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author shixinyu
 * @date 2021-06-28 16:34
 */
@RestController
@RequestMapping("/sys/role")
@Api(value = "SysRoleController", tags = "角色管理")
public class SysRoleController {
    private SysRoleService sysRoleService;
    private SysRoleMenuService sysRoleMenuService;

    SysRoleController() {

    }

    @Autowired
    SysRoleController(SysRoleService sysRoleService, SysRoleMenuService sysRoleMenuService) {
        this.sysRoleService = sysRoleService;
        this.sysRoleMenuService = sysRoleMenuService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "指定角色获取")
    public SysRoleVO getById(@PathVariable Integer id){
        return BeanUtils.map(sysRoleService.getById(id),SysRoleVO.class);
    }

    @PreAuthorize("@aps.hasPermission('sys:role')")
    @GetMapping("/pageList")
    @ApiOperation(value = "角色列表获取")
    public AntPageVO pageList(SysRoleQueryVO sysRoleQueryVO) {
        PageDTO pageDTO = PageUtils.createPageDTO(sysRoleQueryVO);
        Map<String, String> sorter = PageUtils.createSorter(sysRoleQueryVO);
        AntPageDTO<SysRoleDTO> antPageDTO = sysRoleService.getPageList(pageDTO, BeanUtils.map(sysRoleQueryVO, SysRoleQueryDTO.class), sorter);
        return new AntPageVO().build(antPageDTO, SysRoleVO.class);
    }

    @GetMapping("/selector")
    @ApiOperation(value = "选择器数据列表获取")
    public List<AntSelectVO> selector() {
        return AntSelectorUtils.buildRoleSelector(BeanUtils.mapAsList(sysRoleService.list(),SysRoleVO.class));
    }

    @GetMapping("/menuList")
    @ApiOperation(value = "指定角色页面权限列表获取")
    public List<SysRoleMenuVO> roleList(Integer id) {
        return BeanUtils.mapAsList(sysRoleMenuService.getListByRoleId(id),SysRoleMenuVO.class);
    }

    @PreAuthorize("@aps.hasPermission('sys:role:add')")
    @PostMapping("/add")
    @ApiOperation(value = "角色新增")
    @Log(title = "角色新增")
    public void add(@RequestBody SysRoleAddVO sysRoleAddVO) {
        SysRoleDTO sysRoleDTO = BeanUtils.map(sysRoleAddVO, SysRoleDTO.class);
        Date now = new Date();
        sysRoleDTO.setCreateBy(SecurityUtils.getUserName());
        sysRoleDTO.setCreateTime(now);
        sysRoleDTO.setUpdateTime(now);
        sysRoleService.save(sysRoleDTO);
    }

    @PreAuthorize("@aps.hasPermission('sys:role:update')")
    @PutMapping("/update")
    @ApiOperation(value = "角色编辑")
    @Log(title = "角色编辑")
    public void update(@RequestBody SysRoleUpdateVO sysRoleUpdateVO) {
        SysRoleDTO sysRoleDTO = BeanUtils.map(sysRoleUpdateVO, SysRoleDTO.class);
        Date now = new Date();
        sysRoleDTO.setCreateBy(SecurityUtils.getUserName());
        sysRoleDTO.setUpdateTime(now);
        sysRoleService.update(sysRoleDTO);
    }

    @PreAuthorize("@aps.hasPermission('sys:role:delete')")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "角色删除")
    @Log(title = "角色删除")
    public void enable(@PathVariable Integer id) {
        sysRoleService.removeById(id);
    }

    @PreAuthorize("@aps.hasPermission('sys:role:delete')")
    @DeleteMapping("/deleteBatch")
    @ApiOperation(value = "角色批量删除")
    @Log(title = "角色批量删除")
    public void deleteBatch(@RequestBody List<Integer> ids) {
        sysRoleService.removeByIds(ids);
    }

    @PreAuthorize("@aps.hasPermission('sys:role:roleAuthorize')")
    @PutMapping("/roleAuthorize")
    @ApiOperation(value = "角色授权")
    @Log(title = "角色授权")
    public void roleAuthorize(@RequestBody SysRoleAuthorizeVO sysRoleAuthorizeVO) {
        sysRoleService.roleAuthorize(sysRoleAuthorizeVO.getId(),sysRoleAuthorizeVO.getMenuIds());
    }
}
