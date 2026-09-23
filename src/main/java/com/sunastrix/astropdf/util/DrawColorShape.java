package com.sunastrix.astropdf.util;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBoolean;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSFloat;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType2;
import org.apache.pdfbox.util.Matrix;
import org.w3c.dom.svg.SVGDocument;

import de.rototor.pdfbox.graphics2d.PdfBoxGraphics2D;

public class DrawColorShape {
	float pageHeight;
	float pageWidth;
	PDDocument document;
	PDPageContentStream contentStream;

	public void initialize(float pageHeight, float pageWidth, PDDocument document, PDPageContentStream contentStream) {
		this.pageHeight = pageHeight;
		this.pageWidth = pageWidth;
		this.document = document;
		this.contentStream = contentStream;

	}

	public void drawLine(float x1, float y1, float x2, float y2, float lineWidth, Color color) throws IOException {

		contentStream.saveGraphicsState();
		contentStream.setStrokingColor(color);
		contentStream.setLineWidth(lineWidth);
		contentStream.moveTo(x1, y1);
		contentStream.lineTo(x2, y2);
		contentStream.stroke();
		contentStream.restoreGraphicsState();
	}

	public void drawFilledCircle(PDPageContentStream cs, float centerX, float centerY, float radius, Color color)
			throws IOException {
		final float k = 0.5522848f;
		float c = radius * k;
		cs.saveGraphicsState();
		cs.setNonStrokingColor(color);
		cs.moveTo(centerX + radius, centerY);
		// Top-right
		cs.curveTo(centerX + radius, centerY + c, centerX + c, centerY + radius, centerX, centerY + radius);
		// Top-left
		cs.curveTo(centerX - c, centerY + radius, centerX - radius, centerY + c, centerX - radius, centerY);
		// Bottom-left
		cs.curveTo(centerX - radius, centerY - c, centerX - c, centerY - radius, centerX, centerY - radius);
		// Bottom-right
		cs.curveTo(centerX + c, centerY - radius, centerX + radius, centerY - c, centerX + radius, centerY);
		cs.closePath();
		cs.fill();
		cs.restoreGraphicsState();
	}

	public void drawRectAngle(float x, float y, float width, float height, Color color, float lineWidth)
			throws IOException {
		contentStream.saveGraphicsState();
		contentStream.setStrokingColor(color);
		contentStream.setLineWidth(lineWidth);
		contentStream.addRect(x, y, width, height);
		contentStream.stroke();
	}

	public void drawSolidRectAngle(float x, float y, float width, float height, Color color) throws IOException {
		contentStream.saveGraphicsState();
		contentStream.setStrokingColor(color);
		contentStream.addRect(x, y, width, height);
		contentStream.fill();
	}

	public void drawRoundedRectangle(float x, float y, float width, float height, float radius) throws IOException {
		float k = 0.5522848f;
		float c = radius * k;
		contentStream.moveTo(x + radius, y);
		contentStream.lineTo(x + width - radius, y);
		// Bottom-right
		contentStream.curveTo(x + width - radius + c, y, x + width, y + radius - c, x + width, y + radius);
		contentStream.lineTo(x + width, y + height - radius);
		// Top-right
		contentStream.curveTo(x + width, y + height - radius + c, x + width - radius + c, y + height,
				x + width - radius, y + height);
		contentStream.lineTo(x + radius, y + height);
		// Top-left
		contentStream.curveTo(x + radius - c, y + height, x, y + height - radius + c, x, y + height - radius);
		contentStream.lineTo(x, y + radius);
		// Bottom-left
		contentStream.curveTo(x, y + radius - c, x + radius - c, y, x + radius, y);
		contentStream.closePath();
	}

	public void drawTopRoundedRect(float x, float y, float width, float height, float radius, Color color)
			throws IOException {

		contentStream.setNonStrokingColor(color);

		drawRoundedRectangle(x, y, width, height, radius);

		contentStream.fill();

		// Cover the bottom rounded portion so only
		// TOP corners remain rounded.
		contentStream.setNonStrokingColor(color);

		drawSolidRectAngle(x, y, width, radius, color);
	}

