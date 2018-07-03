package com.module.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chc
 * @create 2017-11-17 11:18
 **/
@Data
public class StreetAllInfo {
    @ApiModelProperty(value = "主键Id")
    private String street_Id;
    @ApiModelProperty(value = "镇街名字")
    private String name;
}
