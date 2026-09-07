package com.sunastrix.astropdf.pages;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.IOException;

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

import com.sunastrix.astropdf.util.DrawShape;

import de.rototor.pdfbox.graphics2d.PdfBoxGraphics2D;

public class ThirdPage extends BasePage {

	public void drawPage(PDDocument document, PDType0Font poppinsRegularFont) {
		this.document = document;
		this.poppinsRegularFont = poppinsRegularFont;
		PDPage page = new PDPage(PDRectangle.A4);
		document.addPage(page);
		float cornerSize = 30f;
		pageWidth = page.getMediaBox().getWidth();
		pageHeight = page.getMediaBox().getHeight();

		try (PDPageContentStream cs = new PDPageContentStream(document, page)) {
			Color green = new Color(0, 145, 75);
			Color blue = new Color(20, 145, 205);
			Color purple = new Color(105, 45, 190);
			Color orange = new Color(225, 125, 10);

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

			float favX = 40f;
			float favY = 440f;
			float favWidth = 514f;
			float favHeight = 240f;

			float bgX = favX - 10f;
			float bgY = favY - 8f;
			float bgWidth = favWidth + 19f;
			float bgHeight = favHeight + 37f;

			float headWidth = 170f;
			float headHeight = 38f;
			float headMargin = 19.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "Favourable Points", 14);
			// ITEMS
			FavourableItem[] items = { new FavourableItem("Blue, Green", "Lucky Color", green),
					new FavourableItem("6, 15, 24, 33,\n42, 51, 60", "Good Number", blue),
					new FavourableItem("2", "Lucky Number", purple), new FavourableItem("Friday", "Lucky Day", orange),

					new FavourableItem("Saturn,\nMercury, Venus", "Good Planet", green),
					new FavourableItem("20, 29, 38, 47", "Good Year", blue),
					new FavourableItem("Virgo Capricorn\nAquarius", "Friendly Sign", purple),
					new FavourableItem("Leo, Scorpio,\nCapricorn, Pisces", "Good Lagna", orange),

					new FavourableItem("Silver or Gold", "Lucky Metal", green),
					new FavourableItem("Diamond", "Lucky Stone", purple) };
			drawFavourableItems(poppinsRegularFont, favX, favY, favWidth, favHeight, items);

			favX = 40f;
			favY = 125f;
			favWidth = 514f;
			favHeight = 240f;

			bgX = favX - 10f;
			bgY = favY - 8f;
			bgWidth = favWidth + 19f;
			bgHeight = favHeight + 37f;

			headWidth = 170f;
			headHeight = 38f;
			headMargin = 19.2f;
			drawBgWithHeader(bgX, bgY, bgWidth, bgHeight, headWidth, headHeight, headMargin, "Favourable Points", 14);
			// ITEMS
			FavourableItem[] items1 = { new FavourableItem("Blue, Green", "Lucky Color", green),
					new FavourableItem("6, 15, 24, 33,\n42, 51, 60", "Good Number", blue),
					new FavourableItem("2", "Lucky Number", purple), new FavourableItem("Friday", "Lucky Day", orange),

					new FavourableItem("Saturn,\nMercury, Venus", "Good Planet", green),
					new FavourableItem("20, 29, 38, 47", "Good Year", blue),
					new FavourableItem("Virgo Capricorn\nAquarius", "Friendly Sign", purple),
					new FavourableItem("Leo, Scorpio,\nCapricorn, Pisces", "Good Lagna", orange),

					new FavourableItem("Silver or Gold", "Lucky Metal", green),
					new FavourableItem("Diamond", "Lucky Stone", purple) };
			drawFavourableItems(poppinsRegularFont, favX, favY, favWidth, favHeight, items1);
			drawFooter(poppinsRegularFont, poppinsRegularFont, 0);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

		float valueFontSize = 10f;
		float lineHeight = 12f;

		float valueY = y + height - 23f;

		for (String line : valueLines) {

			float textWidth = font.getStringWidth(line) / 1000f * valueFontSize;

			float textX = x + (width - textWidth) / 2f;
			drawColorShape.drawText(textX, valueY, line, font, valueFontSize, accentColor);
			valueY -= lineHeight;
		}

		// 3. LABEL

		float labelFontSize = 7f;
		float labelWidth = font.getStringWidth(label) / 1000f * labelFontSize;
		float labelX = x + (width - labelWidth) / 2f;
		float labelY = y + 12f;
		drawColorShape.drawText(labelX, labelY, label, font, labelFontSize, Color.BLACK);
	}

	private void drawFavourableItems(PDType0Font font, float sectionX, float sectionY, float sectionWidth,
			float sectionHeight, FavourableItem[] items) throws IOException {

		// COLORS

		// =====================================================
		// CARD LAYOUT
		// =====================================================

		int columns = 4;
		int rows = (int) Math.ceil((double) items.length / columns);

		float gapX = 10f;
		float gapY = 10f;

		float cardWidth = (sectionWidth - ((columns - 1) * gapX)) / columns;

		// Use complete available section height
		float cardHeight = (sectionHeight - ((rows - 1) * gapY)) / rows;

		// =====================================================
		// START FROM TOP OF SECTION
		// =====================================================

		float startX = sectionX;

		float startY = sectionY + sectionHeight - cardHeight;

		// =====================================================
		// DRAW CARDS
		// =====================================================

		for (int i = 0; i < items.length; i++) {

			int row = i / columns;
			int column = i % columns;

			float x = startX + column * (cardWidth + gapX);

			float y = startY - row * (cardHeight + gapY);

			FavourableItem item = items[i];

			drawFavourableCard(font, x, y, cardWidth, cardHeight, item.value, item.label, item.color);
		}
	}
}

class FavourableItem {

	String value;
	String label;
	Color color;

	FavourableItem(String value, String label, Color color) {
		this.value = value;
		this.label = label;
		this.color = color;
	}
}