package com.module.service;

import com.core.base.service.BaseModelService;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.base.support.Order;
import com.core.base.support.Page;
import com.core.exception.ResultException;
import com.core.util.IdGenerator;
import com.module.entity.Mediate;
import com.module.entity.UserInfo;
import com.module.mapper.MediateMapper;
import com.module.vo.MediateQueryVo;
import com.module.vo.MediateSaveVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人民调解
 * @author chc
 * @create 2017-11-18 11:15
 **/
@Service("mediateService")
public class MediateService extends BaseModelService<MediateMapper,Mediate> {

    @Autowired
    private SmsService smsService;

    /**
     * 保存人民调解申请信息
     * @param openId
     * @param mediateSaveVo 申请人信息
     */
    public void saveMediate(String openId, MediateSaveVo mediateSaveVo) {
        //校验验证码是否正确
        String verificationCode = mediateSaveVo.getVerificationCode();
        //当前时间
//        long nowLongTime = DateUtil.getSecondNowLongTime();
        //判断短信验证码是否有效
        boolean checkSmsCode = smsService.checkSmsCode( mediateSaveVo.getPhone(),verificationCode);
        if(!checkSmsCode){
            //失效
            throw new ResultException("短信验证码错误");
        }
        //保存申请信息
        Mediate mediate = new Mediate();
        BeanUtils.copyProperties(mediateSaveVo,mediate);
        mediate.setId(IdGenerator.generate());
        mediate.setOpenid(openId);
        mediate.setStatus(0);
        mediate.setCtime(new Date());
        this.insert(mediate);
        //发送通知短信到操作员
        smsService.sendNoticeSms(mediate.getName());
    }

    /**
     * 人民调解申请申请查询
     * @return
     */
    public List<MediateQueryVo> findMediate(){
//        List<MediateQueryVo> mediateQueryVoList = daoFactory.getMediateDao().findAllToReurnModel(new Document(), null, MediateQueryVo.class);
//        return mediateQueryVoList;
        return null;
    }

}
