package com.techsophy.tsf.workflow.dto;

import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.*;

@With
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowAuditSchema
{
    @NotNull(message = ID_NOT_NULL) String id;
    @NotNull(message = ID_NOT_NULL) String processId;
    @NotBlank(message = NAME_NOT_BLANK) String name;
    @NotNull(message = VERSION_NOT_NULL) Integer version;
    byte[] content;
    String createdById;
    Instant createdOn;
    String createdByName;
    String updatedById;
    Instant updatedOn;
    String updatedByName;
}
