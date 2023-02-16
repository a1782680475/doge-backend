package com.doge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doge.dao.entity.SysUserDO;
import com.doge.dao.mapper.SysUserMapper;
import com.doge.dao.updateentity.SysUserInfoBO;
import com.doge.service.common.MessageResult;
import com.doge.service.entity.*;
import com.doge.service.SysUserRoleService;
import com.doge.service.SysUserService;
import com.doge.utils.BeanUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户服务实现类
 *
 * @author shixinyu
 * @date 2021-06-09 10:44
 */
@Service
@NoArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUserDO, SysUserDTO> implements SysUserService {

    private SysUserRoleService sysUserRoleService;

    @Autowired
    SysUserServiceImpl(SysUserRoleService sysUserRoleService){
        this.sysUserRoleService = sysUserRoleService;
    }

    @Override
    public SysUserDTO getUserByUsername(String username) {
        return BeanUtils.map(baseMapper.getUserByUsername(username), SysUserDTO.class);
    }

    @Override
    public MessageResult<String> register(SysUserRegisterInfoDTO userRegisterInfoDTO) {
        Boolean success = true;
        String message;
        QueryWrapper<SysUserDO> wrapper = new QueryWrapper();
        wrapper.eq("username", userRegisterInfoDTO.getUsername());
        if (baseMapper.selectOne(wrapper) != null) {
            success = false;
            message = "用户名已存在";
        } else {
            SysUserDO user = new SysUserDO();
            user.setUsername(userRegisterInfoDTO.getUsername());
            user.setPassword(new BCryptPasswordEncoder().encode(userRegisterInfoDTO.getPassword()));
            user.setEnabled(true);
            if (baseMapper.insert(user) == 1) {
                message = "注册成功";
            } else {
                success = false;
                message = "未知错误";
            }
        }
        return new MessageResult(success, message);
    }

    @Override
    public AntPageDTO<SysUserDTO> getPageList(PageDTO pageDTO, SysUserQueryDTO userQueryDTO, Map<String, String> sorter) {
        Page<SysUserDO> page = new Page<>(pageDTO.getCurrent(),pageDTO.getPageSize());
        IPage<SysUserDO> userPage = baseMapper.selectListByPage(page, BeanUtils.map(userQueryDTO, SysUserDO.class),sorter);
        return new AntPageDTO<SysUserDO>().init(userPage);
    }

    @Override
    public Boolean enable(Integer id) {
        baseMapper.enable(id);
        return true;
    }

    @Override
    public Boolean disable(Integer id) {
        baseMapper.disable(id);
        return true;
    }

    @Override
    public Boolean enableBatch(List<Integer> ids) {
        baseMapper.enableBatch(ids);
        return true;
    }

    @Override
    public Boolean disableBatch(List<Integer> ids) {
        baseMapper.disableBatch(ids);
        return true;
    }

    @Override
    public Boolean settingRoles(Integer id, List<Integer> roleIds) {
        sysUserRoleService.removeByUserId(id);
        List<SysUserRoleDTO> sysUserRoleList = new ArrayList<>();
        for(Integer roleId : roleIds) {
            SysUserRoleDTO sysUserRoleDTO = new SysUserRoleDTO();
            sysUserRoleDTO.setUserId(id);
            sysUserRoleDTO.setRoleId(roleId);
            Date now = new Date();
            sysUserRoleDTO.setCreateTime(now);
            sysUserRoleDTO.setUpdateTime(now);
            sysUserRoleList.add(sysUserRoleDTO);
        }
        sysUserRoleService.saveBatch(sysUserRoleList,sysUserRoleList.size());
        return true;
    }

    @Override
    public List<SysRoleDTO> getRoleList(Integer id) {
        return BeanUtils.mapAsList(baseMapper.getRoleList(id),SysRoleDTO.class);
    }

    @Override
    public List<SysMenuDTO> getMenuList(Integer id) {
       return BeanUtils.mapAsList(baseMapper.getMenuList(id),SysMenuDTO.class);
    }

    @Override
    public Boolean updateInfo(Integer id, SysUserInfoDTO userInfo) {
        return baseMapper.updateInfo(id,BeanUtils.map(userInfo, SysUserInfoBO.class));
    }
}
