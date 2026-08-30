package com.sunastrix.astropdf.util;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class DrawShape {
	float pageHeight;
	float pageWidth;
	PDDocument document;
	PDPageContentStream contentStream;

	/*
	 * public PDType0Font poppinsRegularFont; public PDType0Font
	 * krutiDevRegularFont; public PDType0Font krutiDevBoldFont;
	 */
	public void initialize(float pageHeight, float pageWidth, PDDocument document, PDPageContentStream contentStream) {
		this.pageHeight = pageHeight;
		this.pageWidth = pageWidth;
		this.document = document;
		this.contentStream = contentStream;

	}

	/*
	 * public PDType0Font getKrutiDevRegularFont(){ return krutiDevRegularFont; }
	 */

	public void drawSolidRect(float x, float y, float height, float width, int rowCount) throws IOException {
		contentStream.setStrokingColor(Color.black);
		contentStream.setNonStrokingColor(Color.WHITE);
		contentStream.addRect(x, y, width, height);
		contentStream.fill();
		contentStream.moveTo(x, y);
		contentStream.lineTo(x, y + height);
		contentStream.lineTo(x + width, y + height);
		contentStream.lineTo(x + width, y);
		contentStream.lineTo(x, y);
		contentStream.closePath();
		contentStream.stroke();

		drawRowAndColoum(x, y, height, width, rowCount, 25);
	}

	public void drawBorder(float pageWidth, float pageHeight) throws IOException {

		float borderPadding = 10f;
		contentStream.setLineWidth(2.5f);
		contentStream.setStrokingColor(new Color(110, 35, 35));
		contentStream.addRect(borderPadding, borderPadding, pageWidth - (2 * borderPadding),
				pageHeight - (2 * borderPadding));
		contentStream.stroke();

		float goldPadding = 15f;
		float cornerLength = 18f;
		contentStream.setLineWidth(2.5f);
		contentStream.setStrokingColor(new Color(190, 165, 90));

		// TOP LEFT
		contentStream.moveTo(goldPadding, pageHeight - goldPadding - cornerLength);
		contentStream.lineTo(goldPadding, pageHeight - goldPadding);
		contentStream.lineTo(goldPadding + cornerLength, pageHeight - goldPadding);
		contentStream.stroke();

		// TOP RIGHT
		contentStream.moveTo(pageWidth - goldPadding - cornerLength, pageHeight - goldPadding);
		contentStream.lineTo(pageWidth - goldPadding, pageHeight - goldPadding);
		contentStream.lineTo(pageWidth - goldPadding, pageHeight - goldPadding - cornerLength);
		contentStream.stroke();

		// BOTTOM LEFT
		contentStream.moveTo(goldPadding, goldPadding + cornerLength);
		contentStream.lineTo(goldPadding, goldPadding);
		contentStream.lineTo(goldPadding + cornerLength, goldPadding);
		contentStream.stroke();

		// BOTTOM RIGHT
		contentStream.moveTo(pageWidth - goldPadding - cornerLength, goldPadding);
		contentStream.lineTo(pageWidth - goldPadding, goldPadding);
		contentStream.lineTo(pageWidth - goldPadding, goldPadding + cornerLength);
		contentStream.stroke();
	}

	public void drawStar(float cx, float cy, float size, Color color) throws IOException {
		contentStream.setNonStrokingColor(color);
		contentStream.moveTo(cx, cy + size);
		contentStream.lineTo(cx + size * 0.30f, cy + size * 0.30f);
		contentStream.lineTo(cx + size, cy);
		contentStream.lineTo(cx + size * 0.30f, cy - size * 0.30f);
		contentStream.lineTo(cx, cy - size);
		contentStream.lineTo(cx - size * 0.30f, cy - size * 0.30f);
		contentStream.lineTo(cx - size, cy);
		contentStream.lineTo(cx - size * 0.30f, cy + size * 0.30f);
		contentStream.closePath();
		contentStream.fill();
	}

	public void drawRowAndColoum(float x, float y, float height, float width, int rowCount, int rowHeight) {

		float yAxis = y + rowHeight;
		try {
			contentStream.setNonStrokingColor(Color.black);
			for (int i = 0; i < rowCount; i++) {
				drawLine(x, yAxis, x + width, yAxis);
				yAxis += rowHeight;
			}
			// drawLine(x + 120, y + (rowCount + 1) * 30, x + 120, y);
		} catch (Exception e) {

		}

	}

	public void drawLine(float x1, float y1, float x2, float y2) throws IOException {
		contentStream.moveTo(x1, y1);
		contentStream.lineTo(x2, y2);
		contentStream.stroke();
	}

	public void drawLine(float x1, float y1, float x2, float y2, float lineWidth) throws IOException {
		contentStream.setLineWidth(lineWidth);
		contentStream.moveTo(x1, y1);
		contentStream.lineTo(x2, y2);
		contentStream.stroke();
	}

	public void drawText(float x, float y, String text, int fontSize, PDType0Font font) throws IOException {
		contentStream.setNonStrokingColor(Color.BLACK); // Set fill color
		contentStream.setStrokingColor(Color.BLACK);
		// PDType0Font font = PDType0Font.load(document, new
		// File("src/main/resources/fonts/poppins_regular.ttf"));
		contentStream.beginText();
		contentStream.setFont(font, fontSize); // Set font and size
		contentStream.newLineAtOffset(x, y); // Set position
		contentStream.showText(text); // Display text
		contentStream.endText();
	}

	public void drawText(float x, float y, String text, int fontSize, PDType0Font font, Color color)
			throws IOException {
		contentStream.setNonStrokingColor(color); // Set fill color
		contentStream.setStrokingColor(color);
		// PDType0Font font = PDType0Font.load(document, new
		// File("src/main/resources/fonts/poppins_regular.ttf"));
		contentStream.beginText();
		contentStream.setFont(font, fontSize); // Set font and size
		contentStream.newLineAtOffset(x, y); // Set position
		contentStream.showText(text); // Display text
		contentStream.endText();
	}

	public void drawText(String text, float x, float y, float fontSize, Color color, PDType0Font font)
			throws IOException {
		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.setNonStrokingColor(color);
		contentStream.newLineAtOffset(x, y);
		contentStream.showText(text);
		contentStream.endText();
	}

	/*
	 * public void drawBoldText( float x, float y, String text, int fontSize,
	 * PDType0Font font, Color color ) throws IOException {
	 * 
	 * contentStream.setNonStrokingColor(color);
	 * 
	 * // First draw contentStream.beginText(); contentStream.setFont(font,
	 * fontSize); contentStream.newLineAtOffset(x, y); contentStream.showText(text);
	 * contentStream.endText();
	 * 
	 * // Second draw with tiny offset contentStream.beginText();
	 * contentStream.setFont(font, fontSize); contentStream.newLineAtOffset(x +
	 * 0.40f, y); contentStream.showText(text); contentStream.endText(); }
	 */
	public void drawBoldText(float x, float y, String text, int fontSize, PDType0Font font, Color color)
			throws IOException {

		contentStream.setNonStrokingColor(color);
		float offset = 0.40f;
		// Main
		drawTextInternal(x, y, text, fontSize, font);
		// Right
		drawTextInternal(x + offset, y, text, fontSize, font);
		// Slightly up
		drawTextInternal(x, y + offset, text, fontSize, font);
		// Up + right
		drawTextInternal(x + offset, y + offset, text, fontSize, font);
	}

	private void drawTextInternal(float x, float y, String text, int fontSize, PDType0Font font) throws IOException {

		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.newLineAtOffset(x, y);
		contentStream.showText(text);
		contentStream.endText();
	}

	public void drawTextHorizontalCenter(float y, String text, int fontSize, PDType0Font font) throws IOException {
		float textWidth = font.getStringWidth(text) / 1000 * fontSize;
		float x = (pageWidth - textWidth) / 2;
		drawText(x, y, text, fontSize, font);
	}

	public void drawTextHorizontalCenter(float y, String text, int fontSize, PDType0Font font, Color color)
			throws IOException {
		float textWidth = font.getStringWidth(text) / 1000 * fontSize;
		float x = (pageWidth - textWidth) / 2;
		drawBoldText(x, y, text, fontSize, font, color);
	}

	public void drawColorTextHorizontalCenter(float y, String text, int fontSize, PDType0Font font, Color color)
			throws IOException {
		float textWidth = font.getStringWidth(text) / 1000 * fontSize;
		float x = (pageWidth - textWidth) / 2;
		drawText(x, y, text, fontSize, font, color);
	}

	/*
	 * public void drawKrutiDevText(float x, float y, String text, int fontSize)
	 * throws IOException { contentStream.setNonStrokingColor(Color.BLACK); // Set
	 * fill color contentStream.setStrokingColor(Color.BLACK); //PDType0Font font =
	 * PDType0Font.load(document, new
	 * File("src/main/resources/fonts/Kruti-Dev-Regular.ttf"));
	 * contentStream.beginText(); contentStream.setFont(krutiDevRegularFont,
	 * fontSize); // Set font and size contentStream.newLineAtOffset(x, y); // Set
	 * position contentStream.showText(text); // Display text
	 * contentStream.endText(); }
	 * 
	 * public void drawKrutiDevBoldText(float x, float y, String text, int fontSize)
	 * throws IOException { contentStream.setNonStrokingColor(Color.BLACK); // Set
	 * fill color contentStream.setStrokingColor(Color.BLACK); //PDType0Font font =
	 * PDType0Font.load(document, new
	 * File("src/main/resources/fonts/kruti_dev_bold.ttf"));
	 * contentStream.beginText(); contentStream.setFont(krutiDevBoldFont, fontSize);
	 * // Set font and size contentStream.newLineAtOffset(x, y); // Set position
	 * contentStream.showText(text); // Display text contentStream.endText(); }
	 */

	public void drawTopRoundedRectangle(float x, float y, float width, float height) throws IOException {
		float k = 0.552284749831f;
		int radius = 2;
		contentStream.setLineWidth(1f);
		contentStream.setStrokingColor(Color.black);
		contentStream.setNonStrokingColor(Color.WHITE);
		// Start from top-left
		contentStream.moveTo(x, y + height);
		// Top line
		contentStream.lineTo(x + width, y + height);
		// Right line
		contentStream.lineTo(x + width, y + radius);
		// Bottom-right corner
		contentStream.curveTo(x + width, y + radius - radius * k, x + width - radius + radius * k, y,
				x + width - radius, y);
		// Bottom line
		contentStream.lineTo(x + radius, y);
		// Bottom-left corner
		contentStream.curveTo(x + radius - radius * k, y, x, y + radius - radius * k, x, y + radius);
		// Left line
		contentStream.lineTo(x, y + height);
		contentStream.closePath();
		contentStream.stroke();
	}

	public void drawRoundedRectangle(float x, float y, float height, float width) throws IOException {
		float k = 0.552284749831f;
		int radius = 6;
		contentStream.setLineWidth(1f);
		contentStream.setStrokingColor(0, 0, 0);

		// Start point
		contentStream.moveTo(x + radius, y);

		// Bottom line
		contentStream.lineTo(x + width - radius, y);
		// Bottom-right corner
		contentStream.curveTo(x + width - radius + radius * k, y, x + width, y + radius - radius * k, x + width,
				y + radius);

		// Right line
		contentStream.lineTo(x + width, y + height - radius);
		// Top-right corner
		contentStream.curveTo(x + width, y + height - radius + radius * k, x + width - radius + radius * k, y + height,
				x + width - radius, y + height);

		// Top line
		contentStream.lineTo(x + radius, y + height);
		// Top-left corner
		contentStream.curveTo(x + radius - radius * k, y + height, x, y + height - radius + radius * k, x,
				y + height - radius);

		// Left line
		contentStream.lineTo(x, y + radius);
		// Bottom-left corner
		contentStream.curveTo(x, y + radius - radius * k, x + radius - radius * k, y, x + radius, y);

		contentStream.closePath();
		contentStream.stroke();
	}

	public void drawKundli(float ph, float pw, float x, float y) {
		try {

			float rh = 170;
			float rw = 170;
			drawRectWithSolid(rh, rw, x, y);
			drawLine(x, y, x + rh, y + rh);
			drawLine(x, y + rh, x + rh, y);
			drawLine(x, y + rh / 2, x + rw / 2, y + rh);
			drawLine(x, y + rh / 2, x + rw / 2, y);
			drawLine(x + rw / 2, y + rh, x + rw, y + rh / 2);
			drawLine(x + rw, y + rh / 2, x + rw / 2, y);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void drawRectWithSolid(float rh, float rw, float x, float y) throws IOException {
		// contentStream.setNonStrokingColor(Color.WHITE); // Set fill color
		contentStream.setStrokingColor(Color.BLACK);

		// Draw filled rectangle
		contentStream.addRect(x, y, rh, rw);
		// contentStream.fill();
		contentStream.moveTo(x, y);
		contentStream.lineTo(x, y + rh);
		contentStream.lineTo(x + rw, y + rh);
		contentStream.lineTo(x + rw, y);
		contentStream.lineTo(x, y);
		contentStream.closePath();
		contentStream.stroke();
	}

	public void drawTableHeader(float x, float y, float height, float width) throws IOException {
		float k = 0.552284749831f;
		int radius = 6;
		contentStream.setLineWidth(1f);
		contentStream.setStrokingColor(Color.black);
		contentStream.setNonStrokingColor(Color.LIGHT_GRAY);
		contentStream.moveTo(x + radius, y);
		// Bottom line
		contentStream.lineTo(x + width, y);
		// Bottom-right corner
		contentStream.curveTo(x + width, y, x + width, y, x + width, y);
		// Right line
		contentStream.lineTo(x + width, y + height - radius);
		// Top-right corner
		contentStream.curveTo(x + width, y + height - radius + radius * k, x + width - radius + radius * k, y + height,
				x + width - radius, y + height);
		// Top line
		contentStream.lineTo(x + radius, y + height);
		// Top-left corner
		contentStream.curveTo(x + radius - radius * k, y + height, x, y + height - radius + radius * k, x,
				y + height - radius);
		// Left line
		contentStream.lineTo(x, y);
		// Bottom-left corner
		contentStream.curveTo(x, y, x, y, x, y);
		contentStream.closePath();
		contentStream.fillAndStroke();

	}

	public void drawTableWithHeader(float x, float y, float height, float width, int rowHeight) throws IOException {
		float k = 0.552284749831f;
		int radius = 6;
		// drawTableHeader(x, y + 390, 25, width);

		contentStream.setLineWidth(.5f);
		contentStream.setStrokingColor(Color.black);
		contentStream.setNonStrokingColor(Color.WHITE);
		// Start from top-left
		contentStream.moveTo(x, y + height - rowHeight);
		// Top line
		contentStream.lineTo(x + width, y + height - rowHeight);
		// Right line
		contentStream.lineTo(x + width, y + radius);
		// Bottom-right corner
		contentStream.curveTo(x + width, y + radius - radius * k, x + width - radius + radius * k, y,
				x + width - radius, y);
		// Bottom line
		contentStream.lineTo(x + radius, y);
		// Bottom-left corner
		contentStream.curveTo(x + radius - radius * k, y, x, y + radius - radius * k, x, y + radius);
		// Left line
		contentStream.lineTo(x, y + height - rowHeight);
		contentStream.closePath();
		contentStream.stroke();

	}

	public void drawRaw(float x, float y, float height, float width, int rowCount, int rowHeight) {

		float yAxis = y + rowHeight;
		try {
			contentStream.setNonStrokingColor(Color.black);
			for (int i = 0; i < rowCount; i++) {
				drawLine(x, yAxis, x + width, yAxis);
				yAxis += rowHeight;
			}
			// drawLine(x + 120, y + (rowCount + 1) * 30, x + 120, y);
		} catch (Exception e) {

		}
	}

	public void drawColumn(ArrayList<AxisPoint> points) throws IOException {
		for (int i = 0; i < points.size(); i++) {
			drawLine(points.get(i).startX, points.get(i).startY, points.get(i).endX, points.get(i).endY);
		}
	}

	public void drawImage() throws IOException {
		InputStream imageStream = getClass().getResourceAsStream("/images/ganeshji.png");
		PDImageXObject image = PDImageXObject.createFromByteArray(document, imageStream.readAllBytes(), "ganeshji");
		float imgWidth = 400;
		float imgHeight = 400;
		float x = (pageWidth - imgWidth) / 2;
		float y = (pageHeight - imgHeight) / 2;
		contentStream.drawImage(image, x, y, imgWidth, imgHeight);
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

	public void drawColorImage() throws IOException {
		InputStream imageStream = getClass().getResourceAsStream("/images/ganeshji_color.jpg");
		if (imageStream == null) {
			throw new FileNotFoundException("Ganeshji image not found: /images/ganeshji.png");
		}
		byte[] imageBytes = imageStream.readAllBytes();
		PDImageXObject image = PDImageXObject.createFromByteArray(document, imageBytes, "ganeshji");
		float imgWidth = 140f;
		float imgHeight = imgWidth * image.getHeight() / image.getWidth();
		float x = (pageWidth - imgWidth) / 2f;
		float y = pageHeight - 370f;
		contentStream.drawImage(image, x, y, imgWidth, imgHeight);
		float diameter = Math.max(imgWidth, imgHeight) + 40f;
		float circleX = (pageWidth - diameter) / 2f;
		float circleY = y - (diameter - imgHeight) / 2f;
		drawEllipse(circleX, circleY, diameter, diameter);
		imageStream.close();
	}

	public void drawEllipse(float x, float y, float width, float height) throws IOException {
		final float k = 0.5522848f;
		float ox = (width / 2f) * k;
		float oy = (height / 2f) * k;
		float x0 = x;
		float y0 = y;
		float x1 = x + width;
		float y1 = y + height;
		float cx = x + width / 2f;
		float cy = y + height / 2f;
		contentStream.setStrokingColor(new Color(110, 35, 35));
		contentStream.moveTo(cx, y0);
		// Bottom → Right
		contentStream.curveTo(cx + ox, y0, x1, cy - oy, x1, cy);
		// Right → Top
		contentStream.curveTo(x1, cy + oy, cx + ox, y1, cx, y1);
		// Top → Left
		contentStream.curveTo(cx - ox, y1, x0, cy + oy, x0, cy);
		// Left → Bottom
		contentStream.curveTo(x0, cy - oy, cx - ox, y0, cx, y0);
		contentStream.closePath();
		contentStream.stroke();
	}

	public void drawRoundedRect(float x, float y, float width, float height, float radius, Color color)
			throws IOException {

		float k = 0.5522848f;

		float c = radius * k;

		contentStream.setNonStrokingColor(color);

		contentStream.moveTo(x + radius, y);

		// Bottom
		contentStream.lineTo(x + width - radius, y);

		// Bottom-right corner
		contentStream.curveTo(x + width - radius + c, y, x + width, y + radius - c, x + width, y + radius);

		// Right
		contentStream.lineTo(x + width, y + height - radius);

		// Top-right corner
		contentStream.curveTo(x + width, y + height - radius + c, x + width - radius + c, y + height,
				x + width - radius, y + height);

		// Top
		contentStream.lineTo(x + radius, y + height);

		// Top-left corner
		contentStream.curveTo(x + radius - c, y + height, x, y + height - radius + c, x, y + height - radius);

		// Left
		contentStream.lineTo(x, y + radius);

		// Bottom-left corner
		contentStream.curveTo(x, y + radius - c, x + radius - c, y, x + radius, y);

		contentStream.closePath();
		contentStream.fill();
	}

	public void drawCenteredText(float boxX, float boxWidth, float y, String text, int fontSize, PDType0Font font,
			Color color) throws IOException {

		float textWidth = font.getStringWidth(text) / 1000f * fontSize;

		float x = boxX + (boxWidth - textWidth) / 2f;

		drawText(x, y, text, fontSize, font, color);
	}

	public void drawCenteredBoldText(float boxX, float boxWidth, float y, String text, int fontSize, PDType0Font font,
			Color color) throws IOException {

		float textWidth = font.getStringWidth(text) / 1000f * fontSize;
		float x = boxX + (boxWidth - textWidth) / 2f;
		float offset = 0.20f;
		contentStream.setNonStrokingColor(color);
		drawTextInternal(x - offset, y, text, fontSize, font);
		drawTextInternal(x, y, text, fontSize, font);
		drawTextInternal(x + offset, y, text, fontSize, font);
	}

	public void drawDottedLine(float x1, float y1, float x2, float y2) throws IOException {
		contentStream.setLineWidth(1f);
		// [dot length, gap length]
		contentStream.setLineDashPattern(new float[] { 1f, 3f }, 0);

		contentStream.moveTo(x1, y1);
		contentStream.lineTo(x2, y2);
		contentStream.stroke();
		// Reset to solid line
		contentStream.setLineDashPattern(new float[] {}, 0);
	}

	private void drawImage(PDDocument document, PDPageContentStream contentStream, String imagePath, float x, float y,
			float width, float height) throws IOException {

		// Load image from resources
		InputStream imageStream = getClass().getResourceAsStream(imagePath);

		if (imageStream == null) {
			throw new FileNotFoundException("Image not found: " + imagePath);
		}

		try (InputStream is = imageStream) {

			byte[] imageBytes = is.readAllBytes();

			// Create PDF image
			PDImageXObject image = PDImageXObject.createFromByteArray(document, imageBytes, imagePath);

			// Draw image
			contentStream.drawImage(image, x, y, width, height);
		}
	}

	public void drawImage(PDDocument document, PDPageContentStream contentStream, String imagePath, float x, float y,
			float width) throws IOException {

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
}
