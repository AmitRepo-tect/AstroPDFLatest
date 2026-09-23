package com.sunastrix.astropdf.pages;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.util.Matrix;
import org.w3c.dom.svg.SVGDocument;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astropdf.calculation.GhatakAndFavorableCalculation;
import com.sunastrix.astropdf.calculation.KpRulingPlanetCalculation;
import com.sunastrix.astropdf.model.FavourablePointBean;
import com.sunastrix.astropdf.model.GhatChakarBean;
import com.sunastrix.astropdf.model.KpRulingPlanetBean;
import com.sunastrix.astropdf.model.PageInfo;
import com.sunastrix.astropdf.service_impl.PDFGenerateColorServiceImpl;
import com.sunastrix.astropdf.util.DrawShape;

import de.rototor.pdfbox.graphics2d.PdfBoxGraphics2D;

public class GhatakAndFavourablePage extends BasePage {
	Color[] colors = { new Color(0, 145, 75), new Color(20, 145, 205), new Color(105, 45, 190), new Color(225, 125, 10),
			new Color(0, 145, 75), new Color(20, 145, 205), new Color(105, 45, 190), new Color(225, 125, 10),
			new Color(0, 145, 75), new Color(20, 145, 205), new Color(105, 45, 190), new Color(225, 125, 10) };

	public GhatakAndFavourablePage(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	public PageInfo drawPage(PDDocument document, PDType0Font poppinsRegularFont, PDType0Font krutiDevRegularFont) {
		PageInfo pageInfo = new PageInfo();
		this.document = document;
		this.poppinsRegularFont = poppinsRegularFont;
		this.krutiDevRegularFont = krutiDevRegularFont;
		PDFGenerateColorServiceImpl.pageNo++;
		PDPage page = new PDPage(PDRectangle.A4);
		pageInfo.setPage(page);
		pageInfo.setPageTitle("vuqdwy vkSj ?kkrd vad");
		pageInfo.setStartPageNo(PDFGenerateColorServiceImpl.pageNo);
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
			drawHeader(pageWidth, pageHeight, krutiDevRegularFont, "vuqdwy vkSj ?kkrd vad");

			float favX = 40f;
			float favY = 475f;
			float favWidth = 514f;
			float favHeight = 230f;

			float bgX = favX - 10f;
			float bgY = favY - 10f;
			float bgWidth = favWidth + 19f;
			float bgHeight = favHeight + 37f;

			float headWidth = 170f;
			float headHeight = 26f;
			float headMargin = 13.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "?kVd¼v'kqHk½", 14);
			// ITEMS
			GhatChakarBean ghatChakarBean = new GhatakAndFavorableCalculation(desktopHoro).getGhatChakarData();
			String[] labels = constantHindi.ghatakLabels;

			String[] values = { ghatChakarBean.getDay(), ghatChakarBean.getKaran(), ghatChakarBean.getLagna(),
					ghatChakarBean.getMonth(), ghatChakarBean.getNakshtra(), ghatChakarBean.getPahar(),
					ghatChakarBean.getRashi(), ghatChakarBean.getTithi(), ghatChakarBean.getYoga(),
					ghatChakarBean.getPlanet() };
			drawFavourableItems(krutiDevRegularFont, favX, favY, favWidth, favHeight, labels, values);

			favX = 40f;
			favY = 175f;
			favWidth = 514f;
			favHeight = 230f;

			bgX = favX - 10f;
			bgY = favY - 10f;
			bgWidth = favWidth + 19f;
			bgHeight = favHeight + 37f;

			headWidth = 170f;
			headHeight = 26f;
			headMargin = 13.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "vuqdwy fcanq", 14);
			// ITEMS
			FavourablePointBean favourablePointBean = new GhatakAndFavorableCalculation(desktopHoro)
					.getFavourableData();
			System.out.println("favourablePointBean" + favourablePointBean.getFriendlySigns());
			labels = constantHindi.favourableLabels;

			String[] fvalues = { favourablePointBean.getLuckyNumbers(), favourablePointBean.getGoodNumbers(),
					favourablePointBean.getGoodYears(), favourablePointBean.getLuckyDays(),
					favourablePointBean.getGoodPlanets(), favourablePointBean.getFriendlySigns(),
					favourablePointBean.getGoodLagna(), favourablePointBean.getLuckyMetal(),
					favourablePointBean.getLuckyStone() };

