package com.techsophy.tsf.workflow.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkflowTestConstants
{
    //GlobalMessageSourceConstants
    public static final String  KEY="key";
    public static final String ARGS="args";

    //WorkflowControllerExceptionTest
    public static final String TENANT="tenant";
    public static final String PROCESS_NOT_FOUND_WITH_GIVEN_ID="Process not found with given id";
    public static final String ENTITY_ID_NOT_FOUND="Entity id not found";
    public static final String USER_DETAILS_NOT_FOUND_WITH_GIVEN_ID="UserDetails not found with given id";

    //WorkflowControllerConstants
    public final static String PROCESS_ID = "1";
    public final static String PROCESS_NAME = "workflow1";
    public final static byte[] PROCESS_CONTENT ={10,20};
    public final static Integer PROCESS_VERSION = 1;
    public final static String CREATED_BY_ID_VALUE ="1";
    public final static Instant CREATED_ON_NOW =Instant.now();
    public final static String UPDATED_BY_ID_VALUE ="1";
    public final static String NAME ="name";
    public final static Instant UPDATED_ON_NOW =Instant.now();
    public static final String TEST_ACTIVE_PROFILE ="test";
    public static final String BASE_URL_PROCESS = "/process-modeler";
    public static final String VERSION_V1 = "/v1";
    public static final String PROCESSES_URL = "/processes";
    public static final String PROCESSES_BY_ID_URL ="/processes/{id}";
    public static final String SEARCH_PROCESSES_URL = "/processes/search";
    public final static String TEST_ID_OR_NAME_LIKE ="abc";
    public final static String PARAM_ID="id";
    public final static String PARAM_ID_VALUE="1";
    public final static String PARAM_NAME="name";
    public final static String PARAM_NAME_VALUE="WorkflowTest";
    public final static String PARAM_VERSION="version";
    public final static String PARAM_VERSION_VALUE="1";
    public final static String PARAM_CONTENT="content";
    public final static String PARAM_CONTENT_VALUE="abc";
    public final static String PARAM_INCLUDE_CONTENT="include-content";
    public final static String PARAM_DEPLOYMENT="deployment";
    public final static String PARAM_DEPLOYMENT_VALUE ="a";
    public final static String PARAM_ID_OR_NAME_LIKE ="idOrNameLike";
    public final static String PARAM_ID_OR_NAME_LIKE_VALUE ="abc";
    public static final String PAGE_SIZE="5";
    public static final String PAGE="page";
    public static final String SIZE="size";
    public static final String TENANT_NAME="tenantName";
    public static final String FILTER="filter";
    public static final String SET_PAGE="1";
    public static final String TOTAL_PAGES="1";
    public static final String TOTAL_ELEMENTS="2";
    public static final String SIZE_VALUE="5";
    public static final String NUMBER_OF_ELEMENTS="2";
    public static final String TRUE="true";
    public static final String PAGE_VALUE="1";
    public static final String Q="abc";
    public static final String DEPLOYMENT_ID_LIST="abc";

    //WORKFLOWDEFCUSTOMREPOSCONSTANTS
    public final static String PROCESS_ID_2="2";

    //WorkflowServiceConstants
    public static final String TEST_PROCESSES_DATA_1 = "testdata/process-schema1.json";
    public static final String TEST_PROCESSES_DATA_2 = "testdata/process-schema2.json";
    public static final String TEST_ID="1";
    public static final Integer PAGE_NUMBER=1;
    public static final Integer PAGE_SIZE_VALUE=1;

    //TokenUtilsTest
    public static final String TOKEN_TXT_PATH = "testdata/token.txt";
    public static final String TECHSOPHY_PLATFORM="techsophy-platform";

    //INITILIZATION CONSTANTS
    public static final String DEPARTMENT="department";
    public static final String  NULL=null;
    public static final String EMAIL_ID="emailId";
    public static final String MOBILE_NUMBER="mobileNumber";
    public static final String LAST_NAME="lastName";
    public static final String  FIRST_NAME="firstName";
    public static final String USER_NAME="userName";
    public static final String ID="id";
    public static final String UPDATED_ON="updatedOn";
    public static final String UPDATED_BY_NAME="updatedByName";
    public static final String UPDATED_BY_ID="updatedById";
    public static final String CREATED_ON="createdOn";
    public static final String CREATED_BY_NAME ="createdByName";
    public static final String CREATED_BY_ID="createdById";
    public static final String USER_FIRST_NAME ="tejaswini";
    public static final String USER_LAST_NAME ="Kaza";
    public static final String NUMBER="1234567890";
    public static final String MAIL_ID ="tejaswini.k@techsophy.com";
    public static final String ID_NUMBER= "847117072898674688";
    public static final String BIGINTEGER_ID = "847117072898674688";
    public static final String LOGGED_USER_ID = "847117072898674688";

    //UserDetailsTestConstants
    public static final String  USER_DETAILS_RETRIEVED_SUCCESS="User details retrieved successfully";
    public static final String  ABC="abc";
    public static final String TEST_TOKEN="token";
    public static final String INITIALIZATION_DATA="{\"data\":[{\"id\":\"847117072898674688\",\"userName\":\"tejaswini\",\"firstName\":\"Kaza\",\"lastName\":\"Tejaswini\",\"mobileNumber\":\"1234567890\",\"emailId\":\"tejaswini.k@techsophy.com\",\"department\":null,\"createdById\":null,\"createdByName\":null,\"createdOn\":null,\"updatedById\":null,\"updatedByName\":null,\"updatedOn\":null}],\"success\":true,\"message\":\"User details retrieved successfully\"}\n";
    public static final String EMPTY_STRING="";

    //TokenUtilsTest
    public static final String TOKEN_TEST_DATA="testdata/token.txt";
    public final static String TEST_SORT="name";

    //WEBCLIENTWrapperTestConstants
    public static final String LOCAL_HOST_URL="http://localhost:8080";
    public static final String TOKEN="token";
    public static final String TEST="test";
    public static final String PUT="put";
    public static final String DELETE="delete";
}


