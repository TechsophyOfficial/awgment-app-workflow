package com.techsophy.tsf.workflow.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.TEST_ACTIVE_PROFILE;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(TEST_ACTIVE_PROFILE)
 class LoggingHandlerTest {
        @Mock
        JoinPoint joinPoint;
        @Mock
        Signature signature;
        @Mock
        Exception exception;
        @Mock
        Logger logger;
        @InjectMocks
        Logging loggingHandler;

        @Test
        void beforeServiceTest(){
            Mockito.when((joinPoint.getSignature())).thenReturn(signature);
            Mockito.when((signature.getName())).thenReturn("abc");
            loggingHandler.beforeService(joinPoint);
            verify(logger,times(1)).info(anyString(),anyString());
            verify(signature,times(1)).getName();
        }

        @Test
        void afterServiceTest(){
            Mockito.when((joinPoint.getSignature())).thenReturn(signature);
            Mockito.when((signature.getName())).thenReturn("abc");
            loggingHandler.afterService(joinPoint);
            verify(logger,times(1)).info(anyString(),anyString());
            verify(signature,times(1)).getName();
        }

        @Test
        void logAfterThrowingController() throws Exception
        {
            Mockito.when((joinPoint.getSignature())).thenReturn(signature);
            Mockito.when((signature.getName())).thenReturn("abc");
            loggingHandler.logAfterThrowingController(joinPoint,exception);
            verify(logger,times(1)).error(anyString(),anyString(),anyString());
            verify(signature,times(1)).getName();
        }

        @Test
        void logAfterThrowingService() throws Exception
        {
            Mockito.when((joinPoint.getSignature())).thenReturn(signature);
            Mockito.when((signature.getName())).thenReturn("abc");
            loggingHandler.logAfterThrowingService(joinPoint,exception);
            verify(logger,times(1)).error(anyString(),anyString(),anyString());
            verify(signature,times(1)).getName();
        }
}