	public void drawBottomRoundedRect(float x, float y, float width, float height, float radius, Color color)
			throws IOException {

		contentStream.setNonStrokingColor(color);
		// Draw complete rounded rectangle
		drawRoundedRectangle(x, y, width, height, radius);
		contentStream.fill();

		// Cover the top rounded portion so only
		// BOTTOM corners remain rounded.
		contentStream.setNonStrokingColor(color);
		drawSolidRectAngle(x, y + height - radius, width, radius, color);
	}

	public void drawTopRoundedGradientRect(float x, float y, float width, float height, float radius, Color startColor,
			Color endColor, boolean horizontal) throws IOException {
		// Save current graphics state
		contentStream.saveGraphicsState();
		float k = 0.5522848f;
		float r = Math.min(radius, Math.min(width / 2f, height / 2f));
		float x0 = x;
		float y0 = y;
		float x1 = x + width;
		float y1 = y + height;
		contentStream.moveTo(x0, y0);
		// Bottom edge
		contentStream.lineTo(x1, y0);
		// Right edge
		contentStream.lineTo(x1, y1 - r);
		// Top-right rounded corner
		contentStream.curveTo(x1, y1 - r + r * k, x1 - r + r * k, y1, x1 - r, y1);
		// Top edge
		contentStream.lineTo(x0 + r, y1);
		// Top-left rounded corner
		contentStream.curveTo(x0 + r - r * k, y1, x0, y1 - r + r * k, x0, y1 - r);
		// Left edge
		contentStream.lineTo(x0, y0);
		contentStream.closePath();
		// Clip gradient to this shape
		contentStream.clip();
		// DRAW GRADIENT
		drawGradientRect(x, y, width, height, startColor, endColor, horizontal);
		// Restore clip
		contentStream.restoreGraphicsState();
	}

	public void drawText(float x, float y, String text, PDType0Font font, float fontSize, Color color)
			throws IOException {
		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.setNonStrokingColor(color);
		contentStream.newLineAtOffset(x, y);
		contentStream.showText(text);
		contentStream.endText();
	}

	public void drawCenteredText(String text, float left, float right, float y, PDType0Font font, float fontSize,
			Color color) throws IOException {
		float availableWidth = right - left;
		float textWidth = font.getStringWidth(text) / 1000f * fontSize;
		float x = left + (availableWidth - textWidth) / 2f;
		drawText(x, y, text, font, fontSize, color);
	}

	public void drawCellText(String text, PDType0Font font, float fontSize, float x, float y, float cellWidth,
			float cellHeight, Color color) throws IOException {

		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.setNonStrokingColor(color);
		float textHeight = fontSize;
		float textY = y + (cellHeight - textHeight) / 2f + 3f;
		contentStream.newLineAtOffset(x, textY);
		contentStream.showText(text);
		contentStream.endText();
	}

	public void drawBoldText(float x, float y, String text, PDType0Font font, float fontSize, Color color)
			throws IOException {
		contentStream.setNonStrokingColor(color);
		float offset = 0.40f;
		drawTextInternal(x, y, text, font, fontSize);
		drawTextInternal(x + offset, y, text, font, fontSize);
		drawTextInternal(x, y + offset, text, font, fontSize);
		drawTextInternal(x + offset, y + offset, text, font, fontSize);
	}

	public void drawCenteredBoldText(float boxX, float boxWidth, float y, String text, int fontSize, PDType0Font font,
			Color color) throws IOException {

		float textWidth = font.getStringWidth(text) / 1000f * fontSize;
		float x = boxX + (boxWidth - textWidth) / 2f;
		float offset = 0.15f;
		contentStream.setNonStrokingColor(color);
		drawTextInternal(x - offset, y, text, font, fontSize);
		drawTextInternal(x, y, text, font, fontSize);
		drawTextInternal(x + offset, y, text, font, fontSize);
	}

	private void drawTextInternal(float x, float y, String text, PDType0Font font, float fontSize) throws IOException {
		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.newLineAtOffset(x, y);
		contentStream.showText(text);
		contentStream.endText();
	}

