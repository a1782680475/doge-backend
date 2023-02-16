package com.doge.service;

import com.doge.service.entity.AreaDTO;

import java.util.List;

/**
 * 行政区域县区相关服务
 *
 * @author shixinyu
 * @date 2021-09-30 09:43
 */
public interface AreaService {

    /**
     * 行政区域县区获取
     *
     * @param cityCode 地州市行政区划code
     * @return java.util.List<com.doge.dao.entity.AreaDTO>
     */
    List<AreaDTO> getAreaList(Integer cityCode);
}
