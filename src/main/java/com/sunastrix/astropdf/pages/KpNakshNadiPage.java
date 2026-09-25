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
import com.sunastrix.astropdf.calculation.KPPlanetSignificationView2Calculation;
import com.sunastrix.astropdf.calculation.KpCilSubCalculation;
import com.sunastrix.astropdf.calculation.KpKundliCalculation;
import com.sunastrix.astropdf.calculation.KpNakshtraNadiCalculation;
import com.sunastrix.astropdf.model.HouseSignificatorsBean;
import com.sunastrix.astropdf.model.KPCilSubBean;
import com.sunastrix.astropdf.model.KPNakshatraNadiBean;
import com.sunastrix.astropdf.model.PageInfo;
import com.sunastrix.astropdf.model.PlanetSignificationView2Bean;

public class KpNakshNadiPage extends BasePage {
	Color[] planetColors = { new Color(230, 30, 70), new Color(200, 30, 160), new Color(230, 110, 0),
			new Color(0, 90, 210), new Color(220, 30, 30), new Color(0, 150, 150), new Color(210, 130, 0),
			new Color(50, 60, 200), new Color(0, 135, 60), new Color(140, 40, 190), new Color(180, 80, 0),
			new Color(0, 130, 190), new Color(200, 40, 90) };

