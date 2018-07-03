package com.module.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.module.entity.Notarize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author chc
 * @create 2017-11-18 11:33
 **/
@Data
public class NotarizeVo extends Notarize{

    private String type;
}
