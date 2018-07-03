package com.module.service;

import com.core.base.service.BaseModelService;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.module.entity.LegalMechanism;
import com.module.mapper.LegalMechanismMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author chc
 * @create 2017-11-17 15:50
 **/
@Service("legalMechanismService")
public class LegalMechanismService extends BaseModelService<LegalMechanismMapper,LegalMechanism> {

    @Autowired
    private LegalMechanismMapper legalMechanismMapper;

    /**
     * 根据镇街id查询有效的机构信息
     * @param street_Id
     * @return
     */
    public List<LegalMechanism> findByStreet_Id(String street_Id){
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("street_Id",street_Id));
        List<LegalMechanism> list = this.selectAll(condition);
        return list;

    }

    /**
     * 查询全部的机构信息
     * @return
     */
    public List<LegalMechanism> findAll(){
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));
        //按照创建时间排序
        condition.addOrder("ctime",1);
        List<LegalMechanism> list = this.selectAll(condition);
        return list;
    }

    /**
     * 查询距离最近的机构
     * @param longitude
     * @param latitude
     * @return
     */
    public LegalMechanism findFristNearLegalMechanism(Double longitude,Double latitude){
        Map map = this.legalMechanismMapper.findFristNearLegalMechanism(latitude,longitude);
        if(map!=null){
            String id = (String)map.get("id");
            LegalMechanism legalMechanism = this.select(id);
            return legalMechanism;
        }
        return null;
    }

}
