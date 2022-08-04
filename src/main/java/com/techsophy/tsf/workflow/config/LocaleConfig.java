package com.techsophy.tsf.workflow.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.*;

@Configuration
public class LocaleConfig extends AcceptHeaderLocaleResolver implements WebMvcConfigurer
{
    @Override
    public Locale resolveLocale(HttpServletRequest request)
    {
        if (StringUtils.isEmpty(request.getHeader(ACCEPT_LANGUAGE)))
        {
            return Locale.getDefault();
        }
        else
        {
            return new Locale(request.getHeader(ACCEPT_LANGUAGE));
        }
    }

    @Bean
    public MessageSource messageSource()
    {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(BASENAME_ERROR_MESSAGES,BASENAME_MESSAGES);
        //refresh cache once per hour
        messageSource.setCacheMillis(CACHEMILLIS);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setUseCodeAsDefaultMessage(USEDEFAULTCODEMESSAGE);
        return messageSource;
    }
}