			drawFavourableItems(krutiDevRegularFont, favX, favY, favWidth, favHeight, labels, fvalues);
			drawFooter(poppinsRegularFont, poppinsRegularFont, 0);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pageInfo;
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

	private void drawFavourableCard1(PDType0Font font, float x, float y, float width, float height, String label,
			String value, Color accentColor) throws IOException {

		// 1. BACKGROUND

		drawFavourableItemBackground(x, y, width, height, accentColor);

		// 2. VALUE

		String[] valueLines = value.split("\n");

		float valueFontSize = 18f;
		float lineHeight = 12f;

		float valueY = y + height - 23f;

		for (String line : valueLines) {

			if (line.contains("1")) {
				font = poppinsRegularFont;
				valueFontSize = 12;
			} else {
				font = krutiDevRegularFont;
				valueFontSize = 16;
			}
			float textWidth = font.getStringWidth(line) / 1000f * valueFontSize;

			float textX = x + (width - textWidth) / 2f;

			drawColorShape.drawCenteredBoldText(textX, width, valueY, line, (int) valueFontSize, font, accentColor);
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

	private void drawFavourableCard(PDType0Font font, float x, float y, float width, float height, String label,
			String value, Color accentColor) throws IOException {

		// 1. BACKGROUND
		drawFavourableItemBackground(x, y, width, height, accentColor);

		// ---------------------------------------------------------
		// 2. VALUE
		// ---------------------------------------------------------

		float valueFontSize = 16f;
		float lineHeight = 19f;

		float textPadding = 8f;
		float maxTextWidth = width - (textPadding * 2);

		// Wrap text according to available width
		List<String> valueLines = wrapText(value, krutiDevRegularFont, valueFontSize, maxTextWidth);

		float valueY = y + height - 23f;

		for (String line : valueLines) {

			PDType0Font currentFont;
			float currentFontSize;

			if (line.matches("[0-9.\\- /]+")) {
				currentFont = poppinsRegularFont;
				currentFontSize = 12f;
			} else {
				currentFont = krutiDevRegularFont;
				currentFontSize = 16f;
			}

			float textWidth = currentFont.getStringWidth(line) / 1000f * currentFontSize;

			// Center text inside card
			float textX = x + (width - textWidth) / 2f;

			drawColorShape.drawCenteredBoldText(x, width, valueY, line, (int) currentFontSize, currentFont,
					accentColor);

			valueY -= lineHeight;
		}

		// ---------------------------------------------------------
		// 3. LABEL
		// ---------------------------------------------------------

		font = krutiDevRegularFont;

		float labelFontSize = 14f;

		float labelWidth = font.getStringWidth(label) / 1000f * labelFontSize;

		float labelX = x + (width - labelWidth) / 2f;
		float labelY = y + 12f;

		drawColorShape.drawText(labelX, labelY, label, font, labelFontSize, new Color(180, 173, 11));
	}

	private void drawFavourableItems(PDType0Font font, float sectionX, float sectionY, float sectionWidth,
			float sectionHeight, String[] lables, String[] values) throws IOException {
		// CARD LAYOUT
		int columns = 4;
		int rows = (int) Math.ceil((double) lables.length / columns);
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

		for (int i = 0; i < lables.length; i++) {
			int row = i / columns;
			int column = i % columns;
			float x = startX + column * (cardWidth + gapX);
			float y = startY - row * (cardHeight + gapY);
			drawFavourableCard(font, x, y, cardWidth, cardHeight, lables[i], values[i], colors[i]);
		}
	}

	private List<String> wrapText(String text, PDType0Font font, float fontSize, float maxWidth) throws IOException {

		List<String> lines = new ArrayList<>();

		if (text == null || text.trim().isEmpty()) {
			return lines;
		}

		String[] paragraphs = text.split("\\r?\\n");

		for (String paragraph : paragraphs) {

			String[] words = paragraph.trim().split("\\s+");

			StringBuilder currentLine = new StringBuilder();

			for (String word : words) {

				String testLine = currentLine.length() == 0 ? word : currentLine + " " + word;

				float textWidth = font.getStringWidth(testLine) / 1000f * fontSize;

				if (textWidth <= maxWidth) {

					currentLine.setLength(0);
					currentLine.append(testLine);

				} else {

					if (currentLine.length() > 0) {
						lines.add(currentLine.toString());
					}

					currentLine.setLength(0);
					currentLine.append(word);
				}
			}

			if (currentLine.length() > 0) {
				lines.add(currentLine.toString());
			}
		}

		return lines;
	}
}
