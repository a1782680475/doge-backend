package com.doge.service;

import com.doge.service.common.MessageResult;
import com.doge.service.entity.*;

import java.util.List;
import java.util.Map;

/**
 * 用户相关服务
 *
 * @author shixinyu
 * @date 2021-06-09 10:45
 */
public interface SysUserService extends BaseService<SysUserDTO> {
    /**
     * 根据用户名查询用户登录信息
     *
     * @param username 用户名
     * @return com.doge.entity.UserDTO 用户登录信息
     */
    SysUserDTO getUserByUsername(String username);

    /**
     * 用户注册
     *
     * @param userRegisterInfoDTO 用户注册信息
     * @return com.doge.common.MessageResult<java.lang.String>
     */
    MessageResult<String> register(SysUserRegisterInfoDTO userRegisterInfoDTO);

    /**
     * 分页获取用户列表
     *
     * @param page         分页信息
     * @param userQueryDTO 用户查询信息
     * @param sorter       用户排序信息
     * @return com.doge.service.entity.AntPageDTO<com.doge.service.entity.SysUserDTO>
     */
    AntPageDTO<SysUserDTO> getPageList(PageDTO page, SysUserQueryDTO userQueryDTO, Map<String, String> sorter);

    /**
     * 账户启用
     *
     * @param id 用户id
     * @return java.lang.Boolean
     */
    Boolean enable(Integer id);

    /**
     * 账户禁用
     *
     * @param id 用户id
     * @return java.lang.Boolean
     */
    Boolean disable(Integer id);

    /**
     * 账户批量启用
     *
     * @param ids 用户id集合
     * @return java.lang.Boolean
     */
    Boolean enableBatch(List<Integer> ids);

    /**
     * 账户批量禁用
     *
     * @param ids 用户id集合
     * @return java.lang.Boolean
     */
    Boolean disableBatch(List<Integer> ids);

    /**
     * 账户角色设置
     *
     * @param id      用户id
     * @param roleIds 角色id集合
     * @return void
     */
    Boolean settingRoles(Integer id, List<Integer> roleIds);

    /**
     * 用户角色获取
     *
     * @param id 用户id
     * @return java.util.List<com.doge.entity.SysRoleDTO> 用户角色集合
     */
    List<SysRoleDTO> getRoleList(Integer id);

    /**
     * 获取用户拥有的的菜单和权限
     *
     * @param id 用户id
     * @return java.util.List<com.doge.service.entity.AntPageDTO < com.doge.service.entity.SysMenuDTO> 菜单权限集合
     */
    List<SysMenuDTO> getMenuList(Integer id);

    /**
     * 个人资料更新
     *
     * @param id       用户id
     * @param userInfo 个人资料
     * @return java.lang.Boolean
     */
    Boolean updateInfo(Integer id, SysUserInfoDTO userInfo);
}