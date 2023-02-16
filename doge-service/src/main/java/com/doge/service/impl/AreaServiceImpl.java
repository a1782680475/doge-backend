package com.doge.service.impl;

import com.doge.dao.entity.AreaDO;
import com.doge.dao.mapper.AreaMapper;
import com.doge.service.entity.AreaDTO;
import com.doge.service.AreaService;
import com.doge.utils.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 行政区域县区服务实现类
 *
 * @author shixinyu
 * @date 2021-09-30 09:58
 */
@Service
public class AreaServiceImpl extends BaseServiceImpl<AreaMapper, AreaDO, AreaDTO> implements AreaService {

    @Override
    public List<AreaDTO> getAreaList(Integer cityCode) {
        return BeanUtils.mapAsList(baseMapper.getAreaList(cityCode),AreaDTO.class);
    }
}
