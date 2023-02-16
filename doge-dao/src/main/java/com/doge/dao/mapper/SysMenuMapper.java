package com.doge.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doge.dao.entity.SysMenuDO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 菜单表Mapper
 *
 * @author shixinyu
 * @date 2021-07-01 16:53
 */
public interface SysMenuMapper extends BaseMapper<SysMenuDO> {
    /**
     * 分页查询菜单列表
     *
     * @param page   分页
     * @param menuDO 查询信息
     * @param sorter 菜单排序信息
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.doge.dao.entity.MenuDO> 带有分页信息的菜单信息
     */
    IPage<SysMenuDO> selectListByPage(Page<SysMenuDO> page, @Param("query") SysMenuDO menuDO, @Param("sorter") Map<String, String> sorter);
}
