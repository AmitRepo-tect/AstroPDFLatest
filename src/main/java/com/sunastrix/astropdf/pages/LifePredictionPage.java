package com.sunastrix.astropdf.pages;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astroganitlib.model.BirthDetailBean;
import com.sunastrix.astropdf.calculation.LagnaCalculation;
import com.sunastrix.astropdf.calculation.LifePredictionCalculation;
import com.sunastrix.astropdf.model.LifePredictionModel;
import com.sunastrix.astropdf.model.PageInfo;
import com.sunastrix.astropdf.model.PersonalityPrediction;
import com.sunastrix.astropdf.service_impl.PDFGenerateColorServiceImpl;
import com.sunastrix.astropdf.util.ConstantHindi;

public class LifePredictionPage extends BasePage {
	Color[][] headerGradientColors = {

			// Gold
			{ new Color(255, 224, 145), new Color(255, 197, 75), new Color(244, 160, 38) },

			// Blue
			{ new Color(190, 213, 255), new Color(135, 174, 245), new Color(75, 125, 210) },

			// Green
			{ new Color(195, 242, 207), new Color(145, 220, 170), new Color(75, 165, 110) },

			// Pink
			{ new Color(255, 205, 220), new Color(255, 160, 190), new Color(220, 105, 145) },

			// Magenta
			{ new Color(255, 205, 235), new Color(245, 160, 210), new Color(205, 100, 165) },

			// Gold
			{ new Color(255, 225, 150), new Color(255, 195, 75), new Color(244, 160, 38) },

			// Sky Blue
			{ new Color(190, 225, 255), new Color(130, 195, 250), new Color(60, 140, 215) },

			// Rose
			{ new Color(255, 205, 210), new Color(255, 155, 170), new Color(215, 95, 115) },

			// Teal
			{ new Color(190, 240, 238), new Color(125, 210, 210), new Color(55, 155, 160) },

			// Gold
			{ new Color(255, 225, 150), new Color(255, 195, 75), new Color(244, 160, 38) },

			// Blue
			{ new Color(190, 225, 255), new Color(130, 195, 250), new Color(60, 140, 215) },

			// Rose
			{ new Color(255, 205, 210), new Color(255, 155, 170), new Color(215, 95, 115) },

			// Teal
			{ new Color(190, 240, 238), new Color(125, 210, 210), new Color(55, 155, 160) } };
	int count;
	ArrayList<ParagraphDetail> arrayList = new ArrayList<ParagraphDetail>();
	String munthaDesc;
	int munthaBhav;

	public LifePredictionPage(DesktopHoroNew desktopHoro, BirthDetailBean birthDetailBean) {
		this.desktopHoro = desktopHoro;
		this.birthDetailBean = birthDetailBean;
		LifePredictionCalculation calculation = new LifePredictionCalculation(desktopHoro);
		ArrayList<LifePredictionModel> list = calculation.getLifePrediction();
		String[] heading = ConstantHindi.lifePredictionHeading;

		for (int i = 0; i < heading.length; i++) {
			arrayList.add(new ParagraphDetail(heading[i], list.get(i).getDetail()));
		}
	}

	public PageInfo drawPage(PDDocument document, PDType0Font poppinsRegularFont, PDType0Font krutiDevRegularFont) {
		String pageHeading = "lEiw.kZ thou Qykns'k";
		this.document = document;
		this.poppinsRegularFont = poppinsRegularFont;
		this.krutiDevRegularFont = krutiDevRegularFont;
		PageDetail pageDetail = addPage(pageHeading);
		try {

			contentStream = new PDPageContentStream(document, pageDetail.getPage());
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			drawColorShape.initialize(pageHeight, pageWidth, document, contentStream);
			drawPageBorder(document, pageDetail.getPage());
			drawCornerImages();
			drawHeader(pageWidth, pageHeight, krutiDevRegularFont, pageHeading);
			drawFooter(poppinsRegularFont, poppinsRegularFont, 15);
			printVrashfalPridiction(document);
			// Close final stream
			if (contentStream != null) {
				contentStream.close();
				contentStream = null;
			}

		} catch (Exception e) {

			e.printStackTrace();

			try {
				if (contentStream != null) {
					contentStream.close();
					contentStream = null;
				}
			} catch (Exception ignored) {
			}
		}
		return pageDetail.getPageInfo();
	}

	private float createNewPage(PDDocument document, PDType0Font poppinsRegularFont, PDType0Font krutiDevRegularFont)
			throws Exception {
		PDFGenerateColorServiceImpl.pageNo++;
		// Close previous page stream
		if (contentStream != null) {
			contentStream.close();
			contentStream = null;
		}

		// Create new page
		PDPage page = new PDPage(PDRectangle.A4);
		document.addPage(page);
		pageWidth = page.getMediaBox().getWidth();
		pageHeight = page.getMediaBox().getHeight();
		// Create stream for new page
		contentStream = new PDPageContentStream(document, page);
		// Initialize drawing helpers
		drawShape.initialize(pageHeight, pageWidth, document, contentStream);
		drawColorShape.initialize(pageHeight, pageWidth, document, contentStream);
		// Page decoration
		drawPageBorder(document, page);
		drawCornerImages();
		drawHeader(pageWidth, pageHeight, krutiDevRegularFont, "lEiw.kZ thou Qykns'k");
		drawFooter(poppinsRegularFont, poppinsRegularFont, 15);
		// Starting position on new page
		return pageHeight - 100f;
	}

