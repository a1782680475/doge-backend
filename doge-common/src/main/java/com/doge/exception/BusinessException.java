package com.doge.exception;

/**
 * 业务异常类
 *
 * @author shixinyu
 * @date 2021-06-09 16:58
 */
public class BusinessException extends RuntimeException {
    private String code;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "BusinessException(code=" + this.getCode() + ")";
    }
}

