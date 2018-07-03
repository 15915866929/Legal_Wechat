package com.module.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author chc
 * @create 2017-11-18 11:33
 **/
@Data
public class NotarizeSaveVo {

    @ApiModelProperty(value = "预约地址")
    private String appoint_address;
    @ApiModelProperty(value = "预约业务")
    private String notarize_type;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "短信验证码")
    private String verificationCode;
    @ApiModelProperty(value = "预约时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date notarize_time;

}
