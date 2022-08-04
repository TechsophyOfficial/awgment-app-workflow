package com.techsophy.tsf.workflow.repository;

import com.techsophy.tsf.workflow.entity.WorkflowDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Stream;

public interface WorkflowDefinitionCustomRepository
{
    List<WorkflowDefinition> findByNameOrId(String idOrNameLike) throws UnsupportedEncodingException;
    Stream<WorkflowDefinition> findWorkflowsByQSorting(String q, Sort sort);
    Page<WorkflowDefinition> findWorkflowsByQPageable(String q, Pageable pageable);
    List<WorkflowDefinition> findByIdIn(List<String> idList);
}
