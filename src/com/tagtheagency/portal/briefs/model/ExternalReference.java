package com.tagtheagency.portal.briefs.model;

public class ExternalReference {

	private int id;
	private int groupId;
	private String permalink;
	private String service;
	private String serviceIconUrl;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getPermalink() {
		return permalink;
	}
	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getServiceIconUrl() {
		return serviceIconUrl;
	}
	public void setServiceIconUrl(String serviceIconUrl) {
		this.serviceIconUrl = serviceIconUrl;
	}
	
	
}
