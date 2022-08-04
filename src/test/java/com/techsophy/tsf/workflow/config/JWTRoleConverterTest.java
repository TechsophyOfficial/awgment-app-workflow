package com.techsophy.tsf.workflow.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.workflow.utils.WebClientWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.*;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.GET;
import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.TEST_ACTIVE_PROFILE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@SpringBootTest
class JWTRoleConverterTest
{
    @Mock
    HttpServletRequest mockHttpServletRequest;

    @Mock
    ObjectMapper mockObjectMapper;

    @Mock
    WebClientWrapper webClientWrapper;

    @InjectMocks
    JWTRoleConverter jwtRoleConverter;

    @Test
    void convertTest() throws JsonProcessingException
    {
        Map<String, Object> map = new HashMap<>();
        map.put("clientRoles", "abc");
        List<String> list=new ArrayList<>();
        list.add("augmnt");
        list.add("augmnt");
        Jwt jwt= new Jwt("1", Instant.now(),null,Map.of("abc","abc"),Map.of("abc","abc"));
        WebClient webClient= WebClient.builder().build();
        when(webClientWrapper.createWebClient(any())).thenReturn(webClient);
        when(webClientWrapper.webclientRequest(any(),any(),eq(GET),any()))
                .thenReturn("abc");
        when(mockObjectMapper.readValue("abc",Map.class)).thenReturn(map);
        when(mockObjectMapper.convertValue(any(),eq(List.class))).thenReturn(list);
        Collection grantedAuthority =  jwtRoleConverter.convert(jwt);
        Assertions.assertNotNull(grantedAuthority);
    }
}
