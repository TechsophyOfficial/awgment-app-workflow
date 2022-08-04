package com.techsophy.tsf.workflow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.workflow.dto.PaginationResponsePayload;
import com.techsophy.tsf.workflow.dto.WorkflowResponse;
import com.techsophy.tsf.workflow.dto.WorkflowSchema;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;

public interface WorkflowService
{
    @Transactional(rollbackFor = Exception.class)
    WorkflowResponse saveProcess(String id, String name, Integer version, String content) throws JsonProcessingException;

    Stream<WorkflowSchema> getAllProcesses(boolean includeProcessContent, String deploymentIdList,String q, Sort sort);

    PaginationResponsePayload getAllProcesses(String q,boolean includeProcessContent, Pageable pageable);

    WorkflowSchema getProcessById(String id);

    void deleteProcessById(String id);

    Stream<WorkflowSchema> searchProcessByIdOrNameLike(String idOrName) throws UnsupportedEncodingException;
}
