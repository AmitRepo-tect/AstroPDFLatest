package com.sunastrix.astropdf.util;

import java.awt.Color;
import java.io.File;
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
}
