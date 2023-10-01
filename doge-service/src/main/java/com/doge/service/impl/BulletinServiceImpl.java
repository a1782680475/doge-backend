package com.doge.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doge.dao.entity.NotifyBulletinDO;
import com.doge.dao.mapper.NotifyBulletinMapper;
import com.doge.dao.queryentity.BulletinBO;
import com.doge.service.BulletinService;
import com.doge.service.entity.AntPageDTO;
import com.doge.service.entity.BulletinDTO;
import com.doge.service.entity.MsgBulletinAddDTO;
import com.doge.service.entity.PageDTO;
import com.doge.utils.BeanUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * 公告服务实现类
 *
 * @author shixinyu
 * @date 2023/9/27 20:22
 */
@Service
@NoArgsConstructor
public class BulletinServiceImpl extends BaseServiceImpl<NotifyBulletinMapper, NotifyBulletinDO, BulletinDTO> implements BulletinService {
    private NotifyBulletinMapper notifyBulletinMapper;
    private static final int briefContentLength = 30;

    @Autowired
    BulletinServiceImpl(NotifyBulletinMapper notifyBulletinMapper) {
        this.notifyBulletinMapper = notifyBulletinMapper;
    }

    @Override
    public AntPageDTO<BulletinDTO> getListByPage(PageDTO pageDTO, Integer userId) {
        Page<BulletinBO> page = new Page<>(pageDTO.getCurrent(), pageDTO.getPageSize());
        IPage<BulletinBO> notifyBulletinPage = notifyBulletinMapper.selectListByPage(page);
        notifyBulletinPage.setRecords(notifyBulletinPage.getRecords().stream().map(bulletin -> {
            String content = bulletin.getContent();
            content = HtmlUtil.cleanHtmlTag(content);
            if (content.length() > briefContentLength) {
                content = StrUtil.sub(content, 0, 30);
                content += "...";
            }
            bulletin.setContent(content);
            return bulletin;
        }).collect(Collectors.toList()));
        return new AntPageDTO<BulletinBO>().init(notifyBulletinPage);
    }

    @Override
    public BulletinDTO getById(Integer id) {
        return BeanUtils.map(baseMapper.selectOne(id), BulletinDTO.class);
    }

    @Override
    public void addBulletin(MsgBulletinAddDTO bulletinAddDTO) {
        Date now = new Date();
        NotifyBulletinDO bulletin = new NotifyBulletinDO();
        bulletin.setTitle(bulletinAddDTO.getTitle());
        bulletin.setContent(bulletinAddDTO.getContent());
        bulletin.setSender(bulletinAddDTO.getSender());
        bulletin.setCreateTime(now);
        bulletin.setUpdateTime(now);
        baseMapper.insert(bulletin);
    }
}
