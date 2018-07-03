package com.module.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chc
 * @create 2017-11-18 11:33
 **/
@Data
public class LegalAidSaveVo {

    @ApiModelProperty(value = "申请人名字")
    private String applicant_name;
    @ApiModelProperty(value = "联系方式")
    private String phone;
    @ApiModelProperty(value = "现居住地")
    private String address;
    @ApiModelProperty(value = "户籍地")
    private String domicile;
    @ApiModelProperty(value = "申请诉求")
    private String appeal;
    @ApiModelProperty(value = "事实与理由")
    private String reason;
    @ApiModelProperty(value = "短信验证码")
    private String verificationCode;
}
