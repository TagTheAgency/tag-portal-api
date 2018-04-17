package com.tagtheagency.portal.pitch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tagtheagency.portal.pitch.pages.CreativeProcessPage;
import com.tagtheagency.portal.pitch.pages.RateCardPage;
import com.tagtheagency.portal.pitch.pages.TextAndImagePage;
import com.tagtheagency.portal.pitch.pages.TermsAndConditionsPage;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PitchPageType {

	STANDARD("Standard page (text and images)", TextAndImagePage.class),
	RATE_CARD("Rate card", RateCardPage.class),
	CREATIVE_PROCESS("Creative process", CreativeProcessPage.class),
	TS_AND_CS("Terms and Conditions", TermsAndConditionsPage.class);
	
	private String description;
	private Class<?> implementationClazz;

	private PitchPageType(String description, Class<?> implementationClazz) {
		this.description = description;
		this.implementationClazz = implementationClazz;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getImplementationClazz() {
		return implementationClazz.getCanonicalName();
	}
}
