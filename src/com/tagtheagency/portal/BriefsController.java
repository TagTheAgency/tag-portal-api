package com.tagtheagency.portal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tagtheagency.portal.briefs.model.Client;
import com.tagtheagency.portal.briefs.model.Project;
import com.tagtheagency.portal.briefs.model.TaskAssignment;
import com.tagtheagency.portal.briefs.model.UserAssignment;
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
	
	@GetMapping("clients/{client}/projects")
	public List<Project> getProjects(@PathVariable int client) {
		return harvestService.getProjects(client);
	}
	
	@GetMapping("projects")
	public List<Project> getProjects() {
		return harvestService.getActiveProjects();
	}
	
	@GetMapping("project/{project}/users")
	public List<UserAssignment> getUsers(@PathVariable int project) {
		return harvestService.getUserAssignments(project);
	}
	
	@GetMapping("project/{project}/tasks")
	public List<TaskAssignment> getTasks(@PathVariable int project) {
		return harvestService.getTasks(project);
	}
}