	public void drawFourPointStar(float cx, float cy, float size, Color color) throws IOException {
		contentStream.setNonStrokingColor(color);
		contentStream.moveTo(cx, cy + size);
		contentStream.curveTo(cx + 2f, cy + 3f, cx + 3f, cy + 2f, cx + size, cy);
		contentStream.curveTo(cx + 3f, cy - 2f, cx + 2f, cy - 3f, cx, cy - size);
		contentStream.curveTo(cx - 2f, cy - 3f, cx - 3f, cy - 2f, cx - size, cy);
		contentStream.curveTo(cx - 3f, cy + 2f, cx - 2f, cy + 3f, cx, cy + size);
		contentStream.fill();
	}

	public void drawImage(String url) throws IOException {
		InputStream imageStream = getClass().getResourceAsStream(url);
		PDImageXObject image = PDImageXObject.createFromByteArray(document, imageStream.readAllBytes(), "ganeshji");
		float imgWidth = 150;
		float imgHeight = 150;
		float x = (pageWidth - imgWidth) / 2;
		float y = (pageHeight - imgHeight) / 2;
		contentStream.drawImage(image, x, y, imgWidth, imgHeight);
	}

	public void drawImage(String imagePath, float x, float y, float width, float height) throws IOException {
		InputStream imageStream = getClass().getResourceAsStream(imagePath);
		if (imageStream == null) {
			throw new FileNotFoundException("Image not found: " + imagePath);
		}
		try (InputStream is = imageStream) {
			byte[] imageBytes = is.readAllBytes();
			PDImageXObject image = PDImageXObject.createFromByteArray(document, imageBytes, imagePath);
			contentStream.drawImage(image, x, y, width, height);
		}
	}

	public void drawImage(String imagePath, float x, float y, float width) throws IOException {
		InputStream imageStream = getClass().getResourceAsStream(imagePath);
		if (imageStream == null) {
			throw new FileNotFoundException("Image not found: " + imagePath);
		}
		try (InputStream is = imageStream) {
			byte[] imageBytes = is.readAllBytes();
			PDImageXObject image = PDImageXObject.createFromByteArray(document, imageBytes, imagePath);
			float height = width * image.getHeight() / image.getWidth();
			contentStream.drawImage(image, x, y, width, height);
		}
	}

	public void drawLabelGradient(float x, float y, float width, float height, Color topColor, Color bottomColor)
			throws IOException {

		// Top = bright orange
		// Color topColor = new Color(255, 150, 0);

		// Bottom = deep orange/red
		// Color bottomColor = new Color(245, 70, 0);

		int steps = 50;
		float stripHeight = height / steps;
		for (int i = 0; i < steps; i++) {
			float ratio = i / (float) (steps - 1);
			int r = Math.round(topColor.getRed() + ratio * (bottomColor.getRed() - topColor.getRed()));
			int g = Math.round(topColor.getGreen() + ratio * (bottomColor.getGreen() - topColor.getGreen()));
			int b = Math.round(topColor.getBlue() + ratio * (bottomColor.getBlue() - topColor.getBlue()));
			contentStream.setNonStrokingColor(new Color(r, g, b));
			contentStream.addRect(x, y + (i * stripHeight), width, stripHeight + 0.3f);
			contentStream.fill();
		}
	}

