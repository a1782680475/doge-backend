package com.doge.service;

import com.doge.service.entity.CityDTO;

import java.util.List;

/**
 * 行政区域地州市相关服务
 *
 * @author shixinyu
 * @date 2021-09-30 09:41
 */
public interface CityService {
    /**
     * 行政区域地州市获取
     *
     * @param provinceCode 省份行政区划code
     * @return java.util.List<com.doge.dao.entity.CityDTO>
     */
    List<CityDTO> getCityList(Integer provinceCode);
}
