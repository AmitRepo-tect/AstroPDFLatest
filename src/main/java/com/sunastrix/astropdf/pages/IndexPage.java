package com.sunastrix.astropdf.pages;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBoolean;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSFloat;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType2;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitDestination;

import com.sunastrix.astropdf.model.PageInfo;

public class IndexPage extends BasePage {
	int pageCount = 1;
	/*
	 * Color[] backgroundColors = { new Color(249, 234, 237), new Color(253, 240,
	 * 231), new Color(255, 246, 237), new Color(240, 248, 240), new Color(224, 245,
	 * 248), new Color(238, 247, 255), new Color(244, 230, 253), new Color(255, 235,
	 * 243), new Color(255, 233, 223), new Color(240, 247, 232), new Color(237, 242,
	 * 255), new Color(239, 232, 250) };
	 */

	Color[] backgroundColors = {

			// 01 - Soft Coral
			new Color(255, 232, 235),

			// 02 - Soft Peach
			new Color(255, 237, 218),

			// 03 - Soft Yellow
			new Color(255, 248, 205),

			// 04 - Soft Lime
			new Color(235, 248, 210),

			// 05 - Soft Green
			new Color(218, 246, 222),

			// 06 - Soft Mint
			new Color(210, 245, 235),

			// 07 - Soft Turquoise
			new Color(210, 242, 242),

			// 08 - Soft Cyan
			new Color(210, 240, 250),

			// 09 - Soft Sky Blue
			new Color(215, 235, 255),

			// 10 - Soft Blue
			new Color(220, 228, 255),

			// 11 - Soft Periwinkle
			new Color(228, 225, 255),

			// 12 - Soft Lavender
			new Color(238, 225, 255),

			// 13 - Soft Purple
			new Color(246, 225, 250),

			// 14 - Soft Magenta
			new Color(255, 225, 245),

			// 15 - Soft Rose
			new Color(255, 225, 235),

			// 16 - Soft Pink
			new Color(255, 232, 242),

			// 17 - Soft Apricot
			new Color(255, 235, 220),

			// 18 - Soft Sand
			new Color(250, 240, 215),

			// 19 - Soft Cream
			new Color(255, 246, 220),

			// 20 - Soft Aqua
			new Color(218, 242, 238) };
	/*
	 * Color[] textColors = {
	 * 
	 * new Color(105, 20, 28), new Color(125, 55, 25), new Color(105, 70, 20), new
	 * Color(20, 75, 40), new Color(20, 85, 105), new Color(15, 65, 140), new
	 * Color(75, 25, 110), new Color(155, 25, 75), new Color(145, 35, 25), new
	 * Color(45, 75, 25), new Color(25, 50, 145), new Color(70, 25, 110) };
	 */
	Color[] textColors = {

			// 01 - Soft Coral
			new Color(105, 15, 25),

			// 02 - Soft Peach
			new Color(105, 40, 8),

			// 03 - Soft Yellow
			new Color(85, 65, 0),

			// 04 - Soft Lime
			new Color(35, 75, 5),

			// 05 - Soft Green
			new Color(5, 70, 25),

			// 06 - Soft Mint
			new Color(0, 70, 50),

			// 07 - Soft Turquoise
			new Color(0, 65, 65),

			// 08 - Soft Cyan
			new Color(0, 55, 85),

			// 09 - Soft Sky Blue
			new Color(5, 40, 95),

			// 10 - Soft Blue
			new Color(15, 25, 100),

			// 11 - Soft Periwinkle
			new Color(30, 20, 105),

			// 12 - Soft Lavender
			new Color(55, 15, 110),

			// 13 - Soft Purple
			new Color(85, 10, 90),

			// 14 - Soft Magenta
			new Color(105, 5, 65),

			// 15 - Soft Rose
			new Color(110, 10, 35),

			// 16 - Soft Pink
			new Color(105, 15, 55),

			// 17 - Soft Apricot
			new Color(105, 35, 10),

			// 18 - Soft Sand
			new Color(80, 55, 10),

			// 19 - Soft Cream
			new Color(85, 65, 5),

			// 20 - Soft Aqua
			new Color(5, 65, 55) };
	/*
	 * Color[] accentColors = { new Color(227, 78, 80), new Color(239, 116, 38), new
	 * Color(230, 169, 42), new Color(90, 166, 92), new Color(47, 186, 193), new
	 * Color(27, 142, 248), new Color(145, 77, 186), new Color(235, 87, 137), new
	 * Color(252, 102, 69), new Color(110, 145, 55), new Color(65, 95, 215), new
	 * Color(125, 65, 180) };
	 */
	Color[] accentColors = {

			// 01 - Soft Coral
			new Color(205, 55, 65),

			// 02 - Soft Peach
			new Color(215, 90, 25),

			// 03 - Soft Yellow
			new Color(190, 135, 10),

			// 04 - Soft Lime
			new Color(65, 125, 35),

			// 05 - Soft Green
			new Color(30, 125, 60),

			// 06 - Soft Mint
			new Color(15, 135, 105),

			// 07 - Soft Turquoise
			new Color(15, 125, 135),

			// 08 - Soft Cyan
			new Color(15, 110, 160),

			// 09 - Soft Sky Blue
			new Color(30, 95, 175),

			// 10 - Soft Blue
			new Color(45, 65, 170),

			// 11 - Soft Periwinkle
			new Color(65, 55, 170),

			// 12 - Soft Lavender
			new Color(100, 45, 170) };
	/*
	 * Color[] colors = { new Color(245, 90, 100), new Color(245, 125, 30), new
	 * Color(245, 175, 20), new Color(75, 155, 75), new Color(35, 155, 190), new
	 * Color(45, 125, 220), new Color(145, 70, 190), new Color(225, 45, 110), new
	 * Color(245, 75, 80), new Color(80, 165, 75), new Color(65, 80, 210), new
	 * Color(120, 55, 170), new Color(65, 80, 210), new Color(120, 55, 170) };
	 */
	Color[] colors = {

			// 01 - Crimson
			new Color(210, 35, 55),

			// 02 - Burnt Orange
			new Color(220, 90, 25),

			// 03 - Golden
			new Color(220, 165, 20),

			// 04 - Olive
			new Color(125, 145, 25),

			// 05 - Forest Green
			new Color(35, 125, 55),

			// 06 - Emerald
			new Color(20, 165, 115),

			// 07 - Teal
			new Color(20, 145, 160),

			// 08 - Cyan
			new Color(20, 175, 205),

			// 09 - Sky Blue
			new Color(45, 140, 215),

			// 10 - Royal Blue
			new Color(45, 75, 190),

			// 11 - Navy
			new Color(35, 55, 130),

			// 12 - Indigo
			new Color(75, 55, 175),

			// 13 - Violet
			new Color(125, 55, 195),

			// 14 - Purple
			new Color(160, 55, 175),

			// 15 - Magenta
			new Color(205, 40, 145),

			// 16 - Hot Pink
			new Color(225, 45, 105),

			// 17 - Rose
			new Color(215, 55, 85),

			// 18 - Brown
			new Color(145, 75, 35),

			// 19 - Copper
			new Color(185, 95, 35),

			// 20 - Charcoal Blue
			new Color(70, 95, 125) };

