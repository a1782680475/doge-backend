package com.doge.service;

import com.doge.service.entity.SysUserRoleDTO;

import java.util.List;

/**
 * 用户角色关联服务
 *
 * @author shixinyu
 * @date 2021-07-26 16:00
 */
public interface SysUserRoleService extends BaseService<SysUserRoleDTO>{

    /**
     * 删除指定用户的用户角色关联
     *
     * @param userId 用户id
     * @return java.lang.Boolean
     */
    Boolean removeByUserId(Integer userId);

    /**
     * 获取指定用户的用户角色关联信息
     *
     * @param userId 用户id
     * @return java.util.List<com.doge.service.entity.SysUserRoleDTO>
     */
    List<SysUserRoleDTO> getListByUserId(Integer userId);
}
