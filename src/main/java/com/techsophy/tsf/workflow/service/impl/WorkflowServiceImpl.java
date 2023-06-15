package com.techsophy.tsf.workflow.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.idgenerator.IdGeneratorImpl;
import com.techsophy.tsf.workflow.config.GlobalMessageSource;
import com.techsophy.tsf.workflow.dto.PaginationResponsePayload;
import com.techsophy.tsf.workflow.dto.WorkflowAuditSchema;
import com.techsophy.tsf.workflow.dto.WorkflowResponse;
import com.techsophy.tsf.workflow.dto.WorkflowSchema;
import com.techsophy.tsf.workflow.entity.WorkflowDefinition;
import com.techsophy.tsf.workflow.exception.EntityIdNotFoundException;
import com.techsophy.tsf.workflow.exception.ProcessIdNotFoundException;
import com.techsophy.tsf.workflow.exception.UserDetailsIdNotFoundException;
import com.techsophy.tsf.workflow.repository.WorkflowDefinitionRepository;
import com.techsophy.tsf.workflow.service.WorkflowAuditService;
import com.techsophy.tsf.workflow.service.WorkflowService;
import com.techsophy.tsf.workflow.utils.TokenUtils;
import com.techsophy.tsf.workflow.utils.UserDetails;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static com.techsophy.tsf.workflow.constants.ErrorConstants.LOGGED_IN_USER_ID_NOT_FOUND;
import static com.techsophy.tsf.workflow.constants.ErrorConstants.PROCESS_NOT_FOUND;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.*;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class WorkflowServiceImpl implements WorkflowService
{
    private final WorkflowDefinitionRepository processDefinitionRepository;
    private final WorkflowAuditService workflowAuditService;
    private final ObjectMapper objectMapper;
    private final UserDetails userDetails;
    private final IdGeneratorImpl idGeneratorImpl;
    private final GlobalMessageSource globalMessageSource;
    private final TokenUtils tokenUtils;

    @Override
    public WorkflowResponse saveProcess(String  id, String name, Integer version, String content) throws JsonProcessingException
    {
        WorkflowDefinition processData;
        BigInteger uniqueId;
        Map<String,Object>loggedInUserDetails =userDetails.getUserDetails().get(0);
        if (isEmpty(loggedInUserDetails.get(ID).toString()))
        {
            throw new UserDetailsIdNotFoundException(LOGGED_IN_USER_ID_NOT_FOUND,globalMessageSource.get(LOGGED_IN_USER_ID_NOT_FOUND, loggedInUserDetails.get(ID).toString()));
        }
        BigInteger loggedInUserId = BigInteger.valueOf(Long.parseLong(loggedInUserDetails.get(ID).toString()));
        if(isEmpty(id))
        {
            uniqueId = idGeneratorImpl.nextId();
            version = 1;
            processData=setCreatedDetails(uniqueId,name,version,content.getBytes(),loggedInUserDetails);
        }
        else
        {
            uniqueId= BigInteger.valueOf(Long.parseLong(id));
            if (!processDefinitionRepository.existsById(BigInteger.valueOf(Long.parseLong(id))))
            {
                processData=setCreatedDetails(uniqueId,name,version,content.getBytes(),loggedInUserDetails);
            }
            else
            {
                processData= processDefinitionRepository.findById(uniqueId).orElseThrow(() -> new EntityIdNotFoundException(LOGGED_IN_USER_ID_NOT_FOUND,globalMessageSource.get(LOGGED_IN_USER_ID_NOT_FOUND,loggedInUserDetails.get(ID).toString())));
                processData.setName(name);
                processData.setVersion(processData.getVersion()+1);
                processData.setContent(content.getBytes());
           }
        }
        processData.setUpdatedOn(Instant.now());
        processData.setUpdatedById(loggedInUserId);
        WorkflowDefinition workflowDefinition=this.processDefinitionRepository.save(processData);
        WorkflowAuditSchema workflowAuditSchema =this.objectMapper.convertValue(processData,WorkflowAuditSchema.class);
        workflowAuditSchema.setId(idGeneratorImpl.nextId().toString());
        workflowAuditSchema.setProcessId(workflowDefinition.getId().toString());
        this.workflowAuditService.saveProcess(workflowAuditSchema);
        return this.objectMapper.convertValue(processData, WorkflowResponse.class);
    }

    @Override
    public Stream<WorkflowSchema> getAllProcesses(boolean includeProcessContent, String deploymentIdList, String q, Sort sort)
    {
        if(StringUtils.isNotBlank(deploymentIdList))
        {
            String[] idList = deploymentIdList.split(REGEX_COMMA);
            List<String> deploymentList = Arrays.asList(idList);
            return this.processDefinitionRepository.findByIdIn(deploymentList).stream()
                    .map(flows ->
                            this.objectMapper.convertValue(flows, WorkflowSchema.class));
        }
        if(StringUtils.isEmpty(q))
        {
            return this.processDefinitionRepository.findAll(sort).stream()
                    .map(this::convertEntityToDTO)
                    .map(processSchema ->
                    {
                        if (includeProcessContent)
                        {
                            return processSchema;
                        }
                        return processSchema.withContent(null);
                    });
        }
        return this.processDefinitionRepository.findWorkflowsByQSorting(q,sort)
                .map(this::convertEntityToDTO)
                .map(processSchema ->
                {
                    if (includeProcessContent)
                    {
                        return processSchema;
                    }
                    return processSchema.withContent(null);
                });
    }

    @Override
    public PaginationResponsePayload getAllProcesses(String q, boolean includeProcessContent, Pageable pageable)
    {
        if(isEmpty(q))
        {
            Page<WorkflowDefinition> workflowDefinitions = this.processDefinitionRepository.findAll(pageable);
            List<Map<String,Object>> workflowSchemaList = workflowDefinitions.stream()
                    .map(this::convertEntityToMap).collect(Collectors.toList());
            return tokenUtils.getPaginationResponsePayload(workflowDefinitions, workflowSchemaList);
        }
        Page<WorkflowDefinition> workflowPage = this.processDefinitionRepository.findWorkflowsByQPageable(q,pageable);
        List<Map<String,Object>> workflowSchemaList = workflowPage.stream()
                .map(this::convertEntityToMap).collect(Collectors.toList());
        return tokenUtils.getPaginationResponsePayload(workflowPage, workflowSchemaList);
    }
    @Override
    public WorkflowSchema getProcessById(String  id)
    {
        WorkflowDefinition process=
         this.processDefinitionRepository.findById(BigInteger.valueOf(Long.parseLong(id)))
                .orElseThrow(() -> new ProcessIdNotFoundException(PROCESS_NOT_FOUND,globalMessageSource.get(PROCESS_NOT_FOUND,id)));
        return convertEntityToDTO(process);
    }

    @Override
    public void deleteProcessById(String id)
    {
        if (!processDefinitionRepository.existsById(BigInteger.valueOf(Long.parseLong(id))))
        {
            throw new EntityIdNotFoundException(PROCESS_NOT_FOUND,globalMessageSource.get(PROCESS_NOT_FOUND,id));
        }
        this.processDefinitionRepository.deleteById(BigInteger.valueOf(Long.parseLong(id)));
    }

    @Override
    public Stream<WorkflowSchema> searchProcessByIdOrNameLike(String idOrName) throws UnsupportedEncodingException
    {
        return this.processDefinitionRepository.findByNameOrId(idOrName).stream().
                map(this::convertEntityToDTO).map(processSchema -> processSchema.withContent(null));
    }

    private Map<String,Object> convertEntityToMap(WorkflowDefinition workflowDefinition)
    {
        WorkflowSchema workflowSchema=convertEntityToDTO(workflowDefinition);
        return this.objectMapper.convertValue(workflowSchema,Map.class);
    }


    private WorkflowSchema convertEntityToDTO(WorkflowDefinition processDefinition)
    {
        return this.objectMapper.convertValue(processDefinition, WorkflowSchema.class);
    }

    private WorkflowDefinition setCreatedDetails(BigInteger uniqueId, String name, Integer version, byte[] content, Map<String,Object> loggedInUserDetails)
    {
        WorkflowDefinition workflowDefinition;
        BigInteger loggedInUserId = BigInteger.valueOf(Long.parseLong(loggedInUserDetails.get(ID).toString()));
        workflowDefinition = new WorkflowDefinition(uniqueId, name, version, content);
        workflowDefinition.setCreatedOn(Instant.now());
        workflowDefinition.setCreatedById(loggedInUserId);
        return workflowDefinition;
    }
}
