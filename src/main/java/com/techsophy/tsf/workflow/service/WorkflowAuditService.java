package com.techsophy.tsf.workflow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.workflow.dto.PaginationResponsePayload;
import com.techsophy.tsf.workflow.dto.WorkflowAuditSchema;
import com.techsophy.tsf.workflow.dto.WorkflowResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Stream;

public interface WorkflowAuditService
{
    @Transactional(rollbackFor = Exception.class)
    WorkflowResponse saveProcess(WorkflowAuditSchema workflowAuditSchema) throws JsonProcessingException;

    Stream<WorkflowAuditSchema> getAllProcesses(String id, boolean includeProcessContent, Sort sort);

    PaginationResponsePayload getAllProcesses(String id, boolean includeProcessContent, Pageable pageable);

    WorkflowAuditSchema getProcessById(String id,Integer version);
}
