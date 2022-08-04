package com.techsophy.tsf.workflow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.workflow.dto.WorkflowAuditSchema;
import com.techsophy.tsf.workflow.dto.WorkflowResponse;
import com.techsophy.tsf.workflow.model.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Stream;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.*;

@RequestMapping(BASE_URL+VERSION_V1+HISTORY)
@Validated
public interface WorkflowAuditController
{
    @PostMapping(PROCESSES_URL)
    @PreAuthorize(CREATE_OR_ALL_ACCESS)
    ApiResponse<WorkflowResponse> saveProcess(WorkflowAuditSchema workflowAuditSchema) throws JsonProcessingException;

    @GetMapping(PROCESS_ID_URL)
    @PreAuthorize(READ_OR_ALL_ACCESS)
    ApiResponse<Stream<WorkflowAuditSchema>>getAllProcesses(@PathVariable(ID) String id,
                                                            @RequestParam(INCLUDE_CONTENT) boolean includeProcessContent,
                                                            @RequestParam(value = PAGE, required = false) Integer page,
                                                            @RequestParam(value =SIZE, required = false) Integer pageSize,
                                                            @RequestParam(value = SORT_BY, required = false) String[] sortBy);

    @GetMapping(PROCESS_VERSION_BY_ID_URL)
    @PreAuthorize(READ_OR_ALL_ACCESS)
    ApiResponse<WorkflowAuditSchema> getProcessById(@PathVariable(ID) String id,@PathVariable(VERSION) Integer version);
}
