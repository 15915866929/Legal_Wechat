package com.module.service;

import com.core.base.service.BaseModelService;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.util.StringUtils;
import com.module.entity.LawFirm;
import com.module.entity.Lawyer;
import com.module.entity.Street;
import com.module.mapper.LawFirmMapper;
import com.module.mapper.LawyerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 法律援助相关服
 * @author chc
 * @create 2017-11-18 11:15
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class LawFirmService extends BaseModelService<LawFirmMapper,LawFirm> {

    @Autowired
    private StreetService streetService;
    @Autowired
    private LawyerService lawyerService;

    public List<LawFirm> queryAllLawFirm(){
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));
        //按照创建时间排序
        condition.addOrder("ctime",1);
        List<LawFirm> lawFirmList = this.selectAll(condition);
        for (LawFirm lawFirm : lawFirmList) {
            if(StringUtils.isNotEmpty(lawFirm.getStreet_Id())){
                Street street = this.streetService.select(lawFirm.getStreet_Id());
                lawFirm.setStreetName(street.getName());
            }
        }
        return lawFirmList;
    }

    public LawFirm queryById(String id){
        LawFirm lawFirm = this.select(id);
        if(StringUtils.isNotEmpty(lawFirm.getStreet_Id())) {
            Street street = this.streetService.select(lawFirm.getStreet_Id());
            lawFirm.setStreetName(street.getName());
        }
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("lawFirm_Id",id));
        condition.addExpression(Expression.eq("status",1));
        if(StringUtils.isNotEmpty(lawFirm.getStreet_Id())){
            Street street = this.streetService.select(lawFirm.getStreet_Id());
            lawFirm.setStreetName(street.getName());
        }
        List<Lawyer> lawyerList = this.lawyerService.selectAll(condition);
        lawFirm.setLawyerList(lawyerList);
        return lawFirm;
    }
}
