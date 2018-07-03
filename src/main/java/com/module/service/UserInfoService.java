package com.module.service;


import com.core.base.service.BaseModelService;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.base.support.Order;
import com.core.base.support.Page;
import com.module.entity.Account;
import com.module.entity.UserInfo;
import com.module.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserInfoService extends BaseModelService<UserInfoMapper,UserInfo> {

    @Autowired
    private AccountService accountService;
    /**
     * 查询所有调解员
     * @return
     */
    public List<UserInfo> findMediatorList(Integer page, Integer pageSize){
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("type",5));
        condition.addExpression(Expression.eq("status",1));
        condition.addOrder(Order.desc("ctime"));
        List<UserInfo> userInfoList = this.selectAll(condition);
        for (UserInfo userInfo : userInfoList) {
            Account account = this.accountService.selectByUniqueColumn("userInfo_Id",userInfo.getId());
            userInfo.setUaccount(account.getUaccount());
        }
        return userInfoList;
    }

    public UserInfo findMediator(String id){
        UserInfo userInfo = this.select(id);
        Account account = this.accountService.selectByUniqueColumn("userInfo_Id",userInfo.getId());
        userInfo.setUaccount(account.getUaccount());
        return userInfo;
    }

}
