package com.doge.service.impl;

import com.doge.dao.entity.CityDO;
import com.doge.dao.mapper.CityMapper;
import com.doge.service.entity.CityDTO;
import com.doge.service.CityService;
import com.doge.utils.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 行政区域地州市服务实现类
 *
 * @author shixinyu
 * @date 2021-09-30 09:55
 */
@Service
public class CityServiceImpl extends BaseServiceImpl<CityMapper, CityDO, CityDTO> implements CityService {
    @Override
    public List<CityDTO> getCityList(Integer provinceCode) {
        return BeanUtils.mapAsList(baseMapper.getCityList(provinceCode),CityDTO.class);
    }
}
