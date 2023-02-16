package com.doge.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doge.dao.entity.SysRoleMenuDO;

import java.util.List;

/**
 * 角色菜单关联表Mapper
 *
 * @author shixinyu
 * @date 2021-07-29 14:52
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuDO> {

    /**
     * 删除指定角色的角色菜单关联
     *
     * @param roleId 角色id
     * @return java.lang.Boolean
     */
    Boolean removeByRoleId(Integer roleId);

    /**
     * 获取指定角色的角色菜单关联信息
     *
     * @param roleId 角色id
     * @return java.util.List<com.doge.service.entity.SysRoleMenuDTO>
     */
    List<SysRoleMenuDO> getListByRoleId(Integer roleId);
}
