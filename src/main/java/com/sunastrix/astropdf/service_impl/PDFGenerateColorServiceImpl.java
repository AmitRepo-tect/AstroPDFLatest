package com.sunastrix.astropdf.service_impl;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.springframework.stereotype.Service;
import org.w3c.dom.svg.SVGDocument;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astroganitlib.model.BirthDetailBean;
import com.sunastrix.astropdf.calculation.DashaCalculation;
import com.sunastrix.astropdf.model.PageInfo;
import com.sunastrix.astropdf.pages.AstakvargPage;
import com.sunastrix.astropdf.pages.PlanetPosPage;
import com.sunastrix.astropdf.pages.ForthPage;
import com.sunastrix.astropdf.pages.KPSignificatorPage;
import com.sunastrix.astropdf.pages.KpBhavSandhiPage;
import com.sunastrix.astropdf.pages.KpChartPage;
import com.sunastrix.astropdf.pages.KpCilPage;
import com.sunastrix.astropdf.pages.KpNakshNadiPage;
import com.sunastrix.astropdf.pages.KpRulingPage;
import com.sunastrix.astropdf.pages.LagnaPredictionPage;
import com.sunastrix.astropdf.pages.LifePredictionPage;
import com.sunastrix.astropdf.pages.NakshReportPage;
import com.sunastrix.astropdf.pages.ChalitChartAndTablePage;
import com.sunastrix.astropdf.pages.DashaPage;
import com.sunastrix.astropdf.pages.PlanetConsiderationPage;
import com.sunastrix.astropdf.pages.PrastakvragaPage;
import com.sunastrix.astropdf.pages.BirthDetailPage;
import com.sunastrix.astropdf.pages.SodasvargaChartPage;
import com.sunastrix.astropdf.pages.SodasvargaTablePage;
import com.sunastrix.astropdf.pages.GhatakAndFavourablePage;
import com.sunastrix.astropdf.pages.IndexPage;
import com.sunastrix.astropdf.pages.VarshfalPredictionPage;
import com.sunastrix.astropdf.pages.VarshfalTablePage;
import com.sunastrix.astropdf.pages.VrashfalPage;
import com.sunastrix.astropdf.pages.YoginiDasaPage;
import com.sunastrix.astropdf.service.PDFGenerateColorService;
import com.sunastrix.astropdf.util.ConstantHindi;
import com.sunastrix.astropdf.util.DrawShape;
import com.sunastrix.astropdf.util.Utility;

import de.rototor.pdfbox.graphics2d.PdfBoxGraphics2D;

@Service
public class PDFGenerateColorServiceImpl implements PDFGenerateColorService {
	DesktopHoroNew desktopHoro;
	BirthDetailBean birthDetailBean;
	DashaCalculation calculation;
	PDPageContentStream contentStream;
	public PDType0Font poppinsRegularFont;
	public PDType0Font krutiDevRegularFont;
	public PDType0Font notoSerifDevanagariRegularFont;
	ConstantHindi constantHindi;
	float pageWidth;
	float pageHeight;
	PDRectangle mediaBox;
	DrawShape drawShape = new DrawShape();
	int pageNumber;
	Utility utility;
	ArrayList<PageInfo> pageList;
	ArrayList<PageInfo> pageList2;
	ArrayList<PageInfo> pageList3;
	public static int pageNo;

