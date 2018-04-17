package com.tagtheagency.portal.pitch.service;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPTableEvent;
import com.lowagie.text.pdf.PdfWriter;
import com.tagtheagency.portal.model.Pitch;
import com.tagtheagency.portal.model.PitchPage;
import com.tagtheagency.portal.pitch.FontUtils;
import com.tagtheagency.portal.pitch.PitchBackground;
import com.tagtheagency.portal.pitch.pages.PageCreator;

@Service
public class PitchCreatorImpl implements PitchCreator {

	@Autowired StorageService storage;
	
	private BaseFont harrietBaseFont;
	private BaseFont harrietBoldBaseFont;
	
	public PitchCreatorImpl() {
		harrietBaseFont = FontUtils.getHarrietNormal();
		harrietBoldBaseFont = FontUtils.getHarrietBold();
	}
	
	@Override
	public void createPitchPDF(OutputStream out, Pitch pitch) throws DocumentException, IOException {

		Document document = new Document(PageSize.A4.rotate());
		PdfWriter writer = PdfWriter.getInstance(document, out);
		PitchBackground event = new PitchBackground(pitch.getTitle());
		writer.setPageEvent(event);

		document.open();      

//		width = 842;
//		height = 595;
		
		//Put on the header
		PdfContentByte cb = writer.getDirectContent();
		writeFrontCover(cb);
		document.newPage();
    	writeInsideFrontCover(cb);

    	int pageNum = 0;
    	for (PitchPage page : pitch.getPages()) {
    		
    		PageCreator pageCreator;
			try {
				pageCreator = (PageCreator) Class.forName(page.getImplementation()).newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
				continue;
			}
			pageCreator.setPitchPage(page);
			pageCreator.setPitch(pitch);
    		pageCreator.setImages(page.getImages());
    		pageCreator.setTitle(page.getTitle());
    		pageCreator.setText(page.getText());
    		pageCreator.setStorage(storage);
    		
    		document.newPage();
    		writeContents(cb, pageNum, pitch.getPages());
    		
    		pageCreator.createPage(cb);

    		writePageNum(cb, pageNum++ + 3);
    	}
    	


    document.close();
		
	}
	
	private void writePageNum(PdfContentByte cb, int pageNum) {
		ColumnText column = new ColumnText(cb);
		//790 x 30
		
		column.addElement(new Paragraph(pageNum+".", new Font(harrietBaseFont, 9, Font.NORMAL, FontUtils.getGreyText())));
		column.setSimpleColumn(790, 40, 810, 20);
    	try {
			column.go();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private void writeTitle(PdfContentByte cb, String title) throws DocumentException {
		ColumnText column = new ColumnText(cb);
		Font large = new Font(harrietBoldBaseFont, 16, Font.NORMAL, Color.BLACK);
		column.setText(new Phrase(title, large));
		column.setSimpleColumn(150, 450, 800, 490);

    	try {
			column.go();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	private void writeContents(PdfContentByte cb, int pageNum, List<PitchPage> pages) throws DocumentException {
		// TODO Auto-generated method stub
		Font tocFont = new Font(FontUtils.getHarrietNormal(), 9, Font.NORMAL, FontUtils.getGreyText());
		Font activeFont = new Font(FontUtils.getHarrietNormal(), 9, Font.NORMAL, Color.BLACK);
		PdfPTable table = new PdfPTable(1);
		table.setTotalWidth(new float[]{ 110});
		table.setTableEvent(new DottedCells());
		
		table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		table.getDefaultCell().setPaddingLeft(40);
		table.getDefaultCell().setPaddingTop(7);
		table.getDefaultCell().setPaddingBottom(7);
		
		int currentPage = 0;
		for (PitchPage page : pages) {
			if (currentPage == pageNum) {
				table.addCell(new Phrase(page.getTitle(), activeFont));
			} else {
				table.addCell(new Phrase(page.getTitle(), tocFont));
			}
			currentPage++;
		}

        table.writeSelectedRows(0, -1, 0, 440, cb);
        
	}

	private void writeInsideFrontCover(PdfContentByte cb) {
		int fontSize = 42;
		ColumnText column = new ColumnText(cb);
		Font large = new Font(harrietBoldBaseFont, fontSize, Font.NORMAL, Color.WHITE);
		Paragraph paragraph = new Paragraph((int)(fontSize * 1.5));
		paragraph.add(new Phrase(60, "Digital storytellers", large));
		paragraph.add(Chunk.NEWLINE);
		paragraph.add(Chunk.NEWLINE);
		paragraph.add(Chunk.NEWLINE);
		paragraph.add(new Phrase(60, "for brands", large));
		paragraph.setLeading(60);
		column.setText(paragraph);
		column.setSimpleColumn(72, 400, 500, 512);
    	try {
			column.go();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}

	private void writeFrontCover(PdfContentByte cb) throws IOException, DocumentException {
	
		Image header = Image.getInstance(PitchBackground.class.getResource("cmyk-blue.png"));
		header.setAbsolutePosition(323, 265);
		header.scaleAbsolute(60, 60);
        cb.addImage(header);
        header = Image.getInstance(PitchBackground.class.getResource("cmyk-pink.png"));
		header.setAbsolutePosition(398, 265);
		header.scaleAbsolute(60, 60);
        cb.addImage(header);
        header = Image.getInstance(PitchBackground.class.getResource("cmyk-yellow.png"));
		header.setAbsolutePosition(474, 265);
		header.scaleAbsolute(60, 60);
        cb.addImage(header);

        header = Image.getInstance(PitchBackground.class.getResource("TAG_white_192x108.png"));
		header.setAbsolutePosition(710, 50);
		header.scaleAbsolute(90, 51);
        cb.addImage(header);

		
	}
	
    class DottedCells implements PdfPTableEvent {
    	 
        public void tableLayout(PdfPTable table, float[][] widths,
            float[] heights, int headerRows, int rowStart,
            PdfContentByte[] canvases) {
        	
        	
            PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
            canvas.setLineCap(PdfContentByte.LINE_CAP_ROUND);
            canvas.setLineDash(0.2f, 2, 2.5f);
            float llx = widths[0][0];
            float urx = widths[0][1];
            for (int i = 1; i < heights.length; i++) {
                canvas.moveTo(llx, heights[i]);
                canvas.lineTo(urx, heights[i]);
            }
            canvas.stroke();
        }
    }
 
}
