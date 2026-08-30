package com.sunastrix.astropdf.service_impl;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.springframework.stereotype.Service;
import org.w3c.dom.svg.SVGDocument;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astroganitlib.model.BirthDetailBean;
import com.sunastrix.astropdf.service.PDFGenerateColorService;
import com.sunastrix.astropdf.util.ConstantHindi;
import com.sunastrix.astropdf.util.DrawShape;
import com.sunastrix.astropdf.util.Utility;

import de.rototor.pdfbox.graphics2d.PdfBoxGraphics2D;

@Service
public class PDFGenerateColorServiceImpl implements PDFGenerateColorService {
	DesktopHoroNew desktopHoro;
	BirthDetailBean birthDetailBean;
	PDPageContentStream contentStream;
	public PDType0Font poppinsRegularFont;
	public PDType0Font krutiDevRegularFont;
	public PDType0Font notoSerifDevanagariRegularFont;
	ConstantHindi constantHindi;
	float pageWidth;
	float pageHeight;
	PDRectangle mediaBox;
	DrawShape drawShape = new DrawShape();
	int pageNumber;
	Utility utility;

	public byte[] generatePDF(BirthDetailBean birthDetailBean) throws IOException {
		PDDocument document = new PDDocument();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		pageNumber = 1;
		try {
			InputStream fontStream1 = getClass().getResourceAsStream("/fonts/poppins_regular.ttf");
			InputStream fontStream2 = getClass().getResourceAsStream("/fonts/Kruti-Dev-Regular.ttf");
			InputStream fontStream3 = getClass().getResourceAsStream("/fonts/NotoSerifDevanagari-Regular.ttf");
			if (fontStream1 == null || fontStream2 == null || fontStream3 == null) {
				throw new IOException("Font resource not found in classpath: /fonts/poppins_regular.ttf");
			}
			poppinsRegularFont = PDType0Font.load(document, fontStream1);
			krutiDevRegularFont = PDType0Font.load(document, fontStream2);
			notoSerifDevanagariRegularFont = PDType0Font.load(document, fontStream3);
		} catch (Exception e) {
			System.out.println("font " + e.getMessage());
		}
		this.birthDetailBean = birthDetailBean;
		desktopHoro = getDesktopHoro(birthDetailBean);
		constantHindi = new ConstantHindi();
		utility = new Utility();
		printCoverPage(document);
		drawPage2(document);

		document.save(byteArrayOutputStream);
		return byteArrayOutputStream.toByteArray();
	};