	public byte[] generatePDF(BirthDetailBean birthDetailBean) throws IOException {
		pageNo = 0;
		pageList = new ArrayList<PageInfo>();
		pageList2 = new ArrayList<PageInfo>();
		pageList3 = new ArrayList<PageInfo>();
		PDDocument document = new PDDocument();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		pageNumber = 1;
		try {
			InputStream fontStream1 = getClass().getResourceAsStream("/fonts/poppins_regular.ttf");
			InputStream fontStream2 = getClass().getResourceAsStream("/fonts/Kruti-Dev-Regular.ttf");
			InputStream fontStream3 = getClass().getResourceAsStream("/fonts/NotoSerifDevanagari-Regular.ttf");
			if (fontStream1 == null || fontStream2 == null || fontStream3 == null) {
				throw new IOException("Font resource not found in classpath: /fonts/poppins_regular.ttf");
			}
			poppinsRegularFont = PDType0Font.load(document, fontStream1);
			krutiDevRegularFont = PDType0Font.load(document, fontStream2);
			notoSerifDevanagariRegularFont = PDType0Font.load(document, fontStream3);
		} catch (Exception e) {
			System.out.println("font " + e.getMessage());
		}
		this.birthDetailBean = birthDetailBean;
		desktopHoro = getDesktopHoro(birthDetailBean);
		constantHindi = new ConstantHindi();
		utility = new Utility();
		printCoverPage(document);

		PageInfo pageInfo = new BirthDetailPage(desktopHoro, birthDetailBean).drawPage(document, poppinsRegularFont,
				krutiDevRegularFont);
		pageList.add(pageInfo);
		pageInfo = new GhatakAndFavourablePage(desktopHoro).drawPage(document, poppinsRegularFont, krutiDevRegularFont);
		pageList.add(pageInfo);
		pageInfo = new ForthPage(desktopHoro).drawPage(document, poppinsRegularFont, krutiDevRegularFont);
		pageList.add(pageInfo);
		pageInfo = new PlanetPosPage(desktopHoro, birthDetailBean).drawPage(document, poppinsRegularFont,
				krutiDevRegularFont);
		pageList.add(pageInfo);
		pageInfo = new ChalitChartAndTablePage(desktopHoro, birthDetailBean).drawPage(document, poppinsRegularFont,
				krutiDevRegularFont);
		pageList.add(pageInfo);
		pageInfo = new AstakvargPage(desktopHoro).drawPage(document, poppinsRegularFont, krutiDevRegularFont);
		pageList.add(pageInfo);
		pageInfo = new PrastakvragaPage(desktopHoro).printPrastakvargaTable(document, poppinsRegularFont,
				krutiDevRegularFont);
		pageList.add(pageInfo);
		DashaPage dashaPage = new DashaPage(desktopHoro, birthDetailBean);
		pageInfo = dashaPage.printAnterDasha(document, poppinsRegularFont, krutiDevRegularFont);
		pageList.add(pageInfo);
		pageInfo = dashaPage.printPratyntarDasha(document, poppinsRegularFont, krutiDevRegularFont);
		pageList.add(pageInfo);
		pageInfo = new YoginiDasaPage(desktopHoro, birthDetailBean).printDasha(document, poppinsRegularFont,
				krutiDevRegularFont);
		pageList.add(pageInfo);
		pageInfo = new SodasvargaChartPage(desktopHoro).printChart(document, poppinsRegularFont, krutiDevRegularFont);
		pageList.add(pageInfo);
		SodasvargaTablePage sodasvargaTablePage = new SodasvargaTablePage(desktopHoro, birthDetailBean);
		pageInfo = sodasvargaTablePage.printShodasvargaTable(document, poppinsRegularFont, krutiDevRegularFont);
		pageList.add(pageInfo);
		pageInfo = sodasvargaTablePage.printShodasvargaBhavTable(document, poppinsRegularFont, krutiDevRegularFont);
		pageList2.add(pageInfo);
		pageInfo = new LagnaPredictionPage(desktopHoro, birthDetailBean).drawPage(document, poppinsRegularFont,
				krutiDevRegularFont);
		pageList2.add(pageInfo);
		pageInfo = new NakshReportPage(desktopHoro, birthDetailBean).drawPage(document, poppinsRegularFont,
				krutiDevRegularFont);
		pageList2.add(pageInfo);
		pageInfo = new VarshfalPredictionPage(desktopHoro, birthDetailBean).drawPage(document, poppinsRegularFont,
				krutiDevRegularFont);
		pageList2.add(pageInfo);
		pageInfo = new PlanetConsiderationPage(desktopHoro, birthDetailBean).drawPage(document, poppinsRegularFont,
				krutiDevRegularFont);
		pageList2.add(pageInfo);
		pageInfo = new LifePredictionPage(desktopHoro, birthDetailBean).drawPage(document, poppinsRegularFont,
				krutiDevRegularFont);
		pageList2.add(pageInfo);
		pageInfo = new KpChartPage(desktopHoro).drawPage(document, poppinsRegularFont, krutiDevRegularFont);
		pageList2.add(pageInfo);
		pageInfo = new KpBhavSandhiPage(desktopHoro).drawPage(document, poppinsRegularFont, krutiDevRegularFont);
		pageList2.add(pageInfo);
		pageInfo = new KPSignificatorPage(desktopHoro).drawPage(document, poppinsRegularFont, krutiDevRegularFont);
		pageList2.add(pageInfo);
		pageInfo = new KpNakshNadiPage(desktopHoro).drawPage(document, poppinsRegularFont, krutiDevRegularFont);
		pageList2.add(pageInfo);
		pageInfo = new KpCilPage(desktopHoro).drawPage(document, poppinsRegularFont, krutiDevRegularFont);
		pageList2.add(pageInfo);
		pageInfo = new KpRulingPage(desktopHoro).drawPage(document, poppinsRegularFont, krutiDevRegularFont);
		pageList2.add(pageInfo);
		pageInfo = new VrashfalPage(desktopHoro).drawPage(document, poppinsRegularFont, krutiDevRegularFont);
		pageList3.add(pageInfo);
		pageInfo = new VarshfalTablePage(desktopHoro, birthDetailBean).drawPage(document, poppinsRegularFont,
				krutiDevRegularFont);
		pageList3.add(pageInfo);
		IndexPage indexPage = new IndexPage();
		document.getPages().insertBefore(
				indexPage.drawIndexPage(document, poppinsRegularFont, krutiDevRegularFont, pageList),
				document.getPage(1));
		document.getPages().insertBefore(
				indexPage.drawIndexPage(document, poppinsRegularFont, krutiDevRegularFont, pageList2),
				document.getPage(2));
		document.getPages().insertBefore(
				indexPage.drawIndexPage(document, poppinsRegularFont, krutiDevRegularFont, pageList3),
				document.getPage(3));
		document.save(byteArrayOutputStream);
		return byteArrayOutputStream.toByteArray();
	};

