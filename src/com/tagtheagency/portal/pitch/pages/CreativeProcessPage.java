package com.tagtheagency.portal.pitch.pages;

import java.io.IOException;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.tagtheagency.portal.pitch.pages.PageCreator;

public class CreativeProcessPage extends PageCreator {

	@Override
	public void setTitle(String title) {
		//ignored, title is always 'Creative process';
		title = "Creative Process";
	}
	
	@Override
	public void printPage(PdfContentByte cb) throws DocumentException {
		Image image;
		try {
			image = Image.getInstance(getClass().getResource("creative_process.png"));
			image.setAbsolutePosition(150, 50);
			image.scaleAbsolute(642, 370);
	        cb.addImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
