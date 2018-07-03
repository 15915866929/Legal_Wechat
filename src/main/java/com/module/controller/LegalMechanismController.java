package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.protocol.BaseResponse;
import com.module.entity.LegalMechanism;
import com.module.service.LegalMechanismService;
import com.module.vo.Location;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chc
 * @create 2017-11-17 15:57
 **/
@RestController
@RequestMapping("/legalMechanism")
@Api(value = "LegalMechanismController", description = "法援机构信息Api")
public class LegalMechanismController extends BaseController {

    @Autowired
    private LegalMechanismService legalMechanismService;

    /**
     * 根据镇街id查询法援机构信息
     * @param skey
     * @param Street_Id
     * @return
     */
    @PostMapping("/findByStreet_Id")
    @ApiOperation(value = "根据镇街id查询法援机构信息")
    public BaseResponse findByStreet_Id(String skey,
                                        @ApiParam(value="镇街id") @RequestParam String Street_Id){
        BaseResponse resp = new BaseResponse();
        List<LegalMechanism> mechanismVos = legalMechanismService.findByStreet_Id(Street_Id);
        resp.setResult(mechanismVos);
        return resp;
    }

    /**
     * 查询全部的机构信息
     * @param skey
     * @return
     */
    @PostMapping("/findAll")
    @ApiOperation(value = "查询全部的机构信息")
    public BaseResponse findAll(String skey){
        BaseResponse resp = new BaseResponse();
        List<LegalMechanism> voList = legalMechanismService.findAll();
        resp.setResult(voList);
        return resp;
    }

    @PostMapping("/findFristNearLegalMechanism")
    @ApiOperation(value = "查询距离最近的机构")
    public BaseResponse findFristNearLegalMechanism(String skey, @ModelAttribute Location location){
        BaseResponse resp = new BaseResponse();
        LegalMechanism legalMechanism = legalMechanismService.findFristNearLegalMechanism(location.getLongitude(),location.getLatitude());
        resp.setResult(legalMechanism);
        return resp;
    }
}
