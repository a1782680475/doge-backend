package com.doge.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doge.dao.entity.NotifyUserBulletinDO;
import com.doge.dao.queryentity.NotifyBulletinBO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 用户公告Mapper
 *
 * @author shixinyu
 * @date 2021/12/29 16:23
 */
public interface NotifyUserBulletinMapper extends BaseMapper<NotifyUserBulletinDO> {

    /**
     * 分页获取用户公告
     *
     * @param page   分页
     * @param userId 用户id
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.doge.dao.queryentity.NotifyBulletinBO>
     */
    IPage<NotifyBulletinBO> selectListByPage(Page<NotifyBulletinBO> page, @Param("userId")Integer userId);

    /**
     * 获取用户未读公告
     *
     * @param userId 用户id
     * @return java.util.List<com.doge.dao.queryentity.NotifyBulletinBO>
     */
    List<NotifyBulletinBO> selectUnreadList(Integer userId);

    /**
     * 获取用户未读公告
     *
     * @param count 查询最大条目数
     * @param userId 用户id
     * @return java.util.List<com.doge.dao.queryentity.NotifyBulletinBO>
     */
    List<NotifyBulletinBO> selectUnreadListByCount(@Param("count")Integer count, @Param("userId")Integer userId);

    /**
     * 用户用户未读公告数获取
     *
     * @param userId 用户id
     * @return java.lang.Integer
     */
    Integer getUnreadCount(int userId);

    /**
     * 根据id和userId查询某公告
     *
     * @param id id
     * @param userId userId
     * @return com.doge.dao.entity.NotifyBulletinDO
     */
    NotifyUserBulletinDO selectByIdAndUserId(@Param("id")Integer id,@Param("userId")Integer userId);

    /**
     * 批量插入用户公告关系记录
     *
     * @param entityList List
     * @return java.lang.Integer
     */
    Integer insertBatchSomeColumn(Collection<NotifyUserBulletinDO> entityList);
}
