package com.sunastrix.astropdf.pages;

import java.awt.Color;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astroganitlib.model.BirthDetailBean;
import com.sunastrix.astropdf.model.PageInfo;
import com.sunastrix.astropdf.service_impl.PDFGenerateColorServiceImpl;

public class BirthDetailPage extends BasePage {
	Color[] planetColors = { new Color(105, 20, 55), new Color(110, 20, 80), new Color(105, 60, 0),
			new Color(20, 65, 110), new Color(110, 30, 40), new Color(20, 85, 85), new Color(105, 60, 0),
			new Color(20, 45, 100), new Color(15, 75, 40), new Color(90, 45, 105), new Color(120, 70, 20),
			new Color(40, 100, 55), new Color(75, 35, 90), new Color(105, 20, 55), new Color(110, 20, 80),
			new Color(105, 60, 0), new Color(20, 65, 110), new Color(110, 30, 40), new Color(20, 85, 85),
			new Color(105, 60, 0) };

	public BirthDetailPage(DesktopHoroNew desktopHoro, BirthDetailBean birthDetailBean) {
		this.desktopHoro = desktopHoro;
		this.birthDetailBean = birthDetailBean;
	}

	public PageInfo drawPage(PDDocument document, PDType0Font poppinsRegularFont, PDType0Font krutiDevRegularFont)
			throws IOException {
		String pageHeading = "tUe fooj.k vkSj vodgM+k pØ";
		this.document = document;
		this.poppinsRegularFont = poppinsRegularFont;
		this.krutiDevRegularFont = krutiDevRegularFont;
		PageDetail pageDetail = addPage(pageHeading);
		float cornerSize = 30f;

		try (PDPageContentStream cs = new PDPageContentStream(document, pageDetail.getPage())) {

			this.contentStream = cs;
			drawShape.initialize(pageHeight, pageWidth, document, cs);
			drawColorShape.initialize(pageHeight, pageWidth, document, cs);
			drawPageBorder(document, pageDetail.getPage());
			byte[] svgBytes = getClass().getResourceAsStream("/images/corner_left_top.svg").readAllBytes();
			float margin = 15f;
			drawColorShape.drawSvg(svgBytes, margin, pageHeight - margin - cornerSize, cornerSize, cornerSize, 0);
			drawColorShape.drawSvg(svgBytes, pageWidth - margin - cornerSize, pageHeight - margin - cornerSize,
					cornerSize, cornerSize, 1);
			drawColorShape.drawSvg(svgBytes, pageWidth - margin - cornerSize, margin, cornerSize, cornerSize, 2);
			drawColorShape.drawSvg(svgBytes, margin, margin, cornerSize, cornerSize, 3);
			drawHeader(pageWidth, pageHeight, krutiDevRegularFont, pageHeading);
			// Draw Basic Details
			float tableX = 40f;
			float tableY = 680f;
			float tableWidth = 495f;
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

			tableY = 520f;
			tableHeight = 100.0f;
			bgX = tableX - 10f;
			bgY = tableY - tableHeight + 26 - 15f;
			bgWidth = tableWidth + 19f;
			bgHeight = tableHeight + 37f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "iapkax fooj.k", 14);
			String[] panchangLabels = constantHindi.panchangLabelNew;
			String[] panchangValues = { desktopHoro.getPakshaName(), desktopHoro.getTithiName(),
					desktopHoro.getNakshatraName(), desktopHoro.getHinduWeekdayName(), desktopHoro.getYoganame(),
					desktopHoro.getKaranName(), utility.getFormattedTime(desktopHoro.getSunRiseTimeIntArr()),
					utility.getFormattedTime(desktopHoro.getSunSetTimeIntArr()) };
			drawTable(tableX, tableY, tableWidth, 4, panchangLabels, panchangValues, krutiDevRegularFont, 14);

			tableY = 360f;
			tableHeight = 250.0f;
			bgX = tableX - 10f;
			bgY = tableY - tableHeight + 26 - 15f;
			bgWidth = tableWidth + 19f;
			bgHeight = tableHeight + 37f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "vodgM+k pØ", 14);
			String[] AvakahadaLabels = constantHindi.avakahadaChakarLabel;
			String[] AvakahadaValues = { desktopHoro.getPayaName(), desktopHoro.getVarnaName(),
					desktopHoro.getYoniName(), desktopHoro.getGanaName(), desktopHoro.getVasyaName(),
					desktopHoro.getNadiName(), getBalanceOfDasha(desktopHoro.getBalanceOfDashaIntArr()),
					desktopHoro.getLagnaSign(), desktopHoro.getLagnaLordName(), desktopHoro.getRasiName(),
					desktopHoro.getRasiLordName(), desktopHoro.getNakshatraName(), desktopHoro.getNakshatraLordName(),
					desktopHoro.getJulianDayValue(), desktopHoro.getIndianSunSignName(), desktopHoro.getSunSignName(),
					"" + desktopHoro.getAyanamsaDms(birthDetailBean.getLanguageCode()),
					"" + desktopHoro.getAyanamsaType(), desktopHoro.getObliquityDms(birthDetailBean.getLanguageCode()),
					utility.getFormattedTime(desktopHoro.getSiderealTimeIntArr()) };
			drawTable(tableX, tableY, tableWidth, 10, AvakahadaLabels, AvakahadaValues, krutiDevRegularFont, 14);

			drawFooter(poppinsRegularFont, poppinsRegularFont, 15);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		/*
		 * Color gradientEnd = new Color(255, 174, 35); Color gradientStart = new
		 * Color(235, 78, 0);
		 */
		/*
		 * Color gradientStart = new Color(235, 78, 0); Color gradientMiddle = new
		 * Color(235, 105, 85); Color gradientEnd = new Color(125, 75, 170);
		 */
		Color gradientStart = new Color(220, 85, 25);
		Color gradientMiddle = new Color(245, 135, 45);
		Color gradientEnd = new Color(255, 195, 95);
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
		Color textColor = new Color(75, 45, 30);
		float tx = x;
		float ty = utility.getTextBaseline(poppinsRegularFont, y, rowHeight, 14);
		float[] colWidth = { 102f, 144f, 102f, 142f };
		for (int i = 0; i < labels.length; i = i + 2) {
			drawColorShape.drawBoldText(tx + 10, ty, labels[i], krutiDevRegularFont, 14, textColor);
			tx += colWidth[0];
			drawColorShape.drawText(tx + 10, ty, values[i], valueFont, valueFontSize, planetColors[i]);
			tx += colWidth[1];
			drawColorShape.drawBoldText(tx + 10, ty, labels[i + 1], krutiDevRegularFont, 14, textColor);
			tx += colWidth[2];
			drawColorShape.drawText(tx + 10, ty, values[i + 1], valueFont, valueFontSize, planetColors[i + 1]);
			tx = x;
			ty -= rowHeight + 1;
		}
	}

	String getBirthDate() {
		int[] arr = desktopHoro.getBirthDate();
		return arr[0] + "-" + constantHindi.monthName[arr[1] - 1] + "-" + arr[2];
	}

	String getBalanceOfDasha(int[] arr) {
		return constantHindi.nakshLord[arr[0]] + " " + arr[1] + constantHindi.year + " " + arr[2] + constantHindi.month
				+ " " + arr[3] + constantHindi.day;
	}
}
