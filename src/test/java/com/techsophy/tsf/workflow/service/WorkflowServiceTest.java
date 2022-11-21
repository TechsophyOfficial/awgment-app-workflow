package com.techsophy.tsf.workflow.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.idgenerator.IdGeneratorImpl;
import com.techsophy.tsf.workflow.dto.PaginationResponsePayload;
import com.techsophy.tsf.workflow.dto.WorkflowAuditSchema;
import com.techsophy.tsf.workflow.dto.WorkflowResponse;
import com.techsophy.tsf.workflow.dto.WorkflowSchema;
import com.techsophy.tsf.workflow.entity.WorkflowDefinition;
import com.techsophy.tsf.workflow.repository.WorkflowDefinitionRepository;
import com.techsophy.tsf.workflow.service.impl.WorkflowServiceImpl;
import com.techsophy.tsf.workflow.utils.TokenUtils;
import com.techsophy.tsf.workflow.utils.UserDetails;
import lombok.Cleanup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

@SpringBootTest
@EnableWebMvc
@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WorkflowServiceTest
{
    @Mock
    UserDetails mockUserDetails;
    @Mock
    WorkflowDefinitionRepository mockWorkflowDefinitionRepository;
    @Mock
    IdGeneratorImpl idGenerator;
    @Mock
    ObjectMapper mockObjectMapper;
    @Mock
    WorkflowAuditService workflowAuditService;
    @InjectMocks
    WorkflowServiceImpl workflowServiceImpl;
    @Mock
    TokenUtils tokenUtils;
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
        map.put(ID, ID_NUMBER);
        map.put(USER_NAME, USER_NAME);
        map.put(FIRST_NAME, USER_FIRST_NAME);
        map.put(LAST_NAME, USER_LAST_NAME);
        map.put(MOBILE_NUMBER, NUMBER);
        map.put(EMAIL_ID, MAIL_ID);
        map.put(DEPARTMENT, NULL);
        userList.add(map);
    }

    @Test
    void saveProcessTest() throws IOException
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_PROCESSES_DATA_2).getInputStream();
        String workflowDataTest = new String(inputStreamTest.readAllBytes());
        WorkflowDefinition workflowDefinitionTest = objectMapperTest.readValue(workflowDataTest, WorkflowDefinition.class);
        WorkflowAuditSchema workflowAuditSchemaTest=new WorkflowAuditSchema(PROCESS_ID,PROCESS_ID, PROCESS_NAME,PROCESS_VERSION, PROCESS_CONTENT, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATED_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        when(mockUserDetails.getUserDetails())
                .thenReturn(userList);
        when(idGenerator.nextId()).thenReturn(BigInteger.valueOf(Long.parseLong(PROCESS_ID)));
        when(mockObjectMapper.convertValue(any(), eq(WorkflowAuditSchema.class))).thenReturn(workflowAuditSchemaTest);
        when(this.mockObjectMapper.convertValue(any(), eq(WorkflowDefinition.class))).thenReturn(workflowDefinitionTest);
        when(mockWorkflowDefinitionRepository.save(any())).thenReturn(workflowDefinitionTest.withId(BigInteger.valueOf(Long.parseLong(PROCESS_ID))));
        when(this.mockObjectMapper.convertValue(any(), eq(WorkflowResponse.class))).thenReturn(new WorkflowResponse(PROCESS_ID, PROCESS_VERSION));
        workflowServiceImpl.saveProcess(null, PROCESS_NAME, PROCESS_VERSION, Arrays.toString(PROCESS_CONTENT));
        verify(mockWorkflowDefinitionRepository, times(1)).save(any());
    }

    @Test
    void updateProcessTest() throws IOException
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_PROCESSES_DATA_1).getInputStream();
        String workflowDataTest = new String(inputStreamTest.readAllBytes());
        WorkflowDefinition workflowDefinitionTest = objectMapperTest.readValue(workflowDataTest,WorkflowDefinition.class);
        WorkflowAuditSchema workflowAuditSchemaTest=new WorkflowAuditSchema(PROCESS_ID,PROCESS_ID, PROCESS_NAME,PROCESS_VERSION, PROCESS_CONTENT, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATED_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        Mockito.when(mockUserDetails.getUserDetails())
                .thenReturn(userList);
        when(this.mockObjectMapper.convertValue(any(), eq(WorkflowDefinition.class)))
                .thenReturn(workflowDefinitionTest);
        when(idGenerator.nextId()).thenReturn(BigInteger.valueOf(Long.parseLong(PROCESS_ID)));
        when(mockObjectMapper.convertValue(any(), eq(WorkflowAuditSchema.class))).thenReturn(workflowAuditSchemaTest);
        when(mockWorkflowDefinitionRepository.save(workflowDefinitionTest)).thenReturn(workflowDefinitionTest.withId(BigInteger.valueOf(Long.parseLong(PROCESS_ID))));
        when(this.mockObjectMapper.convertValue(any(), eq(WorkflowResponse.class))).thenReturn(new WorkflowResponse(PROCESS_ID, PROCESS_VERSION));
        when(mockWorkflowDefinitionRepository.existsById(BigInteger.valueOf(Long.parseLong(PROCESS_ID)))).thenReturn(true);
        when(mockWorkflowDefinitionRepository.findById(BigInteger.valueOf(Long.parseLong(PROCESS_ID)))).thenReturn(Optional.of(workflowDefinitionTest));
        workflowServiceImpl.saveProcess(PROCESS_ID, PROCESS_NAME, PROCESS_VERSION, Arrays.toString(PROCESS_CONTENT));
        verify(mockWorkflowDefinitionRepository, times(1)).save(any());
    }

    @Test
    void updateProcessRecordCreationByIdTest() throws IOException
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_PROCESSES_DATA_1).getInputStream();
        String workflowDataTest = new String(inputStreamTest.readAllBytes());
        WorkflowDefinition workflowDefinitionTest = objectMapperTest.readValue(workflowDataTest,WorkflowDefinition.class);
        WorkflowAuditSchema workflowAuditSchemaTest=new WorkflowAuditSchema(PROCESS_ID,PROCESS_ID, PROCESS_NAME,PROCESS_VERSION, PROCESS_CONTENT, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATED_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        Mockito.when(mockUserDetails.getUserDetails())
                .thenReturn(userList);
        when(this.mockObjectMapper.convertValue(any(), eq(WorkflowDefinition.class)))
                .thenReturn(workflowDefinitionTest);
        when(idGenerator.nextId()).thenReturn(BigInteger.valueOf(Long.parseLong(PROCESS_ID)));
        when(mockObjectMapper.convertValue(any(), eq(WorkflowAuditSchema.class))).thenReturn(workflowAuditSchemaTest);
        when(mockWorkflowDefinitionRepository.save(any())).thenReturn(workflowDefinitionTest);
        when(this.mockObjectMapper.convertValue(any(), eq(WorkflowResponse.class))).thenReturn(new WorkflowResponse(PROCESS_ID, PROCESS_VERSION));
        when(mockWorkflowDefinitionRepository.existsById(BigInteger.valueOf(Long.parseLong(PROCESS_ID)))).thenReturn(false);
        when(mockWorkflowDefinitionRepository.findById(BigInteger.valueOf(Long.parseLong(PROCESS_ID)))).thenReturn(Optional.of(workflowDefinitionTest));
        workflowServiceImpl.saveProcess(PROCESS_ID, PROCESS_NAME, PROCESS_VERSION, Arrays.toString(PROCESS_CONTENT));
        verify(mockWorkflowDefinitionRepository, times(1)).save(any());
    }

    @Test
    void getProcessByIdTest() throws IOException
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_PROCESSES_DATA_1).getInputStream();
        String workflowDataTest=new String(inputStreamTest.readAllBytes());
        WorkflowSchema workflowSchemaTest=new WorkflowSchema(PROCESS_ID, PROCESS_NAME, PROCESS_CONTENT, PROCESS_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATED_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        WorkflowDefinition workflowDefinitionTest=objectMapperTest.readValue(workflowDataTest,WorkflowDefinition.class);
        when(this.mockObjectMapper.convertValue(any(),eq(WorkflowSchema.class))).thenReturn(workflowSchemaTest);
        when(mockWorkflowDefinitionRepository.findById(BigInteger.valueOf(Long.parseLong(String.valueOf(1))))).thenReturn(java.util.Optional.ofNullable(workflowDefinitionTest));
        workflowServiceImpl.getProcessById(PROCESS_ID);
        verify(mockWorkflowDefinitionRepository,times(1)).findById(BigInteger.valueOf(1));
    }

    @Test
    void getAllProcessesIncludeContentTest() throws IOException
    {
        Sort.Order order = new Sort.Order(ASC,"process1");
        List<Sort.Order>  list = new ArrayList<>();
        list.add(order);
        Sort sort = Sort.by(list);
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_PROCESSES_DATA_1).getInputStream();
        String workflowDataTest=new String(inputStreamTest.readAllBytes());
        WorkflowSchema workflowSchemaTest=new WorkflowSchema(PROCESS_ID, PROCESS_NAME, PROCESS_CONTENT, PROCESS_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATED_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        WorkflowDefinition workflowDefinitionTest=objectMapperTest.readValue(workflowDataTest,WorkflowDefinition.class);
        when(this.mockObjectMapper.convertValue(any(),eq(WorkflowSchema.class))).thenReturn(workflowSchemaTest);
        when(mockWorkflowDefinitionRepository.findWorkflowsByQSorting(any(), any())).thenReturn(Stream.of(workflowDefinitionTest));
         workflowServiceImpl.getAllProcesses(true,null,Q,sort).collect(Collectors.toList());
        verify(mockWorkflowDefinitionRepository,times(1)).findWorkflowsByQSorting(any(), any());
    }
    @Test
    void getAllProcesses() throws IOException
    {
        Sort.Order order = new Sort.Order(ASC,"process1");
        List<Sort.Order>  list = new ArrayList<>();
        list.add(order);
        Sort sort = Sort.by(list);
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_PROCESSES_DATA_1).getInputStream();
        String workflowDataTest=new String(inputStreamTest.readAllBytes());
        WorkflowSchema workflowSchemaTest=new WorkflowSchema(PROCESS_ID, PROCESS_NAME, PROCESS_CONTENT, PROCESS_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATED_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        WorkflowDefinition workflowDefinitionTest=objectMapperTest.readValue(workflowDataTest,WorkflowDefinition.class);
        when(this.mockObjectMapper.convertValue(any(),eq(WorkflowSchema.class))).thenReturn(workflowSchemaTest);
        when(mockWorkflowDefinitionRepository.findWorkflowsByQSorting(any(), any())).thenReturn(Stream.of(workflowDefinitionTest));
        workflowServiceImpl.getAllProcesses(false,null,Q,sort).collect(Collectors.toList());
        verify(mockWorkflowDefinitionRepository,times(1)).findWorkflowsByQSorting(any(), any());
    }
    @Test
    void getAllProcessesWithEmptyQ() throws IOException
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_PROCESSES_DATA_1).getInputStream();
        String workflowDataTest=new String(inputStreamTest.readAllBytes());
        WorkflowSchema workflowSchemaTest=new WorkflowSchema(PROCESS_ID, PROCESS_NAME, PROCESS_CONTENT, PROCESS_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATED_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        WorkflowDefinition workflowDefinitionTest=objectMapperTest.readValue(workflowDataTest,WorkflowDefinition.class);
        when(this.mockObjectMapper.convertValue(any(),eq(WorkflowSchema.class))).thenReturn(workflowSchemaTest);
        when(mockWorkflowDefinitionRepository.findAll((Sort) any())).thenReturn(List.of(workflowDefinitionTest));
         workflowServiceImpl.getAllProcesses(true,null,null, null).collect(Collectors.toList());
        verify(mockWorkflowDefinitionRepository,times(1)).findAll((Sort) any());
    }
    @Test
    void getAllProcessesWithEmptyQNoCOntent() throws IOException
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_PROCESSES_DATA_1).getInputStream();
        String workflowDataTest=new String(inputStreamTest.readAllBytes());
        WorkflowSchema workflowSchemaTest=new WorkflowSchema(PROCESS_ID, PROCESS_NAME, PROCESS_CONTENT, PROCESS_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATED_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        WorkflowDefinition workflowDefinitionTest=objectMapperTest.readValue(workflowDataTest,WorkflowDefinition.class);
        when(this.mockObjectMapper.convertValue(any(),eq(WorkflowSchema.class))).thenReturn(workflowSchemaTest);
        when(mockWorkflowDefinitionRepository.findAll((Sort) any())).thenReturn(List.of(workflowDefinitionTest));
        workflowServiceImpl.getAllProcesses(false,null,null, null).collect(Collectors.toList());
        verify(mockWorkflowDefinitionRepository,times(1)).findAll((Sort) any());
    }
    @Test
    void getAllProcessesWithdeploymentIdList() throws IOException
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_PROCESSES_DATA_1).getInputStream();
        String workflowDataTest=new String(inputStreamTest.readAllBytes());
        WorkflowSchema workflowSchemaTest=new WorkflowSchema(PROCESS_ID, PROCESS_NAME, PROCESS_CONTENT, PROCESS_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATED_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        WorkflowDefinition workflowDefinitionTest=objectMapperTest.readValue(workflowDataTest,WorkflowDefinition.class);
        when(this.mockObjectMapper.convertValue(any(),eq(WorkflowSchema.class))).thenReturn(workflowSchemaTest);
        when(mockWorkflowDefinitionRepository.findByIdIn(any())).thenReturn(List.of(workflowDefinitionTest));
        workflowServiceImpl.getAllProcesses(true,DEPLOYMENT_ID_LIST,null, null).collect(Collectors.toList());
        verify(mockWorkflowDefinitionRepository,times(1)).findByIdIn(any());
    }

    @Test
    void deleteProcessByIdTest()
    {
        when(mockWorkflowDefinitionRepository.existsById(BigInteger.valueOf(1))).thenReturn(true);
        when(mockWorkflowDefinitionRepository.deleteById(BigInteger.valueOf(1))).thenReturn(Integer.valueOf(PROCESS_ID));
        workflowServiceImpl.deleteProcessById(PROCESS_ID);
        verify(mockWorkflowDefinitionRepository,times(1)).deleteById(BigInteger.valueOf(1));
    }

    @Test
    void searchWorkflowByIdOrNameLike() throws IOException
    {
        ObjectMapper objectMapperTest=new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_PROCESSES_DATA_1).getInputStream();
        String workflowDataTest=new String(inputStreamTest.readAllBytes());
        WorkflowSchema workflowSchemaTest=new WorkflowSchema(PROCESS_ID, PROCESS_NAME, PROCESS_CONTENT, PROCESS_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATED_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        WorkflowDefinition workflowDefinitionTest=objectMapperTest.readValue(workflowDataTest,WorkflowDefinition.class);
        when(this.mockObjectMapper.convertValue(any(),eq(WorkflowSchema.class))).thenReturn(workflowSchemaTest);
        when(mockWorkflowDefinitionRepository.findByNameOrId(TEST_ID_OR_NAME_LIKE)).thenReturn(Collections.singletonList(workflowDefinitionTest));
        workflowServiceImpl.searchProcessByIdOrNameLike(TEST_ID_OR_NAME_LIKE);
        verify(mockWorkflowDefinitionRepository,times(1)).findByNameOrId(TEST_ID_OR_NAME_LIKE);
    }

    @Test
    void getAllProcessesTest()
    {
        Pageable pageable = PageRequest.of(PAGE_NUMBER,PAGE_SIZE_VALUE,Sort.by(PROCESS_NAME));
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        PaginationResponsePayload paginationResponsePayload = new PaginationResponsePayload();
        byte[] content ={10,20};
        WorkflowDefinition workflowDefinition = new WorkflowDefinition(BigInteger.valueOf(1),PROCESS_NAME,1,content);
        Page<WorkflowDefinition> page = new PageImpl<WorkflowDefinition>(List.of(workflowDefinition));
        when(mockWorkflowDefinitionRepository.findAll(pageable)).thenReturn(page);
        when(mockWorkflowDefinitionRepository.findWorkflowsByQPageable(Q,pageable)).thenReturn(page);
        when(tokenUtils.getPaginationResponsePayload(page,list)).thenReturn(paginationResponsePayload);
        workflowServiceImpl.getAllProcesses(null,true,pageable);
        workflowServiceImpl.getAllProcesses(Q,true,pageable);
        verify(mockWorkflowDefinitionRepository,times(1)).findAll((Pageable) any());
        verify(mockWorkflowDefinitionRepository,times(1)).findWorkflowsByQPageable(any(),any());
    }
}