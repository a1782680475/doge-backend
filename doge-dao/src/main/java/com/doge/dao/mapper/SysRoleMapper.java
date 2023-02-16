package com.doge.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doge.dao.entity.SysRoleDO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 系统角色信息表Mapper
 *
 * @author shixinyu
 * @date 2021-06-09 08:36
 */
public interface SysRoleMapper extends BaseMapper<SysRoleDO> {
    /**
     * 分页查询角色列表
     *
     * @param page   分页
     * @param roleDO 查询信息
     * @param sorter 用户排序信息
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.doge.dao.entity.RoleDO> 带有分页信息的用户信息
     */
    IPage<SysRoleDO> selectListByPage(Page<SysRoleDO> page, @Param("query") SysRoleDO roleDO, @Param("sorter") Map<String, String> sorter);

    /**
     * 角色重复性查询
     * 
     * @param sysRoleDO
     * @return java.lang.Boolean
     */
    Boolean checkDuplication(@Param("query") SysRoleDO sysRoleDO);
}
