package com.doge.service.impl;

import com.doge.dao.entity.SysUserRoleDO;
import com.doge.dao.mapper.SysUserRoleMapper;
import com.doge.service.entity.SysUserRoleDTO;
import com.doge.service.SysUserRoleService;
import com.doge.utils.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色关联实现类
 *
 * @author shixinyu
 * @date 2021-07-26 16:26
 */
@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleMapper, SysUserRoleDO, SysUserRoleDTO> implements SysUserRoleService {
    @Override
    public Boolean removeByUserId(Integer userId) {
        return baseMapper.removeByUserId(userId);
    }

    @Override
    public List<SysUserRoleDTO> getListByUserId(Integer userId) {
        return BeanUtils.mapAsList(baseMapper.getListByUserId(userId), SysUserRoleDTO.class);
    }
}
