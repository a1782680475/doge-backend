package com.doge.entity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 上传结果
 *
 * @author shixinyu
 * @date 2021-09-30 16:33
 */
@Data
@ApiModel
@AllArgsConstructor
public class UploadResultVO {
    @ApiModelProperty(value = "文件名")
    private String name;
    @ApiModelProperty(value = "文件url")
    private String url;
}
