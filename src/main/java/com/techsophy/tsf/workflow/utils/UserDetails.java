package com.techsophy.tsf.workflow.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.workflow.config.GlobalMessageSource;
import com.techsophy.tsf.workflow.exception.InvalidInputException;
import com.techsophy.tsf.workflow.exception.UserDetailsIdNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;
import static com.techsophy.tsf.workflow.constants.ErrorConstants.*;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.*;

@RefreshScope
@Slf4j
@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class UserDetails
{
    private final GlobalMessageSource globalMessageSource;
    private final TokenUtils tokenUtils;
    private final ObjectMapper objectMapper;
    private final WebClientWrapper webClientWrapper;
    @Value(GATEWAY_URL)
    String gatewayApi;

    public List<Map<String, Object>> getUserDetails() throws JsonProcessingException
    {
        Map<String,Object> response;
        List<Map<String, Object>> userDetailsResponse;
        WebClient webClient;
        String loggedInUserId = tokenUtils.getLoggedInUserId();
        if (StringUtils.isEmpty(loggedInUserId))
        {
            throw new InvalidInputException(LOGGED_IN_USER_ID_NOT_FOUND,globalMessageSource.get(LOGGED_IN_USER_ID_NOT_FOUND,loggedInUserId));
        }
        String token = tokenUtils.getTokenFromContext();
        log.info(LOGGED_USER + loggedInUserId);
        log.info(TOKEN + token);
        if (StringUtils.isNotEmpty(token))
        {
            webClient = webClientWrapper.createWebClient(token);
        }
        else
        {
            throw new InvalidInputException(TOKEN_NOT_NULL,globalMessageSource.get(TOKEN_NOT_NULL,loggedInUserId));
        }
        log.info(GATEWAY+gatewayApi);
        String userDetails = webClientWrapper.webclientRequest(webClient,gatewayApi + ACCOUNTS_URL + FILTER_COLUMN_URL + loggedInUserId+MANDATORY_FIELDS,GET,null);
        if ( StringUtils.isEmpty(userDetails) || userDetails.isBlank())
        {
            throw new UserDetailsIdNotFoundException(USER_DETAILS_NOT_FOUND_EXCEPTION,globalMessageSource.get(USER_DETAILS_NOT_FOUND_EXCEPTION,userDetails));
        }
        response = this.objectMapper.readValue(userDetails, new TypeReference<>()
        {
        });
        log.info(RESPONSE+ response);
        List<Map<String, Object>> data = (List<Map<String, Object>>) response.get(DATA);
        log.info(DATA+data);
        if (!data.isEmpty())
        {
            userDetailsResponse = this.objectMapper.convertValue(response.get(DATA), List.class);
            return userDetailsResponse;
        }
        throw new UserDetailsIdNotFoundException(LOGGED_IN_USER_ID_NOT_FOUND,globalMessageSource.get(LOGGED_IN_USER_ID_NOT_FOUND,loggedInUserId));
    }
}


