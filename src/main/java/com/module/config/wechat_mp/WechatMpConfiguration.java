package com.module.config.wechat_mp;


import com.core.util.IdGenerator;
import com.module.entity.WechatInfo;
import com.module.service.WechatInfoService;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信第三方sdk初始化
 * @author chc
 * @create 2017-10-18 11:21
 **/
@Configuration  //这是一个配置类，与@Service、@Component的效果类似。spring会扫描到这个类，@Bean才会生效
@ConditionalOnClass(WxMpService.class)  //该注解的参数对应的类必须存在，否则不解析该注解修饰的配置类
@EnableConfigurationProperties(WechatMpProperties.class)    ////通过这个注解, 将参数这个类的配置到上下文环境中,//需要在本类中使用的@Autowired注解注入才能生效
public class WechatMpConfiguration {

    @Autowired
    private WechatMpProperties properties;
    @Autowired
    WechatInfoService wechatInfoService;



    /**
     * 初始化微信配置信息
     * @Author chc
     * @Date 2017/10/18 11:35
     * @return
     */
    @Bean
    @ConditionalOnMissingBean   //仅仅在当前上下文中不存在某个对象时，才会实例化一个Bean
    public WxMpConfigStorage configStorage(){
        //Redis版的缓存
       /* WxMpInRedisConfigStorage configStorage = new WxMpInRedisConfigStorage();
        configStorage.setJedis(new Jedis("192.168.0.100", 6379));*/

        //java内存版的缓存
        //WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();

        //基于java内存版的自定义版
        WxMpMyConfigStorage configStorage = new WxMpMyConfigStorage();

        configStorage.setAppId(this.properties.getAppId());
        configStorage.setSecret(this.properties.getSecret());
        configStorage.setToken(this.properties.getToken());
        configStorage.setAesKey(this.properties.getAesKey());

        //初始化微信全局信息（自定义版）
//        WechatInfo wechatInfo = wechatInfoService.initInfo(this.properties.getAppId());
        WechatInfo wechatInfo = new WechatInfo();
        wechatInfo.setAppId(this.properties.getAppId());
        wechatInfo.setId(IdGenerator.generate());
        configStorage.initInfo(wechatInfo);

        return configStorage;
    }



    /**
     * 初始化微信sdk第三方服务
     * @param configStorage
     * @return
     */
    @Bean
    @ConditionalOnMissingBean   //仅仅在当前上下文中不存在某个对象时，才会实例化一个Bean
    public WxMpService wxMpService(WxMpConfigStorage configStorage) {
        //WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.okhttp.WxMpServiceImpl();
//        WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.jodd.WxMpServiceImpl();
//        WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.apache.WxMpServiceImpl();
        WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(configStorage);
        return wxMpService;
    }

}
