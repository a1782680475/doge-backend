package com.doge.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doge.dao.entity.CityDO;

import java.util.List;

/**
 * 行政区域地州市Mapper
 *
 * @author shixinyu
 * @date 2021-09-30 09:19
 */
public interface CityMapper extends BaseMapper<CityDO> {
    /**
     * 行政区域地州市获取
     * 
     * @param provinceCode 省份行政区划code
     * @return java.util.List<com.doge.dao.entity.CityDO>
     */
    List<CityDO> getCityList(Integer provinceCode);
}
