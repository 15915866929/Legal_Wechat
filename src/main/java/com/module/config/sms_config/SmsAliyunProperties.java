package com.module.config.sms_config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author chc
 * @create 2017-11-16 9:40
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "sms.aliyun")
public class SmsAliyunProperties {

    /**
     * 阿里云AccessId
     */
    private String accessId;
    /**
     * 阿里云AccessKey
     */
    private String accessKey;
    /**
     * MNS服务的接入地址
     */
    private String MNSEndpoint;
    /**
     * 发送短信使用的主题
     */
    private String topic;
    /**
     * 发送短信使用的签名
     */
    private String signName;
}
