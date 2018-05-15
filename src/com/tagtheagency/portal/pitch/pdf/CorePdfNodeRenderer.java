package com.tagtheagency.portal.pitch.pdf;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.BlockQuote;
import org.commonmark.node.BulletList;
import org.commonmark.node.Code;
import org.commonmark.node.Document;
import org.commonmark.node.Emphasis;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.HardLineBreak;
import org.commonmark.node.Heading;
import org.commonmark.node.HtmlBlock;
import org.commonmark.node.HtmlInline;
import org.commonmark.node.Image;
import org.commonmark.node.IndentedCodeBlock;
import org.commonmark.node.Link;
import org.commonmark.node.ListBlock;
import org.commonmark.node.ListItem;
import org.commonmark.node.Node;
import org.commonmark.node.OrderedList;
import org.commonmark.node.Paragraph;
import org.commonmark.node.SoftLineBreak;
import org.commonmark.node.StrongEmphasis;
import org.commonmark.node.Text;
import org.commonmark.node.ThematicBreak;
import org.commonmark.renderer.NodeRenderer;

import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.tagtheagency.portal.pitch.FontUtils;

/**
 * The node renderer that renders all the core nodes (comes last in the order of node renderers).
 */
public class CorePdfNodeRenderer extends AbstractVisitor implements NodeRenderer {

	private PdfContentByte cb;
	private ColumnText ct;
	final Color GREY = new Color(0x9F, 0xA2, 0xA9);

	private String literal;


	private com.lowagie.text.Paragraph currentParagraph;
	private Stack<Element> elements;

	public CorePdfNodeRenderer(PdfContentByte cb) {
		this.cb = cb;
		this.ct = new ColumnText(cb);
		elements = new Stack<>();

	}

	@Override
	public Set<Class<? extends Node>> getNodeTypes() {
		return new HashSet<>(Arrays.asList(
				Document.class,
				Heading.class,
				Paragraph.class,
				BlockQuote.class,
				BulletList.class,
				FencedCodeBlock.class,
				HtmlBlock.class,
				ThematicBreak.class,
				IndentedCodeBlock.class,
				Link.class,
				ListItem.class,
				OrderedList.class,
				Image.class,
				Emphasis.class,
				StrongEmphasis.class,
				Text.class,
				Code.class,
				HtmlInline.class,
				SoftLineBreak.class,
				HardLineBreak.class
				));
	}

	@Override
	public void render(Node node) {
		node.accept(this);
	}

	@Override
	public void visit(Document document) {
		// No rendering itself
		visitChildren(document);
	}

	@Override
	public void visit(Heading heading) {
		visitChildren(heading);
		
		com.lowagie.text.Paragraph paragraph2 = new com.lowagie.text.Paragraph();
		paragraph2.setFont(new Font(FontUtils.getFutura()));
		paragraph2.getFont().setSize(9+((5-heading.getLevel())*2));
		paragraph2.getFont().setColor(GREY);
		paragraph2.setLeading(0, 2);
		
		for (Element element : elements) {
			paragraph2.add(element);
		}
		elements.empty();
		ct.addElement(paragraph2);
		
		while (!elements.isEmpty()) {
			elements.pop();
		}

	}

	@Override
	public void visit(Paragraph paragraph) {
		visitChildren(paragraph);
		
		//if we're inside a list we actually don't want a paragraph!
		for (Element element : elements) {
			if (element instanceof com.lowagie.text.List) {
				return;
			}
		}

		com.lowagie.text.Paragraph paragraph2 = new com.lowagie.text.Paragraph();
		paragraph2.setFont(new Font(FontUtils.getFutura()));
		paragraph2.getFont().setSize(9);
		paragraph2.getFont().setColor(GREY);
		paragraph2.setSpacingAfter(10);
		
		for (Element element : elements) {
			paragraph2.add(element);
		}
		while (!elements.isEmpty()) {
			elements.pop();
		}
		ct.addElement(paragraph2);

	}

	@Override
	public void visit(BlockQuote blockQuote) {
		/*html.line();
	        html.tag("blockquote", getAttrs(blockQuote, "blockquote"));
	        html.line();
	        visitChildren(blockQuote);
	        html.line();
	        html.tag("/blockquote");
	        html.line();*/
	}

	@Override
	public void visit(BulletList bulletList) {
		com.lowagie.text.List list = new com.lowagie.text.List(com.lowagie.text.List.UNORDERED);
		elements.add(list);
		
		renderListBlock(bulletList, "ul");
		visitChildren(bulletList);
		
		while (elements.peek() != list) {
			list.add(elements.pop());
		}
		
		elements.pop();
		
		ct.addElement(list);
		
		/*renderListBlock(bulletList, "ul", getAttrs(bulletList, "ul"));*/
	}

	@Override
	public void visit(FencedCodeBlock fencedCodeBlock) {
		/*String literal = fencedCodeBlock.getLiteral();
	        Map<String, String> attributes = new LinkedHashMap<>();
	        String info = fencedCodeBlock.getInfo();
	        if (info != null && !info.isEmpty()) {
	            int space = info.indexOf(" ");
	            String language;
	            if (space == -1) {
	                language = info;
	            } else {
	                language = info.substring(0, space);
	            }
	            attributes.put("class", "language-" + language);
	        }
	        renderCodeBlock(literal, fencedCodeBlock, attributes);*/
	}

	@Override
	public void visit(HtmlBlock htmlBlock) {
		/*html.line();
	        if (context.shouldEscapeHtml()) {
	            html.tag("p", getAttrs(htmlBlock, "p"));
	            html.text(htmlBlock.getLiteral());
	            html.tag("/p");
	        } else {
	            html.raw(htmlBlock.getLiteral());
	        }
	        html.line();*/
	}

