package com.tagtheagency.portal.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Order(1)
public class SecurityWebAppInitialiser extends AbstractSecurityWebApplicationInitializer {

//    @Override
//    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
//        insertFilters(servletContext, new MultipartFilter());
//    }
}