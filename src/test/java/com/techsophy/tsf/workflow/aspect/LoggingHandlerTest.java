package com.techsophy.tsf.workflow.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.TEST_ACTIVE_PROFILE;

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
        @InjectMocks
        Logging loggingHandler;

        @Test
        void beforeServiceTest(){
            Mockito.when((joinPoint.getSignature())).thenReturn(signature);
            Mockito.when((signature.getName())).thenReturn("abc");
            loggingHandler.beforeService(joinPoint);
            Assertions.assertTrue(true);
        }

        @Test
        void afterServiceTest(){
            Mockito.when((joinPoint.getSignature())).thenReturn(signature);
            Mockito.when((signature.getName())).thenReturn("abc");
            loggingHandler.afterService(joinPoint);
            Assertions.assertTrue(true);
        }

        @Test
        void logAfterThrowingController() throws Exception
        {
            Mockito.when((joinPoint.getSignature())).thenReturn(signature);
            Mockito.when((signature.getName())).thenReturn("abc");
            loggingHandler.logAfterThrowingController(joinPoint,exception);
            Assertions.assertTrue(true);
        }

        @Test
        void logAfterThrowingService() throws Exception
        {
            Mockito.when((joinPoint.getSignature())).thenReturn(signature);
            Mockito.when((signature.getName())).thenReturn("abc");
            loggingHandler.logAfterThrowingService(joinPoint,exception);
            Assertions.assertTrue(true);
        }
}