	public PDPage drawIndexPage(PDDocument document, PDType0Font poppinsRegularFont, PDType0Font krutiDevRegularFont,
			ArrayList<PageInfo> list) throws IOException {
		this.document = document;
		this.poppinsRegularFont = poppinsRegularFont;
		this.krutiDevRegularFont = krutiDevRegularFont;
		PDPage page = new PDPage(PDRectangle.A4);
		// document.addPage(page);
		pageWidth = page.getMediaBox().getWidth();
		pageHeight = page.getMediaBox().getHeight();

		try (PDPageContentStream cs = new PDPageContentStream(document, page)) {
			this.contentStream = cs;
			drawShape.initialize(pageHeight, pageWidth, document, cs);
			drawColorShape.initialize(pageHeight, pageWidth, document, cs);
			drawPageBorder(document, page);
			drawCornerImages();
			Color darkRed = new Color(125, 0, 15);
			Color darkBlue = new Color(35, 48, 82);
			Color gold = new Color(205, 155, 55);
			// drawIndexDecoration(cs, pageWidth / 2f, pageHeight - 48f, gold);
			drawCenteredText("vuqØef.kdk", pageWidth / 2f, pageHeight - 70f, 45, krutiDevRegularFont, darkRed);
			drawCenteredText("vkidh T;ksfr\"k fjiksVZ dks le>us dh laiw.kZ ekxZnf'kZdk", pageWidth / 2f,
					pageHeight - 100f, 12, krutiDevRegularFont, darkBlue);
			drawIndexDecoration(cs, pageWidth / 2f, pageHeight - 120f, gold);

			float itemWidth = pageWidth - 100;
			float itemHeight = 35f;
			float leftX = 50f;
			float rightX = 50f;
			float startY = pageHeight - 210f;
			float gap = 10f;
			for (int i = 0; i < list.size(); i++) {
				float itemY = startY - i * (itemHeight + gap);
				PageInfo pageInfo = list.get(i);
				drawIndexItem(i, leftX, itemY, itemWidth, itemHeight, pageInfo, colors[i], poppinsRegularFont);
				makeIndexItemClickable(page, pageInfo.getPage(), leftX, itemY, itemWidth, itemHeight);
			}

			float bottomY = 28f;
			drawIndexDecoration(cs, pageWidth / 2f, bottomY + 48f, gold);
			drawCenteredText("The right direction brings a brighter tomorrow", pageWidth / 2f, bottomY + 15f, 13,
					poppinsRegularFont, darkBlue);
			drawIndexDecoration(cs, pageWidth / 2f, bottomY, gold);
			return page;
		}
	}

