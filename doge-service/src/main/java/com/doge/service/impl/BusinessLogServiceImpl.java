package com.doge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doge.dao.entity.BusinessLogDO;
import com.doge.dao.mapper.BusinessLogMapper;
import com.doge.dao.queryentity.BusinessLogQueryBO;
import com.doge.service.entity.AntPageDTO;
import com.doge.service.entity.BusinessLogDTO;
import com.doge.service.entity.BusinessLogQueryDTO;
import com.doge.service.entity.PageDTO;
import com.doge.service.BusinessLogService;
import com.doge.utils.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 业务日志服务实现类
 *
 * @author shixinyu
 * @date 2021-08-30 15:40
 */
@Service
public class BusinessLogServiceImpl extends BaseServiceImpl<BusinessLogMapper, BusinessLogDO, BusinessLogDTO> implements BusinessLogService {
    @Override
    @Async
    public void asyncSave(BusinessLogDTO businessLogDTO) {
        baseMapper.insert(businessLogDTO);
    }

    @Override
    public AntPageDTO<BusinessLogDTO> getPageList(PageDTO pageDTO, BusinessLogQueryDTO businessLogQueryDTO, Map<String, String> sorter) {
        Page<BusinessLogDO> page = new Page<>(pageDTO.getCurrent(),pageDTO.getPageSize());
        IPage<BusinessLogDO> businessLogPage = baseMapper.selectListByPage(page, BeanUtils.map(businessLogQueryDTO, BusinessLogQueryBO.class),sorter);
        return new AntPageDTO<BusinessLogDO>().init(businessLogPage);
    }

    @Override
    public void clear() {
        baseMapper.clear();
    }
}
