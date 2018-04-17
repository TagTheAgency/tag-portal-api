package com.tagtheagency.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tagtheagency.portal.pitch.pages.TextAndImagePage;

@Entity
@Table(name="pitch_image")
public class PitchImage {

	private int id;
	
	private PitchPage page;
	
	private String filename;
	private double x;
	private double y;
	private double w;
	private double h;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id")
	public int getId() {
		return id;
	}

	@Column
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	@Column
	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Column
	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	@Column
	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "page_id", nullable = false)
	public PitchPage getPage() {
		return page;
	}

	public void setPage(PitchPage page) {
		this.page = page;
	}

	
	
	
}