	public PDPage drawNextIndexPage(PDDocument document, PDType0Font poppinsRegularFont,
			PDType0Font krutiDevRegularFont, ArrayList<PageInfo> list) throws IOException {
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
			Color darkBlue = new Color(35, 48, 82);
			Color gold = new Color(205, 155, 55);

			Color[] colors = { new Color(245, 90, 100), new Color(245, 125, 30), new Color(245, 175, 20),
					new Color(75, 155, 75), new Color(35, 155, 190), new Color(45, 125, 220), new Color(145, 70, 190),
					new Color(225, 45, 110), new Color(245, 75, 80), new Color(80, 165, 75), new Color(65, 80, 210),
					new Color(120, 55, 170), new Color(65, 80, 210), new Color(120, 55, 170) };
			float itemWidth = pageWidth - 100;
			float itemHeight = 35f;
			float leftX = 50f;
			float rightX = 50f;
			float startY = pageHeight - 210f;
			float gap = 10f;
			for (int i = 0; i < list.size(); i++) {
				float itemY = startY - i * (itemHeight + gap);
				PageInfo pageInfo = list.get(i);
				drawIndexItem(i, leftX, itemY, itemWidth, itemHeight, pageInfo, colors[i], poppinsRegularFont);
				makeIndexItemClickable(page, pageInfo.getPage(), leftX, itemY, itemWidth, itemHeight);
			}

			float bottomY = 78f;
			drawIndexDecoration(cs, pageWidth / 2f, bottomY + 48f, gold);
			drawCenteredText("The right direction brings a brighter tomorrow", pageWidth / 2f, bottomY + 25f, 13,
					poppinsRegularFont, darkBlue);
			drawIndexDecoration(cs, pageWidth / 2f, bottomY, gold);
			return page;
		}
	}

	private void drawIndexItem(int index, float x, float y, float width, float height, PageInfo pageInfo, Color accent,
			PDType0Font font) throws IOException {

		contentStream.saveGraphicsState();
		contentStream.setNonStrokingColor(accentColors[index]);
		drawRightRoundedRectangle(x + width + 10, y, 7f, height, 5f);
		contentStream.fill();
		contentStream.restoreGraphicsState();

		Color bg = backgroundColors[index];
		contentStream.saveGraphicsState();
		contentStream.setNonStrokingColor(bg);
		drawLeftRoundedRectangle(x, y, width + 10f, height, 5f);
		contentStream.fill();
		contentStream.restoreGraphicsState();
		float diamondCenterX = x;
		float diamondCenterY = y + height / 2f;
		drawDiamond(contentStream, diamondCenterX, diamondCenterY, 40, 3, accent);
		drawCenteredText(String.format("%02d", pageCount++), diamondCenterX, diamondCenterY - 5, 12, font, Color.WHITE);
		float textX = x + 30;
		float textY = utility.getTextBaseline(font, y, height, 12);

		drawText(contentStream, pageInfo.getPageTitle(), textX, textY, 15, krutiDevRegularFont, textColors[index]);
		drawColorShape.drawDotLine(textX + utility.getTextWidth(pageInfo.getPageTitle(), krutiDevRegularFont, 15) + 5,
				y + height / 2 - 1f, x + width - 45);
		drawText(contentStream, "i`\"B Ø- " + String.format("%02d", pageInfo.getStartPageNo()), x + width - 40, textY,
				12, krutiDevRegularFont, textColors[index]);

	}

	private void drawDiamond(PDPageContentStream cs, float centerX, float centerY, float size, float cornerRadius,
			Color color) throws IOException {

		float h = size / 2f;

		// Diamond vertices
		float topX = centerX;
		float topY = centerY + h;
		float rightX = centerX + h;
		float rightY = centerY;
		float bottomX = centerX;
		float bottomY = centerY - h;
		float leftX = centerX - h;
		float leftY = centerY;
		float d = cornerRadius;
		float o = d / (float) Math.sqrt(2);
		cs.saveGraphicsState();
		cs.moveTo(topX - o, topY - o);
		cs.curveTo(topX - o * 0.45f, topY - o * 0.45f, topX + o * 0.45f, topY - o * 0.45f, topX + o, topY - o);
		cs.lineTo(rightX - o, rightY + o);
		cs.curveTo(rightX - o * 0.45f, rightY + o * 0.45f, rightX - o * 0.45f, rightY - o * 0.45f, rightX - o,
				rightY - o);
		cs.lineTo(bottomX + o, bottomY + o);
		cs.curveTo(bottomX + o * 0.45f, bottomY + o * 0.45f, bottomX - o * 0.45f, bottomY + o * 0.45f, bottomX - o,
				bottomY + o);
		cs.lineTo(leftX + o, leftY - o);
		cs.curveTo(leftX + o * 0.45f, leftY - o * 0.45f, leftX + o * 0.45f, leftY + o * 0.45f, leftX + o, leftY + o);
		cs.lineTo(topX - o, topY - o);
		cs.closePath();
		cs.fill();
		cs.restoreGraphicsState();
	}

	private void drawText(PDPageContentStream cs, String text, float x, float y, float fontSize, PDType0Font font,
			Color color) throws IOException {
		cs.beginText();
		cs.setFont(font, fontSize);
		cs.setNonStrokingColor(color);
		cs.newLineAtOffset(x, y);
		cs.showText(text);
		cs.endText();
	}

	private void drawCenteredText(String text, float centerX, float y, float fontSize, PDType0Font font, Color color)
			throws IOException {
		float textWidth = font.getStringWidth(text) / 1000f * fontSize;
		float x = centerX - textWidth / 2f;
		drawColorShape.drawBoldText(x, y, text, font, fontSize, color);
	}

	private void drawIndexDecoration(PDPageContentStream cs, float centerX, float centerY, Color gold)
			throws IOException {

		/*
		 * cs.saveGraphicsState(); cs.setStrokingColor(gold);
		 * cs.setNonStrokingColor(gold); cs.setLineWidth(0.8f); float lineWidth = 55f;
		 * float gap = 13f; cs.moveTo(centerX - gap - lineWidth, centerY);
		 * cs.lineTo(centerX - gap, centerY); cs.moveTo(centerX + gap, centerY);
		 * cs.lineTo(centerX + gap + lineWidth, centerY); cs.stroke();
		 */
		drawGradientLine(
		        contentStream,
		        centerX,
		        centerY,
		        55f,
		        2f,
		        new Color(184, 134, 11),  // dark gold
		        new Color(242, 210, 122)  // light gold
		);
		// Center diamond
		float s = 4f;
		drawColorShape.drawFourPointStar(centerX, centerY, 8f, gold);
		cs.restoreGraphicsState();
	}

	private void drawRightRoundedRectangle(float x, float y, float width, float height, float radius)
			throws IOException {

		float k = 0.5522848f;
		float right = x + width;
		float top = y + height;
		contentStream.moveTo(x, y);
		contentStream.lineTo(right - radius, y);
		contentStream.curveTo(right - radius + radius * k, y, right, y + radius - radius * k, right, y + radius);
		contentStream.lineTo(right, top - radius);
		contentStream.curveTo(right, top - radius + radius * k, right - radius + radius * k, top, right - radius, top);
		contentStream.lineTo(x, top);
		contentStream.lineTo(x, y);
		contentStream.closePath();
	}

	private void drawLeftRoundedRectangle(float x, float y, float width, float height, float radius)
			throws IOException {

		float k = 0.5522848f;
		float right = x + width;
		float top = y + height;
		contentStream.moveTo(x + radius, y);
		contentStream.lineTo(right, y);
		contentStream.lineTo(right, top);
		contentStream.lineTo(x + radius, top);
		contentStream.curveTo(x + radius - radius * k, top, x, top - radius + radius * k, x, top - radius);
		contentStream.lineTo(x, y + radius);
		contentStream.curveTo(x, y + radius - radius * k, x + radius - radius * k, y, x + radius, y);
		contentStream.closePath();
	}

	private void addPageLink(PDPage indexPage, PDPage targetPage, float x, float y, float width, float height)
			throws IOException {

		PDAnnotationLink link = new PDAnnotationLink();
		PDRectangle rectangle = new PDRectangle(x, y, width, height);
		link.setRectangle(rectangle);
		PDPageFitDestination destination = new PDPageFitDestination();
		destination.setPage(targetPage);
		PDActionGoTo action = new PDActionGoTo();
		action.setDestination(destination);
		link.setAction(action);
		PDBorderStyleDictionary border = new PDBorderStyleDictionary();
		border.setWidth(0);
		link.setBorderStyle(border);
		indexPage.getAnnotations().add(link);
	}

	private void makeIndexItemClickable(PDPage indexPage, PDPage targetPage, float x, float y, float width,
			float height) throws IOException {

		PDAnnotationLink link = new PDAnnotationLink();
		link.setRectangle(new PDRectangle(x, y, width, height));
		PDPageFitDestination destination = new PDPageFitDestination();
		destination.setPage(targetPage);
		PDActionGoTo action = new PDActionGoTo();
		action.setDestination(destination);
		link.setAction(action);
		PDBorderStyleDictionary border = new PDBorderStyleDictionary();
		border.setWidth(0);
		link.setBorderStyle(border);
		indexPage.getAnnotations().add(link);
	}

	private void drawGradientLine(PDPageContentStream contentStream, float x, float y, float width, float height,
			Color startColor, Color endColor) throws IOException {

		contentStream.saveGraphicsState();

		contentStream.addRect(x, y, width, height);
		contentStream.clip();

		COSDictionary shadingDictionary = new COSDictionary();

		shadingDictionary.setInt(COSName.SHADING_TYPE, 2);
		shadingDictionary.setItem(COSName.COLORSPACE, PDDeviceRGB.INSTANCE.getCOSObject());

		// Horizontal gradient
		COSArray coords = new COSArray();
		coords.add(new COSFloat(x));
		coords.add(new COSFloat(y));
		coords.add(new COSFloat(x + width));
		coords.add(new COSFloat(y));

		shadingDictionary.setItem(COSName.COORDS, coords);

		// Type 2 function
		COSDictionary function = new COSDictionary();

		function.setInt(COSName.FUNCTION_TYPE, 2);

		COSArray domain = new COSArray();
		domain.add(new COSFloat(0f));
		domain.add(new COSFloat(1f));
		function.setItem(COSName.DOMAIN, domain);

		COSArray c0 = new COSArray();
		c0.add(new COSFloat(startColor.getRed() / 255f));
		c0.add(new COSFloat(startColor.getGreen() / 255f));
		c0.add(new COSFloat(startColor.getBlue() / 255f));

		function.setItem(COSName.C0, c0);

		COSArray c1 = new COSArray();
		c1.add(new COSFloat(endColor.getRed() / 255f));
		c1.add(new COSFloat(endColor.getGreen() / 255f));
		c1.add(new COSFloat(endColor.getBlue() / 255f));

		function.setItem(COSName.C1, c1);

		function.setFloat(COSName.N, 1f);

		shadingDictionary.setItem(COSName.FUNCTION, function);

		COSArray extend = new COSArray();
		extend.add(COSBoolean.TRUE);
		extend.add(COSBoolean.TRUE);

		shadingDictionary.setItem(COSName.EXTEND, extend);

		PDShadingType2 shading = new PDShadingType2(shadingDictionary);

		contentStream.shadingFill(shading);

		contentStream.restoreGraphicsState();
	}
}
