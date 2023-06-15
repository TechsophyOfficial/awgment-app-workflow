package com.techsophy.tsf.workflow.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigInteger;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auditable
{
    private BigInteger createdById;
    private BigInteger updatedById;
    private Instant createdOn;
    private Instant updatedOn;
}