	public void drawMultiLineCellText(PDPageContentStream cs, String[] lines, PDType0Font font, float fontSize, float x,
			float y, float cellWidth, float cellHeight, Color color) throws IOException {
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

	public void drawSvg(byte[] svgBytes, float x, float y, float width, float height, int corner) throws Exception {

		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
		SVGDocument svgDocument = factory.createSVGDocument(null, new ByteArrayInputStream(svgBytes));

		// Batik renderer
		UserAgentAdapter userAgent = new UserAgentAdapter();
		DocumentLoader loader = new DocumentLoader(userAgent);
		BridgeContext bridgeContext = new BridgeContext(userAgent, loader);
		bridgeContext.setDynamicState(BridgeContext.STATIC);
		GVTBuilder builder = new GVTBuilder();
		GraphicsNode graphicsNode = builder.build(bridgeContext, svgDocument);

		// 3. SVG bounds

		Rectangle2D bounds = graphicsNode.getBounds();
		if (bounds == null || bounds.getWidth() <= 0 || bounds.getHeight() <= 0) {
			throw new IllegalArgumentException("SVG has invalid bounds");
		}

		// 4. Scale

		double svgWidth = bounds.getWidth();
		double svgHeight = bounds.getHeight();
		double scaleX = width / svgWidth;
		double scaleY = height / svgHeight;
		double scale = Math.min(scaleX, scaleY);
		double finalWidth = svgWidth * scale;
		double finalHeight = svgHeight * scale;

		// 5. Create Graphics2D

		PdfBoxGraphics2D g2d = new PdfBoxGraphics2D(document, width, height);

		// 6. Scale SVG

		AffineTransform svgTransform = new AffineTransform();
		double offsetX = (width - finalWidth) / 2.0;
		double offsetY = (height - finalHeight) / 2.0;
		svgTransform.translate(offsetX, offsetY);
		svgTransform.scale(scale, scale);
		svgTransform.translate(-bounds.getX(), -bounds.getY());
		g2d.transform(svgTransform);

		// 7. Paint SVG

		graphicsNode.paint(g2d);

		// 8. Dispose FIRST

		g2d.dispose();

		// 9. Get form AFTER dispose

		PDFormXObject form = g2d.getXFormObject();

		// 10. Draw

		contentStream.saveGraphicsState();

		// TOP LEFT

		if (corner == 0) {
			contentStream.transform(new Matrix(1, 0, 0, 1, x, y));
		}

		// TOP RIGHT Horizontal flip

		else if (corner == 1) {
			contentStream.transform(new Matrix(-1, 0, 0, 1, x + width, y));
		}

		// BOTTOM RIGHT Horizontal + Vertical flip

		else if (corner == 2) {
			contentStream.transform(new Matrix(-1, 0, 0, -1, x + width, y + height));
		}

		// BOTTOM LEFT Vertical flip

		else if (corner == 3) {
			contentStream.transform(new Matrix(1, 0, 0, -1, x, y + height));
		}

		else {
			throw new IllegalArgumentException("corner must be 0, 1, 2 or 3");
		}

		// Draw SVG

		contentStream.drawForm(form);
		contentStream.restoreGraphicsState();

	}

	public void drawSvgExact(byte[] svgBytes, float x, float y, float width, float height) throws Exception {
		// 1. Parse SVG
		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
		SVGDocument svgDocument = factory.createSVGDocument(null, new ByteArrayInputStream(svgBytes));
		// 2. Create Batik rendering objects
		UserAgentAdapter userAgent = new UserAgentAdapter();
		DocumentLoader loader = new DocumentLoader(userAgent);
		BridgeContext bridgeContext = new BridgeContext(userAgent, loader);
		bridgeContext.setDynamicState(BridgeContext.STATIC);
		GVTBuilder builder = new GVTBuilder();
		GraphicsNode graphicsNode = builder.build(bridgeContext, svgDocument);
		// 3. Read SVG viewBox
		String viewBoxString = svgDocument.getRootElement().getAttributeNS(null, "viewBox");
		if (viewBoxString == null || viewBoxString.trim().isEmpty()) {
			throw new IllegalArgumentException("SVG must contain viewBox");
		}
		String[] values = viewBoxString.trim().split("[,\\s]+");
		if (values.length != 4) {
			throw new IllegalArgumentException("Invalid SVG viewBox: " + viewBoxString);
		}
		float viewBoxX = Float.parseFloat(values[0]);
		float viewBoxY = Float.parseFloat(values[1]);
		float viewBoxWidth = Float.parseFloat(values[2]);
		float viewBoxHeight = Float.parseFloat(values[3]);
		// 4. Validate
		if (viewBoxWidth <= 0 || viewBoxHeight <= 0) {
			throw new IllegalArgumentException("Invalid SVG viewBox dimensions");
		}
		// 5. Create PDF graphics surface
		PdfBoxGraphics2D g2d = new PdfBoxGraphics2D(document, width, height);
		// 6. Scale SVG VIEWBOX -> requested PDF size
		double scaleX = width / viewBoxWidth;
		double scaleY = height / viewBoxHeight;
		AffineTransform transform = new AffineTransform();
		// Move viewBox origin to 0,0
		transform.translate(-viewBoxX, -viewBoxY);
		// Scale to requested size
		transform.scale(scaleX, scaleY);
		g2d.transform(transform);
		// 7. Paint SVG
		graphicsNode.paint(g2d);
		// 8. Finish graphics
		g2d.dispose();
		PDFormXObject form = g2d.getXFormObject();
		// 9. Put SVG on PDF page
		contentStream.saveGraphicsState();
		contentStream.transform(Matrix.getTranslateInstance(x, y));
		contentStream.drawForm(form);
		contentStream.restoreGraphicsState();
	}

	public void drawExactSvg(byte[] svgBytes, float x, float y, float width, float height) throws Exception {

		// =========================================================
		// 1. Parse SVG
		// =========================================================

		String parser = XMLResourceDescriptor.getXMLParserClassName();

		SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);

		SVGDocument svgDocument = factory.createSVGDocument(null, new ByteArrayInputStream(svgBytes));

		// =========================================================
		// 2. Create Batik renderer
		// =========================================================

		UserAgentAdapter userAgent = new UserAgentAdapter();

		DocumentLoader loader = new DocumentLoader(userAgent);

		BridgeContext bridgeContext = new BridgeContext(userAgent, loader);

		bridgeContext.setDynamicState(BridgeContext.STATIC);

		GVTBuilder builder = new GVTBuilder();

		GraphicsNode graphicsNode = builder.build(bridgeContext, svgDocument);

		// =========================================================
		// 3. Get ACTUAL visible SVG bounds
		// =========================================================

		Rectangle2D bounds = graphicsNode.getBounds();

		if (bounds == null || bounds.getWidth() <= 0 || bounds.getHeight() <= 0) {

			throw new IllegalArgumentException("SVG has invalid bounds");
		}

		float svgWidth = (float) bounds.getWidth();

		float svgHeight = (float) bounds.getHeight();

		System.out.println("SVG visible size = " + svgWidth + " x " + svgHeight);

		System.out.println("Requested size = " + width + " x " + height);

		// =========================================================
		// 4. Create form using ORIGINAL SVG visible size
		// =========================================================

		PdfBoxGraphics2D g2d = new PdfBoxGraphics2D(document, svgWidth, svgHeight);

		// =========================================================
		// 5. Move visible SVG bounds to 0,0
		// =========================================================

		AffineTransform transform = new AffineTransform();

		transform.translate(-bounds.getX(), -bounds.getY());

		g2d.transform(transform);

		// =========================================================
		// 6. Paint SVG
		// =========================================================

		graphicsNode.paint(g2d);

		// =========================================================
		// 7. Finish form
		// =========================================================

		g2d.dispose();

		PDFormXObject form = g2d.getXFormObject();

		// =========================================================
		// 8. IMPORTANT:
		// Explicitly scale form to requested width/height
		// =========================================================

		float formWidth = form.getBBox().getWidth();

		float formHeight = form.getBBox().getHeight();

		float scaleX = width / formWidth;

		float scaleY = height / formHeight;

		// =========================================================
		// 9. Draw form
		// =========================================================

		contentStream.saveGraphicsState();

		Matrix matrix = new Matrix();

		matrix.translate(x, y);

		matrix.scale(scaleX, scaleY);

		contentStream.transform(matrix);

		contentStream.drawForm(form);

		contentStream.restoreGraphicsState();
	}

