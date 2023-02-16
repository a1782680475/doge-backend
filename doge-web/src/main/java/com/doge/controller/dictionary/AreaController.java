package com.doge.controller.dictionary;

import com.doge.entity.vo.response.AreaVO;
import com.doge.entity.vo.response.CityVO;
import com.doge.entity.vo.response.AntSelectVO;
import com.doge.entity.vo.response.ProvinceVO;
import com.doge.service.AreaService;
import com.doge.service.CityService;
import com.doge.service.ProvinceService;
import com.doge.utils.AntSelectorUtils;
import com.doge.utils.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 行政区划相关业务
 *
 * @author shixinyu
 * @date 2021-09-30 09:04
 */
@RestController
@RequestMapping("/area")
@NoArgsConstructor
@Api(value = "AreaController", tags = "行政区划数据获取")
public class AreaController {
    private ProvinceService provinceService;
    private CityService cityService;
    private AreaService areaService;
    @Autowired
    AreaController(ProvinceService provinceService,CityService cityService,AreaService areaService){
        this.provinceService = provinceService;
        this.cityService = cityService;
        this.areaService = areaService;
    }

    @GetMapping("/provinceList")
    @ApiOperation(value = "行政区域省份获取")
    public List<AntSelectVO> provinceList() {
      return AntSelectorUtils.buildProvinceSelector(BeanUtils.mapAsList(provinceService.getProvinceList(), ProvinceVO.class));
    }

    @GetMapping("/cityList")
    @ApiOperation(value = "行政区域地州市获取")
    public List<AntSelectVO> cityList(@RequestParam Integer provinceCode) {
        return AntSelectorUtils.buildCitySelector(BeanUtils.mapAsList(cityService.getCityList(provinceCode), CityVO.class));
    }

    @GetMapping("/areaList")
    @ApiOperation(value = "行政区域区县获取")
    public List<AntSelectVO> areaList(@RequestParam  Integer cityCode) {
        return AntSelectorUtils.buildAreaSelector(BeanUtils.mapAsList(areaService.getAreaList(cityCode), AreaVO.class));
    }
}
