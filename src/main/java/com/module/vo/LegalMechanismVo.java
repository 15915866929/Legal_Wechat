package com.module.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author chc
 * @create 2017-11-17 15:54
 **/
@Data
public class LegalMechanismVo {

    //id
    @ApiModelProperty(value = "机构ID")
    private String legalMechanism_Id;
    //机构名
    @ApiModelProperty(value = "机构名")
    private String name;

    //镇街ID
    @ApiModelProperty(value = "镇街ID")
    private String street_Id;

    //经纬度
    @ApiModelProperty(value = "经纬度")
    private Location location;

    //机构地址
    @ApiModelProperty(value = "机构地址")
    private String address;

    //图片文件id
    @ApiModelProperty(value = "图片文件id")
    private List<String> imageFileIds;

    //联系人
    @ApiModelProperty(value = "联系人")
    private String contact;

    //电话
    @ApiModelProperty(value = "电话")
    private List<String> phones;

    //备注
    @ApiModelProperty(value = "备注")
    private String note;
}
