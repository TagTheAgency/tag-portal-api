package com.tagtheagency.portal.briefs.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

public class UserAssignmentList {

	@JsonAlias("user_assignments")
	private List<UserAssignment> userAssignments;

	public List<UserAssignment> getUserAssignments() {
		return userAssignments;
	}

	public void setUserAssignments(List<UserAssignment> userAssignments) {
		this.userAssignments = userAssignments;
	}
}
