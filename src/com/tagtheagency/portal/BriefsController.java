package com.tagtheagency.portal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tagtheagency.portal.briefs.model.Client;
import com.tagtheagency.portal.briefs.service.HarvestService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/briefs/")
public class BriefsController {
	
	@Autowired private HarvestService harvestService;
	
	@GetMapping("clients")
	public List<Client> getClients() {
		return harvestService.getClients();
	}
}