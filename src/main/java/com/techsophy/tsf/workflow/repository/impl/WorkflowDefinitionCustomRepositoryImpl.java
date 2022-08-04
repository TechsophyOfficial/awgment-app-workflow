package com.techsophy.tsf.workflow.repository.impl;

import com.techsophy.tsf.workflow.entity.WorkflowDefinition;
import com.techsophy.tsf.workflow.repository.WorkflowDefinitionCustomRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.*;

@AllArgsConstructor(onConstructor_ = {@Autowired})
public class WorkflowDefinitionCustomRepositoryImpl implements WorkflowDefinitionCustomRepository
{
    private final MongoTemplate mongoTemplate;

    @Override
    public List<WorkflowDefinition> findByNameOrId(String idOrNameLike)
    {
        Query query = new Query();
        String searchString = URLDecoder.decode(idOrNameLike, StandardCharsets.UTF_8);
        query.addCriteria(new Criteria().orOperator(Criteria.where(WORKFLOW_ID).regex(searchString), Criteria.where(WORKFLOW_NAME).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))));
        return mongoTemplate.find(query, WorkflowDefinition.class);
    }

    @Override
    public Stream<WorkflowDefinition> findWorkflowsByQSorting(String q, Sort sort) {
        Query query = new Query();
        String searchString = URLDecoder.decode(q, StandardCharsets.UTF_8);
        query.addCriteria(new Criteria().orOperator(Criteria.where(WORKFLOW_ID).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where(WORKFLOW_NAME).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where(VERSION).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where(CONTENT).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
        ));
        query.with(Sort.by(Sort.Direction.ASC, NAME));
        return mongoTemplate.find(query, WorkflowDefinition.class).stream();
    }

    @Override
    public Page<WorkflowDefinition> findWorkflowsByQPageable(String q, Pageable pageable)
    {
        Query query = new Query();
        Query countQuery = new Query();
        String searchString = URLDecoder.decode(q, StandardCharsets.UTF_8);
        query.addCriteria(new Criteria().orOperator(Criteria.where(WORKFLOW_ID).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where(WORKFLOW_NAME).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where(VERSION).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where(CONTENT).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
        )).with(pageable);
        List<WorkflowDefinition> workflowDefinitions = mongoTemplate.find(query, WorkflowDefinition.class);
        query.with(Sort.by(Sort.Direction.ASC, NAME));
        countQuery.addCriteria(new Criteria().orOperator(Criteria.where(WORKFLOW_ID).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where(WORKFLOW_NAME).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where(VERSION).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where(CONTENT).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
        ));
        long count=mongoTemplate.count(countQuery,WorkflowDefinition.class);
        return new PageImpl<>(workflowDefinitions, pageable,count );
    }

    @Override
    public List<WorkflowDefinition> findByIdIn(List<String> idList)
    {
        Query query = new Query(Criteria.where(WORKFLOW_ID).in(idList));
        return mongoTemplate.find(query, WorkflowDefinition.class);
    }
}
