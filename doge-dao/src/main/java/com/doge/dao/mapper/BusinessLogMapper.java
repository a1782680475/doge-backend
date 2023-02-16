package com.doge.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doge.dao.entity.BusinessLogDO;
import com.doge.dao.queryentity.BusinessLogQueryBO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 业务日志Mapper
 *
 * @author shixinyu
 * @date 2021-08-30 15:36
 */
public interface BusinessLogMapper extends BaseMapper<BusinessLogDO> {
    /**
     * 分页查询业务日志列表
     *
     * @param page   分页
     * @param businessLogQueryBO 查询信息
     * @param sorter 用户排序信息
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.doge.dao.entity.BusinessLogDO> 带有分页信息的业务日志信息
     */
    IPage<BusinessLogDO> selectListByPage(Page<BusinessLogDO> page, @Param("query") BusinessLogQueryBO businessLogQueryBO, @Param("sorter") Map<String, String> sorter);

    /**
     * 业务日志清空
     *
     * @param
     * @return void
     */
    void clear();
}
