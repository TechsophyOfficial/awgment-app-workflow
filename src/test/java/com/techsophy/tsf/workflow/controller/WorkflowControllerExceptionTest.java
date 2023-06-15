package com.techsophy.tsf.workflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.workflow.config.CustomFilter;
import com.techsophy.tsf.workflow.dto.WorkflowSchema;
import com.techsophy.tsf.workflow.exception.*;
import com.techsophy.tsf.workflow.utils.TokenUtils;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static com.techsophy.tsf.workflow.constants.ErrorConstants.TOKEN_NOT_NULL;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.BASE_URL;
import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.*;
import static javax.swing.Action.NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WorkflowControllerExceptionTest
{
    @MockBean
    TokenUtils mockTokenUtils;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    CustomFilter customFilter;
    @Mock
    private WorkflowController mockWorkflowController;

    @BeforeEach
    public void setUp()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(new GlobalExceptionHandler(),mockWorkflowController).addFilters(customFilter).build();
    }

    @Test
    void ProcessIdNotFoundExceptionTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockWorkflowController.getProcessById(PROCESS_ID)).thenThrow(new ProcessIdNotFoundException(PROCESS_NOT_FOUND_WITH_GIVEN_ID,PROCESS_NOT_FOUND_WITH_GIVEN_ID));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL + VERSION_V1 + PROCESSES_BY_ID_URL,1)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
        assertEquals(500,mvcResult.getResponse().getStatus());
    }

    @Test
    void entityIdNotFoundExceptionTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockWorkflowController.deleteProcessById(PROCESS_ID)).thenThrow(new EntityIdNotFoundException(ENTITY_ID_NOT_FOUND,ENTITY_ID_NOT_FOUND));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.delete(BASE_URL + VERSION_V1 + PROCESSES_BY_ID_URL,1)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
        assertEquals(500,mvcResult.getResponse().getStatus());
    }

    @Test
    void  constraintViolationExceptionTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.post(BASE_URL + VERSION_V1 + PROCESSES_BY_ID_URL,1)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().is4xxClientError()).andReturn();
        assertEquals(405,mvcResult.getResponse().getStatus());
    }

    @Test
    void userDetailsNotFoundExceptionTest() throws Exception
    {
        Mockito.when(mockWorkflowController.getProcessById(PROCESS_ID)).thenThrow(new UserDetailsIdNotFoundException(USER_DETAILS_NOT_FOUND_WITH_GIVEN_ID,USER_DETAILS_NOT_FOUND_WITH_GIVEN_ID));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL + VERSION_V1 + PROCESSES_BY_ID_URL,1)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
        assertEquals(500,mvcResult.getResponse().getStatus());
    }

    @Test
    void invalidInputExceptionTest() throws Exception
    {
        Mockito.when(mockWorkflowController.saveProcess(any(),any(),any(),any())).thenThrow(new InvalidInputException(TOKEN_NOT_NULL,TOKEN_NOT_NULL));
        ObjectMapper objectMapperTest = new ObjectMapper();
        WorkflowSchema workflowSchemaTest=new WorkflowSchema(PROCESS_ID, PROCESS_NAME, PROCESS_CONTENT, PROCESS_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW);
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.post(BASE_URL + VERSION_V1 +PROCESSES_URL)
                .param(PARAM_ID,PARAM_ID_VALUE).param(PARAM_NAME,PARAM_NAME_VALUE).param(PARAM_VERSION,PARAM_VERSION_VALUE).param(PARAM_CONTENT,PARAM_CONTENT_VALUE)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
        assertEquals(500,mvcResult.getResponse().getStatus());
    }
}
