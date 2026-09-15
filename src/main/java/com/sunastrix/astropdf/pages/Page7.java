package com.sunastrix.astropdf.pages;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import com.sunastrix.astroganitlib.model.BirthDetailBean;
import com.sunastrix.astroganitlib.model.DateTimeBean;
import com.sunastrix.astropdf.calculation.DashaCalculation;
import com.sunastrix.astropdf.model.DasaBean;

public class Page7 extends BasePage {
	DashaCalculation calculation;
	Color startColor = new Color(130, 0, 14); // #82000E
	Color middleColor = new Color(201, 0, 28); // #C9001C
	Color endColor = new Color(130, 0, 14); // #82000E
	Color[][] headerGradientColors = {
			// Jupiter
			{ new Color(255, 224, 145), new Color(255, 197, 75) },
			// Saturn
			{ new Color(190, 213, 255), new Color(135, 174, 245) },
			// Mercury
			{ new Color(195, 242, 207), new Color(145, 220, 170) },
			// Ketu
			{ new Color(255, 205, 220), new Color(255, 160, 190) },
			// Venus
			{ new Color(255, 205, 235), new Color(245, 160, 210) },
			// Sun
			{ new Color(255, 225, 150), new Color(255, 195, 75) },
			// Moon
			{ new Color(190, 225, 255), new Color(130, 195, 250) },
			// Mars
			{ new Color(255, 205, 210), new Color(255, 155, 170) },
			// Rahu
			{ new Color(190, 240, 238), new Color(125, 210, 210) },
			{ new Color(255, 225, 150), new Color(255, 195, 75) },
			// Moon
			{ new Color(190, 225, 255), new Color(130, 195, 250) },
			// Mars
			{ new Color(255, 205, 210), new Color(255, 155, 170) },
			// Rahu
			{ new Color(190, 240, 238), new Color(125, 210, 210) } };
	Color[] headerBorderColors = { new Color(140, 90, 0), // JUP
			new Color(45, 75, 135), // SAT
			new Color(35, 105, 60), // MER
			new Color(145, 45, 80), // KET
			new Color(145, 45, 110), // VEN
			new Color(145, 90, 0), // SUN
			new Color(145, 45, 80), // KET
			new Color(145, 45, 110), // VEN
			new Color(145, 90, 0) // SUN
	};
	Color[][] headerGradientColors1 = {
			// Ketu
			{ new Color(255, 205, 220), new Color(255, 160, 190) },

			// Venus
			{ new Color(255, 205, 235), new Color(245, 160, 210) },

			// Sun
			{ new Color(255, 225, 150), new Color(255, 195, 75) },

			// Moon
			{ new Color(190, 225, 255), new Color(130, 195, 250) },

			// Mars
			{ new Color(255, 205, 210), new Color(255, 155, 170) },

			// Rahu
			{ new Color(190, 240, 238), new Color(125, 210, 210) },

			// Jupiter
			{ new Color(255, 224, 145), new Color(255, 197, 75) },

			// Saturn
			{ new Color(190, 213, 255), new Color(135, 174, 245) },

			// Mercury
			{ new Color(195, 242, 207), new Color(145, 220, 170) } };

	Color[] planetColors = {

			// Ketu - Dark Magenta
			new Color(105, 20, 55),
			// Venus - Dark Purple/Magenta
			new Color(110, 20, 80),

			// Sun - Dark Brown/Gold
			new Color(105, 60, 0),

			// Moon - Dark Blue
			new Color(20, 65, 110),

			// Mars - Dark Red
			new Color(110, 30, 40),

			// Rahu - Dark Teal
			new Color(20, 85, 85),

			// Jupiter - Dark Golden Brown
			new Color(105, 60, 0),

			// Saturn - Dark Navy Blue
			new Color(20, 45, 100),

			// Mercury - Dark Green
			new Color(15, 75, 40) };
	int hCount = 0;
	int rCount = 0;
	String dashaStartDate;
	ArrayList<DasaBean> pratyantraDasaList;
	ArrayList<DasaBean> anterDasaList;

	public Page7(DashaCalculation calculation, BirthDetailBean birthDetailBean) {
		this.calculation = calculation;
		this.birthDetailBean = birthDetailBean;
	}

