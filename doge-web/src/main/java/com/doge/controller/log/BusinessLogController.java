package com.doge.controller.log;

import com.doge.entity.vo.request.SysBusinessLogQueryVO;
import com.doge.entity.vo.response.AntPageVO;
import com.doge.entity.vo.response.BusinessLogVO;
import com.doge.service.entity.AntPageDTO;
import com.doge.service.entity.BusinessLogDTO;
import com.doge.service.entity.BusinessLogQueryDTO;
import com.doge.service.entity.PageDTO;
import com.doge.annotation.Log;
import com.doge.service.BusinessLogService;
import com.doge.utils.BeanUtils;
import com.doge.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 业务日志业务
 *
 * @author shixinyu
 * @date 2021-08-31 10:01
 */
@RestController
@RequestMapping("/businessLog")
@NoArgsConstructor
@Api(value = "BusinessLogController", tags = "业务日志业务")
public class BusinessLogController {
    private BusinessLogService businessLogService;

    @Autowired
    BusinessLogController(BusinessLogService businessLogService) {
        this.businessLogService = businessLogService;
    }

    @PreAuthorize("@aps.hasPermission('log:businessLog')")
    @GetMapping("/pageList")
    @ApiOperation(value = "业务日志列表获取")
    public AntPageVO pageList(SysBusinessLogQueryVO sysBusinessLogQueryVO) {
        PageDTO pageDTO = PageUtils.createPageDTO(sysBusinessLogQueryVO);
        Map<String, String> sorter = PageUtils.createSorter(sysBusinessLogQueryVO);
        AntPageDTO<BusinessLogDTO> antPageDTO = businessLogService.getPageList(pageDTO, BeanUtils.map(sysBusinessLogQueryVO, BusinessLogQueryDTO.class), sorter);
        return new AntPageVO().build(antPageDTO, BusinessLogVO.class);
    }

    @PreAuthorize("@aps.hasPermission('log:businessLog:delete')")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "业务日志删除")
    @Log(title = "业务日志删除")
    public void enable(@PathVariable Integer id) {
        businessLogService.removeById(id);
    }

    @PreAuthorize("@aps.hasPermission('log:businessLog:delete')")
    @DeleteMapping("/deleteBatch")
    @ApiOperation(value = "业务日志批量删除")
    @Log(title = "业务日志批量删除")
    public void deleteBatch(@RequestBody List<Integer> ids) {
        businessLogService.removeByIds(ids);
    }

    @PreAuthorize("@aps.hasPermission('log:businessLog:delete')")
    @DeleteMapping("/clear")
    @ApiOperation(value = "业务日志清空")
    @Log(title = "业务日志清空")
    public void clear() {
        businessLogService.clear();
    }
}
