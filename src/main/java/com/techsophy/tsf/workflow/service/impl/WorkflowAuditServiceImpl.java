package com.techsophy.tsf.workflow.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.workflow.config.GlobalMessageSource;
import com.techsophy.tsf.workflow.dto.PaginationResponsePayload;
import com.techsophy.tsf.workflow.dto.WorkflowAuditSchema;
import com.techsophy.tsf.workflow.dto.WorkflowResponse;
import com.techsophy.tsf.workflow.entity.WorkflowAuditDefinition;
import com.techsophy.tsf.workflow.exception.ProcessIdNotFoundException;
import com.techsophy.tsf.workflow.repository.WorkflowDefinitionAuditRepository;
import com.techsophy.tsf.workflow.service.WorkflowAuditService;
import com.techsophy.tsf.workflow.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static com.techsophy.tsf.workflow.constants.ErrorConstants.PROCESS_NOT_FOUND;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class WorkflowAuditServiceImpl implements WorkflowAuditService
{
    private final WorkflowDefinitionAuditRepository processDefinitionAuditRepository;
    private final ObjectMapper objectMapper;
    private final GlobalMessageSource globalMessageSource;
    private final TokenUtils tokenUtils;

    @Override
    public WorkflowResponse saveProcess(WorkflowAuditSchema workflowAuditSchema) throws JsonProcessingException
    {
        WorkflowAuditDefinition workflowAuditDefinition=this.objectMapper.convertValue(workflowAuditSchema,WorkflowAuditDefinition.class);
        processDefinitionAuditRepository.save(workflowAuditDefinition);
        return new WorkflowResponse(String.valueOf(workflowAuditDefinition.getId()),workflowAuditDefinition.getVersion());
    }

    @Override
    public Stream<WorkflowAuditSchema> getAllProcesses(String id, boolean includeProcessContent, Sort sort)
    {
        return this.processDefinitionAuditRepository.findAllById(BigInteger.valueOf(Long.parseLong(id)),sort)
                .map(this::convertEntityToDTO)
                .map(processAuditSchema ->
                {
                    if (includeProcessContent)
                    {
                        return processAuditSchema;
                    }
                    return processAuditSchema.withContent(null);
                });
    }
    @Override
    public PaginationResponsePayload getAllProcesses(String id, boolean includeProcessContent, Pageable pageable)
    {
        Page<WorkflowAuditDefinition> workflowAuditSchemaPage= this.processDefinitionAuditRepository.findAllById(BigInteger.valueOf(Long.parseLong(id)),pageable);
        List<Map<String,Object>> workflowAuditSchemaList=   workflowAuditSchemaPage.stream().map(this::convertEntityToMap).collect(Collectors.toList());
        return tokenUtils.getPaginationResponsePayload(workflowAuditSchemaPage,workflowAuditSchemaList);
    }

    @Override
    public WorkflowAuditSchema getProcessById(String id, Integer version)
    {
        WorkflowAuditDefinition process=
                this.processDefinitionAuditRepository.findById(BigInteger.valueOf(Long.parseLong(id)),version)
                        .orElseThrow(() -> new ProcessIdNotFoundException(PROCESS_NOT_FOUND,globalMessageSource.get(PROCESS_NOT_FOUND,id)));
        return this.objectMapper.convertValue(process, WorkflowAuditSchema.class);
    }

    private Map<String,Object> convertEntityToMap(WorkflowAuditDefinition workflowAuditDefinition)
    {
        WorkflowAuditSchema workflowAuditSchema=convertEntityToDTO(workflowAuditDefinition);
        return this.objectMapper.convertValue(workflowAuditSchema,Map.class);
    }

    private WorkflowAuditSchema convertEntityToDTO(WorkflowAuditDefinition processDefinitionAudit)
    {
        return this.objectMapper.convertValue(processDefinitionAudit, WorkflowAuditSchema.class);
    }
}
