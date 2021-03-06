package com.tagtheagency.portal.briefs.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Project {

	private long id;
	private Client client;
	private String name;
	private String code;
	@JsonAlias("is_active")
	private boolean active;
	
	@JsonAlias("is_billable")
	private boolean billable;
	
	@JsonAlias("is_fixed_fee")
	private boolean fixedFee;
	
	private double budget;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	
	public boolean isFixedFee() {
		return fixedFee;
	}
	public void setFixedFee(boolean fixedFee) {
		this.fixedFee = fixedFee;
	}

	public double getBudget() {
		return budget;
	}
	public void setBudget(double budget) {
		this.budget = budget;
	}
}
