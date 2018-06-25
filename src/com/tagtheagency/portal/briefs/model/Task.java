package com.tagtheagency.portal.briefs.model;

import java.util.Date;

public class Task {

	private int id;
	private String name;
	private boolean billableByDefault;
	private double defaultHourlyRate;
	private boolean isDefault;
	private boolean isActive;
	private Date createdAt;
	private Date updatedAt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isBillableByDefault() {
		return billableByDefault;
	}
	public void setBillableByDefault(boolean billableByDefault) {
		this.billableByDefault = billableByDefault;
	}
	public double getDefaultHourlyRate() {
		return defaultHourlyRate;
	}
	public void setDefaultHourlyRate(double defaultHourlyRate) {
		this.defaultHourlyRate = defaultHourlyRate;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	
}
