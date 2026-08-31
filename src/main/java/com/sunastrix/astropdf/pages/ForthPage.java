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

public class ForthPage {
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
			float bgY = 360f;
			float bgWidth = 514f;
			float bgHeight = 340f;
			float headWidth = 150f;
			float headX = 37f;
			float headY = 683f;
			drawAstrologicalHeader(document, page, pageWidth, pageHeight, poppinsRegularFont);
			drawPersonalDetailsBackground(contentStream, bgX, bgY, bgWidth, bgHeight);
			drawShape.drawImage(document, contentStream, "/images/head1.png", headX, headY, headWidth);
			drawShape.drawBoldText(headX + 25f, headY + 10f, "Lagna Chart", 14, poppinsRegularFont, Color.WHITE);
			float chartX = 45f;
			float chartY = 365f;
			float chartWidth = 500f;
			float chartHeight = 315f;
			svgBytes = getClass().getResourceAsStream("/images/lagna_chart.svg").readAllBytes();
			drawSvgExact(document, page, svgBytes, chartX, chartY, chartWidth, chartHeight);

			bgX = 40f;
			bgY = 90f;
			bgWidth = 250f;
			bgHeight = 250f;
			headWidth = 125f;
			headX = 39f;
			headY = 327f;
			drawPersonalDetailsBackground(contentStream, bgX, bgY, bgWidth, bgHeight);
			drawShape.drawImage(document, contentStream, "/images/head1.png", headX, headY, headWidth);
			drawShape.drawBoldText(headX + 15f, headY + 8f, "Lagna Chart", 11, poppinsRegularFont, Color.WHITE);
			chartX = 45f;
			chartY = 95f;
			chartWidth = 240f;
			chartHeight = 230f;
			svgBytes = getClass().getResourceAsStream("/images/lagna_chart.svg").readAllBytes();
			drawSvgExact(document, page, svgBytes, chartX, chartY, chartWidth, chartHeight);

			bgX = 300f;
			bgY = 90f;
			bgWidth = 250f;
			bgHeight = 250f;
			headWidth = 125f;
			headX = 299f;
			headY = 327f;
			drawPersonalDetailsBackground(contentStream, bgX, bgY, bgWidth, bgHeight);
			drawShape.drawImage(document, contentStream, "/images/head1.png", headX, headY, headWidth);
			drawShape.drawBoldText(headX + 25f, headY + 10f, "Lagna Chart", 11, poppinsRegularFont, Color.WHITE);
			chartX = 305f;
			chartY = 95f;
			chartWidth = 240f;
			chartHeight = 230f;
			svgBytes = getClass().getResourceAsStream("/images/lagna_chart.svg").readAllBytes();
			drawSvgExact(document, page, svgBytes, chartX, chartY, chartWidth, chartHeight);
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

	private void drawSvgExact(PDDocument document, PDPage page, byte[] svgBytes, float x, float y, float width,
			float height) throws Exception {

		// =========================================================
		// 1. Load SVG
		// =========================================================

		String parser = XMLResourceDescriptor.getXMLParserClassName();

		SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);

		SVGDocument svgDocument = factory.createSVGDocument(null, new ByteArrayInputStream(svgBytes));

		// =========================================================
		// 2. Batik renderer
		// =========================================================

		UserAgentAdapter userAgent = new UserAgentAdapter();

		DocumentLoader loader = new DocumentLoader(userAgent);

		BridgeContext bridgeContext = new BridgeContext(userAgent, loader);

		bridgeContext.setDynamicState(BridgeContext.STATIC);

		GVTBuilder builder = new GVTBuilder();

		GraphicsNode graphicsNode = builder.build(bridgeContext, svgDocument);

		// =========================================================
		// 3. SVG bounds
		// =========================================================

		Rectangle2D bounds = graphicsNode.getBounds();

		if (bounds == null || bounds.getWidth() <= 0 || bounds.getHeight() <= 0) {

			throw new IllegalArgumentException("SVG has invalid bounds");
		}

		// =========================================================
		// 4. Create Graphics2D using EXACT requested size
		// =========================================================

		PdfBoxGraphics2D g2d = new PdfBoxGraphics2D(document, width, height);

		// =========================================================
		// 5. IMPORTANT
		// Stretch SVG exactly to width/height
		// =========================================================

		double scaleX = width / bounds.getWidth();

		double scaleY = height / bounds.getHeight();

		AffineTransform transform = new AffineTransform();

		transform.translate(-bounds.getX(), -bounds.getY());

		transform.scale(scaleX, scaleY);

		g2d.transform(transform);

		// =========================================================
		// 6. Draw SVG
		// =========================================================

		graphicsNode.paint(g2d);

		// =========================================================
		// 7. Dispose BEFORE getXFormObject()
		// =========================================================

		g2d.dispose();

		PDFormXObject form = g2d.getXFormObject();

		// =========================================================
		// 8. Put form on PDF page
		// =========================================================

