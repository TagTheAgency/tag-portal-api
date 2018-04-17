package com.tagtheagency.portal.pitch.dto;

import java.util.Date;
import java.util.List;

public class PitchDTO {

	private int id;
	private String title;
	
	private Date createdDate;
	private Date modifiedDate;
	private String createdUser;
	private String modifiedUser;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public List<PitchPageDTO> getPages() {
		return pages;
	}

	public void setPages(List<PitchPageDTO> pages) {
		this.pages = pages;
	}

	private List<PitchPageDTO> pages;
	
	
//	public static PitchDTO convert(Pitch pitch) {
//		PitchDTO dto = new PitchDTO();
//		dto.setTitle(pitch.getTitle());
//		dto.setId(pitch.getId());
//		dto.setCreatedDate(pitch.getCreatedDate());
//		dto.setModifiedDate(pitch.getModifiedDate());
//		dto.setCreatedUser(pitch.getCreatedUser());
//		dto.setModifiedUser(pitch.getModifiedUser());
//
//		dto.setPages(new ArrayList<PitchPageDTO>());
//		pitch.getPages().forEach(p -> {
//			PitchPageDTO ppDto = new PitchPageDTO();
//			ppDto.setId(p.getId());
//			ppDto.setOrder(p.getOrder());
//			ppDto.setTitle(p.getTitle());
//			ppDto.setClazz(p.getClass().getSimpleName());
//			if (p instanceof TextAndImagePage) {
//				ppDto.setText(((TextAndImagePage) p).getText());
//			}
//			dto.getPages().add(ppDto);
//		});
//		
//		return dto;
//	}
}
