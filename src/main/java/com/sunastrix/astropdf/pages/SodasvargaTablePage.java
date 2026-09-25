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
import com.sunastrix.astroganitlib.model.BirthDetailBean;
import com.sunastrix.astropdf.model.PageInfo;

public class SodasvargaTablePage extends BasePage {
	/*
	 * Color[] planetColors = { new Color(220, 45, 75), // 1 - Crimson new
	 * Color(205, 45, 155), // 2 - Magenta new Color(225, 125, 15), // 3 - Orange
	 * new Color(25, 105, 205), // 4 - Blue new Color(220, 55, 45), // 5 - Red new
	 * Color(0, 145, 145), // 6 - Teal new Color(225, 145, 15), // 7 - Golden new
	 * Color(55, 75, 200), // 8 - Indigo new Color(20, 140, 65), // 9 - Green new
	 * Color(145, 55, 185), // 10 - Purple new Color(190, 90, 15), // 11 - Burnt
	 * Orange new Color(20, 145, 190), // 12 - Sky Blue new Color(200, 55, 105), //
	 * 13 - Rose new Color(225, 70, 30), // 14 - Vermilion new Color(100, 65, 190),
	 * // 15 - Violet new Color(0, 125, 100) // 16 - Emerald Teal };
	 */

	public SodasvargaTablePage(DesktopHoroNew desktopHoro, BirthDetailBean birthDetailBean) {
		this.desktopHoro = desktopHoro;
		this.birthDetailBean = birthDetailBean;
	}

	public PageInfo printShodasvargaTable(PDDocument document, PDType0Font poppinsRegularFont,
			PDType0Font krutiDevRegularFont) {
		this.document = document;
		this.poppinsRegularFont = poppinsRegularFont;
		this.krutiDevRegularFont = krutiDevRegularFont;
		return drawPage("'kksM\"koxZ rkfydk", 1);

	}

	public PageInfo printShodasvargaBhavTable(PDDocument document, PDType0Font poppinsRegularFont,
			PDType0Font krutiDevRegularFont) {
		this.document = document;
		this.poppinsRegularFont = poppinsRegularFont;
		this.krutiDevRegularFont = krutiDevRegularFont;
		return drawPage("'kksM\"koxZ Hkko rkfydk", 2);
	}

	public PageInfo drawPage(String heading, int tableNo) {
		String pageHeading = heading;

		PageDetail pageDetail = addPage(pageHeading);

		try (PDPageContentStream cs = new PDPageContentStream(document, pageDetail.getPage())) {

			this.contentStream = cs;
			drawShape.initialize(pageHeight, pageWidth, document, cs);
			drawColorShape.initialize(pageHeight, pageWidth, document, cs);
			drawPageBorder(document, pageDetail.getPage());
			drawCornerImages();
			drawHeader(pageWidth, pageHeight, krutiDevRegularFont, heading);

			// Draw Basic Details
			float tableX = 39f;
			float tableY = 680f;
			float tableWidth = 514f;
			float tableHeight = 30 + 16 * 24 + 12 * 1;

			float bgX = tableX - 10f;
			float bgY = tableY - tableHeight + 26 - 10f;
			float bgWidth = tableWidth + 19f;
			float bgHeight = tableHeight + 37f;

			float headWidth = 170f;
			float headHeight = 26f;
			float headMargin = 13.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, heading, 14);
			drawPlanetTable(tableX, tableY, tableWidth, tableNo);

			drawFooter(poppinsRegularFont, poppinsRegularFont, 15);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
		return pageDetail.getPageInfo();
	}

	private void drawPlanetTable(float x, float y, float width, int tableNo) throws IOException {
		float tx = x;
		float ty = y;
		float headerHeight = 30f;
		float rowHeight = 24f;
		float radius = 8;
		int rowCount = 16;
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
				rowColor = Color.WHITE;// new Color(252, 246, 235);
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

		float firstColumnWidth = 65f;
		float columnWidth = (width - firstColumnWidth) / 10;
		float cx = x + firstColumnWidth;

		float tableHeight = headerHeight + rowCount * rowHeight + rowCount * divider;
		for (int i = 0; i < 10; i++) {
			drawColorShape.drawLine(cx, ty, cx, ty + tableHeight, 1f, gridColor);
			cx += columnWidth;
		}
		contentStream.saveGraphicsState();
		contentStream.setStrokingColor(outerBorderColor);
		contentStream.setLineWidth(1f);
		drawColorShape.drawRoundedRectangle(tx, ty, width, tableHeight, radius);
		contentStream.stroke();
		contentStream.restoreGraphicsState();
		if (tableNo == 1) {
			populateSodavargaTable(x, y, width);
		} else {
			populateSodavargaBhavTable(x, y, width);
		}

	}

