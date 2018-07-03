package com.module.controller;

import com.core.base.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chc
 * @create 2017-11-17 17:29
 **/
@Controller
@RequestMapping("file")
@Api(value = "FileController",description = "文件服务Api")
public class FileController extends BaseController {

    /**
     * 展示图片
     * @author chc
     * @date 2017-09-20
     * @param fileId
     * @param request
     * @param response
     */
    @ApiOperation(value = "根据文件id展示图片")
    @GetMapping("/showImage")
    public void showImage(@ApiParam(value = "文件id") @RequestParam String fileId, HttpServletRequest request, HttpServletResponse response) {
//        serviceFactory.getFileService().showImage(new Document("fileId", fileId).toJson(), request, response);
    }
}
