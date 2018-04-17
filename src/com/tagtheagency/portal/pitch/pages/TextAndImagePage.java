package com.tagtheagency.portal.pitch.pages;

import java.io.IOException;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.tagtheagency.portal.model.PitchImage;
import com.tagtheagency.portal.pitch.pdf.MarkdownPdfRenderer;

public class TextAndImagePage extends PageCreator {

	@Override
	public void printPage(PdfContentByte cb) throws DocumentException {
		if (text == null) {
			text = "";
		}
		Parser parser = Parser.builder().build();
		Node document = parser.parse(text);


		MarkdownPdfRenderer renderer = new MarkdownPdfRenderer(cb);
		renderer.render(document);
	
		renderer.getRenderer().render(new Rectangle[] {new Rectangle(150, 36, 360, 430), new Rectangle(375, 36, 580, 430), new Rectangle(595, 36, 810, 430)});

		for (PitchImage imageRef : images) {

			Image image;
			try {
				System.out.println("Pitch: "+pitch);
				System.out.println("page: "+page);
				System.out.println("storage: "+storage);

				image = Image.getInstance(storage.load(pitch.getId(), page.getId(), imageRef.getFilename()).toUri().toURL());
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			image.setAbsolutePosition((float)imageRef.getX(), (float)imageRef.getY());
			image.scaleAbsolute((float)imageRef.getW(), (float)imageRef.getH());
	        cb.addImage(image);
		}
	}
}
