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
import com.sunastrix.astropdf.calculation.KpRulingPlanetCalculation;
import com.sunastrix.astropdf.model.KpRulingPlanetBean;
import com.sunastrix.astropdf.model.PageInfo;

public class KpRulingPage extends BasePage {
	Color[] colors = { new Color(0, 145, 75), new Color(20, 145, 205), new Color(105, 45, 190), new Color(225, 125, 10),
			new Color(0, 145, 75), new Color(20, 145, 205), new Color(105, 45, 190), new Color(225, 125, 10),
			new Color(0, 145, 75), new Color(20, 145, 205), new Color(105, 45, 190), new Color(225, 125, 10) };

	public KpRulingPage(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	public PageInfo drawPage(PDDocument document, PDType0Font poppinsRegularFont, PDType0Font krutiDevRegularFont) {
		String pageHeading = "'kkld xzg vkSj vU;";
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

			float favX = 40f;
			float favY = 550f;
			float favWidth = 514f;
			float favHeight = 150f;

			float bgX = favX - 10f;
			float bgY = favY - 10f;
			float bgWidth = favWidth + 19f;
			float bgHeight = favHeight + 37f;

			float headWidth = 170f;
			float headHeight = 26f;
			float headMargin = 13.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "'kkld xzg", 14);
			// ITEMS
			ArrayList<KpRulingPlanetBean> rulingList = new KpRulingPlanetCalculation(desktopHoro)
					.getKPRulingPlanetData();
			drawFavourableItems(krutiDevRegularFont, favX, favY, favWidth, favHeight, rulingList);

			favX = 40f;
			favY = 415f;
			favWidth = 514f;
			favHeight = 70f;

			bgX = favX - 10f;
			bgY = favY - 10f;
			bgWidth = favWidth + 19f;
			bgHeight = favHeight + 37f;

			headWidth = 170f;
			headHeight = 26f;
			headMargin = 13.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "vU;", 14);
			// ITEMS
			ArrayList<KpRulingPlanetBean> miscList = new KpRulingPlanetCalculation(desktopHoro).getKPMiscData();
			drawFavourableItems(krutiDevRegularFont, favX, favY, favWidth, favHeight, miscList);
			drawFooter(poppinsRegularFont, poppinsRegularFont, 0);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pageDetail.getPageInfo();
	}

	private void drawFavourableItemBackground(float x, float y, float width, float height, Color accentColor)
			throws IOException {
		float radius = 8f;

		contentStream.setNonStrokingColor(accentColor);
		drawColorShape.drawRoundedRectangle(x, y, width, height, radius);
		contentStream.fill();

		// 1. WHITE CARD

		contentStream.setNonStrokingColor(Color.WHITE);
		drawColorShape.drawRoundedRectangle(x, y + 5, width, height - 3, radius);
		contentStream.fill();

		// 2. LIGHT GRAY BORDER

		contentStream.setStrokingColor(new Color(220, 220, 220));
		contentStream.setLineWidth(0.7f);
		drawColorShape.drawRoundedRectangle(x, y + 5, width, height - 3, radius);
		contentStream.stroke();

	}

	private void drawFavourableCard(PDType0Font font, float x, float y, float width, float height, String value,
			String label, Color accentColor) throws IOException {

		// 1. BACKGROUND

		drawFavourableItemBackground(x, y, width, height, accentColor);

		// 2. VALUE

		String[] valueLines = value.split("\n");

		float valueFontSize = 18f;
		float lineHeight = 12f;

		float valueY = y + height - 23f;

		for (String line : valueLines) {

			if (line.contains("°")) {
				font = poppinsRegularFont;
				valueFontSize = 12;
			} else {
				font = krutiDevRegularFont;
				valueFontSize = 16;
			}
			float textWidth = font.getStringWidth(line) / 1000f * valueFontSize;

			float textX = x + (width - textWidth) / 2f;
			drawColorShape.drawText(textX, valueY, line, font, valueFontSize, accentColor);
			valueY -= lineHeight;
		}

		// 3. LABEL
		font = krutiDevRegularFont;
		float labelFontSize = 12f;
		float labelWidth = font.getStringWidth(label) / 1000f * labelFontSize;
		float labelX = x + (width - labelWidth) / 2f;
		float labelY = y + 12f;
		drawColorShape.drawText(labelX, labelY, label, font, labelFontSize, Color.BLACK);
	}

	private void drawFavourableItems(PDType0Font font, float sectionX, float sectionY, float sectionWidth,
			float sectionHeight, ArrayList<KpRulingPlanetBean> list) throws IOException {
		// CARD LAYOUT
		int columns = 4;
		int rows = (int) Math.ceil((double) list.size() / columns);
		float gapX = 10f;
		float gapY = 10f;
		float cardWidth = (sectionWidth - ((columns - 1) * gapX)) / columns;

		// Use complete available section height
		float cardHeight = 70f;

		// =====================================================
		// START FROM TOP OF SECTION
		// =====================================================

		float startX = sectionX;
		float startY = sectionY + sectionHeight - cardHeight;
		// =====================================================
		// DRAW CARDS
		// =====================================================

		for (int i = 0; i < list.size(); i++) {
			int row = i / columns;
			int column = i % columns;
			float x = startX + column * (cardWidth + gapX);
			float y = startY - row * (cardHeight + gapY);
			drawFavourableCard(font, x, y, cardWidth, cardHeight, list.get(i).getValue(), list.get(i).getLabel(),
					colors[i]);
		}
	}
}
