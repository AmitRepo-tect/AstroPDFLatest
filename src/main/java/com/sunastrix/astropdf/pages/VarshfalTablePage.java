package com.sunastrix.astropdf.pages;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astroganitlib.model.BirthDetailBean;
import com.sunastrix.astropdf.calculation.PlanetAndSunPlanetPositionCalculation;
import com.sunastrix.astropdf.calculation.VarshfalCalculation;
import com.sunastrix.astropdf.model.BasicPlanetDataModel;
import com.sunastrix.astropdf.model.PageInfo;
import com.sunastrix.astropdf.model.VarshfalPlanetData;

public class VarshfalTablePage extends BasePage {
	public VarshfalTablePage(DesktopHoroNew desktopHoro, BirthDetailBean birthDetailBean) {
		this.desktopHoro = desktopHoro;
		this.birthDetailBean = birthDetailBean;
	}

	public PageInfo drawPage(PDDocument document, PDType0Font poppinsRegularFont, PDType0Font krutiDevRegularFont) {
		String pageHeading = "o\"kZQy xzg fLFkfr";
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
			drawHeader(pageWidth, pageHeight, krutiDevRegularFont, "o\"kZQy xzg fLFkfr");

			// Draw Basic Details
			float tableX = 39f;
			float tableY = 680f;
			float tableWidth = 514f;
			float tableHeight = 30 + 13 * 24 + 12 * 1;

			float bgX = tableX - 10f;
			float bgY = tableY - tableHeight + 26 - 10f;
			float bgWidth = tableWidth + 19f;
			float bgHeight = tableHeight + 37f;

			float headWidth = 170f;
			float headHeight = 26f;
			float headMargin = 13.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "xzg fLFkfr", 14);
			drawPlanetTable(tableX, tableY, tableWidth);

			drawFooter(poppinsRegularFont, poppinsRegularFont, 15);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
		return pageDetail.getPageInfo();
	}

	private void drawPlanetTable(float x, float y, float width) throws IOException {
		float tx = x;
		float ty = y;
		float headerHeight = 30f;
		float rowHeight = 24f;
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

		float columnWidth = width / 3;
		float cx = x;
		float tableHeight = headerHeight + rowCount * rowHeight + rowCount * divider;
		for (int i = 0; i < 2; i++) {
			cx += columnWidth;
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
		Calendar calendar = Calendar.getInstance();
		ArrayList<VarshfalPlanetData> list = new VarshfalCalculation().getVarshfalPlanetsData(birthDetailBean,
				calendar.get(Calendar.YEAR), 1);
		String[] heading = constantHindi.varshfalPlanetPosHeading;
		float headerHeight = 26f;
		float tx = x;
		float starty = utility.getTextBaseline(krutiDevRegularFont, y, headerHeight, 14);
		float rowHeight = 24f;
		float boxWidth = 50;
		float horizontalGap = width / 3;
		for (int i = 0; i < heading.length; i++) {
			drawColorShape.drawCenteredBoldText(tx, horizontalGap, starty, heading[i], 18, krutiDevRegularFont,
					Color.WHITE);
			tx += horizontalGap;
		}
		starty = y - rowHeight - 1;
		starty = utility.getTextBaseline(krutiDevRegularFont, starty, rowHeight, 14);
		VarshfalPlanetData varshfalPlanetData;
		for (int i = 0; i < list.size(); i++) {
			varshfalPlanetData = list.get(i);
			tx = x;

			/*
			 * drawColorShape.drawCenteredBoldText(varshfalPlanetData.getPlaName(), tx, tx +
			 * horizontalGap, starty, krutiDevRegularFont, 16, planetColors[i]);
			 */
			drawColorShape.drawCenteredBoldText(tx, horizontalGap, starty, varshfalPlanetData.getPlaName(), 16,
					krutiDevRegularFont, planetColors[i]);
			/*
			 * drawColorShape.drawText(tx + 15, starty, varshfalPlanetData.getPlaName(),
			 * krutiDevRegularFont, 16, planetColors[i]);
			 */
			tx += horizontalGap;
			/*
			 * drawColorShape.drawText(tx + 15, starty,
			 * String.valueOf(varshfalPlanetData.getSign()), krutiDevRegularFont, 16,
			 * planetColors[i]);
			 */
			/*
			 * drawColorShape.drawCenteredBoldText(String.valueOf(varshfalPlanetData.getSign
			 * ()), tx, tx + horizontalGap, starty, krutiDevRegularFont, 16,
			 * planetColors[i]);
			 */
			drawColorShape.drawCenteredBoldText(tx, horizontalGap, starty, String.valueOf(varshfalPlanetData.getSign()),
					16, krutiDevRegularFont, planetColors[i]);
			tx += horizontalGap;
			/*
			 * drawColorShape.drawText(tx + 15, starty, varshfalPlanetData.getDegree(),
			 * poppinsRegularFont, 12, planetColors[i]);
			 */
			/*
			 * drawColorShape.drawCenteredBoldText(varshfalPlanetData.getDegree(), tx, tx +
			 * horizontalGap, starty, poppinsRegularFont, 12, planetColors[i]);
			 */
			drawColorShape.drawCenteredBoldText(tx, horizontalGap, starty, varshfalPlanetData.getDegree(), 12,
					poppinsRegularFont, planetColors[i]);

			starty -= rowHeight + 1;
		}

	}
}
