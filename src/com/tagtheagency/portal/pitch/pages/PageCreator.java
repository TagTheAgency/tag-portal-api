package com.tagtheagency.portal.pitch.pages;

import java.awt.Color;
import java.util.List;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.tagtheagency.portal.model.Pitch;
import com.tagtheagency.portal.model.PitchImage;
import com.tagtheagency.portal.model.PitchPage;
import com.tagtheagency.portal.pitch.FontUtils;
import com.tagtheagency.portal.pitch.service.StorageService;

public abstract class PageCreator {

	protected StorageService storage;
	protected Pitch pitch;
	protected String title;
	protected String text;
	protected List<PitchImage> images;
	protected PitchPage page;
	
	public void setStorage(StorageService svc) {
		this.storage = svc;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setImages(List<PitchImage> images) {
		this.images = images;
	}
	
	public void setPitch(Pitch pitch) {
		this.pitch = pitch;
	}
	
	public void setPitchPage(PitchPage page) {
		this.page = page;
	};
	
	public void createPage(PdfContentByte cb) throws DocumentException {
		printTitle(cb);
		printPage(cb);
		ColumnText column = new ColumnText(cb);
		Font large = new Font(FontUtils.getHarrietBold(), 16, Font.NORMAL, Color.BLACK);
		column.setText(new Phrase(title, large));
		column.setSimpleColumn(150, 450, 800, 490);

	    try {
			column.go();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	protected void printTitle(PdfContentByte cb) {
		ColumnText column = new ColumnText(cb);
		Font large = new Font(FontUtils.getHarrietBold(), 16, Font.NORMAL, Color.BLACK);
		column.setText(new Phrase(title, large));
		column.setSimpleColumn(150, 450, 800, 490);

    	try {
			column.go();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}

	public abstract void printPage(PdfContentByte cb) throws DocumentException;


	
	
}
