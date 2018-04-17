package com.tagtheagency.portal.pitch.pages;

import java.awt.Color;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.tagtheagency.portal.model.PitchPage;
import com.tagtheagency.portal.pitch.FontUtils;
import com.tagtheagency.portal.pitch.service.StorageService;

public class RateCardPage extends PageCreator {

	private final Color HEADING_BACKGROUND = new Color(0xE5, 0xE7, 0xE9);
	private final Color TITLE_BACKGROUND = new Color(0x6C, 0x6C, 0x70);
	
	private final Color BODY_COLOR =  new Color(0x9F, 0xA2, 0xA9);
	
	public RateCardPage() {
		this.title = "Rate Card";
	}
	
	@Override
	public void setTitle(String title) {
		//can't override title
	}
	
	public void printPage(PdfContentByte cb) throws DocumentException {
		PdfPTable table = createTableWithTitleCols();
        
        //empty row
        table.addCell("");
        table.addCell("");
        
        //strategy
        table.addCell(createHeadingCell("Strategy"));

        table.addCell(createDescriptionCell("Social media strategy", true));
        table.addCell(createCostCell("$220.00 p/h", true));
        table.addCell(createDescriptionCell("Social media tactical planning", true));
        table.addCell(createCostCell("$220.00 p/h", true));
        table.addCell(createDescriptionCell("Creative", false));
        table.addCell(createCostCell("$220.00 p/h", false));

        //Social media content
        table.addCell(createHeadingCell("Social media content"));
        
        table.addCell(createDescriptionCell("Research", true));
        table.addCell(createCostCell("$80.00 p/h", true));
        table.addCell(createDescriptionCell("Social media support", true));
        table.addCell(createCostCell("$140.00 p/h", true));
        table.addCell(createDescriptionCell("Page management 24/7 charged after business hours ", true));
        table.addCell(createCostCell("$180.00 p/h", true));
        table.addCell(createDescriptionCell("Page management business hours ", true));
        table.addCell(createCostCell("$120.00 p/h", true));
        table.addCell(createDescriptionCell("Content development", true));
        table.addCell(createCostCell("$140.00 p/h", true));
        table.addCell(createDescriptionCell("Content development junior", true));
        table.addCell(createCostCell("$80.00 p/h", true));
        table.addCell(createDescriptionCell("Campaign management", false));
        table.addCell(createCostCell("$120.00 p/h", false));
        
        table.addCell(createHeadingCell("Design"));
        table.addCell(createDescriptionCell("Design", false));
        table.addCell(createCostCell("$120.00 p/h", false));
        
        table.addCell(createHeadingCell("Development"));
        table.addCell(createDescriptionCell("Facebook application development", true));
        table.addCell(createCostCell("$140.00 p/h", true));
        table.addCell(createDescriptionCell("Web development", false));
        table.addCell(createCostCell("$120.00 p/h", false));
        
        table.writeSelectedRows(0, -1, 150, 595-160, cb);

        
        table = createTableWithTitleCols();
        
        //empty row
        table.addCell("");
        table.addCell("");
        
        //video        
        table.addCell(createHeadingCell("Video"));
        table.addCell(createDescriptionCell("Direction (senior)", true));
        table.addCell(createCostCell("$140.00 p/h", true));
        table.addCell(createDescriptionCell("Direction", true));
        table.addCell(createCostCell("$120.00 p/h", true));
        table.addCell(createDescriptionCell("Planning", true));
        table.addCell(createCostCell("$120.00 p/h", true));
        table.addCell(createDescriptionCell("Script", true));
        table.addCell(createCostCell("$120.00 p/h", true));
        table.addCell(createDescriptionCell("Filming", true));
        table.addCell(createCostCell("$120.00 p/h", true));
        table.addCell(createDescriptionCell("Post production", true));
        table.addCell(createCostCell("$120.00 p/h", true));
        table.addCell(createDescriptionCell("Runner", false));
        table.addCell(createCostCell("$80.00 p/h", false));

        table.addCell(createHeadingCell("Photography"));
        table.addCell(createDescriptionCell("Photography (senior photographer)", true));
        table.addCell(createCostCell("$250.00 p/h", true));
        table.addCell(createDescriptionCell("Photography (junior photographer)", false));
        table.addCell(createCostCell("$80.00 p/h", false));
        
        table.addCell(createHeadingCell("Reporting"));
        table.addCell(createDescriptionCell("Monthly performance reports", false));
        table.addCell(createCostCell("$200.00 p/report", false));

        table.addCell(createHeadingCell("Campaign Reporting"));
        table.addCell(createDescriptionCell("Research data collection", true));
        table.addCell(createCostCell("$80.00 p/h", true));
        table.addCell(createDescriptionCell("Research data interpretation", false));
        table.addCell(createCostCell("$140.00 p/h", false));

        table.addCell(createHeadingCell("Channel monitoring"));
        table.addCell(createDescriptionCell("Monitor set up", false));
        table.addCell(createCostCell("$420", false));

        table.writeSelectedRows(0, -1, 490, 595-160, cb);
	}

