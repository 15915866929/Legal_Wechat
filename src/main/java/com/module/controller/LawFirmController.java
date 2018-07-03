package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.base.support.Page;
import com.core.protocol.BaseResponse;
import com.module.entity.LawFirm;
import com.module.entity.LegalAid;
import com.module.entity.LegalAidGuide;
import com.module.service.EmailService;
import com.module.service.LawFirmService;
import com.module.service.LegalAidGuideService;
import com.module.service.LegalAidService;
import com.module.vo.LegalAidSaveVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chc
 * @create 2017-11-18 11:15
 **/
@RestController
@RequestMapping("/lawFirm")
@Api(value = "LawFirmController",description = "")
public class LawFirmController extends BaseController {

    @Autowired
    private LawFirmService lawFirmService;

    /**
     * 查询所有律师事务所
     */
    @PostMapping("/findAllLawFirm")
    public BaseResponse findAllReq(String skey){
        BaseResponse resp = new BaseResponse();
        List list = this.lawFirmService.queryAllLawFirm();
        resp.setResult(list);
        return resp;
    }

    /**
     * 查询所有律师事务所
     */
    @PostMapping("/findLawFirm")
    public BaseResponse findLawFirm(String skey, @RequestParam String id){
        BaseResponse resp = new BaseResponse();
        LawFirm lawFirm = this.lawFirmService.queryById(id);
        resp.setResult(lawFirm);
        return resp;
    }


}
