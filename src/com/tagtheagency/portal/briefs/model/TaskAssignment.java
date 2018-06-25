package com.tagtheagency.portal.briefs.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 *
 * @author Colin
 *
 */
public class TaskAssignment {
	
	private int id;
	
	private Project project; 
	
	private Task task;
	
	@JsonAlias("is_active")
	private boolean active;
	
	private boolean billable;
	
	@JsonAlias("hourly_rate")
	private double hourlyRate;
	
	private double budget;
	
	@JsonAlias("created_at")
	private Date created;
	
	@JsonAlias("updated_at")
	private Date updated;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isBillable() {
		return billable;
	}

	public void setBillable(boolean billable) {
		this.billable = billable;
	}

	public double getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	
}
