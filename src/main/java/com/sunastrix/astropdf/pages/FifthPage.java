package com.sunastrix.astropdf.pages;

import java.awt.Color;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class FifthPage extends BasePage {
	public void drawPage(PDDocument document, PDType0Font poppinsRegularFont) {
		this.document = document;
		this.poppinsRegularFont = poppinsRegularFont;
		PDPage page = new PDPage(PDRectangle.A4);
		document.addPage(page);
		float cornerSize = 30f;
		pageWidth = page.getMediaBox().getWidth();
		pageHeight = page.getMediaBox().getHeight();

		try (PDPageContentStream cs = new PDPageContentStream(document, page)) {

			this.contentStream = cs;
			drawShape.initialize(pageHeight, pageWidth, document, cs);
			drawColorShape.initialize(pageHeight, pageWidth, document, cs);
			drawPageBorder(document, page);
			byte[] svgBytes = getClass().getResourceAsStream("/images/corner_left_top.svg").readAllBytes();
			float margin = 15f;
			drawColorShape.drawSvg(svgBytes, margin, pageHeight - margin - cornerSize, cornerSize, cornerSize, 0);
			drawColorShape.drawSvg(svgBytes, pageWidth - margin - cornerSize, pageHeight - margin - cornerSize,
					cornerSize, cornerSize, 1);
			drawColorShape.drawSvg(svgBytes, pageWidth - margin - cornerSize, margin, cornerSize, cornerSize, 2);
			drawColorShape.drawSvg(svgBytes, margin, margin, cornerSize, cornerSize, 3);
			drawHeader(pageWidth, pageHeight, poppinsRegularFont);

			// Draw Basic Details
			float tableX = 39f;
			float tableY = 340f;
			float tableWidth = 514f;
			float tableHeight = 354f;
			float radius = 8f;

			float bgX = tableX - 10f;
			float bgY = tableY - 8f;
			float bgWidth = tableWidth + 19f;
			float bgHeight = tableHeight + 37f;

			float headWidth = 170f;
			float headHeight = 30f;
			float headMargin = 15.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "Basic Detail", 14);

			drawPlanetsTable(poppinsRegularFont, poppinsRegularFont, tableX, tableY, tableWidth, tableHeight, radius);

			drawFooter(poppinsRegularFont, poppinsRegularFont, 15);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
	}

	private void drawPlanetsTable(PDType0Font regularFont, PDType0Font boldFont, float tableX, float tableY,
			float tableWidth, float tableHeight, float radius) throws IOException {

		// =========================================================

		float headerHeight = 26f;

		// =========================================================
		// COLUMN WIDTH
		// =========================================================

		float col1 = 125f;
		float col2 = 88f;
		float col3 = 105f;
		float col4 = 105f;
		float col5 = 91f;

		float x1 = tableX;
		float x2 = x1 + col1;
		float x3 = x2 + col2;
		float x4 = x3 + col3;
		float x5 = x4 + col4;
		float x6 = x5 + col5;

		// =========================================================
		// ROW
		// =========================================================

		int rowCount = 13;

		float rowHeight = ((tableHeight - headerHeight) / rowCount) - 1;

		// =========================================================
		// COLORS
		// =========================================================

		Color headerColor = new Color(235, 91, 0);
		Color headerBorderColor = new Color(205, 50, 0);
		Color outerBorderColor = new Color(215, 125, 40);
		Color gridColor = new Color(225, 215, 200);
		Color alternateRowColor = new Color(255, 250, 240);

		// =========================================================
		// PLANET DATA
		// =========================================================

		String[][] data = {

				{ "Ascendent", "Scorpio", "03-12-51", "Vishakha", "4" },

				{ "Sun", "Taurus", "25-24-56", "Mrigasira", "1" },

				{ "Moon", "Pisces", "21-17-54", "Revati", "2" },

				{ "Mars", "Aries", "22-31-09", "Bharani", "3" },

				{ "Mercury", "Gemini", "19-05-19", "Ardra", "4" },

				{ "Jupiter", "Cancer", "01-38-47", "Punarvasu", "4" },

				{ "Venus", "Cancer", "02-20-20", "Punarvasu", "4" },

				{ "Saturn", "Pisces", "18-45-01", "Revati", "1" },

				{ "Rahu", "Aquarius", "09-26-18", "Satabhisa", "1" },

				{ "Ketu", "Leo", "09-26-18", "Magha", "3" },

				{ "Uranus", "Taurus", "08-25-21", "Krittika", "4" },

				{ "Neptune", "Pisces", "09-55-59", "Uttarabhadra", "2" },

				{ "Pluto", "Capricorn", "10-48-27", "Sravana", "1" } };

		// ICONS

		String[] icons = {

				null, "/images/sun.png", "/images/moon.png", "/images/mars.png", "/images/mercury.png",
				"/images/jupiter.png", "/images/venus.png", "/images/saturn.png", "/images/rahu.png",
				"/images/ketu.png", "/images/uranus.png", "/images/neptune.png", "/images/pluto.png" };

		// SAVE STATE

		contentStream.saveGraphicsState();

		// ROUNDED CLIP

		// drawColorShape.drawRoundedRectangle(tableX, tableY, tableWidth, tableHeight,
		// radius);
		// contentStream.clip();

		// TABLE WHITE BACKGROUND

		contentStream.setNonStrokingColor(gridColor);
		drawColorShape.drawRoundedRectangle(tableX, tableY, tableWidth, tableHeight, radius);
		contentStream.fill();

		// ORANGE HEADER
		drawColorShape.drawTopRoundedRect(tableX, tableY + tableHeight - headerHeight, tableWidth, headerHeight, radius,
				headerColor);

		// HEADER BOTTOM LINE

		float headerBottomY = tableY + tableHeight - headerHeight;
		drawColorShape.drawLine(tableX, headerBottomY, tableX + tableWidth, headerBottomY, 0.8f, headerBorderColor);

		// ALTERNATE ROWS
		float rowY = tableY + tableHeight - headerHeight - rowHeight - 1;
		for (int i = 0; i < rowCount; i++) {

			if (i % 2 == 0) {
				contentStream.setNonStrokingColor(alternateRowColor);
				drawColorShape.drawSolidRectAngle(tableX, rowY, tableWidth, rowHeight, alternateRowColor);

			} else {
				contentStream.setNonStrokingColor(Color.WHITE);
				drawColorShape.drawSolidRectAngle(tableX, rowY, tableWidth, rowHeight, Color.WHITE);
			}
			rowY = rowY - rowHeight - 1;

		}

		// VERTICAL GRID LINES

		float[] verticalLines = { x2, x3, x4, x5 };

		for (float x : verticalLines) {
			drawColorShape.drawLine(x, tableY, x, tableY + tableHeight, 0.8f, gridColor);
		}

		// HEADER TEXT

		float headerTextY = tableY + tableHeight - 16.5f;

		drawColorShape.drawCenteredText("Planets", x1, x2, headerTextY, boldFont, 12f, Color.WHITE);
		drawColorShape.drawCenteredText("Rashi", x2, x3, headerTextY, boldFont, 12f, Color.WHITE);
		drawColorShape.drawCenteredText("Longitude", x3, x4, headerTextY, boldFont, 12f, Color.WHITE);
		drawColorShape.drawCenteredText("Nakshatra", x4, x5, headerTextY, boldFont, 12f, Color.WHITE);
		drawColorShape.drawCenteredText("Pada", x5, x6, headerTextY, boldFont, 12f, Color.WHITE);

		rowY = tableY + tableHeight - headerHeight - rowHeight - 1;
		for (int i = 0; i < data.length; i++) {

			float textY = rowY + (rowHeight / 2f) - 3;

			// PLANET ICON

			if (icons[i] != null) {
				float iconSize = 14f;
				float iconX = x1 + 8f;
				float iconY = rowY + (rowHeight - iconSize) / 2f;
				// drawShape.drawImage(document, contentStream, icons[i], iconX, iconY,
				// iconSize);
			}

			// =====================================================
			// PLANET NAME
			// =====================================================

			float planetX = icons[i] == null ? x1 + 10f : x1 + 28f;
			drawColorShape.drawText(planetX, textY, data[i][0], regularFont, 10f, Color.BLACK);
			// RASHI
			drawColorShape.drawCenteredText(data[i][1], x2, x3, textY, regularFont, 10f, Color.BLACK);
			// LONGITUDE
			drawColorShape.drawCenteredText(data[i][2], x3, x4, textY, regularFont, 10f, Color.BLACK);
			// NAKSHATRA
			drawColorShape.drawCenteredText(data[i][3], x4, x5, textY, regularFont, 10f, Color.BLACK);
			// PADA
			drawColorShape.drawCenteredText(data[i][4], x5, x6, textY, regularFont, 10f, Color.BLACK);
			rowY = rowY - rowHeight - 1;
		}

		// IMPORTANT:
		// REMOVE CLIP BEFORE DRAWING OUTER BORDER

		contentStream.restoreGraphicsState();
		// OUTER ROUNDED TABLE BORDER
		contentStream.setLineWidth(1.2f);
		contentStream.setStrokingColor(outerBorderColor);
		drawColorShape.drawRoundedRectangle(tableX, tableY, tableWidth, tableHeight, radius);
		contentStream.stroke();
		contentStream.restoreGraphicsState();
	}

}

class PlanetDetailItem {

	String imagePath;
	String title;
	String subtitle;
	Color accentColor;

	PlanetDetailItem(String imagePath, String title, String subtitle, Color accentColor) {

		this.imagePath = imagePath;
		this.title = title;
		this.subtitle = subtitle;
		this.accentColor = accentColor;
	}
}
