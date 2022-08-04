package com.techsophy.tsf.workflow.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.workflow.config.GlobalMessageSource;
import com.techsophy.tsf.workflow.dto.PaginationResponsePayload;
import com.techsophy.tsf.workflow.exception.InvalidInputException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import java.util.*;
import static com.techsophy.tsf.workflow.constants.ErrorConstants.*;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.*;

@Component
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class TokenUtils
{
    @Value(DEFAULT_PAGE_LIMIT)
    private Integer defaultPageLimit;
    private final ObjectMapper objectMapper;
    private GlobalMessageSource globalMessageSource;

    public String getIssuerFromToken(String idToken)
    {
        String tenantName = EMPTY_STRING;
        final Base64.Decoder decoder = Base64.getDecoder();
        if (idToken.startsWith(BEARER))
        {
            idToken=idToken.substring(SEVEN);
        }
        Map<String, Object> tokenBody = new HashMap<>();
        List<String> tokenizer = Arrays.asList(idToken.split(REGEX_SPLIT));
        for(String token:tokenizer)
        {
            if(token.equals(tokenizer.get(ONE)))
            {
                tokenBody=string2JSONMap(new String(decoder.decode(token)));
            }
        }
        if( tokenBody == null )
        {
            throw new InvalidInputException(INVALID_TOKEN,globalMessageSource.get(INVALID_TOKEN));
        }
        if(tokenBody.containsKey(ISS))
        {
            List<String> elements= Arrays.asList(tokenBody.get(ISS).toString().split(URL_SEPERATOR));
            tenantName=elements.get(elements.size()-1);
        }
        return tenantName;
    }

    public String getTokenFromContext()
    {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null)
        {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken))
            {
                Object principal = authentication.getPrincipal();
                if (principal instanceof OAuth2User)
                {
                    return Optional.of(((OAuth2User) principal).getName()).orElseThrow(()->new SecurityException(AUTHENTICATION_FAILED));
                }
                else if (principal instanceof Jwt)
                {
                    Jwt jwt = (Jwt) principal;

                    return jwt.getTokenValue();
                }
                else
                {
                    throw new SecurityException(UNABLE_TO_GET_TOKEN);
                }
            }
            else
            {
                throw new SecurityException(UNABLE_TO_GET_TOKEN);
            }
        }
        else
        {
            throw new SecurityException(UNABLE_TO_GET_TOKEN);
        }
    }

    public String getLoggedInUserId()
    {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null)
        {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken))
            {
                Object principal = authentication.getPrincipal();
                if (principal instanceof OAuth2User)
                {
                    return Optional.of(((OAuth2User) principal).getName()).orElseThrow(()->new SecurityException(AUTHENTICATION_FAILED));
                }
                else if (principal instanceof Jwt)
                {
                    Jwt jwt = (Jwt) principal;
                    return jwt.getClaim(PREFERED_USERNAME);
                }
                else
                {
                    throw new SecurityException(AUTHENTICATION_FAILED);
                }
            }
            else
            {
                throw new SecurityException(AUTHENTICATION_FAILED);
            }
        }
        else
        {
            throw new SecurityException(AUTHENTICATION_FAILED);
        }
    }

    @SneakyThrows
    public Map<String, Object> string2JSONMap(String json)
    {
        ObjectMapper objecMapper = new ObjectMapper();
        // convert JSON string to Map
        return objecMapper.readValue(json, new TypeReference<>(){});
    }

    public PageRequest getPageRequest(Integer page, Integer pageSize, String[] sortByArray)
    {
        if (page != null && page > 0)
        {
            if (pageSize == null || pageSize <= 0)
            {
                pageSize = this.defaultPageLimit;
            }
            Integer pageNo = page - 1;
            return PageRequest.of(pageNo, pageSize, getSortBy(sortByArray));
        }
        else
        {
            throw new InvalidInputException(INVALID_PAGE_REQUEST,globalMessageSource.get(INVALID_PAGE_REQUEST));
        }
    }
    public <T> PaginationResponsePayload getPaginationResponsePayload(Page<T> page, List<Map<String,Object>> content)
    {
        PaginationResponsePayload paginationResponsePayload = new PaginationResponsePayload();
        paginationResponsePayload.setTotalPages(page.getTotalPages());
        paginationResponsePayload.setTotalElements(page.getTotalElements());
        paginationResponsePayload.setPage(page.getNumber() + 1);
        paginationResponsePayload.setSize(page.getSize());
        paginationResponsePayload.setNumberOfElements(page.getNumberOfElements());
        paginationResponsePayload.setContent((content.isEmpty())?this.objectMapper.convertValue(page.getContent(), new TypeReference<List<Map<String,Object>>>() {
        }):content);
        return paginationResponsePayload;
    }
    public Sort getSortBy(String[] sortByArray)
    {
        List<String> sortByList = new ArrayList<>();
    if (sortByArray != null)
    {
        sortByList = Arrays.asList(sortByArray);
    }

        Sort sort = Sort.unsorted();
        if (sortByList.isEmpty())
        {
            sort = Sort.by(CREATED_ON).descending();
        }
        else
        {
            for (String item : sortByList)
            {
                String[] itemArray = item.split(COLON);

                if (org.springframework.util.StringUtils.hasText(itemArray[0]))
                {
                    Sort itemSort = Sort.by(itemArray[0]);

                    if (itemArray.length > 1 && org.springframework.util.StringUtils.hasText(itemArray[1]) &&
                            itemArray[1].equalsIgnoreCase(DESCENDING))
                    {
                        itemSort = itemSort.descending();
                    }
                    sort = sort.and(itemSort);
                }
            }
            sort =sort.toString().contains(CREATED_ON_ASC)?sort:sort.and(Sort.by(CREATED_ON).descending());

            }
        return sort;
    }
}
