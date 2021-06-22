package com.nordea.logging;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static java.time.Instant.now;

@Aspect
@Log4j2
@Component
public class LogTimeConsumptionAspect {

    @Around("@annotation(com.nordea.logging.LogTime)")
    public void aroundLogTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String methodName = proceedingJoinPoint.getSignature().getName();

        log.info("{}.{} started ...", className, methodName);
        long start = now().toEpochMilli();

        proceedingJoinPoint.proceed();

        long duration = (now().toEpochMilli() - start) / 1000;
        log.info("{}.{} finished. Time consumed: {}s", className, methodName, duration);
    }
}
