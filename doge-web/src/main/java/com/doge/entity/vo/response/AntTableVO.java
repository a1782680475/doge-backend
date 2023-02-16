package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Ant表格
 *
 * @author shixinyu
 * @date 2021-07-02 15:01
 */
@Data
public class AntTableVO<V> {
    @ApiModelProperty(value="是否成功",position = 1)
    private Boolean success;
    @ApiModelProperty(value="数据",position = 2)
    private List<V> data;
    public AntTableVO build(List<V> voList){
        this.success = true;
        this.data = voList;
        return this;
    }
}
