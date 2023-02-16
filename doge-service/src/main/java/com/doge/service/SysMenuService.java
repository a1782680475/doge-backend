package com.doge.service;

import com.doge.service.entity.AntPageDTO;
import com.doge.service.entity.PageDTO;
import com.doge.service.entity.SysMenuDTO;
import com.doge.service.entity.SysMenuQueryDTO;

import java.util.Map;

/**
 * 菜单相关服务
 *
 * @author shixinyu
 * @date 2021-06-29 16:21
 */
public interface SysMenuService extends BaseService<SysMenuDTO> {
    /**
     * 分页获取菜单列表
     *
     * @param page         分页信息
     * @param menuQueryDTO 菜单查询信息
     * @param sorter       用户排序信息
     * @return com.doge.service.entity.AntPageDTO<com.doge.service.entity.SysMenuDTO>
     */
    AntPageDTO<SysMenuDTO> getPageList(PageDTO page, SysMenuQueryDTO menuQueryDTO, Map<String, String> sorter);
}
