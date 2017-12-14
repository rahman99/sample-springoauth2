package com.sample.spring.sampleoauth2.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
public class SecurityAdapter extends ResourceServerConfigurerAdapter {
	
	@Value("${security.oauth2.client.resourceId}")
	private String clientResourceId;

	@Override
    public void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
        	.antMatchers("/api/dummy").access("hasAuthority('ROLE_USER_VIEW')")
        	.anyRequest().authenticated()
        	.and()
        	.requestMatchers().anyRequest();
    }
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(clientResourceId);
	}
}
