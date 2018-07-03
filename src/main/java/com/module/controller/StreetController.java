package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.protocol.BaseResponse;
import com.module.entity.Street;
import com.module.service.StreetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author chc
 * @create 2017-11-17 11:24
 **/
@RestController
@RequestMapping("/street")
@Api(value = "StreetController", description = "镇街信息Api")
public class StreetController extends BaseController {

    @Autowired
    private StreetService streetService;

    /**
     * 查询全部有效的镇街信息
     * @return
     */
    @ApiOperation(value = "查询全部有效的镇街信息")
    @PostMapping("/findAllStreet")
    public BaseResponse findAllStreet(String skey){
        BaseResponse resp = new BaseResponse();
        List<Street> allSteet = streetService.findAllSteet();
        resp.setResult(allSteet);
        return resp;
    }



}
