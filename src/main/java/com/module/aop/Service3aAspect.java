package com.module.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/12/12.
 */
@Component    //相当于 注入bean
@Aspect       //注入aop
public class Service3aAspect {

    private static Logger log = LoggerFactory.getLogger(Service3aAspect.class);

   /*@Resource
    private HttpServletRequest request;*/

   @Pointcut(value = "execution(public * com.module.service.*.*(..))")
    public void wxMethod() {
    }


    @Around("wxMethod()")
    public Object aroundExc(ProceedingJoinPoint joinPoint) throws Throwable{
        Object result = null;
        String clazz = "";
        String classShort = "";
        String method = "";
        String lMethod = "";

	        clazz = joinPoint.getTarget().getClass().getName();
	        classShort = clazz.substring(clazz.lastIndexOf('.')+1);
	        method = joinPoint.getSignature().getName();
	        lMethod = classShort+"."+method;
	        log.info("before exc: "+lMethod);
	        Object[] args = joinPoint.getArgs();
	        Object json = "";
	        if(args.length > 0){
	            json = args[0];
	        }
	        log.info("parameter:" + json);
	        result = joinPoint.proceed();

        log.info("after exc " + classShort + "." + method + ",result:" + result);
        return result;
    }
}
