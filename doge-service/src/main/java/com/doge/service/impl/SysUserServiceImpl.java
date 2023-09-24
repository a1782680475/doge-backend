package com.doge.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doge.dao.entity.SysUserDO;
import com.doge.dao.mapper.SysUserMapper;
import com.doge.dao.updateentity.SysUserInfoBO;
import com.doge.service.NoticeService;
import com.doge.service.SendMailService;
import com.doge.service.common.MessageResult;
import com.doge.service.entity.*;
import com.doge.service.SysUserRoleService;
import com.doge.service.SysUserService;
import com.doge.utils.BeanUtils;
import com.doge.service.utils.RsaUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * 用户服务实现类
 *
 * @author shixinyu
 * @date 2021-06-09 10:44
 */
@Service
@NoArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUserDO, SysUserDTO> implements SysUserService {
    private RedisTemplate redisTemplate;
    private SysUserRoleService sysUserRoleService;
    private SendMailService sendMailService;
    private NoticeService noticeService;
    @Value("${website.name}")
    private String webSiteName;
    @Value("${website.link}")
    private String webSiteLink;
    @Value("${website.logo}")
    private String webSiteLogo;
    final String bindEmailTemplate = "<div><includetail><div align=\"center\"><div class=\"open_email\"style=\"margin-left: 8px; margin-top: 8px; margin-bottom: 8px; margin-right: 8px;\"><div><br><span class=\"genEmailContent\"><div id=\"cTMail-Wrap\"style=\"word-break: break-all;box-sizing:border-box;text-align:center;min-width:320px; max-width:660px; border:1px solid #f6f6f6; background-color:#f7f8fa; margin:auto; padding:20px 0 30px; font-family:'helvetica neue',PingFangSC-Light,arial,'hiragino sans gb','microsoft yahei ui','microsoft yahei',simsun,sans-serif\"><div class=\"main-content\"style=\"\"><table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse\"><tbody><tr style=\"font-weight:300\"><td style=\"width:3%;max-width:30px;\"></td><td style=\"max-width:600px;\"><div id=\"cTMail-logo\"style=\"height:60px;\"><a href=\"${website.link}\"><img border=\"0\"src=\"${website.logo}\"style=\"width:60px;display:block\"></a></div><p style=\"height:2px;background-color: #00a4ff;border: 0;font-size:0;padding:0;width:100%;margin-top:20px;\"></p><div id=\"cTMail-inner\"style=\"background-color:#fff; padding:23px 0 20px;box-shadow: 0px 1px 1px 0px rgba(122, 55, 55, 0.2);text-align:left;\"><table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse;text-align:left;\"><tbody><tr style=\"font-weight:300\"><td style=\"width:3.2%;max-width:30px;\"></td><td style=\"max-width:480px;text-align:left;\"><h1 id=\"cTMail-title\"style=\"font-size: 20px; line-height: 36px; margin: 0px 0px 22px;\">【${website.name}平台】账户邮箱绑定</h1><p id=\"cTMail-userName\"style=\"font-size:14px;color:#333; line-height:24px; margin:0;\">你好！</p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 14px;\">你正在${website.name}绑定账户邮箱，请点击下方按钮完成绑定：</span></p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 14px;\"><span style=\"font-weight: bold;\">非本人操作可忽略。</span></span></p><p class=\"cTMail-content\"style=\"font-size: 14px; color: rgb(51, 51, 51); line-height: 24px; margin: 6px 0px 0px; word-wrap: break-word; word-break: break-all;\"><a id=\"cTMail-btn\"href=\"${linkUrl}\"title=\"\"style=\"font-size: 16px; line-height: 45px; display: block; background-color: rgb(0, 164, 255); color: rgb(255, 255, 255); text-align: center; text-decoration: none; margin-top: 20px; border-radius: 3px;\">点击此处验证邮箱</a></p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 14px;\"><br>无法正常显示？请复制以下链接至浏览器打开：<br><a href=\"${website.link}\"title=\"\"style=\"color: rgb(0, 164, 255); text-decoration: none; word-break: break-all; overflow-wrap: normal; font-size: 14px;\">${linkUrl}</a></span></p><dl style=\"font-size: 14px; color: rgb(51, 51, 51); line-height: 18px;\"><dd style=\"margin: 0px 0px 6px; padding: 0px; font-size: 12px; line-height: 22px;\"><p id=\"cTMail-sender\"style=\"font-size: 14px; line-height: 26px; word-wrap: break-word; word-break: break-all; margin-top: 32px;\">祝你使用愉快！<br><strong>${website.name}团队</strong></p></dd></dl></td><td style=\"width:3.2%;max-width:30px;\"></td></tr></tbody></table></div><div id=\"cTMail-copy\"style=\"text-align:center; font-size:12px; line-height:18px; color:#999\"><table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse\"><tbody><tr style=\"font-weight:300\"><td style=\"width:3.2%;max-width:30px;\"></td><td style=\"max-width:540px;\"><p style=\"text-align:center; margin:20px auto 14px auto;font-size:12px;color:#999;\">此为系统邮件，请勿回复。</p><p style=\"text-align:center; margin:20px auto 14px auto;font-size:12px;color:#999;\">Copyright&nbsp;©&nbsp;2021&nbsp;${website.name}.</p></td><td style=\"width:3.2%;max-width:30px;\"></td></tr></tbody></table></div></td><td style=\"width:3%;max-width:30px;\"></td></tr></tbody></table></div></div></span></div></div></div></includetail></div>";
    final String changeEmailTemplate = "<div><includetail><div align=\"center\"><div class=\"open_email\"style=\"margin-left: 8px; margin-top: 8px; margin-bottom: 8px; margin-right: 8px;\"><div><br><span class=\"genEmailContent\"><div id=\"cTMail-Wrap\"style=\"word-break: break-all;box-sizing:border-box;text-align:center;min-width:320px; max-width:660px; border:1px solid #f6f6f6; background-color:#f7f8fa; margin:auto; padding:20px 0 30px; font-family:'helvetica neue',PingFangSC-Light,arial,'hiragino sans gb','microsoft yahei ui','microsoft yahei',simsun,sans-serif\"><div class=\"main-content\"style=\"\"><table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse\"><tbody><tr style=\"font-weight:300\"><td style=\"width:3%;max-width:30px;\"></td><td style=\"max-width:600px;\"><div id=\"cTMail-logo\"style=\"height:60px;\"><a href=\"${website.link}\"><img border=\"0\"src=\"${website.logo}\"style=\"width:60px;display:block\"></a></div><p style=\"height:2px;background-color: #00a4ff;border: 0;font-size:0;padding:0;width:100%;margin-top:20px;\"></p><div id=\"cTMail-inner\"style=\"background-color:#fff; padding:23px 0 20px;box-shadow: 0px 1px 1px 0px rgba(122, 55, 55, 0.2);text-align:left;\"><table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse;text-align:left;\"><tbody><tr style=\"font-weight:300\"><td style=\"width:3.2%;max-width:30px;\"></td><td style=\"max-width:480px;text-align:left;\"><h1 id=\"cTMail-title\"style=\"font-size: 20px; line-height: 36px; margin: 0px 0px 22px;\">【${website.name}平台】更换邮箱验证码</h1><p id=\"cTMail-userName\"style=\"font-size:14px;color:#333; line-height:24px; margin:0;\">你好！</p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 14px;\">你正在${website.name}更换账户绑定邮箱，验证码是：</span></p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 18px;\"><span style=\"font-weight: bold;\">${code}</span></span></p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 14px;\"><span style=\"font-weight: bold;\">验证码5分钟内有效，请尽快输入。</span></span></p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 14px;\"><span style=\"font-weight: bold;\">非本人操作可忽略。</span></span></p><dl style=\"font-size: 14px; color: rgb(51, 51, 51); line-height: 18px;\"><dd style=\"margin: 0px 0px 6px; padding: 0px; font-size: 12px; line-height: 22px;\"><p id=\"cTMail-sender\"style=\"font-size: 14px; line-height: 26px; word-wrap: break-word; word-break: break-all; margin-top: 32px;\">祝你使用愉快！<br><strong>${website.name}团队</strong></p></dd></dl></td><td style=\"width:3.2%;max-width:30px;\"></td></tr></tbody></table></div><div id=\"cTMail-copy\"style=\"text-align:center; font-size:12px; line-height:18px; color:#999\"><table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse\"><tbody><tr style=\"font-weight:300\"><td style=\"width:3.2%;max-width:30px;\"></td><td style=\"max-width:540px;\"><p style=\"text-align:center; margin:20px auto 14px auto;font-size:12px;color:#999;\">此为系统邮件，请勿回复。</p><p style=\"text-align:center; margin:20px auto 14px auto;font-size:12px;color:#999;\">Copyright&nbsp;©&nbsp;2021&nbsp;${website.name}.</p></td><td style=\"width:3.2%;max-width:30px;\"></td></tr></tbody></table></div></td><td style=\"width:3%;max-width:30px;\"></td></tr></tbody></table></div></div></span></div></div></div></includetail></div>";
    final String changePasswordTemplate = "<div><includetail><div align=\"center\"><div class=\"open_email\"style=\"margin-left: 8px; margin-top: 8px; margin-bottom: 8px; margin-right: 8px;\"><div><br><span class=\"genEmailContent\"><div id=\"cTMail-Wrap\"style=\"word-break: break-all;box-sizing:border-box;text-align:center;min-width:320px; max-width:660px; border:1px solid #f6f6f6; background-color:#f7f8fa; margin:auto; padding:20px 0 30px; font-family:'helvetica neue',PingFangSC-Light,arial,'hiragino sans gb','microsoft yahei ui','microsoft yahei',simsun,sans-serif\"><div class=\"main-content\"style=\"\"><table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse\"><tbody><tr style=\"font-weight:300\"><td style=\"width:3%;max-width:30px;\"></td><td style=\"max-width:600px;\"><div id=\"cTMail-logo\"style=\"height:60px;\"><a href=\"${website.link}\"><img border=\"0\"src=\"${website.logo}\"style=\"width:60px;display:block\"></a></div><p style=\"height:2px;background-color: #00a4ff;border: 0;font-size:0;padding:0;width:100%;margin-top:20px;\"></p><div id=\"cTMail-inner\"style=\"background-color:#fff; padding:23px 0 20px;box-shadow: 0px 1px 1px 0px rgba(122, 55, 55, 0.2);text-align:left;\"><table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse;text-align:left;\"><tbody><tr style=\"font-weight:300\"><td style=\"width:3.2%;max-width:30px;\"></td><td style=\"max-width:480px;text-align:left;\"><h1 id=\"cTMail-title\"style=\"font-size: 20px; line-height: 36px; margin: 0px 0px 22px;\">【${website.name}平台】密码修改验证码</h1><p id=\"cTMail-userName\"style=\"font-size:14px;color:#333; line-height:24px; margin:0;\">你好！</p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 14px;\">你正在${website.name}修改账户密码，验证码是：</span></p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 18px;\"><span style=\"font-weight: bold;\">${code}</span></span></p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 14px;\"><span style=\"font-weight: bold;\">验证码5分钟内有效，请尽快输入。</span></span></p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 14px;\"><span style=\"font-weight: bold;\">非本人操作可忽略。</span></span></p><dl style=\"font-size: 14px; color: rgb(51, 51, 51); line-height: 18px;\"><dd style=\"margin: 0px 0px 6px; padding: 0px; font-size: 12px; line-height: 22px;\"><p id=\"cTMail-sender\"style=\"font-size: 14px; line-height: 26px; word-wrap: break-word; word-break: break-all; margin-top: 32px;\">祝你使用愉快！<br><strong>${website.name}团队</strong></p></dd></dl></td><td style=\"width:3.2%;max-width:30px;\"></td></tr></tbody></table></div><div id=\"cTMail-copy\"style=\"text-align:center; font-size:12px; line-height:18px; color:#999\"><table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse\"><tbody><tr style=\"font-weight:300\"><td style=\"width:3.2%;max-width:30px;\"></td><td style=\"max-width:540px;\"><p style=\"text-align:center; margin:20px auto 14px auto;font-size:12px;color:#999;\">此为系统邮件，请勿回复。</p><p style=\"text-align:center; margin:20px auto 14px auto;font-size:12px;color:#999;\">Copyright&nbsp;©&nbsp;2021&nbsp;${website.name}.</p></td><td style=\"width:3.2%;max-width:30px;\"></td></tr></tbody></table></div></td><td style=\"width:3%;max-width:30px;\"></td></tr></tbody></table></div></div></span></div></div></div></includetail></div>";
    final String findPasswordTemplate = "<div><includetail><div align=\"center\"><div class=\"open_email\"style=\"margin-left: 8px; margin-top: 8px; margin-bottom: 8px; margin-right: 8px;\"><div><br><span class=\"genEmailContent\"><div id=\"cTMail-Wrap\"style=\"word-break: break-all;box-sizing:border-box;text-align:center;min-width:320px; max-width:660px; border:1px solid #f6f6f6; background-color:#f7f8fa; margin:auto; padding:20px 0 30px; font-family:'helvetica neue',PingFangSC-Light,arial,'hiragino sans gb','microsoft yahei ui','microsoft yahei',simsun,sans-serif\"><div class=\"main-content\"style=\"\"><table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse\"><tbody><tr style=\"font-weight:300\"><td style=\"width:3%;max-width:30px;\"></td><td style=\"max-width:600px;\"><div id=\"cTMail-logo\"style=\"height:60px;\"><a href=\"${website.link}\"><img border=\"0\"src=\"${website.logo}\"style=\"width:60px;display:block\"></a></div><p style=\"height:2px;background-color: #00a4ff;border: 0;font-size:0;padding:0;width:100%;margin-top:20px;\"></p><div id=\"cTMail-inner\"style=\"background-color:#fff; padding:23px 0 20px;box-shadow: 0px 1px 1px 0px rgba(122, 55, 55, 0.2);text-align:left;\"><table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse;text-align:left;\"><tbody><tr style=\"font-weight:300\"><td style=\"width:3.2%;max-width:30px;\"></td><td style=\"max-width:480px;text-align:left;\"><h1 id=\"cTMail-title\"style=\"font-size: 20px; line-height: 36px; margin: 0px 0px 22px;\">【${website.name}平台】密码找回验证码</h1><p id=\"cTMail-userName\"style=\"font-size:14px;color:#333; line-height:24px; margin:0;\">你好！</p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 14px;\">你正在${website.name}找回账户密码，验证码是：</span></p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 18px;\"><span style=\"font-weight: bold;\">${code}</span></span></p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 14px;\"><span style=\"font-weight: bold;\">验证码5分钟内有效，请尽快输入。</span></span></p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 14px;\"><span style=\"font-weight: bold;\">非本人操作可忽略。</span></span></p><dl style=\"font-size: 14px; color: rgb(51, 51, 51); line-height: 18px;\"><dd style=\"margin: 0px 0px 6px; padding: 0px; font-size: 12px; line-height: 22px;\"><p id=\"cTMail-sender\"style=\"font-size: 14px; line-height: 26px; word-wrap: break-word; word-break: break-all; margin-top: 32px;\">祝你使用愉快！<br><strong>${website.name}团队</strong></p></dd></dl></td><td style=\"width:3.2%;max-width:30px;\"></td></tr></tbody></table></div><div id=\"cTMail-copy\"style=\"text-align:center; font-size:12px; line-height:18px; color:#999\"><table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse\"><tbody><tr style=\"font-weight:300\"><td style=\"width:3.2%;max-width:30px;\"></td><td style=\"max-width:540px;\"><p style=\"text-align:center; margin:20px auto 14px auto;font-size:12px;color:#999;\">此为系统邮件，请勿回复。</p><p style=\"text-align:center; margin:20px auto 14px auto;font-size:12px;color:#999;\">Copyright&nbsp;©&nbsp;2021&nbsp;${website.name}.</p></td><td style=\"width:3.2%;max-width:30px;\"></td></tr></tbody></table></div></td><td style=\"width:3%;max-width:30px;\"></td></tr></tbody></table></div></div></span></div></div></div></includetail></div>";

    @Autowired
    SysUserServiceImpl(SysUserRoleService sysUserRoleService, SendMailService sendMailService, RedisTemplate redisTemplate, NoticeService noticeService) {
        this.sysUserRoleService = sysUserRoleService;
        this.sendMailService = sendMailService;
        this.redisTemplate = redisTemplate;
        this.noticeService = noticeService;
    }

    @Override
    public SysUserDTO getUserByUsername(String username) {
        return BeanUtils.map(baseMapper.getUserByUsername(username), SysUserDTO.class);
    }

    @Override
    public String addUser(SysUserAddDTO userAddDTO) {
        String errorMessage;
        String pattern = "^[a-zA-Z][a-zA-Z0-9_]+$";
        boolean isMatch = Pattern.matches(pattern, userAddDTO.getUsername());
        if (!isMatch) {
            errorMessage = "用户名只能由字母、数字、下划线组成，且必须以字母开头";
            return errorMessage;
        }
        int minLength = 4;
        int maxLength = 20;
        if (userAddDTO.getUsername().length() < minLength || userAddDTO.getUsername().length() > maxLength) {
            errorMessage = "用户名不能短于4个字符长于20个字符";
            return errorMessage;
        }
        QueryWrapper<SysUserDO> wrapper = new QueryWrapper();
        wrapper.eq("username", userAddDTO.getUsername());
        if (baseMapper.selectOne(wrapper) != null) {
            errorMessage = "用户名已存在";
            return errorMessage;
        } else {
            Date now = new Date();
            SysUserDO user = new SysUserDO();
            user.setUsername(userAddDTO.getUsername());
            user.setNickName(userAddDTO.getUsername());
            user.setPassword(new BCryptPasswordEncoder().encode(RsaUtils.decrypt(userAddDTO.getPassword())));
            user.setEnabled(true);
            user.setCreateTime(now);
            user.setUpdateTime(now);
            if (baseMapper.insert(user) != 1) {
                errorMessage = "未知错误";
                return errorMessage;
            }
        }
        return null;
    }

    @Override
    public MessageResult<String> register(SysUserRegisterInfoDTO userRegisterInfoDTO) {
        Boolean success = true;
        String message;
        QueryWrapper<SysUserDO> wrapper = new QueryWrapper();
        wrapper.eq("username", userRegisterInfoDTO.getUsername());
        if (baseMapper.selectOne(wrapper) != null) {
            success = false;
            message = "用户名已存在";
        } else {
            Date now = new Date();
            SysUserDO user = new SysUserDO();
            user.setUsername(userRegisterInfoDTO.getUsername());
            user.setNickName(userRegisterInfoDTO.getUsername());
            user.setPassword(new BCryptPasswordEncoder().encode(RsaUtils.decrypt(userRegisterInfoDTO.getPassword())));
            user.setEnabled(true);
            user.setCreateTime(now);
            user.setUpdateTime(now);
            if (baseMapper.insert(user) == 1) {
                message = "注册成功";
            } else {
                success = false;
                message = "未知错误";
            }
        }
        return new MessageResult(success, message);
    }

    @Override
    public AntPageDTO<SysUserDTO> getPageList(PageDTO pageDTO, SysUserQueryDTO userQueryDTO, Map<String, String> sorter) {
        Page<SysUserDO> page = new Page<>(pageDTO.getCurrent(), pageDTO.getPageSize());
        IPage<SysUserDO> userPage = baseMapper.selectListByPage(page, BeanUtils.map(userQueryDTO, SysUserDO.class), sorter);
        return new AntPageDTO<SysUserDO>().init(userPage);
    }

    @Override
    public Boolean enable(Integer id) {
        baseMapper.enable(id);
        return true;
    }

    @Override
    public Boolean disable(Integer id) {
        baseMapper.disable(id);
        return true;
    }

    @Override
    public Boolean enableBatch(List<Integer> ids) {
        baseMapper.enableBatch(ids);
        return true;
    }

    @Override
    public Boolean disableBatch(List<Integer> ids) {
        baseMapper.disableBatch(ids);
        return true;
    }

    @Override
    public Boolean settingRoles(Integer id, List<Integer> roleIds) {
        sysUserRoleService.removeByUserId(id);
        List<SysUserRoleDTO> sysUserRoleList = new ArrayList<>();
        for (Integer roleId : roleIds) {
            SysUserRoleDTO sysUserRoleDTO = new SysUserRoleDTO();
            sysUserRoleDTO.setUserId(id);
            sysUserRoleDTO.setRoleId(roleId);
            Date now = new Date();
            sysUserRoleDTO.setCreateTime(now);
            sysUserRoleDTO.setUpdateTime(now);
            sysUserRoleList.add(sysUserRoleDTO);
        }
        sysUserRoleService.saveBatch(sysUserRoleList, sysUserRoleList.size());
        return true;
    }

    @Override
    public List<SysRoleDTO> getRoleList(Integer id) {
        return BeanUtils.mapAsList(baseMapper.getRoleList(id), SysRoleDTO.class);
    }

    @Override
    public List<SysMenuDTO> getMenuList(Integer id) {
        return BeanUtils.mapAsList(baseMapper.getMenuList(id), SysMenuDTO.class);
    }

    @Override
    public Boolean updateInfo(Integer id, SysUserInfoDTO userInfo) {
        return baseMapper.updateInfo(id, BeanUtils.map(userInfo, SysUserInfoBO.class));
    }

    @Override
    public String sendBindEmailCode(int userId, String email) {
        String errorMessage;
        //先验证邮箱是否已被绑定
        QueryWrapper<SysUserDO> wrapper = new QueryWrapper();
        wrapper.eq("account_email", email);
        SysUserDO sysUserDO = baseMapper.selectOne(wrapper);
        if (sysUserDO != null) {
            errorMessage = "该邮箱已被绑定";
            return errorMessage;
        }
        String redisKey = "doge:account:bindEmail" + "_" + email;
        Object redisValue = redisTemplate.opsForValue().get(redisKey);
        String linkUrl = null;
        if (redisValue != null) {
            linkUrl = webSiteLink + "/bindEmailResult?email=" + email + "&token=" + redisValue;
        } else {
            String uuid = UUID.randomUUID().toString();
            linkUrl = webSiteLink + "/bindEmailResult?email=" + email + "&token=" + uuid;
            redisTemplate.opsForValue().set(redisKey, uuid + "_" + userId, 48, TimeUnit.HOURS);
        }
        //发送邮件
        MailRequest mailRequest = new MailRequest();
        mailRequest.setSubject(webSiteName + "用户邮箱绑定验证");
        mailRequest.setSendTo(email);
        String emailContent = bindEmailTemplate
                .replace("${linkUrl}", linkUrl)
                .replace("${website.link}", webSiteLink)
                .replace("${website.logo}", webSiteLogo)
                .replace("${website.name}", webSiteName);
        mailRequest.setText(emailContent);
        sendMailService.sendHtmlMail(mailRequest);
        return null;
    }

    @Override
    public String bindEmailVerify(String email, String token) {
        String errorMessage;
        String redisKey = "doge:account:bindEmail" + "_" + email;
        Object redisValue = redisTemplate.opsForValue().get(redisKey);
        String splitStr = "_";
        if (redisValue == null) {
            errorMessage = "验证链接无效或已过期";
            return errorMessage;
        } else {
            if (!redisValue.toString().split(splitStr)[0].equals(token)) {
                errorMessage = "验证链接无效或已过期";
                return errorMessage;
            }
        }
        //更新邮箱到用户信息
        baseMapper.bindEmail(Integer.valueOf(redisValue.toString().split(splitStr)[1]), email);
        return null;
    }

    @Override
    public String sendVerificationCodeForChangeEmail(int userId) {
        String errorMessage;
        //先检查是否已绑定邮箱
        SysUserDO sysUserDO = baseMapper.selectById(userId);
        String accountEmail = sysUserDO.getAccountEmail();
        if (sysUserDO.getAccountEmail().isEmpty()) {
            errorMessage = "用户未绑定邮箱";
            return errorMessage;
        }
        String redisKey = "doge:account:changeEmail" + "_" + userId;
        Object redisValue = redisTemplate.opsForValue().get(redisKey);
        String verificationCode;
        if (redisValue == null) {
            verificationCode = RandomUtil.randomNumbers(6);
            redisTemplate.opsForValue().set(redisKey, verificationCode, 5, TimeUnit.MINUTES);
        } else {
            verificationCode = redisValue.toString();
        }
        //发送邮件
        MailRequest mailRequest = new MailRequest();
        mailRequest.setSubject(webSiteName + "用户邮箱更换验证码");
        mailRequest.setSendTo(accountEmail);
        String emailContent = changeEmailTemplate
                .replace("${website.link}", webSiteLink)
                .replace("${website.logo}", webSiteLogo)
                .replace("${website.name}", webSiteName)
                .replace("${code}", verificationCode);
        mailRequest.setText(emailContent);
        sendMailService.sendHtmlMail(mailRequest);
        return null;
    }

    @Override
    public String verifyCodeForChangeEmail(int userId, String code) {
        String errorMessage;
        String redisKey = "doge:account:changeEmail" + "_" + userId;
        Object redisValue = redisTemplate.opsForValue().get(redisKey);
        if (redisValue == null) {
            errorMessage = "验证码已过期";
            return errorMessage;
        } else {
            if (!redisValue.toString().equals(code)) {
                errorMessage = "验证码无效";
                return errorMessage;
            }
        }
        return null;
    }

    @Override
    public String sendVerificationCodeForChangePassword(int userId) {
        String errorMessage;
        //先检查是否已绑定邮箱
        SysUserDO sysUserDO = baseMapper.selectById(userId);
        String accountEmail = sysUserDO.getAccountEmail();
        if (sysUserDO.getAccountEmail().isEmpty()) {
            errorMessage = "用户未绑定邮箱";
            return errorMessage;
        }
        String redisKey = "doge:account:changePassword" + "_" + userId;
        String verificationCode;
        verificationCode = RandomUtil.randomNumbers(6);
        redisTemplate.opsForValue().set(redisKey,verificationCode, 5, TimeUnit.MINUTES);
        //发送邮件
        MailRequest mailRequest = new MailRequest();
        mailRequest.setSubject(webSiteName + "用户密码修改验证码");
        mailRequest.setSendTo(accountEmail);
        String emailContent = changePasswordTemplate
                .replace("${website.link}", webSiteLink)
                .replace("${website.logo}", webSiteLogo)
                .replace("${website.name}", webSiteName)
                .replace("${code}", verificationCode);
        mailRequest.setText(emailContent);
        sendMailService.sendHtmlMail(mailRequest);
        return null;
    }

    @Override
    public String verifyCodeForChangePassword(int userId, String code) {
        String errorMessage;
        String redisKey = "doge:account:changePassword" + "_" + userId;
        Object redisValue = redisTemplate.opsForValue().get(redisKey);
        if (redisValue == null) {
            errorMessage = "验证码已过期";
            return errorMessage;
        } else {
            if (!redisValue.toString().equals(code)) {
                errorMessage = "验证码无效";
                return errorMessage;
            }
        }
        return null;
    }

    @Override
    public String changePassword(int userId, String password, PasswordChangeTypeEnum passwordChangeType) {
        String errorMessage;
        int minLength = 6;
        if (password.length() < minLength) {
            errorMessage = "密码不能短于6位！";
            return errorMessage;
        }
        String newPassword = new BCryptPasswordEncoder().encode(RsaUtils.decrypt(password));
        baseMapper.changePassword(userId, newPassword);
        switch (passwordChangeType) {
            case CHANGE -> {
                noticeService.sendChangePasswordRemind(userId);
            }
            case RESET -> {
                noticeService.sendResetPasswordRemind(userId);
            }
            default -> {

            }
        }
        return null;
    }

    @Override
    public SendVerificationCodeForPasswordResultDTO sendVerificationCodeForFindPassword(String username) {
        SendVerificationCodeForPasswordResultDTO sendVerificationCodeForPasswordResultDTO = new SendVerificationCodeForPasswordResultDTO();
        //检查是否存在指定用户名
        QueryWrapper<SysUserDO> wrapper = new QueryWrapper();
        wrapper.eq("username", username);
        SysUserDO sysUserDO = baseMapper.selectOne(wrapper);
        if (sysUserDO == null) {
            sendVerificationCodeForPasswordResultDTO.setErrorMessage("该用户不存在");
            return sendVerificationCodeForPasswordResultDTO;
        }
        //检查指定用户名是否已绑定邮箱
        String accountEmail = sysUserDO.getAccountEmail();
        if (sysUserDO.getAccountEmail().isEmpty()) {
            sendVerificationCodeForPasswordResultDTO.setErrorMessage("用户未绑定邮箱");
            return sendVerificationCodeForPasswordResultDTO;
        }
        sendVerificationCodeForPasswordResultDTO.setEmail(accountEmail);
        String uuid = UUID.randomUUID().toString();
        sendVerificationCodeForPasswordResultDTO.setToken(uuid);
        String verificationCode = RandomUtil.randomNumbers(6);
        String redisKey = "doge:account:findPassword" + "_" + uuid + "_" + verificationCode;
        redisTemplate.opsForValue().set(redisKey, sysUserDO.getId().toString(), 5, TimeUnit.MINUTES);
        //发送邮件
        MailRequest mailRequest = new MailRequest();
        mailRequest.setSubject(webSiteName + "用户找回密码验证码");
        mailRequest.setSendTo(accountEmail);
        String emailContent = findPasswordTemplate
                .replace("${website.link}", webSiteLink)
                .replace("${website.logo}", webSiteLogo)
                .replace("${website.name}", webSiteName)
                .replace("${code}", verificationCode);
        mailRequest.setText(emailContent);
        sendMailService.sendHtmlMail(mailRequest);
        return sendVerificationCodeForPasswordResultDTO;
    }

    @Override
    public SimpleTokenResultDTO verifyCodeForFindPassword(String token, String code) {
        SimpleTokenResultDTO simpleTokenResultDTO = new SimpleTokenResultDTO();
        String redisKey = "doge:account:findPassword" + "_" + token + "_" + code;
        Object redisValue = redisTemplate.opsForValue().get(redisKey);
        if (redisValue == null) {
            simpleTokenResultDTO.setErrorMessage("验证码已过期");
            return simpleTokenResultDTO;
        }
        String uuid = UUID.randomUUID().toString();
        redisKey = "doge:account:findPassword" + "_" + uuid;
        redisTemplate.opsForValue().set(redisKey, redisValue, 5, TimeUnit.MINUTES);
        simpleTokenResultDTO.setToken(uuid);
        return simpleTokenResultDTO;
    }

    @Override
    public String changePasswordForFindPassword(String token, String password) {
        String errorMessage;
        String redisKey = "doge:account:findPassword" + "_" + token;
        Object redisValue = redisTemplate.opsForValue().get(redisKey);
        if (redisValue == null) {
            errorMessage = "验证码已过期";
            return errorMessage;
        }
        int userId = Integer.parseInt(redisValue.toString());
        String result = changePassword(userId, password, PasswordChangeTypeEnum.RESET);
        return result;
    }
}
