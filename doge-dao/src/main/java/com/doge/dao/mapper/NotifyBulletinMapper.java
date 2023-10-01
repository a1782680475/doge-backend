package com.doge.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doge.dao.entity.NotifyBulletinDO;
import com.doge.dao.queryentity.BulletinBO;

import org.springframework.stereotype.Repository;

/**
 * 公告相关Mapper
 *
 * @author shixinyu
 * @date 2023/9/27 20:25
 */
@Repository
public interface NotifyBulletinMapper extends BaseMapper<NotifyBulletinDO> {
    /**
     * 分页获取公告
     *
     * @param page 分页
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.doge.dao.queryentity.BulletinBO>
     */
    IPage<BulletinBO> selectListByPage(Page<BulletinBO> page);

    /**
     * 获取公告
     *
     * @param id 公告id
     * @return com.doge.dao.queryentity.BulletinBO
     */
    BulletinBO selectOne(Integer id);
}
