package com.sunastrix.astropdf.pages;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astropdf.model.PageInfo;

public class VrashfalPage extends BasePage {
	Color[] planetColors = { new Color(230, 30, 70), new Color(200, 30, 160), new Color(230, 110, 0),
			new Color(0, 90, 210), new Color(220, 30, 30), new Color(0, 150, 150), new Color(210, 130, 0),
			new Color(50, 60, 200), new Color(0, 135, 60), new Color(140, 40, 190), new Color(180, 80, 0),
			new Color(0, 130, 190), new Color(200, 40, 90) };

	public VrashfalPage(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	public PageInfo drawPage(PDDocument document, PDType0Font poppinsRegularFont, PDType0Font krutiDevRegularFont) {
		String pageHeading = "dLiy baVjfyaDl";
		this.document = document;
		this.poppinsRegularFont = poppinsRegularFont;
		this.krutiDevRegularFont = krutiDevRegularFont;
		PageDetail pageDetail = addPage(pageHeading);

		try (PDPageContentStream cs = new PDPageContentStream(document, pageDetail.getPage())) {

			this.contentStream = cs;
			drawShape.initialize(pageHeight, pageWidth, document, cs);
			drawColorShape.initialize(pageHeight, pageWidth, document, cs);
			drawPageBorder(document, pageDetail.getPage());
			drawCornerImages();
			drawHeader(pageWidth, pageHeight, krutiDevRegularFont, "dLiy baVjfyaDl");
			// Draw Basic Details
			float tableX = 39f;
			float tableY = 680f;
			float tableWidth = 514f;
			float tableHeight = 100.0f;

			float bgX = tableX - 10f;
			float bgY = tableY - tableHeight + 26 - 15f;
			float bgWidth = tableWidth + 19f;
			float bgHeight = tableHeight + 37f;

			float headWidth = 170f;
			float headHeight = 26f;
			float headMargin = 13.2f;

			String[] labels = constantHindi.birthDetailLabel;
			String[] values = { desktopHoro.getName(), getBirthDate(), desktopHoro.getBirthTime(),
					desktopHoro.getPlace(), String.valueOf(desktopHoro.getAyan()), desktopHoro.getGanaName(),
					desktopHoro.getLatitude(), desktopHoro.getLongitude() };
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "tUe fooj.k", 14);
			drawTable(tableX, tableY, tableWidth, 4, labels, values, poppinsRegularFont, 10);

			tableY = 250f;
			tableWidth = 320f;
			tableX = pageWidth / 2 - 160f;
			tableHeight = 280f;

			bgX = tableX - 10f;
			bgY = tableY - 8f;
			bgWidth = tableWidth + 19f;
			bgHeight = tableHeight + 30f;

			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "yXu pkVZ", 14);

			int[] planetArray = getIntArray(desktopHoro.getPositionForShodasvarg(0), 0);
			drawChart(tableX, tableY, tableWidth, tableHeight, planetArray, 16);

			drawFooter(poppinsRegularFont, poppinsRegularFont, 15);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return pageDetail.getPageInfo();
	}

	void drawTable(float x, float y, float width, int rowCount, String[] labels, String[] values, PDType0Font valueFont,
			int valueFontSize) throws IOException {
		float tx = x;
		float ty = y;
		float headerHeight = 24f;
		float rowHeight = 24f;
		float radius = 8;

		float divider = 1f;
		Color gradientEnd = new Color(255, 174, 35);
		Color gradientStart = new Color(235, 78, 0);
		Color headerBorderColor = new Color(205, 50, 0);
		Color outerBorderColor = new Color(215, 125, 40);
		Color gridColor = new Color(225, 215, 200);
		Color alternateRowColor = new Color(255, 250, 240);
		Color rowColor;

		float[] colWidth = { 102f, 144f, 102f, 142f };
		for (int i = 0; i < rowCount; i++) {

			drawColorShape.drawGradientRect(tx, ty, colWidth[0], rowHeight, gradientStart, gradientEnd, true);
			tx += colWidth[0];
			// drawColorShape.drawSolidRectAngle(tx, ty, colWidth[1], rowHeight,
			// Color.WHITE);
			drawColorShape.drawGradientRect(tx, ty, colWidth[1], rowHeight, Color.WHITE, Color.WHITE, true);
			tx += colWidth[1];
			drawColorShape.drawGradientRect(tx, ty, colWidth[2], rowHeight, gradientStart, gradientEnd, true);
			tx += colWidth[2];
			drawColorShape.drawGradientRect(tx, ty, colWidth[3], rowHeight, Color.WHITE, Color.WHITE, true);
			// ty -= .5;
			tx = x;
			if (i != rowCount - 1) {
				drawColorShape.drawLine(tx, ty, tx + width, ty, 1, gridColor);
				ty = ty - rowHeight - 1f;
			}

		}
		float tableHeight = rowCount * rowHeight + rowCount * divider;
		System.out.println("Height--" + tableHeight);
		contentStream.saveGraphicsState();
		contentStream.setStrokingColor(outerBorderColor);
		contentStream.setLineWidth(1f);
		drawColorShape.drawRectAngle(tx, ty, width, tableHeight, gridColor, 1);
		contentStream.stroke();
		contentStream.restoreGraphicsState();
		printValues(x, y, width, rowHeight, labels, values, valueFont, valueFontSize);

	}