	public void printDasha(PDDocument document, PDType0Font poppinsRegularFont, PDType0Font krutiDevRegularFont) {
		drawVimAntarPage(document, poppinsRegularFont, krutiDevRegularFont);
		initDasha();
		drawVimPratyntarPage(document, poppinsRegularFont, krutiDevRegularFont);
		drawVimPratyntarPage(document, poppinsRegularFont, krutiDevRegularFont);
		drawVimPratyntarPage(document, poppinsRegularFont, krutiDevRegularFont);
		drawVimPratyntarPage(document, poppinsRegularFont, krutiDevRegularFont);
		drawVimPratyntarPage(document, poppinsRegularFont, krutiDevRegularFont);
		drawVimPratyntarPage(document, poppinsRegularFont, krutiDevRegularFont);
		drawVimPratyntarPage(document, poppinsRegularFont, krutiDevRegularFont);
		drawVimPratyntarPage(document, poppinsRegularFont, krutiDevRegularFont);
		drawVimPratyntarPage(document, poppinsRegularFont, krutiDevRegularFont);
	}

	public void drawVimAntarPage(PDDocument document, PDType0Font poppinsRegularFont, PDType0Font krutiDevRegularFont) {
		this.document = document;
		this.poppinsRegularFont = poppinsRegularFont;
		this.krutiDevRegularFont = krutiDevRegularFont;
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
			drawHeader(pageWidth, pageHeight, krutiDevRegularFont, "foa'kksÙkjh varj n'kk ");
			// Draw Basic Details
			float tableX = 39f;
			float tableY = 710f;
			float tableWidth = ((pageWidth - 100f) / 3);
			float tableHeight = 30f;
			float headerHeight = 30f;
			svgBytes = getClass().getResourceAsStream("/images/header22.svg").readAllBytes();

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					drawTable(tableX, tableY, tableWidth, tableHeight, 8, headerGradientColors[i * 3 + j],
							headerBorderColors[i * 3 + j]);
					tableX = tableX + tableWidth + 10f;
				}
				// Move to next row of tables
				tableX = 39f;
				tableY = tableY - 220f;
			}
			ArrayList<DasaBean> list = calculation.getVimDasaFormmattedData();
			float tX = 39f;
			float tY = utility.getTextBaseline(poppinsRegularFont, 710, headerHeight, 16);
			printHeaderText(tX, tY, tableWidth, list);
			tY = 710f;
			populateAnterDashaData(tX, tY, tableWidth);
			drawFooter(poppinsRegularFont, poppinsRegularFont, 15);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
	}

	public void drawVimPratyntarPage(PDDocument document, PDType0Font poppinsRegularFont,
			PDType0Font krutiDevRegularFont) {
		this.document = document;
		this.poppinsRegularFont = poppinsRegularFont;
		this.krutiDevRegularFont = krutiDevRegularFont;
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
			drawHeader(pageWidth, pageHeight, krutiDevRegularFont, "foa'kksÙkjh çR;arj n'kk");
			// Draw Basic Details
			float tableX = 39f;
			float tableY = 710f;
			float tableWidth = ((pageWidth - 100f) / 3);
			float tableHeight = 30f;
			float headerHeight = 30f;
			svgBytes = getClass().getResourceAsStream("/images/header22.svg").readAllBytes();

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					drawTable(tableX, tableY, tableWidth, tableHeight, 8, headerGradientColors[i * 3 + j],
							headerBorderColors[i * 3 + j]);
					tableX = tableX + tableWidth + 10f;
				}
				// Move to next row of tables
				tableX = 39f;
				tableY = tableY - 220f;
			}
			ArrayList<DasaBean> list = calculation.getVimDasaFormmattedData();
			float tX = 39f;
			float tY = utility.getTextBaseline(poppinsRegularFont, 710, headerHeight, 16);
			printPratyantraHeaderText(tX, tY, tableWidth, list);
			tY = 710f;
			populatePratyantraDashaData(tX, tY, tableWidth);
			drawFooter(poppinsRegularFont, poppinsRegularFont, 15);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
	}

	private void initDasha() {
		DateTimeBean dateTimeBean = birthDetailBean.getDateTimeBean();
		dashaStartDate = dateTimeBean.getDay() + "/" + dateTimeBean.getMonth() + "/" + dateTimeBean.getYear();
		anterDasaList = new ArrayList<DasaBean>();
		pratyantraDasaList = new ArrayList<DasaBean>();
		ArrayList<DasaBean> list1;
		ArrayList<DasaBean> list2;
		for (int i = 0; i < 9; i++) {
			list1 = calculation.getAntaraDasaFormmattedData(i, i);
			anterDasaList.addAll(list1);
			for (int j = 0; j < 9; j++) {
				list2 = calculation.getPratyantraDasaFormmattedData(j, i);
				pratyantraDasaList.addAll(list2);
			}
		}

	}

	void printHeaderText(float x, float y, float tableWidth, ArrayList<DasaBean> list) throws IOException {
		float tX = x;
		float tY = y;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				String timeRange;
				if (i == 0) {
					DateTimeBean dateTimeBean = birthDetailBean.getDateTimeBean();
					timeRange = dateTimeBean.getDay() + "/" + dateTimeBean.getMonth() + "/" + dateTimeBean.getYear()
							+ " - " + list.get(i).getDasaTimeStr();
				} else {
					timeRange = list.get(i * 3 + j - 1).getDasaTimeStr() + " - " + list.get(i * 3 + j).getDasaTimeStr();
				}
				String planetName = list.get(i * 3 + j).getPlanetName();
				float textWidth = utility.getTextWidth(planetName, krutiDevRegularFont, 16);

				drawColorShape.drawText(tX + 5, tY, planetName, krutiDevRegularFont, 15,
						planetColors[list.get(i * 3 + j).getPlanetNo()]);
				drawColorShape.drawText(tX + textWidth + 3, tY, "- " + timeRange, poppinsRegularFont, 10,
						planetColors[list.get(i * 3 + j).getPlanetNo()]);
				tX = tX + tableWidth + 10f;
			}
			tX = 39f;
			tY = tY - 220f;
		}
	}

	void printPratyantraHeaderText(float x, float y, float tableWidth, ArrayList<DasaBean> list) throws IOException {
		float tX = x;
		float tY = y;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				String timeRange = dashaStartDate + " - " + anterDasaList.get(hCount).getDasaTimeStr();
				float textWidth = utility.getTextWidth(anterDasaList.get(hCount).getPlanetSubPlaName(),
						krutiDevRegularFont, 15);
				drawColorShape.drawText(tX + (tableWidth - textWidth) / 2, tY + 6,
						anterDasaList.get(hCount).getPlanetSubPlaName(), krutiDevRegularFont, 15, Color.BLACK);
				textWidth = utility.getTextWidth(timeRange, poppinsRegularFont, 8);
				drawColorShape.drawText(tX + (tableWidth - textWidth) / 2, tY - 6, timeRange, poppinsRegularFont, 8,
						Color.BLACK);

				tX = tX + tableWidth + 10f;
				hCount++;
			}
			tX = 39f;
			tY = tY - 220f;

		}
	}

	void populateAnterDashaData(float x, float y, float tableWidth) throws IOException {
		float rowHeight = 19;
		float vy = y - 5;
		float vx = x + 12;
		float sidePadding = 12f;
		int selectedVimPlanet = 0;
		for (int i = 0; i < 9; i++) {
			ArrayList<DasaBean> innerlist = calculation.getAntaraDasaFormmattedData(i, selectedVimPlanet);
			float ty = utility.getTextBaseline(poppinsRegularFont, vy - rowHeight, rowHeight, 13);
			for (int j = 0; j < innerlist.size(); j++) {
				drawColorShape.drawText(vx, ty, innerlist.get(j).getPlanetName(), krutiDevRegularFont, 13,
						planetColors[innerlist.get(j).getPlanetNo()]);
				drawColorShape.drawText(vx + tableWidth / 2 - 15, ty, innerlist.get(j).getDasaTimeStr(),
						poppinsRegularFont, 9, planetColors[innerlist.get(j).getPlanetNo()]);
				ty = ty - rowHeight;
			}
			if (i == 2 || i == 5 || i == 8) {
				vx = x + 12;
				vy = vy - 220;
			} else {
				vx = tableWidth + vx + sidePadding;
			}
			selectedVimPlanet++;
		}

	}

	void populatePratyantraDashaData(float x, float y, float tableWidth) throws IOException {
		float rowHeight = 19;
		float vy = y - 5;
		float vx = x + 12;
		float sidePadding = 12f;
		for (int i = 0; i < 9; i++) {
			float ty = utility.getTextBaseline(poppinsRegularFont, vy - rowHeight, rowHeight, 13);
			for (int j = 0; j < 9; j++) {
				// drawShape.drawText(vx + 5, vy,
				// pratyantraDasaList.get(rCount).getPlanetName(), 12, krutiDevRegularFont);
				drawColorShape.drawText(vx, ty, pratyantraDasaList.get(rCount).getPlanetName(), krutiDevRegularFont, 12,
						planetColors[pratyantraDasaList.get(rCount).getPlanetNo()]);
				// drawShape.drawText(vx + tableWidth / 2 - 15, vy,
				// pratyantraDasaList.get(rCount).getDasaTimeStr(), 10,
				// poppinsRegularFont);
				drawColorShape.drawText(vx + tableWidth / 2 - 15, ty, pratyantraDasaList.get(rCount).getDasaTimeStr(),
						poppinsRegularFont, 9, planetColors[pratyantraDasaList.get(rCount).getPlanetNo()]);
				dashaStartDate = pratyantraDasaList.get(rCount).getDasaTimeStr();
				ty = ty - rowHeight;
				rCount++;
			}
			if (i == 2 || i == 5 || i == 8) {
				vx = x + 12;
				vy = vy - 220;
			} else {
				vx = tableWidth + vx + sidePadding;
			}
		}
	}

	void drawTable(float x, float y, float width, float height, int rowCount, Color[] headerGradientColors,
			Color borderColor) throws Exception {
		float startY = y;
		float rowHeight = 18f;
		float headerHeight = 30f;
		float gap = 18f;
		float sidePadding = 5f;
		contentStream.saveGraphicsState();

		// 1. HEADER
		InputStream inputStream = getClass().getResourceAsStream("/images/header_bottom_corners_batik.svg");
		if (inputStream == null) {
			throw new FileNotFoundException("header_bottom_corners_batik.svg not found");
		}
		inputStream.close();
		drawColorShape.drawGradientHeader(x, y, width, headerHeight, 5, headerGradientColors[1],
				headerGradientColors[0], headerGradientColors[1], headerGradientColors[1]);

		// 2. FIRST ROUNDED ROW

		float bodyX = x + sidePadding;
		float bodyWidth = width - (sidePadding * 2);
		Color rowColor = new Color(251, 241, 225);
		startY = y - 24;
		contentStream.setNonStrokingColor(rowColor);
		drawColorShape.drawTopRoundedRect(bodyX, startY, bodyWidth, rowHeight, 5f, rowColor);
		contentStream.fill();

		// 3. NORMAL ROWS

		for (int i = 0; i < rowCount; i++) {
			startY -= rowHeight + 1f;
			if (i % 2 == 0) {
				rowColor = new Color(252, 246, 235);
			} else {
				rowColor = new Color(251, 241, 225);
			}
			contentStream.setNonStrokingColor(rowColor);
			drawColorShape.drawSolidRectAngle(bodyX, startY, bodyWidth, rowHeight, rowColor);
			contentStream.fill();
		}

		// 4. BOTTOM BORDER
		float bottomY = startY;
		// Body starts at y - gap
		float bodyTopY = y - gap;
		// Actual body height
		float bodyHeight = bodyTopY - bottomY + rowHeight;
		// 5. DRAW LEFT / RIGHT / BOTTOM BORDER NO TOP LINE
		contentStream.setStrokingColor(headerGradientColors[1]);
		contentStream.setLineWidth(.5f);
		drawColorShape.drawRoundedBottomRectangle(x, bottomY - 5, width, bodyHeight + 5, 8f);
		contentStream.stroke();
		contentStream.restoreGraphicsState();
	}

}
