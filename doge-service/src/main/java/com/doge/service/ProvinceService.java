package com.doge.service;

import com.doge.service.entity.ProvinceDTO;

import java.util.List;

/**
 * 行政区域省份相关服务
 *
 * @author shixinyu
 * @date 2021-09-30 09:40
 */
public interface ProvinceService {
    /**
     * 行政区域省份获取
     *
     * @return java.util.List<com.doge.dao.entity.ProvinceDTO>
     */
    List<ProvinceDTO> getProvinceList();
}
