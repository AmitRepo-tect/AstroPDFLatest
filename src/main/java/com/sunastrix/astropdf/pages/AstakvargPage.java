package com.sunastrix.astropdf.pages;

import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astropdf.calculation.AshtakVargaCalculation;
import com.sunastrix.astropdf.model.PageInfo;

public class AstakvargPage extends BasePage {
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
	Color[] rashiColors = {
			// 1. Aries (Mesh) - Deep Fire Red (Ruled by Mars)
			new Color(115, 25, 25),
			// 2. Taurus (Vrishabha) - Dark Earth Green / Rich Olive (Ruled by Venus)
			new Color(30, 65, 35),
			// 3. Gemini (Mithuna) - Dark Vivid Green (Ruled by Mercury)
			new Color(15, 75, 40),
			// 4. Cancer (Karka) - Deep Sea Blue / Indigo (Ruled by Moon)
			new Color(20, 50, 95),
			// 5. Leo (Simha) - Dark Royal Gold / Crimson Orange (Ruled by Sun)
			new Color(115, 55, 10),
			// 6. Virgo (Kanya) - Dark Slate / Earthy Brown (Ruled by Mercury)
			new Color(75, 55, 35),
			// 7. Libra (Tula) - Deep Violet / Midnight Purple (Ruled by Venus)
			new Color(85, 25, 95),
			// 8. Scorpio (Vrishchika) - Dark Blood Red / Maroon (Ruled by Mars)
			new Color(90, 15, 30),
			// 9. Sagittarius (Dhanu) - Dark Imperial Yellow / Ochre (Ruled by Jupiter)
			new Color(110, 75, 15),
			// 10. Capricorn (Makara) - Charcoal / Deep Slate Grey (Ruled by Saturn)
			new Color(45, 50, 55),
			// 11. Aquarius (Kumbha) - Dark Midnight Navy Blue (Ruled by Saturn)
			new Color(15, 30, 75),
			// 12. Pisces (Meena) - Dark Plum / Deep Spiritual Yellow-Brown (Ruled by
			// Jupiter)
			new Color(80, 40, 75) };

	public AstakvargPage(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	public PageInfo drawPage(PDDocument document, PDType0Font poppinsRegularFont,PDType0Font krutiDevRegularFont) {
		String pageHeading = "v\"VdoxZ rkfydk";
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
			float tableX = 39f;
			float tableY = 660f;
			float tableWidth = pageWidth - 80;
			float tableHeight = 30+12*22+12;
			float radius = 8f;

			float bgX = tableX - 10f;
			float bgY = tableY-tableHeight+30 - 8f;
			float bgWidth = tableWidth + 19f;
			float bgHeight = tableHeight + 37f;

			float headWidth = 170f;
			float headHeight = 30f;
			float headMargin = 15.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "v\"VdoxZ rkfydk", 14);
			drawTable(tableX, tableY, tableWidth, tableHeight);
			populateAstakvargaTable(tableX, tableY, tableWidth, tableHeight);
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
		float rowHeight = 22f;
		float radius = 8;
		int rowCount = 12;
		float lineWidth = .5f;
		float divider = 1f;
		Color headerColor = new Color(235, 91, 0);

		Color gradientEnd = new Color(255, 174, 35);
		Color gradientStart = new Color(235, 78, 0);
		Color headerBorderColor = new Color(205, 50, 0);
		Color outerBorderColor = new Color(215, 125, 40);
		Color gridColor = new Color(225, 215, 200);
		Color alternateRowColor = new Color(255, 250, 240);
		Color rowColor;
		drawColorShape.drawTopRoundedGradientRect(tx, ty, width, headerHeight, radius - 2, gradientStart, gradientEnd,
				false);

		ty = ty - 1;
		drawColorShape.drawLine(tx, ty, tx + width, ty, 1, headerBorderColor);

		for (int i = 0; i < rowCount; i++) {
			ty = ty - rowHeight;
			if (i % 2 == 0) {
				rowColor = alternateRowColor;
			} else {
				rowColor = Color.WHITE;
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
		float firstColumn = 80;
		float horizontalGap = (width - firstColumn) / 8;
		System.out.println(horizontalGap);
		float cx = x + firstColumn;

		float tableHeight = headerHeight + rowCount * rowHeight + rowCount * divider;
		for (int i = 0; i < 8; i++) {
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

	void populateAstakvargaTable(float x, float y, float width, float height) throws IOException {
		String[] ashtakvarg = new AshtakVargaCalculation(desktopHoro).getAshtakVargaData();
		String[] planets = { "Rashi", "Sun", "Mon", "Mar", "Mec", "Jup", "Ven", "Sat", "total" };
		String[] rashi = { "Ari", "Tau", "Gem", "Can", "Leo", "Vir", "Lib", "Sco", "Sag", "Cap", "Aqu", "Pis" };
		float headerHeight = 30;
		float tx = x;
		float ty = utility.getTextBaseline(poppinsRegularFont, y, headerHeight, 12);
		float rowHeight = 22f;
		float boxWidth = 80;
		float horizontalGap = (width - boxWidth) / 8;

		for (int i = 0; i < planets.length; i++) {
			drawColorShape.drawCenteredBoldText(tx, boxWidth, ty, planets[i], 12, poppinsRegularFont, Color.black);
			tx += boxWidth;
			boxWidth = horizontalGap;

		}
		tx = x;
		boxWidth = 80;
		ty = utility.getTextBaseline(poppinsRegularFont, y - rowHeight - 1, rowHeight, 12);
		for (int i = 0; i < rashi.length; i++) {
			drawColorShape.drawCenteredBoldText(tx, boxWidth, ty, rashi[i], 12, poppinsRegularFont, rashiColors[i]);
			ty -= (rowHeight + 1);
		}
		tx = x + boxWidth;
		ty = utility.getTextBaseline(poppinsRegularFont, y - rowHeight - 1, rowHeight, 12);
		for (int i = 0; i < ashtakvarg.length; i++) {
			String[] arr = ashtakvarg[i].split(",");
			int total = 0;
			for (int j = 0; j < arr.length + 1; j++) {

				if (j == arr.length) {
					drawColorShape.drawCenteredBoldText(tx, horizontalGap, ty, String.valueOf(total), 12,
							poppinsRegularFont, Color.black);
				} else {
					total += Integer.parseInt(arr[j]);
					drawColorShape.drawCenteredText(arr[j], tx, tx + horizontalGap, ty, poppinsRegularFont, 12,
							Color.BLACK);
				}
				tx = tx + horizontalGap;
			}
			ty -= (rowHeight + 1);
			tx = x + boxWidth;
		}
	}

}
