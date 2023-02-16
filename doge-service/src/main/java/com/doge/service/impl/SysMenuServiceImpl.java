package com.doge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doge.dao.entity.SysMenuDO;
import com.doge.dao.mapper.SysMenuMapper;
import com.doge.service.entity.AntPageDTO;
import com.doge.service.entity.PageDTO;
import com.doge.service.entity.SysMenuDTO;
import com.doge.service.entity.SysMenuQueryDTO;
import com.doge.service.SysMenuService;
import com.doge.utils.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 菜单服务实现类
 *
 * @author shixinyu
 * @date 2021-07-01 17:07
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenuDO, SysMenuDTO> implements SysMenuService {
    private SysMenuDTO getMenuDtoByType(SysMenuDTO sysMenuDTO){
        switch (sysMenuDTO.getType()){
            case 1:
                sysMenuDTO.setPid(0);
                break;
            case 2:
                sysMenuDTO.setIcon(null);
                break;
            case 3:
                sysMenuDTO.setPath(null);
                sysMenuDTO.setRedirect(null);
                sysMenuDTO.setFrame(null);
                sysMenuDTO.setCache(null);
                sysMenuDTO.setIcon(null);
                break;
            default:
                break;
        }
        return sysMenuDTO;
    }
    @Override
    public AntPageDTO<SysMenuDTO> getPageList(PageDTO pageDTO, SysMenuQueryDTO menuQueryDTO, Map<String, String> sorter) {
        Page<SysMenuDO> page = new Page<>(pageDTO.getCurrent(), pageDTO.getPageSize());
        IPage<SysMenuDO> menuPage = baseMapper.selectListByPage(page, BeanUtils.map(menuQueryDTO, SysMenuDO.class), sorter);
        return new AntPageDTO<SysMenuDO>().init(menuPage);
    }
    @Override
    public boolean save(SysMenuDTO sysMenuDTO){
        super.save(getMenuDtoByType(sysMenuDTO));
        return true;
    }

    @Override
    public boolean update(SysMenuDTO sysMenuDTO){
        super.update(getMenuDtoByType(sysMenuDTO));
        return true;
    }
}
