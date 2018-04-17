package com.tagtheagency.portal.pitch.pdf;

import org.commonmark.node.Node;
import org.commonmark.renderer.Renderer;

import com.lowagie.text.pdf.PdfContentByte;

public class MarkdownPdfRenderer implements Renderer {

	private PdfContentByte cb;
	private CorePdfNodeRenderer context;

	public MarkdownPdfRenderer(PdfContentByte cb) {
		this.cb = cb;
	}
	
	@Override
	public String render(Node node) {
		context = new CorePdfNodeRenderer(cb);
        context.render(node);
        return "";
	}

	@Override
	public void render(Node arg0, Appendable arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public CorePdfNodeRenderer getRenderer() {
		return context;
	}
	
}
