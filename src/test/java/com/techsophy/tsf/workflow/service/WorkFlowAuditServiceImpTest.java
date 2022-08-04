package com.techsophy.tsf.workflow.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.workflow.config.GlobalMessageSource;
import com.techsophy.tsf.workflow.dto.PaginationResponsePayload;
import com.techsophy.tsf.workflow.dto.WorkflowAuditSchema;
import com.techsophy.tsf.workflow.entity.WorkflowAuditDefinition;
import com.techsophy.tsf.workflow.repository.WorkflowDefinitionAuditRepository;
import com.techsophy.tsf.workflow.service.impl.WorkflowAuditServiceImpl;
import com.techsophy.tsf.workflow.utils.TokenUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import java.math.BigInteger;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;
import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@EnableWebMvc
@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WorkFlowAuditServiceImpTest
{
    @Mock
    WorkflowDefinitionAuditRepository mockWorkflowDefinitionAuditRepo;
    @Mock
    ObjectMapper objectMapper;
    @Mock
    GlobalMessageSource globalMessageSource;
    @Mock
    TokenUtils tokenUtils;
    @InjectMocks
    WorkflowAuditServiceImpl mockWorkflowServiceImpl;

    @Test
    void saveProcessTest() throws Exception
    {
        byte[] content ={10,20};
        WorkflowAuditSchema workflowAuditSchema = new WorkflowAuditSchema(TEST_ID,PROCESS_ID,PROCESS_NAME,PROCESS_VERSION,content,CREATED_BY_ID_VALUE,Instant.now(),CREATED_BY_NAME,TEST_ID,Instant.now(),UPDATED_BY_NAME);
        WorkflowAuditDefinition workflowAuditDefinition=new WorkflowAuditDefinition();
        when(objectMapper.convertValue(workflowAuditSchema,WorkflowAuditDefinition.class)).thenReturn(workflowAuditDefinition);
        when(mockWorkflowDefinitionAuditRepo.save(workflowAuditDefinition)).thenReturn(workflowAuditDefinition.withId(BigInteger.valueOf(Long.parseLong(TEST_ID))));
        mockWorkflowServiceImpl.saveProcess(workflowAuditSchema);
        verify(mockWorkflowDefinitionAuditRepo,times(1)).save(any());
    }

    @Test
    void getAllProcessesIncludeContentSortTest()
    {
        byte[] content ={10,20};
        WorkflowAuditDefinition workflowAuditDefinition=new WorkflowAuditDefinition(BigInteger.valueOf(Long.parseLong(TEST_ID)),BigInteger.valueOf(Long.parseLong(PROCESS_ID)),PROCESS_NAME,PROCESS_VERSION,content);
        when(mockWorkflowDefinitionAuditRepo.findAllById(BigInteger.valueOf(Long.parseLong(PROCESS_ID)),Sort.by(PROCESS_NAME))).thenReturn(Stream.of(workflowAuditDefinition));
        mockWorkflowServiceImpl.getAllProcesses(TEST_ID,true,Sort.by(PROCESS_NAME));
        verify(mockWorkflowDefinitionAuditRepo,times(1)).findAllById(any(),(Sort) any());
    }

    @Test
    void getAllProcessesNoContentSortTest()
    {
        byte[] content ={10,20};
        WorkflowAuditDefinition workflowAuditDefinition=new WorkflowAuditDefinition(BigInteger.valueOf(Long.parseLong(TEST_ID)),BigInteger.valueOf(Long.parseLong(PROCESS_ID)),PROCESS_NAME,PROCESS_VERSION,content);
        when(mockWorkflowDefinitionAuditRepo.findAllById(BigInteger.valueOf(Long.parseLong(PROCESS_ID)),Sort.by(PROCESS_NAME))).thenReturn(Stream.of(workflowAuditDefinition));
        mockWorkflowServiceImpl.getAllProcesses(TEST_ID,false,Sort.by(PROCESS_NAME));
        verify(mockWorkflowDefinitionAuditRepo,times(1)).findAllById(any(),(Sort) any());
    }

    @Test
    void getAllProcessesPaginationTest()
    {
        Pageable pageable = PageRequest.of(PAGE_NUMBER,PAGE_SIZE_VALUE,Sort.by(PROCESS_NAME));
        PaginationResponsePayload paginationResponsePayload = new PaginationResponsePayload();
        byte[] content ={10,20};
        WorkflowAuditDefinition workflowAuditDefinition=new WorkflowAuditDefinition(BigInteger.valueOf(Long.parseLong(TEST_ID)),BigInteger.valueOf(Long.parseLong(PROCESS_ID)),PROCESS_NAME,PROCESS_VERSION,content);
        Page<WorkflowAuditDefinition> page = new PageImpl<WorkflowAuditDefinition>(List.of(workflowAuditDefinition));
        when(mockWorkflowDefinitionAuditRepo.findAllById(BigInteger.valueOf(Long.parseLong(PROCESS_ID)),pageable)).thenReturn(page);
        when(tokenUtils.getPaginationResponsePayload(any(),any())).thenReturn(paginationResponsePayload);
        mockWorkflowServiceImpl.getAllProcesses(PROCESS_ID,true,pageable);
        verify(mockWorkflowDefinitionAuditRepo,times(1)).findAllById(any(),(Pageable) any());
    }

    @Test
    void getProcessByIdTest()
    {
        byte[] content ={10,20};
        WorkflowAuditDefinition workflowAuditDefinition=new WorkflowAuditDefinition(BigInteger.valueOf(1),BigInteger.valueOf(1),PROCESS_NAME,PROCESS_VERSION,content);
        when(mockWorkflowDefinitionAuditRepo.findById(BigInteger.valueOf(Long.parseLong(PROCESS_ID)),PROCESS_VERSION)).thenReturn(Optional.of(workflowAuditDefinition));
        WorkflowAuditSchema workflowAuditSchema = new WorkflowAuditSchema(TEST_ID,PROCESS_ID,PROCESS_NAME,PROCESS_VERSION,content,CREATED_BY_ID_VALUE,Instant.now(),CREATED_BY_NAME,UPDATED_BY_ID_VALUE,Instant.now(),UPDATED_BY_NAME);
        when(objectMapper.convertValue(workflowAuditDefinition,WorkflowAuditSchema.class)).thenReturn(workflowAuditSchema);
        mockWorkflowServiceImpl.getProcessById(PROCESS_ID,PROCESS_VERSION);
        verify(mockWorkflowDefinitionAuditRepo,times(1)).findById(any(),any());
    }
}
