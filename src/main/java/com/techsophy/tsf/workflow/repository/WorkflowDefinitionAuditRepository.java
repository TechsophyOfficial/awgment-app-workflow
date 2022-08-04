package com.techsophy.tsf.workflow.repository;

import com.techsophy.tsf.workflow.entity.WorkflowAuditDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Stream;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.FIND_ALL_BY_ID_QUERY;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.FIND_BY_ID_QUERY;

public interface WorkflowDefinitionAuditRepository extends MongoRepository<WorkflowAuditDefinition, Long>
{
    @Query(FIND_BY_ID_QUERY)
    Optional<WorkflowAuditDefinition> findById(BigInteger id, Integer version);

    @Query(FIND_ALL_BY_ID_QUERY)
    Stream<WorkflowAuditDefinition> findAllById(BigInteger id, Sort sort);

    @Query(FIND_ALL_BY_ID_QUERY)
    Page<WorkflowAuditDefinition> findAllById(BigInteger id, Pageable pageable);
}
