package com.doge.utils;

import com.doge.entity.vo.response.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Ant选择器数据构造
 *
 * @author shixinyu
 * @date 2021-07-26 10:40
 */
public class AntSelectorUtils {

    /**
     * 生成角色选择器数据
     *
     * @param allData
     * @return java.util.List<com.doge.entity.vo.response.AntSelectVO>
     */
    public static List<AntSelectVO> buildRoleSelector(List<SysRoleVO> allData) {
        List<AntSelectVO> list = new ArrayList<>();
        for (SysRoleVO sysRoleVO : allData) {
            list.add(new AntSelectVO(sysRoleVO.getId(), sysRoleVO.getRoleName()));
        }
        return list;
    }

    /**
     * 生成省份选择器数据
     *
     * @author shixinyu
     * @date 2021-09-30 10:17
     */
    public static List<AntSelectVO> buildProvinceSelector(List<ProvinceVO> allData) {
        List<AntSelectVO> list = new ArrayList<>();
        for (ProvinceVO provinceVO : allData) {
            list.add(new AntSelectVO(provinceVO.getProvinceCode(), provinceVO.getProvinceName()));
        }
        return list;
    }

    /**
     * 生成地州市选择器数据
     *
     * @author shixinyu
     * @date 2021-09-30 10:19
     */
    public static List<AntSelectVO> buildCitySelector(List<CityVO> allData) {
        List<AntSelectVO> list = new ArrayList<>();
        for (CityVO cityVO : allData) {
            list.add(new AntSelectVO(cityVO.getCityCode(), cityVO.getCityName()));
        }
        return list;
    }

    /**
     * 生成区县选择器数据
     *
     * @author shixinyu
     * @date 2021-09-30 10:21
     */
    public static List<AntSelectVO> buildAreaSelector(List<AreaVO> allData) {
        List<AntSelectVO> list = new ArrayList<>();
        for (AreaVO areaVO : allData) {
            list.add(new AntSelectVO(areaVO.getAreaCode(), areaVO.getAreaName()));
        }
        return list;
    }
}
