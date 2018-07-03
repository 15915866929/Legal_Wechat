package com.module.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chc
 * @create 2017-11-23 17:13
 **/
@Data
public class WxJsapiSignatureVo {
    @ApiModelProperty(value = "公众号appId")
    private String appId;
    @ApiModelProperty(value = "签名的随机串")
    private String nonceStr;
    @ApiModelProperty(value = "签名的时间戳")
    private long timestamp;
    @ApiModelProperty(value = "地址")
    private String url;
    @ApiModelProperty(value = "签名")
    private String signature;
}