	void printVrashfalPridiction(PDDocument document) throws Exception {

		float x = 20f;
		float fontSize = 14f;
		float availableWidth = pageWidth - x - 20f;
		float y = pageHeight - 100f;
		float leading = 18f;
		float headingHeight = 25f;
		float topPadding = 15f;
		float bottomPadding = 10f;
		float bottomMargin = 40f;
		float paragraphGap = 25f;

		for (int i = 0; i < arrayList.size(); i++) {
			ParagraphDetail item = arrayList.get(i);
			List<String> lines = wrapText(item.getContent(), availableWidth - 20f, krutiDevRegularFont, fontSize);
			float textHeight = lines.size() * leading;
			float paragraphHeight = headingHeight + topPadding + textHeight + bottomPadding;
			float remainingHeight = y - bottomMargin;
			if (paragraphHeight > remainingHeight - 40) {
				y = createNewPage(document, poppinsRegularFont, krutiDevRegularFont);
			}
			int colorIndex = i % headerGradientColors.length;
			y = printParagraph(x, y, item.getHeading(), lines, colorIndex, paragraphHeight);
			y -= paragraphGap;
		}
	}

	private float printParagraph(float startX, float startY, String heading, List<String> lines, int colorIndex,
			float paraHeight) throws Exception {
		Color textColor = new Color(55, 48, 75);
		final float headingHeight = 30f;
		final float fontSize = 14f;
		final float headingFontSize = 16f;
		final float leading = 18f;
		final float availableWidth = pageWidth - startX - 20f;
		final float cornerRadius = 8f;
		final float verticalPadding = 6f;
		final float verticalLineWidth = 2.5f;
		float headerY = startY - headingHeight;
		contentStream.saveGraphicsState();
		contentStream.setNonStrokingColor(headerGradientColors[colorIndex][2]);
		drawColorShape.drawRoundedRectangle(startX, headerY - paraHeight + headingHeight - 1, availableWidth,
				paraHeight + 1.4f, cornerRadius);
		contentStream.fill();
		contentStream.setNonStrokingColor(Color.WHITE);
		drawColorShape.drawRoundedRectangle(startX + 4, headerY - paraHeight + headingHeight, availableWidth - 5,
				paraHeight, cornerRadius);
		contentStream.fill();

		/*
		 * drawColorShape.drawGradientHeader(startX + 4, headerY, availableWidth - 5,
		 * headingHeight, cornerRadius, new Color(240, 220, 85), new Color(240, 220,
		 * 85), new Color(123, 218, 132), new Color(244, 160, 38));
		 */
		drawColorShape.drawGradientHeader(startX + 4, headerY, availableWidth - 5, headingHeight, cornerRadius,
				headerGradientColors[colorIndex][1], headerGradientColors[colorIndex][1],
				headerGradientColors[colorIndex][0], headerGradientColors[colorIndex][2]);

		float headingY = utility.getTextBaseline(krutiDevRegularFont, headerY, headingHeight, headingFontSize);
		drawShape.drawBoldText(startX + 15f, headingY, heading, (int) headingFontSize, krutiDevRegularFont,
				Color.WHITE);
		float firstLineY = headerY - 15f;
		float yPosition = firstLineY - 10f;
		for (String line : lines) {
			if (line == null || line.trim().isEmpty()) {
				continue;
			}
			drawColorShape.drawText(startX + 15f, yPosition, line, krutiDevRegularFont, fontSize, textColor);
			yPosition -= leading;
		}
		yPosition += leading;
		float bottomY = yPosition - 15f;
		float bodyTop = headerY;
		float bodyHeight = bodyTop - bottomY;
		/*
		 * if (bodyHeight > 0) { contentStream.saveGraphicsState();
		 * contentStream.setStrokingColor(headerGradientColors[colorIndex][1]);
		 * contentStream.setNonStrokingColor(Color.WHITE);
		 * contentStream.setLineWidth(0.5f);
		 * drawColorShape.drawSolidBottomRoundedRectangle(startX, bottomY,
		 * availableWidth, bodyHeight, cornerRadius,Color.WHITE);
		 * 
		 * float lineX = startX + 6f; float lineTop = bodyTop - verticalPadding; float
		 * lineBottom = bottomY + verticalPadding;
		 * contentStream.setLineWidth(verticalLineWidth);
		 * contentStream.setLineCapStyle(1); contentStream.moveTo(lineX, lineTop);
		 * contentStream.lineTo(lineX, lineBottom); contentStream.stroke();
		 * contentStream.restoreGraphicsState(); }
		 */

		return bottomY;
	}

	private static List<String> wrapText(String text, float width, PDType0Font font, float fontSize)
			throws IOException {
		List<String> lines = new ArrayList<>();
		if (text == null || text.trim().isEmpty()) {
			return lines;
		}
		String[] paragraphs = text.split("\\r?\\n");
		for (String paragraph : paragraphs) {
			String[] words = paragraph.trim().split("\\s+");
			StringBuilder currentLine = new StringBuilder();
			for (String word : words) {
				if (word.isEmpty()) {
					continue;
				}
				String testLine;
				if (currentLine.length() == 0) {
					testLine = word;
				} else {
					testLine = currentLine.toString() + " " + word;
				}

				float textWidth = font.getStringWidth(testLine) / 1000f * fontSize;
				if (textWidth <= width) {
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