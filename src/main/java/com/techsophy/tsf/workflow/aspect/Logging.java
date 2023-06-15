package com.techsophy.tsf.workflow.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.*;

@Aspect
@EnableAspectJAutoProxy
@Component
public class Logging
{
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before(CONTROLLER_CLASS_PATH)
    void beforeController(JoinPoint joinPoint)
    {
        String name = joinPoint.getSignature().getName();
        logger.info(IS_INVOKED_IN_CONTROLLER,name);
    }

    @After(CONTROLLER_CLASS_PATH)
    void afterController(JoinPoint joinPoint)
    {
        String name = joinPoint.getSignature().getName();
        logger.info(EXECUTION_IS_COMPLETED_IN_CONTROLLER,name);
    }

    @Before(SERVICE_CLASS_PATH)
    void beforeService(JoinPoint joinPoint)
    {
        String name = joinPoint.getSignature().getName();
        logger.info(IS_INVOKED_IN_SERVICE,name);
    }

    @After(SERVICE_CLASS_PATH)
    void afterService(JoinPoint joinPoint)
    {
        String name = joinPoint.getSignature().getName();
        logger.info(EXECUTION_IS_COMPLETED_IN_SERVICE,name);
    }

    @AfterThrowing(value= CONTROLLER_CLASS_PATH,throwing=EXCEPTION)
    public void logAfterThrowingController(JoinPoint joinPoint, Exception ex)
    {
        logger.error(EXCEPTION_THROWN ,joinPoint.getSignature().getName(),BRACKETS_IN_CONTROLLER);
        logger.error(CAUSE,ex.getMessage());
    }

    @AfterThrowing(value=SERVICE_CLASS_PATH,throwing=EXCEPTION)
    public void logAfterThrowingService(JoinPoint joinPoint, Exception ex)
    {
        logger.error(EXCEPTION_THROWN ,joinPoint.getSignature().getName(),BRACKETS_IN_SERVICE);
        logger.error(CAUSE ,ex.getMessage());
    }
}
