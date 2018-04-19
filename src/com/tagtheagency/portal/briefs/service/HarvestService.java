package com.tagtheagency.portal.briefs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
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

@Service
@PropertySource(value= {"classpath:google-oauth2.properties"})
public class HarvestService {

	@Value("${briefs.harvestId}")
	private String harvestId;
	
	@Value("${briefs.harvestKey}")
	private String harvestKey;
	
	@Autowired RestTemplate restTemplate;
	
	public List<Client> getClients() {
		
		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new LoggingRequestInterceptor());
		restTemplate.setInterceptors(interceptors);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Harvest-Account-ID", harvestId);
		headers.set("Authorization", "Bearer "+harvestKey);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<ClientList> response =
	        restTemplate.exchange(
	        	"https://api.harvestapp.com/api/v2/clients",
	            HttpMethod.GET, 
	            entity, 
	            new ParameterizedTypeReference<ClientList>() {}
	        );

		List<Client> rates = response.getBody().getClients();
		
		return rates;
	}

}
