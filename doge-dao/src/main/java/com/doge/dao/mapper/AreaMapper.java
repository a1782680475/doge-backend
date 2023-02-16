package com.doge.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doge.dao.entity.AreaDO;

import java.util.List;

/**
 * 行政区域县区Mapper
 *
 * @author shixinyu
 * @date 2021-09-30 09:22
 */
public interface AreaMapper extends BaseMapper<AreaDO> {
    /**
     * 行政区域县区获取
     *
     * @param cityCode 地州市行政区划code
     * @return java.util.List<com.doge.dao.entity.AreaDO>
     */
    List<AreaDO> getAreaList(Integer cityCode);
}
