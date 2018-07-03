package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.base.support.Page;
import com.core.protocol.BaseResponse;
import com.module.entity.Mediate;
import com.module.entity.Notarize;
import com.module.service.NotarizeArticleService;
import com.module.service.NotarizeService;
import com.module.vo.NotarizeQueryVo;
import com.module.vo.NotarizeSaveVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chc
 * @create 2017-11-18 11:15
 * 公证服务预约
 **/
@RestController
@RequestMapping("/notarize")
@Api(value = "NotarizeController",description = "公证服务预约")
public class NotarizeController extends BaseController {

    @Autowired
    private NotarizeService notarizeService;
    @Autowired
    private NotarizeArticleService notarizeArticleService;

    /**
     * 保存公证服务预约申请接口
     * @param skey
     * @param notarizeSaveVo
     * @return
     */
    @ApiOperation(value = "保存公证服务预约申请接口")
    @PostMapping("/saveNotarize")
    public BaseResponse saveNotarize(String skey,
                                             @ApiParam(value = "申请信息") @ModelAttribute NotarizeSaveVo notarizeSaveVo) {
        BaseResponse resp = new BaseResponse();
        notarizeService.saveNotarize(skey,notarizeSaveVo);
        resp.setResult("添加成功");
        return resp;
    }

    /**
     * 根据镇街id查询公证服务预约申请
     * @param skey
     * @param id
     * @return
     */
    @PostMapping("/findNotarize")
    @ApiOperation(value = "根据id查询公证服务预约申请信息")
    public BaseResponse findNotarize(String skey,
                                    @ApiParam(value="公证服务预约申请id") @RequestParam String id){
        BaseResponse resp = new BaseResponse();
        Notarize notarize = notarizeService.select(id);
        resp.setResult(notarize);
        return resp;
    }

    @PostMapping("/findNotarizeList")
    public BaseResponse findNotarizeList(String skey,@RequestParam Integer page,@RequestParam Integer pageSize){
        BaseResponse resp = new BaseResponse();
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("openid",skey));
        condition.setPageNo(page);
        condition.setPageSize(pageSize);
        Page<Notarize> notarizePage = this.notarizeService.selectPage(condition);
        resp.setResult(notarizePage);
        return resp;
    }

    @PostMapping("/queryAllNotarizeArticle")
    public BaseResponse queryAllNotarizeArticle(String skey){
        BaseResponse resp = new BaseResponse();
        resp.setResult(this.notarizeArticleService.queryAll());
        return resp;
    }

    @PostMapping("/queryNotarizeArticle")
    public BaseResponse queryNotarizeArticle(String skey,@RequestParam String id){
        BaseResponse resp = new BaseResponse();
        resp.setResult(this.notarizeArticleService.queryById(id));
        return resp;
    }

//    /**
//     * 发送邮件给操作员
//     * @author hsj
//     * @date 2017-11-21
//     * @return
//     */
//    @PostMapping("/sendEmailToOperator")
//    @ApiOperation(value = "发送邮件给操作员接口")
//    public ResultBean<String> sendEmailToOperator(String skey,
//            @ApiParam(value = "邮箱地址") @RequestParam  String sendEmail,
//            @ApiParam(value = "文件id") @RequestParam List<String> fileIds) throws Exception {
//        ResultBean resultBean = new ResultBean();
//        serviceFactory.getEmailService().sendEmailToOperator(sendEmail,fileIds);
//        return new ResultBean<>("发送成功");
//    }
}
