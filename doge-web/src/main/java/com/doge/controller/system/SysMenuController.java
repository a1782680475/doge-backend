package com.doge.controller.system;

import com.doge.entity.vo.request.SysMenuAddVO;
import com.doge.entity.vo.response.SysMenuVO;
import com.doge.service.entity.SysMenuDTO;
import com.doge.annotation.Log;
import com.doge.entity.vo.response.AntTableVO;
import com.doge.entity.vo.response.AntTreeSelectVO;
import com.doge.entity.vo.response.AntTreeVO;
import com.doge.service.SysMenuService;
import com.doge.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 菜单管理
 *
 * @author shixinyu
 * @date 2021-06-28 16:34
 */
@RestController
@RequestMapping("/sys/menu")
@Api(value = "SysMenuController", tags = "菜单管理")
public class SysMenuController {
    private SysMenuService sysMenuService;

    SysMenuController() {

    }

    @Autowired
    SysMenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @PreAuthorize("@aps.hasPermission('sys:menu')")
    @GetMapping("/treeList")
    @ApiOperation(value = "菜单列表获取（树状显示，无分页）")
    public AntTableVO treeList() {
        List<SysMenuDTO> menuDTOList = sysMenuService.list();
        return new AntTableVO().build(AntTableTreeUtils.buildMenuTableTree(BeanUtils.mapAsList(menuDTOList, SysMenuVO.class)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "指定菜单获取")
    public SysMenuVO getById(@PathVariable Integer id) {
        return BeanUtils.map(sysMenuService.getById(id),SysMenuVO.class);
    }

    @GetMapping("/treeSelector")
    @ApiOperation(value = "菜单树状列表获取")
    public List<AntTreeSelectVO> treeSelector() {
        List<SysMenuDTO> menuDTOList = sysMenuService.list();
        return AntTreeSelectorUtils.buildMenuTreeSelect(BeanUtils.mapAsList(menuDTOList,SysMenuVO.class));
    }

    @GetMapping("/tree")
    @ApiOperation(value = "菜单树获取")
    public List<AntTreeVO> tree() {
        List<SysMenuDTO> menuDTOList = sysMenuService.list();
        return AntTreeUtils.buildMenuTree(BeanUtils.mapAsList(menuDTOList,SysMenuVO.class));
    }

    @PreAuthorize("@aps.hasPermission('sys:menu:add')")
    @PostMapping("/add")
    @ApiOperation(value = "菜单新增")
    @Log(title = "菜单新增")
    public void add(@RequestBody SysMenuAddVO sysMenuAddVO) {
        SysMenuDTO sysMenuDTO = BeanUtils.map(sysMenuAddVO, SysMenuDTO.class);
        Date now = new Date();
        sysMenuDTO.setCreateBy(SecurityUtils.getUserName());
        sysMenuDTO.setCreateTime(now);
        sysMenuDTO.setUpdateTime(now);
        sysMenuService.save(sysMenuDTO);
    }

    @PreAuthorize("@aps.hasPermission('sys:menu:edit')")
    @PutMapping("/update")
    @ApiOperation(value = "菜单编辑")
    @Log(title = "菜单编辑")
    public void update(@RequestBody SysMenuVO sysMenuUpdateVO) {
        SysMenuDTO sysMenuDTO = BeanUtils.map(sysMenuUpdateVO, SysMenuDTO.class);
        Date now = new Date();
        sysMenuDTO.setCreateBy(SecurityUtils.getUserName());
        sysMenuDTO.setUpdateTime(now);
        sysMenuService.update(sysMenuDTO);
    }

    @PreAuthorize("@aps.hasPermission('sys:menu:delete')")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "菜单删除")
    @Log(title = "菜单删除")
    public void enable(@PathVariable Integer id) {
        sysMenuService.removeById(id);
    }

    @PreAuthorize("@aps.hasPermission('sys:menu:delete')")
    @DeleteMapping("/deleteBatch")
    @ApiOperation(value = "菜单批量删除")
    @Log(title = "菜单批量删除")
    public void deleteBatch(@RequestBody List<Integer> ids) {
        sysMenuService.removeByIds(ids);
    }
}
