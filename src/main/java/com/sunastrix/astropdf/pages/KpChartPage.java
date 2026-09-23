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
import com.sunastrix.astropdf.calculation.PlanetAndSunPlanetPositionCalculation;
import com.sunastrix.astropdf.model.BasicPlanetDataModel;
import com.sunastrix.astropdf.model.PageInfo;

public class KpChartPage extends BasePage {
	Color[] planetColors = { new Color(230, 30, 70), new Color(200, 30, 160), new Color(230, 110, 0),
			new Color(0, 90, 210), new Color(220, 30, 30), new Color(0, 150, 150), new Color(210, 130, 0),
			new Color(50, 60, 200), new Color(0, 135, 60), new Color(140, 40, 190), new Color(180, 80, 0),
			new Color(0, 130, 190), new Color(200, 40, 90) };

	public KpChartPage(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	public PageInfo drawPage(PDDocument document, PDType0Font poppinsRegularFont, PDType0Font krutiDevRegularFont) {
		String pageHeading = "foa'kksÙkjh varj n'kk";
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
			drawHeader(pageWidth, pageHeight, krutiDevRegularFont, pageHeading);
			// Draw Basic Details
			float tableX = 40f;
			float tableY = 480f;
			float tableWidth = 240f;
			float tableHeight = 220f;
			float bgX = tableX - 10f;
			float bgY = tableY - 8f;
			float bgWidth = tableWidth + 19f;
			float bgHeight = tableHeight + 30f;
			float headWidth = 170f;
			float headHeight = 26f;
			float headMargin = 13.2f;
			int[] planetArray = getIntArray(desktopHoro.getPositionForShodasvarg(0), 0);
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "yXu pkVZ", 14);
			drawChart(tableX, tableY, tableWidth, tableHeight, 12, planetArray, planetArray[12]);
			tableX = 315f;
			tableY = 480f;
			tableWidth = 240f;
			tableHeight = 220f;
			bgX = tableX - 10f;
			bgY = tableY - 8f;
			bgWidth = tableWidth + 19f;
			bgHeight = tableHeight + 30f;
			headWidth = 170f;
			headHeight = 26f;
			headMargin = 13.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "uoeka'k pkVZ", 14);
			drawChart(tableX, tableY, tableWidth, tableHeight, 12, planetArray, planetArray[12]);
			// Draw Basic Details
			tableX = 39f;
			tableY = 390f;
			tableWidth = 514f;
			tableHeight = 26 + 12 * 20 + 12 * 1;

			bgX = tableX - 10f;
			bgY = tableY - tableHeight + 26 - 15f;
			bgWidth = tableWidth + 19f;
			bgHeight = tableHeight + 37f;

