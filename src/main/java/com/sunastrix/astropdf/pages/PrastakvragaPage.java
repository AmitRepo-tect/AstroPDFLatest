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
import com.sunastrix.astropdf.calculation.PrashtakVargaCalculation;
import com.sunastrix.astropdf.model.PageInfo;
import com.sunastrix.astropdf.model.PrastharashtakvargaModel;

public class PrastakvragaPage extends BasePage {
	/*
	 * Color[] planetColors = { new Color(105, 20, 55), new Color(110, 20, 80), new
	 * Color(105, 60, 0), new Color(20, 65, 110), new Color(110, 30, 40), new
	 * Color(20, 85, 85), new Color(105, 60, 0), new Color(20, 45, 100), new
	 * Color(15, 75, 40) };
	 */
	Color[] rashiColors = { new Color(115, 25, 25), new Color(30, 65, 35), new Color(15, 75, 40), new Color(20, 50, 95),
			new Color(115, 55, 10), new Color(75, 55, 35), new Color(85, 25, 95), new Color(90, 15, 30),
			new Color(110, 75, 15), new Color(45, 50, 55), new Color(15, 30, 75), new Color(80, 40, 75) };
	ArrayList<PrastharashtakvargaModel> prashtakVargaList;
	int count = 0;

	String[] headings;

	public PrastakvragaPage(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	public PageInfo printPrastakvargaTable(PDDocument document, PDType0Font poppinsRegularFont,
			PDType0Font krutiDevRegularFont) {
		this.document = document;
		this.poppinsRegularFont = poppinsRegularFont;
		this.krutiDevRegularFont = krutiDevRegularFont;
		prashtakVargaList = new PrashtakVargaCalculation(desktopHoro).getPrashtakVargaData();
		headings = constantHindi.prastakvargaLabels;
		PageInfo pageInfo = drawPage();
		drawPage();
		drawPage();
		drawPage();
		return pageInfo;
	}

	public PageInfo drawPage() {
		String pageHeading = "çLrjv\"VdoxZ rkfydk";
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
			float tableY = 670f;
			float tableWidth = pageWidth - 80;
			float tableHeight = 30 + 9 * 24 + 9;

			float bgX = tableX - 10f;
			float bgY = tableY - tableHeight + 26 - 10f;
			float bgWidth = tableWidth + 19f;
			float bgHeight = tableHeight + 37f;

			float headWidth = 170f;
			float headHeight = 30f;
			float headMargin = 15.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, headings[count], 14);
			drawTable(tableX, tableY, tableWidth, tableHeight);
			populatePrastakTable(tableX, tableY, tableWidth, tableHeight);

			tableY = tableY - tableHeight - 70;
			bgX = tableX - 10f;
			bgY = tableY - tableHeight + 26 - 8f;
			bgWidth = tableWidth + 19f;
			bgHeight = tableHeight + 37f;

			headWidth = 170f;
			headHeight = 30f;
			headMargin = 15.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, headings[count], 12);
			drawTable(tableX, tableY, tableWidth, tableHeight);
			populatePrastakTable(tableX, tableY, tableWidth, tableHeight);

