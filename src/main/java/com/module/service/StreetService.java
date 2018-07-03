package com.module.service;

import com.core.base.service.BaseModelService;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.module.entity.Street;
import com.module.mapper.StreetMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 镇街信息服务
 * @author chc
 * @create 2017-11-17 11:12
 **/
@Service("streetService")
public class StreetService extends BaseModelService<StreetMapper,Street> {
    /**
     * 查询全部有效的镇街信息
     * @return
     */
    public List<Street> findAllSteet(){
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("status",1));
        condition.addOrder("name",1);
        List<Street> streetList = this.selectAll(condition);
        return  streetList;
    }

}
