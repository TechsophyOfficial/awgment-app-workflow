package com.techsophy.tsf.workflow.utils;

import com.techsophy.tsf.workflow.dto.PaginationResponsePayload;
import io.micrometer.core.instrument.util.IOUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(TEST_ACTIVE_PROFILE)
class TokenUtilsPaginationTest
{
    private static final String TOKEN_TXT = TOKEN_TXT_PATH;

    @Mock
    SecurityContext securityContext;
    @InjectMocks
    TokenUtils tokenUtils;

    @Test
    void getTokenFromIssuerTest() throws Exception
    {
        InputStream resource = new ClassPathResource(TOKEN_TXT).getInputStream();
        String result = IOUtils.toString(resource, StandardCharsets.UTF_8);
        String tenant= tokenUtils.getIssuerFromToken(result);
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
        String token= tokenUtils.getTokenFromContext();
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
        List content=new ArrayList();
        content.add(NAME);
        PaginationResponsePayload response= tokenUtils.getPaginationResponsePayload(page,content);
        Assertions.assertNotNull(response);
    }

    @Test
    void getLoggedInUserIdTest()
    {
        Mockito.when(securityContext.getAuthentication()).thenReturn(null);
        assertThatExceptionOfType(SecurityException.class)
                .isThrownBy(() -> tokenUtils.getLoggedInUserId());
    }

}
