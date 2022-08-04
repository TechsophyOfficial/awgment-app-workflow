package com.techsophy.tsf.workflow.service;

import com.techsophy.tsf.workflow.entity.WorkflowDefinition;
import com.techsophy.tsf.workflow.repository.impl.WorkflowDefinitionCustomRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;
import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@SpringBootTest
class WorkflowDefinitionCustomRepositoryTest
{
    @Mock
    MongoTemplate mongoTemplate;
    @Mock
    WorkflowDefinition mockWorkflowDefinition;
    @InjectMocks
    WorkflowDefinitionCustomRepositoryImpl mockWorkflowDefinitionCustomRepositoryImpl;

    @Test
    void findByNameOrIdTest()
    {
        when(mockWorkflowDefinitionCustomRepositoryImpl.findByNameOrId(ABC)).thenReturn(List.of(mockWorkflowDefinition));
        List<WorkflowDefinition> workflowDefinitionTest= mockWorkflowDefinitionCustomRepositoryImpl.findByNameOrId(ABC);
        Assertions.assertNotNull(workflowDefinitionTest);
    }

    @Test
    void findByIdInTest()
    {
        when(mockWorkflowDefinitionCustomRepositoryImpl.findByIdIn(List.of(PROCESS_ID,PROCESS_ID_2))).thenReturn(List.of(mockWorkflowDefinition));
        List<WorkflowDefinition> workflowDefinitionTest= mockWorkflowDefinitionCustomRepositoryImpl.findByIdIn(List.of(PROCESS_ID,PROCESS_ID_2));
        Assertions.assertNotNull(workflowDefinitionTest);
    }

    @Test
    void findWorkflowsByQPageableTest()
    {
        Pageable pageable = PageRequest.of(PAGE_NUMBER,PAGE_SIZE_VALUE, Sort.by(PROCESS_NAME));
        byte[] content ={10,20};
        WorkflowDefinition workflowDefinition = new WorkflowDefinition(BigInteger.valueOf(Long.parseLong(TEST_ID)),PROCESS_NAME,PROCESS_VERSION,content);
        Mockito.when(mongoTemplate.count(any(), (Class<?>) any())).thenReturn(1L);
        Mockito.when(mongoTemplate.find(any(),any())).thenReturn(List.of(workflowDefinition));
        Page<WorkflowDefinition> workflowDefinitionTest= mockWorkflowDefinitionCustomRepositoryImpl.findWorkflowsByQPageable(Q,pageable);
        Assertions.assertNotNull(workflowDefinitionTest);
    }

    @Test
    void findWorkflowsByQSortingTest()
    {
        byte[] content ={10,20};
        WorkflowDefinition workflowDefinition = new WorkflowDefinition(BigInteger.valueOf(1),PROCESS_NAME,PROCESS_VERSION,content);
        Mockito.when(mongoTemplate.find(any(),any())).thenReturn((List.of(workflowDefinition)));
        Stream<WorkflowDefinition> workflowDefinitionTest= mockWorkflowDefinitionCustomRepositoryImpl.findWorkflowsByQSorting(Q,Sort.by(PROCESS_NAME));
        Assertions.assertNotNull(workflowDefinitionTest);
    }
}
