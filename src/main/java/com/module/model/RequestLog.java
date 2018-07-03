package com.module.model;

import lombok.Data;

/**
 * Created by LJJ on 2017/7/18.
 * 操作日志表
 */

@Data
public class RequestLog {
    //WeChatPubAccount.paid
    private String paid;
    //活动id
    private String activityId;
    private String ip;
    private String method;
    private String params;
    private String logTime;
    private long ctime;

}
