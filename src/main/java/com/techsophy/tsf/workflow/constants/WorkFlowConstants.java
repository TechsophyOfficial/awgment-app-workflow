package com.techsophy.tsf.workflow.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkFlowConstants
{
    //CustomFilterConstants
    public static final String PACKAGE_NAME ="com.techsophy.tsf.*";
    public static final String MULTI_TENANCY_PACKAGE_NAME ="com.techsophy.multitenancy.mongo.*";

    /*LocaleConfig Constants*/
    public static final String ACCEPT_LANGUAGE = "Accept-Language";

    /*TenantAuthenticationManager*/
    public static final String KEYCLOAK_ISSUER_URI = "${keycloak.issuer-uri}";

    /*WorkflowControllerConstants*/
    public static final String BASE_URL = "/process-modeler";
    public static final String VERSION_V1 = "/v1";
    public static final String HISTORY ="/history";
    public static final String PROCESSES_URL = "/processes";
    public static final String PROCESS_BY_ID_URL = "/processes/{id}";
    public static final String PROCESS_VERSION_BY_ID_URL = "/processes/{id}/{version}";
    public static final String PROCESS_ID_URL = "/processes/{id}";
    public static final String SEARCH_PROCESS_URL = "/processes/search";
    public static final String MANDATORY_FIELDS="&only-mandatory-fields=true";
    public static final String ID = "id";
    public static final String NAME="name";
    public static final String VERSION="version";
    public static final String CONTENT = "content";
    public static final String ID_OR_NAME_LIKE = "idOrNameLike";
    public static final String INCLUDE_CONTENT ="include-content";
    public static final String DEPLOYMENT="deployment";
    public static final String SAVE_PROCESS_SUCCESS="SAVE_PROCESS_SUCCESS";
    public static final String GET_PROCESS_SUCCESS="GET_PROCESS_SUCCESS";
    public static final String DELETE_PROCESS_SUCCESS="DELETE_PROCESS_SUCCESS";
    public static final String PAGE="page";
    public static final String SIZE="size";
    public static final String SORT_BY="sort-by";
    public static final String FILTER_COLUMN= "filter-column";
    public static final String FILTER_VALUE= "filter-value";
    public static final String QUERY ="q";

    /*WorkflowSchemaConstants*/
    public static final String ID_NOT_NULL= "Id should not be null";
    public static final String NAME_NOT_BLANK="Name should not be blank";
    public static final String VERSION_NOT_NULL="Version should not be null";
    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String TIME_ZONE = "UTC";

    /*WorkflowDefinitionConstants*/
    public static final String TP_PROCESS_DEFINITION_COLLECTION ="tp_process_definition";
    public static final String TP_PROCESS_DEFINITION_AUDIT_COLLECTION ="tp_process_definition_audit";

    /*WorkflowDefinitionCustomRepositoryImplConstants*/
    public static final String WORKFLOW_ID="_id";
    public static final String WORKFLOW_NAME="name";

    /*WorkflowDefinitionAuditRepositoryConstants*/
    public static final String FIND_BY_ID_QUERY ="{'processId' : ?0 , 'version' : ?1}";
    public static final String FIND_ALL_BY_ID_QUERY="{'processId' : ?0 }";
    /*WorkflowServiceImplConstants*/
    public static final String REGEX_COMMA=",";
    public static final String FIRST_NAME="firstName";
    public static final String LAST_NAME="lastName";

    /*TokenUtilsConstants*/
    public static final String PREFERED_USERNAME="preferred_username";
    public static final String DESCENDING="desc";
    public static final String CREATED_ON="createdOn";
    public static final String CREATED_ON_ASC="createdOn: ASC";
    public static final String  COLON=":";
    public static final String DEFAULT_PAGE_LIMIT= "${default.pagelimit}";

    /*UserDetailsConstants*/
    public static final String GATEWAY_URL ="${gateway.uri}";
    public static final String LOGGED_USER="loggeduser";
    public static final String TOKEN="token";
    public static final String AUTHORIZATION="Authorization";
    public static final String CONTENT_TYPE="Content-Type";
    public static final String RESPONSE="response";
    public static final String DATA="data";
    public static final String FILTER_COLUMN_URL ="?filter-column=loginId&filter-value=";
    public static final String GATEWAY="gateway";

    /*TokenUtilsAndWebclientWrapperConstants*/
    public static final String EMPTY_STRING="";
    public static final String BEARER="Bearer ";
    public static final String REGEX_SPLIT="\\.";
    public static final String ISS="iss";
    public static final String URL_SEPERATOR="/";
    public static final int SEVEN=7;
    public static final int ONE=1;

    /*UserDetailsConstants*/
    public static final String APPLICATION_JSON ="application/json";
    public static final String ACCOUNTS_URL ="/accounts/v1/users";

    //WebClientWrapper
    public static final String GET="GET";
    public static final String POST="POST";
    public static final String PUT="PUT";
    public static final String DELETE="DELETE";
    public static final String SERVICE = "service";

    /*MainMethodConstants*/
    public static final String VERSION_1="v1";
    public static final String WORKFLOW_MODELER ="tp-app-workflow";
    public static final String WORKFLOW_MODELER_API_VERSION_V1 ="Workflow Modeler API v1";
    //LoggingHandler
    public static final String CONTROLLER_CLASS_PATH = "execution(* com.techsophy.tsf.workflow.controller..*(..))";
    public static final String SERVICE_CLASS_PATH= "execution(* com.techsophy.tsf.workflow.service..*(..))";
    public static final String EXCEPTION = "ex";
    public static final String IS_INVOKED_IN_CONTROLLER= "{}() is invoked in controller ";
    public static final String IS_INVOKED_IN_SERVICE= "{}() is invoked in service ";
    public static final String EXECUTION_IS_COMPLETED_IN_CONTROLLER="{}() execution is completed  in controller";
    public static final String EXECUTION_IS_COMPLETED_IN_SERVICE="{}() execution is completed  in service";
    public static final String EXCEPTION_THROWN="An exception has been thrown in {} ";
    public static final String CAUSE="Cause : {} ";
    public static final String BRACKETS_IN_CONTROLLER="{}() in controller";
    public static final String BRACKETS_IN_SERVICE="{}() in service";

    /*LocaleConfig Constants*/
    public static final String BASENAME_ERROR_MESSAGES = "classpath:errorMessages";
    public static final String BASENAME_MESSAGES = "classpath:messages";
    public static final Long CACHEMILLIS = 3600L;
    public static final Boolean USEDEFAULTCODEMESSAGE = true;

    //JWTRoleConverter
    public static final String CLIENT_ROLES="clientRoles";
    public static final String USER_INFO_URL= "/protocol/openid-connect/userinfo";
    public static final String TOKEN_VERIFICATION_FAILED="Token verification failed";
    public static final String AWGMENT_ROLES_MISSING_IN_CLIENT_ROLES ="AwgmentRoles are missing in clientRoles";
    public static final String CLIENT_ROLES_MISSING_IN_USER_INFORMATION="ClientRoles are missing in the userInformation";

    // Roles
    public static final String OR=" or ";
    public static final String HAS_ANY_AUTHORITY="hasAnyAuthority('";
    public static final String HAS_ANY_AUTHORITY_ENDING="')";
    public static final String AWGMENT_WORKFLOW_CREATE_OR_UPDATE = "awgment-workflow-create-or-update";
    public static final String AWGMENT_WORKFLOW_READ = "awgment-workflow-read";
    public static final String AWGMENT_WORKFLOW_DELETE = "awgment-workflow-delete";
    public static final String AWGMENT_WORKFLOW_ALL ="awgment-workflow-all";
    public static final String CREATE_OR_ALL_ACCESS =HAS_ANY_AUTHORITY+AWGMENT_WORKFLOW_CREATE_OR_UPDATE+HAS_ANY_AUTHORITY_ENDING+OR+HAS_ANY_AUTHORITY+AWGMENT_WORKFLOW_ALL+HAS_ANY_AUTHORITY_ENDING;
    public static final String READ_OR_ALL_ACCESS =HAS_ANY_AUTHORITY+AWGMENT_WORKFLOW_READ+HAS_ANY_AUTHORITY_ENDING+OR+HAS_ANY_AUTHORITY+AWGMENT_WORKFLOW_ALL+HAS_ANY_AUTHORITY_ENDING;
    public static final String DELETE_OR_ALL_ACCESS =HAS_ANY_AUTHORITY+ AWGMENT_WORKFLOW_DELETE +HAS_ANY_AUTHORITY_ENDING+OR+HAS_ANY_AUTHORITY+AWGMENT_WORKFLOW_ALL+HAS_ANY_AUTHORITY_ENDING;
}
