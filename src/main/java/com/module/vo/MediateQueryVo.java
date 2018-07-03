package com.module.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chc
 * @create 2017-11-18 11:33
 **/
@Data
public class MediateQueryVo {

    @ApiModelProperty(value = "id")
    private String mediate_id;
    @ApiModelProperty(value = "现居住地")
    private String address;
    @ApiModelProperty(value = "户籍地")
    private String household;
    @ApiModelProperty(value = "所在村居")
    private String village;
    @ApiModelProperty(value = "调解事宜")
    private String mediate_remark;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "电话")
    private String phone;


}
