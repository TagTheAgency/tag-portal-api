package com.tagtheagency.portal.briefs.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

public class TaskAssignmentList {

	@JsonAlias("task_assignments")
	private List<TaskAssignment> taskAssignments;

	public List<TaskAssignment> getTaskAssignments() {
		return taskAssignments;
	}
	
	public void setTaskAssignments(List<TaskAssignment> taskAssignments) {
		this.taskAssignments = taskAssignments;
	}
}
