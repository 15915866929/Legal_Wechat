package com.module.service;

import com.core.util.DateUtils;
import com.module.model.RequestLog;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by LJJ on 2017/7/3.
 */
@Service("requestLogService")
public class RequestLogService {

    private Logger logger = LoggerFactory.getLogger(RequestLogService.class);
    /**
     * 获取访问者IP
     *
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     *
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request
     * @return
     */
    public  String getIpAddr(HttpServletRequest request){
        String  ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
// 多次反向代理后会有多个IP值，第一个为真实IP。
            System.out.println(ip);
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    private HashMap<String ,Long> ipTimeLog = new HashMap(); //记下同一个ip调用同一个方法的记录
    private HashMap<String, Integer> ipExcTime = new HashMap(); //记下同一个ip调用同一个方法并且 两次调用相隔3s内 的次数

    public  boolean legalRequest(HttpServletRequest request, String viewName){
        boolean legal = true;
        String ip = getIpAddr(request);
        long currentTime = new Date().getTime();
        if(ip != null && ip.length() > 0){
            if(ipTimeLog.containsKey(ip + viewName)){
                long lastTime = ipTimeLog.get(ip + viewName);
                if(currentTime - lastTime < 3000){
                    addLog(ip, viewName,"异常访问，访问时间间隔为：" + (currentTime - lastTime) + "毫秒");
                    if(!ipExcTime.containsKey(ip + viewName)){
                        //第一次进来添加记录
                        ipExcTime.put(ip + viewName, 1);
                    }else{
                        ipExcTime.put(ip + viewName, ipExcTime.get(ip + viewName) + 1);
                    }

                    if(ipExcTime.get(ip + viewName) > 5){
                        legal = false;
                        addLog(ip, viewName,"频繁访问，访问时间间隔为：" + (currentTime - lastTime) + "毫秒");
                    }
                }else{
                    ipExcTime.put(ip + viewName, 0);
                }
            }
        }
        ipTimeLog.put(ip + viewName, currentTime);
        return legal;
    }

    public void addLog(String ip, String method, String params){
        RequestLog log = new RequestLog();
        log.setIp(ip);
        log.setMethod(method);
        log.setParams(params);
        Date date = new Date();
        log.setLogTime(DateUtils.dateToString(date,"yyyy-MM-dd HH:mm:ss.SSS"));
        log.setCtime(date.getTime());
//        daoFactory.getRequestLogDao().insert(log);

        logger.info("=============IP："+ip+", 访问："+method+", 参数："+params+"==================",this.getClass());
    }

    public void addLog(String ip, String method, Map params){
        String paramsStr = "";
        if(params != null){
            Set<String> keys = params.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()){
                String key = iterator.next();
                Object value = params.get(key);
                if(value != null){
                    if(value instanceof String[] && ((String[])value).length != 0){
                        value = ((String[])value)[0];
                    }
                }
                paramsStr += ( key+":"+value + "  " );

            }
        }
        addLog(ip,method,paramsStr);
    }


}
