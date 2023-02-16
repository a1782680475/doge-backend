package com.doge.service.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Ant分页
 *
 * @author shixinyu
 * @date 2021-06-21 10:22
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AntPageDTO<T> {
    private Boolean success;
    private List<T> data;
    private Long total;
    private Long pageSize;
    private Long current;

    public AntPageDTO init(IPage<T> page) {
        this.success = true;
        this.data = page.getRecords();
        this.total = page.getTotal();
        this.current = page.getCurrent();
        this.pageSize = page.getSize();
        return this;
    }
}
