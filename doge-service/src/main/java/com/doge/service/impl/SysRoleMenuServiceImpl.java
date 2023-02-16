package com.doge.service.impl;

import com.doge.dao.entity.SysRoleMenuDO;
import com.doge.dao.mapper.SysRoleMenuMapper;
import com.doge.service.entity.SysRoleMenuDTO;
import com.doge.service.SysRoleMenuService;
import com.doge.utils.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色菜单关联实现类
 *
 * @author shixinyu
 * @date 2021-07-29 14:53
 */
@Service
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuMapper, SysRoleMenuDO, SysRoleMenuDTO> implements SysRoleMenuService {
    
    @Override
    public Boolean removeByRoleId(Integer roleId) {
        return baseMapper.removeByRoleId(roleId);
    }

    @Override
    public List<SysRoleMenuDTO> getListByRoleId(Integer roleId) {
        return BeanUtils.mapAsList(baseMapper.getListByRoleId(roleId),SysRoleMenuDTO.class);
    }
}