	@Override
	public void visit(ThematicBreak thematicBreak) {
		/*html.line();
	        html.tag("hr", getAttrs(thematicBreak, "hr"), true);
	        html.line();*/
	}

	@Override
	public void visit(IndentedCodeBlock indentedCodeBlock) {
		renderCodeBlock(indentedCodeBlock.getLiteral(), indentedCodeBlock, Collections.<String, String>emptyMap());
	}

	@Override
	public void visit(Link link) {
		/*Map<String, String> attrs = new LinkedHashMap<>();
	        String url = context.encodeUrl(link.getDestination());
	        attrs.put("href", url);
	        if (link.getTitle() != null) {
	            attrs.put("title", link.getTitle());
	        }
	        html.tag("a", getAttrs(link, "a", attrs));
	        visitChildren(link);
	        html.tag("/a");*/
	}

	@Override
	public void visit(ListItem listItem) {
//		com.lowagie.text.List list = (com.lowagie.text.List) elements.pop();
		
		com.lowagie.text.ListItem pdfListItem = new com.lowagie.text.ListItem();
		pdfListItem.setFont(new Font(FontUtils.getFutura()));
		pdfListItem.getFont().setSize(9);
		pdfListItem.getFont().setColor(GREY);
		elements.push(pdfListItem);
		
		
		visitChildren(listItem);
		while (elements.peek() != pdfListItem) {
			pdfListItem.add(elements.pop());
		}
		elements.pop();
		((com.lowagie.text.List)elements.peek()).add(pdfListItem);
		
	}

	@Override
	public void visit(OrderedList orderedList) {
		/*	        int start = orderedList.getStartNumber();
	        Map<String, String> attrs = new LinkedHashMap<>();
	        if (start != 1) {
	            attrs.put("start", String.valueOf(start));
	        }
	        renderListBlock(orderedList, "ol", getAttrs(orderedList, "ol", attrs));*/
	}

	@Override
	public void visit(Image image) {
		/*String url = context.encodeUrl(image.getDestination());

	        AltTextVisitor altTextVisitor = new AltTextVisitor();
	        image.accept(altTextVisitor);
	        String altText = altTextVisitor.getAltText();

	        Map<String, String> attrs = new LinkedHashMap<>();
	        attrs.put("src", url);
	        attrs.put("alt", altText);
	        if (image.getTitle() != null) {
	            attrs.put("title", image.getTitle());
	        }

	        html.tag("img", getAttrs(image, "img", attrs), true);*/
	}

	@Override
	public void visit(Emphasis emphasis) {
		visitChildren(emphasis);
		List<Chunk> chunks = elements.peek().getChunks();
		Chunk chunk = chunks.get(chunks.size()-1);
		chunk.getFont().setStyle(Font.ITALIC);

	}

	@Override
	public void visit(StrongEmphasis strongEmphasis) {	    	
		visitChildren(strongEmphasis);
		List<Chunk> chunks = elements.peek().getChunks();
		Chunk chunk = chunks.get(chunks.size()-1);
		chunk.getFont().setStyle(Font.BOLD);
	}

	@Override
	public void visit(Text text) {
		Chunk chunk = new Chunk(text.getLiteral());
		chunk.setFont(new Font(FontUtils.getFutura()));
				
		elements.add(chunk);		
	}

	@Override
	public void visit(Code code) {
		/*  html.tag("code", getAttrs(code, "code"));
	        html.text(code.getLiteral());
	        html.tag("/code");*/
	}

	@Override
	public void visit(HtmlInline htmlInline) {
		/*	        if (context.shouldEscapeHtml()) {
	            html.text(htmlInline.getLiteral());
	        } else {
	            html.raw(htmlInline.getLiteral());
	        }*/
	}

	@Override
	public void visit(SoftLineBreak softLineBreak) {
		/*	        html.raw(context.getSoftbreak());*/
	}

	@Override
	public void visit(HardLineBreak hardLineBreak) {
		//	        html.tag("br", getAttrs(hardLineBreak, "br"), true);
		//	        html.line();
	}

	@Override
	protected void visitChildren(Node parent) {
		Node node = parent.getFirstChild();
		while (node != null) {
			Node next = node.getNext();
			this.render(node);
			node = next;
		}
	}

	private void renderCodeBlock(String literal, Node node, Map<String, String> attributes) {
		//	        html.line();
		//	        html.tag("pre", getAttrs(node, "pre"));
		//	        html.tag("code", getAttrs(node, "code", attributes));
		//	        html.text(literal);
		//	        html.tag("/code");
		//	        html.tag("/pre");
		//	        html.line();
	}

	private void renderListBlock(ListBlock listBlock, String tagName) {
		//	        html.line();
		//	        html.tag(tagName, attributes);
		//	        html.line();
		//	        visitChildren(listBlock);
		//	        html.line();
		//	        html.tag('/' + tagName);
		//	        html.line();
	}

	private boolean isInTightList(Paragraph paragraph) {
		Node parent = paragraph.getParent();
		if (parent != null) {
			Node gramps = parent.getParent();
			if (gramps != null && gramps instanceof ListBlock) {
				ListBlock list = (ListBlock) gramps;
				return list.isTight();
			}
		}
		return false;
	}

	public void render(Rectangle[] columns) throws DocumentException {
		int c = 0;
        int status = ColumnText.START_COLUMN;
        while (ColumnText.hasMoreText(status)) {
            ct.setSimpleColumn(columns[c].getLeft(), columns[c].getBottom(), columns[c].getRight(), columns[c].getTop());
            status = ct.go();
            c++;
        }
	}
	
}