	public void drawExactSvgNew(byte[] svgBytes, float x, float y, float width, float height) throws Exception {

		// =========================================================
		// 1. Parse SVG
		// =========================================================

		String parser = XMLResourceDescriptor.getXMLParserClassName();

		SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);

		SVGDocument svgDocument = factory.createSVGDocument(null, new ByteArrayInputStream(svgBytes));

		// =========================================================
		// 2. Build Batik GraphicsNode
		// =========================================================

		UserAgentAdapter userAgent = new UserAgentAdapter();

		DocumentLoader loader = new DocumentLoader(userAgent);

		BridgeContext bridgeContext = new BridgeContext(userAgent, loader);

		bridgeContext.setDynamicState(BridgeContext.STATIC);

		GVTBuilder builder = new GVTBuilder();

		GraphicsNode graphicsNode = builder.build(bridgeContext, svgDocument);

		// =========================================================
		// 3. Get SVG visible bounds
		// =========================================================

		Rectangle2D bounds = graphicsNode.getBounds();

		if (bounds == null || bounds.getWidth() <= 0 || bounds.getHeight() <= 0) {

			throw new IllegalArgumentException("SVG has invalid bounds");
		}

		double svgWidth = bounds.getWidth();
		double svgHeight = bounds.getHeight();

		System.out.println("SVG bounds = " + svgWidth + " x " + svgHeight);

