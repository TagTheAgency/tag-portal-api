package com.tagtheagency.portal.briefs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tagtheagency.portal.briefs.model.Client;
import com.tagtheagency.portal.briefs.model.ClientList;
import com.tagtheagency.portal.briefs.model.Project;
import com.tagtheagency.portal.briefs.model.ProjectList;
import com.tagtheagency.portal.briefs.model.UserAssignment;
import com.tagtheagency.portal.briefs.model.UserAssignmentList;

@Service
@PropertySource(value= {"classpath:google-oauth2.properties"})
public class HarvestService {

	@Value("${briefs.harvestId}")
	private String harvestId;
	
	@Value("${briefs.harvestKey}")
	private String harvestKey;
	
	@Autowired RestTemplate restTemplate;
	
	public List<Client> getClients() {
		
		ResponseEntity<ClientList> response =
	        restTemplate.exchange(
	        	"https://api.harvestapp.com/api/v2/clients",
	            HttpMethod.GET, 
	            getHarvestHeaders(), 
	            ClientList.class
	        );

		List<Client> rates = response.getBody().getClients();
		
		return rates;
	}
	
	public List<Project> getProjects(int clientId) {

		ResponseEntity<ProjectList> response =
	        restTemplate.exchange(
	        	"https://api.harvestapp.com/api/v2/projects?client_id="+clientId,
	            HttpMethod.GET, 
	            getHarvestHeaders(), 
	            ProjectList.class
	        );

		return response.getBody().getProjects();		
	}
	
	public List<Project> getActiveProjects() {

		ResponseEntity<ProjectList> response =
	        restTemplate.exchange(
	        	"https://api.harvestapp.com/api/v2/projects?is_active="+true,
	            HttpMethod.GET, 
	            getHarvestHeaders(), 
	            ProjectList.class
	        );

		return response.getBody().getProjects();		
	}

	private HttpEntity<String> getHarvestHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Harvest-Account-ID", harvestId);
		headers.set("Authorization", "Bearer "+harvestKey);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		return entity;
	}
	
	public List<UserAssignment> getUserAssignments(int project) {
		
		String url = "https://api.harvestapp.com/v2/projects/"+project+"/user_assignments";
		
		ResponseEntity<UserAssignmentList> response =
		        restTemplate.exchange(
		        	url,
		            HttpMethod.GET, 
		            getHarvestHeaders(), 
		            UserAssignmentList.class
		        );

			return response.getBody().getUserAssignments();		
		
	}
	
	private RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new LoggingRequestInterceptor());
		restTemplate.setInterceptors(interceptors);
		return restTemplate;
	}
	
}
