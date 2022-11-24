package com.techsophy.tsf.workflow.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import lombok.With;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.*;

@With
@Value
public class WorkflowSchema
{
    @NotNull(message = ID_NOT_NULL) String id;
    @NotBlank(message = NAME_NOT_BLANK) String name;
    byte[] content;
    @NotNull(message = VERSION_NOT_NULL) Integer version;
    String createdById;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = TIME_ZONE)
    Instant createdOn;
    String updatedById;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = TIME_ZONE)
    Instant updatedOn;
}
