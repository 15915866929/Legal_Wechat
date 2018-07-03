package com.module.service;

import com.core.base.service.BaseModelService;
import com.core.exception.ResultException;
import com.core.util.IdGenerator;
import com.module.entity.Notarize;
import com.module.mapper.NotarizeMapper;
import com.module.vo.NotarizeQueryVo;
import com.module.vo.NotarizeSaveVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 公证服务
 * @author chc
 * @create 2017-11-18 11:15
 **/
@Service("notarizeService")
public class NotarizeService extends BaseModelService<NotarizeMapper,Notarize> {

    @Autowired
    private SmsService smsService;

    /**
     * 保存公证服务预约申请信息
     * @param openId
     * @param notarizeSaveVo 申请人信息
     */
    public void saveNotarize(String openId, NotarizeSaveVo notarizeSaveVo) {
        //校验验证码是否正确
        String verificationCode = notarizeSaveVo.getVerificationCode();
        //当前时间
//        long nowLongTime = DateUtil.getSecondNowLongTime();
        //判断短信验证码是否有效
        boolean checkSmsCode = smsService.checkSmsCode( notarizeSaveVo.getPhone(),verificationCode);
        if(!checkSmsCode){
            //失效
            throw new ResultException("短信验证码错误");
        }
        //保存申请信息
        Notarize notarize = new Notarize();
        BeanUtils.copyProperties(notarizeSaveVo,notarize);
        notarize.setId(IdGenerator.generate());
        notarize.setOpenid(openId);
        notarize.setStatus(0);
        notarize.setCtime(new Date());
        this.insert(notarize);
        //发送通知短信到操作员
        smsService.sendNoticeSms(notarize.getName());
    }

//    /**
//     * 发送短信给操作员
//     * @param username
//     * @throws BaseException
//     */
//    private void sendNoticeSms(String username) throws BaseException{
//        Document find = new Document("type", new Document("$gt", 2)).append("department_type",1).append("status",1).append("can_receive_SMS",1);
//        List<UserInfo> userInfos = daoFactory.getUserInfoDao().findAllToReurnModel(find, null, UserInfo.class);
//        List<String> phoneList = new ArrayList<>();
//        for(UserInfo userInfo:userInfos){
//            String phone = userInfo.getContactPhone();
//            phoneList.add(phone);
//        }
//        String[] phonetoArray = phoneList.toArray(new String[]{});
//        serviceFactory.getSmsService().sendNoticeSms(phonetoArray,username);
//    }

    /**
     * 公证服务预约申请查询
     * @return
     */
    public List<NotarizeQueryVo> findNotarize(){
//        List<NotarizeQueryVo> notarizeQueryVoList = daoFactory.getLegalAidDao().findAllToReurnModel(new Document(), null, NotarizeQueryVo.class);
//        return notarizeQueryVoList;
        return null;
    }

}
