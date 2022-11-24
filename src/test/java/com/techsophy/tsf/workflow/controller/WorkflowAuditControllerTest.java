package com.techsophy.tsf.workflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.workflow.config.CustomFilter;
import com.techsophy.tsf.workflow.dto.PaginationResponsePayload;
import com.techsophy.tsf.workflow.dto.WorkflowAuditSchema;
import com.techsophy.tsf.workflow.dto.WorkflowResponse;
import com.techsophy.tsf.workflow.service.WorkflowAuditService;
import com.techsophy.tsf.workflow.utils.TokenUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.stream.Stream;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.*;
import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.PAGE;
import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.PROCESSES_URL;
import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.SIZE;
import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.TOKEN;
import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.VERSION_V1;
import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.*;
import static javax.swing.Action.NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WorkflowAuditControllerTest
{
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtSaveOrUpdate = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_WORKFLOW_CREATE_OR_UPDATE));
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtRead = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_WORKFLOW_READ));

    @MockBean
    TokenUtils mockTokenUtils;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    WorkflowAuditService workflowAuditService;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    CustomFilter customFilter;

    @BeforeEach
    public void setUp()
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .addFilters(customFilter)
                .build();
    }

    @Test
    void saveWorkflowTest() throws Exception
    {
        ObjectMapper objectMapperTest=new ObjectMapper();
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        WorkflowAuditSchema workflowAuditSchema=new WorkflowAuditSchema(PROCESS_ID,PROCESS_ID, PROCESS_NAME,PROCESS_VERSION, PROCESS_CONTENT, CREATED_BY_ID_VALUE, CREATED_ON_NOW, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW);
        WorkflowResponse workflowResponse=new WorkflowResponse(TEST_ID,PROCESS_VERSION);
        Mockito.when(workflowAuditService.saveProcess(workflowAuditSchema)).thenReturn(workflowResponse);
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.post(BASE_URL+VERSION_V1+ HISTORY+PROCESSES_URL)
                .with(jwtSaveOrUpdate)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void getProcessByIdTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        WorkflowAuditSchema workflowAuditSchema=new WorkflowAuditSchema(PROCESS_ID,PROCESS_ID, PROCESS_NAME,PROCESS_VERSION, PROCESS_CONTENT, CREATED_BY_ID_VALUE, CREATED_ON_NOW, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW);
        Mockito.when(workflowAuditService.getProcessById(TEST_ID,PROCESS_VERSION)).thenReturn(workflowAuditSchema);
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL+VERSION_V1+ HISTORY+PROCESS_VERSION_BY_ID_URL,1,1)
                .with(jwtRead)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void getAllProcessesPaginationTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        WorkflowAuditSchema workflowAuditSchema=new WorkflowAuditSchema(PROCESS_ID,PROCESS_ID, PROCESS_NAME,PROCESS_VERSION, PROCESS_CONTENT, CREATED_BY_ID_VALUE, CREATED_ON_NOW, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW);
        Mockito.when(workflowAuditService.getAllProcesses(TEST_ID,true, Sort.by(NAME))).thenReturn(Stream.of(workflowAuditSchema));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL+VERSION_V1+ HISTORY+PROCESSES_BY_ID_URL,1)
                .param(INCLUDE_CONTENT,TRUE).param(PAGE,PAGE_VALUE).param(SIZE,SIZE_VALUE)
                .param(SORT_BY,NAME)
                .with(jwtRead)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void getAllProcessesNullPaginationTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        PaginationResponsePayload paginationResponsePayload=new PaginationResponsePayload();
        Mockito.when(workflowAuditService.getAllProcesses(TEST_ID,true, PageRequest.of(1,1,Sort.by(NAME)))).thenReturn(paginationResponsePayload);
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL+VERSION_V1+ HISTORY+PROCESSES_BY_ID_URL,1)
                .param(INCLUDE_CONTENT,TRUE).param(SIZE,SIZE_VALUE)
                .param(SORT_BY,NAME)
                .with(jwtRead)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }
}
