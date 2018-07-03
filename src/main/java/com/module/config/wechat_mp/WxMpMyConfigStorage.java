package com.module.config.wechat_mp;


import com.module.entity.WechatInfo;
import com.module.service.WechatInfoService;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基于java内存版的缓存功能，进行自定义
 * @author chc
 * @create 2017-10-26 14:53
 **/
public class WxMpMyConfigStorage extends WxMpInMemoryConfigStorage {

    @Autowired
    WechatInfoService wechatInfoService;




    /**
     * 更新全局accessToken缓存信息
     * @param accessToken
     * @param expiresInSeconds
     */
    @Override
    public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {

        super.updateAccessToken(accessToken, expiresInSeconds);
        //记录最新的AccessToken信息
        saveNewAccessToken();
    }

    /**
     * 更新全局的jsapiTicket信息
     * @param jsapiTicket
     * @param expiresInSeconds
     */
    @Override
    public synchronized void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {

        super.updateJsapiTicket(jsapiTicket, expiresInSeconds);
        //记录最新的JsapiTicket信息
        saveNewJsapiTicket();
    }

    /**
     * 更新全局CardApiTicket信息
     * @param cardApiTicket
     * @param expiresInSeconds
     */
    @Override
    public synchronized void updateCardApiTicket(String cardApiTicket, int expiresInSeconds) {
        super.updateCardApiTicket(cardApiTicket, expiresInSeconds);
        //记录最新的CardApiTicket信息
        saveNewCardApiTicket();
    }

    /**
     * 记录最新的CardApiTicket信息
     */
    public void saveNewCardApiTicket(){
        wechatInfoService.saveNewCardApiTicket(getCardApiTicket(),getCardApiTicketExpiresTime());
    }

    /**
     * 记录最新的AccessToken信息
     * 可以保持到数据库或者文件中
     * 用户初始化时候用
     */
    public void saveNewAccessToken(){
        wechatInfoService.saveNewAccessToken(getAccessToken(),getExpiresTime());
    }

    /**
     * 记录最新的JsapiTicket信息
     * 可以保持到数据库或者文件中
     * 用户初始化时候用
     */
    public void saveNewJsapiTicket(){
        wechatInfoService.saveNewJsapiTicket(getJsapiTicket(),getJsapiTicketExpiresTime());
    }

    /**
     * 启动初始化全局accessToken和时间
     * 建议在程序中使用到才进行启动初始化
     */
    public void initInfo(WechatInfo wechatInfo){


        String wechat_access_token=wechatInfo.getWechat_access_token();
        Long token_expire_time=getLongValue(wechatInfo.getToken_expire_time());

        String wechat_jsapi_ticket=wechatInfo.getWechat_jsapi_ticket();
        Long jsapi_expire_time= getLongValue(wechatInfo.getJsapi_expire_time());;

        String wechat_cardapi_ticket = wechatInfo.getWechat_cardapi_ticket();
        Long cardapi_expire_time = getLongValue(wechatInfo.getCardapi_expire_time());

        //初始化全局AccessToken
        setAccessToken(wechat_access_token);
        setExpiresTime(token_expire_time);

        //初始化全局JsapiTicket
        setJsapiTicket(wechat_jsapi_ticket);
        setJsapiTicketExpiresTime(jsapi_expire_time);

        //初始化全局CardApiTicket
        setCardApiTicket(wechat_cardapi_ticket);
        setCardApiTicketExpiresTime(cardapi_expire_time);

    }

    private long getLongValue(Object longTime){
        long valueOf=0l;
        if(longTime==null){
            valueOf=0l;
        }else {
            valueOf = Long.valueOf(longTime.toString());
        }
        return valueOf;
    }

}
