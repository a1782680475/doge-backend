package com.doge.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doge.dao.entity.ProvinceDO;

import java.util.List;

/**
 * 行政区域省份Mapper
 *
 * @author shixinyu
 * @date 2021-09-30 09:14
 */
public interface ProvinceMapper extends BaseMapper<ProvinceDO> {
    /**
     * 行政区域省份获取
     *
     * @return java.util.List<com.doge.dao.entity.ProvinceDO>
     */
    List<ProvinceDO> getProvinceList();
}
