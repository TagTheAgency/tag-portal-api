package com.tagtheagency.portal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="pitch")
public class Pitch {

	public enum Status {
		DEVELOPMENT, WITH_CLIENT, ACCEPTED, DECLINED
	}
	
	private int id;
	private String title;
	
	private Date createdDate;
	private Date modifiedDate;
	
	private String createdUser;
	private String modifiedUser;
	
	private Status status;
	
	private List<PitchPage> pages;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id")
	public int getId() {
		return id;
	}

	@Column
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="create_date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name="modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name="create_user")
	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	@Column(name="modified_user")
	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="pitch")
	public List<PitchPage> getPages() {
		if (pages == null) {
			pages = new ArrayList<>();
		}
		return pages;
	}
	
	public void setPages(List<PitchPage> pages) {
		this.pages = pages;
	}	
	
	@Enumerated(EnumType.STRING)
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
}


