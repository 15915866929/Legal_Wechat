package com.module.service;

import com.aliyuncs.exceptions.ClientException;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.cache.CacheService;
import com.core.exception.ResultException;
import com.core.util.StringUtils;
import com.module.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author chc
 * @create 2017-11-18 9:51
 **/
@Service("smsService")
public class SmsService {

    /**
     * 电话验证码缓存时间
     **/
    private final static long PHONE_CACHE_TIME = 10L * 60;

    @Autowired
    private SmsAliyunService smsAliyunService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private UserInfoService userInfoService;

    public void sendVerificationCode(String phone,String openId) {
        String SmsTempateCode= "SMS_66805166";
        Map<String,String> smsParam = new HashMap<>();
        String verificationCode = getVerificationCode();
        smsParam.put("code",verificationCode);
        smsParam.put("product","法律援助");
        //发送短信验证码
        try {
            smsAliyunService.sendSms(SmsTempateCode,smsParam,phone);
        } catch (ClientException e) {
            e.getStackTrace();
            throw new ResultException("发送短信失败");
        }
        //记录短信验证码信息
        cacheService.put(phone, verificationCode, PHONE_CACHE_TIME);
//        daoFactory.getSmsCodeDao().saveSmsCode(verificationCode,openId,phone, DateUtil.getSecondLongTime());
    }

    public void sendNoticeSms(String[] phone,String userName) {
        String SmsTempateCode= "SMS_112930012";
        Map<String,String> smsParam = new HashMap<>();
        smsParam.put("name","");
        smsParam.put("username",userName);
        try {
            smsAliyunService.sendSms(SmsTempateCode,smsParam,phone);
        } catch (ClientException e) {
            e.getStackTrace();
            throw new ResultException("发送短信失败");
        }

    }

    /**
     * 发送短信给操作员
     * @param username
     */
    public void sendNoticeSms(String username) {
        Condition condition = Condition.create();
        condition.addExpression(Expression.gt("type",2));
        condition.addExpression(Expression.eq("department_type",1));
        condition.addExpression(Expression.eq("status",1));
        condition.addExpression(Expression.eq("can_receive_SMS",1));
        List<UserInfo> userInfos = this.userInfoService.selectAll(condition);
        List<String> phoneList = new ArrayList<>();
        for(UserInfo userInfo:userInfos){
            String phone = userInfo.getContactPhone();
            phoneList.add(phone);
        }
        String[] phonetoArray = phoneList.toArray(new String[]{});
        sendNoticeSms(phonetoArray,username);
    }

    /**
     * 校验电话验证码是否正确
     * @param phone
     * @param verificationCode
     * @return
     */
    public boolean checkSmsCode(String phone,String verificationCode){
        String code = (String) cacheService.get(phone);
        if(StringUtils.isEmpty(code)){
            return false;
        }
        if(!code.equals(verificationCode)){
            return false;
        }
        return true;
    }

    /**
     * 获取随机验证码
     *
     * @return
     */
    private String getVerificationCode(){
        int max = 999999;
        int min = 100000;
        int code = new Random().nextInt(max - min) + min;
        return String.valueOf(code);
    }




//    public static void main(String[] args) {
//        int max = 999999;
//        int min = 100000;
//        for(int i=0;i<30;i++){
//            System.out.println(new Random().nextInt(max - min) + min);
//        }
//
//    }
}
