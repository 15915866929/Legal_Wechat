package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.base.support.Page;
import com.core.protocol.BaseResponse;
import com.core.protocol.ReturnCode;
import com.module.entity.LegalAid;
import com.module.entity.Mediate;
import com.module.entity.UserInfo;
import com.module.service.MediateArticleService;
import com.module.service.MediateService;
import com.module.service.UserInfoService;
import com.module.vo.MediateQueryVo;
import com.module.vo.MediateSaveVo;
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
 * 人民调解
 **/
@RestController
@RequestMapping("/mediate")
@Api(value = "MediateController",description = "人民调解")
public class MediateController extends BaseController {

    @Autowired
    private MediateService mediateService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MediateArticleService mediateArticleService;

    /**
     * 保存人民调解申请接口
     * @param skey
     * @param mediateSaveVo
     * @return
     */
    @ApiOperation(value = "保存人民调解申请接口")
    @PostMapping("/saveMediate")
    public BaseResponse saveMediate(String skey,
                                          @ApiParam(value = "申请信息") @ModelAttribute MediateSaveVo mediateSaveVo) {
        BaseResponse resp = new BaseResponse();
        mediateService.saveMediate(skey,mediateSaveVo);
        resp.setResult("添加成功");
        return resp;
    }

    /**
     * 根据镇街id查询法人民调解申请
     * @param skey
     * @param id
     * @return
     */
    @PostMapping("/findMediate")
    @ApiOperation(value = "根据id查询人民调解申请信息")
    public BaseResponse findMediate(String skey,
                                     @ApiParam(value="民调解申请id") @RequestParam String id){
        BaseResponse resp = new BaseResponse();
        Mediate mediate = mediateService.select(id);
        resp.setResult(mediate);
        return resp;
    }

    @PostMapping("/findMediateList")
    public BaseResponse findMediateList(String skey,@RequestParam Integer page,@RequestParam Integer pageSize){
        BaseResponse resp = new BaseResponse();
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("openid",skey));
        condition.setPageNo(page);
        condition.setPageSize(pageSize);
        Page<Mediate> mediatePage = this.mediateService.selectPage(condition);
        resp.setResult(mediatePage);
        return resp;
    }

    /**
     * 查看所有调解员列表
     * @author hsj
     * @date 2017-11-1
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findMediatorList")
    public BaseResponse findMediatorList(String skey) {
        BaseResponse result = new BaseResponse();
        try{
            List<UserInfo> userInfoList = userInfoService.findMediatorList(null, null);
            result.setResult(userInfoList);
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/findMediator")
    public BaseResponse findMediator(String skey,@RequestParam String id) {
        BaseResponse result = new BaseResponse();
        try{
            UserInfo userInfo = userInfoService.findMediator(id);
            result.setResult(userInfo);
        }catch(Exception e){
            e.printStackTrace();
            result.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @PostMapping("/queryAllMediatorArticle")
    public BaseResponse queryAllMediatorArticle(String skey){
        BaseResponse resp = new BaseResponse();
        resp.setResult(this.mediateArticleService.queryAll());
        return resp;
    }

    @PostMapping("/queryMediatorArticle")
    public BaseResponse queryMediatorArticle(String skey,@RequestParam String id){
        BaseResponse resp = new BaseResponse();
        resp.setResult(this.mediateArticleService.queryById(id));
        return resp;
    }

}
