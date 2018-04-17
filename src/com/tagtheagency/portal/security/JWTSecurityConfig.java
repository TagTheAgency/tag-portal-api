package com.tagtheagency.portal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.google.common.collect.ImmutableList;

@Configuration
@EnableWebSecurity
public class JWTSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        //webSecurity.ignoring().antMatchers(HttpMethod.GET, "/security/");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authenticationTokenFilterBean(), BasicAuthenticationFilter.class);
        http.authorizeRequests().antMatchers("/security/**").permitAll();
        http.csrf().disable();
        http.cors();
    }

	@Bean
	public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
	  JwtAuthenticationTokenFilter authenticationTokenFilter = new JwtAuthenticationTokenFilter();
	  AuthenticationManager authManager = authenticationManager();
	  
	  System.out.println("Auth manager: "+authManager);
	  authenticationTokenFilter.setAuthenticationManager(authManager);
	  authenticationTokenFilter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
	  return authenticationTokenFilter;
	}
	
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("token", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

//	@Configuration
//    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
//    	
////    	@Bean(value="jwtAuthenticationProvider")
//    	public AuthenticationProvider authenticationProvider() {
//    		System.out.println("Providing and auth provider");
//    		JwtAuthenticationProvider authenticationProvider = new JwtAuthenticationProvider();
////    		authenticationProvider.setUserDetailsService(userDetailsService);
//    		return authenticationProvider;
//    	}
//
//    	@Autowired
//    	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//    		if (auth == null) {
//    			System.out.println("Auth is null, returning without configuring the auth provider");
//    			return;
//    		}
//    		System.out.println("Setting auth provider on the auth ("+auth+")");
//    		auth.authenticationProvider(authenticationProvider());
//    		auth.eraseCredentials(false);
//    	}
//
//    	@Override
//    	protected void configure(HttpSecurity http) throws Exception {
//    		http
//    		//.antMatcher("/pitch/**")
//    		// Custom JWT based security filter
//    		.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
//    		//.authorizeRequests()
//    		//.anyRequest().authenticated().and()
//    		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    		
//    	}
//    	
//    	@Override
//        public void configure(WebSecurity web) throws Exception {
//    		web.ignoring().antMatchers(HttpMethod.POST, "/security");
//    	}
//
//        @Bean
//        public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
//            JwtAuthenticationTokenFilter authenticationTokenFilter = new JwtAuthenticationTokenFilter();
//            AuthenticationManager authManager = authenticationManager();
//            
//            System.out.println("Auth manager: "+authManager);
//            authenticationTokenFilter.setAuthenticationManager(authManager);
//            authenticationTokenFilter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
//            return authenticationTokenFilter;
//        }
//    }

//}