	public DesktopHoroNew getDesktopHoro(BirthDetailBean birthDetailBean) {
		DesktopHoroNew args1 = new DesktopHoroNew();
		try {
			args1.setBirthDetail(birthDetailBean);
			args1.initialize();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return args1;
	}

	void printCoverPage(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			mediaBox = page.getMediaBox();
			pageWidth = mediaBox.getWidth();
			pageHeight = mediaBox.getHeight();
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);

			Color gold = new Color(190, 165, 90);
			Color color = new Color(110, 35, 35);
			Color gray = new Color(120, 120, 120);

			drawShape.drawBorder(pageWidth, pageHeight);
			float centerX = pageWidth / 2f;
			float y = pageHeight - 60f;
			drawShape.drawStar(centerX - 30f, y + 5f, 8f, gold);
			drawShape.drawText("ॐ", centerX - 8f, y, 15f, gold, poppinsRegularFont);
			drawShape.drawStar(centerX + 30f, y + 5f, 8f, gold);
			y = pageHeight - 120;
			String text = ",LVªksxf.kr T;ksfr\"k fjiksVZ"; // Kruti Dev text
			drawShape.drawTextHorizontalCenter(y, text, 30, krutiDevRegularFont, color);
			y = pageHeight - 150;
			text = "vkidh tUedqaMyh vkSj xzg fLFkfr dk laiw.kZ fo'ys\"k.k";
			drawShape.drawTextHorizontalCenter(y, text, 20, krutiDevRegularFont);

			drawPersonDetails("Jitendra Singh chauhan", "10 - 06 - 2026", "17 : 25 : 19", "Agra (27N9 78E0 5.5)");

			text = "Horoscope By"; // Kruti Dev text
			y = pageHeight - 550;
			drawShape.drawColorTextHorizontalCenter(y, text, 10, poppinsRegularFont, gray);

			text = "AstroGanit.com ";
			y = pageHeight - 570;
			drawShape.drawTextHorizontalCenter(y, text, 14, poppinsRegularFont, color);
			text = "SunAstrix Soft Pvt Ltd";
			y = pageHeight - 590;
			drawShape.drawColorTextHorizontalCenter(y, text, 10, poppinsRegularFont, gray);
			text = "Copyright © 2026 by Astro Ganit Software. All rights reserved." + "Terms of Use";
			y = pageHeight / 24;
			drawCopyRightText(poppinsRegularFont);
			drawShape.drawColorImage();
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void drawPersonDetails(String name, String date, String time, String place) throws IOException {

		float boxWidth = 400f;
		float boxHeight = 100f;
		float boxX = (pageWidth - boxWidth) / 2f;
		float boxY = pageHeight - 500f;
		Color white = Color.WHITE;
		Color boxColor = new Color(130, 10, 10);
		Color yellow = new Color(255, 215, 0);
		Color bottomLineColor = new Color(190, 165, 90);
		drawShape.drawRoundedRect(boxX, boxY, boxWidth, boxHeight, 6f, boxColor);
		float nameY = boxY + 72f;
		float dottedLineY = boxY + 57f;
		float dateY = boxY + 37f;
		float timeY = boxY + 25f;
		float placeY = boxY + 13f;
		drawShape.drawCenteredBoldText(boxX, boxWidth, nameY, name, 12, poppinsRegularFont, white);
		contentStream.setStrokingColor(yellow);
		drawShape.drawDottedLine(boxX + 40f, dottedLineY, boxX + boxWidth - 40f, dottedLineY);
		drawShape.drawCenteredText(boxX, boxWidth, dateY, "Date: " + date, 9, poppinsRegularFont, white);
		drawShape.drawCenteredText(boxX, boxWidth, timeY, "Time: " + time, 9, poppinsRegularFont, white);
		drawShape.drawCenteredText(boxX, boxWidth, placeY, "Place: " + place, 9, poppinsRegularFont, white);
		contentStream.setStrokingColor(bottomLineColor);
		drawShape.drawLine(80f, boxY - 15f, pageWidth - 80f, boxY - 15f);
	}

	void drawCopyRightText(PDType0Font font) throws IOException {
		Color gray = new Color(120, 120, 120);
		Color termsColor = new Color(180, 140, 60); // your desired color
		String copyright = "Copyright © 2026 by Astro Ganit Software. All rights reserved.";
		String terms = "Terms of Use";
		float fontSize = 7f;
		float copyrightWidth = font.getStringWidth(copyright) / 1000f * fontSize;
		float termsWidth = font.getStringWidth(terms) / 1000f * fontSize;
		float gap = 2f;
		float totalWidth = copyrightWidth + gap + termsWidth;
		float startX = (pageWidth - totalWidth) / 2f;
		float y = 25f;
		drawShape.drawText(startX, y, copyright, (int) fontSize, font, gray);
		drawShape.drawText(startX + copyrightWidth + gap, y, terms, (int) fontSize, font, termsColor);
	}

}
