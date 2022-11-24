package com.techsophy.tsf.workflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.workflow.config.CustomFilter;
import com.techsophy.tsf.workflow.config.GlobalMessageSource;
import com.techsophy.tsf.workflow.dto.WorkflowResponse;
import com.techsophy.tsf.workflow.dto.WorkflowSchema;
import com.techsophy.tsf.workflow.service.WorkflowService;
import com.techsophy.tsf.workflow.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
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
import java.util.Arrays;
import java.util.stream.Stream;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.PAGE;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.PROCESSES_URL;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.TOKEN;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.VERSION_V1;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.*;
import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.SIZE;
import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.*;
import static javax.swing.Action.NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({MockitoExtension.class})
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WorkflowControllerTest
{
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtSaveOrUpdate = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_WORKFLOW_CREATE_OR_UPDATE));
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtRead = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_WORKFLOW_READ));
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtDelete = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_WORKFLOW_DELETE));

    @Mock
    GlobalMessageSource mockGlobalMessageSource;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    WorkflowService mockWorkflowService;
    @MockBean
    TokenUtils mockTokenUtils;
    @MockBean
    PageRequest pageRequest;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    CustomFilter customFilter;

    @BeforeEach public void setUp()
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(customFilter)
                .apply(springSecurity())
                .build();
    }

    @Test
    void saveProcessTest() throws Exception
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        WorkflowSchema workflowSchemaTest=new WorkflowSchema(PROCESS_ID, PROCESS_NAME, PROCESS_CONTENT, PROCESS_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW);
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockWorkflowService.saveProcess(PROCESS_ID, PROCESS_NAME, PROCESS_VERSION, Arrays.toString(PROCESS_CONTENT))).thenReturn(new WorkflowResponse(PROCESS_ID, PROCESS_VERSION));
        RequestBuilder requestBuilderTest= MockMvcRequestBuilders.post(BASE_URL_PROCESS + VERSION_V1 + PROCESSES_URL)
                .param(PARAM_ID,PARAM_ID_VALUE).param(PARAM_NAME,PARAM_NAME_VALUE).param(PARAM_VERSION,PARAM_VERSION_VALUE).param(PARAM_CONTENT,PARAM_CONTENT_VALUE)
                .with(jwtSaveOrUpdate)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void getProcessByIdTest() throws Exception
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        WorkflowSchema workflowSchemaTest=new WorkflowSchema(PROCESS_ID, PROCESS_NAME, PROCESS_CONTENT, PROCESS_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW);
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockWorkflowService.getProcessById(PROCESS_ID)).thenReturn(new WorkflowSchema(PROCESS_ID, PROCESS_NAME, PROCESS_CONTENT, PROCESS_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW));
        RequestBuilder requestBuilderTest=MockMvcRequestBuilders.get(BASE_URL_PROCESS + VERSION_V1 + PROCESSES_BY_ID_URL,1)
                .with(jwtRead)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void deleteProcessByIdTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        RequestBuilder requestBuilderTest=MockMvcRequestBuilders
                .delete(BASE_URL_PROCESS + VERSION_V1 + PROCESSES_BY_ID_URL,1)
                .with(jwtDelete);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void searchProcessByIdOrNameLike() throws Exception
    {
        ObjectMapper objectMapperTest=new ObjectMapper();
        WorkflowSchema workflowSchemaTest=new WorkflowSchema(PROCESS_ID, PROCESS_NAME, PROCESS_CONTENT, PROCESS_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW);
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockWorkflowService.searchProcessByIdOrNameLike(TEST_ID_OR_NAME_LIKE)).thenReturn(
                Stream.of(
                        new WorkflowSchema(PROCESS_ID, PROCESS_NAME, PROCESS_CONTENT, PROCESS_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW),
                        new WorkflowSchema(PROCESS_ID, PROCESS_NAME, PROCESS_CONTENT, PROCESS_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW)));
        RequestBuilder requestBuilderTest=MockMvcRequestBuilders.get(BASE_URL_PROCESS + VERSION_V1 + SEARCH_PROCESSES_URL)
            .param(PARAM_ID_OR_NAME_LIKE,PARAM_ID_OR_NAME_LIKE_VALUE)
                .with(jwtRead)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void getAllWorkflowsPaginationTest() throws Exception
    {
        RequestBuilder requestBuilderTest=MockMvcRequestBuilders.get(BASE_URL_PROCESS + VERSION_V1 + PROCESSES_URL)
                .param(PARAM_INCLUDE_CONTENT, String.valueOf(true)).param(PAGE, String.valueOf(1)).param(SIZE,String.valueOf(1))
                .with(jwtRead);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void getAllWorkflowsTest() throws Exception
    {
        RequestBuilder requestBuilderTest=MockMvcRequestBuilders.get(BASE_URL_PROCESS + VERSION_V1 + PROCESSES_URL)
                .param(PARAM_INCLUDE_CONTENT, String.valueOf(true))
               .with(jwtRead);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }
}