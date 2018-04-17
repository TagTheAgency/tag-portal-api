package com.tagtheagency.portal.pitch.service;

import java.util.List;

import com.tagtheagency.portal.model.Pitch;
import com.tagtheagency.portal.model.PitchImage;
import com.tagtheagency.portal.model.PitchPage;

public interface PitchManager {


	public Pitch createPitch(String title, String user);
	
	public Pitch getById(int id);

	public List<Pitch> getAllPitches();

	public void updatePitch(Pitch pitch);

	public PitchPage createPage(Pitch pitch);

	public PitchPage getPageById(int id);

	public void updatePitchPage(PitchPage page);

	public void createPage(PitchPage convertedPage);

	public void deletePage(PitchPage domainPage);

	public PitchImage createImage(PitchPage page, double x, double y, double w, double h, String filename);
	
	public void updateImage(PitchImage image);

	public void deleteImageFromPage(PitchPage actualPage, int image);
	
	
}
