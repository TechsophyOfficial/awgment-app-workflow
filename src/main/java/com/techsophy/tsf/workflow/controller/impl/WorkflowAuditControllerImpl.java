package com.techsophy.tsf.workflow.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.workflow.config.GlobalMessageSource;
import com.techsophy.tsf.workflow.controller.WorkflowAuditController;
import com.techsophy.tsf.workflow.dto.WorkflowAuditSchema;
import com.techsophy.tsf.workflow.dto.WorkflowResponse;
import com.techsophy.tsf.workflow.model.ApiResponse;
import com.techsophy.tsf.workflow.service.WorkflowAuditService;
import com.techsophy.tsf.workflow.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.GET_PROCESS_SUCCESS;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.SAVE_PROCESS_SUCCESS;

@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class WorkflowAuditControllerImpl implements WorkflowAuditController
{
    private final WorkflowAuditService workflowAuditService;
    private final GlobalMessageSource globalMessageSource;
    private final TokenUtils tokenUtils;

    @Override
    public ApiResponse<WorkflowResponse> saveProcess(WorkflowAuditSchema workflowAuditSchema) throws JsonProcessingException
    {
        return new ApiResponse<>(workflowAuditService.saveProcess(workflowAuditSchema),
                true, globalMessageSource.get(SAVE_PROCESS_SUCCESS));
    }

    @Override
    public ApiResponse  getAllProcesses(String id, boolean includeProcessContent, Integer page, Integer pageSize, String[] sortBy)
    {
        if(page==null)
        {
            return new ApiResponse<>(workflowAuditService.getAllProcesses(id, includeProcessContent,tokenUtils.getSortBy(sortBy) ), true, globalMessageSource.get(GET_PROCESS_SUCCESS));
        }
        return new ApiResponse<>(workflowAuditService.getAllProcesses(id, includeProcessContent, tokenUtils.getPageRequest(page,pageSize,sortBy)), true, globalMessageSource.get(GET_PROCESS_SUCCESS));
    }

    @Override
    public ApiResponse getProcessById(String id, Integer version)
    {
        return new ApiResponse<>(workflowAuditService.getProcessById(id,version), true, globalMessageSource.get(GET_PROCESS_SUCCESS));
    }
}