	void populateSodavargaTable(float x, float y, float width) throws IOException {
		String[] heading = constantHindi.shodasHeading;
		String columnName[] = constantHindi.shodasColumnNew;
		float headerHeight = 30f;
		float tx = x;
		float starty = utility.getTextBaseline(krutiDevRegularFont, y, headerHeight, 18);
		float rowHeight = 24f;
		float boxWidth = 65f;
		float horizontalGap = (width - boxWidth) / 10;
		for (int i = 0; i < heading.length; i++) {
			if (i == 0) {
				drawColorShape.drawBoldText(tx + 5, starty, heading[i], krutiDevRegularFont, 18, Color.WHITE);

			} else {
				drawColorShape.drawCenteredBoldText(tx, boxWidth, starty, heading[i], 18, krutiDevRegularFont,
						Color.WHITE);

			}
			tx += boxWidth;
			boxWidth = horizontalGap;
		}
		starty = y - rowHeight - 1f;
		starty = utility.getTextBaseline(krutiDevRegularFont, starty, rowHeight, 16);

		int[] arr1 = getIntArray(desktopHoro.getPositionForShodasvarg(0), 0);
		int[] arr2 = getIntArray(desktopHoro.getPositionForShodasvarg(1), 0);
		int[] arr3 = getIntArray(desktopHoro.getPositionForShodasvarg(2), 0);
		int[] arr4 = getIntArray(desktopHoro.getPositionForShodasvarg(3), 0);
		int[] arr5 = getIntArray(desktopHoro.getPositionForShodasvarg(4), 0);
		int[] arr6 = getIntArray(desktopHoro.getPositionForShodasvarg(5), 0);
		int[] arr7 = getIntArray(desktopHoro.getPositionForShodasvarg(6), 0);
		int[] arr8 = getIntArray(desktopHoro.getPositionForShodasvarg(7), 0);
		int[] arr9 = getIntArray(desktopHoro.getPositionForShodasvarg(8), 0);
		int[] arr10 = getIntArray(desktopHoro.getPositionForShodasvarg(9), 0);
		int[] arr11 = getIntArray(desktopHoro.getPositionForShodasvarg(10), 0);
		int[] arr12 = getIntArray(desktopHoro.getPositionForShodasvarg(11), 0);
		int[] arr13 = getIntArray(desktopHoro.getPositionForShodasvarg(12), 0);
		int[] arr14 = getIntArray(desktopHoro.getPositionForShodasvarg(13), 0);
		int[] arr15 = getIntArray(desktopHoro.getPositionForShodasvarg(14), 0);
		int[] arr16 = getIntArray(desktopHoro.getPositionForShodasvarg(15), 0);
		ArrayList<int[]> list = new ArrayList<int[]>();
		list.add(arr1);
		list.add(arr2);
		list.add(arr3);
		list.add(arr4);
		list.add(arr5);
		list.add(arr6);
		list.add(arr7);
		list.add(arr8);
		list.add(arr9);
		list.add(arr10);
		list.add(arr11);
		list.add(arr12);
		list.add(arr13);
		list.add(arr14);
		list.add(arr15);
		list.add(arr16);
		boxWidth = 60f;
		for (int i = 0; i < list.size(); i++) {
			int[] arr = list.get(i);
			tx = x;
			for (int j = 0; j < 11; j++) {
				if (j == 0) {
					// drawColorShape.drawText(tx + 7, starty, columnName[i], krutiDevRegularFont,
					// 14, planetColors[i]);
					drawColorShape.drawBoldText(tx + 5, starty, columnName[i], krutiDevRegularFont, 16,
							planetColors[i]);
					tx += boxWidth;
				} else {
					if (j == 1) {

						drawColorShape.drawCenteredBoldText(tx, horizontalGap, starty, String.valueOf(arr[12]), 12,
								poppinsRegularFont, Color.black);

					} else {

						drawColorShape.drawCenteredBoldText(tx, horizontalGap, starty, String.valueOf(arr[j - 2]), 12,
								poppinsRegularFont, Color.black);

					}
					tx += horizontalGap;
				}

			}
			starty -= rowHeight + 1;
		}

	}

