package com.techsophy.tsf.workflow.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorConstants
{
    public static final String LOGGED_IN_USER_ID_NOT_FOUND ="AWGMENT-WORKFLOW-1001";
    public static final String  PROCESS_NOT_FOUND="AWGMENT-WORKFLOW-1002";
    public static final String INVALID_TOKEN ="AWGMENT-WORKFLOW-1003";
    public static final String TOKEN_NOT_NULL="AWGMENT-WORKFLOW-1004";
    public static final String USER_DETAILS_NOT_FOUND_EXCEPTION="AWGMENT-WORKFLOW-1005";
    public static final String UNABLE_TO_GET_TOKEN="AWGMENT-WORKFLOW-1006";
    public static final String AUTHENTICATION_FAILED="AWGMENT-WORKFLOW-1007";
    public static final String INVALID_PAGE_REQUEST ="AWGMENT-WORKFLOW-1008";
    public static final String SERVICE_NOT_AVAILABLE="AWGMENT-WORKFLOW-1009";
}
