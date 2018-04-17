package com.tagtheagency.portal.pitch;

import java.awt.Color;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class PitchBackground extends PdfPageEventHelper {
	
	private String pitchName;
	
	public PitchBackground(String pitchName) {

		this.pitchName = pitchName;
	}
	
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        int pagenumber = writer.getPageNumber();
        //do background
        if (pagenumber <= 2) {
	        PdfContentByte canvas = writer.getDirectContentUnder();
	        Rectangle rect = document.getPageSize();
	        canvas.setColorFill(new Color(0x23, 0x1F, 0x20));
	        canvas.rectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(), rect.getHeight());
	        canvas.fill();
        }
        
        if (pagenumber == 2) {
        	return;
        }
        
        ColumnText column = new ColumnText(writer.getDirectContent());
		Font normal = new Font(FontUtils.getHarrietNormal(), 9, Font.NORMAL, pagenumber <= 2 ? Color.WHITE : Color.BLACK);
		Font bold = new Font(FontUtils.getHarrietBold(), 9, Font.NORMAL, pagenumber <= 2 ? Color.WHITE : Color.BLACK);
		Chunk client = new Chunk(pitchName, bold);
		Chunk pitch = new Chunk("   | Pitch", normal);
		Phrase title = new Phrase();
		title.add(client);
		title.add(pitch);
		column.setText(title);
		column.setSimpleColumn(40, 535, 500, 560);
    	try {
			column.go();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    }
}