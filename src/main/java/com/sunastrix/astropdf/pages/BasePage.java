package com.sunastrix.astropdf.pages;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astroganitlib.model.BirthDetailBean;
import com.sunastrix.astropdf.util.ConstantHindi;
import com.sunastrix.astropdf.util.DrawColorShape;
import com.sunastrix.astropdf.util.DrawShape;
import com.sunastrix.astropdf.util.Utility;

//http://10.244.125.216:5001/generatepdf_color?name=Jdjdjdjd&sex=M&day=24&month=7&year=2026&hrs=22&min=35&sec=24&place=Jhunjhunun&latDeg=28&latMin=0&latNS=N&longDeg=75&longMin=30&longEW=E&state=New Delhi&country=India&timezone=5.5&timezoneStr=5.5&dst=0&ayanamsa=0&charting=0&kphn=0&button1=Get+Kundali&languageCode=0
public class BasePage {
	Utility utility = new Utility();
	ConstantHindi constantHindi = new ConstantHindi();
	BirthDetailBean birthDetailBean;
	DesktopHoroNew desktopHoro;
	float pageWidth;
	float pageHeight;
	PDDocument document;
	DrawShape drawShape = new DrawShape();
	DrawColorShape drawColorShape = new DrawColorShape();
	PDPageContentStream contentStream;
	public PDType0Font poppinsRegularFont;
	public PDType0Font krutiDevRegularFont;
	public PDType0Font notoSerifDevanagariRegularFont;

	public void drawPageBorder(PDDocument document, PDPage page) throws IOException {
		float outerMargin = 5f;
		float innerMargin = 10f;

		drawColorShape.drawRectAngle(outerMargin, outerMargin, pageWidth - (outerMargin * 2),
				pageHeight - (outerMargin * 2), new Color(105, 20, 20), 1.5f);

		drawColorShape.drawRectAngle(innerMargin, innerMargin, pageWidth - (innerMargin * 2),
				pageHeight - (innerMargin * 2), new Color(145, 30, 25), 0.8f);

	}

	public void drawCornerImages() {
		try {
			float cornerSize = 30f;
			byte[] svgBytes = getClass().getResourceAsStream("/images/corner_left_top.svg").readAllBytes();
			float margin = 15f;
			drawColorShape.drawSvg(svgBytes, margin, pageHeight - margin - cornerSize, cornerSize, cornerSize, 0);
			drawColorShape.drawSvg(svgBytes, pageWidth - margin - cornerSize, pageHeight - margin - cornerSize,
					cornerSize, cornerSize, 1);
			drawColorShape.drawSvg(svgBytes, pageWidth - margin - cornerSize, margin, cornerSize, cornerSize, 2);
			drawColorShape.drawSvg(svgBytes, margin, margin, cornerSize, cornerSize, 3);
		} catch (Exception e) {

		}

	}

	void drawHeader(float pageWidth, float pageHeight, PDType0Font titleFont, String title) throws IOException {

		float centerX = pageWidth / 2f;

		// Title baseline
		float titleY = pageHeight - 55f;

		// =========================================================
		// COLORS
		// =========================================================

		Color maroon = new Color(105, 0, 0);
		Color orange = new Color(225, 147, 0);
		contentStream.saveGraphicsState();

		// 1. TITLE

		// String title = "Astrological Profile";
		float fontSize = 26f;
		float titleWidth = titleFont.getStringWidth(title) / 1000f * fontSize;
		float titleX = centerX - titleWidth / 2f;
		drawColorShape.drawBoldText(titleX, titleY, title, titleFont, fontSize, maroon);

		// =====================================================
		// STAR
		// =====================================================

		float starY = titleY + 10f;
		drawColorShape.drawFourPointStar(titleX - 32f, starY, 9f, orange);
		drawColorShape.drawFourPointStar(titleX + titleWidth + 32f, starY, 9f, orange);

		// =====================================================
		// 4. DECORATIVE LINE
		// =====================================================

		float lineY = pageHeight - 105f;
		byte[] svgBytes = getClass().getResourceAsStream("/images/ornamental_shape.svg").readAllBytes();
		float lineWidth = 105f;
		float lineHeight = 41f;
		float lineX = (pageWidth - lineWidth) / 2f;
		try {
			drawColorShape.drawSvgExact(svgBytes, lineX, lineY, lineWidth, lineHeight);
		} catch (Exception e) {

		}
		Color gold = new Color(243, 155, 0);
		float centerY = lineY + (lineHeight / 2f) + 6.5f;
		float gap = -3f;
		float sideLength = 100f;
		float radius = 3f;
		// LEFT
		float leftBulletX = lineX - gap - sideLength;
		float leftLineEndX = lineX - gap;
		drawColorShape.drawLine(leftBulletX, centerY, leftLineEndX, centerY, 1f, gold);
		drawColorShape.drawFilledCircle(contentStream, leftBulletX, centerY, radius, gold);
		// RIGHT
		float rightLineStartX = lineX + lineWidth + gap;
		float rightBulletX = rightLineStartX + sideLength;
		drawColorShape.drawLine(rightLineStartX, centerY, rightBulletX, centerY, 1f, gold);
		drawColorShape.drawFilledCircle(contentStream, rightBulletX, centerY, radius, gold);
		contentStream.restoreGraphicsState();

	}

