package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.cache.CacheService;
import com.core.protocol.BaseResponse;
import com.core.util.IdGenerator;
import com.module.config.wechat_mp.WechatMpProperties;
import com.module.entity.WechatUserInfo;
import com.module.service.WechatUserInfoService;
import com.module.vo.WxJsapiSignatureVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chc
 * @create 2017-10-27 15:32
 **/
@RestController
@RequestMapping("/wechat")
@Api(value = "WechatMpController", description = "微信用户信息Api")
public class WechatMpController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(WechatMpController.class);
    /**
     * 用户信息缓存时间
     **/
    private final static long USER_CACHE_TIME = 30L * 60;

    @Autowired
    private CacheService cacheService;
    @Autowired
    WechatMpProperties properties;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WechatUserInfoService wechatUserInfoService;


    private static String url = "http://serv.teamfort.cn/Legal_Wechat";

    private static String legalAidUrl=url+"/wechat/legalAid";
    private static String mediateUrl=url+"/wechat/mediate";
    private static String notarize=url+"/wechat/notarize";
    private static String lawer=url+"/wechat/lawer";
    @GetMapping("/index")
    public ModelAndView index(String type) throws WxErrorException {
        if(type==null){
            type="";
        }
        String redirectUri = "http://serv.teamfort.cn/Legal_Wechat/wechat/userInfo";
        switch (type) {
            case "legalAid":
                redirectUri=legalAidUrl;
                break;
            case "mediate":
                redirectUri=mediateUrl;
                break;
            case "notarize":
                redirectUri=notarize;
                break;
            case "lawer":
                redirectUri=lawer;
                break;
            default:
//                redirectUri=url+properties.getRedirectUri();
                break;
        }

        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(redirectUri, WxConsts.OAUTH2_SCOPE_USER_INFO, null);
        try {
            redirectUrl = URLDecoder.decode(redirectUrl, "utf-8");
            log.info(redirectUrl);
            //response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:" + redirectUrl);
//        String redirectUri = "http://118.24.152.132/index.html";
//        String skey = wxMpUserInfoSkey( "",  "");
//        Map<String,String> model = new HashMap<>();
//        model.put("skey",skey);
//        return new ModelAndView("redirect:" + redirectUri,model);
        //return "redirect:" + redirectUrl;
    }


    @GetMapping("/userInfo")
    public ModelAndView  userInfo(String code,String state) throws WxErrorException {
        String skey = wxMpUserInfoSkey( code,  state);
        Map<String,String> model = new HashMap<>();
        model.put("skey",skey);
        String redirectUri = "http://law.th3ee-win.com/index.html";
        return new ModelAndView("redirect:" + redirectUri,model);
//        return new ModelAndView("index",model);
    }

    @GetMapping("/legalAid")
    public ModelAndView legalAid(String code,String state) throws WxErrorException{
        String skey = wxMpUserInfoSkey( code,  state);
        Map<String,String> model = new HashMap<>();
        model.put("skey",skey);
        model.put("type","legalAid");
        String redirectUri = "http://law.th3ee-win.com/index.html";
        return new ModelAndView("redirect:" + redirectUri,model);
    }

    @GetMapping("/mediate")
    public ModelAndView mediate(String code,String state) throws WxErrorException{
        String skey = wxMpUserInfoSkey( code,  state);
        Map<String,String> model = new HashMap<>();
        model.put("skey",skey);
        model.put("type","mediate");
        String redirectUri = "http://law.th3ee-win.com/index.html";
        return new ModelAndView("redirect:" + redirectUri,model);
    }

    @GetMapping("/notarize")
    public ModelAndView notarize(String code,String state) throws WxErrorException{
        String skey = wxMpUserInfoSkey( code,  state);
        Map<String,String> model = new HashMap<>();
        model.put("skey",skey);
        model.put("type","notarize");
        String redirectUri = "http://law.th3ee-win.com/index.html";
        return new ModelAndView("redirect:" + redirectUri,model);
    }

    @GetMapping("/lawer")
    public ModelAndView lawer(String code,String state) throws WxErrorException{
        String skey = wxMpUserInfoSkey( code,  state);
        Map<String,String> model = new HashMap<>();
        model.put("skey",skey);
        model.put("type","lawer");
        String redirectUri = "http://law.th3ee-win.com/index.html";
        return new ModelAndView("redirect:" + redirectUri,model);
    }

    /**
     * 获取微信签名信息接口
     * @param url
     * @return
     * @throws WxErrorException
     */
    @ApiOperation(value = "获取微信签名信息接口")
    @PostMapping("/getJsapiSignature")
    public BaseResponse getJsapiSignature(@ApiParam(value = "地址") @RequestParam String url) throws WxErrorException{
        WxJsapiSignature jsapiSignature = wxMpService.createJsapiSignature(url);
        WxJsapiSignatureVo wxJsapiSignatureVo = new WxJsapiSignatureVo();
        BeanUtils.copyProperties(jsapiSignature,wxJsapiSignatureVo);
        BaseResponse resp = new BaseResponse();
        resp.setResult(wxJsapiSignatureVo);
        return resp;
    }



    private String wxMpUserInfoSkey(String code,String state) throws WxErrorException{
        //根据code获取OAuth2网页授权的accessToken和openId
        WxMpOAuth2AccessToken auth2AccessToken = findAuth2AccessToken(code);
        //获取用户信息
        WxMpUser wxUserInfo = findWxUserInfo(auth2AccessToken);
        //更新或保存微信用户信息
        wechatUserInfoService.saveWeChatUserInfo(wxUserInfo);
        //根据openId查询skey
//        Skey skey = serviceFactory.getAuthService().refreshSkey(auth2AccessToken.getOpenId());
        String skey = IdGenerator.generate();
        cacheService.put(skey,wxUserInfo.getOpenId(),USER_CACHE_TIME);
        log.info("加入的openId:"+wxUserInfo.getOpenId());
//        cacheService.put(skey,"-1");
        return skey;
    }

    /**
     * 根据code获取OAuth2网页授权的
     * AccessToken和openId
     * @param code
     * @return
     */
    private WxMpOAuth2AccessToken findAuth2AccessToken(String code) throws WxErrorException {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        return wxMpOAuth2AccessToken;
    }

    /**
     * 获取微信用户的信息
     * 只要关注过都可以获取，就是之后取消了也一样可以
     * @param wxMpOAuth2AccessToken
     * @return
     */
    private WxMpUser findWxUserInfo(WxMpOAuth2AccessToken wxMpOAuth2AccessToken) throws WxErrorException{
        WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        return wxMpUser;
    }

    @PostMapping("/getWecahtUserInfo")
    public BaseResponse getWecahtUserInfo(String skey){
        BaseResponse resp = new BaseResponse();
//        String openId = (String)cacheService.get(skey);
//        log.info("查处的openId:"+openId);
        WechatUserInfo wecahtUserInfo = this.wechatUserInfoService.getWecahtUserInfo(skey);
        resp.setResult(wecahtUserInfo);
        return resp;
    }


}