			drawFooter(poppinsRegularFont, poppinsRegularFont, 15);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
		return pageDetail.getPageInfo();
	}

	private void drawTable(float x, float y, float width, float height) throws IOException {
		float tx = x;
		float ty = y;
		float headerHeight = 30;
		float rowHeight = 24f;
		float radius = 8;
		int rowCount = 9;
		float divider = 1f;
		Color gradientEnd = new Color(255, 174, 35);
		Color gradientStart = new Color(235, 78, 0);
		Color headerBorderColor = new Color(205, 50, 0);
		Color outerBorderColor = new Color(215, 125, 40);
		Color gridColor = new Color(225, 215, 200);
		Color alternateRowColor = new Color(251, 241, 225);//new Color(255, 250, 240);
		Color rowColor;
		drawColorShape.drawTopRoundedGradientRect(tx, ty, width, headerHeight, radius - 2, gradientStart, gradientEnd,
				false);

		ty = ty - 1;
		drawColorShape.drawLine(tx, ty, tx + width, ty, 1, headerBorderColor);

		for (int i = 0; i < rowCount; i++) {
			ty = ty - rowHeight;
			if (i % 2 == 0) {
				rowColor = Color.WHITE;
				
			} else {
				rowColor = alternateRowColor;
			}
			contentStream.setNonStrokingColor(rowColor);
			if (i == rowCount - 1) {
				drawColorShape.drawSolidBottomRoundedRectangle(tx, ty, width, rowHeight, radius, rowColor);
			} else {
				drawColorShape.drawSolidRectAngle(tx, ty, width, rowHeight, rowColor);
				ty = ty - divider;
				drawColorShape.drawLine(tx, ty, tx + width, ty, 1, gridColor);
			}

		}
		float firstColumn = 50;
		float horizontalGap = (width - firstColumn) / 13;
		System.out.println(horizontalGap);
		float cx = x + firstColumn;

		float tableHeight = headerHeight + rowCount * rowHeight + rowCount * divider;
		for (int i = 0; i < 13; i++) {
			drawColorShape.drawLine(cx, ty, cx, ty + tableHeight, 1f, gridColor);
			cx += horizontalGap;
		}
		contentStream.saveGraphicsState();
		contentStream.setStrokingColor(outerBorderColor);
		contentStream.setLineWidth(1f);
		drawColorShape.drawRoundedRectangle(tx, ty, width, tableHeight, radius);
		contentStream.stroke();
		contentStream.restoreGraphicsState();
	}

	void populatePrastakTable(float x, float y, float width, float height) throws IOException {
		String[] planets = constantHindi.praPlaFullName;// { "Sun", "Mon", "Mar", "Mec", "Jup", "Ven", "Sat", "Asc",
														// "total" };
		String[] rashi = constantHindi.rashiNames;
		float headerHeight = 30;
		float tx = x;
		float ty = utility.getTextBaseline(krutiDevRegularFont, y, headerHeight, 16);
		float rowHeight = 24f;
		float boxWidth = 50;
		float horizontalGap = (width - boxWidth) / 13;

		for (int i = 0; i < rashi.length; i++) {
			drawColorShape.drawCenteredBoldText(tx, boxWidth, ty, rashi[i], 16, krutiDevRegularFont, Color.WHITE);
			tx += boxWidth;
			boxWidth = horizontalGap;

		}
		tx = x;
		boxWidth = 50;
		ty = utility.getTextBaseline(poppinsRegularFont, y - rowHeight - 1, rowHeight, 12);
		for (int i = 0; i < planets.length; i++) {
			drawColorShape.drawCenteredBoldText(tx, boxWidth, ty, planets[i], 16, krutiDevRegularFont, planetColors[i]);
			ty -= (rowHeight + 1);
		}
		tx = x + boxWidth;
		ty = utility.getTextBaseline(poppinsRegularFont, y - rowHeight - 1, rowHeight, 12);
		drawValues(tx, ty, horizontalGap, rowHeight, boxWidth, prashtakVargaList.get(count));
		count++;

	}

	void drawValues(float x, float y, float horizontalGap, float rowHeight, float boxWidth,
			PrastharashtakvargaModel prastharashtakvargaModel) throws IOException {
		float tx = x;
		float ty = y;

		String[] arr1 = prastharashtakvargaModel.getSu().split(",");
		String[] arr2 = prastharashtakvargaModel.getMo().split(",");
		String[] arr3 = prastharashtakvargaModel.getMa().split(",");
		String[] arr4 = prastharashtakvargaModel.getMe().split(",");
		String[] arr5 = prastharashtakvargaModel.getJu().split(",");
		String[] arr6 = prastharashtakvargaModel.getVe().split(",");
		String[] arr7 = prastharashtakvargaModel.getSa().split(",");
		String[] arr8 = prastharashtakvargaModel.getAsc().split(",");
		String[] arr9 = getPraTotal(arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8);

		ArrayList<String[]> list = new ArrayList<String[]>();
		list.add(arr1);
		list.add(arr2);
		list.add(arr3);
		list.add(arr4);
		list.add(arr5);
		list.add(arr6);
		list.add(arr7);
		list.add(arr8);
		list.add(arr9);
		for (int i = 0; i < list.size(); i++) {
			String[] arr = list.get(i);
			int total = 0;
			for (int j = 0; j < arr.length + 1; j++) {

				if (j == arr.length) {
					drawColorShape.drawCenteredBoldText(tx, horizontalGap, ty, String.valueOf(total), 12,
							poppinsRegularFont, Color.black);
				} else {
					total += Integer.parseInt(arr[j]);
					if (i == list.size() - 1) {
						drawColorShape.drawCenteredBoldText(tx, horizontalGap, ty, arr[j], 12, poppinsRegularFont,
								Color.black);
					} else {
						drawColorShape.drawCenteredText(arr[j], tx, tx + horizontalGap, ty, poppinsRegularFont, 12,
								Color.BLACK);
					}

				}

				tx = tx + horizontalGap;
			}
			ty -= (rowHeight + 1);
			tx = x;
		}

	}

	String[] getPraTotal(String[] arr1, String[] arr2, String[] arr3, String[] arr4, String[] arr5, String[] arr6,
			String[] arr7, String[] arr8) {
		String[] arr = new String[12];
		for (int i = 0; i < arr1.length; i++) {
			arr[i] = String.valueOf(Integer.parseInt(arr1[i]) + Integer.parseInt(arr2[i]) + Integer.parseInt(arr3[i])
					+ Integer.parseInt(arr4[i]) + Integer.parseInt(arr5[i]) + Integer.parseInt(arr6[i])
					+ Integer.parseInt(arr7[i]) + Integer.parseInt(arr8[i]))

			;
		}
		return arr;

	}
}
