package com.tagtheagency.portal.pitch.dto;

import java.util.List;

public class PitchPageDTO {

	private int id;
	private String title;
	private int order;
	
	private String implementation;
	
	private String text;
	
	private List<PitchImageDTO> images;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getImplementation() {
		return implementation;
	}
	public void setImplementation(String implementation) {
		this.implementation = implementation;
	}
	public List<PitchImageDTO> getImages() {
		return images;
	}
	public void setImages(List<PitchImageDTO> images) {
		this.images = images;
	}
	
	
}