	void printValues(float x, float y, float width, float rowHeight, String[] labels, String[] values,
			PDType0Font valueFont, int valueFontSize) throws IOException {
		float tx = x;
		float ty = utility.getTextBaseline(poppinsRegularFont, y, rowHeight, 14);
		float[] colWidth = { 102f, 144f, 102f, 142f };
		for (int i = 0; i < labels.length; i = i + 2) {
			drawColorShape.drawBoldText(tx + 10, ty, labels[i], krutiDevRegularFont, 14, Color.WHITE);
			tx += colWidth[0];
			drawColorShape.drawText(tx + 10, ty, values[i], valueFont, valueFontSize, planetColors[i]);
			tx += colWidth[1];
			drawColorShape.drawBoldText(tx + 10, ty, labels[i + 1], krutiDevRegularFont, 14, Color.WHITE);
			tx += colWidth[2];
			drawColorShape.drawText(tx + 10, ty, values[i + 1], valueFont, valueFontSize, planetColors[i + 1]);
			tx = x;
			ty -= rowHeight + 1;
		}
	}

	void drawChart(float x, float y, float width, float height, int[] planetArray, int rashiFontSize) throws Exception {
		byte[] svgBytes = getClass().getResourceAsStream("/images/lagna_chart_scaled.svg").readAllBytes();
		drawColorShape.drawExactSvg(svgBytes, x, y, width, height);
		drawRashiInBhav(x, y, width, height, planetArray[12], rashiFontSize);
		printPlanetsInHouse(x, y, width, height, planetArray, planetArray[12], rashiFontSize);
	}

	private void drawRashiInBhav(float x, float y, float width, float height, int lagna, int rashiFontSize)
			throws IOException {
		float cx = x;
		float cy = y;
		float cwidth = width;
		float cheight = height;
		float quarterX = width / 4;
		float quarterY = height / 4;

		// System.out.println("tHeight" + tHeight);
		float[] x_axis = { x + 2 * quarterX, x + quarterX - 5, x + quarterX - 25, x + 2 * quarterX - 20,
				x + quarterX - 25, x + quarterX - 5, x + 2 * quarterX, x + 3 * quarterX, x + 3 * quarterX + 25,
				x + 2 * quarterX + 20, x + 3 * quarterX + 25, x + 3 * quarterX, };
		float[] y_axis = { y + 2 * quarterY + 15, y + 3 * quarterY + 15, y + 3 * quarterY + 3, y + 2 * quarterY,
				y + quarterY - 3, y + quarterY - 15, y + 2 * quarterY - 15, y + quarterY - 15, y + quarterY - 3,
				y + 2 * quarterY, y + 3 * quarterY + 3, y + 3 * quarterY + 15 };

		int lagna1 = lagna;
		for (int i = 0; i < 12; i++) {
			if (lagna1 > 12)
				lagna1 = 1;
			float tWidth = utility.getTextWidth(String.valueOf(lagna1), poppinsRegularFont, rashiFontSize);
			float tHeight = utility.getTextHeight(poppinsRegularFont, rashiFontSize, String.valueOf(lagna1));
			drawColorShape.drawText(x_axis[i] - tWidth / 2, y_axis[i] - tHeight / 2, String.valueOf(lagna1),
					poppinsRegularFont, rashiFontSize, Color.black);
			lagna1++;
		}
	}

