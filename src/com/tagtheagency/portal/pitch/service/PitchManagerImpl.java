package com.tagtheagency.portal.pitch.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagtheagency.portal.model.Pitch;
import com.tagtheagency.portal.model.PitchImage;
import com.tagtheagency.portal.model.PitchPage;
import com.tagtheagency.portal.pitch.pages.TextAndImagePage;
import com.tagtheagency.portal.pitch.persistence.PitchDAO;
import com.tagtheagency.portal.pitch.persistence.PitchImageDAO;
import com.tagtheagency.portal.pitch.persistence.PitchPageDAO;

@Service
public class PitchManagerImpl implements PitchManager {

	@Autowired PitchDAO pitchDao;
	@Autowired PitchPageDAO pitchPageDao;
	@Autowired PitchImageDAO pitchImageDao;

	@Override
	public Pitch createPitch(String title, String user) {
		Pitch pitch = new Pitch();
		pitch.setTitle(title);
		pitch.setCreatedUser(user);
		pitch.setCreatedDate(new Date());
		pitch.setModifiedDate(new Date());
		pitchDao.save(pitch);
		
		PitchPage firstPage = new PitchPage();
		firstPage.setOrder(1);
		firstPage.setPitch(pitch);
		firstPage.setImplementation(TextAndImagePage.class.getCanonicalName());
		pitch.getPages().add(firstPage);

		pitchPageDao.save(firstPage);
		if (pitch.getPages().isEmpty()) {
			pitch.getPages().add(firstPage);
		}
		return pitch;
	}
	
	@Override
	public Pitch getById(int id) {
		Pitch found = pitchDao.findById(id).orElse(null);
		if (found == null) {
			return null;
		}
		List<PitchPage> pages = pitchPageDao.findByPitch(found);
		pages.sort(Comparator.comparing(PitchPage::getOrder));
		found.setPages(pages);
		return found;
	}
	
	@Override
	public PitchPage getPageById(int id) {
		return pitchPageDao.findById(id).orElse(null);
	}
	
	@Override
	public List<Pitch> getAllPitches() {
		List<Pitch> target = new ArrayList<>();
		pitchDao.findAll().forEach(target::add);
		return target;
	}
	
	@Override
	public void updatePitch(Pitch pitch) {
		pitchDao.save(pitch);
		
	}
	
	@Override
	public void updatePitchPage(PitchPage page) {
		pitchPageDao.save(page);
		
	}
	
	@Override
	public PitchPage createPage(Pitch pitch) {
		PitchPage page = new PitchPage();
		page.setImplementation(TextAndImagePage.class.getCanonicalName());
		page.setPitch(pitch);
		int maxOrder = pitch.getPages().stream().map(PitchPage::getOrder).max(Comparator.naturalOrder()).get();
		page.setOrder(maxOrder+1);
		pitchPageDao.save(page);
		return page;
	}
	
	@Override
	public void createPage(PitchPage convertedPage) {
		pitchPageDao.save(convertedPage);
	}
	
	@Override
	public void deletePage(PitchPage domainPage) {
		pitchPageDao.delete(domainPage);
		
	}
	
	@Override
	public PitchImage createImage(PitchPage page, double x, double y, double w, double h, String filename) {

		PitchImage image = new PitchImage();
		image.setPage(page);

		image.setX(x);
		image.setY(y);
		image.setH(h);
		image.setW(w);
		image.setFilename(filename);

		pitchImageDao.save(image);
		return image;
	}

	@Override
	public void updateImage(PitchImage image) {
		pitchImageDao.save(image);
		
	}
	
	@Override
	public void deleteImageFromPage(PitchPage actualPage, int image) {
		Optional<PitchImage> pitchImage = pitchImageDao.findById(image);
		if (pitchImage.isPresent() && pitchImage.get().getPage().getId() == actualPage.getId()) {
			pitchImageDao.deleteById(image);
		}
	}
}
