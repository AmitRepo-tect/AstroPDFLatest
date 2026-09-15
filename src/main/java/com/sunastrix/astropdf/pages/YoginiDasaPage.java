package com.sunastrix.astropdf.pages;

import java.awt.Color;
import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astroganitlib.model.BirthDetailBean;
import com.sunastrix.astropdf.calculation.YoginiDashaCalculation;
import com.sunastrix.astropdf.model.CharAntaraDashaModel;
import com.sunastrix.astropdf.model.YoginiDashaModel;

public class YoginiDasaPage extends BasePage {
	Color startColor = new Color(130, 0, 14); // #82000E
	Color middleColor = new Color(201, 0, 28); // #C9001C
	Color endColor = new Color(130, 0, 14); // #82000E
	Color[][] headerGradientColors = { { new Color(255, 224, 145), new Color(255, 197, 75) },
			{ new Color(190, 213, 255), new Color(135, 174, 245) },
			{ new Color(195, 242, 207), new Color(145, 220, 170) },
			{ new Color(255, 205, 220), new Color(255, 160, 190) },
			{ new Color(255, 205, 235), new Color(245, 160, 210) },
			{ new Color(255, 225, 150), new Color(255, 195, 75) },
			{ new Color(190, 225, 255), new Color(130, 195, 250) },
			{ new Color(255, 205, 210), new Color(255, 155, 170) },
			{ new Color(190, 240, 238), new Color(125, 210, 210) },
			{ new Color(255, 225, 150), new Color(255, 195, 75) },
			{ new Color(190, 225, 255), new Color(130, 195, 250) },
			{ new Color(255, 205, 210), new Color(255, 155, 170) },
			{ new Color(190, 240, 238), new Color(125, 210, 210) } };
	Color[] headerBorderColors = { new Color(140, 90, 0), new Color(45, 75, 135), new Color(35, 105, 60),
			new Color(145, 45, 80), new Color(145, 45, 110), new Color(145, 90, 0), new Color(145, 45, 80),
			new Color(145, 45, 110), new Color(145, 90, 0) };
	Color[][] headerGradientColors1 = { { new Color(255, 205, 220), new Color(255, 160, 190) },
			{ new Color(255, 205, 235), new Color(245, 160, 210) },
			{ new Color(255, 225, 150), new Color(255, 195, 75) },
			{ new Color(190, 225, 255), new Color(130, 195, 250) },
			{ new Color(255, 205, 210), new Color(255, 155, 170) },
			{ new Color(190, 240, 238), new Color(125, 210, 210) },
			{ new Color(255, 224, 145), new Color(255, 197, 75) },
			{ new Color(190, 213, 255), new Color(135, 174, 245) },
			{ new Color(195, 242, 207), new Color(145, 220, 170) } };

	Color[] planetColors = { new Color(105, 20, 55), new Color(110, 20, 80), new Color(105, 60, 0),
			new Color(20, 65, 110), new Color(110, 30, 40), new Color(20, 85, 85), new Color(105, 60, 0),
			new Color(20, 45, 100), new Color(15, 75, 40) };
	ArrayList<YoginiDashaModel> dataList;
	int count = 0;

	public YoginiDasaPage(DesktopHoroNew desktopHoro, BirthDetailBean birthDetailBean) {
		this.desktopHoro = desktopHoro;
		this.birthDetailBean = birthDetailBean;
		dataList = getYoginiDashaData();
	}

	ArrayList<YoginiDashaModel> getYoginiDashaData() {
		YoginiDashaCalculation yoginiDashaCalculation = new YoginiDashaCalculation(birthDetailBean, desktopHoro);
		return yoginiDashaCalculation.getYoginiDashaData();
	}

	public void printDasha(PDDocument document, PDType0Font poppinsRegularFont, PDType0Font krutiDevRegularFont) {
		drawPage(document, poppinsRegularFont, krutiDevRegularFont);
		drawPage(document, poppinsRegularFont, krutiDevRegularFont);
	}

