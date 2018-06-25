package com.tagtheagency.portal.briefs.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

@Service
@PropertySource(value= {"classpath:google-oauth2.properties"})
public class GoogleService {

	@Value("${google.apikey}")
	private String apiKey;
	
    /**
     * Define a global instance of the HTTP transport.
     */
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /**
     * Define a global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
		
	
	@Autowired RestTemplate restTemplate;
	
	public List<Video> getVideoTrends() throws IOException {
		
		YouTube youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName("tag-portal").build();
		
		/*Map<String, String> parameters = new HashMap<>();
        parameters.put("part", "snippet,contentDetails,statistics");
        parameters.put("regionCode", "NZ");
        parameters.put("maxResults", "10");
        parameters.put("chart", "mostPopular");
        parameters.put("key", apiKey);
*/
        YouTube.Videos.List videosList = youtube.videos().list("snippet,contentDetails,statistics");
        videosList.setChart("mostPopular");
        videosList.setRegionCode("NZ");
        videosList.setMaxResults(10l);
        videosList.setKey(apiKey);
        
        List<Video> response = videosList.execute().getItems();
        
        return response;
/*		String queryString = "?part=snippet,contentDetails,statistics&chart=mostPopular&regionCode=NZ&maxResults=10&key="+apiKey;
		
		ResponseEntity<VideoListResponse> response =
	        restTemplate.exchange(
	        	"https://www.googleapis.com/youtube/v3/videos"+queryString,
	            HttpMethod.GET, 
	            null,
	            VideoListResponse.class
	        );

		return response.getBody();
*/	}
	
}
