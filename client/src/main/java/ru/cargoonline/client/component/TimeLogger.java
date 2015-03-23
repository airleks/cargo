package ru.cargoonline.client.component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Data sync time logger
 */
@Aspect
@Component(value = "logAspect")
public class TimeLogger {

    Logger logger = LoggerFactory.getLogger(TimeLogger.class);

    private final AtomicInteger iterationCounter = new AtomicInteger();

    /**
     * Data synchronizer pointcut
     */
    @Pointcut("@annotation(ru.cargoonline.common.context.LogDataSyncAction)")
    public void dataSyncPointcut() { }

    /**
     * Log time
     */
    @Around("dataSyncPointcut()")
    public Object logDataSyncTime(ProceedingJoinPoint joinPoint) throws Throwable
    {
        long start = System.currentTimeMillis();
        Object ret = joinPoint.proceed();
        long timeSpent = System.currentTimeMillis() - start;
        logger.info("{} iteration processing time: {} mls", iterationCounter.incrementAndGet(), timeSpent);

        return ret;
    }
}
