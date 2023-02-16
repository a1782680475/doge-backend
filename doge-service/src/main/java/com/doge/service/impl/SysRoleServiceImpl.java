package com.doge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doge.dao.entity.SysRoleDO;
import com.doge.dao.mapper.SysRoleMapper;
import com.doge.service.entity.*;
import com.doge.exception.BusinessException;
import com.doge.service.SysRoleMenuService;
import com.doge.service.SysRoleService;
import com.doge.utils.BeanUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色服务实现类
 *
 * @author shixinyu
 * @date 2021-06-29 16:21
 */
@Service
@NoArgsConstructor
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRoleDO, SysRoleDTO> implements SysRoleService {
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    SysRoleServiceImpl(SysRoleMenuService sysRoleMenuService) {
        this.sysRoleMenuService = sysRoleMenuService;
    }

    @Override
    public boolean save(SysRoleDTO sysRoleDTO) {
        if (baseMapper.checkDuplication(sysRoleDTO)) {
            throw new BusinessException("角色编码或角色名称已存在！");
        } else {
            baseMapper.insert(sysRoleDTO);
        }
        return true;
    }

    @Override
    public AntPageDTO<SysRoleDTO> getPageList(PageDTO pageDTO, SysRoleQueryDTO roleQueryDTO, Map<String, String> sorter) {
        Page<SysRoleDO> page = new Page<>(pageDTO.getCurrent(), pageDTO.getPageSize());
        IPage<SysRoleDO> rolePage = baseMapper.selectListByPage(page, BeanUtils.map(roleQueryDTO, SysRoleDO.class), sorter);
        return new AntPageDTO<SysRoleDO>().init(rolePage);
    }

    @Override
    public Boolean roleAuthorize(Integer id, List<Integer> menuIds) {
        sysRoleMenuService.removeByRoleId(id);
        List<SysRoleMenuDTO> sysRoleMenuList = new ArrayList<>();
        for (Integer menuId : menuIds) {
            SysRoleMenuDTO sysRoleMenuDTO = new SysRoleMenuDTO();
            sysRoleMenuDTO.setRoleId(id);
            sysRoleMenuDTO.setMenuId(menuId);
            Date now = new Date();
            sysRoleMenuDTO.setCreateTime(now);
            sysRoleMenuDTO.setUpdateTime(now);
            sysRoleMenuList.add(sysRoleMenuDTO);
        }
        sysRoleMenuService.saveBatch(sysRoleMenuList, sysRoleMenuList.size());
        return true;
    }
}
