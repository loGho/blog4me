package com.logho.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//@EnableAspectJAutoProxy
@Aspect
@Component
public class LogAspect {

    @Autowired
    private Logger logger;



    @Pointcut("execution(* com.logho.controller.*.*.*(..))")
    public void log() {

    }

    @Before("log()")
    public void beforeExcu(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        StringBuffer requestURL = requestAttributes.getRequest().getRequestURL();
        logger.info("request url is {} , request args is {}, {} method before ",requestURL,joinPoint.getArgs(),joinPoint.getSignature().toString());
    }

    @After("log()")
    public void AfterExcu(JoinPoint joinPoint) {
        logger.info("{} method excute finish",joinPoint.getSignature().toString());
    }

    @AfterReturning(value = "log()",returning = "result", argNames = "joinPoint,result")
    public void afterReturning(JoinPoint joinPoint,Object result) {
        logger.info("{} method's return value is {}",joinPoint.getSignature().toString(),result);

    }


}
