package com.doge.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doge.dao.entity.SysMenuDO;
import com.doge.dao.entity.SysRoleDO;
import com.doge.dao.entity.SysUserDO;
import com.doge.dao.updateentity.SysUserInfoBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统用户登录信息表Mapper
 *
 * @author shixinyu
 * @date 2021-06-09 08:36
 */
public interface SysUserMapper extends BaseMapper<SysUserDO> {
    /**
     * 根据用户名查询用户登录信息
     *
     * @param username 登录名
     * @return com.doge.entity.UserDO 用户登录信息
     */
    SysUserDO getUserByUsername(String username);

    /**
     * 分页查询用户列表
     *
     * @param page   分页
     * @param userDO 查询信息
     * @param sorter 用户排序信息
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.doge.dao.entity.UserDO> 带有分页信息的用户信息
     */
    IPage<SysUserDO> selectListByPage(Page<SysUserDO> page, @Param("query") SysUserDO userDO,@Param("sorter") Map<String, String> sorter);

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
    Boolean enableBatch(@Param("ids") List<Integer> ids);

    /**
     * 账户批量禁用
     *
     * @param ids 用户id集合
     * @return java.lang.Boolean
     */
    Boolean disableBatch(@Param("ids") List<Integer> ids);

    /**
     * 用户角色获取
     *
     * @param id 用户id
     * @return java.util.List<com.doge.entity.SysRoleDO> 用户角色集合
     */
    List<SysRoleDO> getRoleList(Integer id);


    /**
     * 获取用户拥有的的菜单和权限
     *
     * @param id 用户id
     * @return java.util.List<com.doge.service.entity.AntPageDTO<com.doge.service.entity.SysMenuDTO> 菜单权限集合
     */
    List<SysMenuDO> getMenuList(Integer id);

    /**
     * 个人资料更新
     *
     * @param id 用户id
     * @param userInfo 个人资料
     * @return java.lang.Boolean
     */
    Boolean updateInfo(@Param("id")Integer id,@Param("info") SysUserInfoBO userInfo);
}
