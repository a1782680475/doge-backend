package com.doge.entity.vo.response;

import com.doge.service.entity.AntPageDTO;
import com.doge.utils.BeanUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Ant表格（含分页）
 *
 * @author shixinyu
 * @date 2021-06-21 10:22
 */
@Getter
@ToString
@ApiModel
public class AntPageVO<D,V> extends AntTableVO{
    @ApiModelProperty(value="是否成功",position = 1)
    private Boolean success;
    @ApiModelProperty(value="数据",position = 2)
    private List<V> data;
    @ApiModelProperty(value="总数",position = 3)
    private Long total;
    @ApiModelProperty(value="总页数",position = 4)
    private Long pageSize;
    @ApiModelProperty(value="当前页",position = 5)
    private Long current;
    public AntPageVO build(AntPageDTO<D> dtoPage,Class<V> voClass){
        this.data = BeanUtils.mapAsList(dtoPage.getData(),voClass);
        this.success = dtoPage.getSuccess();
        this.total = dtoPage.getTotal();
        this.current = dtoPage.getCurrent();
        this.pageSize = dtoPage.getPageSize();
        return this;
    }
}