	private PdfPTable createTableWithTitleCols() throws DocumentException {
		Color titleBackground = new Color(0x6C, 0x6C, 0x70);
		
		PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(new float[]{ 180, 100 });
        table.setLockedWidth(true);
        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        table.getDefaultCell().setFixedHeight(20);
        table.getDefaultCell().setPaddingLeft(10);

        // first row
        PdfPCell cell = new PdfPCell(new Phrase("Task", new Font(FontUtils.getHarrietNormal(), 9, Font.NORMAL, Color.WHITE)));
        cell.setFixedHeight(20);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setBackgroundColor(titleBackground);
        cell.setPaddingLeft(5);
        cell.setPaddingTop(5);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Cost", new Font(FontUtils.getHarrietNormal(), 9, Font.NORMAL, Color.WHITE)));
        cell.setFixedHeight(20);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setBackgroundColor(titleBackground);
        cell.setPaddingLeft(2);
        cell.setPaddingTop(5);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        
        return table;
	}

	private PdfPCell createHeadingCell(String heading) {
		PdfPCell cell = new PdfPCell(new Phrase(heading, new Font(FontUtils.getFutura(), 9, Font.NORMAL, Color.BLACK)));
        cell.setColspan(2);
		cell.setBorder(Rectangle.NO_BORDER);
        cell.setBackgroundColor(HEADING_BACKGROUND);
        cell.setPaddingLeft(5);
        cell.setPaddingTop(3);
        cell.setPaddingBottom(3);
        return cell;
	}
	
	private PdfPCell createDescriptionCell(String description, boolean border) {
		PdfPCell cell = new PdfPCell(new Phrase(description, new Font(FontUtils.getFutura(), 9, Font.NORMAL, BODY_COLOR)));
		if (border) {
			cell.setBorder(Rectangle.BOTTOM);
		} else {
			cell.setBorder(Rectangle.NO_BORDER);
		}
        cell.setBorderWidth(1.0f);
        cell.setBorderColor(BODY_COLOR);
        cell.setBackgroundColor(Color.WHITE);
        cell.setPaddingLeft(5);
        cell.setPaddingTop(3);
        cell.setPaddingBottom(3);
        return cell;
	}
	
	private PdfPCell createCostCell(String cost, boolean border) {
		PdfPCell cell = new PdfPCell(new Phrase(cost, new Font(FontUtils.getFutura(), 9, Font.NORMAL, BODY_COLOR)));
		if (border) {
			cell.setBorder(Rectangle.BOTTOM);
		} else {
			cell.setBorder(Rectangle.NO_BORDER);
		}
        cell.setBorderWidth(1.0f);
        cell.setBorderColor(BODY_COLOR);
        cell.setBackgroundColor(Color.WHITE);
        cell.setPaddingLeft(2);
        cell.setPaddingTop(3);
        cell.setPaddingBottom(3);
        return cell;
	}

}