		try (PDPageContentStream cs = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				true, true)) {

			cs.saveGraphicsState();

			cs.transform(Matrix.getTranslateInstance(x, y));

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

	private void drawLagnaChart(PDDocument document, PDPage page, PDPageContentStream cs, float x, float y, float width,
			float height, PDType0Font font) throws IOException {

		Color orange = new Color(230, 120, 0);
		Color lightOrange = new Color(245, 145, 20);
		Color maroon = new Color(105, 0, 0);

		// =========================================================
		// SECTION
		// =========================================================

		float headerHeight = 30f;

		// Outer rounded section
		cs.setStrokingColor(orange);
		cs.setLineWidth(1.2f);

		drawRoundedRect(cs, x, y, width, height, 10f);

		cs.stroke();

		// =========================================================
		// HEADER / LABEL
		// =========================================================

		float labelWidth = 150f;
		float labelHeight = 30f;

		float labelX = x;
		float labelY = y + height - labelHeight;

		drawHeaderRibbon(cs, labelX, labelY, labelWidth, labelHeight, orange);

		drawText(cs, "Lagna Chart", labelX + 20f, labelY + 8f, 16f, font, Color.WHITE);

		// =========================================================
		// CHART POSITION
		// =========================================================

		float chartX = x + 12f;
		float chartY = y + 12f;

		float chartWidth = width - 24f;
		float chartHeight = height - headerHeight - 24f;

		// =========================================================
		// CHART BACKGROUND
		// =========================================================

		cs.setNonStrokingColor(new Color(255, 253, 247));

		drawRoundedRect(cs, chartX, chartY, chartWidth, chartHeight, 10f);

		cs.fill();

		// =========================================================
		// CHART BORDER
		// =========================================================

		cs.setStrokingColor(orange);
		cs.setLineWidth(1.4f);

		drawRoundedRect(cs, chartX, chartY, chartWidth, chartHeight, 10f);

		cs.stroke();

		// =========================================================
		// NORTH INDIAN KUNDLI
		// =========================================================

		drawNorthIndianChart(cs, chartX + 2f, chartY + 2f, chartWidth - 4f, chartHeight - 4f, font);
	}

	private void drawHeaderRibbon(PDPageContentStream cs, float x, float y, float width, float height, Color color)
			throws IOException {

		float r = 8f;

		cs.setNonStrokingColor(color);

		// Start bottom-left
		cs.moveTo(x, y);

		// Left side
		cs.lineTo(x, y + height - r);

		// Top-left curve
		float k = 0.5522848f;

		cs.curveTo(x, y + height - r + r * k, x + r - r * k, y + height, x + r, y + height);

		// Top
		cs.lineTo(x + width - r, y + height);

		// Top-right curve
		cs.curveTo(x + width - r + r * k, y + height, x + width, y + height - r + r * k, x + width, y + height - r);

		// Right side
		cs.lineTo(x + width - 8f, y);

		// Bottom
		cs.closePath();

		cs.fill();
	}

	private void drawNorthIndianChart(PDPageContentStream cs, float x, float y, float width, float height,
			PDType0Font font) throws IOException {

		Color orange = new Color(230, 120, 0);

		cs.setStrokingColor(orange);
		cs.setLineWidth(1.25f);

		float left = x;
		float right = x + width;

		float bottom = y;
		float top = y + height;

		float cx = x + width / 2f;
		float cy = y + height / 2f;

		// =========================================================
		// OUTER BORDER
		// =========================================================

		drawRoundedRect(cs, x, y, width, height, 9f);

		cs.stroke();

		// =========================================================
		// MAIN DIAGONALS
		// =========================================================

		// Top-left -> center
		cs.moveTo(left, top);
		cs.lineTo(cx, cy);

		// Top-right -> center
		cs.moveTo(right, top);
		cs.lineTo(cx, cy);

		// Bottom-left -> center
		cs.moveTo(left, bottom);
		cs.lineTo(cx, cy);

		// Bottom-right -> center
		cs.moveTo(right, bottom);
		cs.lineTo(cx, cy);

		// =========================================================
		// CURVED HOUSE LINES
		// =========================================================

		float curve = width * 0.12f;

		// ---------------------------------------------------------
		// TOP LEFT HOUSE
		// ---------------------------------------------------------

		cs.moveTo(left, top);

		cs.curveTo(left + width * 0.18f, top - height * 0.15f,

				left + width * 0.30f, cy + height * 0.10f,

				cx, cy);

		// ---------------------------------------------------------
		// TOP RIGHT HOUSE
		// ---------------------------------------------------------

		cs.moveTo(right, top);

		cs.curveTo(right - width * 0.18f, top - height * 0.15f,

				right - width * 0.30f, cy + height * 0.10f,

				cx, cy);

		// ---------------------------------------------------------
		// LEFT MIDDLE
		// ---------------------------------------------------------

		cs.moveTo(left, cy);

		cs.curveTo(left + width * 0.18f, cy - height * 0.05f,

				left + width * 0.22f, cy + height * 0.05f,

				cx, cy);

		// ---------------------------------------------------------
		// RIGHT MIDDLE
		// ---------------------------------------------------------

		cs.moveTo(right, cy);

		cs.curveTo(right - width * 0.18f, cy - height * 0.05f,

				right - width * 0.22f, cy + height * 0.05f,

				cx, cy);

		// =========================================================
		// LOWER CURVES
		// =========================================================

		// Bottom-left
		cs.moveTo(left, bottom);

		cs.curveTo(left + width * 0.18f, bottom + height * 0.15f,

				left + width * 0.30f, cy - height * 0.10f,

				cx, cy);

		// Bottom-right
		cs.moveTo(right, bottom);

		cs.curveTo(right - width * 0.18f, bottom + height * 0.15f,

				right - width * 0.30f, cy - height * 0.10f,

				cx, cy);

		cs.stroke();

		// =========================================================
		// EXTRA HOUSE BOUNDARIES
		// =========================================================

		drawNorthIndianCurves(cs, x, y, width, height);

		// =========================================================
		// PLANETS
		// =========================================================

		drawPlanet(cs, "Su", "7", x + width * 0.17f, y + height * 0.79f, font, new Color(230, 0, 0));

		drawPlanet(cs, "Mo", "8", x + width * 0.33f, y + height * 0.82f, font, new Color(0, 110, 190));

		drawPlanet(cs, "Ma", "5", x + width * 0.64f, y + height * 0.82f, font, new Color(230, 0, 0));

		drawPlanet(cs, "Ra", "4", x + width * 0.82f, y + height * 0.80f, font, new Color(80, 30, 130));

		drawPlanet(cs, "Me", "9", x + width * 0.08f, y + height * 0.57f, font, new Color(0, 120, 60));

		drawPlanet(cs, "Ve", "3", x + width * 0.91f, y + height * 0.57f, font, new Color(220, 20, 100));

		drawPlanet(cs, "Ju", "11", x + width * 0.24f, y + height * 0.38f, font, new Color(220, 110, 0));

		drawPlanet(cs, "Sa", "2", x + width * 0.75f, y + height * 0.38f, font, new Color(20, 70, 130));

		drawPlanet(cs, "Ke", "", x + width * 0.08f, y + height * 0.18f, font, new Color(80, 50, 30));

		// =========================================================
		// CENTER ASCENDANT
		// =========================================================

		drawPlanet(cs, "As", "10", cx - 10f, cy + 8f, font, new Color(220, 110, 0));

		// =========================================================
		// HOUSE NUMBER 1
		// =========================================================

		drawText(cs, "1", cx + width * 0.15f, y + height * 0.08f, 10f, font, Color.BLACK);
	}

	private void drawNorthIndianCurves(PDPageContentStream cs, float x, float y, float width, float height)
			throws IOException {

		float left = x;
		float right = x + width;

		float bottom = y;
		float top = y + height;

		float cx = x + width / 2f;
		float cy = y + height / 2f;

		// =========================================================
		// LEFT TOP CURVE
		// =========================================================

		cs.moveTo(left, top);

		cs.curveTo(x + width * 0.17f, top - height * 0.10f, x + width * 0.28f, cy + height * 0.12f, x + width * 0.50f,
				cy);

		// =========================================================
		// RIGHT TOP CURVE
		// =========================================================

		cs.moveTo(right, top);

		cs.curveTo(right - width * 0.17f, top - height * 0.10f, right - width * 0.28f, cy + height * 0.12f, cx, cy);

		// =========================================================
		// LEFT BOTTOM CURVE
		// =========================================================

		cs.moveTo(left, bottom);

		cs.curveTo(x + width * 0.17f, bottom + height * 0.10f, x + width * 0.28f, cy - height * 0.12f, cx, cy);

		// =========================================================
		// RIGHT BOTTOM CURVE
		// =========================================================

		cs.moveTo(right, bottom);

		cs.curveTo(right - width * 0.17f, bottom + height * 0.10f, right - width * 0.28f, cy - height * 0.12f, cx, cy);

		cs.stroke();
	}

	private void drawPlanet(PDPageContentStream cs, String planet, String number, float x, float y, PDType0Font font,
			Color color) throws IOException {

		// Planet
		cs.beginText();

		cs.setFont(font, 12f);

		cs.setNonStrokingColor(color);

		cs.newLineAtOffset(x, y);

		cs.showText(planet);

		cs.endText();

		// Number
		if (number != null && !number.isEmpty()) {

			cs.beginText();

			cs.setFont(font, 10f);

			cs.setNonStrokingColor(Color.BLACK);

			cs.newLineAtOffset(x + 2f, y - 15f);

			cs.showText(number);

			cs.endText();
		}
	}

	private void drawText(PDPageContentStream cs, String text, float x, float y, float fontSize, PDType0Font font,
			Color color) throws IOException {

		cs.beginText();

		cs.setFont(font, fontSize);

		cs.setNonStrokingColor(color);

		cs.newLineAtOffset(x, y);

		cs.showText(text);

		cs.endText();
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
