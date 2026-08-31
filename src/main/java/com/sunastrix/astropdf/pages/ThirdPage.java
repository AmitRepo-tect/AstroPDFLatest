package com.sunastrix.astropdf.pages;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.util.Matrix;
import org.w3c.dom.svg.SVGDocument;

import com.sunastrix.astropdf.util.DrawShape;

import de.rototor.pdfbox.graphics2d.PdfBoxGraphics2D;

public class ThirdPage {
	float pageWidth;
	float pageHeight;
	PDDocument document;
	DrawShape drawShape = new DrawShape();
	PDPageContentStream contentStream;
	public PDType0Font poppinsRegularFont;
	public PDType0Font krutiDevRegularFont;
	public PDType0Font notoSerifDevanagariRegularFont;

	public void drawPage(PDDocument document, PDType0Font poppinsRegularFont) {
		this.document = document;
		this.poppinsRegularFont = poppinsRegularFont;
		PDPage page = new PDPage(PDRectangle.A4);
		document.addPage(page);
		float cornerSize = 40f;
		pageWidth = page.getMediaBox().getWidth();
		pageHeight = page.getMediaBox().getHeight();

		try (PDPageContentStream cs = new PDPageContentStream(document, page)) {

			this.contentStream = cs;

			drawShape.initialize(pageHeight, pageWidth, document, cs);
			drawPageBorder(document, page);
			byte[] svgBytes = getClass().getResourceAsStream("/images/corner_left_top.svg").readAllBytes();

			float margin = 15f;
			drawSvgNew(document, page, svgBytes, margin, pageHeight - margin - cornerSize, cornerSize, cornerSize, 0);
			drawSvgNew(document, page, svgBytes, pageWidth - margin - cornerSize, pageHeight - margin - cornerSize,
					cornerSize, cornerSize, 1);
			drawSvgNew(document, page, svgBytes, pageWidth - margin - cornerSize, margin, cornerSize, cornerSize, 2);
			drawSvgNew(document, page, svgBytes, margin, margin, cornerSize, cornerSize, 3);
			float bgX = 39f;
			float bgY = 482f;
			float bgWidth = 514f;
			float bgHeight = 233f;
			drawAstrologicalHeader(document, page, pageWidth, pageHeight, poppinsRegularFont);
			drawPersonalDetailsBackground(contentStream, bgX, bgY, bgWidth, bgHeight);
			float headWidth = 200f;
			float headX = 37f;
			float headY = 693f;

			float favX = 39f;
			float favY = 415f;
			float favWidth = 514f;
			float favHeight = 300f;

			drawPersonalDetailsBackground(contentStream, favX, favY, favWidth, favHeight);
			drawShape.drawImage(document, contentStream, "/images/head1.png", headX, headY, headWidth);

			drawShape.drawBoldText(headX + 25f, headY + 15f, "Favourable Points", 16, poppinsRegularFont, Color.WHITE);

			// =========================================================
			// FAVOURABLE CARDS
			// =========================================================

			drawFavourableItems(contentStream, poppinsRegularFont, favX - 5, favY - 25, favWidth, favHeight);

			headWidth = 200f;
			headX = 37f;
			headY = 358f;

			favX = 39f;
			favY = 80f;
			favWidth = 514f;
			favHeight = 300f;

			drawPersonalDetailsBackground(contentStream, favX, favY, favWidth, favHeight);
			drawShape.drawImage(document, contentStream, "/images/head1.png", headX, headY, headWidth);
			drawShape.drawBoldText(headX + 25f, headY + 15f, "Favourable Points", 16, poppinsRegularFont, Color.WHITE);
			drawFavourableItems(contentStream, poppinsRegularFont, favX - 5, favY - 25, favWidth, favHeight);
			drawFooter(document, page, pageWidth, pageHeight, poppinsRegularFont, poppinsRegularFont, 2);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void drawPageBorder(PDDocument document, PDPage page) throws IOException {

		PDRectangle mediaBox = page.getMediaBox();

		float pageWidth = mediaBox.getWidth();
		float pageHeight = mediaBox.getHeight();

		// Distance from page edge
		float outerMargin = 5f;
		float innerMargin = 10f;

		try (PDPageContentStream cs = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				true, true)) {

			cs.saveGraphicsState();

			cs.setStrokingColor(new Color(105, 20, 20));

			cs.setLineWidth(1.5f);

			cs.addRect(outerMargin, outerMargin, pageWidth - (outerMargin * 2), pageHeight - (outerMargin * 2));

			cs.stroke();

			// =====================================================
			// INNER BORDER
			// =====================================================

			cs.setStrokingColor(new Color(145, 30, 25));

			cs.setLineWidth(0.8f);

			cs.addRect(innerMargin, innerMargin, pageWidth - (innerMargin * 2), pageHeight - (innerMargin * 2));

			cs.stroke();

			cs.restoreGraphicsState();
		}
	}

	public void drawSvgNew(PDDocument document, PDPage page, byte[] svgBytes, float x, float y, float width,
			float height, int corner) throws Exception {

		// corner:
		// 0 = TOP LEFT
		// 1 = TOP RIGHT
		// 2 = BOTTOM RIGHT
		// 3 = BOTTOM LEFT

		// ---------------------------------------------------------
		// 1. Load SVG
		// ---------------------------------------------------------

		String parser = XMLResourceDescriptor.getXMLParserClassName();

		SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);

		SVGDocument svgDocument = factory.createSVGDocument(null, new ByteArrayInputStream(svgBytes));

		// ---------------------------------------------------------
		// 2. Batik renderer
		// ---------------------------------------------------------

		UserAgentAdapter userAgent = new UserAgentAdapter();

		DocumentLoader loader = new DocumentLoader(userAgent);

		BridgeContext bridgeContext = new BridgeContext(userAgent, loader);

		bridgeContext.setDynamicState(BridgeContext.STATIC);

		GVTBuilder builder = new GVTBuilder();

		GraphicsNode graphicsNode = builder.build(bridgeContext, svgDocument);

		// ---------------------------------------------------------
		// 3. SVG bounds
		// ---------------------------------------------------------

		Rectangle2D bounds = graphicsNode.getBounds();

		if (bounds == null || bounds.getWidth() <= 0 || bounds.getHeight() <= 0) {

			throw new IllegalArgumentException("SVG has invalid bounds");
		}

		// ---------------------------------------------------------
		// 4. Scale
		// ---------------------------------------------------------

		double svgWidth = bounds.getWidth();

		double svgHeight = bounds.getHeight();

		double scaleX = width / svgWidth;

		double scaleY = height / svgHeight;

		double scale = Math.min(scaleX, scaleY);

		double finalWidth = svgWidth * scale;

		double finalHeight = svgHeight * scale;

		// ---------------------------------------------------------
		// 5. Create Graphics2D
		// ---------------------------------------------------------

		PdfBoxGraphics2D g2d = new PdfBoxGraphics2D(document, width, height);

		// ---------------------------------------------------------
		// 6. Scale SVG
		// ---------------------------------------------------------

		AffineTransform svgTransform = new AffineTransform();

		double offsetX = (width - finalWidth) / 2.0;

		double offsetY = (height - finalHeight) / 2.0;

		svgTransform.translate(offsetX, offsetY);

		svgTransform.scale(scale, scale);

		svgTransform.translate(-bounds.getX(), -bounds.getY());

		g2d.transform(svgTransform);

		// ---------------------------------------------------------
		// 7. Paint SVG
		// ---------------------------------------------------------

		graphicsNode.paint(g2d);

		// ---------------------------------------------------------
		// 8. Dispose FIRST
		// ---------------------------------------------------------

		g2d.dispose();

		// ---------------------------------------------------------
		// 9. Get form AFTER dispose
		// ---------------------------------------------------------

		PDFormXObject form = g2d.getXFormObject();

		// ---------------------------------------------------------
		// 10. Draw
		// ---------------------------------------------------------

		try (PDPageContentStream cs = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				true, true)) {

			cs.saveGraphicsState();

			// =====================================================
			// TOP LEFT
			// =====================================================

			if (corner == 0) {

				cs.transform(new Matrix(1, 0, 0, 1, x, y));
			}

			// =====================================================
			// TOP RIGHT
			// Horizontal flip
			// =====================================================

			else if (corner == 1) {

				cs.transform(new Matrix(-1, 0, 0, 1, x + width, y));
			}

			// =====================================================
			// BOTTOM RIGHT
			// Horizontal + Vertical flip
			// =====================================================

			else if (corner == 2) {

				cs.transform(new Matrix(-1, 0, 0, -1, x + width, y + height));
			}

			// =====================================================
			// BOTTOM LEFT
			// Vertical flip
			// =====================================================

			else if (corner == 3) {

				cs.transform(new Matrix(1, 0, 0, -1, x, y + height));
			}

			else {

				throw new IllegalArgumentException("corner must be 0, 1, 2 or 3");
			}

			// -----------------------------------------------------
			// Draw SVG
			// -----------------------------------------------------

			cs.drawForm(form);

			cs.restoreGraphicsState();
		}
	}

	private void drawPersonalDetailsBackground(PDPageContentStream cs, float x, float y, float width, float height)
			throws IOException {

		Color borderColor = new Color(245, 70, 20);

		cs.saveGraphicsState();

		// ---------------------------------------------------------
		// Background
		// ---------------------------------------------------------

		cs.setNonStrokingColor(Color.WHITE);

		// ---------------------------------------------------------
		// Border
		// ---------------------------------------------------------

		cs.setStrokingColor(borderColor);
		cs.setLineWidth(1.2f);

		float radius = 12f;

		drawRoundedRectangle(cs, x, y, width, height, radius);

		// Fill + border
		cs.fillAndStroke();

		cs.restoreGraphicsState();
		// }
	}

	private void drawRoundedRectangle(PDPageContentStream cs, float x, float y, float width, float height, float radius)
			throws IOException {

		float k = 0.5522848f;

		float c = radius * k;

		cs.moveTo(x + radius, y);

		cs.lineTo(x + width - radius, y);

		// Bottom-right
		cs.curveTo(x + width - radius + c, y, x + width, y + radius - c, x + width, y + radius);

		cs.lineTo(x + width, y + height - radius);

		// Top-right
		cs.curveTo(x + width, y + height - radius + c, x + width - radius + c, y + height, x + width - radius,
				y + height);

		cs.lineTo(x + radius, y + height);

		// Top-left
		cs.curveTo(x + radius - c, y + height, x, y + height - radius + c, x, y + height - radius);

		cs.lineTo(x, y + radius);

		// Bottom-left
		cs.curveTo(x, y + radius - c, x + radius - c, y, x + radius, y);

		cs.closePath();
	}

	private void drawAstrologicalHeader(PDDocument document, PDPage page, float pageWidth, float pageHeight,
			PDType0Font titleFont) throws IOException {

		// =========================================================
		// HEADER POSITION
		// =========================================================

		float centerX = pageWidth / 2f;

		// Title baseline
		float titleY = pageHeight - 65f;

		// =========================================================
		// COLORS
		// =========================================================

		Color maroon = new Color(105, 0, 0);
		Color orange = new Color(225, 147, 0);

		try (PDPageContentStream cs = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				true, true)) {

			cs.saveGraphicsState();

			// =====================================================
			// 1. TITLE
			// =====================================================

			String title = "Astrological Profile";

			float fontSize = 30f;

			float titleWidth = titleFont.getStringWidth(title) / 1000f * fontSize;

			float titleX = centerX - titleWidth / 2f;

			cs.beginText();

			cs.setFont(titleFont, fontSize);

			cs.setNonStrokingColor(maroon);

			cs.newLineAtOffset(titleX, titleY);

			cs.showText(title);

			cs.endText();

			// =====================================================
			// 2. LEFT STAR
			// =====================================================

			float starY = titleY + 10f;

			drawFourPointStar(cs, titleX - 32f, starY, 9f, orange);

			// =====================================================
			// 3. RIGHT STAR
			// =====================================================

			drawFourPointStar(cs, titleX + titleWidth + 32f, starY, 9f, orange);

			// =====================================================
			// 4. DECORATIVE LINE
			// =====================================================

			float lineY = pageHeight - 105f;

			float lineLeft = centerX - 230f;
			float lineRight = centerX + 230f;

			// drawSvgNew(document, page, null, titleX, lineY, titleWidth, pageHeight,
			// pageNumber);
			byte[] svgBytes = getClass().getResourceAsStream("/images/ornamental_shape.svg").readAllBytes();
			// drawSvgNew(document, page, svgBytes, margin, pageHeight - margin -
			// cornerSize, cornerSize, cornerSize, 0);

			float lineWidth = 460f;
			float lineHeight = 43f;

			float lineX = (pageWidth - lineWidth) / 2f;
			lineY = pageHeight - 105f;
			try {
				drawSvgNew(document, page, svgBytes, lineX, lineY, lineWidth, lineHeight, 0);
			} catch (Exception e) {

			}

			// drawCenterOrnament(cs, centerX, lineY, orange);

			cs.restoreGraphicsState();
		}
	}

	private void drawFourPointStar(PDPageContentStream cs, float cx, float cy, float size, Color color)
			throws IOException {

		cs.setNonStrokingColor(color);

		cs.moveTo(cx, cy + size);

		cs.curveTo(cx + 2f, cy + 3f, cx + 3f, cy + 2f, cx + size, cy);

		cs.curveTo(cx + 3f, cy - 2f, cx + 2f, cy - 3f, cx, cy - size);

		cs.curveTo(cx - 2f, cy - 3f, cx - 3f, cy - 2f, cx - size, cy);

		cs.curveTo(cx - 3f, cy + 2f, cx - 2f, cy + 3f, cx, cy + size);

		cs.fill();
	}

	private void drawFavourableItemBackground(PDPageContentStream cs, float x, float y, float width, float height,
			Color accentColor) throws IOException {
		float radius = 8f;

		cs.setNonStrokingColor(accentColor);

		drawRoundedRect(cs, x, y, width, 40, radius);
		cs.fill();

		// =========================================================
		// 1. WHITE CARD
		// =========================================================

		cs.setNonStrokingColor(Color.WHITE);

		drawRoundedRect(cs, x, y + 5, width, height - 3, radius);

		cs.fill();

		// =========================================================
		// 2. LIGHT GRAY BORDER
		// =========================================================

		cs.setStrokingColor(new Color(220, 220, 220));

		cs.setLineWidth(0.7f);

		drawRoundedRect(cs, x, y + 5, width, height - 3, radius);

		cs.stroke();

	}

	private void drawRoundedRect(PDPageContentStream cs, float x, float y, float width, float height, float radius)
			throws IOException {

		float k = 0.5522848f;

		float c = radius * k;

		// Bottom
		cs.moveTo(x + radius, y);

		cs.lineTo(x + width - radius, y);

		// Bottom-right
		cs.curveTo(x + width - radius + c, y, x + width, y + radius - c, x + width, y + radius);

		// Right
		cs.lineTo(x + width, y + height - radius);

		// Top-right
		cs.curveTo(x + width, y + height - radius + c, x + width - radius + c, y + height, x + width - radius,
				y + height);

		// Top
		cs.lineTo(x + radius, y + height);

		// Top-left
		cs.curveTo(x + radius - c, y + height, x, y + height - radius + c, x, y + height - radius);

		// Left
		cs.lineTo(x, y + radius);

		// Bottom-left
		cs.curveTo(x, y + radius - c, x + radius - c, y, x + radius, y);

		cs.closePath();
	}

	private void drawFavourableCard(PDPageContentStream cs, PDType0Font font, float x, float y, float width,
			float height, String value, String label, Color accentColor) throws IOException {

		// =========================================================
		// 1. BACKGROUND
		// =========================================================

		drawFavourableItemBackground(cs, x, y, width, height, accentColor);

		// =========================================================
		// 2. VALUE
		// =========================================================

		String[] valueLines = value.split("\n");

		float valueFontSize = 10f;
		float lineHeight = 12f;

		float valueY = y + height - 23f;

		for (String line : valueLines) {

			float textWidth = font.getStringWidth(line) / 1000f * valueFontSize;

			float textX = x + (width - textWidth) / 2f;

			cs.beginText();

			cs.setFont(font, valueFontSize);

			cs.setNonStrokingColor(accentColor);

			cs.newLineAtOffset(textX, valueY);

			cs.showText(line);

			cs.endText();

			valueY -= lineHeight;
		}

		// =========================================================
		// 3. LABEL
		// =========================================================

		float labelFontSize = 7f;

		float labelWidth = font.getStringWidth(label) / 1000f * labelFontSize;

		float labelX = x + (width - labelWidth) / 2f;

		float labelY = y + 12f;

		cs.beginText();

		cs.setFont(font, labelFontSize);

		cs.setNonStrokingColor(Color.BLACK);

		cs.newLineAtOffset(labelX, labelY);

		cs.showText(label);

		cs.endText();
	}

	private void drawFavourableItems(PDPageContentStream cs, PDType0Font font, float sectionX, float sectionY,
			float sectionWidth, float sectionHeight) throws IOException {

		// =========================================================
		// COLORS
		// =========================================================

		Color green = new Color(0, 145, 75);

		Color blue = new Color(20, 145, 205);

		Color purple = new Color(105, 45, 190);

		Color orange = new Color(225, 125, 10);

		// =========================================================
		// ITEMS
		// =========================================================

		FavourableItem[] items = {

				new FavourableItem("Blue, Green", "Lucky Color", green),

				new FavourableItem("6, 15, 24, 33,\n42, 51, 60", "Good Number", blue),

				new FavourableItem("2", "Lucky Number", purple),

				new FavourableItem("Friday", "Lucky Day", orange),

				new FavourableItem("Saturn,\nMercury, Venus", "Good Planet", green),

				new FavourableItem("20, 29, 38, 47", "Good Year", blue),

				new FavourableItem("Virgo Capricorn\nAquarius", "Friendly Sign", purple),

				new FavourableItem("Leo, Scorpio,\nCapricorn, Pisces", "Good Lagna", orange),

				new FavourableItem("Silver or Gold", "Lucky Metal", green),

				new FavourableItem("Diamond", "Lucky Stone", purple) };

		// =========================================================
		// CARD DIMENSIONS
		// =========================================================

		float cardWidth = 117f;
		float cardHeight = 76f;

		float gapX = 10f;
		float gapY = 15f;

		// =========================================================
		// START POSITION
		// =========================================================

		float startX = sectionX + 12f;

		float startY = sectionY + sectionHeight - 80f;

		// =========================================================
		// DRAW 4 COLUMNS
		// =========================================================

		for (int i = 0; i < items.length; i++) {

			int row = i / 4;

			int column = i % 4;

			float x = startX + column * (cardWidth + gapX);

			float y = startY - row * (cardHeight + gapY);

			FavourableItem item = items[i];

			drawFavourableCard(cs, font, x, y, cardWidth, cardHeight, item.value, item.label, item.color);
		}
	}

	private void drawFooter(PDDocument document, PDPage page, float pageWidth, float pageHeight, PDType0Font titleFont,
			PDType0Font subtitleFont, int pageNumber) throws IOException {

		// =========================================================
		// COLORS
		// =========================================================

		Color green = new Color(0, 91, 72);
		Color gray = new Color(125, 125, 125);
		Color gold = new Color(225, 147, 0);
		Color pageBox = new Color(255, 190, 20);

		// =========================================================
		// FOOTER POSITIONS
		// =========================================================

		float centerX = pageWidth / 2f;

		// Main footer text
		float titleY = 67f;

		// Subtitle
		float subtitleY = 44f;

		try (PDPageContentStream cs = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				true, true)) {

			cs.saveGraphicsState();

			// =====================================================
			// 1. ASTROGANIT KUNDLI
			// =====================================================

			String title = "AstroGanit Kundli";

			float titleFontSize = 13f;

			float titleWidth = titleFont.getStringWidth(title) / 1000f * titleFontSize;

			float titleX = centerX - titleWidth / 2f;

			cs.beginText();

			cs.setFont(titleFont, titleFontSize);

			cs.setNonStrokingColor(green);

			cs.newLineAtOffset(titleX, titleY);

			cs.showText(title);

			cs.endText();

			// =====================================================
			// 2. LEFT DECORATIVE LINE
			// =====================================================

			float lineY = titleY + 5f;

			float lineStartX = titleX - 38f;

			float lineEndX = titleX - 8f;

			cs.setStrokingColor(gold);

			cs.setLineWidth(1.2f);

			cs.moveTo(lineStartX, lineY);

			cs.lineTo(lineEndX, lineY);

			cs.stroke();

			// Left dot
			cs.setNonStrokingColor(gold);

			cs.addRect(lineStartX - 2f, lineY - 2f, 4f, 4f);

			cs.fill();

			// =====================================================
			// 3. RIGHT DECORATIVE LINE
			// =====================================================

			float rightLineStart = titleX + titleWidth + 8f;

			float rightLineEnd = titleX + titleWidth + 38f;

			cs.setStrokingColor(gold);

			cs.setLineWidth(1.2f);

			cs.moveTo(rightLineStart, lineY);

			cs.lineTo(rightLineEnd, lineY);

			cs.stroke();

			// Right dot
			cs.setNonStrokingColor(gold);

			cs.addRect(rightLineEnd - 2f, lineY - 2f, 4f, 4f);

			cs.fill();

			// =====================================================
			// 4. POWERED BY TEXT
			// =====================================================

			String subtitle = "Powered by SunAstrix Soft Pvt. Ltd.";

			float subtitleFontSize = 11f;

			float subtitleWidth = subtitleFont.getStringWidth(subtitle) / 1000f * subtitleFontSize;

			float subtitleX = centerX - subtitleWidth / 2f;

			cs.beginText();

			cs.setFont(subtitleFont, subtitleFontSize);

			cs.setNonStrokingColor(gray);

			cs.newLineAtOffset(subtitleX, subtitleY);

			cs.showText(subtitle);

			cs.endText();

			// =====================================================
			// 5. PAGE NUMBER BOX
			// =====================================================

			float boxWidth = 40f;
			float boxHeight = 38f;

			float boxX = pageWidth - 40f - boxWidth;

			float boxY = 27f;

			// -----------------------------------------------------
			// Rounded yellow box
			// -----------------------------------------------------

			cs.setNonStrokingColor(pageBox);

			drawRoundedRectangle(cs, boxX, boxY, boxWidth, boxHeight, 6f);

			cs.fill();

			// -----------------------------------------------------
			// Box border
			// -----------------------------------------------------

			cs.setStrokingColor(new Color(220, 155, 0));

			cs.setLineWidth(0.8f);

			drawRoundedRectangle(cs, boxX, boxY, boxWidth, boxHeight, 6f);

			cs.stroke();

			// =====================================================
			// 6. PAGE NUMBER
			// =====================================================

			String number = String.valueOf(pageNumber);

			float numberFontSize = 17f;

			float numberWidth = titleFont.getStringWidth(number) / 1000f * numberFontSize;

			float numberX = boxX + (boxWidth - numberWidth) / 2f;

			float numberY = boxY + 11f;

			cs.beginText();

			cs.setFont(titleFont, numberFontSize);

			cs.setNonStrokingColor(Color.BLACK);

			cs.newLineAtOffset(numberX, numberY);

			cs.showText(number);

			cs.endText();

			cs.restoreGraphicsState();
		}
	}
}

class FavourableItem {

	String value;
	String label;
	Color color;

	FavourableItem(String value, String label, Color color) {
		this.value = value;
		this.label = label;
		this.color = color;
	}
}