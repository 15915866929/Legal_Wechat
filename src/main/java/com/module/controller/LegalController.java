package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.base.support.Page;
import com.core.protocol.BaseResponse;
import com.module.entity.LegalAid;
import com.module.entity.LegalAidGuide;
import com.module.service.EmailService;
import com.module.service.LegalAidGuideService;
import com.module.service.LegalAidService;
import com.module.vo.LegalAidSaveVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author chc
 * @create 2017-11-18 11:15
 **/
@RestController
@RequestMapping("/legal")
@Api(value = "LegalController",description = "法律援助相关Api")
public class LegalController extends BaseController {

    @Autowired
    private LegalAidService legalAidService;
    @Autowired
    private LegalAidGuideService legalAidGuideService;
    @Autowired
    private EmailService emailService;

    /**
     * 保存法律援助申请接口
     * @param skey
     * @param legalAidSaveVo
     * @return
     */
    @ApiOperation(value = "保存法律援助申请接口")
    @PostMapping("/saveLegalAid")
    public BaseResponse<String> saveLegalAid(String skey,
                                             @ApiParam(value = "申请信息") @ModelAttribute LegalAidSaveVo legalAidSaveVo) {
        BaseResponse resp = new BaseResponse();
        legalAidService.saveLegalAid(skey,legalAidSaveVo);
        return resp;
    }

    /**
     * 根据镇街id查询法援机构信息
     * @param skey
     * @param id
     * @return
     */
    @PostMapping("/findLegalAid")
    @ApiOperation(value = "根据id查询法援援助预约信息")
    public BaseResponse findLegalAid(String skey,
                                        @ApiParam(value="法律援助预约id") @RequestParam String id){
        BaseResponse resp = new BaseResponse();
        LegalAid legalAid = legalAidService.select(id);
        resp.setResult(legalAid);
        return resp;
    }

    @PostMapping("/findLegalAidList")
    @ApiOperation(value = "查询法援援助预约信息")
    public BaseResponse findLegalAidList(String skey,@RequestParam Integer page,@RequestParam Integer pageSize){
        BaseResponse resp = new BaseResponse();
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("openid",skey));
        condition.setPageNo(page);
        condition.setPageSize(pageSize);
        Page<LegalAid> legalAidPage = this.legalAidService.selectPage(condition);
        resp.setResult(legalAidPage);
        return resp;
    }

    @PostMapping("/findAllReq")
    public BaseResponse findAllReq(String skey){
        BaseResponse resp = new BaseResponse();
        List list = this.legalAidService.findAllReq(skey);
        resp.setResult(list);
        return resp;
    }

    /**
     * 查询法律援助办理指南接口
     * @param skey
     * @return
     */
    @ApiOperation(value = "查询法律援助办理指南接口")
    @PostMapping("/findLegalAidGuide")
    public BaseResponse findLegalAidGuide(String skey){
        BaseResponse resp = new BaseResponse();
        LegalAidGuide legalAidGuide = legalAidGuideService.findLegalAidGuide();
        resp.setResult(legalAidGuide);
        return resp;
    }

    /**
     * 发送邮件给操作员
     * @author hsj
     * @date 2017-11-21
     * @return
     */
    @PostMapping("/sendEmailToOperator")
    @ApiOperation(value = "发送邮件给操作员接口")
    public BaseResponse sendEmailToOperator(String skey,
                                                  @ApiParam(value = "邮箱地址") @RequestParam  String sendEmail,
                                                  @ApiParam(value = "文件id") @RequestParam List<String> fileIds) throws Exception {
        BaseResponse resp = new BaseResponse();
        emailService.sendEmailToOperator(sendEmail,fileIds);
        resp.setResult("发送成功");
        return resp;
    }


}