			headWidth = 170f;
			headHeight = 26f;
			headMargin = 13.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "xzg fLFkfr", 14);
			drawPlanetTable(tableX, tableY, tableWidth);
			drawFooter(poppinsRegularFont, poppinsRegularFont, 15);
		} catch (Exception e) {

		}
		return pageDetail.getPageInfo();
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

	void drawChart(float x, float y, float width, float height, int rashiFontSize, int[] planetArray, int lagna)
			throws Exception {
		byte[] svgBytes = getClass().getResourceAsStream("/images/lagna_chart_scaled.svg").readAllBytes();
		drawColorShape.drawExactSvg(svgBytes, x, y, width, height);
		drawRashiInBhav(x, y, width, height, planetArray[12], rashiFontSize);
		printPlanetsInHouse(x, y, width, height, planetArray, lagna, rashiFontSize);
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

	private void drawPlanetTable(float x, float y, float width) throws IOException {
		float tx = x;
		float ty = y;
		float headerHeight = 26f;
		float rowHeight = 19f;
		float radius = 8;
		int rowCount = 13;
		float divider = 1f;
		Color gradientEnd = new Color(255, 174, 35);
		Color gradientStart = new Color(235, 78, 0);
		Color headerBorderColor = new Color(205, 50, 0);
		Color outerBorderColor = new Color(215, 125, 40);
		Color gridColor = new Color(225, 215, 200);
		Color alternateRowColor = new Color(255, 250, 240);
		Color rowColor;

		drawColorShape.drawTopRoundedGradientRect(tx, ty, width, headerHeight, radius - 2, gradientStart, gradientEnd,
				false);
		ty -= .5;
		drawColorShape.drawLine(tx, ty, tx + width, ty, 1f, gridColor);

		for (int i = 0; i < rowCount; i++) {
			ty = ty - rowHeight - .5f;
			if (i % 2 == 0) {
				rowColor = new Color(252, 246, 235);
			} else {
				rowColor = new Color(251, 241, 225);
			}
			contentStream.setNonStrokingColor(rowColor);
			if (i == rowCount - 1) {
				drawColorShape.drawSolidBottomRoundedRectangle(tx, ty, width, rowHeight, radius, rowColor);
			} else {
				drawColorShape.drawSolidRectAngle(tx, ty, width, rowHeight, rowColor);
				ty -= .5;
				drawColorShape.drawLine(tx, ty, tx + width, ty, 1, gridColor);

			}

		}

		float[] columnWidth = { 90f, 90f, 150f, 110f };
		float cx = x;
		float tableHeight = headerHeight + rowCount * rowHeight + rowCount * divider;
		for (int i = 0; i < columnWidth.length; i++) {
			cx += columnWidth[i];
			System.out.println(cx);
			drawColorShape.drawLine(cx, ty, cx, ty + tableHeight, 1f, gridColor);
		}
		contentStream.saveGraphicsState();
		contentStream.setStrokingColor(outerBorderColor);
		contentStream.setLineWidth(1f);
		drawColorShape.drawRoundedRectangle(tx, ty, width, tableHeight, radius);
		contentStream.stroke();
		contentStream.restoreGraphicsState();
		populatePlanetDetail(x, y, width);
	}

	void populatePlanetDetail(float x, float y, float width) throws IOException {
		ArrayList<BasicPlanetDataModel> list = new PlanetAndSunPlanetPositionCalculation(desktopHoro).getPlanetsData();
		String[] heading = constantHindi.planetPosHeading;
		float headerHeight = 26f;
		float tx = x;
		float starty = utility.getTextBaseline(krutiDevRegularFont, y, headerHeight, 14);
		float rowHeight = 19f;
		float boxWidth = 50;
		float horizontalGap = (width - boxWidth) / 13;
		float[] columnWidth = { 90f, 90f, 150f, 110f, 74.0f };
		for (int i = 0; i < heading.length; i++) {
			drawColorShape.drawCenteredBoldText(tx, columnWidth[i], starty, heading[i], 16, krutiDevRegularFont,
					Color.WHITE);
			tx += columnWidth[i];
		}
		starty = starty - headerHeight - 3f;
		starty = utility.getTextBaseline(krutiDevRegularFont, starty, rowHeight, 14);
		BasicPlanetDataModel basicPlanetDataModel;
		for (int i = 0; i < list.size(); i++) {
			basicPlanetDataModel = list.get(i);
			tx = x;

			drawColorShape.drawCenteredText(basicPlanetDataModel.getPlaName(), tx, tx + columnWidth[0], starty,
					krutiDevRegularFont, 14, planetColors[i]);
			tx += columnWidth[0];
			drawColorShape.drawCenteredText(String.valueOf(basicPlanetDataModel.getSign()), tx, tx + columnWidth[1],
					starty, krutiDevRegularFont, 14, planetColors[i]);
			tx += columnWidth[1];
			drawColorShape.drawCenteredText(basicPlanetDataModel.getDegree(), tx, tx + columnWidth[2], starty,
					poppinsRegularFont, 10, planetColors[i]);
			tx += columnWidth[2];
			drawColorShape.drawCenteredText(String.valueOf(basicPlanetDataModel.getNaks()), tx, tx + columnWidth[3],
					starty, krutiDevRegularFont, 14, planetColors[i]);
			tx += columnWidth[3];
			drawColorShape.drawCenteredText(String.valueOf(basicPlanetDataModel.getPlaCharan()), tx,
					tx + columnWidth[4], starty, poppinsRegularFont, 10, planetColors[i]);
			starty -= rowHeight + 1;
		}

	}
}
