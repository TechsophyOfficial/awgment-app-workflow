package com.techsophy.tsf.workflow.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class PaginationResponsePayload
{
    private List<Map<String,Object>> content;
    private int totalPages;
    private long totalElements;
    private int page;
    private int size;
    private int numberOfElements;
}

