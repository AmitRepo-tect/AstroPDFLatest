package com.sunastrix.astropdf.pages;

import java.awt.Color;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class SecondPage extends BasePage {

	public void drawPage(PDDocument document, PDType0Font poppinsRegularFont) throws IOException {
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
			float tableX = 40f;
			float tableY = 508f;
			float tableWidth = 495f;
			float tableHeight = 198f;
			int totalRows = 5;

			float bgX = tableX - 10f;
			float bgY = tableY - 8f;
			float bgWidth = tableWidth + 19f;
			float bgHeight = tableHeight + 37f;

			float headWidth = 170f;
			float headHeight = 38f;
			float headMargin = 19.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "Basic Detail", 14);
			String[][] data = { { "Name", "jitendra", "Date", "14-8-1994" }, { "Time", "15:45:0", "Timezone", "5.5" },
					{ "Day", "Sunday", "Longitude", "25.16.E" }, { "Place", "chandauli", "Sunrise", "05/29/53" },
					{ "Latitude", "83.16.N", "Sunset", "18/33/26" } };
			drawBasicDetailsTable(tableX, tableY, tableWidth, tableHeight, poppinsRegularFont, poppinsRegularFont, data,
					totalRows);
			// Draw Avakahada detail
			float firstBgBottom = bgY;
			float sectionGap = 40f;
			float secondBgHeight = 282f;
			float secondBgY = firstBgBottom - sectionGap - secondBgHeight;
			float secondBgX = 35f;
			float secondBgWidth = 514f;
			drawBgWithHeader(secondBgX, secondBgY, secondBgWidth, secondBgHeight, headWidth, headHeight, headMargin,
					"Avakhada Detail", 14);
			float secondTableX = secondBgX + 10f;
			float secondTableY = secondBgY + 8f;
			float secondTableWidth = secondBgWidth - 19f;
			float secondTableHeight = secondBgHeight - 37f;
			int secondTotalRows = 7;
			String[][] avakhadaDetails = { { "Paya", "Swarna", "Lagna", "Sagittarius" },
					{ "Varna", "Sudra", "Lagna Lord", "JUP" }, { "Yoni", "Vyagh", "Rasi", "Libra" },
					{ "Gana", "Rakshasa", "Rasi Lord", "VEN" },
					{ "Vashya", "Manav", "Nakshatra Pada", "Purvashadha-3" },
					{ "Nadi", "Antya", "Nakshatra Lord", "JUP" },
					{ "Balance Dasha", "Jupiter 4 Y 0 M 15 D", "SunSign (Indian)", "Leo" } };

			drawBasicDetailsTable(secondTableX, secondTableY, secondTableWidth, secondTableHeight, poppinsRegularFont,
					poppinsRegularFont, avakhadaDetails, secondTotalRows);
			drawFooter(poppinsRegularFont, poppinsRegularFont, 15);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawBasicDetailsTable(float xaxis, float yaxis, float width, float height, PDType0Font labelFont,
			PDType0Font valueFont, String[][] data, int totalRows) throws IOException {

		// =====================================================
		// TABLE POSITION
		// =====================================================
		float tableX = xaxis;
		float tableY = yaxis;
		float tableWidth = width;
		float tableHeight = height;

		// =====================================================
		// COLUMN WIDTHS
		// =====================================================
		float col1 = 102f;
		float col2 = 144f;
		float col3 = 102f;
		float col4 = 142f;

		// =====================================================
		// ROW
		// =====================================================
		float rowHeight = tableHeight / totalRows;

		// =====================================================
		// DATA
		// =====================================================

		// =====================================================
		// COLORS
		// =====================================================
		Color valueBackground = new Color(253, 251, 247);
		Color borderColor = new Color(215, 205, 190);
		Color topColor = new Color(255, 150, 0);
		Color bottomColor = new Color(245, 70, 0);

		// =====================================================
		// BORDER WIDTH
		// =====================================================
		float borderWidth = 0.7f;

		// =====================================================
		// OUTER RECTANGLE
		// =====================================================
		drawColorShape.drawRectAngle(tableX, tableY, tableWidth, tableHeight, valueBackground, borderWidth);

		// =====================================================
		// DRAW CELL BACKGROUNDS
		// =====================================================
		for (int row = 0; row < totalRows; row++) {

			float y = tableY + tableHeight - ((row + 1) * rowHeight);

			// ---------------------------------------------
			// LABEL COLUMN 1
			// ---------------------------------------------
			drawColorShape.drawLabelGradient(tableX, y, col1, rowHeight, topColor, bottomColor);

			// ---------------------------------------------
			// VALUE COLUMN 1
			// ---------------------------------------------
			drawColorShape.drawRectAngle(tableX + col1, y, col2, rowHeight, valueBackground, 0f);

			// ---------------------------------------------
			// LABEL COLUMN 2
			// ---------------------------------------------
			drawColorShape.drawLabelGradient(tableX + col1 + col2, y, col3, rowHeight, topColor, bottomColor);

			// ---------------------------------------------
			// VALUE COLUMN 2
			// ---------------------------------------------
			drawColorShape.drawRectAngle(tableX + col1 + col2 + col3, y, col4, rowHeight, valueBackground, 0f);
		}

		// =====================================================
		// VERTICAL DIVIDER LINES
		// =====================================================

		// Line after Column 1
		float x1 = tableX + col1;
		// Line after Column 2
		float x2 = tableX + col1 + col2;
		// Line after Column 3
		float x3 = tableX + col1 + col2 + col3;
		drawColorShape.drawLine(x1, tableY, x1, tableY + tableHeight, borderWidth, borderColor);
		drawColorShape.drawLine(x2, tableY, x2, tableY + tableHeight, borderWidth, borderColor);
		drawColorShape.drawLine(x3, tableY, x3, tableY + tableHeight, borderWidth, borderColor);

		// =====================================================
		// HORIZONTAL DIVIDER LINES
		// =====================================================
		for (int row = 1; row < totalRows; row++) {

			float y = tableY + (row * rowHeight);
			drawColorShape.drawLine(tableX, y, tableX + tableWidth, y, borderWidth, borderColor);
		}

		// =====================================================
		// OUTER BORDER AGAIN
		// =====================================================
		// Draw it again so the outside edge stays clean.
		drawColorShape.drawRectAngle(tableX, tableY, tableWidth, tableHeight, borderColor, borderWidth);
		// =====================================================
		// DRAW TEXT
		// =====================================================
		for (int row = 0; row < totalRows; row++) {
			float y = tableY + tableHeight - ((row + 1) * rowHeight);
			// ---------------------------------------------
			// LABEL 1
			// ---------------------------------------------
			drawColorShape.drawCellText(data[row][0], labelFont, 12f, tableX + 13f, y, col1, rowHeight, Color.WHITE);

			// ---------------------------------------------
			// VALUE 1
			// ---------------------------------------------
			drawColorShape.drawCellText(data[row][1], valueFont, 11f, tableX + col1 + 13f, y, col2, rowHeight,
					Color.BLACK);

			// ---------------------------------------------
			// LABEL 2
			// ---------------------------------------------
			drawColorShape.drawCellText(data[row][2], labelFont, 12f, tableX + col1 + col2 + 13f, y, col3, rowHeight,
					Color.WHITE);

			// ---------------------------------------------
			// VALUE 2
			// ---------------------------------------------
			drawColorShape.drawCellText(data[row][3], valueFont, 11f, tableX + col1 + col2 + col3 + 13f, y, col4,
					rowHeight, Color.BLACK);
		}
	}

	

}
