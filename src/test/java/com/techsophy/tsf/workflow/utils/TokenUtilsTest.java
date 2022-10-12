package com.techsophy.tsf.workflow.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.workflow.config.GlobalMessageSource;
import com.techsophy.tsf.workflow.dto.PaginationResponsePayload;
import com.techsophy.tsf.workflow.dto.WorkflowSchema;
import com.techsophy.tsf.workflow.exception.InvalidInputException;
import io.micrometer.core.instrument.util.IOUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(TEST_ACTIVE_PROFILE)
class TokenUtilsTest
{
    private static final String TOKEN_TXT = TOKEN_TEST_DATA;

    @Mock
    SecurityContext securityContext;
    @Mock
    GlobalMessageSource mockGlobalMessageSource;
    @Mock
    MessageSource mockMessageSource;
    @Mock
    ObjectMapper mockObjectMapper;
    @InjectMocks
    TokenUtils tokenUtils;

    @BeforeEach
    public void init()
    {
        ReflectionTestUtils.setField(tokenUtils,"defaultPageLimit", 20);
    }

    @Test
    void getTokenFromIssuerTest() throws Exception
    {
        InputStream resource = new ClassPathResource(TOKEN_TXT).getInputStream();
        String result = IOUtils.toString(resource, StandardCharsets.UTF_8);
        String tenant=tokenUtils.getIssuerFromToken(result);
        assertThat(tenant).isEqualTo(TECHSOPHY_PLATFORM);
    }

    @Test
    void getTokenFromContext()
    {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Jwt jwt= mock(Jwt.class);
        when(authentication.getPrincipal()).thenReturn(jwt);
        String token=tokenUtils.getTokenFromContext();
        assertThat(token).isNull();
    }

    @Test
    void getTokenFromContextException()
    {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        assertThatExceptionOfType(SecurityException.class)
                .isThrownBy(() -> tokenUtils.getLoggedInUserId());
    }

    @Test
    void getPaginationTest()
    {
        Page page = mock(Page.class);
        List<Map<String,Object>> content=new ArrayList();
        PaginationResponsePayload response=tokenUtils.getPaginationResponsePayload(page,content);
        Assertions.assertNotNull(response);
    }

    @Test
    void getPaginationTestContentNullTest()
    {
        WorkflowSchema workflowSchemaTest =new WorkflowSchema(PROCESS_ID,PROCESS_NAME,PROCESS_CONTENT,PROCESS_VERSION,CREATED_BY_ID_VALUE,CREATED_ON_NOW,CREATED_BY_NAME,UPDATED_BY_ID_VALUE,UPDATED_ON_NOW,UPDATED_BY_NAME);
        List<WorkflowSchema> content=new ArrayList();
        content.add(workflowSchemaTest);
        Page page = mock(Page.class);
        Mockito.when(mockObjectMapper.convertValue(page.getContent(),new TypeReference<List<WorkflowSchema>>() {})).thenReturn(Collections.singletonList(workflowSchemaTest));
        PaginationResponsePayload response=tokenUtils.getPaginationResponsePayload(page,Collections.emptyList());
        Assertions.assertNotNull(response);
    }
//    @Test
//    void getTokenFromContextAuthentication()
//    {
//        try (MockedStatic<SecurityContextHolder> dummy = Mockito.mockStatic(SecurityContextHolder.class)) {
//            dummy.when(() -> SecurityContextHolder.getContext())
//                    .thenReturn(null);
//            Assertions.assertThrows(SecurityException.class,()->tokenUtils.getTokenFromContext());
//        }
////        Authentication authentication = mock(Authentication.class);
////        SecurityContext securityContext = mock(SecurityContext.class);
////        SecurityContextHolder securityContextHolder = mock(SecurityContextHolder.class);
////        Mockito.when(securityContext.getAuthentication()).thenReturn(null);
////        Mockito.when(SecurityContextHolder.getContext()).thenReturn(null);
////        SecurityContextHolder.setContext(securityContext);
////        Jwt jwt= mock(Jwt.class);
////        when(authentication.getPrincipal()).thenReturn(jwt);
////        Assertions.assertThrows(SecurityException.class,()->tokenUtils.getTokenFromContext());
//    }

    @Test
    void getPageExceptionTest()
    {
        Assertions.assertThrows(InvalidInputException.class, () ->
                tokenUtils.getPageRequest(null,null,null));
    }

    @Test
    void getPageRequestTest()
    {
        PageRequest pageRequest=tokenUtils.getPageRequest(1,1, new String[]{TEST_SORT});
        Assertions.assertNotNull(pageRequest);
    }

    @Test
    void getPageRequestPageSizeNegativeTest()
    {
        PageRequest pageRequest=tokenUtils.getPageRequest(1,-1, new String[]{TEST_SORT});
        Assertions.assertNotNull(pageRequest);
    }

    @Test
    void getLoggedInUserIdTest()
    {
        Mockito.when(securityContext.getAuthentication()).thenReturn(null);
        assertThatExceptionOfType(SecurityException.class)
                .isThrownBy(() -> tokenUtils.getLoggedInUserId());
    }

    @Test
    void getSortByTest()
    {
        String[] sortByArray={"name","id"};
        Sort sort= tokenUtils.getSortBy(sortByArray);
        Assertions.assertNotNull(sort);
    }
}

