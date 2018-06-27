package com.tagtheagency.portal;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.youtube.model.Video;
import com.tagtheagency.portal.briefs.service.GoogleService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/trends/")
public class TrendsController {
	
	@Autowired private GoogleService googleService;
	
	
	
	@GetMapping("videos")
	public List<Video> getYoutubeTrends() throws IOException {
		return googleService.getVideoTrends("NZ");
	}
	
	@GetMapping("videos/{country}")
	public List<Video> getYoutubeTrends(@PathVariable String country) throws IOException {
		return googleService.getVideoTrends(country);
	}
}