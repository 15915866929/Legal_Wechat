package com.module.vo;

import lombok.Data;

/**
 * 邮箱附件
 * @author chc
 * @create 2017-11-23 15:52
 **/
@Data
public class EmailFileVo {
    /**
     * 原文件名
     */
    private String fileName;
    /**
     * 文件地址
     */
    private String filePath;
}
