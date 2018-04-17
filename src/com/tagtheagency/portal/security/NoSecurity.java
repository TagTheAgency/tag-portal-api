//package com.tagtheagency.portal.security;
//
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.oauth2.client.OAuth2RestOperations;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
//
//@EnableWebSecurity
//@Profile("dev")
//public class NoSecurity extends WebSecurityConfigurerAdapter{
//
//
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//        web.debug(true);
//		web.ignoring().antMatchers(
//				"/", "/login", "/resources/**", "/errors");
//	}
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().anyRequest().permitAll();
//	}
//		
//}
