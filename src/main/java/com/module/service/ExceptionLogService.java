package com.module.service;

import org.springframework.stereotype.Service;

/**
 * 异常信息服务
 * @author chc
 * @create 2017-11-22 11:30
 **/
@Service("exceptionLogService")
public class ExceptionLogService {


//    public void addExceptionLog(HttpServletRequest req,Exception e){
//
//        try {
//            ExceptionLog log = new ExceptionLog();
//            Enumeration<String> parameterNames = req.getParameterNames();
//
//            if(req instanceof XSSRequestWrapper){
//                ServletRequest request = ((XSSRequestWrapper)req).getRequest();
//                if(request instanceof HttpRequestWapper){
//                    Map<String, Object> paramsMap = ((HttpRequestWapper) request).getParamsMap();
//                    //记录解密后的参数
//                    log.setDecryptParam(FastJsonUtils.beanToJson(paramsMap));
//                }
//            }
//
//
//            Map<String,Object> objectMap = new HashMap<>(16);
//            while (parameterNames.hasMoreElements()){
//                String key = parameterNames.nextElement();
//                String parameter = req.getParameter(key);
//                objectMap.put(key,parameter);
//            }
//            String exceptionType = e.getClass().getSimpleName();
//            String toJson = FastJsonUtils.beanToJson(objectMap);
//            String ipAddr = IpUtil.getIpAddr(req);
//            log.setException_Id(UUIDUtils.randomUUID());
//            log.setDateTime(DateUtil.formatDefalutDateFormat());
//            //异常类型
//            log.setExceptionType(exceptionType);
//            //
//            log.setInterfaceAddress(req.getServletPath());
//            //异常内容
//            log.setMessage(e.getMessage());
//            //客户端请求参数
//            log.setClientParam(toJson);
//            //客户端ip地址
//            log.setUserIpAddr(ipAddr);
//
//            daoFactory.getExceptionLogDao().insert(log);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

}
