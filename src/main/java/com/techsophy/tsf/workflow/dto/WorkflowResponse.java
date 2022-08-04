package com.techsophy.tsf.workflow.dto;

import lombok.Value;
import lombok.With;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@With
@Value
public class WorkflowResponse
{
    @NotBlank String id;
    @NotNull Integer version;
}
