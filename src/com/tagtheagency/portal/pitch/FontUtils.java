package com.tagtheagency.portal.pitch;

import java.awt.Color;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;

public class FontUtils {

	private static BaseFont harrietNormal;
	private static BaseFont harrietBold;
	private static BaseFont futura;
	private static Color greyText;
	
	public static BaseFont getHarrietNormal() {
		if (harrietNormal == null) {
			Font font = FontFactory.getFont("/com/tagtheagency/portal/pitch/HarrietDisplayRegular.otf",
				    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, Color.BLACK);
			harrietNormal = font.getBaseFont();
		}
		return harrietNormal;
	}
	
	public static BaseFont getHarrietBold() {
		if (harrietBold == null) {
			Font font = FontFactory.getFont("/com/tagtheagency/portal/pitch/HarrietDisplayBold.otf",
				    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.BOLD, Color.BLACK);
			harrietBold = font.getBaseFont();
		}
		return harrietBold;
	}
	
	public static BaseFont getFutura() {
		if (futura == null) {
			Font font = FontFactory.getFont("/com/tagtheagency/portal/pitch/Futura.ttc",
				    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, Color.BLACK);
			futura = font.getBaseFont();
		}
		return futura;
	}


	public static Color getGreyText() {
		if (greyText == null) {
			greyText = new Color(0x9F, 0xA2, 0xA9);
		}
		return greyText;
	}
}
