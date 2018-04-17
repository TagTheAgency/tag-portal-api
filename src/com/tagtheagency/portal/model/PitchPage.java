package com.tagtheagency.portal.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.tagtheagency.portal.pitch.service.StorageService;


@Entity
@Table(name="pitch_page")
@Inheritance(strategy=InheritanceType.JOINED)
public class PitchPage {


	private int id;
	private Pitch pitch;
	private String title;
	private String text;
	private String implementation;
	private List<PitchImage> images;
	
	private int order;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="page_id")
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


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pitch_id", nullable = false)
	public Pitch getPitch() {
		return pitch;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setPitch(Pitch pitch) {
		this.pitch = pitch;
	}

	@Column(name="page_order")
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	@Lob
	@Column
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="page")
	public List<PitchImage> getImages() {
		return images;
	}
	
	public void setImages(List<PitchImage> images) {
		this.images = images;
	}

	@Column
	public String getImplementation() {
		return implementation;
	}

	public void setImplementation(String implementation) {
		this.implementation = implementation;
	}
	
//	public abstract void printPage(PdfContentByte cb, StorageService storage) throws DocumentException;
	
}