	private void printPlanetsInHouse(float x, float y, float width, float height, int[] planetArray, int lagnaRashi,
			int fontSize) throws IOException {
		float cx = x;
		float cy = y;
		float cwidth = width;
		float cheight = height;
		float quarterX = width / 4;
		float quarterY = height / 4;
		float halfX = width / 2;
		float halfY = height / 2;
		float hMargin = quarterX / 4;
		float vMargin = quarterY / 5;
		float centerX1 = (x + hMargin / 2);
		float centerY1 = y + 3 * quarterY + vMargin / 2;

		/* 2,6,8 and 12 house */
		float[] housePositionsX2 = { x + quarterX - 5, x + quarterX - 5 - hMargin, x + quarterX - 5 + hMargin,
				x + quarterX - 5 + 2 * hMargin, x + quarterX - 5 - 2 * hMargin, x + quarterX + 5 };
		float[] housePositionsX6 = { x + quarterX - 5, x + quarterX - 5 - hMargin, x + quarterX - 5 + hMargin,
				x + quarterX - 5 + 2 * hMargin, x + quarterX - 5 - 2 * hMargin, x + quarterX + 5 };
		float[] housePositionsX8 = { x + width / 2 + quarterX - 5, x + width / 2 + quarterX - 5 - hMargin,
				x + width / 2 + quarterX - 5 + hMargin, x + width / 2 + quarterX - 5 + 2 * hMargin,
				x + width / 2 + quarterX - 5 - 2 * hMargin, x + width / 2 + quarterX + 5 };
		float[] housePositionsX12 = { x + width / 2 + quarterX - 5, x + width / 2 + quarterX - 5 - hMargin,
				x + width / 2 + quarterX - 5 + hMargin, x + width / 2 + quarterX - 5 + 2 * hMargin,
				x + width / 2 + quarterX - 5 - 2 * hMargin, x + width / 2 + quarterX + 5 };
		float[] housePositionsY2 = { y + height - vMargin, y + height - 2 * vMargin, y + height - 2 * vMargin,
				y + height - vMargin, y + height - vMargin, y + height - 3 * vMargin };
		float[] housePositionsY6 = { y + vMargin, y + 2 * vMargin, y + 2 * vMargin, y + vMargin, y + vMargin,
				y + 3 * vMargin };
		float[] housePositionsY8 = { y + vMargin, y + 2 * vMargin, y + 2 * vMargin, y + vMargin, y + vMargin,
				y + 3 * vMargin };
		float[] housePositionsY12 = { y + height - vMargin, y + height - 2 * vMargin, y + height - 2 * vMargin,
				y + height - vMargin, y + height - vMargin, y + height - 3 * vMargin };

		/* 3,5,9 and 11 house */

		float[] housePositionsX3 = { x + 2 * hMargin - 7, x + hMargin - 9, x + hMargin - 9, x + hMargin - 9,
				x + 2 * hMargin - 10, x + 2 * hMargin - 10, };
		float[] housePositionsX5 = { x + 2 * hMargin - 7, x + hMargin - 9, x + hMargin - 9, x + hMargin - 9,
				x + 2 * hMargin - 10, x + 2 * hMargin - 10, };

		float[] housePositionsY3 = { y + halfY + 2 * halfY / 4, y + halfY + 2 * halfY / 4, y + halfY + 3 * halfY / 4,
				y + halfY + halfY / 4, y + halfY + halfY / 3, y + halfY + 2 * halfY / 3 };
		float[] housePositionsY5 = { y + 2 * halfY / 4, y + 2 * halfY / 4, y + 3 * halfY / 4, y + halfY / 4,
				y + halfY / 3, y + 2 * halfY / 3 };
		float[] housePositionsX9 = { x + width - 2 * hMargin + 5, x + width - hMargin + 5, x + width - hMargin + 5,
				x + width - hMargin + 5, x + width - 2 * hMargin + 5, x + width - 2 * hMargin + 5, };
		float[] housePositionsY9 = { y + 2 * halfY / 4, y + 2 * halfY / 4, y + 3 * halfY / 4, y + halfY / 4,
				y + halfY / 3, y + 2 * halfY / 3 };
		float[] housePositionsX11 = { x + width - 2 * hMargin + 5, x + width - hMargin + 5, x + width - hMargin + 5,
				x + width - hMargin + 5, x + width - 2 * hMargin + 5, x + width - 2 * hMargin + 5, };
		float[] housePositionsY11 = { y + halfY + 2 * halfY / 4, y + halfY + 2 * halfY / 4, y + halfY + 3 * halfY / 4,
				y + halfY + halfY / 4, y + halfY + halfY / 3, y + halfY + 2 * halfY / 3 };
		/* 1,4,7 and 10 house */

		float[] housePositionsX1 = { x + width / 2, x + width / 2 - hMargin - 10, x + width / 2 + hMargin + 10,
				x + width / 2 - hMargin - 10, x + width / 2 + hMargin + 10, x + width / 2 - hMargin - 30 };
		float[] housePositionsY1 = { y + 3 * quarterY, y + 3 * quarterY + quarterY / 3, y + 3 * quarterY + quarterY / 3,
				y + 3 * quarterY - quarterY / 3, y + 3 * quarterY - quarterY / 3, y + 3 * quarterY };
		float[] housePositionsX7 = { x + width / 2, x + width / 2 - hMargin - 10, x + width / 2 + hMargin + 10,
				x + width / 2 - hMargin - 10, x + width / 2 + hMargin + 10, x + width / 2 - hMargin - 30 };
		float[] housePositionsY7 = { y + quarterY, y + quarterY + quarterY / 3, y + quarterY + quarterY / 3,
				y + quarterY - quarterY / 3, y + quarterY - quarterY / 3, y + quarterY };
		float[] housePositionsX4 = { x + quarterX, x + quarterX - hMargin - 10, x + quarterX - hMargin - 10,
				x + quarterX + hMargin + 10, x + quarterX + hMargin + 10, x + quarterX - hMargin - 30, };
		float[] housePositionsY4 = { y + 2 * quarterY, y + 2 * quarterY + quarterY / 3, y + 2 * quarterY - quarterY / 3,
				y + 2 * quarterY + quarterY / 3, y + 2 * quarterY - quarterY / 3, y + 2 * quarterY };
		float[] housePositionsX10 = { x + width / 2 + quarterX, x + width / 2 + quarterX - hMargin - 10,
				x + width / 2 + quarterX - hMargin - 10, x + width / 2 + quarterX + hMargin + 10,
				x + width / 2 + quarterX + hMargin + 10, x + width / 2 + quarterX + hMargin + 30, };
		float[] housePositionsY10 = { y + 2 * quarterY, y + 2 * quarterY + quarterY / 3,
				y + 2 * quarterY - quarterY / 3, y + 2 * quarterY + quarterY / 3, y + 2 * quarterY - quarterY / 3,
				y + 2 * quarterY };
		ArrayList<ArrayList<String>> arrayLists = new ArrayList<>();
		ArrayList<float[]> xArrayLists = new ArrayList<>();
		ArrayList<float[]> yArrayLists = new ArrayList<>();
		xArrayLists.add(housePositionsX1);
		xArrayLists.add(housePositionsX2);
		xArrayLists.add(housePositionsX3);
		xArrayLists.add(housePositionsX4);
		xArrayLists.add(housePositionsX5);
		xArrayLists.add(housePositionsX6);
		xArrayLists.add(housePositionsX7);
		xArrayLists.add(housePositionsX8);
		xArrayLists.add(housePositionsX9);
		xArrayLists.add(housePositionsX10);
		xArrayLists.add(housePositionsX11);
		xArrayLists.add(housePositionsX12);

		yArrayLists.add(housePositionsY1);
		yArrayLists.add(housePositionsY2);
		yArrayLists.add(housePositionsY3);
		yArrayLists.add(housePositionsY4);
		yArrayLists.add(housePositionsY5);
		yArrayLists.add(housePositionsY6);
		yArrayLists.add(housePositionsY7);
		yArrayLists.add(housePositionsY8);
		yArrayLists.add(housePositionsY9);
		yArrayLists.add(housePositionsY10);
		yArrayLists.add(housePositionsY11);
		yArrayLists.add(housePositionsY12);

		String[] plaName = constantHindi.plaShortName;
		for (int i = 0; i < 12; i++) {
			arrayLists.add(new ArrayList<>());
		}
		for (int i = 0; i < planetArray.length; i++) {
			int planetBhav = getBhavOfPlant(lagnaRashi, planetArray[i]);
			planetBhav--;
			if (planetBhav >= 12) {
				planetBhav = 0;
			}
			arrayLists.get(planetBhav).add(plaName[i]);
		}
		ArrayList<String> innerList;
		float[] x_axis;
		float[] y_axis;
		int coordinateIndex;
		int count = 0;
		for (int i = 0; i < arrayLists.size(); i++) {
			innerList = arrayLists.get(i);
			coordinateIndex = 0;
			if (innerList.size() > 0) {
				x_axis = xArrayLists.get(i);
				y_axis = yArrayLists.get(i);
				for (int j = 0; j < innerList.size(); j++) {
					if (coordinateIndex > 5) {
						coordinateIndex = 0;
					}

					drawColorShape.drawText(x_axis[coordinateIndex], y_axis[coordinateIndex], innerList.get(j),
							krutiDevRegularFont, fontSize, planetColors[count]);
					// }
					coordinateIndex++;
					count++;
				}

			}
		}
	}

	private int getBhavOfPlant(int lagnaRashi, int plntRashi) {
		int bhavNumber;
		bhavNumber = plntRashi - lagnaRashi;
		if (bhavNumber < 0) {
			bhavNumber += 12;
		}
		bhavNumber += 1;
		return bhavNumber;

	}

	String getBirthDate() {
		int[] arr = desktopHoro.getBirthDate();
		return arr[0] + "-" + constantHindi.monthName[arr[1] - 1] + "-" + arr[2];
	}

	private int[] getIntArray(int[] planetPosition, int lagnaPos) {

		int[] intArray = new int[13];
		int lagna = planetPosition[lagnaPos];
		for (int i = 0; i < planetPosition.length - 1; i++) {
			intArray[i] = planetPosition[i + 1];
		}
		intArray[planetPosition.length - 1] = lagna;
		return intArray;
	}
}