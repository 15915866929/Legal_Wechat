package com.module.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chc
 * @create 2017-11-21 16:45
 **/
@Data
public class FileInfoVo {
    @ApiModelProperty(value = "文件id")
    private String fileId;
    @ApiModelProperty(value = "文件名")
    private String fileName;
}
