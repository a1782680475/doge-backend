package com.doge.service;

import com.doge.service.entity.AntPageDTO;
import com.doge.service.entity.BusinessLogDTO;
import com.doge.service.entity.BusinessLogQueryDTO;
import com.doge.service.entity.PageDTO;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * 业务日志相关服务
 *
 * @author shixinyu
 * @date 2021-08-30 15:35
 */
public interface BusinessLogService extends  BaseService<BusinessLogDTO>{
    /**
     * 异步保存业务日志
     *
     * @param businessLogDTO 业务日志
     * @return void
     */
    @Async
    void asyncSave(BusinessLogDTO businessLogDTO);

    /**
     * 分页获取业务日志列表
     *
     * @param page         分页信息
     * @param businessLogQueryDTO 业务日志查询信息
     * @param sorter       业务日志排序信息
     * @return com.doge.service.entity.AntPageDTO<com.doge.service.entity.BusinessLogDTO>
     */
    AntPageDTO<BusinessLogDTO> getPageList(PageDTO page, BusinessLogQueryDTO businessLogQueryDTO, Map<String, String> sorter);

    /**
     * 业务日志清空
     *
     * @param
     * @return void
     */
    void clear();
}
