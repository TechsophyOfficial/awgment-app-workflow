package com.techsophy.tsf.workflow.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.math.BigInteger;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.TP_PROCESS_DEFINITION_COLLECTION;

@EqualsAndHashCode(callSuper = true)
@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = TP_PROCESS_DEFINITION_COLLECTION)
public class WorkflowDefinition extends Auditable implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    private BigInteger id;
    private String name;
    private Integer version;
    private byte[] content;
}
