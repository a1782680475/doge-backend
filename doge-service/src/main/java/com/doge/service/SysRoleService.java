package com.doge.service;

import com.doge.service.entity.AntPageDTO;
import com.doge.service.entity.PageDTO;
import com.doge.service.entity.SysRoleDTO;
import com.doge.service.entity.SysRoleQueryDTO;

import java.util.List;
import java.util.Map;

/**
 * 角色相关服务
 *
 * @author shixinyu
 * @date 2021-06-29 16:21
 */
public interface SysRoleService extends BaseService<SysRoleDTO> {
    /**
     * 角色保存（添加重复性校验）
     *
     * @param sysRoleDTO 新增信息
     * @return boolean
     */
    @Override
    boolean save(SysRoleDTO sysRoleDTO);

    /**
     * 分页获取角色列表
     *
     * @param page         分页信息
     * @param roleQueryDTO 角色查询信息
     * @param sorter       用户排序信息
     * @return com.doge.service.entity.AntPageDTO<com.doge.service.entity.SysRoleDTO>
     */
    AntPageDTO<SysRoleDTO> getPageList(PageDTO page, SysRoleQueryDTO roleQueryDTO, Map<String, String> sorter);

    /**
     * 角色权限设置
     *
     * @param id 用户id
     * @param menuIds 页面权限id集合
     * @return void
     */
    Boolean roleAuthorize(Integer id, List<Integer> menuIds);
}
