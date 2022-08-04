package com.techsophy.tsf.workflow.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.workflow.config.GlobalMessageSource;
import com.techsophy.tsf.workflow.controller.WorkflowController;
import com.techsophy.tsf.workflow.dto.PaginationResponsePayload;
import com.techsophy.tsf.workflow.dto.WorkflowResponse;
import com.techsophy.tsf.workflow.dto.WorkflowSchema;
import com.techsophy.tsf.workflow.model.ApiResponse;
import com.techsophy.tsf.workflow.service.WorkflowService;
import com.techsophy.tsf.workflow.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.*;

@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class WorkflowControllerImpl implements WorkflowController
{
    private final WorkflowService workflowService;
    private final GlobalMessageSource globalMessageSource;
    private final TokenUtils tokenUtils;

    @Override
    public ApiResponse<WorkflowResponse> saveProcess(String id, String name, Integer version, String content) throws JsonProcessingException
    {
        WorkflowResponse data= workflowService.saveProcess(id,name,version,content);
        return new ApiResponse<>(data,
                true, globalMessageSource.get(SAVE_PROCESS_SUCCESS));
    }

    @Override
    public ApiResponse getAllProcesses(boolean includeProcessContent, String deploymentIdList, String q, Integer page, Integer pageSize, String[] sortBy, String filterColumn, String filterValue) {
        if (page == null)
        {
            return new ApiResponse<>(workflowService.getAllProcesses(includeProcessContent, deploymentIdList, q, tokenUtils.getSortBy(sortBy)), true,
                    globalMessageSource.get(GET_PROCESS_SUCCESS));
        }
            PaginationResponsePayload workflowPaginationResponse = workflowService.getAllProcesses(q, includeProcessContent, tokenUtils.getPageRequest(page, pageSize, sortBy));
            return new ApiResponse<>(workflowPaginationResponse, true, globalMessageSource.get(GET_PROCESS_SUCCESS));

    }

    @Override
    public ApiResponse<WorkflowSchema> getProcessById(String id)
    {
        return new ApiResponse<>(workflowService.getProcessById(id), true, globalMessageSource.get(GET_PROCESS_SUCCESS));
    }

    @Override
    public ApiResponse<Void> deleteProcessById(String id)
    {
        workflowService.deleteProcessById(id);
        return new ApiResponse<>(null, true,  globalMessageSource.get(DELETE_PROCESS_SUCCESS));
    }

    @Override
    public ApiResponse<Stream<WorkflowSchema>> searchProcessByIdOrNameLike(String idOrNameLike) throws UnsupportedEncodingException
    {
        return new ApiResponse<>(this.workflowService.searchProcessByIdOrNameLike(idOrNameLike), true, globalMessageSource.get(GET_PROCESS_SUCCESS));
    }
}
