package com.doge.aop;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doge.annotation.Log;
import com.doge.entity.BusinessLog;
import com.doge.service.BusinessLogService;
import com.doge.service.entity.BusinessLogDTO;
import com.doge.utils.BeanUtils;
import com.doge.utils.IpUtils;
import com.doge.utils.SecurityUtils;
import lombok.NoArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 业务日志切面
 *
 * @author shixinyu
 * @date 2021-08-30 15:15
 */
@Aspect
@Component
@NoArgsConstructor
public class LogAdvice {
    private BusinessLogService businessLogService;

    @Autowired
    LogAdvice(BusinessLogService businessLogService) {
        this.businessLogService = businessLogService;
    }

    @Pointcut("@annotation(log)")
    public void loggerPointcut(Log log) {
    }

    @Around("loggerPointcut(log)")
    public Object around(ProceedingJoinPoint p, Log log) throws Throwable {
        Object result;
        BusinessLog logInfo = new BusinessLog();
        try {
            Date now = new Date();
            result = p.proceed();
            logInfo.setTitle(log.title());
            logInfo.setRequestTime(now);
            logInfo.setTargetClassName(p.getTarget().getClass().getName());
            logInfo.setTargetMethodName(p.getSignature().getName());
            HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String requestUrl = httpServletRequest.getRequestURI();
            logInfo.setRequestUrl(requestUrl);
            logInfo.setRequestMethod(httpServletRequest.getMethod());
            logInfo.setRequestParams(getParams(p));
            logInfo.setStatus(true);
            logInfo.setResponse(result == null ? null : JSONObject.toJSONString(result));
            logInfo.setResponseTime(new Date());
            logInfo.setOperationUser(SecurityUtils.getUserName());
            logInfo.setOperationIp(IpUtils.getIpAddress(httpServletRequest));
            now = new Date();
            logInfo.setCreateTime(now);
            logInfo.setUpdateTime(now);
        } catch (Throwable e) {
            logInfo.setException(e.getMessage());
            logInfo.setStatus(false);
            //异常抛出
            throw e;
        } finally {
            businessLogService.asyncSave(BeanUtils.map(logInfo, BusinessLogDTO.class));
            ;
        }
        return result;
    }

    private String getParams(ProceedingJoinPoint p) {
        Object[] obj = p.getArgs();
        StringBuffer buffer = new StringBuffer();
        if (obj != null) {
            for (int i = 0; i < obj.length; i++) {
                Object o = obj[i];
                if (o instanceof Model) {
                    continue;
                }
                if (o instanceof MultipartFile) {
                    buffer.append("上传文件名：" + ((MultipartFile) o).getOriginalFilename());
                    continue;
                }
                String parameter = null;
                try {
                    parameter = JSON.toJSONString(o);
                } catch (Exception e) {
                    buffer.append("☺");
                    continue;
                }
                buffer.append(parameter);
                buffer.append("☺");
            }
        }
        String param = StrUtil.removeSuffix(buffer.toString(),"☺");
        return StrUtil.hasEmpty(param) ? null : param;
    }
}
