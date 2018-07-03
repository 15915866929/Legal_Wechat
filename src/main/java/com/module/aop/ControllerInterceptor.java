package com.module.aop;


import com.core.cache.CacheService;
import com.core.exception.ResultException;
import com.core.protocol.BaseResponse;
import com.core.util.JSONUtils;
import com.core.util.StringUtils;
import com.module.config.tool_config.ToolConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by LJJ on 2017/7/26.
 */
@Aspect
@Component
public class ControllerInterceptor {

    private Logger logger = LoggerFactory.getLogger(ControllerInterceptor.class);

//    @Resource
//    private ServiceFactory serviceFactory;
//    @Resource
//    private DaoFactory daoFactory;

    @Autowired
    private ToolConfig toolConfig;
    @Autowired
    private CacheService cacheService;


    /**
     * 定义拦截规则：拦截com.xjj.web.controller包下面的所有类中。
     */
    @Pointcut("execution(* com.module.controller..*(..)) ")
    public void controllerMethodPointcut(){}

    /**
     * 拦截器具体实现
     * @param proceedingJoinPoint
     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。）
     * @throws Throwable,Exception 
     */
    @Around("controllerMethodPointcut()") //指定拦截器规则；也可以直接把“execution(* com.xjj.........)”写进这里
    public Object Interceptor(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        //获取被拦截的方法
        Method method = signature.getMethod();
        //获取被拦截的方法名
        String methodName = method.getName();
        //访问的域名
        String serverName = request.getServerName();

        Map pa = request.getParameterMap();
        System.err.println("请求方法:"+methodName+",请求参数:"+ JSONUtils.beanToJson(pa));
        Object[] args = proceedingJoinPoint.getArgs();
        //args[1]="2";
        BaseResponse result = new BaseResponse();
        if (Arrays.asList(ToolConfig.notInterceptMethodName).contains(methodName)){
            Object proceed = proceedingJoinPoint.proceed(args);
            return proceed;
        }
        String skey = request.getParameter(toolConfig.getTokenName());
        if ( null == skey || "".equals(skey) || skey.length() < 5) {
            throw new ResultException("非法请求");
        }

        String openId = (String)cacheService.get(skey);
        logger.info("拦截openId:"+openId);
//        Skey bySkey = this.serviceFactory.getAuthService().findBySkey(skey);

        if (StringUtils.isEmpty(openId) ) {
            throw new ResultException("登陆状态错误，请刷新页面");
        }
//        String openId=bySkey.getOpenid();


        /*if (DateUtils.getSystemTime() > token.getCtime() + token.getLife()){
            serviceFactory.getRequestLogService().addLog("return:"+serviceFactory.getRequestLogService().getIpAddr(request), methodName, "token已失效");
            throw new BaseException(ErrorCode.Token_200, "token已失效，请刷新页面");
        }*/

        /*if (!serviceFactory.getRequestLogService().legalRequest(request, methodName)){
            result.setMsg("短时间内频繁访问，请刷新页面");
            result.setErrorcode(ErrorCode.frequently_400);
            result.setSucc(false);
            return result;
        }*/


        //替换keys和openId互换
        args[0] = openId;
        //一切正常，则继续执行被拦截的方法。
        result = (BaseResponse) proceedingJoinPoint.proceed(args);

//        //刷新token的方法名
//        String refreshToken=toolConfig.getRefreshToken();
//        //swagger访问的ip
//        String swaggerIp = toolConfig.getSwaggerIp();
//        if (
//                //刷新token接口
//                !refreshToken.equals(methodName)
//                // 是否需要加密
//                && toolConfig.resultCryptBool &&
//                //是否是Swagger访问
//                !serverName.equals(swaggerIp)
//                ) {
//            //加密访问参数
////        	String str = JSONUtils.beanToJson(result.getResult());
////            String EncryptStr = AES.Encrypt(str, bySkey.getKey(), bySkey.getIvParameterSpec());
////            result.setResult(EncryptStr);
//        }

        return result;
    }

}
