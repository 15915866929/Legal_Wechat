package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.protocol.BaseResponse;
import com.module.entity.Lawyer;
import com.module.service.LawFirmService;
import com.module.service.LawyerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chc
 * @create 2017-11-18 11:15
 **/
@RestController
@RequestMapping("/lawyer")
@Api(value = "LawyerController",description = "")
public class LawyerController extends BaseController {

    @Autowired
    private LawyerService lawyerService;

    /**
     * 查询所有律师
     */
    @PostMapping("/findAllLawyer")
    public BaseResponse findAllLawyer(String skey){
        BaseResponse resp = new BaseResponse();
        List list = this.lawyerService.queryAllLawyer();
        resp.setResult(list);
        return resp;
    }

    /**
     * 查询所有律师
     */
    @PostMapping("/findLawyer")
    public BaseResponse findLawyer(String skey, @RequestParam String id){
        BaseResponse resp = new BaseResponse();
        Lawyer lawyer = this.lawyerService.queryById(id);
        resp.setResult(lawyer);
        return resp;
    }


}
