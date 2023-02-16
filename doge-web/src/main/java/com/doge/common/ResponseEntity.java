package com.doge.common;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 响应实体类
 *
 * @author shixinyu
 * @describe 此实体类按前台umi-request统一的接口规范设计
 * @date 2021-06-09 08:36
 */
@ApiModel
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntity<T> implements Serializable {

    private static final long serialVersionUID = -6547410396516217748L;

    /**
     * 是否成功
     */
    @ApiModelProperty(name = "success", value = "是否成功")
    private boolean success = true;

    /**
     * 数据
     */
    @ApiModelProperty(name = "data", value = "数据")
    private T data;

    /**
     * 错误码
     */
    @ApiModelProperty(name = "errorCode", value = "响应编码：200->成功,400->校验失败,408->登录超时,500->内部服务器异常")
    private int errorCode = ResponseCode.SUCCESS.getCode();

    /**
     * 错误消息
     */
    @ApiModelProperty(name = "errorMessage")
    private String errorMessage;


    public static ResponseEntity build() {
        return new ResponseEntity();
    }

    public static ResponseEntity ok(Object result) {
        return new ResponseEntity(true, result, ResponseCode.SUCCESS.getCode(), StrUtil.EMPTY);
    }

    public static ResponseEntity error(String errorMessage) {
        return new ResponseEntity(false, null, ResponseCode.INTERNAL_ERROR.getCode(), errorMessage);
    }

    public static ResponseEntity error(ResponseCode errorCode, String errorMessage) {
        return new ResponseEntity(false, null, errorCode.getCode(), errorMessage);
    }
}
