package com.doge.service;

import com.doge.service.entity.AntPageDTO;
import com.doge.service.entity.BulletinDTO;
import com.doge.service.entity.MsgBulletinAddDTO;
import com.doge.service.entity.PageDTO;

/**
 * 公告服务
 *
 * @author shixinyu
 * @date 2023/9/27 20:17
 */
public interface BulletinService extends BaseService<BulletinDTO> {
    /**
     * 分页获取公告
     *
     * @param pageDTO 分页
     * @param userId  用户id
     * @return com.doge.service.entity.AntPageDTO<com.doge.service.entity.NotifyBulletinDTO>
     */
    AntPageDTO<BulletinDTO> getListByPage(PageDTO pageDTO, Integer userId);

    /**
     * 获取公告
     *
     * @param id 公告id
     * @return com.doge.service.entity.BulletinDTO
     */
    BulletinDTO getById(Integer id);

    /**
     * 公告新增
     *
     * @param bulletinAddDTO 公告新增信息
     * @return java.lang.String
     */
    void addBulletin(MsgBulletinAddDTO bulletinAddDTO);
}
