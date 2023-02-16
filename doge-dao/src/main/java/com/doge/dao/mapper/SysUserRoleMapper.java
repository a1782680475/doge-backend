package com.doge.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doge.dao.entity.SysUserRoleDO;

import java.util.List;

/**
 * 用户角色关联表Mapper
 *
 * @author shixinyu
 * @date 2021-07-26 16:02
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleDO> {
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
     * @return java.util.List<com.doge.dao.entity.SysUserRoleDO>
     */
    List<SysUserRoleDO> getListByUserId(Integer userId);
}
