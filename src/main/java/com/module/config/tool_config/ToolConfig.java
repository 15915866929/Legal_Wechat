package com.module.config.tool_config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

/**
 * 基础信息初始化
 * @author chc
 * @create 2017-11-20 9:27
 **/
@Data
@Configuration
public class ToolConfig {
    static{
        try {
            localIP= InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }

        notInterceptMethodName=new String[]{"index","userInfo","testInfo","sendSms"
                ,"showImage","lawFirmIndex","legalAid","mediate","notarize","lawer","getJsapiSignature"};
    }

    /**
     * 不拦截的方法名
     */
    public static String[] notInterceptMethodName;
    /**
     * 本地局域网ip
     */
    public static String localIP;

    /**
     * 端口号
     */
    @Value("${server.port}")
    public String port;

    /**
     * swagger接口访问ip
     */
    @Value("${swaggerIp}")
    public String swaggerIp;

    /**
     * swagger扫描控制器controller包位置
     */
    @Value("${swaggerBasePackage}")
    public String swaggerBasePackage;

    /**
     * swagger标题
     */
    @Value("${swaggerTitle}")
    public String swaggerTitle;

    /**
     * 授权的token名
     */
    @Value("${tokenName}")
    public String tokenName;
    /**
     * 加密参数名
     */
    @Value("${cryptParamsName}")
    public String cryptParamsName;

    /**
     * 请求是否需要加密
     */
    @Value("${cryptBool}")
    public boolean cryptBool=true;

    /**
     * 返回结果是否需要加密
     */
    @Value("${resultCryptBool}")
    public boolean resultCryptBool=true;

    /**
     * 刷新token的方法名
     */
    @Value("${refreshToken}")
    public String refreshToken;

    public String getSwaggerHost(){
        return getSwaggerIp()+":"+getPort();
    }

}
