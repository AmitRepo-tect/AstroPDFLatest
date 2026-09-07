package com.sunastrix.astropdf.pages;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class ForthPage extends BasePage {

	public void drawPage(PDDocument document, PDType0Font poppinsRegularFont) {
		this.document = document;
		this.poppinsRegularFont = poppinsRegularFont;
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
			drawHeader(pageWidth, pageHeight, poppinsRegularFont);
			// Draw Basic Details
			float tableX = 60f;
			float tableY = 370f;
			float tableWidth = 470f;
			float tableHeight = 315f;

			float bgX = tableX - 10f;
			float bgY = tableY - 8f;
			float bgWidth = tableWidth + 19f;
			float bgHeight = tableHeight + 37f;

			float headWidth = 170f;
			float headHeight = 38f;
			float headMargin = 19.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "Lagna Chart", 14);
			svgBytes = getClass().getResourceAsStream("/images/lagna_chart_scaled.svg").readAllBytes();
			drawColorShape.drawExactSvg(svgBytes, tableX, tableY, tableWidth, tableHeight);

			tableX = 35f;
			tableY = 80f;
			tableWidth = 240f;
			tableHeight = 220f;

			bgX = tableX - 10f;
			bgY = tableY - 8f;
			bgWidth = tableWidth + 19f;
			bgHeight = tableHeight + 37f;

			headWidth = 170f;
			headHeight = 30f;
			headMargin = 15.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "Navamsa Chart", 12);
			svgBytes = getClass().getResourceAsStream("/images/lagna_chart_scaled.svg").readAllBytes();
			drawColorShape.drawExactSvg(svgBytes, tableX, tableY, tableWidth, tableHeight);
			tableX = 310f;
			tableY = 80f;
			tableWidth = 240f;
			tableHeight = 220f;

			bgX = tableX - 10f;
			bgY = tableY - 8f;
			bgWidth = tableWidth + 19f;
			bgHeight = tableHeight + 37f;

			headWidth = 170f;
			headHeight = 30f;
			headMargin = 15.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "Chandra Chart", 12);
			svgBytes = getClass().getResourceAsStream("/images/lagna_chart_scaled.svg").readAllBytes();
			drawColorShape.drawExactSvg(svgBytes, tableX, tableY, tableWidth, tableHeight);

			drawFooter(poppinsRegularFont, poppinsRegularFont, 0);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
