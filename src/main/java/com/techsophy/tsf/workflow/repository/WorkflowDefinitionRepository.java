package com.techsophy.tsf.workflow.repository;

import com.techsophy.tsf.workflow.entity.WorkflowDefinition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface WorkflowDefinitionRepository extends MongoRepository<WorkflowDefinition, Long>, WorkflowDefinitionCustomRepository
{
    Optional<WorkflowDefinition> findById(BigInteger id);

    boolean existsById(BigInteger id);

    int deleteById(BigInteger id);
}
