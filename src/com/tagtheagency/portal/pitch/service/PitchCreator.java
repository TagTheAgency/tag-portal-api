package com.tagtheagency.portal.pitch.service;

import java.io.IOException;
import java.io.OutputStream;

import com.lowagie.text.DocumentException;
import com.tagtheagency.portal.model.Pitch;

public interface PitchCreator {

	public void createPitchPDF(OutputStream out, Pitch pitch) throws DocumentException, IOException;
	
}
