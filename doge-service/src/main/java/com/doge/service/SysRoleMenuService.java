package com.doge.service;

import com.doge.service.entity.SysRoleMenuDTO;

import java.util.List;

/**
 * 角色菜单关联服务
 *
 * @author shixinyu
 * @date 2021-07-29 14:47
 */
public interface SysRoleMenuService extends BaseService<SysRoleMenuDTO>{

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
    List<SysRoleMenuDTO> getListByRoleId(Integer roleId);
}