	public void drawPage(PDDocument document, PDType0Font poppinsRegularFont, PDType0Font krutiDevRegularFont) {
		this.document = document;
		this.poppinsRegularFont = poppinsRegularFont;
		this.krutiDevRegularFont = krutiDevRegularFont;
		PDPage page = new PDPage(PDRectangle.A4);
		document.addPage(page);
		pageWidth = page.getMediaBox().getWidth();
		pageHeight = page.getMediaBox().getHeight();

		try (PDPageContentStream cs = new PDPageContentStream(document, page)) {

			this.contentStream = cs;
			drawShape.initialize(pageHeight, pageWidth, document, cs);
			drawColorShape.initialize(pageHeight, pageWidth, document, cs);
			drawPageBorder(document, page);
			drawCornerImages();
			drawHeader(pageWidth, pageHeight, krutiDevRegularFont, "foa'kksÙkjh varj n'kk ");
			// Draw Basic Details
			float tableX = 39f;
			float tableY = 700f;
			float tableWidth = ((pageWidth - 100f) / 2);

			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					drawTable(tableX, tableY, tableWidth, 7, headerGradientColors[i * 2 + j],
							headerBorderColors[i * 2 + j]);
					tableX = tableX + tableWidth + 25f;
				}
				// Move to next row of tables
				tableX = 39f;
				tableY = tableY - 290f;
			}

			drawFooter(poppinsRegularFont, poppinsRegularFont, 15);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
	}

	void drawTable(float x, float y, float width, int rowCount, Color[] headerGradientColors, Color borderColor)
			throws Exception {
		float startY = y;
		float rowHeight = 25f;
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
		drawColorShape.drawGradientHeader(x, y, width, headerHeight, 8, headerGradientColors[1],
				headerGradientColors[0], headerGradientColors[1], headerGradientColors[1]);

		// 2. FIRST ROUNDED ROW

		float bodyX = x + sidePadding;
		float bodyWidth = width - (sidePadding * 2);
		Color rowColor = new Color(251, 241, 225);
		startY = y - 30;
		contentStream.setNonStrokingColor(rowColor);
		drawColorShape.drawTopRoundedRect(bodyX, startY, bodyWidth, rowHeight, 8f, rowColor);
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
			if (i == rowCount - 1) {
				drawColorShape.drawBottomRoundedRect(bodyX, startY, bodyWidth, rowHeight, 8f, rowColor);
			} else {
				drawColorShape.drawSolidRectAngle(bodyX, startY, bodyWidth, rowHeight, rowColor);
			}

			contentStream.fill();
		}
		contentStream.setNonStrokingColor(rowColor);

		contentStream.fill();
		// 4. BOTTOM BORDER
		float bottomY = startY;
		// Body starts at y - gap
		float bodyTopY = y - gap;
		// Actual body height
		float bodyHeight = bodyTopY - bottomY + rowHeight;
		// 5. DRAW LEFT / RIGHT / BOTTOM BORDER NO TOP LINE
		contentStream.setStrokingColor(headerGradientColors[1]);
		contentStream.setLineWidth(.5f);
		drawColorShape.drawRoundedBottomRectangle(x, bottomY - 5, width, bodyHeight + 5, 18f);
		contentStream.stroke();
		contentStream.restoreGraphicsState();

		populateYoginiDashaData(x, y, width, headerHeight, rowHeight, dataList.get(count));
		count++;
	}

	void populateYoginiDashaData(float x, float y, float width, float headerHeight, float rowHeight,
			YoginiDashaModel yoginiDashaModel) throws IOException {
		float columnWidth1 = 55;
		float columnWidth2 = 100;
		String heading = yoginiDashaModel.getPlanetName() + " " + yoginiDashaModel.getDuration() + " ¼ "
				+ yoginiDashaModel.getStartYear() + "&" + yoginiDashaModel.getEndYear() + " ½";
		float starty = utility.getTextBaseline(krutiDevRegularFont, y, headerHeight, 14);
		drawColorShape.drawCenteredBoldText(x, width, starty, heading, 14, krutiDevRegularFont, Color.BLACK);
		ArrayList<CharAntaraDashaModel> list = yoginiDashaModel.getAntaraDashaList();
		starty = starty - headerHeight - 2;
		for (int i = 0; i < list.size(); i++) {

			drawColorShape.drawText(x + 8, starty, list.get(i).getPlanetName(), krutiDevRegularFont, 13,
					planetColors[i]);
			drawColorShape.drawText(x + columnWidth1, starty, list.get(i).getStartDate(), krutiDevRegularFont, 13,
					planetColors[i]);
			drawColorShape.drawText(x + columnWidth1 + columnWidth2, starty, list.get(i).getEndDate(),
					krutiDevRegularFont, 13, planetColors[i]);
			starty -= rowHeight + 1;
		}

	}
}
