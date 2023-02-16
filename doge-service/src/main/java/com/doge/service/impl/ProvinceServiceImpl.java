package com.doge.service.impl;

import com.doge.dao.entity.ProvinceDO;
import com.doge.dao.mapper.ProvinceMapper;
import com.doge.service.entity.ProvinceDTO;
import com.doge.service.ProvinceService;
import com.doge.utils.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 行政区域省份服务实现类
 *
 * @author shixinyu
 * @date 2021-09-30 09:51
 */
@Service
public class ProvinceServiceImpl extends BaseServiceImpl<ProvinceMapper,ProvinceDO, ProvinceDTO> implements ProvinceService {
    @Override
    public List<ProvinceDTO> getProvinceList() {
        return BeanUtils.mapAsList(baseMapper.getProvinceList(),ProvinceDTO.class);
    }
}
