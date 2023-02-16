package com.doge.utils.monitor.server;

import lombok.Data;

/**
 * 系统相关信息
 * 
 * @author ruoyi
 */
@Data
public class Sys
{
    /**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器Ip
     */
    private String computerIp;


    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;

}