	public KpNakshNadiPage(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	public PageInfo drawPage(PDDocument document, PDType0Font poppinsRegularFont, PDType0Font krutiDevRegularFont) {
		String pageHeading = "xzg funsZ'ku ¼u{k= ukM+h½ vkSj dLiy baVjfyaDl ¼lc½";
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
			float tableX = 39f;
			float tableY = 680f;
			float tableWidth = 514f;
			float tableHeight = 26 + 9 * 20 + 5 * 1;

			float bgX = tableX - 10f;
			float bgY = tableY - tableHeight + 26 - 15f;
			float bgWidth = tableWidth + 19f;
			float bgHeight = tableHeight + 37f;

			float headWidth = 170f;
			float headHeight = 26f;
			float headMargin = 13.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin,
					"xzg funsZ'ku ¼u{k= ukM+h½", 14);
			drawNakshNadiTable(tableX, tableY, tableWidth);
			tableX = 39f;
			tableY = 400f;
			tableWidth = 514f;
			tableHeight = 26 + 12 * 20 + 5 * 1;

			bgX = tableX - 10f;
			bgY = tableY - tableHeight + 26 - 15f;
			bgWidth = tableWidth + 19f;
			bgHeight = tableHeight + 37f;

			headWidth = 170f;
			headHeight = 26f;
			headMargin = 13.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "dLiy baVjfyaDl ¼lc½", 14);
			drawCILSubTable(tableX, tableY, tableWidth);
			drawFooter(poppinsRegularFont, poppinsRegularFont, 15);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return pageDetail.getPageInfo();
	}

	private void drawNakshNadiTable(float x, float y, float width) throws IOException {
		float tx = x;
		float ty = y;
		float headerHeight = 26f;
		float rowHeight = 20f;
		float radius = 8;
		int rowCount = 9;
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

		System.out.println("Width--" + width);
		// float[] columnWidth = { 90f, 90f, 150f, 110f };
		float columnWidth = width / 3;
		float cx = x;
		float tableHeight = headerHeight + rowCount * rowHeight + rowCount * divider;
		for (int i = 0; i < 2; i++) {
			cx += columnWidth;
			System.out.println(cx);
			drawColorShape.drawLine(cx, ty, cx, ty + tableHeight, 1f, gridColor);
		}
		contentStream.saveGraphicsState();
		contentStream.setStrokingColor(outerBorderColor);
		contentStream.setLineWidth(1f);
		drawColorShape.drawRoundedRectangle(tx, ty, width, tableHeight, radius);
		contentStream.stroke();
		contentStream.restoreGraphicsState();
		populateNakshNadiTable(x, y, width);
	}

	void populateNakshNadiTable(float x, float y, float width) throws IOException {
		String[] heading = constantHindi.nakshNadiHeading;
		ArrayList<KPNakshatraNadiBean> list = new KpNakshtraNadiCalculation(desktopHoro).getNakshtraNadiData();
		float headerHeight = 26f;
		float tx = x;
		float rowHeight = 20f;
		float starty = utility.getTextBaseline(krutiDevRegularFont, y, headerHeight, 14);
		float columnWidth = width / 3;
		for (int i = 0; i < heading.length; i++) {
			drawColorShape.drawBoldText(tx + 20f, starty, heading[i], krutiDevRegularFont, 16, Color.WHITE);
			if (i < heading.length) {
				tx += columnWidth;
			}
		}
		starty = starty - headerHeight - 3f;
		starty = utility.getTextBaseline(krutiDevRegularFont, starty, rowHeight, 14);

		KPNakshatraNadiBean kpNakshatraNadiBean;
		for (int i = 0; i < list.size(); i++) {
			kpNakshatraNadiBean = list.get(i);
			tx = x;
			drawColorShape.drawText(tx + 20, starty, String.valueOf(kpNakshatraNadiBean.getPlanet()),
					krutiDevRegularFont, 16, planetColors[i]);
			tx += columnWidth;
			drawColorShape.drawText(tx + 20, starty, kpNakshatraNadiBean.getStarLord(), krutiDevRegularFont, 16,
					planetColors[i]);
			tx += columnWidth;
			drawColorShape.drawText(tx + 20, starty, kpNakshatraNadiBean.getSubLord(), krutiDevRegularFont, 16,
					planetColors[i]);

			starty -= rowHeight + 1;
		}
	}

	private void drawCILSubTable(float x, float y, float width) throws IOException {
		float tx = x;
		float ty = y;
		float headerHeight = 26f;
		float rowHeight = 20f;
		float radius = 8;
		int rowCount = 12;
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

		System.out.println("Width--" + width);
		// float[] columnWidth = { 90f, 90f, 150f, 110f };
		float firstColumnWidth = 60f;
		float columnWidth = (width - firstColumnWidth) / 4;
		float cx = x + firstColumnWidth;
		float tableHeight = headerHeight + rowCount * rowHeight + rowCount * divider;
		for (int i = 0; i < 4; i++) {
			drawColorShape.drawLine(cx, ty, cx, ty + tableHeight, 1f, gridColor);
			cx += columnWidth;
		}
		contentStream.saveGraphicsState();
		contentStream.setStrokingColor(outerBorderColor);
		contentStream.setLineWidth(1f);
		drawColorShape.drawRoundedRectangle(tx, ty, width, tableHeight, radius);
		contentStream.stroke();
		contentStream.restoreGraphicsState();
		populateCILSubTable(x, y, width);
	}

	void populateCILSubTable(float x, float y, float width) throws IOException {
		String[] heading = constantHindi.cusperInterLinksHeading;
		ArrayList<KPCilSubBean> list = new KpCilSubCalculation(desktopHoro).getKPCilSubData();
		float headerHeight = 26f;

		float firstColumnWidth = 60f;
		float columnWidth = (width - firstColumnWidth) / 4;
		float tx = x + firstColumnWidth;
		float rowHeight = 20f;
		float starty = utility.getTextBaseline(krutiDevRegularFont, y, headerHeight, 14);
		for (int i = 0; i < heading.length; i++) {
			drawColorShape.drawBoldText(tx + 20f, starty, heading[i], krutiDevRegularFont, 16, Color.WHITE);
			if (i < heading.length) {
				tx += columnWidth;
			}
		}
		starty = starty - headerHeight - 3f;
		starty = utility.getTextBaseline(krutiDevRegularFont, starty, rowHeight, 14);

		KPCilSubBean kpCilSubBean;
		for (int i = 0; i < list.size(); i++) {
			kpCilSubBean = list.get(i);
			tx = x;
			drawColorShape.drawText(tx + 20, starty, String.valueOf(kpCilSubBean.getHouseNo()), krutiDevRegularFont, 16,
					planetColors[i]);
			tx += firstColumnWidth;
			drawColorShape.drawText(tx + 20, starty, kpCilSubBean.getT1(), poppinsRegularFont, 12, planetColors[i]);
			tx += columnWidth;
			drawColorShape.drawText(tx + 20, starty, kpCilSubBean.getT2(), poppinsRegularFont, 12, planetColors[i]);
			tx += columnWidth;
			drawColorShape.drawText(tx + 20, starty, kpCilSubBean.getT3(), poppinsRegularFont, 12, planetColors[i]);
			tx += columnWidth;
			drawColorShape.drawText(tx + 20, starty, kpCilSubBean.getT4(), poppinsRegularFont, 12, planetColors[i]);

			starty -= rowHeight + 1;
		}
	}

}
