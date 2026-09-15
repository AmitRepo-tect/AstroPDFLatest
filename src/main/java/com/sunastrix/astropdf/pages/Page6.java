package com.sunastrix.astropdf.pages;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class Page6 extends BasePage {
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
			drawHeader(pageWidth, pageHeight, poppinsRegularFont,"foa'kksÙkjh varj n'kk ");

			// Draw Basic Details
			float tableX = 39f;
			float tableY = 480f;
			float tableWidth = 240f;
			float tableHeight = 220f;
			float radius = 8f;

			float bgX = tableX - 10f;
			float bgY = tableY - 8f;
			float bgWidth = tableWidth + 19f;
			float bgHeight = tableHeight + 37f;

			float headWidth = 170f;
			float headHeight = 30f;
			float headMargin = 15.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "Navamsa Chart", 12);
			svgBytes = getClass().getResourceAsStream("/images/lagna_chart_scaled.svg").readAllBytes();
			drawColorShape.drawExactSvg(svgBytes, tableX, tableY, tableWidth, tableHeight);
			tableX = 310f;
			tableY = 480f;
			tableWidth = 240f;
			tableHeight = 220f;
			radius = 8f;

			bgX = tableX - 10f;
			bgY = tableY - 8f;
			bgWidth = tableWidth + 19f;
			bgHeight = tableHeight + 37f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "Chandra Chart", 12);
			svgBytes = getClass().getResourceAsStream("/images/lagna_chart_scaled.svg").readAllBytes();
			drawColorShape.drawExactSvg(svgBytes, tableX, tableY, tableWidth, tableHeight);

			tableX = 39f;
			tableY = 90f;
			tableWidth = 514f;
			tableHeight = 325f;
			bgX = tableX - 10f;
			bgY = tableY - 8f;
			bgWidth = tableWidth + 19f;
			bgHeight = tableHeight + 37f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "Chandra Chart", 12);
			drawBhavTable(poppinsRegularFont, poppinsRegularFont, tableX, tableY, tableWidth, tableHeight, 5);

			drawFooter(poppinsRegularFont, poppinsRegularFont, 15);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
	}

	private void drawBhavTable(PDType0Font regularFont, PDType0Font boldFont, float tableX, float tableY,
			float tableWidth, float tableHeight, float radius) throws IOException {
		String[][] bhavData = {

				{ "1", "Scorpio", "03-12-51", "Scorpio", "03-12-51" },
				{ "2", "Sagittarius", "03-12-51", "Sagittarius", "03-12-51" },
				{ "3", "Capricorn", "03-12-51", "Capricorn", "03-12-51" },
				{ "4", "Aquarius", "03-12-51", "Aquarius", "03-12-51" },
				{ "5", "Pisces", "03-12-51", "Pisces", "03-12-51" }, { "6", "Aries", "03-12-51", "Aries", "03-12-51" },
				{ "7", "Taurus", "03-12-51", "Taurus", "03-12-51" },
				{ "8", "Gemini", "03-12-51", "Gemini", "03-12-51" },
				{ "9", "Cancer", "03-12-51", "Cancer", "03-12-51" }, { "10", "Leo", "03-12-51", "Leo", "03-12-51" },
				{ "11", "Virgo", "03-12-51", "Virgo", "03-12-51" },
				{ "12", "Libra", "03-12-51", "Libra", "03-12-51" } };

		// HEADER
		float headerHeight = 30f;
		// COLORS
		Color gradientEnd = new Color(255, 174, 35);
		Color gradientStart = new Color(235, 78, 0);
		Color headerBorderColor = new Color(205, 50, 0);
		Color outerBorderColor = new Color(215, 125, 40);
		Color gridColor = new Color(225, 215, 200);
		Color alternateRowColor = new Color(255, 250, 240);
		// ROW
		int rowCount = 12;
		float rowHeight = (tableHeight - headerHeight) / (rowCount + 1);
		// COLUMNS

		float colWidth = (tableWidth - 80) / 4f;

		// SAVE TABLE STATE
		contentStream.saveGraphicsState();

		// GRADIENT HEADER

		float headerY = tableY + tableHeight - headerHeight;
		drawColorShape.drawTopRoundedGradientRect(tableX, headerY, tableWidth, headerHeight, radius, gradientStart,
				gradientEnd, true);

		// =========================================================
		// HEADER BOTTOM LINE
		// =========================================================
		float headerBottomY = tableY + tableHeight - headerHeight;
		drawColorShape.drawLine(tableX, headerBottomY, tableX + tableWidth, headerBottomY, 0.8f, headerBorderColor);

		// =========================================================
		// BODY ROWS
		// =========================================================

		float rowY = tableY + tableHeight - headerHeight - rowHeight - 1;
		for (int i = 0; i < rowCount; i++) {

			if (i % 2 == 0) {
				contentStream.setNonStrokingColor(alternateRowColor);
				drawColorShape.drawSolidRectAngle(tableX, rowY, tableWidth, rowHeight, alternateRowColor);

			} else {
				contentStream.setNonStrokingColor(Color.white);
				drawColorShape.drawSolidRectAngle(tableX, rowY, tableWidth, rowHeight, Color.white);
			}
			if (i < rowCount - 1) {
				drawColorShape.drawLine(tableX, rowY - 1, tableX + tableWidth, rowY - 1, 1f, gridColor);
				rowY = rowY - rowHeight - 2;
			}

		}

		// =========================================================
		// VERTICAL LINES
		// =========================================================
		System.out.println("colWidth-" + colWidth);
		float x1 = tableX + 80;
		float x2 = x1 + colWidth;
		float x3 = x2 + colWidth;
		float x4 = x3 + colWidth;
		float x5 = tableX + tableWidth;
		drawColorShape.drawLine(x1, tableY, x1, tableY + tableHeight, 0.5f, gridColor);
		drawColorShape.drawLine(x2, tableY, x2, tableY + tableHeight, 0.5f, gridColor);
		drawColorShape.drawLine(x3, tableY, x3, tableY + tableHeight, 0.5f, gridColor);
		drawColorShape.drawLine(x4, tableY, x4, tableY + tableHeight, 0.5f, gridColor);

		// =========================================================
		// HEADER TEXT
		// =========================================================

		float headerTextY = headerY + (headerHeight / 2f) - 4f;
		drawColorShape.drawCenteredText("Bhav", tableX, x1, headerTextY, boldFont, 10f, Color.WHITE);
		drawColorShape.drawCenteredText("Rashi", x1, x2, headerTextY, boldFont, 10f, Color.WHITE);
		drawColorShape.drawCenteredText("Bhav Begin", x2, x3, headerTextY, boldFont, 10f, Color.WHITE);
		drawColorShape.drawCenteredText("Sign", x3, x4, headerTextY, boldFont, 10f, Color.WHITE);
		drawColorShape.drawCenteredText("Mid Bhav", x4, x5, headerTextY, boldFont, 10f, Color.WHITE);

		rowY = tableY + tableHeight - headerHeight - rowHeight - 1;
		float textY = rowY + (rowHeight / 2f) - 3;
		for (int i = 0; i < bhavData.length; i++) {

			drawColorShape.drawCenteredText(bhavData[i][0], tableX, x1, textY, regularFont, 10f, Color.BLACK);
			drawColorShape.drawCenteredText(bhavData[i][1], x1, x2, textY, regularFont, 10f, Color.BLACK);
			drawColorShape.drawCenteredText(bhavData[i][2], x2, x3, textY, regularFont, 10f, Color.BLACK);
			drawColorShape.drawCenteredText(bhavData[i][3], x3, x4, textY, regularFont, 10f, Color.BLACK);
			drawColorShape.drawCenteredText(bhavData[i][4], x4, x5, textY, regularFont, 10f, Color.BLACK);
			textY -= (rowHeight+2);
		}

		// =========================================================
		// RESTORE TABLE CLIP
		// =========================================================

		contentStream.restoreGraphicsState();

		// =========================================================
		// OUTER BORDER
		// =========================================================

		contentStream.saveGraphicsState();
		contentStream.setStrokingColor(outerBorderColor);
		contentStream.setLineWidth(1.2f);
		drawColorShape.drawRoundedRectangle(tableX, tableY, tableWidth, tableHeight, radius);
		contentStream.stroke();
		contentStream.restoreGraphicsState();
	}

}
