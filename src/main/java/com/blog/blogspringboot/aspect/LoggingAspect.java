package com.blog.blogspringboot.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Log log = LogFactory.getLog(LoggingAspect.class);

    @Pointcut("execution(* com.blog.blogspringboot.controller.*.*(..))")
    public void servicePointcut() {}

    @Before("servicePointcut()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Executing " + joinPoint.getSignature().getName() + "...");
    }
}
