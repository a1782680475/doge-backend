package com.doge.service.entity;

import lombok.Data;

/**
 * 简单Token结果
 *
 * @author shixinyu
 * @date 2023/3/28 17:04
 */
@Data
public class SimpleTokenResultDTO {
    private String token ;
    private String errorMessage;
}
