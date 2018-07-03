package com.module.service;


import com.core.base.service.BaseModelService;
import com.core.util.IdGenerator;
import com.module.config.wechat_mp.WechatMpProperties;
import com.module.entity.WechatInfo;
import com.module.mapper.WechatInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chc
 * @create 2017-10-26 17:30
 **/
@Service("wxMpCacheService")
public class WechatInfoService extends BaseModelService<WechatInfoMapper,WechatInfo> {

    @Autowired
    WechatMpProperties wechatMpProperties;


    /**
     * 启动初始化微信全局的信息
     */
    public WechatInfo initInfo(String appid){
        //查询WechatInfo的数据
        WechatInfo wechatInfo = this.selectByUniqueColumn("appId",appid);
        if(wechatInfo==null){
            //没有就创建
            wechatInfo = new WechatInfo();
            wechatInfo.setAppId(appid);
            wechatInfo.setId(IdGenerator.generate());
            this.insert(wechatInfo);
        }


        return wechatInfo;
    }

    /**
     * 记录最新的AccessToken信息
     * 可以保持到数据库或者文件中
     * 用户初始化时候用
     */
    public void saveNewAccessToken(String accessToken, long expirationDate){
        WechatInfo wechatInfo = this.selectByUniqueColumn("appId",wechatMpProperties.getAppId());
        wechatInfo.setWechat_access_token(accessToken);
        wechatInfo.setToken_expire_time(expirationDate);
        this.updateById(wechatInfo);
    }

    /**
     * 记录最新的JsapiTicket信息
     * 可以保持到数据库或者文件中
     * 用户初始化时候用
     */
    public void saveNewJsapiTicket(String jsapiTicket, long expirationDate){
        WechatInfo wechatInfo = this.selectByUniqueColumn("appId",wechatMpProperties.getAppId());
        wechatInfo.setWechat_jsapi_ticket(jsapiTicket);
        wechatInfo.setJsapi_expire_time(expirationDate);
        this.updateById(wechatInfo);
    }

    /**
     * 记录最新CardApiTicket信息
     * @param cardApiTicket
     * @param expirationDate
     */
    public void saveNewCardApiTicket(String cardApiTicket,long expirationDate){
        WechatInfo wechatInfo = this.selectByUniqueColumn("appId",wechatMpProperties.getAppId());
        wechatInfo.setWechat_cardapi_ticket(cardApiTicket);
        wechatInfo.setCardapi_expire_time(expirationDate);
        this.updateById(wechatInfo);
    }

}