	void populateSodavargaBhavTable(float x, float y, float width) throws IOException {
		String[] heading = constantHindi.shodasHeading;
		String columnName[] = constantHindi.shodasColumnNew;
		float headerHeight = 30f;
		float tx = x;
		float starty = utility.getTextBaseline(krutiDevRegularFont, y, headerHeight, 14);
		float rowHeight = 24f;
		float boxWidth = 65f;
		float horizontalGap = (width - boxWidth) / 10;
		for (int i = 0; i < heading.length; i++) {
			drawColorShape.drawCenteredBoldText(tx, boxWidth, starty, heading[i], 16, krutiDevRegularFont, Color.WHITE);
			tx += boxWidth;
			boxWidth = horizontalGap;
		}
		starty = y - rowHeight - 1f;
		starty = utility.getTextBaseline(krutiDevRegularFont, starty, rowHeight, 16);

		int[] arr1 = getIntArray(desktopHoro.getPositionForShodasvarg(0), 0);
		int[] arr2 = getIntArray(desktopHoro.getPositionForShodasvarg(1), 0);
		int[] arr3 = getIntArray(desktopHoro.getPositionForShodasvarg(2), 0);
		int[] arr4 = getIntArray(desktopHoro.getPositionForShodasvarg(3), 0);
		int[] arr5 = getIntArray(desktopHoro.getPositionForShodasvarg(4), 0);
		int[] arr6 = getIntArray(desktopHoro.getPositionForShodasvarg(5), 0);
		int[] arr7 = getIntArray(desktopHoro.getPositionForShodasvarg(6), 0);
		int[] arr8 = getIntArray(desktopHoro.getPositionForShodasvarg(7), 0);
		int[] arr9 = getIntArray(desktopHoro.getPositionForShodasvarg(8), 0);
		int[] arr10 = getIntArray(desktopHoro.getPositionForShodasvarg(9), 0);
		int[] arr11 = getIntArray(desktopHoro.getPositionForShodasvarg(10), 0);
		int[] arr12 = getIntArray(desktopHoro.getPositionForShodasvarg(11), 0);
		int[] arr13 = getIntArray(desktopHoro.getPositionForShodasvarg(12), 0);
		int[] arr14 = getIntArray(desktopHoro.getPositionForShodasvarg(13), 0);
		int[] arr15 = getIntArray(desktopHoro.getPositionForShodasvarg(14), 0);
		int[] arr16 = getIntArray(desktopHoro.getPositionForShodasvarg(15), 0);
		ArrayList<int[]> list = new ArrayList<int[]>();
		list.add(arr1);
		list.add(arr2);
		list.add(arr3);
		list.add(arr4);
		list.add(arr5);
		list.add(arr6);
		list.add(arr7);
		list.add(arr8);
		list.add(arr9);
		list.add(arr10);
		list.add(arr11);
		list.add(arr12);
		list.add(arr13);
		list.add(arr14);
		list.add(arr15);
		list.add(arr16);
		boxWidth = 60f;
		for (int i = 0; i < list.size(); i++) {
			int[] arr = list.get(i);
			tx = x;
			for (int j = 0; j < 11; j++) {
				if (j == 0) {
					/*
					 * drawColorShape.drawCenteredText(columnName[i], tx, tx + boxWidth, starty,
					 * krutiDevRegularFont, 14, planetColors[i]);
					 */
					drawColorShape.drawBoldText(tx + 5, starty, columnName[i], krutiDevRegularFont, 16,
							planetColors[i]);
					/*
					 * drawColorShape.drawText(tx + 7, starty, columnName[i], krutiDevRegularFont,
					 * 14, planetColors[i]);
					 */
					tx += boxWidth;
				} else {
					if (j == 1) {
						/*
						 * drawColorShape.drawCenteredText(String.valueOf(arr[12]), tx, tx +
						 * horizontalGap, starty, poppinsRegularFont, 10, Color.black);
						 */
						drawColorShape.drawCenteredBoldText(tx, horizontalGap, starty, String.valueOf(arr[12]), 12,
								poppinsRegularFont, Color.black);

					} else {
						int lagna = arr[12];
						int bhav = 1;
						if (j != 1) {
							if (arr[j - 2] < lagna) {
								bhav = 12 - lagna + arr[j - 2] + 1;
							} else {
								bhav = arr[j - 2] - lagna + 1;
							}
						}

						/*
						 * drawColorShape.drawCenteredText(String.valueOf(bhav), tx, tx + horizontalGap,
						 * starty, poppinsRegularFont, 10, Color.black);
						 */
						drawColorShape.drawCenteredBoldText(tx, horizontalGap, starty, String.valueOf(bhav), 12,
								poppinsRegularFont, Color.black);

					}
					tx += horizontalGap;
				}

			}
			starty -= rowHeight + 1;
		}

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