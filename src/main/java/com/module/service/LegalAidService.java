package com.module.service;

import com.core.base.service.BaseModelService;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.exception.ResultException;
import com.core.util.BeanUtils;
import com.core.util.IdGenerator;
import com.module.entity.LegalAid;
import com.module.entity.Mediate;
import com.module.entity.Notarize;
import com.module.mapper.LegalAidMapper;
import com.module.vo.LegalAidSaveVo;
import com.module.vo.LegalAidVo;
import com.module.vo.MediateVo;
import com.module.vo.NotarizeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 法律援助相关服
 * @author chc
 * @create 2017-11-18 11:15
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class LegalAidService extends BaseModelService<LegalAidMapper,LegalAid> {

    @Autowired
    private SmsService smsService;
    @Autowired
    private MediateService mediateService;
    @Autowired
    private NotarizeService notarizeService;

    /**
     * 保存法律援助的申请信息
     * @param openId
     * @param aidSaveVo 申请人信息
     */
    public void saveLegalAid(String openId, LegalAidSaveVo aidSaveVo) {
        //校验验证码是否正确
        String verificationCode = aidSaveVo.getVerificationCode();
        //当前时间
//        long nowLongTime = DateUtil.getSecondNowLongTime();
        //判断短信验证码是否有效
        boolean checkSmsCode = smsService.checkSmsCode( aidSaveVo.getPhone(),verificationCode);
        if(!checkSmsCode){
            //失效
            throw new ResultException("短信验证码错误");
        }
        //保存申请信息
        LegalAid legalAid = new LegalAid();
        BeanUtils.copyPropertiesWithoutNull(aidSaveVo,legalAid);
        legalAid.setOpenid(openId);
        legalAid.setCtime(new Date());
        legalAid.setLegalAid_Id(IdGenerator.generate());
        legalAid.setId(legalAid.getLegalAid_Id());
        legalAid.setStatus(0);
        this.insert(legalAid);
        //发送通知短信到操作员
        smsService.sendNoticeSms(legalAid.getApplicant_name());
    }

    public List findAllReq(String openId){
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("openid",openId));
        List<LegalAid> legalAidList = this.selectAll(condition);
        List<Mediate> mediateList = this.mediateService.selectAll(condition);
        List<Notarize> notarizeList = this.notarizeService.selectAll(condition);

        List resultList = new ArrayList<>();
        for (LegalAid legalAid : legalAidList) {
            LegalAidVo legalAidVo = new LegalAidVo();
            BeanUtils.copyPropertiesWithoutNull(legalAid,legalAidVo);
            legalAidVo.setType("legalAid");
            resultList.add(legalAidVo);
        }
        for (Mediate mediate : mediateList) {
            MediateVo mediateVo = new MediateVo();
            BeanUtils.copyProperties(mediate,mediateVo);
            mediateVo.setType("mediate");
            resultList.add(mediateVo);
        }
        for (Notarize notarize : notarizeList) {
            NotarizeVo notarizeVo = new NotarizeVo();
            BeanUtils.copyProperties(notarize,notarizeVo);
            notarizeVo.setType("notarize");
            resultList.add(notarizeVo);
        }
        return resultList;
    }

}
