package com.module.service;


import com.core.base.service.BaseModelService;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.exception.ResultException;
import com.core.util.IdGenerator;
import com.module.entity.LegalAidGuide;
import com.module.mapper.LegalAidGuideMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class LegalAidGuideService extends BaseModelService<LegalAidGuideMapper,LegalAidGuide> {

    @Autowired
    private LegalAidGuideMapper LegalAidGuideMapper;

    public LegalAidGuide findLegalAidGuide(){
//        Condition condition = Condition.create();
//        condition.addExpression(Expression.eq("status",1));
        List<LegalAidGuide> legalAidGuideList = this.LegalAidGuideMapper.selectByStatus(1);
        if(legalAidGuideList!=null && legalAidGuideList.size()>0){
            return legalAidGuideList.get(0);
        }
        return null;
    }

}
