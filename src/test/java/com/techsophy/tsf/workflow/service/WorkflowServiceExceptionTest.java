package com.techsophy.tsf.workflow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.idgenerator.IdGeneratorImpl;
import com.techsophy.tsf.workflow.config.GlobalMessageSource;
import com.techsophy.tsf.workflow.config.LocaleConfig;
import com.techsophy.tsf.workflow.dto.WorkflowSchema;
import com.techsophy.tsf.workflow.exception.EntityIdNotFoundException;
import com.techsophy.tsf.workflow.exception.ProcessIdNotFoundException;
import com.techsophy.tsf.workflow.exception.UserDetailsIdNotFoundException;
import com.techsophy.tsf.workflow.repository.WorkflowDefinitionRepository;
import com.techsophy.tsf.workflow.service.impl.WorkflowServiceImpl;
import com.techsophy.tsf.workflow.utils.UserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigInteger;
import java.util.*;

import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.CONTENT;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.EMPTY_STRING;
import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@SpringBootTest
class WorkflowServiceExceptionTest
{
    @Mock
    UserDetails mockUserDetails;
    @Mock
    WorkflowDefinitionRepository mockWorkflowDefinitionRepository;
    @Mock
    ObjectMapper mockObjectMapper;
    @Mock
    IdGeneratorImpl mockIdGeneratorImpl;
    @Mock
    GlobalMessageSource mockGlobalMessageSource;
    @Mock
    LocaleConfig mockLocaleConfig;
    @InjectMocks
    WorkflowServiceImpl mockWorkflowServiceImpl;

    List<Map<String, Object>> userList = new ArrayList<>();

    @BeforeEach
    public void init()
    {
        Map<String, Object> map = new HashMap<>();
        map.put(CREATED_BY_ID, NULL);
        map.put(CREATED_BY_NAME, NULL);
        map.put(CREATED_ON, NULL);
        map.put(UPDATED_BY_ID, NULL);
        map.put(UPDATED_BY_NAME, NULL);
        map.put(UPDATED_ON, NULL);
        map.put(ID, EMPTY_STRING);
        map.put(USER_NAME, USER_FIRST_NAME);
        map.put(FIRST_NAME, USER_LAST_NAME);
        map.put(LAST_NAME, USER_FIRST_NAME);
        map.put(MOBILE_NUMBER, NUMBER);
        map.put(EMAIL_ID, MAIL_ID);
        map.put(DEPARTMENT, NULL);
        userList.add(map);
    }

    @Test
    void saveWorkflowUserDetailsExceptionTest() throws JsonProcessingException
    {
        when(mockUserDetails.getUserDetails())
                .thenReturn(userList);
        Assertions.assertThrows(UserDetailsIdNotFoundException.class,()-> mockWorkflowServiceImpl.saveProcess(PROCESS_ID,PROCESS_NAME,PROCESS_VERSION,CONTENT));
    }

    @Test
    void getWorkflowByIdExceptionTest()
    {
        WorkflowSchema workflowSchemaTest =new WorkflowSchema(PROCESS_ID, PROCESS_NAME, PROCESS_CONTENT, PROCESS_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW);
        when(this.mockObjectMapper.convertValue(any(),eq(WorkflowSchema.class))).thenReturn(workflowSchemaTest);
        when(mockWorkflowDefinitionRepository.findById(BigInteger.valueOf(Long.parseLong(String.valueOf(1))))).thenReturn(Optional.empty());
        Assertions.assertThrows(ProcessIdNotFoundException.class,()-> mockWorkflowServiceImpl.getProcessById(PROCESS_ID));
    }

    @Test
    void deleteRuleByIdExceptionTest()
    {
       when(mockWorkflowDefinitionRepository.existsById(BigInteger.valueOf(1))).thenReturn(false);
        Assertions.assertThrows(EntityIdNotFoundException.class,()->
                mockWorkflowServiceImpl.deleteProcessById(PROCESS_ID));
    }
}
