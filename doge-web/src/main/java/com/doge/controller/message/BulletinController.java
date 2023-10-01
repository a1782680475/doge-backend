package com.doge.controller.message;

import com.doge.annotation.Log;
import com.doge.entity.PageQuery;
import com.doge.entity.vo.request.MsgBulletinAddVO;
import com.doge.entity.vo.request.SysUserAddVO;
import com.doge.entity.vo.response.AntPageVO;
import com.doge.entity.vo.response.BulletinVO;
import com.doge.entity.vo.response.SimpleResultVO;
import com.doge.entity.vo.response.SysRoleVO;
import com.doge.service.BulletinService;
import com.doge.service.entity.*;
import com.doge.utils.BeanUtils;
import com.doge.utils.PageUtils;
import com.doge.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 公告业务
 *
 * @author shixinyu
 * @date 2023/9/27 17:03
 */
@RestController
@RequestMapping("/bulletin")
@Api(value = "BulletinController", tags = "公告管理")
@NoArgsConstructor
public class BulletinController {
    private BulletinService bulletinService;
    @Autowired
    BulletinController(BulletinService bulletinService) {
        this.bulletinService = bulletinService;
    }
    @GetMapping("/pageList")
    @ApiOperation(value = "公告列表获取（分页）")
    @PreAuthorize("@aps.hasPermission('msg:bulletin')")
    public AntPageVO bulletinPageList(PageQuery pageQuery) {
        int userId = SecurityUtils.getUserId();
        PageDTO pageDTO = PageUtils.createPageDTO(pageQuery);
        AntPageDTO<BulletinDTO> antPageDTO = bulletinService.getListByPage(pageDTO, userId);
        return new AntPageVO().build(antPageDTO, BulletinVO.class);
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "指定公告获取")
    @PreAuthorize("@aps.hasPermission('msg:bulletin')")
    public BulletinVO getBulletinById(@PathVariable Integer id){
        return BeanUtils.map(bulletinService.getById(id),BulletinVO.class);
    }

    @PostMapping("/bulletin/add")
    @ApiOperation(value = "公告新增")
    @Log(title = "公告新增")
    @PreAuthorize("@aps.hasPermission('msg:bulletin:add')")
    public void add(@RequestBody MsgBulletinAddVO msgBulletinAddVO) {
        MsgBulletinAddDTO msgBulletinAddDTO = BeanUtils.map(msgBulletinAddVO, MsgBulletinAddDTO.class);
        msgBulletinAddDTO.setSender(SecurityUtils.getUserId());
        bulletinService.addBulletin(msgBulletinAddDTO);
    }
}
