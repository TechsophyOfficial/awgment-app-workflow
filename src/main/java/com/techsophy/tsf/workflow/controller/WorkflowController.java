package com.techsophy.tsf.workflow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.workflow.dto.WorkflowResponse;
import com.techsophy.tsf.workflow.dto.WorkflowSchema;
import com.techsophy.tsf.workflow.model.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.*;

@RequestMapping(BASE_URL+VERSION_V1)
@Validated
public interface WorkflowController
{
    @PostMapping(PROCESSES_URL)
    @PreAuthorize(CREATE_OR_ALL_ACCESS)
    ApiResponse<WorkflowResponse> saveProcess(@RequestParam(value = ID,required = false)  String id,
                                              @RequestParam(NAME) @NotEmpty @NotNull String name,
                                              @RequestParam(value = VERSION,required = false) Integer version,
                                              @RequestParam(CONTENT)   @NotEmpty @NotNull String content) throws JsonProcessingException;

    @GetMapping(PROCESSES_URL)
    @PreAuthorize(READ_OR_ALL_ACCESS)
    ApiResponse<Stream<WorkflowSchema>> getAllProcesses(@RequestParam(INCLUDE_CONTENT) boolean includeProcessContent,
                                                        @RequestParam(value=DEPLOYMENT,required = false) String deploymentIdList,
                                                        @RequestParam(value = QUERY,required = false) String q,
                                                        @RequestParam(value = PAGE, required = false) Integer page,
                                                        @RequestParam(value =SIZE, required = false) Integer pageSize,
                                                        @RequestParam(value = SORT_BY, required = false) String[] sortBy,
                                                        @RequestParam(value = FILTER_COLUMN, required = false) String filterColumn,
                                                        @RequestParam(value =FILTER_VALUE, required = false) String filterValue);

    @GetMapping(PROCESS_BY_ID_URL)
    @PreAuthorize(READ_OR_ALL_ACCESS)
    ApiResponse<WorkflowSchema> getProcessById(@PathVariable(ID) String id);

    @DeleteMapping(PROCESS_BY_ID_URL)
    @PreAuthorize(DELETE_OR_ALL_ACCESS)
    ApiResponse<Void> deleteProcessById(@PathVariable(ID) String id);

    @GetMapping(SEARCH_PROCESS_URL)
    @PreAuthorize(READ_OR_ALL_ACCESS)
    ApiResponse<Stream<WorkflowSchema>> searchProcessByIdOrNameLike(@RequestParam(ID_OR_NAME_LIKE) String idOrNameLike) throws UnsupportedEncodingException;
}
