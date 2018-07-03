package com.module.controller;

import com.core.base.controller.BaseController;
import com.core.protocol.BaseResponse;
import com.module.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信控制
 * @author chc
 * @create 2017-11-18 9:48
 **/
@RestController
@RequestMapping("/sms")
@Api(value = "SmsController",description = "阿里云短信Api")
public class SmsController extends BaseController {

    @Autowired
    private SmsService smsService;

    /**
     * 验证发送接口
     * @param skey
     * @param phone
     * @return
     */
    @ApiOperation(value = "验证发送接口")
    @PostMapping("/sendVerificationCode")
    public BaseResponse sendVerificationCode(String skey,
                                             @ApiParam(value = "电话号码") @RequestParam String phone) {
        BaseResponse resp = new BaseResponse();
        smsService.sendVerificationCode(phone,skey);
        resp.setResult("短信发送成功");
        return resp;
    }
}