	protected void drawBgWithHeader(float x, float y, float width, float height, float headerWidth, float headerHeight,
			float headerMargin, String headText, int fontSize) throws IOException {
		Color borderColor = new Color(245, 70, 20);
		contentStream.saveGraphicsState();
		contentStream.setNonStrokingColor(Color.WHITE);
		contentStream.setStrokingColor(borderColor);
		contentStream.setLineWidth(1.2f);
		float radius = 12f;
		drawColorShape.drawRoundedRectangle(x, y, width, height, radius);
		contentStream.fillAndStroke();
		contentStream.restoreGraphicsState();
		float textWidth = new Utility().getTextWidth(headText, krutiDevRegularFont, fontSize);
		float headwidth = textWidth + 50f;
		drawColorShape.drawImage("/images/head_new.png", x - .5f, y + height - headerMargin, headwidth, headerHeight);

		// (float x, float y, String text, PDType0Font font, float fontSize, Color
		// color);
		float startY = utility.getTextBaseline(krutiDevRegularFont, y + height - headerMargin, headerHeight, fontSize);
		drawColorShape.drawBoldText(x + 20, startY, headText, krutiDevRegularFont, fontSize, Color.WHITE);
	}

	public void drawFooter(PDType0Font titleFont, PDType0Font subtitleFont, int pageNumber) throws IOException {

		// =========================================================
		// COLORS
		// =========================================================

		Color green = new Color(0, 91, 72);
		Color gray = new Color(125, 125, 125);
		Color gold = new Color(225, 147, 0);
		Color pageBox = new Color(255, 190, 20);

		// =========================================================
		// FOOTER POSITIONS
		// =========================================================

		float centerX = pageWidth / 2f;

		// Main footer text
		float titleY = 50f;

		// Subtitle
		float subtitleY = 30f;

		// contentStream.saveGraphicsState();

		// =====================================================
		// 1. ASTROGANIT KUNDLI
		// =====================================================

		String title = "AstroGanit Kundli";

		float titleFontSize = 13f;

		float titleWidth = titleFont.getStringWidth(title) / 1000f * titleFontSize;

		float titleX = centerX - titleWidth / 2f;

		drawShape.drawText(title, titleX, titleY, titleFontSize, green, titleFont);

		// =====================================================
		// 2. LEFT DECORATIVE LINE
		// =====================================================

		float lineY = titleY + 5f;

		float lineStartX = titleX - 38f;

		float lineEndX = titleX - 8f;

		drawColorShape.drawLine(lineStartX, lineY, lineEndX, lineY, 1.2f, gold);

		// Left dot

		drawColorShape.drawSolidRectAngle(lineStartX - 2f, lineY - 2f, 4f, 4f, gold);

		// =====================================================
		// 3. RIGHT DECORATIVE LINE
		// =====================================================

		float rightLineStart = titleX + titleWidth + 8f;

		float rightLineEnd = titleX + titleWidth + 38f;

		drawColorShape.drawLine(rightLineStart, lineY, rightLineEnd, lineY, 1.2f, gold);

		// Right dot

		drawColorShape.drawSolidRectAngle(rightLineEnd - 2f, lineY - 2f, 4f, 4f, gold);

		// =====================================================
		// 4. POWERED BY TEXT
		// =====================================================

		String subtitle = "Powered by SunAstrix Soft Pvt. Ltd.";

		float subtitleFontSize = 11f;

		float subtitleWidth = subtitleFont.getStringWidth(subtitle) / 1000f * subtitleFontSize;

		float subtitleX = centerX - subtitleWidth / 2f;
		drawShape.drawText(subtitle, subtitleX, subtitleY, subtitleFontSize, gray, subtitleFont);

		// =====================================================
		// 5. PAGE NUMBER BOX
		// =====================================================

		float boxWidth = 30f;
		float boxHeight = 30f;
		float boxX = pageWidth - 40f - boxWidth;
		float boxY = 25f;

		// -----------------------------------------------------
		// Rounded yellow box
		// -----------------------------------------------------

		contentStream.setNonStrokingColor(pageBox);
		drawColorShape.drawRoundedRectangle(boxX, boxY, boxWidth, boxHeight, 6f);
		contentStream.fill();

		// -----------------------------------------------------
		// Box border
		// -----------------------------------------------------

		contentStream.setStrokingColor(new Color(220, 155, 0));
		contentStream.setLineWidth(0.8f);
		drawColorShape.drawRoundedRectangle(boxX, boxY, boxWidth, boxHeight, 6f);
		contentStream.stroke();

		// =====================================================
		// 6. PAGE NUMBER
		// =====================================================

		String number = String.valueOf(pageNumber);
		float numberFontSize = 13f;
		float numberWidth = titleFont.getStringWidth(number) / 1000f * numberFontSize;
		float numberX = boxX + (boxWidth - numberWidth) / 2f;
		float numberY = boxY + 9f;
		drawShape.drawText(number, numberX, numberY, numberFontSize, Color.BLACK, titleFont);

	}

}
