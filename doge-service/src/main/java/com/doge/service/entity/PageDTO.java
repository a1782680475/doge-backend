package com.doge.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页DTO类
 *
 * @author shixinyu
 * @date 2021-06-29 09:17
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageDTO {
    private Integer current;
    private Integer pageSize;
}
