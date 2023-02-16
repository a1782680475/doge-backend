package com.doge.utils;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

/**
 * 地址获取工具类
 *
 * @author shixinyu
 * @date 2021-09-02 11:24
 */
public class AddressUtils {
    public static final String IP_URL = "https://whois.pconline.com.cn/ipJson.jsp";
    public static final String UNKNOWN = "XX XX";
    public static final String KEY_ADDRESS = "addr";
    public static String getRealAddressByIp(String ip) {
        String address = UNKNOWN;
        // 内网不查询
        if (NetUtil.isInnerIP(ip)) {
            return "内网IP";
        }
        try {
            HashMap<String, Object> paramMap = new HashMap<>(2);
            paramMap.put("ip", ip);
            paramMap.put("json", true);
            String rspStr= HttpUtil.get(IP_URL, paramMap);
            if (StrUtil.hasEmpty(rspStr)) {
                return UNKNOWN;
            }
            JSONObject obj = JSONObject.parseObject(rspStr);
            return obj.getString(KEY_ADDRESS);
        } catch (Exception e) {

        }
        return address;
    }
}