	public DesktopHoroNew getDesktopHoro(BirthDetailBean birthDetailBean) {
		DesktopHoroNew args1 = new DesktopHoroNew();
		try {
			args1.setBirthDetail(birthDetailBean);
			args1.initialize();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return args1;
	}

	void printCoverPage(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			mediaBox = page.getMediaBox();
			pageWidth = mediaBox.getWidth();
			pageHeight = mediaBox.getHeight();
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);

			Color gold = new Color(190, 165, 90);
			Color color = new Color(110, 35, 35);
			Color gray = new Color(120, 120, 120);

			drawShape.drawBorder(pageWidth, pageHeight);
			float centerX = pageWidth / 2f;
			float y = pageHeight - 60f;
			drawShape.drawStar(centerX - 30f, y + 5f, 8f, gold);
			drawShape.drawText("ॐ", centerX - 8f, y, 15f, gold, poppinsRegularFont);
			drawShape.drawStar(centerX + 30f, y + 5f, 8f, gold);
			y = pageHeight - 120;
			String text = ",LVªksxf.kr T;ksfr\"k fjiksVZ"; // Kruti Dev text
			drawShape.drawTextHorizontalCenter(y, text, 30, krutiDevRegularFont, color);
			y = pageHeight - 150;
			text = "vkidh tUedqaMyh vkSj xzg fLFkfr dk laiw.kZ fo'ys\"k.k";
			drawShape.drawTextHorizontalCenter(y, text, 20, krutiDevRegularFont);

			drawPersonDetails("Jitendra Singh chauhan", "10 - 06 - 2026", "17 : 25 : 19", "Agra (27N9 78E0 5.5)");

			text = "Horoscope By"; // Kruti Dev text
			y = pageHeight - 550;
			drawShape.drawColorTextHorizontalCenter(y, text, 10, poppinsRegularFont, gray);

			text = "AstroGanit.com ";
			y = pageHeight - 570;
			drawShape.drawTextHorizontalCenter(y, text, 14, poppinsRegularFont, color);
			text = "SunAstrix Soft Pvt Ltd";
			y = pageHeight - 590;
			drawShape.drawColorTextHorizontalCenter(y, text, 10, poppinsRegularFont, gray);
			text = "Copyright © 2026 by Astro Ganit Software. All rights reserved." + "Terms of Use";
			y = pageHeight / 24;
			drawCopyRightText(poppinsRegularFont);
			drawShape.drawColorImage();
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void drawPersonDetails(String name, String date, String time, String place) throws IOException {

		float boxWidth = 400f;
		float boxHeight = 100f;
		float boxX = (pageWidth - boxWidth) / 2f;
		float boxY = pageHeight - 500f;
		Color white = Color.WHITE;
		Color boxColor = new Color(130, 10, 10);
		Color yellow = new Color(255, 215, 0);
		Color bottomLineColor = new Color(190, 165, 90);
		drawShape.drawRoundedRect(boxX, boxY, boxWidth, boxHeight, 6f, boxColor);
		float nameY = boxY + 72f;
		float dottedLineY = boxY + 57f;
		float dateY = boxY + 37f;
		float timeY = boxY + 25f;
		float placeY = boxY + 13f;
		drawShape.drawCenteredBoldText(boxX, boxWidth, nameY, name, 12, poppinsRegularFont, white);
		contentStream.setStrokingColor(yellow);
		drawShape.drawDottedLine(boxX + 40f, dottedLineY, boxX + boxWidth - 40f, dottedLineY);
		drawShape.drawCenteredText(boxX, boxWidth, dateY, "Date: " + date, 9, poppinsRegularFont, white);
		drawShape.drawCenteredText(boxX, boxWidth, timeY, "Time: " + time, 9, poppinsRegularFont, white);
		drawShape.drawCenteredText(boxX, boxWidth, placeY, "Place: " + place, 9, poppinsRegularFont, white);
		contentStream.setStrokingColor(bottomLineColor);
		drawShape.drawLine(80f, boxY - 15f, pageWidth - 80f, boxY - 15f);
	}

	void drawCopyRightText(PDType0Font font) throws IOException {
		Color gray = new Color(120, 120, 120);
		Color termsColor = new Color(180, 140, 60); // your desired color
		String copyright = "Copyright © 2026 by Astro Ganit Software. All rights reserved.";
		String terms = "Terms of Use";
		float fontSize = 7f;
		float copyrightWidth = font.getStringWidth(copyright) / 1000f * fontSize;
		float termsWidth = font.getStringWidth(terms) / 1000f * fontSize;
		float gap = 2f;
		float totalWidth = copyrightWidth + gap + termsWidth;
		float startX = (pageWidth - totalWidth) / 2f;
		float y = 25f;
		drawShape.drawText(startX, y, copyright, (int) fontSize, font, gray);
		drawShape.drawText(startX + copyrightWidth + gap, y, terms, (int) fontSize, font, termsColor);
	}

	private void drawPage2(PDDocument document) throws IOException {

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
			drawAstrologicalHeader(document, page, pageWidth, pageHeight, poppinsRegularFont);

			float bgX = 39f;
			float bgY = 482f;
			float bgWidth = 514f;
			float bgHeight = 233f;

			drawPersonalDetailsBackground(contentStream, bgX, bgY, bgWidth, bgHeight);

			// =====================================================
			// HEAD IMAGE ON TOP OF RECTANGLE
			// =====================================================

			float headWidth = 200f;
			float headX = 37f;
			float headY = 693f;

			drawShape.drawImage(document, contentStream, "/images/head1.png", headX, headY, headWidth);
			drawShape.drawBoldText(headX + 25f, headY + 15f, "Basic Detail", 16, poppinsRegularFont, Color.WHITE);
			drawBasicDetailsTable(document, page, poppinsRegularFont, poppinsRegularFont);

			bgX = 39f;
			bgY = 136f;
			bgWidth = 514f;
			bgHeight = 310f;

			drawPersonalDetailsBackground(contentStream, bgX, bgY, bgWidth, bgHeight);
			float avakhadaHeadWidth = 205f;
			float avakhadaHeadX = 37f;
			float avakhadaHeadY = 423f;

			drawShape.drawImage(document, contentStream, "/images/head1.png", avakhadaHeadX, avakhadaHeadY,
					avakhadaHeadWidth);

			drawShape.drawBoldText(avakhadaHeadX + 20f, avakhadaHeadY + 15f, "Avakhada Details", 16, poppinsRegularFont,
					Color.WHITE);

			// =====================================================
			// AVAKHADA TABLE
			// =====================================================

			drawAvakhadaDetailsTable(document, page, poppinsRegularFont, poppinsRegularFont);

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

	public void drawBorderImages(PDDocument document, PDPage page, byte[] topLeftBytes, byte[] topRightBytes,
			byte[] bottomLeftBytes, byte[] bottomRightBytes) throws IOException {

		PDRectangle box = page.getMediaBox();

		float pageWidth = box.getWidth();
		float pageHeight = box.getHeight();

		// --------------------------------------------------
		// Corner image size
		// --------------------------------------------------

		float cornerWidth = 45f;
		float cornerHeight = 45f;

		// --------------------------------------------------
		// Create PDF images
		// --------------------------------------------------

		PDImageXObject topLeft = PDImageXObject.createFromByteArray(document, topLeftBytes, "top-left");

		PDImageXObject topRight = PDImageXObject.createFromByteArray(document, topRightBytes, "top-right");

		PDImageXObject bottomLeft = PDImageXObject.createFromByteArray(document, bottomLeftBytes, "bottom-left");

		PDImageXObject bottomRight = PDImageXObject.createFromByteArray(document, bottomRightBytes, "bottom-right");

		// --------------------------------------------------
		// Draw images
		// --------------------------------------------------

		try (PDPageContentStream cs = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				true, true)) {

			cs.saveGraphicsState();

			float margin = 8f;

			// ==============================================
			// TOP LEFT
			// ==============================================

			cs.drawImage(topLeft, margin, pageHeight - margin - cornerHeight, cornerWidth, cornerHeight);

			// ==============================================
			// TOP RIGHT
			// ==============================================

			cs.drawImage(topRight, pageWidth - margin - cornerWidth, pageHeight - margin - cornerHeight, cornerWidth,
					cornerHeight);

			// ==============================================
			// BOTTOM LEFT
			// ==============================================

			cs.drawImage(bottomLeft, margin, margin, cornerWidth, cornerHeight);

			// ==============================================
			// BOTTOM RIGHT
			// ==============================================

			cs.drawImage(bottomRight, pageWidth - margin - cornerWidth, margin, cornerWidth, cornerHeight);

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

	private void drawPersonalDetailsBackground(PDPageContentStream cs, float x, float y, float width, float height)
			throws IOException {

		Color borderColor = new Color(245, 70, 20);

		/*
		 * try (PDPageContentStream cs = new PDPageContentStream(document, page,
		 * PDPageContentStream.AppendMode.APPEND, true, true)) {
		 */

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

	private void drawBasicDetailsTable(PDDocument document, PDPage page, PDType0Font labelFont, PDType0Font valueFont)
			throws IOException {

		// =========================================================
		// TABLE POSITION
		// =========================================================

		float tableX = 45f;
		float tableY = 490f;

		float tableWidth = 490f;
		float tableHeight = 198f;

		// =========================================================
		// COLUMN WIDTHS
		// =========================================================

		float col1 = 102f;
		float col2 = 144f;
		float col3 = 102f;
		float col4 = 153f;

		float rowHeight = tableHeight / 5f;

		// =========================================================
		// DATA
		// =========================================================

		String[][] data = {

				{ "Name", "jitendra", "Date", "14-8-1994" }, { "Time", "15:45:0", "Timezone", "5.5" },
				{ "Day", "Sunday", "Longitude", "25.16.E" }, { "Place", "chandauli", "Sunrise", "05/29/53" },
				{ "Latitude", "83.16.N", "Sunset", "18/33/26" } };

		// =========================================================
		// COLORS
		// =========================================================

		Color valueBackground = new Color(253, 251, 247);

		Color borderColor = new Color(215, 205, 190);

		// =========================================================
		// CONTENT STREAM
		// =========================================================

		try (PDPageContentStream cs = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				true, true)) {

			cs.saveGraphicsState();

			// =====================================================
			// DRAW EACH ROW
			// =====================================================

			for (int row = 0; row < 5; row++) {

				float y = tableY + tableHeight - ((row + 1) * rowHeight);

				// =================================================
				// LABEL COLUMN 1 - GRADIENT
				// =================================================

				drawLabelGradient(cs, tableX, y, col1, rowHeight);

				// =================================================
				// VALUE COLUMN 1
				// =================================================

				cs.setNonStrokingColor(valueBackground);

				cs.addRect(tableX + col1, y, col2, rowHeight);

				cs.fill();

				// =================================================
				// LABEL COLUMN 2 - GRADIENT
				// =================================================

				drawLabelGradient(cs, tableX + col1 + col2, y, col3, rowHeight);

				// =================================================
				// VALUE COLUMN 2
				// =================================================

				cs.setNonStrokingColor(valueBackground);

				cs.addRect(tableX + col1 + col2 + col3, y, col4, rowHeight);

				cs.fill();

				// =================================================
				// CELL BORDERS
				// =================================================

				cs.setStrokingColor(borderColor);

				cs.setLineWidth(0.5f);

				// Column 1
				cs.addRect(tableX, y, col1, rowHeight);

				cs.stroke();

				// Column 2
				cs.addRect(tableX + col1, y, col2, rowHeight);

				cs.stroke();

				// Column 3
				cs.addRect(tableX + col1 + col2, y, col3, rowHeight);

				cs.stroke();

				// Column 4
				cs.addRect(tableX + col1 + col2 + col3, y, col4, rowHeight);

				cs.stroke();

				// =================================================
				// LABEL TEXT 1
				// =================================================

				drawCellText(cs, data[row][0], labelFont, 12f, tableX + 13f, y, col1, rowHeight, Color.WHITE);

				// =================================================
				// VALUE TEXT 1
				// =================================================

				drawCellText(cs, data[row][1], valueFont, 11f, tableX + col1 + 13f, y, col2, rowHeight, Color.BLACK);

				// =================================================
				// LABEL TEXT 2
				// =================================================

				drawCellText(cs, data[row][2], labelFont, 12f, tableX + col1 + col2 + 13f, y, col3, rowHeight,
						Color.WHITE);

				// =================================================
				// VALUE TEXT 2
				// =================================================

				drawCellText(cs, data[row][3], valueFont, 11f, tableX + col1 + col2 + col3 + 13f, y, col4, rowHeight,
						Color.BLACK);
			}

			cs.restoreGraphicsState();
		}
	}

	private void drawLabelGradient(PDPageContentStream cs, float x, float y, float width, float height)
			throws IOException {

		// Top = bright orange
		Color topColor = new Color(255, 150, 0);

		// Bottom = deep orange/red
		Color bottomColor = new Color(245, 70, 0);

		int steps = 50;

		float stripHeight = height / steps;

		for (int i = 0; i < steps; i++) {

			float ratio = i / (float) (steps - 1);

			int r = Math.round(topColor.getRed() + ratio * (bottomColor.getRed() - topColor.getRed()));

			int g = Math.round(topColor.getGreen() + ratio * (bottomColor.getGreen() - topColor.getGreen()));

			int b = Math.round(topColor.getBlue() + ratio * (bottomColor.getBlue() - topColor.getBlue()));

			cs.setNonStrokingColor(new Color(r, g, b));

			cs.addRect(x, y + (i * stripHeight), width, stripHeight + 0.3f);

			cs.fill();
		}
	}

	private void drawCellText(PDPageContentStream cs, String text, PDType0Font font, float fontSize, float x, float y,
			float cellWidth, float cellHeight, Color color) throws IOException {

		cs.beginText();
		cs.setFont(font, fontSize);
		cs.setNonStrokingColor(color);
		// Vertical center
		float textHeight = fontSize;
		float textY = y + (cellHeight - textHeight) / 2f + 3f;
		cs.newLineAtOffset(x, textY);
		cs.showText(text);
		cs.endText();
	}

	private void drawAvakhadaDetailsTable(PDDocument document, PDPage page, PDType0Font labelFont,
			PDType0Font valueFont) throws IOException {

		// =========================================================
		// TABLE POSITION
		// =========================================================

		float tableX = 45f;
		float tableY = 144f;

		float tableWidth = 510f;
		float tableHeight = 275f;

		// =========================================================
		// COLUMN WIDTHS
		// =========================================================

		float col1 = 105f;
		float col2 = 140f;
		float col3 = 110f;
		float col4 = 145f;

		// 7 rows
		float rowHeight = tableHeight / 7f;

		// =========================================================
		// DATA
		// =========================================================

		String[][] data = {

				{ "Paya", "Swarna", "Lagna", "Sagittarius" },

				{ "Varna", "Sudra", "Lagna Lord", "JUP" },

				{ "Yoni", "Vyagh", "Rasi", "Libra" },

				{ "Gana", "Rakshasa", "Rasi Lord", "VEN" },

				{ "Vashya", "Manav", "Nakshatra Pada", "Purvashadha-3" },

				{ "Nadi", "Antya", "Nakshatra Lord", "JUP" },

				{ "Balance of Dasha\nat Birth", "Jupiter 4 Y 0 M 15 D", "SunSign (Indian)", "Leo" } };

		// =========================================================
		// COLORS
		// =========================================================

		Color valueBackground = new Color(253, 251, 247);

		Color borderColor = new Color(215, 205, 190);

		// =========================================================
		// CONTENT STREAM
		// =========================================================

		try (PDPageContentStream cs = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				true, true)) {

			cs.saveGraphicsState();

			// =====================================================
			// ROWS
			// =====================================================

			for (int row = 0; row < 7; row++) {

				float y = tableY + tableHeight - ((row + 1) * rowHeight);

				// =================================================
				// LABEL 1 - GRADIENT
				// =================================================

				drawLabelGradient(cs, tableX, y, col1, rowHeight);

				// =================================================
				// VALUE 1
				// =================================================

				cs.setNonStrokingColor(valueBackground);

				cs.addRect(tableX + col1, y, col2, rowHeight);

				cs.fill();

				// =================================================
				// LABEL 2 - GRADIENT
				// =================================================

				drawLabelGradient(cs, tableX + col1 + col2, y, col3, rowHeight);

				// =================================================
				// VALUE 2
				// =================================================

				cs.setNonStrokingColor(valueBackground);

				cs.addRect(tableX + col1 + col2 + col3, y, col4, rowHeight);

				cs.fill();

				// =================================================
				// BORDERS
				// =================================================

				cs.setStrokingColor(borderColor);

				cs.setLineWidth(0.5f);

				// Column 1
				cs.addRect(tableX, y, col1, rowHeight);
				cs.stroke();

				// Column 2
				cs.addRect(tableX + col1, y, col2, rowHeight);
				cs.stroke();

				// Column 3
				cs.addRect(tableX + col1 + col2, y, col3, rowHeight);
				cs.stroke();

				// Column 4
				cs.addRect(tableX + col1 + col2 + col3, y, col4, rowHeight);
				cs.stroke();

				// =================================================
				// LABEL 1
				// =================================================

				if (data[row][0].contains("\n")) {

					String[] lines = data[row][0].split("\n");

					drawMultiLineCellText(cs, lines, labelFont, 11f, tableX + 13f, y, col1, rowHeight, Color.WHITE);

				} else {

					drawCellText(cs, data[row][0], labelFont, 11f, tableX + 13f, y, col1, rowHeight, Color.WHITE);
				}

				// =================================================
				// VALUE 1
				// =================================================

				drawCellText(cs, data[row][1], valueFont, 10.5f, tableX + col1 + 13f, y, col2, rowHeight, Color.BLACK);

				// =================================================
				// LABEL 2
				// =================================================

				drawCellText(cs, data[row][2], labelFont, 11f, tableX + col1 + col2 + 13f, y, col3, rowHeight,
						Color.WHITE);

				// =================================================
				// VALUE 2
				// =================================================

				drawCellText(cs, data[row][3], valueFont, 10.5f, tableX + col1 + col2 + col3 + 13f, y, col4, rowHeight,
						Color.BLACK);
			}

			cs.restoreGraphicsState();
		}
	}

	private void drawMultiLineCellText(PDPageContentStream cs, String[] lines, PDType0Font font, float fontSize,
			float x, float y, float cellWidth, float cellHeight, Color color) throws IOException {

		float lineHeight = fontSize + 2f;

		float totalHeight = lines.length * lineHeight;

		float startY = y + (cellHeight + totalHeight) / 2f - lineHeight;

		cs.beginText();

		cs.setFont(font, fontSize);

		cs.setNonStrokingColor(color);

		cs.newLineAtOffset(x, startY);

		for (int i = 0; i < lines.length; i++) {

			if (i > 0) {
				cs.newLineAtOffset(0, -lineHeight);
			}

			cs.showText(lines[i]);
		}

		cs.endText();
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