		System.out.println("Target = " + width + " x " + height);

		// =========================================================
		// 4. Create PDF graphics using SVG bounds
		// =========================================================

		PdfBoxGraphics2D g2d = new PdfBoxGraphics2D(document, (float) svgWidth, (float) svgHeight);

		// =========================================================
		// 5. Move SVG visible bounds to 0,0
		// =========================================================

		g2d.translate(-bounds.getX(), -bounds.getY());

		// =========================================================
		// 6. Paint SVG
		// =========================================================

		graphicsNode.paint(g2d);

		// =========================================================
		// 7. Finish PDF form
		// =========================================================

		g2d.dispose();

		PDFormXObject form = g2d.getXFormObject();

		// =========================================================
		// 8. Scale directly to requested size
		// =========================================================

		float scaleX = width / (float) svgWidth;

		float scaleY = height / (float) svgHeight;

		// =========================================================
		// 9. Draw form
		// =========================================================

		contentStream.saveGraphicsState();
		Matrix matrix = new Matrix();

		matrix.translate(x, y);

		matrix.scale(width / 1000f, height / 120f);

		contentStream.transform(matrix);

		contentStream.drawForm(form);

		contentStream.restoreGraphicsState();
	}

	public void drawGradientRect(float x, float y, float width, float height, Color startColor, Color endColor,
			boolean horizontal) throws IOException {
		// --------------------------------------------------------
		// Save current graphics state
		// ---------------------------------------------------------
		contentStream.saveGraphicsState();
		// ---------------------------------------------------------
		// CLIP ONLY TO GRADIENT RECTANGLE
		// ---------------------------------------------------------
		contentStream.addRect(x, y, width, height);
		contentStream.clip();
		// ---------------------------------------------------------
		// SHADING DICTIONARY
		// ---------------------------------------------------------
		COSDictionary shadingDictionary = new COSDictionary();
		shadingDictionary.setInt(COSName.SHADING_TYPE, 2);
		shadingDictionary.setItem(COSName.COLORSPACE, PDDeviceRGB.INSTANCE.getCOSObject());

		// ---------------------------------------------------------
		// GRADIENT COORDINATES
		// ---------------------------------------------------------
		COSArray coords = new COSArray();
		if (horizontal) {
			// LEFT -> RIGHT
			coords.add(new COSFloat(x));
			coords.add(new COSFloat(y));
			coords.add(new COSFloat(x + width));
			coords.add(new COSFloat(y));

		} else {

			// BOTTOM -> TOP
			coords.add(new COSFloat(x));
			coords.add(new COSFloat(y));
			coords.add(new COSFloat(x));
			coords.add(new COSFloat(y + height));
		}
		shadingDictionary.setItem(COSName.COORDS, coords);
		// ---------------------------------------------------------
		// FUNCTION
		// ---------------------------------------------------------
		COSDictionary functionDictionary = new COSDictionary();
		functionDictionary.setInt(COSName.FUNCTION_TYPE, 2);

		// Domain
		COSArray domain = new COSArray();
		domain.add(new COSFloat(0f));
		domain.add(new COSFloat(1f));

		functionDictionary.setItem(COSName.DOMAIN, domain);

		// ---------------------------------------------------------
		// C0
		// ---------------------------------------------------------
		COSArray c0 = new COSArray();
		c0.add(new COSFloat(startColor.getRed() / 255f));
		c0.add(new COSFloat(startColor.getGreen() / 255f));
		c0.add(new COSFloat(startColor.getBlue() / 255f));
		functionDictionary.setItem(COSName.C0, c0);
		// ---------------------------------------------------------
		// C1
		// ---------------------------------------------------------
		COSArray c1 = new COSArray();

		c1.add(new COSFloat(endColor.getRed() / 255f));
		c1.add(new COSFloat(endColor.getGreen() / 255f));
		c1.add(new COSFloat(endColor.getBlue() / 255f));
		functionDictionary.setItem(COSName.C1, c1);

		// ---------------------------------------------------------
		// N
		// ---------------------------------------------------------
		functionDictionary.setFloat(COSName.N, 1f);

		shadingDictionary.setItem(COSName.FUNCTION, functionDictionary);

		// ---------------------------------------------------------
		// EXTEND
		// ---------------------------------------------------------
		COSArray extend = new COSArray();
		extend.add(COSBoolean.TRUE);
		extend.add(COSBoolean.TRUE);
		shadingDictionary.setItem(COSName.EXTEND, extend);

		// ---------------------------------------------------------
		// CREATE SHADING
		// ---------------------------------------------------------
		PDShadingType2 shading = new PDShadingType2(shadingDictionary);
		// ---------------------------------------------------------
		// DRAW
		// ---------------------------------------------------------
		contentStream.shadingFill(shading);
		// ---------------------------------------------------------
		// RESTORE
		// ---------------------------------------------------------
		contentStream.restoreGraphicsState();
	}

	public void drawGradientHeader(float x, float y, float width, float height, float radius, Color startColor,
			Color middleColor, Color endColor, Color borderColor) throws IOException {

		// -------------------------------------------------
		// Gradient colors
		// -------------------------------------------------

		// -------------------------------------------------
		// Create rounded-top clipping path
		// -------------------------------------------------

		contentStream.saveGraphicsState();

		contentStream.moveTo(x, y);

		// Bottom-left
		contentStream.lineTo(x, y + height - radius);

		// Top-left rounded corner
		contentStream.curveTo(x, y + height - radius * 0.45f, x + radius * 0.45f, y + height, x + radius, y + height);

		// Top
		contentStream.lineTo(x + width - radius, y + height);

		// Top-right rounded corner
		contentStream.curveTo(x + width - radius * 0.45f, y + height, x + width, y + height - radius * 0.45f, x + width,
				y + height - radius);

		// Right side
		contentStream.lineTo(x + width, y);

		// Bottom
		contentStream.lineTo(x, y);

		contentStream.closePath();

		// Clip everything to the rounded shape
		contentStream.clip();

		// -------------------------------------------------
		// Gradient strips
		// -------------------------------------------------

		int steps = 100;

		float stripWidth = width / steps;

		for (int i = 0; i < steps; i++) {

			float t = (float) i / (steps - 1);

			Color color;

			// Left -> center -> right
			if (t < 0.5f) {

				float p = t / 0.5f;

				color = interpolateColor(startColor, middleColor, p);

			} else {

				float p = (t - 0.5f) / 0.5f;

				color = interpolateColor(middleColor, endColor, p);
			}

			contentStream.setNonStrokingColor(color);

			contentStream.addRect(x + i * stripWidth, y, stripWidth + 0.5f, height);

			contentStream.fill();
		}

		// -------------------------------------------------
		// Remove clipping
		// -------------------------------------------------

		contentStream.restoreGraphicsState();

		// -------------------------------------------------
		// Gold border
		// -------------------------------------------------

		contentStream.saveGraphicsState();

		contentStream.setStrokingColor(borderColor);

		contentStream.setLineWidth(1f);

		contentStream.moveTo(x, y);

		contentStream.lineTo(x, y + height - radius);

		contentStream.curveTo(x, y + height - radius * 0.45f, x + radius * 0.45f, y + height, x + radius, y + height);

		contentStream.lineTo(x + width - radius, y + height);

		contentStream.curveTo(x + width - radius * 0.45f, y + height, x + width, y + height - radius * 0.45f, x + width,
				y + height - radius);

		contentStream.lineTo(x + width, y);

		contentStream.lineTo(x, y);

		contentStream.closePath();

		contentStream.stroke();

		contentStream.restoreGraphicsState();
	}

	private Color interpolateColor(Color c1, Color c2, float ratio) {

		ratio = Math.max(0f, Math.min(1f, ratio));

		int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * ratio);

		int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * ratio);

		int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * ratio);

		return new Color(r, g, b);
	}

	public void drawRoundedBottomRectangle(float x, float y, float width, float height, float radius)
			throws IOException {
		contentStream.saveGraphicsState();
		// Start at top-left
		contentStream.moveTo(x, y + height);
		// Left side
		contentStream.lineTo(x, y + radius);
		// Bottom-left rounded corner
		contentStream.curveTo(x, y, x, y, x + radius, y);
		// Bottom line
		contentStream.lineTo(x + width - radius, y);
		// Bottom-right rounded corner
		contentStream.curveTo(x + width, y, x + width, y, x + width, y + radius);
		// Right side
		contentStream.lineTo(x + width, y + height);
		// IMPORTANT:
		// Do NOT lineTo(x, y + height)
		// because the top must remain open.
		contentStream.stroke();
		contentStream.restoreGraphicsState();
	}

	public void drawSolidBottomRoundedRectangle(float x, float y, float width, float height, float radius, Color color)
			throws IOException {
		contentStream.setStrokingColor(color);
		// contentStream.setLineWidth(.5f);
		contentStream.saveGraphicsState();
		contentStream.moveTo(x, y + height);
		contentStream.lineTo(x, y + radius);
		contentStream.curveTo(x, y, x, y, x + radius, y);
		contentStream.lineTo(x + width - radius, y);
		contentStream.curveTo(x + width, y, x + width, y, x + width, y + radius);
		contentStream.lineTo(x + width, y + height);
		contentStream.fill();
		contentStream.restoreGraphicsState();
	}

	public void drawCurvedSideDesign(float x, float y, float height) throws IOException {

		float radius = 18f;
		float k = 0.5522848f;

		contentStream.saveGraphicsState();

		contentStream.setStrokingColor(new Color(238, 165, 35));

		contentStream.setLineWidth(4f);
		contentStream.setLineCapStyle(1);
		contentStream.setLineJoinStyle(1);

		// --------------------------------------------
		// BOTTOM
		// --------------------------------------------

		contentStream.moveTo(x + radius, y);

		contentStream.curveTo(x + radius - radius * k, y, x, y + radius - radius * k, x, y + radius);

		// --------------------------------------------
		// VERTICAL
		// --------------------------------------------

		contentStream.lineTo(x, y + height - radius);

		// --------------------------------------------
		// TOP
		// --------------------------------------------

		contentStream.curveTo(x, y + height - radius + radius * k, x + radius - radius * k, y + height, x + radius,
				y + height);

		// --------------------------------------------
		// TOP EXTENSION
		// --------------------------------------------

		contentStream.lineTo(x + 55f, y + height);

		contentStream.stroke();

		contentStream.restoreGraphicsState();
	}

	public void drawDotLine(float x1, float y, float x2) throws IOException {

		contentStream.saveGraphicsState();

		contentStream.setStrokingColor(new Color(120, 120, 120));
		contentStream.setLineWidth(1f);

		// [dot length, gap length]
		contentStream.setLineDashPattern(new float[] { 1f, 4f }, 0);

		contentStream.moveTo(x1, y);
		contentStream.lineTo(x2, y);
		contentStream.stroke();

		contentStream.restoreGraphicsState();
	}
}
