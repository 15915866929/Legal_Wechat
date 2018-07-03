package com.module.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author chc
 * @create 2017-11-21 16:56
 **/
@Data
public class LegalAidGuideVo {

    //表ID
    @ApiModelProperty(value = "表ID")
    private String legalAidGuide_Id;
    //提交材料
    @ApiModelProperty(value = "提交材料")
    private String materialHtml;
    //办理流程
    @ApiModelProperty(value = "办理流程")
    private String processHtml;
    //收费标准
    @ApiModelProperty(value = "收费标准")
    private String chargeHtml;
    //表格Id数组
    @ApiModelProperty(value = "文件信息")
    private List<FileInfoVo> fileInfoVos;
    //状态
    @ApiModelProperty(value = "状态")
    private Integer status;
}
