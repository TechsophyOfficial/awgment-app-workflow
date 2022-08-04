package com.techsophy.tsf.workflow.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests(authorize -> authorize.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2->oauth2.authenticationManagerResolver(this.authenticationManagerResolver));
    }
}

