package com.module.service;

import com.core.base.service.BaseModelService;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.exception.ResultException;
import com.core.util.BeanUtils;
import com.core.util.IdGenerator;
import com.core.util.StringUtils;
import com.module.entity.*;
import com.module.mapper.LawyerMapper;
import com.module.mapper.LegalAidMapper;
import com.module.vo.LegalAidSaveVo;
import com.module.vo.LegalAidVo;
import com.module.vo.MediateVo;
import com.module.vo.NotarizeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 法律援助相关服
 * @author chc
 * @create 2017-11-18 11:15
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class LawyerService extends BaseModelService<LawyerMapper,Lawyer> {

    @Autowired
    private StreetService streetService;
    @Autowired
    private LawFirmService lawFirmService;

    public List<Lawyer> queryAllLawyer(){
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));
        List<Lawyer> lawyerList = this.selectAll(condition);
        for (Lawyer lawyer : lawyerList) {
            if(StringUtils.isNotEmpty(lawyer.getStreet_Id())){
                Street street = this.streetService.select(lawyer.getStreet_Id());
                lawyer.setStreetName(street.getName());
            }
            if(StringUtils.isNotEmpty(lawyer.getLawFirm_Id())){
                LawFirm lawFirm = this.lawFirmService.select(lawyer.getLawFirm_Id());
                lawyer.setLawFirmName(lawFirm.getName());
            }
        }
        return lawyerList;
    }

    public Lawyer queryById(String id){
        Lawyer lawyer = this.select(id);
        if(lawyer==null){
            throw new ResultException("异常！没有该律师信息");
        }
        if(StringUtils.isNotEmpty(lawyer.getStreet_Id())){
            Street street = this.streetService.select(lawyer.getStreet_Id());
            lawyer.setStreetName(street.getName());
        }
        if(StringUtils.isNotEmpty(lawyer.getLawFirm_Id())){
            LawFirm lawFirm = this.lawFirmService.select(lawyer.getLawFirm_Id());
            lawyer.setLawFirmName(lawFirm.getName());
        }
        return lawyer;
    }
}
