package com.tagtheagency.portal.briefs.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Client {

	private long id;
	
	private String name;
	
	@JsonAlias("is_active")
	private boolean isActive;
	
	private String address;
	
	@JsonAlias("created_at")
	private Date createdAt;
	
	@JsonAlias("updated_at")
	private Date updatedAt;
	
	private String currency;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
}
