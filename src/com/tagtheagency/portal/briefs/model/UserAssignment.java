package com.tagtheagency.portal.briefs.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * <table>
  <thead>
    <tr>
      <th>Attribute</th>
      <th>Type</th>
      <th>Description</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code class="highlighter-rouge">id</code></td>
      <td>integer</td>
      <td>Unique ID for the user assignment.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">project</code></td>
      <td>object</td>
      <td>An object containing the id, name, and code of the associated project.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">user</code></td>
      <td>object</td>
      <td>An object containing the id and name of the associated user.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">is_active</code></td>
      <td>boolean</td>
      <td>Whether the user assignment is active or archived.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">is_project_manager</code></td>
      <td>boolean</td>
      <td>Determines if the user has project manager permissions for the project.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">hourly_rate</code></td>
      <td>decimal</td>
      <td>Rate used when the project’s <code class="highlighter-rouge">bill_by</code> is <code class="highlighter-rouge">People</code>.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">budget</code></td>
      <td>decimal</td>
      <td>Budget used when the project’s <code class="highlighter-rouge">budget_by</code> is <code class="highlighter-rouge">person</code>.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">created_at</code></td>
      <td>datetime</td>
      <td>Date and time the user assignment was created.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">updated_at</code></td>
      <td>datetime</td>
      <td>Date and time the user assignment was last updated.</td>
    </tr>
  </tbody>
</table>
 *
 * @author Colin
 *
 */
public class UserAssignment {
	
	private int id;
	
	@JsonAlias("is_project_manager")
	private boolean projectManager;
	
	@JsonAlias("is_active")
	private boolean active;
	
	@JsonAlias("hourly_rate")
	private double hourlyRate;
	
	private double budget;
	
	@JsonAlias("created_at")
	private Date created;
	
	@JsonAlias("updated_at")
	private Date updated;
	
	private Project project;
	
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isProjectManager() {
		return projectManager;
	}

	public void setProjectManager(boolean projectManager) {
		this.projectManager = projectManager;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	
}
