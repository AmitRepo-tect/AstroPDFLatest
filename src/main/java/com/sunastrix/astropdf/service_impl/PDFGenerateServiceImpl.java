package com.sunastrix.astropdf.service_impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astroganitlib.model.BirthDetailBean;
import com.sunastrix.astroganitlib.model.DateTimeBean;
import com.sunastrix.astropdf.calculation.AshtakVargaCalculation;
import com.sunastrix.astropdf.calculation.ChalitCalculation;
import com.sunastrix.astropdf.calculation.DashaCalculation;
import com.sunastrix.astropdf.calculation.GemstoneCalculation;
import com.sunastrix.astropdf.calculation.KPPlanetSignificationView2Calculation;
import com.sunastrix.astropdf.calculation.KarkanshAndSwanshCalcultion;
import com.sunastrix.astropdf.calculation.KpCilSubCalculation;
import com.sunastrix.astropdf.calculation.KpCilSubSubCalculation;
import com.sunastrix.astropdf.calculation.KpKundliCalculation;
import com.sunastrix.astropdf.calculation.KpNakshtraNadiCalculation;
import com.sunastrix.astropdf.calculation.KpRulingPlanetCalculation;
import com.sunastrix.astropdf.calculation.LagnaCalculation;
import com.sunastrix.astropdf.calculation.LifePredictionCalculation;
import com.sunastrix.astropdf.calculation.NakshtraReportCalculation;
import com.sunastrix.astropdf.calculation.PlanetAndSunPlanetPositionCalculation;
import com.sunastrix.astropdf.calculation.PlanetConsiderationCalculation;
import com.sunastrix.astropdf.calculation.PrashtakVargaCalculation;
import com.sunastrix.astropdf.calculation.TransitCalculation;
import com.sunastrix.astropdf.calculation.VarshfalCalculation;
import com.sunastrix.astropdf.calculation.YoginiDashaCalculation;
import com.sunastrix.astropdf.model.BasicKundliPlanetSubData;
import com.sunastrix.astropdf.model.BasicPlanetDataModel;
import com.sunastrix.astropdf.model.BasicPlanetSubDataModel;
import com.sunastrix.astropdf.model.CharAntaraDashaModel;
import com.sunastrix.astropdf.model.ChartDetailModel;
import com.sunastrix.astropdf.model.DasaBean;
import com.sunastrix.astropdf.model.GemstoneItem;
import com.sunastrix.astropdf.model.HouseSignificatorsBean;
import com.sunastrix.astropdf.model.KPCilSubBean;
import com.sunastrix.astropdf.model.KPCilSubSubBean;
import com.sunastrix.astropdf.model.KPNakshatraNadiBean;
import com.sunastrix.astropdf.model.KpRulingPlanetBean;
import com.sunastrix.astropdf.model.KundliChalitTableModel;
import com.sunastrix.astropdf.model.LifePredictionModel;
import com.sunastrix.astropdf.model.NakshtraReportBean;
import com.sunastrix.astropdf.model.PersonalityPrediction;
import com.sunastrix.astropdf.model.PlanetConsiderationBean;
import com.sunastrix.astropdf.model.PlanetSignificationBean;
import com.sunastrix.astropdf.model.PlanetSignificationView2Bean;
import com.sunastrix.astropdf.model.PrastharashtakvargaModel;
import com.sunastrix.astropdf.model.TransitModel;
import com.sunastrix.astropdf.model.VarshfalDashaBean;
import com.sunastrix.astropdf.model.VarshfalPlanetData;
import com.sunastrix.astropdf.model.YoginiDashaModel;
import com.sunastrix.astropdf.service.PDFGenerateService;
import com.sunastrix.astropdf.util.AxisPoint;
import com.sunastrix.astropdf.util.ConstantHindi;
import com.sunastrix.astropdf.util.DrawShape;
import com.sunastrix.astropdf.util.Utility;

@Service
public class PDFGenerateServiceImpl implements PDFGenerateService {
	DesktopHoroNew desktopHoro;
	BirthDetailBean birthDetailBean;
	PDPageContentStream contentStream;
	public PDType0Font poppinsRegularFont;
	public PDType0Font krutiDevRegularFont;
	public PDType0Font notoSerifDevanagariRegularFont;
	ConstantHindi constantHindi;
	float pageWidth;
	float pageHeight;
	PDRectangle mediaBox;
	DrawShape drawShape = new DrawShape();
	ArrayList<PrastharashtakvargaModel> prashtakVargaList;
	int pageNumber;
	Utility utility;
	DashaCalculation calculation;
	ArrayList<DasaBean> anterDasaList;
	ArrayList<DasaBean> pratyantraDasaList;

	public byte[] generatePDF(BirthDetailBean birthDetailBean) throws IOException {
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
		printFirstPage(document);
		printSecondPage(document);
		printThirdPage(document);
		printForthPage(document);
		printFifthPage(document);
		printSixthPage(document);
		printSeventhPage(document);
		printEigthPage(document);
		printNinthPage(document);
		printTenthPage(document);
		printeleventhPage(document);
		printTwelvethPage(document);
		printThirteenPage(document);
		printForteenPage(document);
		printPage15(document);
		printPage16(document);
		printPage17(document);
		printVimshotriDasha(document);
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

	private void addWatermarkUnderContent(PDDocument document, PDPage page) throws IOException {
		PDRectangle pageSize = page.getMediaBox();

		String text = "AstroGanit";
		PDType1Font font = PDType1Font.HELVETICA_BOLD;
		int fontSize = 80;

		// Measure text width
		float textWidth = font.getStringWidth(text) / 1000 * fontSize;
		float textHeight = font.getFontDescriptor().getCapHeight() / 1000 * fontSize;
		// Diagonal size using rotation
		double radians = Math.toRadians(45);
		double rotatedWidth = textWidth * Math.cos(radians) + textHeight * Math.sin(radians);
		double rotatedHeight = textWidth * Math.sin(radians) + textHeight * Math.cos(radians);

		// Center the rotated text
		float centerX = (float) ((pageWidth - rotatedWidth) / 2);
		float centerY = (float) ((pageHeight - rotatedHeight) / 2);
		try (PDPageContentStream contentStream = new PDPageContentStream(document, page,
				PDPageContentStream.AppendMode.PREPEND, // <== this makes it go under content
				true, true)) {
			contentStream.beginText();
			contentStream.setFont(font, fontSize);
			contentStream.setNonStrokingColor(240, 240, 240); // light gray
			contentStream.setTextRotation(Math.toRadians(45), centerX, centerY);
			contentStream.showText(text);
			contentStream.endText();
		}
	}

	void addPageHeading(float pageHeight, float pageWidth) throws Exception {
		drawShape.drawLine(0, pageHeight - 30, pageWidth, pageHeight - 30);
		drawShape.drawText(20, pageHeight - 20, "" + pageNumber, 12, poppinsRegularFont);
		drawShape.drawTextHorizontalCenter(pageHeight - 20, "Astro Ganit", 12, poppinsRegularFont);
		drawShape.drawText(pageWidth - 150, pageHeight - 20, "www.astroganit.com", 12, poppinsRegularFont);
		pageNumber++;
	}

	void printText(DrawShape drawShape, float x, float pageHeight, String[] values, PDType0Font font) {
		float y = pageHeight;
		for (int i = 0; i < values.length; i++) {
			try {

				drawShape.drawText(x, y, values[i], 16, font);
				y = y - 30;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void printHeading(float[] xaxis, float y, String[] arr) throws IOException {
		y = y - 2;
		for (int i = 0; i < arr.length; i++) {
			drawShape.drawText(xaxis[i], y, arr[i], 16, krutiDevRegularFont);
		}
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
			String text = ",LVªksxf.kr T;ksfr\"k fjiksVZ"; // Kruti Dev text
			float y = pageHeight - 120;
			drawShape.drawTextHorizontalCenter(y, text, 30, krutiDevRegularFont);
			drawShape.drawTextHorizontalCenter(y, text, 30, krutiDevRegularFont);
			drawShape.drawTextHorizontalCenter(y, text, 30, krutiDevRegularFont);
			text = "vkidh tUedqaMyh vkSj xzg fLFkfr dk laiw.kZ fo'ys\"k.k";
			y = pageHeight - 150;
			drawShape.drawTextHorizontalCenter(y, text, 20, krutiDevRegularFont);
			text = "Horoscope By"; // Kruti Dev text
			y = pageHeight - 670;
			drawShape.drawTextHorizontalCenter(y, text, 12, poppinsRegularFont);
			text = "AstroGanit.com ";
			y = pageHeight - 690;
			drawShape.drawTextHorizontalCenter(y, text, 16, poppinsRegularFont);
			text = "SunAstrix Soft Pvt Ltd";
			y = pageHeight - 710;
			drawShape.drawTextHorizontalCenter(y, text, 12, poppinsRegularFont);

			drawShape.drawImage();
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	void printFirstPage(PDDocument document) {
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
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			printPersonalDetail(pageHeight, pageWidth);
			printPanchang(pageHeight, pageWidth);
			printAvakahadaChakar(pageHeight, pageWidth);

			contentStream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	void printSecondPage(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			printLagnaChart();
			printMoonChart();
			printNavmanshChart();
			printPlanetPositionTable();
			printSubPlanetPositionTable();
			contentStream.close();
		} catch (Exception e) {

			System.out.println(e);
		}
		// addChart();

	}

	void printThirdPage(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			printChalitChart();
			printGocharChart();
			printKarkanshChart();
			printSwanshChart();
			printChalitTable();
			printAstakvargTable();
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printForthPage(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			prashtakVargaList = new PrashtakVargaCalculation(desktopHoro).getPrashtakVargaData();
			printPrashtakvargaTable1();
			printPrashtakvargaTable2();
			printPrashtakvargaTable3();
			printPrashtakvargaTable4();
			printPrashtakvargaTable5();
			printPrashtakvargaTable6();
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printFifthPage(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			printPrashtakvargaTable7();
			printPrashtakvargaTable8();
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printSixthPage(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			drawShape.drawText(20, pageHeight - 50, ";ksfxuh n'kk", 16, krutiDevRegularFont);
			drawShape.drawText(20, pageHeight - 50, ";ksfxuh n'kk", 16, krutiDevRegularFont);
			ArrayList<YoginiDashaModel> dataList = getYoginiDashaData();
			printYoginiDasha1(dataList.get(0));
			printYoginiDasha2(dataList.get(1));
			printYoginiDasha3(dataList.get(2));
			printYoginiDasha4(dataList.get(3));
			printYoginiDasha5(dataList.get(4));
			printYoginiDasha6(dataList.get(5));
			printYoginiDasha7(dataList.get(6));
			printYoginiDasha8(dataList.get(7));
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printSeventhPage(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			String[] chartHeading = constantHindi.shodasChartHeading;

			ArrayList<ChartDetailModel> arrayList = new ArrayList<ChartDetailModel>();
			arrayList.add(new ChartDetailModel(20f, pageHeight - 230f,
					getIntArray(desktopHoro.getPositionForShodasvarg(0), 0)));
			arrayList.add(new ChartDetailModel(212f, pageHeight - 230f,
					getIntArray(desktopHoro.getPositionForShodasvarg(1), 0)));
			arrayList.add(new ChartDetailModel(404f, pageHeight - 230f,
					getIntArray(desktopHoro.getPositionForShodasvarg(2), 0)));
			arrayList.add(new ChartDetailModel(20f, pageHeight - 430f,
					getIntArray(desktopHoro.getPositionForShodasvarg(3), 0)));
			arrayList.add(new ChartDetailModel(212f, pageHeight - 430f,
					getIntArray(desktopHoro.getPositionForShodasvarg(4), 0)));
			arrayList.add(new ChartDetailModel(404f, pageHeight - 430f,
					getIntArray(desktopHoro.getPositionForShodasvarg(5), 0)));
			arrayList.add(new ChartDetailModel(20f, pageHeight - 630f,
					getIntArray(desktopHoro.getPositionForShodasvarg(6), 0)));
			arrayList.add(new ChartDetailModel(212f, pageHeight - 630f,
					getIntArray(desktopHoro.getPositionForShodasvarg(7), 0)));
			arrayList.add(new ChartDetailModel(404f, pageHeight - 630f,
					getIntArray(desktopHoro.getPositionForShodasvarg(8), 0)));
			arrayList.add(new ChartDetailModel(20f, pageHeight - 830f,
					getIntArray(desktopHoro.getPositionForShodasvarg(9), 0)));
			arrayList.add(new ChartDetailModel(212f, pageHeight - 830f,
					getIntArray(desktopHoro.getPositionForShodasvarg(10), 0)));
			arrayList.add(new ChartDetailModel(404f, pageHeight - 830f,
					getIntArray(desktopHoro.getPositionForShodasvarg(11), 0)));
			ChartDetailModel chartDetailModel;
			for (int i = 0; i < arrayList.size(); i++) {
				chartDetailModel = arrayList.get(i);
				printChart(chartDetailModel.getStartX(), chartDetailModel.getStartY(),
						chartDetailModel.getPlanetArray(), chartHeading[i]);
			}

			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printEigthPage(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			int chartNumber = 12;
			String[] chartHeading = constantHindi.shodasChartHeading;
			ArrayList<ChartDetailModel> arrayList = new ArrayList<ChartDetailModel>();
			arrayList.add(new ChartDetailModel(20f, pageHeight - 230f,
					getIntArray(desktopHoro.getPositionForShodasvarg(12), 0)));
			arrayList.add(new ChartDetailModel(212f, pageHeight - 230f,
					getIntArray(desktopHoro.getPositionForShodasvarg(13), 0)));
			arrayList.add(new ChartDetailModel(404f, pageHeight - 230f,
					getIntArray(desktopHoro.getPositionForShodasvarg(14), 0)));
			arrayList.add(new ChartDetailModel(20f, pageHeight - 430f,
					getIntArray(desktopHoro.getPositionForShodasvarg(15), 0)));
			ChartDetailModel chartDetailModel;
			for (int i = 0; i < arrayList.size(); i++) {
				chartDetailModel = arrayList.get(i);
				printChart(chartDetailModel.getStartX(), chartDetailModel.getStartY(),
						chartDetailModel.getPlanetArray(), chartHeading[chartNumber]);
				chartNumber++;
			}
			printShodasTable();
			printShodasBhavTable();
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printNinthPage(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			printKPChart();
			printKPRashiChart();
			printKPPlanetPositionTable();
			printBhavSandhiTable();
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		// addChart();

	}

	void printTenthPage(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addPageHeading(pageHeight, pageWidth);
			addWatermarkUnderContent(document, page);
			printPlanetSignificatorTable();
			printHouseSignificatorTable();
			printPlanetSignificatorView2Table();
			printRularTable();
			printMiscTable();
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printeleventhPage(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addPageHeading(pageHeight, pageWidth);
			addWatermarkUnderContent(document, page);
			printNakshNadi();
			printCILSubTable();
			printCILSubSubTable();
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printTwelvethPage(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			printVarshfalDetail(pageHeight, pageWidth);
			printVrashfalChart();
			printVarshfalPlanetPositionTable();
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printThirteenPage(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			printVrashfalPridiction(document);
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printForteenPage(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			printLagnaReport(document);
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printPage15(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			printNakshtraReport(document);
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printPage16(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			printPlanetConsideration(document);
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printPage17(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			printGeneralPrediction(document);
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printVimshotriDasha(PDDocument document) {
		printPage18(document);
		initDasha();
		printPage19(document);
		printPage20(document);
		printPage21(document);
		printPage22(document);
	}

	void printPage18(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			printVimDasha(document);
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printPage19(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			printPratyantraDasha1(document);
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printPage20(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			printPratyantraDasha1(document);
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printPage21(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			printPratyantraDasha1(document);
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printPage22(PDDocument document) {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addWatermarkUnderContent(document, page);
			addPageHeading(pageHeight, pageWidth);
			printPratyantraDasha1(document);
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printThirteenPage1(PDDocument document) {
		try {
			String text = "mijksä ifjHkk\"kk dk vFkZ gS fd fucU/k ys[kd ds eu dh ço`fÙk ds vuq:i gh gksuk pkfg, vkSj fucU/k dk ys[ku LoPNUn xfr ij vk/kkfjr gks vFkkZr fuca/k ,slk fy[kuk pkfg, fd ys[kd dk fparu] oSpkfjd Lrj] fo\"k; ij mldh Lo;a dh fopkj/kkjk Li\"V gks tkuh pkfg,A";
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);

			contentStream = new PDPageContentStream(document, page);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addPageHeading(pageHeight, pageWidth);
			float startY = pageHeight - 50;
			drawShape.drawText(20, startY, "yXu pkVZ", 16, krutiDevRegularFont);
			contentStream.setFont(krutiDevRegularFont, 12);
			float y = addParagraph(document, startY - 20, text);
			drawShape.drawText(20, y - 20, "yXu pkVZ", 16, krutiDevRegularFont);
			contentStream.setFont(krutiDevRegularFont, 12);
			y = addParagraph(document, y - 40, text);
			drawShape.drawText(20, y - 20, "yXu pkVZ", 16, krutiDevRegularFont);
			contentStream.setFont(krutiDevRegularFont, 12);
			y = addParagraph(document, y - 40, text);
			drawShape.drawText(20, y - 20, "yXu pkVZ", 16, krutiDevRegularFont);
			contentStream.setFont(krutiDevRegularFont, 12);
			y = addParagraph(document, y - 40, text);
			drawShape.drawText(20, y - 20, "yXu pkVZ", 16, krutiDevRegularFont);
			contentStream.setFont(krutiDevRegularFont, 12);
			y = addParagraph(document, y - 40, text);
			drawShape.drawText(20, y - 20, "yXu pkVZ", 16, krutiDevRegularFont);
			contentStream.setFont(krutiDevRegularFont, 12);
			y = addParagraph(document, y - 40, text);
			drawShape.drawText(20, y - 20, "yXu pkVZ", 16, krutiDevRegularFont);
			contentStream.setFont(krutiDevRegularFont, 12);
			y = addParagraph(document, y - 40, text);
			drawShape.drawText(20, y - 20, "yXu pkVZ", 16, krutiDevRegularFont);
			contentStream.setFont(krutiDevRegularFont, 12);
			y = addParagraph(document, y - 40, text);
			drawShape.drawText(20, y - 20, "yXu pkVZ", 16, krutiDevRegularFont);
			contentStream.setFont(krutiDevRegularFont, 12);
			y = addParagraph(document, y - 40, text);
			drawShape.drawText(20, y - 20, "yXu pkVZ", 16, krutiDevRegularFont);
			contentStream.setFont(krutiDevRegularFont, 12);
			y = addParagraph(document, y - 40, text);
			drawShape.drawText(20, y - 20, "yXu pkVZ", 16, krutiDevRegularFont);
			contentStream.setFont(krutiDevRegularFont, 12);
			y = addParagraph(document, y - 40, text);
			drawShape.drawText(20, y - 20, "yXu pkVZ", 16, krutiDevRegularFont);
			contentStream.setFont(krutiDevRegularFont, 12);
			y = addParagraph(document, y - 40, text);
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	void printPersonalDetail(float pageHeight, float pageWidth) throws IOException {
		float x = 20;
		float y = pageHeight - 210;
		float h = 150;
		float w = 267;

		drawShape.drawText(20, pageHeight - 50, "tUe fooj.k", 16, krutiDevRegularFont);
		drawShape.drawText(20, pageHeight - 50, "tUe fooj.k", 16, krutiDevRegularFont);
		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, 4, 30);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + w / 2 - 20, y, x + w / 2 - 20, y + h));
		drawShape.drawColumn(points);
		String[] labels = constantHindi.birthDetailLabel;
		String[] values = { desktopHoro.getName(), getBirthDate(), desktopHoro.getBirthTime(), desktopHoro.getPlace(),
				String.valueOf(desktopHoro.getAyan()) };
		printText(drawShape, x + 5, pageHeight - 80, labels, krutiDevRegularFont);
		printText(drawShape, x + (w / 2) - 15, pageHeight - 80, values, notoSerifDevanagariRegularFont);
	}

	String getBirthDate() {
		int[] arr = desktopHoro.getBirthDate();
		return arr[0] + "-" + constantHindi.monthName[arr[1] - 1] + "-" + arr[2];
	}

	void printPanchang(float pageHeight, float pageWidth) {
		try {
			float x = 20;
			float y = pageHeight - 510;
			float h = 270;
			float w = 267;
			drawShape.drawText(20, pageHeight - 230, "iapkax", 16, krutiDevRegularFont);
			drawShape.drawText(20, pageHeight - 230, "iapkax", 16, krutiDevRegularFont);
			drawShape.drawRoundedRectangle(x, y, h, w);
			drawShape.drawRowAndColoum(x, y, h, w, 8, 30);
			ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
			points.add(new AxisPoint(x + w / 2 - 20, y, x + w / 2 - 20, y + h));
			drawShape.drawColumn(points);
			String[] panchangLabels = constantHindi.panchangLabel;

			printText(drawShape, x + 5, pageHeight - 260, panchangLabels, krutiDevRegularFont);
			String[] values = { desktopHoro.getPakshaName(), desktopHoro.getTithiName(), desktopHoro.getNakshatraName(),
					desktopHoro.getHinduWeekdayName(), desktopHoro.getHinduWeekdayName(), desktopHoro.getYoganame(),
					desktopHoro.getKaranName(), utility.getFormattedTime(desktopHoro.getSunRiseTimeIntArr()),
					utility.getFormattedTime(desktopHoro.getSunSetTimeIntArr()) };

			printText(drawShape, x + (w / 2) - 15, pageHeight - 260, values, krutiDevRegularFont);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void printAvakahadaChakar(float pageHeight, float pageWidth) {
		try {
			float x = 307;
			float y = pageHeight - 660;
			float h = 600;
			float w = 267;
			drawShape.drawText(x, pageHeight - 50, "vodgM+k pØ", 16, krutiDevRegularFont);
			drawShape.drawText(x, pageHeight - 50, "vodgM+k pØ", 16, krutiDevRegularFont);
			drawShape.drawRoundedRectangle(x, y, h, w);
			drawShape.drawRowAndColoum(x, y, h, w, 19, 30);
			ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
			points.add(new AxisPoint(x + w / 2 - 20, y, x + w / 2 - 20, y + h));
			drawShape.drawColumn(points);
			String[] panchangLabels = constantHindi.avakahadaChakarLabel;
			printText(drawShape, x + 5, pageHeight - 80, panchangLabels, krutiDevRegularFont);
			System.out.println("Balance" + desktopHoro.getBalanceOfDasha());
			String[] values = { desktopHoro.getPayaName(), desktopHoro.getVarnaName(), desktopHoro.getYoniName(),
					desktopHoro.getGanaName(), desktopHoro.getVasyaName(), desktopHoro.getNadiName(),
					getBalanceOfDasha(desktopHoro.getBalanceOfDashaIntArr()), desktopHoro.getLagnaSign(),
					desktopHoro.getLagnaLordName(), desktopHoro.getRasiName(), desktopHoro.getRasiLordName(),
					desktopHoro.getNakshatraName(), desktopHoro.getNakshatraLordName(), desktopHoro.getJulianDayValue(),
					desktopHoro.getIndianSunSignName(), desktopHoro.getSunSignName(),
					"" + desktopHoro.getAyanamsaDms(birthDetailBean.getLanguageCode()),
					"" + desktopHoro.getAyanamsaType(), desktopHoro.getObliquityDms(birthDetailBean.getLanguageCode()),
					utility.getFormattedTime(desktopHoro.getSiderealTimeIntArr()) };
			printText(drawShape, x + (w / 2) - 15, pageHeight - 80, values, krutiDevRegularFont);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void printLagnaChart() throws IOException {
		drawShape.drawText(20, pageHeight - 50, "yXu pkVZ", 16, krutiDevRegularFont);
		drawShape.drawText(20, pageHeight - 50, "yXu pkVZ", 16, krutiDevRegularFont);
		drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 230f);
		float[] x = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38, 20f + 80, 20f + 124, 20f + 136,
				20f + 95, 20f + 136, 20f + 124 };
		float[] y = { pageHeight - 230f + 90, pageHeight - 230f + 132, pageHeight - 230f + 122, pageHeight - 230f + 80,
				pageHeight - 230f + 40, pageHeight - 230f + 30, pageHeight - 230f + 70, pageHeight - 230f + 30,
				pageHeight - 230f + 40, pageHeight - 230f + 80, pageHeight - 230f + 122, pageHeight - 230f + 132 };
		int[] planetArray = getIntArray(desktopHoro.getPositionForShodasvarg(0), 0);
		drawRashiInBhav(x, y, planetArray[12]);
		printPlanetsInHouse(20f, pageHeight - 230f, planetArray, planetArray[12]);
	}

	void printMoonChart() throws IOException {
		drawShape.drawText(20, pageHeight - 250f, "paæ pkVZ", 16, krutiDevRegularFont);
		drawShape.drawText(20, pageHeight - 250f, "paæ pkVZ", 16, krutiDevRegularFont);
		drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 430f);

		float[] x1 = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38, 20f + 80, 20f + 124, 20f + 136,
				20f + 95, 20f + 136, 20f + 124 };
		float[] y1 = { pageHeight - 430f + 90, pageHeight - 430f + 132, pageHeight - 430f + 122, pageHeight - 430f + 80,
				pageHeight - 430f + 40, pageHeight - 430f + 30, pageHeight - 430f + 70, pageHeight - 430f + 30,
				pageHeight - 430f + 40, pageHeight - 430f + 80, pageHeight - 430f + 122, pageHeight - 430f + 132 };
		int[] planetArray = getIntArray(desktopHoro.getPositionForShodasvarg(0), 2);
		drawRashiInBhav(x1, y1, planetArray[12]);
		printPlanetsInHouse(20f, pageHeight - 430f, planetArray, planetArray[12]);
	}

	void printNavmanshChart() throws IOException {
		drawShape.drawText(20, pageHeight - 450f, "uoeka'k pkVZ", 16, krutiDevRegularFont);
		drawShape.drawText(20, pageHeight - 450f, "uoeka'k pkVZ", 16, krutiDevRegularFont);
		drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 630f);

		float[] x2 = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38, 20f + 80, 20f + 124, 20f + 136,
				20f + 95, 20f + 136, 20f + 124 };
		float[] y2 = { pageHeight - 630f + 90, pageHeight - 630f + 132, pageHeight - 630f + 122, pageHeight - 630f + 80,
				pageHeight - 630f + 40, pageHeight - 630f + 30, pageHeight - 630f + 70, pageHeight - 630f + 30,
				pageHeight - 630f + 40, pageHeight - 630f + 80, pageHeight - 630f + 122, pageHeight - 630f + 132 };
		int[] planetArray = getIntArray(desktopHoro.getPositionForShodasvarg(5), 0);
		drawRashiInBhav(x2, y2, planetArray[12]);
		printPlanetsInHouse(20f, pageHeight - 630f, planetArray, planetArray[12]);
	}

	void printPlanetPositionTable() throws IOException {
		drawShape.drawText(210, pageHeight - 50, "xzg fLFkfr", 16, krutiDevRegularFont);
		drawShape.drawText(210, pageHeight - 50, "xzg fLFkfr", 16, krutiDevRegularFont);
		drawShape.drawTableHeader(210, pageHeight - 85, 25, 360);
		float x = 210;
		float y = pageHeight - 410f;
		float h = 350;
		float w = 360;
		drawShape.drawTableWithHeader(x, y, h, w, 25);

		drawShape.drawRaw(x, y, h, w, 13, 25);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 50, y, x + 50, y + 350));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 350));
		points.add(new AxisPoint(x + 220, y, x + 220, y + 350));
		points.add(new AxisPoint(x + 300, y, x + 300, y + 350));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 60, x + 130, x + 230, x + 310 };
		printHeading(xaxis, pageHeight - 75, constantHindi.planetPosHeading);
		ArrayList<BasicPlanetDataModel> list = new PlanetAndSunPlanetPositionCalculation(desktopHoro).getPlanetsData();
		populatePlanetTable(xaxis, y + 325, list);
	}

	void populatePlanetTable(float[] xaxis, float y, ArrayList<BasicPlanetDataModel> list) throws IOException {
		y = y + 8;
		BasicPlanetDataModel basicPlanetDataModel;
		for (int i = 0; i < list.size(); i++) {
			basicPlanetDataModel = list.get(i);
			drawShape.drawText(xaxis[0], y - (i + 1) * 25, basicPlanetDataModel.getPlaName(), 14, krutiDevRegularFont);
			drawShape.drawText(xaxis[1], y - (i + 1) * 25, String.valueOf(basicPlanetDataModel.getSign()), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[2], y - (i + 1) * 25, basicPlanetDataModel.getDegree(), 10, poppinsRegularFont);
			drawShape.drawText(xaxis[3], y - (i + 1) * 25, String.valueOf(basicPlanetDataModel.getNaks()), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[4], y - (i + 1) * 25, String.valueOf(basicPlanetDataModel.getPlaCharan()), 10,
					poppinsRegularFont);
		}
	}

	void printSubPlanetPositionTable() throws IOException {

		drawShape.drawText(210, pageHeight - 430, "lc xzg fLFkfr", 16, krutiDevRegularFont);
		drawShape.drawText(210, pageHeight - 430, "lc xzg fLFkfr", 16, krutiDevRegularFont);
		drawShape.drawTableHeader(210, pageHeight - 465, 25, 360);
		float x = 210;
		float y = pageHeight - 790f;
		float h = 350;
		float w = 360;
		drawShape.drawTableWithHeader(x, y, h, w, 25);
		drawShape.drawRaw(x, y, h, w, 13, 25);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 50, y, x + 50, y + 350));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 350));
		points.add(new AxisPoint(x + 210, y, x + 210, y + 350));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 350));
		points.add(new AxisPoint(x + 310, y, x + 310, y + 350));
		drawShape.drawColumn(points);

		float[] xaxis = { x + 10, x + 60, x + 170, x + 220, x + 270, x + 320 };
		printHeading(xaxis, pageHeight - 455, constantHindi.subPlanetPosHeading);
		ArrayList<BasicPlanetSubDataModel> list = new PlanetAndSunPlanetPositionCalculation(desktopHoro)
				.getPlanetsSubData();
		populateSubPlanetTable(xaxis, y + 325, list);
	}

	void populateSubPlanetTable(float[] xaxis, float y, ArrayList<BasicPlanetSubDataModel> list) throws IOException {
		y = y + 8;
		BasicPlanetSubDataModel basicPlanetSubDataModel;
		for (int i = 0; i < list.size(); i++) {
			basicPlanetSubDataModel = list.get(i);
			drawShape.drawText(xaxis[0], y - (i + 1) * 25, basicPlanetSubDataModel.getPlaName(), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[1], y - (i + 1) * 25, basicPlanetSubDataModel.getPlaDeg(), 10, poppinsRegularFont);
			drawShape.drawText(xaxis[2], y - (i + 1) * 25, basicPlanetSubDataModel.getSignLord(), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[3], y - (i + 1) * 25, basicPlanetSubDataModel.getNakshLord(), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[4], y - (i + 1) * 25, basicPlanetSubDataModel.getSubLord(), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[5], y - (i + 1) * 25, basicPlanetSubDataModel.getSubSubLord(), 14,
					krutiDevRegularFont);
		}
	}

	void printChalitChart() throws IOException {
		drawShape.drawText(20, pageHeight - 50, "pfyr pkVZ", 16, krutiDevRegularFont);
		drawShape.drawText(20, pageHeight - 50, "pfyr pkVZ", 16, krutiDevRegularFont);
		drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 230f);
		float[] x = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38, 20f + 80, 20f + 124, 20f + 136,
				20f + 95, 20f + 136, 20f + 124 };
		float[] y = { pageHeight - 230f + 90, pageHeight - 230f + 132, pageHeight - 230f + 122, pageHeight - 230f + 80,
				pageHeight - 230f + 40, pageHeight - 230f + 30, pageHeight - 230f + 70, pageHeight - 230f + 30,
				pageHeight - 230f + 40, pageHeight - 230f + 80, pageHeight - 230f + 122, pageHeight - 230f + 132 };
		ChalitCalculation chalitCalculation = new ChalitCalculation(desktopHoro);
		int[] planetArray = chalitCalculation.getChalitChartArray();
		drawRashiInBhavForChalit(x, y, chalitCalculation.getKpDegreeArray());
		printPlanetsInHouse(20f, pageHeight - 230f, planetArray, planetArray[12]);
	}

	void printGocharChart() throws IOException {
		drawShape.drawText(20, pageHeight - 250f, "xkspj", 16, krutiDevRegularFont);
		drawShape.drawText(20, pageHeight - 250f, "xkspj", 16, krutiDevRegularFont);
		drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 430f);

		float[] x1 = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38, 20f + 80, 20f + 124, 20f + 136,
				20f + 95, 20f + 136, 20f + 124 };
		float[] y1 = { pageHeight - 430f + 90, pageHeight - 430f + 132, pageHeight - 430f + 122, pageHeight - 430f + 80,
				pageHeight - 430f + 40, pageHeight - 430f + 30, pageHeight - 430f + 70, pageHeight - 430f + 30,
				pageHeight - 430f + 40, pageHeight - 430f + 80, pageHeight - 430f + 122, pageHeight - 430f + 132 };

		TransitModel transitModel = new TransitCalculation().calculate(birthDetailBean);
		drawRashiInBhav(x1, y1, transitModel.getLagna());
		printPlanetsInHouse(20f, pageHeight - 430f, transitModel.getPlaRashiArray(), transitModel.getLagna());
	}

	void printKarkanshChart() throws IOException {
		drawShape.drawText(20, pageHeight - 450f, "dkjdka'k", 16, krutiDevRegularFont);
		drawShape.drawText(20, pageHeight - 450f, "dkjdka'k", 16, krutiDevRegularFont);
		drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 630f);

		float[] x2 = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38, 20f + 80, 20f + 124, 20f + 136,
				20f + 95, 20f + 136, 20f + 124 };
		float[] y2 = { pageHeight - 630f + 90, pageHeight - 630f + 132, pageHeight - 630f + 122, pageHeight - 630f + 80,
				pageHeight - 630f + 40, pageHeight - 630f + 30, pageHeight - 630f + 70, pageHeight - 630f + 30,
				pageHeight - 630f + 40, pageHeight - 630f + 80, pageHeight - 630f + 122, pageHeight - 630f + 132 };

		int lagna = new KarkanshAndSwanshCalcultion(desktopHoro).getKarakanshLagna();

		int[] planetArray = getIntArray(desktopHoro.getPositionForShodasvarg(0), 0);
		drawRashiInBhav(x2, y2, lagna);
		printPlanetsInHouse(20f, pageHeight - 630f, planetArray, lagna);
	}

	void printSwanshChart() throws IOException {
		drawShape.drawText(20, pageHeight - 650f, "Loka'k", 16, krutiDevRegularFont);
		drawShape.drawText(20, pageHeight - 650f, "Loka'k", 16, krutiDevRegularFont);
		drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 830f);

		float[] x2 = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38, 20f + 80, 20f + 124, 20f + 136,
				20f + 95, 20f + 136, 20f + 124 };
		float[] y2 = { pageHeight - 830f + 90, pageHeight - 830f + 132, pageHeight - 830f + 122, pageHeight - 830f + 80,
				pageHeight - 830f + 40, pageHeight - 830f + 30, pageHeight - 830f + 70, pageHeight - 830f + 30,
				pageHeight - 830f + 40, pageHeight - 830f + 80, pageHeight - 830f + 122, pageHeight - 830f + 132 };
		int lagna = new KarkanshAndSwanshCalcultion(desktopHoro).getKarakanshLagna();

		int[] planetArray = getIntArray(desktopHoro.getPositionForShodasvarg(5), 0);
		drawRashiInBhav(x2, y2, lagna);
		printPlanetsInHouse(20f, pageHeight - 830f, planetArray, lagna);

	}

	void printChalitTable() throws IOException {
		drawShape.drawText(210, pageHeight - 50, "pfyr rkfydk", 16, krutiDevRegularFont);
		drawShape.drawText(210, pageHeight - 50, "pfyr rkfydk", 16, krutiDevRegularFont);
		drawShape.drawTableHeader(210, pageHeight - 85, 25, 360);
		float x = 210;
		float y = pageHeight - 385f;
		float h = 325;
		float w = 360;
		drawShape.drawTableWithHeader(x, y, h, w, 25);
		drawShape.drawRaw(x, y, h, w, 12, 25);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 30, y, x + 30, y + 325));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 325));
		points.add(new AxisPoint(x + 180, y, x + 180, y + 325));
		points.add(new AxisPoint(x + 235, y, x + 235, y + 325));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 40, x + 90, x + 190, x + 245 };
		printHeading(xaxis, pageHeight - 75, constantHindi.chalitTableHeading);
		ArrayList<KundliChalitTableModel> list = new ChalitCalculation(desktopHoro).getChalitTableData();
		populateChalitTableData(xaxis, y + 300, list);

	}

	void populateChalitTableData(float[] xaxis, float y, ArrayList<KundliChalitTableModel> list) throws IOException {
		y = y + 8;
		KundliChalitTableModel kundliChalitTableModel;
		for (int i = 0; i < list.size(); i++) {
			kundliChalitTableModel = list.get(i);
			drawShape.drawText(xaxis[0], y - (i + 1) * 25, String.valueOf(kundliChalitTableModel.getBhav()), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[1], y - (i + 1) * 25, String.valueOf(kundliChalitTableModel.getBhBegSign()), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[2], y - (i + 1) * 25, String.valueOf(kundliChalitTableModel.getBhBegDeg()), 10,
					poppinsRegularFont);
			drawShape.drawText(xaxis[3], y - (i + 1) * 25, String.valueOf(kundliChalitTableModel.getBhMidSign()), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[4], y - (i + 1) * 25, String.valueOf(kundliChalitTableModel.getBhMidDeg()), 10,
					poppinsRegularFont);
		}
	}

	void printAstakvargTable() throws IOException {
		drawShape.drawText(210, pageHeight - 430, "v\"VdoxZ", 16, krutiDevRegularFont);
		drawShape.drawText(210, pageHeight - 430, "v\"VdoxZ", 16, krutiDevRegularFont);
		drawShape.drawTableHeader(210, pageHeight - 465, 25, 360);
		float x = 210;
		float y = pageHeight - 765f;
		float h = 325;
		float w = 360;
		drawShape.drawTableWithHeader(x, y, h, w, 25);
		drawShape.drawRaw(x, y, h, w, 12, 25);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 40, y, x + 40, y + 325));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 325));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 325));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 325));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 325));
		points.add(new AxisPoint(x + 240, y, x + 240, y + 325));
		points.add(new AxisPoint(x + 280, y, x + 280, y + 325));
		points.add(new AxisPoint(x + 320, y, x + 320, y + 325));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 15, x + 55, x + 95, x + 135, x + 175, x + 215, x + 255, x + 295, x + 335 };

		printHeading(xaxis, pageHeight - 455, constantHindi.ashatakvargHeading);
		float[] xaxis1 = { x + 20, x + 60, x + 100, x + 140, x + 180, x + 220, x + 260, x + 300, x + 340 };
		String[] ashtakvarg = new AshtakVargaCalculation(desktopHoro).getAshtakVargaData();
		populateAshtakvargaTable(xaxis1, y + 300, ashtakvarg);
	}

	void populateAshtakvargaTable(float[] xaxis, float y, String[] strArray) throws IOException {
		y = y + 8;
		for (int i = 0; i < strArray.length; i++) {
			String[] arr = strArray[i].split(",");
			int total = 0;
			for (int j = 0; j < arr.length + 1; j++) {
				if (j == 0) {
					drawShape.drawText(xaxis[j] - 3, y - (i + 1) * 25, String.valueOf(i + 1), 14, krutiDevRegularFont);
				} else {
					total += Integer.parseInt(arr[j - 1]);
					drawShape.drawText(xaxis[j] - 3, y - (i + 1) * 25, arr[j - 1], 14, krutiDevRegularFont);
				}
			}
			drawShape.drawText(xaxis[8] - 3, y - (i + 1) * 25, String.valueOf(total), 14, krutiDevRegularFont);
		}
	}

	void printPrashtakvargaTable1() throws IOException {

		drawShape.drawText(10, pageHeight - 50, "lw;Z", 16, krutiDevRegularFont);
		drawShape.drawText(10, pageHeight - 50, "lw;Z", 16, krutiDevRegularFont);
		float x = 10;
		float y = pageHeight - 260;
		float h = 200;
		float w = 282;
		drawShape.drawRoundedRectangle(10, pageHeight - 260, 200, 282);
		drawShape.drawRowAndColoum(10, pageHeight - 260, 200, 282, 9, 20);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 20, y, x + 20, y + 200));
		points.add(new AxisPoint(x + 40, y, x + 40, y + 200));
		points.add(new AxisPoint(x + 60, y, x + 60, y + 200));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 200));
		points.add(new AxisPoint(x + 100, y, x + 100, y + 200));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 200));
		points.add(new AxisPoint(x + 140, y, x + 140, y + 200));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 200));
		points.add(new AxisPoint(x + 180, y, x + 180, y + 200));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 200));
		points.add(new AxisPoint(x + 220, y, x + 220, y + 200));
		points.add(new AxisPoint(x + 240, y, x + 240, y + 200));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 200));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 30, x + 50, x + 70, x + 90, x + 110, x + 130, x + 150, x + 170, x + 190, x + 210,
				x + 230, x + 250, x + 270 };
		populatePrashtakvargaTable(xaxis, y + 180, prashtakVargaList.get(0));
	}

	void printPrashtakvargaTable2() throws IOException {
		drawShape.drawText(302, pageHeight - 50, "paæ", 16, krutiDevRegularFont);
		drawShape.drawText(302, pageHeight - 50, "paæ", 16, krutiDevRegularFont);
		float x = 302;
		float y = pageHeight - 260;
		float h = 200;
		float w = 282;
		drawShape.drawRoundedRectangle(302, pageHeight - 260, 200, 282);
		drawShape.drawRowAndColoum(302, pageHeight - 260, 200, 282, 9, 20);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 20, y, x + 20, y + 200));
		points.add(new AxisPoint(x + 40, y, x + 40, y + 200));
		points.add(new AxisPoint(x + 60, y, x + 60, y + 200));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 200));
		points.add(new AxisPoint(x + 100, y, x + 100, y + 200));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 200));
		points.add(new AxisPoint(x + 140, y, x + 140, y + 200));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 200));
		points.add(new AxisPoint(x + 180, y, x + 180, y + 200));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 200));
		points.add(new AxisPoint(x + 220, y, x + 220, y + 200));
		points.add(new AxisPoint(x + 240, y, x + 240, y + 200));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 200));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 30, x + 50, x + 70, x + 90, x + 110, x + 130, x + 150, x + 170, x + 190, x + 210,
				x + 230, x + 250, x + 270 };
		populatePrashtakvargaTable(xaxis, y + 180, prashtakVargaList.get(1));
	}

	void printPrashtakvargaTable3() throws IOException {
		drawShape.drawText(10, pageHeight - 280, "eaxy", 16, krutiDevRegularFont);
		drawShape.drawText(10, pageHeight - 280, "eaxy", 16, krutiDevRegularFont);
		float x = 10;
		float y = pageHeight - 490;
		float h = 200;
		float w = 282;
		drawShape.drawRoundedRectangle(10, pageHeight - 490, 200, 282);
		drawShape.drawRowAndColoum(10, pageHeight - 490, 200, 282, 9, 20);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 20, y, x + 20, y + 200));
		points.add(new AxisPoint(x + 40, y, x + 40, y + 200));
		points.add(new AxisPoint(x + 60, y, x + 60, y + 200));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 200));
		points.add(new AxisPoint(x + 100, y, x + 100, y + 200));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 200));
		points.add(new AxisPoint(x + 140, y, x + 140, y + 200));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 200));
		points.add(new AxisPoint(x + 180, y, x + 180, y + 200));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 200));
		points.add(new AxisPoint(x + 220, y, x + 220, y + 200));
		points.add(new AxisPoint(x + 240, y, x + 240, y + 200));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 200));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 30, x + 50, x + 70, x + 90, x + 110, x + 130, x + 150, x + 170, x + 190, x + 210,
				x + 230, x + 250, x + 270 };
		populatePrashtakvargaTable(xaxis, y + 180, prashtakVargaList.get(2));
	}

	void printPrashtakvargaTable4() throws IOException {
		drawShape.drawText(302, pageHeight - 280, "cq/k", 16, krutiDevRegularFont);
		drawShape.drawText(302, pageHeight - 280, "cq/k", 16, krutiDevRegularFont);
		float x = 302;
		float y = pageHeight - 490;
		float h = 200;
		float w = 282;
		drawShape.drawRoundedRectangle(302, pageHeight - 490, 200, 282);
		drawShape.drawRowAndColoum(302, pageHeight - 490, 200, 282, 9, 20);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 20, y, x + 20, y + 200));
		points.add(new AxisPoint(x + 40, y, x + 40, y + 200));
		points.add(new AxisPoint(x + 60, y, x + 60, y + 200));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 200));
		points.add(new AxisPoint(x + 100, y, x + 100, y + 200));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 200));
		points.add(new AxisPoint(x + 140, y, x + 140, y + 200));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 200));
		points.add(new AxisPoint(x + 180, y, x + 180, y + 200));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 200));
		points.add(new AxisPoint(x + 220, y, x + 220, y + 200));
		points.add(new AxisPoint(x + 240, y, x + 240, y + 200));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 200));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 30, x + 50, x + 70, x + 90, x + 110, x + 130, x + 150, x + 170, x + 190, x + 210,
				x + 230, x + 250, x + 270 };
		populatePrashtakvargaTable(xaxis, y + 180, prashtakVargaList.get(3));
	}

	void printPrashtakvargaTable5() throws IOException {
		drawShape.drawText(10, pageHeight - 510, "xq#", 16, krutiDevRegularFont);
		drawShape.drawText(10, pageHeight - 510, "xq#", 16, krutiDevRegularFont);
		float x = 10;
		float y = pageHeight - 720;
		float h = 200;
		float w = 282;
		drawShape.drawRoundedRectangle(10, pageHeight - 720, 200, 282);
		drawShape.drawRowAndColoum(10, pageHeight - 720, 200, 282, 9, 20);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 20, y, x + 20, y + 200));
		points.add(new AxisPoint(x + 40, y, x + 40, y + 200));
		points.add(new AxisPoint(x + 60, y, x + 60, y + 200));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 200));
		points.add(new AxisPoint(x + 100, y, x + 100, y + 200));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 200));
		points.add(new AxisPoint(x + 140, y, x + 140, y + 200));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 200));
		points.add(new AxisPoint(x + 180, y, x + 180, y + 200));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 200));
		points.add(new AxisPoint(x + 220, y, x + 220, y + 200));
		points.add(new AxisPoint(x + 240, y, x + 240, y + 200));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 200));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 30, x + 50, x + 70, x + 90, x + 110, x + 130, x + 150, x + 170, x + 190, x + 210,
				x + 230, x + 250, x + 270 };
		populatePrashtakvargaTable(xaxis, y + 180, prashtakVargaList.get(4));
	}

	void printPrashtakvargaTable6() throws IOException {
		drawShape.drawText(302, pageHeight - 510, "'kqØ", 16, krutiDevRegularFont);
		drawShape.drawText(302, pageHeight - 510, "'kqØ", 16, krutiDevRegularFont);
		float x = 302;
		float y = pageHeight - 720;
		float h = 200;
		float w = 282;
		drawShape.drawRoundedRectangle(302, pageHeight - 720, 200, 282);
		drawShape.drawRowAndColoum(302, pageHeight - 720, 200, 282, 9, 20);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 20, y, x + 20, y + 200));
		points.add(new AxisPoint(x + 40, y, x + 40, y + 200));
		points.add(new AxisPoint(x + 60, y, x + 60, y + 200));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 200));
		points.add(new AxisPoint(x + 100, y, x + 100, y + 200));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 200));
		points.add(new AxisPoint(x + 140, y, x + 140, y + 200));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 200));
		points.add(new AxisPoint(x + 180, y, x + 180, y + 200));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 200));
		points.add(new AxisPoint(x + 220, y, x + 220, y + 200));
		points.add(new AxisPoint(x + 240, y, x + 240, y + 200));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 200));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 30, x + 50, x + 70, x + 90, x + 110, x + 130, x + 150, x + 170, x + 190, x + 210,
				x + 230, x + 250, x + 270 };
		populatePrashtakvargaTable(xaxis, y + 180, prashtakVargaList.get(5));
	}

	void printPrashtakvargaTable7() throws IOException {
		drawShape.drawText(10, pageHeight - 50, "'kfu ", 16, krutiDevRegularFont);
		drawShape.drawText(10, pageHeight - 50, "'kfu ", 16, krutiDevRegularFont);
		float x = 10;
		float y = pageHeight - 260;
		float h = 200;
		float w = 282;
		drawShape.drawRoundedRectangle(10, pageHeight - 260, 200, 282);
		drawShape.drawRowAndColoum(10, pageHeight - 260, 200, 282, 9, 20);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 20, y, x + 20, y + 200));
		points.add(new AxisPoint(x + 40, y, x + 40, y + 200));
		points.add(new AxisPoint(x + 60, y, x + 60, y + 200));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 200));
		points.add(new AxisPoint(x + 100, y, x + 100, y + 200));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 200));
		points.add(new AxisPoint(x + 140, y, x + 140, y + 200));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 200));
		points.add(new AxisPoint(x + 180, y, x + 180, y + 200));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 200));
		points.add(new AxisPoint(x + 220, y, x + 220, y + 200));
		points.add(new AxisPoint(x + 240, y, x + 240, y + 200));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 200));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 30, x + 50, x + 70, x + 90, x + 110, x + 130, x + 150, x + 170, x + 190, x + 210,
				x + 230, x + 250, x + 270 };
		populatePrashtakvargaTable(xaxis, y + 180, prashtakVargaList.get(6));
	}

	void printPrashtakvargaTable8() throws IOException {
		drawShape.drawText(302, pageHeight - 50, "jkgq", 16, krutiDevRegularFont);
		drawShape.drawText(302, pageHeight - 50, "jkgq", 16, krutiDevRegularFont);
		float x = 302;
		float y = pageHeight - 260;
		float h = 200;
		float w = 282;
		drawShape.drawRoundedRectangle(302, pageHeight - 260, 200, 282);
		drawShape.drawRowAndColoum(302, pageHeight - 260, 200, 282, 9, 20);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 20, y, x + 20, y + 200));
		points.add(new AxisPoint(x + 40, y, x + 40, y + 200));
		points.add(new AxisPoint(x + 60, y, x + 60, y + 200));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 200));
		points.add(new AxisPoint(x + 100, y, x + 100, y + 200));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 200));
		points.add(new AxisPoint(x + 140, y, x + 140, y + 200));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 200));
		points.add(new AxisPoint(x + 180, y, x + 180, y + 200));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 200));
		points.add(new AxisPoint(x + 220, y, x + 220, y + 200));
		points.add(new AxisPoint(x + 240, y, x + 240, y + 200));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 200));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 30, x + 50, x + 70, x + 90, x + 110, x + 130, x + 150, x + 170, x + 190, x + 210,
				x + 230, x + 250, x + 270 };
		populatePrashtakvargaTable(xaxis, y + 180, prashtakVargaList.get(7));
	}

	void populatePrashtakvargaTable(float[] xaxis, float y, PrastharashtakvargaModel prastharashtakvargaModel)
			throws IOException {
		y = y + 5;
		String plaName[] = new ConstantHindi().praPlaName;
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
		for (int i = 0; i < 10; i++) {
			if (i == 0) {
				for (int j = 0; j < 13; j++) {
					if (j == 0) {
						drawShape.drawText(xaxis[j] - 3, y, "", 14, krutiDevRegularFont);
					} else {

						drawShape.drawText(xaxis[j] - 3, y, String.valueOf(j), 14, krutiDevRegularFont);
					}
				}
				drawShape.drawText(xaxis[13] - 3, y, ";ks", 14, krutiDevRegularFont);
			} else if (i == 9) {
				for (int j = 0; j < 13; j++) {
					if (j == 0) {
						drawShape.drawText(xaxis[j] - 3, y - i * 20, ";ks", 14, krutiDevRegularFont);
					} else {

						drawShape.drawText(xaxis[j] - 3, y - i * 20, arr9[j - 1], 14, krutiDevRegularFont);
					}
				}
			} else {
				String[] arr = list.get(i - 1);
				int total = 0;
				for (int j = 0; j < arr.length + 1; j++) {
					if (j == 0) {
						drawShape.drawText(xaxis[j] - 3, y - i * 20, plaName[i - 1], 14, krutiDevRegularFont);
					} else {
						total += Integer.parseInt(arr[j - 1]);
						drawShape.drawText(xaxis[j] - 3, y - i * 20, arr[j - 1], 14, krutiDevRegularFont);
					}
				}
				drawShape.drawText(xaxis[13] - 3, y - i * 20, String.valueOf(total), 14, krutiDevRegularFont);
			}

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

	ArrayList<YoginiDashaModel> getYoginiDashaData() {
		YoginiDashaCalculation yoginiDashaCalculation = new YoginiDashaCalculation(birthDetailBean, desktopHoro);
		return yoginiDashaCalculation.getYoginiDashaData();
	}

	void printYoginiDasha1(YoginiDashaModel yoginiDashaModel) throws IOException {

		float x = 20;
		float y = pageHeight - 222;
		float h = 162;
		float w = 267;
		int row = 8;
		int rowH = 18;

		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, row, rowH);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 67, y, x + 67, y + 144));
		points.add(new AxisPoint(x + 167, y, x + 167, y + 144));
		drawShape.drawColumn(points);
		populateYoginiDashaData(x, y + 144, yoginiDashaModel);
	}

	void printYoginiDasha2(YoginiDashaModel yoginiDashaModel) throws IOException {

		float x = 307;
		float y = pageHeight - 222;
		float h = 162;
		float w = 267;
		int row = 8;
		int rowH = 18;
		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, row, rowH);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 67, y, x + 67, y + 144));
		points.add(new AxisPoint(x + 167, y, x + 167, y + 144));
		drawShape.drawColumn(points);
		populateYoginiDashaData(x, y + 144, yoginiDashaModel);
	}

	void printYoginiDasha3(YoginiDashaModel yoginiDashaModel) throws IOException {

		float x = 20;
		float y = pageHeight - 414;
		float h = 162;
		float w = 267;
		int row = 8;
		int rowH = 18;
		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, row, rowH);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 67, y, x + 67, y + 144));
		points.add(new AxisPoint(x + 167, y, x + 167, y + 144));
		drawShape.drawColumn(points);

		populateYoginiDashaData(x, y + 144, yoginiDashaModel);
	}

	void printYoginiDasha4(YoginiDashaModel yoginiDashaModel) throws IOException {

		float x = 307;
		float y = pageHeight - 414;
		float h = 162;
		float w = 267;
		int row = 8;
		int rowH = 18;
		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, row, rowH);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 67, y, x + 67, y + 144));
		points.add(new AxisPoint(x + 167, y, x + 167, y + 144));
		drawShape.drawColumn(points);
		populateYoginiDashaData(x, y + 144, yoginiDashaModel);
	}

	void printYoginiDasha5(YoginiDashaModel yoginiDashaModel) throws IOException {

		float x = 20;
		float y = pageHeight - 606;
		float h = 162;
		float w = 267;
		int row = 8;
		int rowH = 18;
		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, row, rowH);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 67, y, x + 67, y + 144));
		points.add(new AxisPoint(x + 167, y, x + 167, y + 144));
		drawShape.drawColumn(points);
		populateYoginiDashaData(x, y + 144, yoginiDashaModel);
	}

	void printYoginiDasha6(YoginiDashaModel yoginiDashaModel) throws IOException {
		float x = 307;
		float y = pageHeight - 606;
		float h = 162;
		float w = 267;
		int row = 8;
		int rowH = 18;
		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, row, rowH);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 67, y, x + 67, y + 144));
		points.add(new AxisPoint(x + 167, y, x + 167, y + 144));
		drawShape.drawColumn(points);
		populateYoginiDashaData(x, y + 144, yoginiDashaModel);
	}

	void printYoginiDasha7(YoginiDashaModel yoginiDashaModel) throws IOException {

		float x = 20;
		float y = pageHeight - 798;
		float h = 162;
		float w = 267;
		int row = 8;
		int rowH = 18;
		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, row, rowH);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 67, y, x + 67, y + 144));
		points.add(new AxisPoint(x + 167, y, x + 167, y + 144));
		drawShape.drawColumn(points);
		populateYoginiDashaData(x, y + 144, yoginiDashaModel);
	}

	void printYoginiDasha8(YoginiDashaModel yoginiDashaModel) throws IOException {

		float x = 307;
		float y = pageHeight - 798;
		float h = 162;
		float w = 267;
		int row = 8;
		int rowH = 18;
		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, row, rowH);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 67, y, x + 67, y + 144));
		points.add(new AxisPoint(x + 167, y, x + 167, y + 144));
		drawShape.drawColumn(points);
		populateYoginiDashaData(x, y + 144, yoginiDashaModel);
	}

	void populateYoginiDashaData(float x, float y, YoginiDashaModel yoginiDashaModel) throws IOException {
		float[] xaxis = { x + 5, x + 72, x + 172 };
		y = y + 5;
		drawShape.drawText(x + 5, y,
				yoginiDashaModel.getPlanetName() + " " + yoginiDashaModel.getDuration() + " ¼ "
						+ yoginiDashaModel.getStartYear() + "&" + yoginiDashaModel.getEndYear() + " ½",
				14, krutiDevRegularFont);
		ArrayList<CharAntaraDashaModel> list = yoginiDashaModel.getAntaraDashaList();
		for (int i = 0; i < list.size(); i++) {
			drawShape.drawText(xaxis[0], y - (i + 1) * 18, list.get(i).getPlanetName(), 14, krutiDevRegularFont);
			drawShape.drawText(xaxis[1], y - (i + 1) * 18, list.get(i).getStartDate(), 14, krutiDevRegularFont);
			drawShape.drawText(xaxis[2], y - (i + 1) * 18, list.get(i).getEndDate(), 14, krutiDevRegularFont);
		}
	}

	void printShodasTable() throws IOException {
		drawShape.drawText(10, pageHeight - 450, "'kksM\"koxZ rkfydk", 16, krutiDevRegularFont);
		drawShape.drawText(10, pageHeight - 450, "'kksM\"koxZ rkfydk", 16, krutiDevRegularFont);
		float x = 13;
		float y = pageHeight - 800;
		float h = 360;
		float w = 282;
		drawShape.drawRoundedRectangle(13, pageHeight - 800, 340, 275);
		drawShape.drawRowAndColoum(13, pageHeight - 800, 340, 275, 16, 20);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 25, y, x + 25, y + 340));
		points.add(new AxisPoint(x + 50, y, x + 50, y + 340));
		points.add(new AxisPoint(x + 75, y, x + 75, y + 340));
		points.add(new AxisPoint(x + 100, y, x + 100, y + 340));
		points.add(new AxisPoint(x + 125, y, x + 125, y + 340));
		points.add(new AxisPoint(x + 150, y, x + 150, y + 340));
		points.add(new AxisPoint(x + 175, y, x + 175, y + 340));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 340));
		points.add(new AxisPoint(x + 225, y, x + 225, y + 340));
		points.add(new AxisPoint(x + 250, y, x + 250, y + 340));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 13, x + 35, x + 60, x + 85, x + 110, x + 135, x + 160, x + 185, x + 210, x + 235, x + 260,
				x + 285, x + 310, x + 335 };
		populateShodasTable(xaxis, y + 320);
	}

	void printShodasBhavTable() throws IOException {
		drawShape.drawText(302, pageHeight - 450, "'kksM\"koxZ Hkko rkfydk", 16, krutiDevRegularFont);
		drawShape.drawText(302, pageHeight - 450, "'kksM\"koxZ Hkko rkfydk", 16, krutiDevRegularFont);
		float x = 307;
		float y = pageHeight - 800;
		float h = 340;
		float w = 282;
		System.out.println("width" + pageWidth);
		drawShape.drawRoundedRectangle(307, pageHeight - 800, 340, 275);
		drawShape.drawRowAndColoum(307, pageHeight - 800, 340, 275, 16, 20);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 25, y, x + 25, y + 340));
		points.add(new AxisPoint(x + 50, y, x + 50, y + 340));
		points.add(new AxisPoint(x + 75, y, x + 75, y + 340));
		points.add(new AxisPoint(x + 100, y, x + 100, y + 340));
		points.add(new AxisPoint(x + 125, y, x + 125, y + 340));
		points.add(new AxisPoint(x + 150, y, x + 150, y + 340));
		points.add(new AxisPoint(x + 175, y, x + 175, y + 340));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 340));
		points.add(new AxisPoint(x + 225, y, x + 225, y + 340));
		points.add(new AxisPoint(x + 250, y, x + 250, y + 340));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 13, x + 35, x + 60, x + 85, x + 110, x + 135, x + 160, x + 185, x + 210, x + 235, x + 260,
				x + 285, x + 310, x + 335 };
		populateShodasBhavTable(xaxis, y + 320);
	}

	void printChart(float startX, float startY, int[] planetArray, String heading) throws IOException {
		drawShape.drawText(startX, startY + 180, heading, 16, krutiDevRegularFont);
		drawShape.drawText(startX, startY + 180, heading, 16, krutiDevRegularFont);
		drawShape.drawKundli(pageHeight, pageWidth, startX, startY);
		float[] x = { startX + 80, startX + 38, startX + 26, startX + 70, startX + 26, startX + 38, startX + 80,
				startX + 124, startX + 136, startX + 95, startX + 136, startX + 124 };
		float[] y = { startY + 90, startY + 132, startY + 122, startY + 80, startY + 40, startY + 30, startY + 70,
				startY + 30, startY + 40, startY + 80, startY + 122, startY + 132 };
		drawRashiInBhav(x, y, planetArray[12]);
		printPlanetsInHouse(startX, startY, planetArray, planetArray[12]);
	}

	private void drawRashiInBhav(float[] x_axis, float[] y_axis, int lagna) throws IOException {
		int lagna1 = lagna;
		for (int i = 0; i < 12; i++) {
			if (lagna1 > 12)
				lagna1 = 1;
			drawShape.drawText(x_axis[i], y_axis[i], String.valueOf(lagna1), 10, poppinsRegularFont);
			lagna1++;
		}
	}

	private void drawRashiInBhavForChalit(float[] x_axis, float[] y_axis, double[] midDegreeArray) throws IOException {
		int iRasi;

		for (int i = 0; i < 12; i++) {
			iRasi = (int) (midDegreeArray[i] / 30.0) + 1;
			drawShape.drawText(x_axis[i], y_axis[i], String.valueOf(iRasi), 10, poppinsRegularFont);
		}
	}

	private void printPlanetsInHouse(float x, float y, int[] planetArray, int lagnaRashi) throws IOException {
		float rh = 170;
		float rw = 170;
		y = y + rh;

		/* 3,5,9 and 11 */
		float[] housePositionsX3 = { x + 2, x + 12, x + 2, x + 12, x + 3, x + 22 };
		float[] housePositionsY3 = { y - 24, y - 34, y - 44, y - 54, y - 66, y - 44 };
		float[] housePositionsX5 = { x + 2, x + 12, x + 2, x + 12, x + 3, x + 22 };
		float[] housePositionsY5 = { y - rh / 2 - 23, y - rh / 2 - 35, y - rh / 2 - 46, y - rh / 2 - 57,
				y - rh / 2 - 69, y - rh / 2 - 46 };
		float[] housePositionsX9 = { x + rw - 14, x + rw - 26, x + rw - 14, x + rw - 26, x + rw - 14, x + rw - 38 };
		float[] housePositionsY9 = { y - rh / 2 - 23, y - rh / 2 - 35, y - rh / 2 - 46, y - rh / 2 - 57,
				y - rh / 2 - 69, y - rh / 2 - 46 };
		float[] housePositionsX11 = { x + rw - 14, x + rw - 26, x + rw - 14, x + rw - 26, x + rw - 14, x + rw - 38 };
		float[] housePositionsY11 = { y - 24, y - 34, y - 44, y - 54, y - 66, y - 44 };
		/* 2,6,8 and 12 house */
		float[] housePositionsX12 = { x + rw / 2 + 12, x + rw / 2 + 24, x + rw / 2 + 36, x + rw / 2 + 48,
				x + rw / 2 + 60, x + rw / 2 + 36 };
		float[] housePositionsY12 = { y - 8, y - 16, y - 8, y - 16, y - 8, y - 24 };
		float[] housePositionsX2 = { x + 12, x + 24, x + 36, x + 48, x + 60, x + 36 };
		float[] housePositionsY2 = { y - 8, y - 16, y - 8, y - 16, y - 8, y - 24 };
		float[] housePositionsX6 = { x + 12, x + 24, x + 36, x + 48, x + 60, x + 36 };
		float[] housePositionsY6 = { y - rh + 4, y - rh + 12, y - rh + 4, y - rh + 12, y - rh + 4, y - rh + 20 };
		float[] housePositionsX8 = { x + rw / 2 + 12, x + rw / 2 + 24, x + rw / 2 + 36, x + rw / 2 + 48,
				x + rw / 2 + 60, x + rw / 2 + 36 };
		float[] housePositionsY8 = { y - rh + 4, y - rh + 12, y - rh + 4, y - rh + 12, y - rh + 4, y - rh + 20 };
		/* 1,7,4 and 10 house */
		float[] housePositionsX1 = { x + rw / 4 + 18, x + rw / 4 + 36, x + rw / 4 + 54, x + rw / 4 + 27,
				x + rw / 4 + 45, x + rw / 4 + 36 };
		float[] housePositionsY1 = { y - rh / 4, y - rh / 4, y - rh / 4, y - rh / 4 + 15, y - rh / 4 + 15,
				y - rh / 4 - 15 };
		float[] housePositionsX7 = { x + rw / 4 + 18, x + rw / 4 + 36, x + rw / 4 + 54, x + rw / 4 + 27,
				x + rw / 4 + 45, x + rw / 4 + 36 };
		float[] housePositionsY7 = { y - 3 * rh / 4, y - 3 * rh / 4, y - 3 * rh / 4, y - 3 * rh / 4 - 15,
				y - 3 * rh / 4 - 15, y - 3 * rh / 4 + 15 };
		float[] housePositionsX10 = { x + 3 * rw / 4, x + 3 * rw / 4, x + 3 * rw / 4, x + 3 * rw / 4 - 20,
				x + 3 * rw / 4 - 20, x + 3 * rw / 4 + 20 };
		float[] housePositionsY10 = { y - rh / 4 - 24, y - rh / 4 - 44, y - rh / 4 - 64, y - rh / 4 - 34,
				y - rh / 4 - 54, y - rh / 4 - 44 };
		float[] housePositionsX4 = { x + rw / 4 - 5, x + rw / 4 - 5, x + rw / 4 - 5, x + rw / 4 - 25, x + rw / 4 - 25,
				x + rw / 4 + 15 };
		float[] housePositionsY4 = { y - rh / 4 - 24, y - rh / 4 - 44, y - rh / 4 - 64, y - rh / 4 - 34,
				y - rh / 4 - 54, y - rh / 4 - 44 };

		ArrayList<ArrayList<String>> arrayLists = new ArrayList<>();
		ArrayList<float[]> xArrayLists = new ArrayList<>();
		ArrayList<float[]> yArrayLists = new ArrayList<>();

		xArrayLists.add(housePositionsX1);
		xArrayLists.add(housePositionsX2);
		xArrayLists.add(housePositionsX3);
		xArrayLists.add(housePositionsX4);
		xArrayLists.add(housePositionsX5);
		xArrayLists.add(housePositionsX6);
		xArrayLists.add(housePositionsX7);
		xArrayLists.add(housePositionsX8);
		xArrayLists.add(housePositionsX9);
		xArrayLists.add(housePositionsX10);
		xArrayLists.add(housePositionsX11);
		xArrayLists.add(housePositionsX12);

		yArrayLists.add(housePositionsY1);
		yArrayLists.add(housePositionsY2);
		yArrayLists.add(housePositionsY3);
		yArrayLists.add(housePositionsY4);
		yArrayLists.add(housePositionsY5);
		yArrayLists.add(housePositionsY6);
		yArrayLists.add(housePositionsY7);
		yArrayLists.add(housePositionsY8);
		yArrayLists.add(housePositionsY9);
		yArrayLists.add(housePositionsY10);
		yArrayLists.add(housePositionsY11);
		yArrayLists.add(housePositionsY12);
		String[] plaName = constantHindi.plaShortName;
		for (int i = 0; i < 12; i++) {
			arrayLists.add(new ArrayList<>());
		}
		for (int i = 0; i < planetArray.length; i++) {
			int planetBhav = getBhavOfPlant(lagnaRashi, planetArray[i]);
			planetBhav--;
			if (planetBhav >= 12) {
				planetBhav = 0;
			}
			arrayLists.get(planetBhav).add(plaName[i]);
		}
		ArrayList<String> innerList;
		float[] x_axis;
		float[] y_axis;
		int coordinateIndex;
		for (int i = 0; i < arrayLists.size(); i++) {
			innerList = arrayLists.get(i);
			coordinateIndex = 0;
			if (innerList.size() > 0) {
				x_axis = xArrayLists.get(i);
				y_axis = yArrayLists.get(i);
				for (int j = 0; j < innerList.size(); j++) {
					if (coordinateIndex > 5) {
						coordinateIndex = 0;
					}

					drawShape.drawText(x_axis[coordinateIndex], y_axis[coordinateIndex], innerList.get(j), 12,
							krutiDevRegularFont);
					// }
					coordinateIndex++;
				}

			}
		}
	}

	private int getBhavOfPlant(int lagnaRashi, int plntRashi) {
		int bhavNumber;
		bhavNumber = plntRashi - lagnaRashi;
		if (bhavNumber < 0) {
			bhavNumber += 12;
		}
		bhavNumber += 1;
		return bhavNumber;
	}

	private int[] getIntArray(int[] planetPosition, int lagnaPos) {

		int[] intArray = new int[13];
		int lagna = planetPosition[lagnaPos];
		for (int i = 0; i < planetPosition.length - 1; i++) {
			intArray[i] = planetPosition[i + 1];
		}
		intArray[planetPosition.length - 1] = lagna;
		return intArray;
	}

	void populateShodasTable(float[] xaxis, float y) {
		try {
			y = y + 5;
			ConstantHindi constantHindi = new ConstantHindi();
			String columnName[] = constantHindi.shodasColumn;
			String headings[] = constantHindi.shodasHeading;
			int[] arr1 = getIntArray(desktopHoro.getPositionForShodasvarg(0), 0);
			int[] arr2 = getIntArray(desktopHoro.getPositionForShodasvarg(1), 0);
			int[] arr3 = getIntArray(desktopHoro.getPositionForShodasvarg(2), 0);
			int[] arr4 = getIntArray(desktopHoro.getPositionForShodasvarg(3), 0);
			int[] arr5 = getIntArray(desktopHoro.getPositionForShodasvarg(4), 0);
			int[] arr6 = getIntArray(desktopHoro.getPositionForShodasvarg(5), 0);
			int[] arr7 = getIntArray(desktopHoro.getPositionForShodasvarg(6), 0);
			int[] arr8 = getIntArray(desktopHoro.getPositionForShodasvarg(7), 0);
			int[] arr9 = getIntArray(desktopHoro.getPositionForShodasvarg(8), 0);
			int[] arr10 = getIntArray(desktopHoro.getPositionForShodasvarg(9), 0);
			int[] arr11 = getIntArray(desktopHoro.getPositionForShodasvarg(10), 0);
			int[] arr12 = getIntArray(desktopHoro.getPositionForShodasvarg(11), 0);
			int[] arr13 = getIntArray(desktopHoro.getPositionForShodasvarg(12), 0);
			int[] arr14 = getIntArray(desktopHoro.getPositionForShodasvarg(13), 0);
			int[] arr15 = getIntArray(desktopHoro.getPositionForShodasvarg(14), 0);
			int[] arr16 = getIntArray(desktopHoro.getPositionForShodasvarg(15), 0);
			ArrayList<int[]> list = new ArrayList<int[]>();
			list.add(arr1);
			list.add(arr2);
			list.add(arr3);
			list.add(arr4);
			list.add(arr5);
			list.add(arr6);
			list.add(arr7);
			list.add(arr8);
			list.add(arr9);
			list.add(arr10);
			list.add(arr11);
			list.add(arr12);
			list.add(arr13);
			list.add(arr14);
			list.add(arr15);
			list.add(arr16);
			for (int i = 0; i < list.size() + 1; i++) {
				if (i == 0) {
					for (int j = 0; j < 11; j++) {
						if (j == 0) {
							drawShape.drawText(xaxis[j] - 8, y, columnName[i], 14, krutiDevRegularFont);
						} else {

							drawShape.drawText(xaxis[j] - 3, y, headings[j - 1], 14, krutiDevRegularFont);
						}
					}
				} else {
					int[] arr = list.get(i - 1);

					for (int j = 0; j < 11; j++) {
						if (j == 0) {
							drawShape.drawText(xaxis[j] - 8, y - i * 20, columnName[i], 14, krutiDevRegularFont);
						} else {
							if (j == 1) {
								drawShape.drawText(xaxis[j] - 3, y - i * 20, String.valueOf(arr[12]), 14,
										krutiDevRegularFont);

							} else {
								drawShape.drawText(xaxis[j] - 3, y - i * 20, String.valueOf(arr[j - 2]), 14,
										krutiDevRegularFont);

							}
						}
					}
				}

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	void populateShodasBhavTable(float[] xaxis, float y) {
		try {
			y = y + 5;
			ConstantHindi constantHindi = new ConstantHindi();
			String columnName[] = constantHindi.shodasColumn;
			String headings[] = constantHindi.shodasHeading;
			int[] arr1 = getIntArray(desktopHoro.getPositionForShodasvarg(0), 0);
			int[] arr2 = getIntArray(desktopHoro.getPositionForShodasvarg(1), 0);
			int[] arr3 = getIntArray(desktopHoro.getPositionForShodasvarg(2), 0);
			int[] arr4 = getIntArray(desktopHoro.getPositionForShodasvarg(3), 0);
			int[] arr5 = getIntArray(desktopHoro.getPositionForShodasvarg(4), 0);
			int[] arr6 = getIntArray(desktopHoro.getPositionForShodasvarg(5), 0);
			int[] arr7 = getIntArray(desktopHoro.getPositionForShodasvarg(6), 0);
			int[] arr8 = getIntArray(desktopHoro.getPositionForShodasvarg(7), 0);
			int[] arr9 = getIntArray(desktopHoro.getPositionForShodasvarg(8), 0);
			int[] arr10 = getIntArray(desktopHoro.getPositionForShodasvarg(9), 0);
			int[] arr11 = getIntArray(desktopHoro.getPositionForShodasvarg(10), 0);
			int[] arr12 = getIntArray(desktopHoro.getPositionForShodasvarg(11), 0);
			int[] arr13 = getIntArray(desktopHoro.getPositionForShodasvarg(12), 0);
			int[] arr14 = getIntArray(desktopHoro.getPositionForShodasvarg(13), 0);
			int[] arr15 = getIntArray(desktopHoro.getPositionForShodasvarg(14), 0);
			int[] arr16 = getIntArray(desktopHoro.getPositionForShodasvarg(15), 0);
			ArrayList<int[]> list = new ArrayList<int[]>();
			list.add(arr1);
			list.add(arr2);
			list.add(arr3);
			list.add(arr4);
			list.add(arr5);
			list.add(arr6);
			list.add(arr7);
			list.add(arr8);
			list.add(arr9);
			list.add(arr10);
			list.add(arr11);
			list.add(arr12);
			list.add(arr13);
			list.add(arr14);
			list.add(arr15);
			list.add(arr16);
			for (int i = 0; i < list.size() + 1; i++) {
				if (i == 0) {
					for (int j = 0; j < 11; j++) {
						if (j == 0) {
							drawShape.drawText(xaxis[j] - 8, y, columnName[i], 14, krutiDevRegularFont);
						} else {

							drawShape.drawText(xaxis[j] - 3, y, headings[j - 1], 14, krutiDevRegularFont);
						}
					}
				} else {
					int[] arr = list.get(i - 1);

					for (int j = 0; j < 11; j++) {
						if (j == 0) {
							drawShape.drawText(xaxis[j] - 8, y - i * 20, columnName[i], 14, krutiDevRegularFont);
						} else {
							int lagna = arr[12];
							int bhav = 1;
							if (j != 1) {
								if (arr[j - 2] < lagna) {
									bhav = 12 - lagna + arr[j - 2] + 1;
								} else {
									bhav = arr[j - 2] - lagna + 1;
								}
							}
							drawShape.drawText(xaxis[j] - 3, y - i * 20, String.valueOf(bhav), 14, krutiDevRegularFont);
						}
					}
				}

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	void printKPChart() throws IOException {
		drawShape.drawText(20, pageHeight - 50, "dsih dqaMyh", 16, krutiDevRegularFont);
		drawShape.drawText(20, pageHeight - 50, "dsih dqaMyh", 16, krutiDevRegularFont);
		drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 230f);
		float[] x = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38, 20f + 80, 20f + 124, 20f + 136,
				20f + 95, 20f + 136, 20f + 124 };
		float[] y = { pageHeight - 230f + 90, pageHeight - 230f + 132, pageHeight - 230f + 122, pageHeight - 230f + 80,
				pageHeight - 230f + 40, pageHeight - 230f + 30, pageHeight - 230f + 70, pageHeight - 230f + 30,
				pageHeight - 230f + 40, pageHeight - 230f + 80, pageHeight - 230f + 122, pageHeight - 230f + 132 };
		KpKundliCalculation kpKundliCalculation = new KpKundliCalculation(desktopHoro);
		int[] kpChartArray = kpKundliCalculation.getKPLagnaRashiKundliPlanetsRashiArray();
		int[] kpDegArray = kpKundliCalculation.getKpChartArray();
		int[] planetArray = new int[13];
		for (int i = 0; i < 12; i++) {
			planetArray[i] = (int) (kpDegArray[i] / 30.0) + 1;
		}
		drawRashiInBhav(x, y, kpChartArray[12]);
		printPlanetsInHouse(20f, pageHeight - 230f, kpDegArray, kpDegArray[12]);
	}

	void printKPRashiChart() throws IOException {
		drawShape.drawText(20, pageHeight - 250f, "jkf'k dqaMyh", 16, krutiDevRegularFont);
		drawShape.drawText(20, pageHeight - 250f, "jkf'k dqaMyh", 16, krutiDevRegularFont);
		drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 430f);

		float[] x1 = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38, 20f + 80, 20f + 124, 20f + 136,
				20f + 95, 20f + 136, 20f + 124 };
		float[] y1 = { pageHeight - 430f + 90, pageHeight - 430f + 132, pageHeight - 430f + 122, pageHeight - 430f + 80,
				pageHeight - 430f + 40, pageHeight - 430f + 30, pageHeight - 430f + 70, pageHeight - 430f + 30,
				pageHeight - 430f + 40, pageHeight - 430f + 80, pageHeight - 430f + 122, pageHeight - 430f + 132 };
		int[] planetArray = new KpKundliCalculation(desktopHoro).getKPLagnaRashiKundliPlanetsRashiArray();
		drawRashiInBhav(x1, y1, planetArray[12]);
		System.out.println(planetArray[12] + "--" + planetArray[0]);
		printPlanetsInHouse(20f, pageHeight - 430f, planetArray, planetArray[12]);
	}

	void printKPPlanetPositionTable() throws IOException {
		drawShape.drawText(210, pageHeight - 50, "xzg fLFkfr", 16, krutiDevRegularFont);
		drawShape.drawText(210, pageHeight - 50, "xzg fLFkfr", 16, krutiDevRegularFont);
		drawShape.drawTableHeader(210, pageHeight - 85, 25, 360);
		float x = 210;
		float y = pageHeight - 410f;
		float h = 350;
		float w = 360;
		drawShape.drawTableWithHeader(x, y, h, w, 25);
		drawShape.drawRaw(x, y, h, w, 13, 25);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 50, y, x + 50, y + 350));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 350));
		points.add(new AxisPoint(x + 210, y, x + 210, y + 350));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 350));
		points.add(new AxisPoint(x + 310, y, x + 310, y + 350));
		drawShape.drawColumn(points);

		float[] xaxis = { x + 10, x + 60, x + 170, x + 220, x + 270, x + 320 };
		printHeading(xaxis, pageHeight - 75, constantHindi.subPlanetPosHeading);
		ArrayList<BasicKundliPlanetSubData> list = new KpKundliCalculation(desktopHoro).getKPPlanetsData();
		populateKPPlanetTable(xaxis, y + 325, list);
	}

	void populateKPPlanetTable(float[] xaxis, float y, ArrayList<BasicKundliPlanetSubData> list) throws IOException {
		y = y + 8;
		BasicKundliPlanetSubData basicKundliPlanetSubData;
		for (int i = 0; i < list.size(); i++) {
			basicKundliPlanetSubData = list.get(i);
			drawShape.drawText(xaxis[0], y - (i + 1) * 25, basicKundliPlanetSubData.getPlaName(), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[1], y - (i + 1) * 25, basicKundliPlanetSubData.getPlaDeg(), 10,
					poppinsRegularFont);
			drawShape.drawText(xaxis[2], y - (i + 1) * 25, basicKundliPlanetSubData.getSignLord(), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[3], y - (i + 1) * 25, basicKundliPlanetSubData.getNakshLord(), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[4], y - (i + 1) * 25, basicKundliPlanetSubData.getSubLord(), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[5], y - (i + 1) * 25, basicKundliPlanetSubData.getSubSubLord(), 14,
					krutiDevRegularFont);
		}
	}

	void printBhavSandhiTable() throws IOException {
		drawShape.drawText(210, pageHeight - 430, "Hkko laf/k", 16, krutiDevRegularFont);
		drawShape.drawText(210, pageHeight - 430, "Hkko laf/k", 16, krutiDevRegularFont);
		drawShape.drawTableHeader(210, pageHeight - 465, 25, 360);
		float x = 210;
		float y = pageHeight - 765f;
		float h = 325;
		float w = 360;
		drawShape.drawTableWithHeader(x, y, h, w, 25);
		drawShape.drawRaw(x, y, h, w, 12, 25);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 50, y, x + 50, y + h));
		points.add(new AxisPoint(x + 160, y, x + 160, y + h));
		points.add(new AxisPoint(x + 210, y, x + 210, y + h));
		points.add(new AxisPoint(x + 260, y, x + 260, y + h));
		points.add(new AxisPoint(x + 310, y, x + 310, y + h));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 60, x + 170, x + 220, x + 270, x + 320 };
		printHeading(xaxis, pageHeight - 455, constantHindi.bhavSandhiHeading);
		ArrayList<BasicKundliPlanetSubData> list = new KpKundliCalculation(desktopHoro).getKPCuspData();
		populateKPSubPlanetTable(xaxis, y + 300, list);
	}

	void populateKPSubPlanetTable(float[] xaxis, float y, ArrayList<BasicKundliPlanetSubData> list) throws IOException {
		y = y + 8;
		BasicKundliPlanetSubData basicKundliPlanetSubData;
		for (int i = 0; i < list.size(); i++) {
			basicKundliPlanetSubData = list.get(i);
			drawShape.drawText(xaxis[0], y - (i + 1) * 25, basicKundliPlanetSubData.getPlaName(), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[1], y - (i + 1) * 25, basicKundliPlanetSubData.getPlaDeg(), 10,
					poppinsRegularFont);
			drawShape.drawText(xaxis[2], y - (i + 1) * 25, basicKundliPlanetSubData.getSignLord(), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[3], y - (i + 1) * 25, basicKundliPlanetSubData.getNakshLord(), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[4], y - (i + 1) * 25, basicKundliPlanetSubData.getSubLord(), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[5], y - (i + 1) * 25, basicKundliPlanetSubData.getSubSubLord(), 14,
					krutiDevRegularFont);
		}
	}

	void printPlanetSignificatorTable() {
		try {
			float x = 10;
			float y = pageHeight - 310f;
			float h = 250;
			float w = 230;
			drawShape.drawText(x, pageHeight - 50, "xzg funsZ'ku", 16, krutiDevRegularFont);
			drawShape.drawText(x, pageHeight - 50, "xzg funsZ'ku", 16, krutiDevRegularFont);
			drawShape.drawTableHeader(x, pageHeight - 85, 25, w);
			drawShape.drawTableWithHeader(x, y, h, w, 25);
			drawShape.drawRaw(x, y, h, w, 8, 25);

			ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
			points.add(new AxisPoint(x + 30, y, x + 30, y + 250));
			drawShape.drawColumn(points);
			float[] xaxis = { x + 5, x + 40 };
			printHeading(xaxis, pageHeight - 75, constantHindi.planetSignificatorHeading);
			ArrayList<PlanetSignificationBean> list = new KpKundliCalculation(desktopHoro)
					.getKPPlanetSignificationData();
			populatePlanetSignificatorTable(xaxis, y + 230, list);
		} catch (Exception e) {

		}
	}

	void populatePlanetSignificatorTable(float[] xaxis, float y, ArrayList<PlanetSignificationBean> list)
			throws IOException {
		y = y + 5;
		PlanetSignificationBean planetSignificationBean;
		for (int i = 0; i < list.size(); i++) {
			planetSignificationBean = list.get(i);
			drawShape.drawText(xaxis[0], y - (i + 1) * 25, planetSignificationBean.getPlanetName(), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[1], y - (i + 1) * 25, planetSignificationBean.getHouseSignified(), 10,
					poppinsRegularFont);
		}
	}

	void printHouseSignificatorTable() {
		try {
			float x = 10;
			float y = pageHeight - 665;
			float h = 325;
			float w = 230;
			drawShape.drawText(x, pageHeight - 330, "Hkko funsZ'ku", 16, krutiDevRegularFont);
			drawShape.drawText(x, pageHeight - 330, "Hkko funsZ'ku", 16, krutiDevRegularFont);
			drawShape.drawTableHeader(x, pageHeight - 365, 25, w);
			drawShape.drawTableWithHeader(x, y, h, w, 25);
			drawShape.drawRaw(x, y, h, w, 11, 25);
			ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
			points.add(new AxisPoint(x + 30, y, x + 30, y + 325));
			drawShape.drawColumn(points);
			float[] xaxis = { x + 10, x + 40 };
			float[] xaxis1 = { x + 5, x + 40 };
			printHeading(xaxis1, pageHeight - 355, constantHindi.bhavSignificatorHeading);
			ArrayList<HouseSignificatorsBean> list = new KpKundliCalculation(desktopHoro).getKPHouseSignificatorsData();
			populateHouseSignificatorTable(xaxis, y + 305, list);

		} catch (Exception e) {

		}
	}

	void populateHouseSignificatorTable(float[] xaxis, float y, ArrayList<HouseSignificatorsBean> list)
			throws IOException {
		y = y + 5;
		HouseSignificatorsBean houseSignificatorsBean;
		for (int i = 0; i < list.size(); i++) {
			houseSignificatorsBean = list.get(i);
			drawShape.drawText(xaxis[0], y - (i + 1) * 25, String.valueOf(houseSignificatorsBean.getHouseNo()), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[1], y - (i + 1) * 25, houseSignificatorsBean.getSignificator(), 14,
					krutiDevRegularFont);
		}
	}

	void printPlanetSignificatorView2Table() {
		try {
			float x = 255;
			float y = pageHeight - 310f;
			float h = 250;
			float w = 330;
			drawShape.drawText(x, pageHeight - 50, "xzg funsZ'ku¼ [kkdk 2½", 16, krutiDevRegularFont);
			drawShape.drawText(x, pageHeight - 50, "xzg funsZ'ku¼ [kkdk 2½", 16, krutiDevRegularFont);
			drawShape.drawTableHeader(x, pageHeight - 85, 25, w);
			drawShape.drawTableWithHeader(x, y, h, w, 25);
			drawShape.drawRaw(x, y, h, w, 8, 25);
			ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
			points.add(new AxisPoint(x + 30, y, x + 30, y + 250));
			points.add(new AxisPoint(x + 105, y, x + 105, y + 250));
			points.add(new AxisPoint(x + 180, y, x + 180, y + 250));
			points.add(new AxisPoint(x + 255, y, x + 255, y + 250));
			drawShape.drawColumn(points);
			float[] xaxis = { x + 5, x + 40, x + 110, x + 185, x + 260 };
			printHeading(xaxis, pageHeight - 75, constantHindi.planetSignificator2Heading);
			ArrayList<PlanetSignificationView2Bean> list = new KPPlanetSignificationView2Calculation(desktopHoro)
					.getPlanetSignifiactionView2Data();
			populatePlanetSignifiactionView2Table(xaxis, y + 230, list);
		} catch (Exception e) {

		}
	}

	void populatePlanetSignifiactionView2Table(float[] xaxis, float y, ArrayList<PlanetSignificationView2Bean> list)
			throws IOException {
		y = y + 5;
		PlanetSignificationView2Bean planetSignificationView2Bean;
		for (int i = 0; i < list.size(); i++) {
			planetSignificationView2Bean = list.get(i);
			drawShape.drawText(xaxis[0], y - (i + 1) * 25, planetSignificationView2Bean.getPlaNo(), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[1], y - (i + 1) * 25, planetSignificationView2Bean.getL1(), 10,
					poppinsRegularFont);
			drawShape.drawText(xaxis[2], y - (i + 1) * 25, planetSignificationView2Bean.getL2(), 10,
					poppinsRegularFont);
			drawShape.drawText(xaxis[3], y - (i + 1) * 25, planetSignificationView2Bean.getL3(), 10,
					poppinsRegularFont);
			drawShape.drawText(xaxis[4], y - (i + 1) * 25, planetSignificationView2Bean.getL4(), 10,
					poppinsRegularFont);
		}
	}

	void printRularTable() {
		try {
			float x = 255;
			float y = pageHeight - 540;
			float h = 200;
			float w = 330;
			drawShape.drawText(x, pageHeight - 330, "'kkld xzg", 16, krutiDevRegularFont);
			drawShape.drawText(x, pageHeight - 330, "'kkld xzg", 16, krutiDevRegularFont);
			drawShape.drawTableHeader(x, pageHeight - 365, 25, w);
			drawShape.drawTableWithHeader(x, y, h, w, 25);
			drawShape.drawRaw(x, y, h, w, 6, 25);
			ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
			points.add(new AxisPoint(x + 165, y, x + 165, y + 200));
			drawShape.drawColumn(points);
			float[] xaxis = { x + 10, x + 170 };
			ArrayList<KpRulingPlanetBean> list = new KpRulingPlanetCalculation(desktopHoro).getKPRulingPlanetData();
			populatRulingPlanetTable(xaxis, y + 180, list);
		} catch (Exception e) {

		}
	}

	void populatRulingPlanetTable(float[] xaxis, float y, ArrayList<KpRulingPlanetBean> list) throws IOException {
		y = y + 3;
		KpRulingPlanetBean kpRulingPlanetBean;
		for (int i = 0; i < list.size(); i++) {
			kpRulingPlanetBean = list.get(i);
			drawShape.drawText(xaxis[0], y - (i + 1) * 25, kpRulingPlanetBean.getLabel(), 14, krutiDevRegularFont);
			drawShape.drawText(xaxis[1], y - (i + 1) * 25, kpRulingPlanetBean.getValue(), 14, krutiDevRegularFont);
		}
	}

	void printMiscTable() {
		try {
			float x = 255;
			float y = pageHeight - 695;
			float h = 125;
			float w = 330;
			drawShape.drawText(x, pageHeight - 560, "vU;", 16, krutiDevRegularFont);
			drawShape.drawText(x, pageHeight - 560, "vU;", 16, krutiDevRegularFont);
			drawShape.drawTableHeader(x, pageHeight - 595, 25, w);
			drawShape.drawTableWithHeader(x, y, h, w, 25);
			drawShape.drawRaw(x, y, h, w, 3, 25);
			ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
			points.add(new AxisPoint(x + 165, y, x + 165, y + 125));
			drawShape.drawColumn(points);
			float[] xaxis = { x + 10, x + 170 };
			ArrayList<KpRulingPlanetBean> list = new KpRulingPlanetCalculation(desktopHoro).getKPMiscData();
			populateMiscTable(xaxis, y + 105, list);

		} catch (Exception e) {

		}
	}

	void populateMiscTable(float[] xaxis, float y, ArrayList<KpRulingPlanetBean> list) throws IOException {
		try {
			y = y + 3;
			KpRulingPlanetBean kpRulingPlanetBean;
			for (int i = 0; i < list.size(); i++) {
				kpRulingPlanetBean = list.get(i);
				drawShape.drawText(xaxis[0], y - (i + 1) * 25, kpRulingPlanetBean.getLabel(), 14, krutiDevRegularFont);
				if (i % 2 == 0) {
					drawShape.drawText(xaxis[1], y - (i + 1) * 25, kpRulingPlanetBean.getValue(), 14,
							krutiDevRegularFont);
				} else {
					drawShape.drawText(xaxis[1], y - (i + 1) * 25, kpRulingPlanetBean.getValue(), 10,
							poppinsRegularFont);
				}

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	void printNakshNadi() {
		try {
			float x = 10;
			float y = pageHeight - 260f;
			float h = 200;
			float w = pageWidth - 20;
			drawShape.drawText(x, pageHeight - 50, "xzg funsZ'ku ¼u{k= ukM+h½", 16, krutiDevRegularFont);
			drawShape.drawText(x, pageHeight - 50, "xzg funsZ'ku ¼u{k= ukM+h½", 16, krutiDevRegularFont);
			drawShape.drawTableHeader(x, pageHeight - 80, 20, w);
			drawShape.drawTableWithHeader(x, y, h, w, 20);
			drawShape.drawRaw(x, y, h, w, 8, 20);
			ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
			points.add(new AxisPoint(x + w / 3, y, x + w / 3, y + 200));
			points.add(new AxisPoint(x + 2 * w / 3, y, x + 2 * w / 3, y + 200));
			drawShape.drawColumn(points);
			float[] xaxis = { x + 10, x + 10 + w / 3, x + 10 + 2 * w / 3 };
			printHeading(xaxis, pageHeight - 75, constantHindi.nakshNadiHeading);
			ArrayList<KPNakshatraNadiBean> list = new KpNakshtraNadiCalculation(desktopHoro).getNakshtraNadiData();
			populateNakshNadiTable(xaxis, y + 180, list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	void populateNakshNadiTable(float[] xaxis, float y, ArrayList<KPNakshatraNadiBean> list) throws IOException {
		y = y + 5;
		KPNakshatraNadiBean kpNakshatraNadiBean;
		for (int i = 0; i < list.size(); i++) {
			kpNakshatraNadiBean = list.get(i);
			drawShape.drawText(xaxis[0], y - (i + 1) * 20, String.valueOf(kpNakshatraNadiBean.getPlanet()), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[1], y - (i + 1) * 20, kpNakshatraNadiBean.getStarLord(), 14, krutiDevRegularFont);
			drawShape.drawText(xaxis[2], y - (i + 1) * 20, kpNakshatraNadiBean.getSubLord(), 14, krutiDevRegularFont);
		}
	}

	void printCILSubTable() {
		try {
			float x = 10;
			float y = pageHeight - 550f;
			float h = 260;
			float w = pageWidth - 20;
			drawShape.drawText(x, pageHeight - 280, "dLiy baVjfyaDl ¼lc½", 16, krutiDevRegularFont);
			drawShape.drawText(x, pageHeight - 280, "dLiy baVjfyaDl ¼lc½", 16, krutiDevRegularFont);
			drawShape.drawTableHeader(x, pageHeight - 310, 20, w);
			drawShape.drawTableWithHeader(x, y, h, w, 20);
			drawShape.drawRaw(x, y, h, w, 11, 20);
			w = w - 30;
			ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
			points.add(new AxisPoint(x + 30, y, x + 30, y + 260));
			points.add(new AxisPoint(x + 30 + w / 4, y, x + 30 + w / 4, y + 260));
			points.add(new AxisPoint(x + 30 + 2 * w / 4, y, x + 30 + 2 * w / 4, y + 260));
			points.add(new AxisPoint(x + 30 + 3 * w / 4, y, x + 30 + 3 * w / 4, y + 260));
			drawShape.drawColumn(points);
			float[] xaxis = { x + 10, x + 40, x + 30 + w / 4 + 10, x + 30 + 2 * w / 4 + 10, x + 30 + 3 * w / 4 + 10 };
			printHeading(xaxis, pageHeight - 305, constantHindi.cusperInterLinksHeading);
			ArrayList<KPCilSubBean> list = new KpCilSubCalculation(desktopHoro).getKPCilSubData();
			populateCILSubTable(xaxis, y + 240, list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	void populateCILSubTable(float[] xaxis, float y, ArrayList<KPCilSubBean> list) throws IOException {
		y = y + 5;
		KPCilSubBean kpCilSubBean;
		for (int i = 0; i < list.size(); i++) {
			kpCilSubBean = list.get(i);
			drawShape.drawText(xaxis[0], y - (i + 1) * 20, String.valueOf(kpCilSubBean.getHouseNo()), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[1], y - (i + 1) * 20, kpCilSubBean.getT1(), 10, poppinsRegularFont);
			drawShape.drawText(xaxis[2], y - (i + 1) * 20, kpCilSubBean.getT2(), 10, poppinsRegularFont);
			drawShape.drawText(xaxis[3], y - (i + 1) * 20, kpCilSubBean.getT3(), 10, poppinsRegularFont);
			drawShape.drawText(xaxis[4], y - (i + 1) * 20, kpCilSubBean.getT4(), 10, poppinsRegularFont);
		}
	}

	void printCILSubSubTable() {
		try {
			float x = 10;
			float y = pageHeight - 780f;
			float h = 200;
			float w = pageWidth - 20;
			drawShape.drawText(x, pageHeight - 570, "dLiy baVjfyaDl ¼lc lc½", 16, krutiDevRegularFont);
			drawShape.drawText(x, pageHeight - 570, "dLiy baVjfyaDl ¼lc lc½", 16, krutiDevRegularFont);
			drawShape.drawTableHeader(x, pageHeight - 600, 20, w);
			drawShape.drawTableWithHeader(x, y, h, w, 20);
			drawShape.drawRaw(x, y, h, w, 8, 20);
			w = w - 30;
			ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
			points.add(new AxisPoint(x + 30, y, x + 30, y + 200));
			points.add(new AxisPoint(x + 30 + w / 4, y, x + 30 + w / 4, y + 200));
			points.add(new AxisPoint(x + 30 + 2 * w / 4, y, x + 30 + 2 * w / 4, y + 200));
			points.add(new AxisPoint(x + 30 + 3 * w / 4, y, x + 30 + 3 * w / 4, y + 200));
			drawShape.drawColumn(points);
			float[] xaxis = { x + 5, x + 40, x + 30 + w / 4 + 10, x + 30 + 2 * w / 4 + 10, x + 30 + 3 * w / 4 + 10 };
			printHeading(xaxis, pageHeight - 595, constantHindi.cusperInterLinksSubHeading);
			ArrayList<KPCilSubSubBean> list = new KpCilSubSubCalculation(desktopHoro).getKPCilSubSubData();
			populateCILSubSubTable(xaxis, y + 180, list);
		} catch (Exception e) {

		}
	}

	void populateCILSubSubTable(float[] xaxis, float y, ArrayList<KPCilSubSubBean> list) throws IOException {
		y = y + 5;
		KPCilSubSubBean kpCilSubSubBean;
		for (int i = 0; i < list.size(); i++) {
			kpCilSubSubBean = list.get(i);
			drawShape.drawText(xaxis[0], y - (i + 1) * 20, String.valueOf(kpCilSubSubBean.getPlanetName()), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[1], y - (i + 1) * 20, kpCilSubSubBean.getStar(), 10, poppinsRegularFont);
			drawShape.drawText(xaxis[2], y - (i + 1) * 20, kpCilSubSubBean.getSub(), 10, poppinsRegularFont);
			drawShape.drawText(xaxis[3], y - (i + 1) * 20, kpCilSubSubBean.getSubSub(), 10, poppinsRegularFont);
			drawShape.drawText(xaxis[4], y - (i + 1) * 20, kpCilSubSubBean.getPosStatus(), 10, poppinsRegularFont);
		}
	}

	public float addParagraph(PDDocument document, float startY, String text) throws Exception {
		try {

			float margin = 20; // Left and right margin
			float width = pageWidth - 2 * margin; // Text width
			// float startY = page.getMediaBox().getHeight() - margin; // Start from top
			float leading = 15; // Line spacing
			List<String> lines = wrapText(text, width, krutiDevRegularFont, 12);
			float yPosition = startY;
			contentStream.beginText();
			contentStream.newLineAtOffset(margin, yPosition);

			for (String line : lines) {
				if (yPosition <= margin) {
					contentStream.endText();
					contentStream.close();

					// Add a new page
					PDPage page = new PDPage(PDRectangle.A4);
					document.addPage(page);
					contentStream = new PDPageContentStream(document, page);
					drawShape.initialize(pageHeight, pageWidth, document, contentStream);
					addPageHeading(pageHeight, pageWidth);
					addWatermarkUnderContent(document, page);
					contentStream.setFont(krutiDevRegularFont, 12);
					yPosition = pageHeight - 50;
					contentStream.beginText();
					contentStream.newLineAtOffset(margin, yPosition);
				}

				contentStream.showText(line);
				contentStream.newLineAtOffset(0, -leading);
				yPosition -= leading;
			}

			contentStream.endText();

			return yPosition;
			// document.save("ParagraphPDF.pdf");
			// System.out.println("PDF Created Successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return startY;
	}

	private static List<String> wrapText(String text, float width, PDType0Font font, int fontSize) throws IOException {
		List<String> lines = new ArrayList<>();
		String[] words = text.split(" ");
		StringBuilder line = new StringBuilder();

		for (String word : words) {
			if (font.getStringWidth(line + word) / 1000 * fontSize > width) {
				lines.add(line.toString());
				line = new StringBuilder(word).append(" ");
			} else {
				line.append(word).append(" ");
			}
		}
		lines.add(line.toString().trim());
		return lines;
	}

	void printVrashfalChart() throws IOException {
		float startX = 212f;
		float startY = pageHeight - 230f;
		drawShape.drawText(startX, pageHeight - 50, "rkftd o\"kZQy dqaMyh", 16, krutiDevRegularFont);
		drawShape.drawText(startX, pageHeight - 50, "rkftd o\"kZQy dqaMyh", 16, krutiDevRegularFont);
		drawShape.drawKundli(pageHeight, pageWidth, startX, startY);
		float[] x = { startX + 80, startX + 38, startX + 26, startX + 70, startX + 26, startX + 38, startX + 80,
				startX + 124, startX + 136, startX + 95, startX + 136, startX + 124 };
		float[] y = { startY + 90, startY + 132, startY + 122, startY + 80, startY + 40, startY + 30, startY + 70,
				startY + 30, startY + 40, startY + 80, startY + 122, startY + 132 };
		Calendar calendar = Calendar.getInstance();
		int[] planetArray = new VarshfalCalculation().getVarshfalKundliPlanetsRashiArray(birthDetailBean,
				calendar.get(Calendar.YEAR), 1);
		drawRashiInBhav(x, y, planetArray[12]);
		printPlanetsInHouse(startX, startY, planetArray, planetArray[12]);
	}

	void printVarshfalPlanetPositionTable() throws IOException {
		drawShape.drawText(20, pageHeight - 250f, "xzg fLFkfr", 16, krutiDevRegularFont);
		drawShape.drawText(20, pageHeight - 250f, "xzg fLFkfr", 16, krutiDevRegularFont);
		drawShape.drawTableHeader(20, pageHeight - 285f, 25, 220);
		float x = 20;
		float y = pageHeight - 610f;
		float h = 350;
		float w = 220;
		drawShape.drawTableWithHeader(x, y, h, w, 25);
		drawShape.drawRaw(x, y, h, w, 13, 25);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 50, y, x + 50, y + 350));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 350));
		// points.add(new AxisPoint(x + 220, y, x + 220, y + 350));
		// points.add(new AxisPoint(x + 300, y, x + 300, y + 350));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 60, x + 130, x + 230, x + 310 };
		printHeading(xaxis, pageHeight - 275f, constantHindi.varshfalPlanetPosHeading);
		Calendar calendar = Calendar.getInstance();
		ArrayList<VarshfalPlanetData> list = new VarshfalCalculation().getVarshfalPlanetsData(birthDetailBean,
				calendar.get(Calendar.YEAR), 1);
		populateVarshfalPlanetTable(xaxis, y + 325, list);
	}

	void populateVarshfalPlanetTable(float[] xaxis, float y, ArrayList<VarshfalPlanetData> list) throws IOException {
		y = y + 8;
		VarshfalPlanetData varshfalPlanetData;
		for (int i = 0; i < list.size(); i++) {
			varshfalPlanetData = list.get(i);
			drawShape.drawText(xaxis[0], y - (i + 1) * 25, varshfalPlanetData.getPlaName(), 14, krutiDevRegularFont);
			drawShape.drawText(xaxis[1], y - (i + 1) * 25, String.valueOf(varshfalPlanetData.getSign()), 14,
					krutiDevRegularFont);
			drawShape.drawText(xaxis[2], y - (i + 1) * 25, varshfalPlanetData.getDegree(), 10, poppinsRegularFont);

		}
	}

	void printVarshfalDetail(float pageHeight, float pageWidth) throws IOException {
		float x = pageWidth / 2 + 10;
		float y = pageHeight - 410f;
		float h = 150;
		float w = 267;

		drawShape.drawText(x, pageHeight - 250f, "tUe fooj.k", 16, krutiDevRegularFont);
		drawShape.drawText(x, pageHeight - 250f, "tUe fooj.k", 16, krutiDevRegularFont);
		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, 4, 30);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + w / 2 - 30, y, x + w / 2 - 30, y + h));
		drawShape.drawColumn(points);
		String[] labels = constantHindi.birthDetailLabel;
		String[] values = { desktopHoro.getName(), getBirthDate(), desktopHoro.getBirthTime(), desktopHoro.getPlace(),
				String.valueOf(desktopHoro.getAyan()) };
		printText(drawShape, x + 5, pageHeight - 280, labels, krutiDevRegularFont);
		printText(drawShape, x + (w / 2) - 25, pageHeight - 280, values, poppinsRegularFont);
	}

	void printVrashfalPridiction(PDDocument document) throws Exception {
		float x = 20;
		VarshfalCalculation varshfalCalculation = new VarshfalCalculation();
		Calendar calendar = Calendar.getInstance();
		int yearCount = calendar.get(Calendar.YEAR) - Integer.parseInt(birthDetailBean.getDateTimeBean().getYear());
		int munthBhav = varshfalCalculation.getMunthaBhav(birthDetailBean, calendar.get(Calendar.YEAR), yearCount);
		String munthaDesc = varshfalCalculation.getMunthaDesc(munthBhav - 1).getDesc();

		ArrayList<VarshfalDashaBean> arrayList = varshfalCalculation.getBhavishyafal(birthDetailBean,
				calendar.get(Calendar.YEAR), yearCount);
		float startY = pageHeight - 50;
		drawShape.drawText(20, startY, "eqaFkk Hkko% " + munthBhav, 16, krutiDevRegularFont);
		drawShape.drawText(20, startY, "eqaFkk Hkko% " + munthBhav, 16, krutiDevRegularFont);
		contentStream.setFont(krutiDevRegularFont, 12);
		float y = addParagraph(document, startY - 20, munthaDesc);
		for (int i = 0; i < arrayList.size(); i++) {
			drawShape.drawText(20, y - 20, arrayList.get(i).getStartTime() + " & " + arrayList.get(i).getEndTime(), 16,
					krutiDevRegularFont);
			drawShape.drawText(20, y - 20, arrayList.get(i).getStartTime() + " & " + arrayList.get(i).getEndTime(), 16,
					krutiDevRegularFont);
			contentStream.setFont(krutiDevRegularFont, 12);
			y = addParagraph(document, y - 40, arrayList.get(i).getResult());
		}
		y = y - 10;
		drawShape.drawText(20, y, constantHindi.jeevanRatan, 16, krutiDevRegularFont);
		drawShape.drawText(20, y, constantHindi.jeevanRatan, 16, krutiDevRegularFont);
		float h = 180;
		float w = 500;
		y = y - 10;
		drawShape.drawRoundedRectangle(x, y - h, h, w);
		drawShape.drawRowAndColoum(x, y - h, h, w, 5, 30);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + w / 4, y, x + w / 4, y - h));
		drawShape.drawColumn(points);
		String[] labels = constantHindi.gemstoneLabel;
		GemstoneCalculation gemstoneCalculation = new GemstoneCalculation(desktopHoro);
		GemstoneItem lifeStone = gemstoneCalculation.getLifeStone();
		GemstoneItem beneficStone = gemstoneCalculation.getBeneficStone();
		GemstoneItem luckyStone = gemstoneCalculation.getLuckyStone();
		String[] values1 = { lifeStone.getLifeStone(), lifeStone.getFinger(), lifeStone.getMetal(),
				lifeStone.getQualities(), lifeStone.getMantra(), lifeStone.getDevotee() };
		printText(drawShape, x + 5, y - 20, labels, krutiDevRegularFont);
		printText(drawShape, x + (w / 4) + 5, y - 20, values1, krutiDevRegularFont);

		y = y - h - 20;
		drawShape.drawText(20, y, constantHindi.punyaRatan, 16, krutiDevRegularFont);
		drawShape.drawText(20, y, constantHindi.punyaRatan, 16, krutiDevRegularFont);
		y = y - 10;
		drawShape.drawRoundedRectangle(x, y - h, h, w);
		drawShape.drawRowAndColoum(x, y - h, h, w, 5, 30);
		points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + w / 4, y, x + w / 4, y - h));
		drawShape.drawColumn(points);
		labels = constantHindi.gemstoneLabel;
		String[] values2 = { beneficStone.getLifeStone(), beneficStone.getFinger(), beneficStone.getMetal(),
				beneficStone.getQualities(), beneficStone.getMantra(), beneficStone.getDevotee() };
		printText(drawShape, x + 5, y - 20, labels, krutiDevRegularFont);
		printText(drawShape, x + (w / 4) + 5, y - 20, values2, krutiDevRegularFont);
		y = y - h - 20;
		drawShape.drawText(20, y, constantHindi.bhagyaRatan, 16, krutiDevRegularFont);
		drawShape.drawText(20, y, constantHindi.bhagyaRatan, 16, krutiDevRegularFont);
		y = y - 10;
		drawShape.drawRoundedRectangle(x, y - h, h, w);
		drawShape.drawRowAndColoum(x, y - h, h, w, 5, 30);
		points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + w / 4, y, x + w / 4, y - h));
		drawShape.drawColumn(points);
		labels = constantHindi.gemstoneLabel;
		String[] values3 = { luckyStone.getLifeStone(), luckyStone.getFinger(), luckyStone.getMetal(),
				luckyStone.getQualities(), luckyStone.getMantra(), luckyStone.getDevotee() };
		printText(drawShape, x + 5, y - 20, labels, krutiDevRegularFont);
		printText(drawShape, x + (w / 4) + 5, y - 20, values3, krutiDevRegularFont);

	}

	void printLagnaReport(PDDocument document) throws Exception {
		float x = 20;
		LagnaCalculation lagnaCalculation = new LagnaCalculation(desktopHoro);
		String lagna = lagnaCalculation.getLagna();
		PersonalityPrediction personalityPrediction = lagnaCalculation.getLagnaReport();
		float startY = pageHeight - 50;
		drawShape.drawText(20, startY, "vkidk yXu gS% " + lagna, 16, krutiDevRegularFont);
		drawShape.drawText(20, startY, "vkidk yXu gS% " + lagna, 16, krutiDevRegularFont);
		contentStream.setFont(krutiDevRegularFont, 12);
		float y = addParagraph(document, startY - 20, constantHindi.lagnaDesc);
		String[] heading = { "LokLFk; # yXu ds fy,".replace("#", lagna),
				"'kkjhfjd jax :i # yXu ds fy,".replace("#", lagna),
				"LokHkko o O;fäRo # yXu ds fy,".replace("#", lagna) };
		String[] arr = { personalityPrediction.getHealthPrediction(),
				personalityPrediction.getPhysicalAppearancePrediction(),
				personalityPrediction.getTemperamentAndPersonalityPrediction() };
		contentStream.setFont(krutiDevRegularFont, 12);

		for (int i = 0; i < heading.length; i++) {
			drawShape.drawText(20, y - 20, heading[i].replace("\u200D", ""), 16, krutiDevRegularFont);
			drawShape.drawText(20, y - 20, heading[i].replace("\u200D", ""), 16, krutiDevRegularFont);
			contentStream.setFont(krutiDevRegularFont, 12);
			y = addParagraph(document, y - 40, arr[i].replace("\u200D", ""));
		}

	}

	String getBalanceOfDasha(int[] arr) {
		return constantHindi.nakshLord[arr[0]] + " " + arr[1] + constantHindi.year + " " + arr[2] + constantHindi.month
				+ " " + arr[3] + constantHindi.day;
	}

	void printNakshtraReport(PDDocument document) throws Exception {
		float x = 20;
		NakshtraReportCalculation calculation = new NakshtraReportCalculation(desktopHoro);
		String nakshtra = calculation.getNakshtra();
		NakshtraReportBean prediction = calculation.getReport();
		float startY = pageHeight - 50;
		drawShape.drawText(20, startY, "vkidk u{k= gS% " + nakshtra, 16, krutiDevRegularFont);
		drawShape.drawText(20, startY, "vkidk u{k= gS% " + nakshtra, 16, krutiDevRegularFont);
		contentStream.setFont(krutiDevRegularFont, 12);
		float y = addParagraph(document, startY - 20, constantHindi.lagnaDesc);
		String[] heading = { "u{k= Qy", "f'k{kk vkSj vk;", "ikfjokfjd thou" };
		String[] arr = { prediction.getReports(), prediction.getEducation(), prediction.getFamily() };
		contentStream.setFont(krutiDevRegularFont, 12);

		for (int i = 0; i < arr.length; i++) {
			drawShape.drawText(20, y - 20, heading[i], 16, krutiDevRegularFont);
			drawShape.drawText(20, y - 20, heading[i], 16, krutiDevRegularFont);
			contentStream.setFont(krutiDevRegularFont, 12);
			y = addParagraph(document, y - 40, arr[i]);
		}
	}

	void printPlanetConsideration(PDDocument document) throws Exception {
		float x = 20;
		PlanetConsiderationCalculation calculation = new PlanetConsiderationCalculation(desktopHoro);
		ArrayList<PlanetConsiderationBean> list = calculation.getPlaConsideration();
		float startY = pageHeight - 50;
		drawShape.drawText(pageWidth / 2 - 40, startY, "xzg fopkj", 16, krutiDevRegularFont);
		drawShape.drawText(pageWidth / 2 - 40, startY, "xzg fopkj", 16, krutiDevRegularFont);
		contentStream.setFont(krutiDevRegularFont, 12);
		float y = startY;

		String[] heading = ConstantHindi.plaConsiderationHeading;
		// String[] arr = { prediction.getReports(), prediction.getEducation(),
		// prediction.getFamily() };
		contentStream.setFont(krutiDevRegularFont, 12);

		for (int i = 0; i < list.size(); i++) {
			drawShape.drawText(20, y - 20, heading[i], 16, krutiDevRegularFont);
			drawShape.drawText(20, y - 20, heading[i], 16, krutiDevRegularFont);
			contentStream.setFont(krutiDevRegularFont, 12);
			y = addParagraph(document, y - 40, list.get(i).getConsideration().replace("\n", " "));
		}

	}

	void printGeneralPrediction(PDDocument document) throws Exception {
		float x = 20;
		LifePredictionCalculation calculation = new LifePredictionCalculation(desktopHoro);
		ArrayList<LifePredictionModel> list = calculation.getLifePrediction();
		float startY = pageHeight - 50;
		drawShape.drawText(pageWidth / 2 - 40, startY, "lEiw.kZ thou Qykns'k", 16, krutiDevRegularFont);
		drawShape.drawText(pageWidth / 2 - 40, startY, "lEiw.kZ thou Qykns'k", 16, krutiDevRegularFont);
		contentStream.setFont(krutiDevRegularFont, 12);
		float y = startY;

		String[] heading = ConstantHindi.lifePredictionHeading;
		// String[] arr = { prediction.getReports(), prediction.getEducation(),
		// prediction.getFamily() };
		contentStream.setFont(krutiDevRegularFont, 12);

		for (int i = 0; i < list.size(); i++) {
			drawShape.drawText(20, y - 20, heading[i], 16, krutiDevRegularFont);
			drawShape.drawText(20, y - 20, heading[i], 16, krutiDevRegularFont);
			contentStream.setFont(krutiDevRegularFont, 12);
			y = addParagraph(document, y - 40, list.get(i).getDetail().replace("\n", " "));
		}

	}

	void printVimDasha(PDDocument document) throws Exception {
		float ty = pageHeight - 50;
		float x = 20;
		float y = pageHeight - 260;
		float hy = pageHeight - 80;
		float h = 200;
		float w = 178;
		for (int j = 0; j < 3; j++) {
			x = 20;
			for (int i = 0; i < 3; i++) {
				drawShape.drawTableHeader(x, hy, 20, w);
				drawShape.drawRoundedRectangle(x, y, h, w);
				drawShape.drawRowAndColoum(x, y, h, w, 9, 20);
				ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
				points.add(new AxisPoint(x + w / 2 - 20, y, x + w / 2 - 20, y + h - 20));
				drawShape.drawColumn(points);
				x = w + x + 10;
			}
			ty = y - 20;
			hy = y - 50;
			y = y - 230;
			
		}
		x = 25;
		ty = pageHeight - 75;
		float vx = 25;
		float vy = pageHeight - 85;
		calculation = new DashaCalculation(birthDetailBean, desktopHoro);
		ArrayList<DasaBean> list = calculation.getVimDasaFormmattedData();
		int selectedVimPlanet = 0;
		for (int i = 0; i < list.size(); i++) {
			ArrayList<DasaBean> innerlist = calculation.getAntaraDasaFormmattedData(i, selectedVimPlanet);

			String timeRange;
			if (i == 0) {
				DateTimeBean dateTimeBean = birthDetailBean.getDateTimeBean();
				timeRange = dateTimeBean.getDay() + "/" + dateTimeBean.getMonth() + "/" + dateTimeBean.getYear() + " - "
						+ list.get(i).getDasaTimeStr();
			} else {
				timeRange = list.get(i - 1).getDasaTimeStr() + " - " + list.get(i).getDasaTimeStr();
			}
			float textWidth = getTextWidth(list.get(i).getPlanetName(), krutiDevRegularFont, 16);
			drawShape.drawText(x, ty, list.get(i).getPlanetName() , 16, krutiDevRegularFont);
			drawShape.drawText(x + textWidth + 3, ty, "- "+timeRange, 10, poppinsRegularFont);
			vy = ty;
			for (int j = 0; j < innerlist.size(); j++) {
				vy = vy - 20;
				drawShape.drawText(x, vy, innerlist.get(j).getPlanetName(), 16, krutiDevRegularFont);
				drawShape.drawText(x + w / 2 - 15, vy, innerlist.get(j).getDasaTimeStr(), 10, poppinsRegularFont);

			}
			if (i == 2 || i == 5 || i == 8) {
				x = 25;
				ty = ty - 230;
			} else {
				x = w + x + 10;
			}
			selectedVimPlanet++;
		}

	}

	String dashaStartDate;

	private void initDasha() {
		DateTimeBean dateTimeBean = birthDetailBean.getDateTimeBean();
		dashaStartDate = dateTimeBean.getDay() + "/" + dateTimeBean.getMonth() + "/" + dateTimeBean.getYear();
		anterDasaList = new ArrayList<DasaBean>();
		pratyantraDasaList = new ArrayList<DasaBean>();
		ArrayList<DasaBean> list1;
		ArrayList<DasaBean> list2;
		for (int i = 0; i < 9; i++) {
			list1 = calculation.getAntaraDasaFormmattedData(i, i);
			anterDasaList.addAll(list1);
			for (int j = 0; j < 9; j++) {
				list2 = calculation.getPratyantraDasaFormmattedData(j, i);
				pratyantraDasaList.addAll(list2);
			}
		}
		for (int i = 0; i < pratyantraDasaList.size(); i++) {
			System.out.println(i + "--" + pratyantraDasaList.get(i).getPlanetName());
		}
	}

	int rCount = 0;
	int hCount = 0;

	void printPratyantraDasha1(PDDocument document) throws Exception {
		float ty = pageHeight - 50;
		float hy = pageHeight - 73;
		float x = 5;
		float y = pageHeight - 235;
		float h = 190;
		float w = 114;
		for (int j = 0; j < 4; j++) {
			x = 5;
			for (int i = 0; i < 5; i++) {
				drawShape.drawTableHeader(x, hy, 28, w);
				drawShape.drawRoundedRectangle(x, y, h, w);
				drawShape.drawRowAndColoum(x, y, h, w, 9, 18);
				ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
				points.add(new AxisPoint(x + w / 2 - 20, y, x + w / 2 - 20, y + h - 28));
				drawShape.drawColumn(points);
				x = w + x + 3;
			}
			hy = y - 33;
			y = y - 195;
		}

		x = 5;
		ty = pageHeight - 58;
		float vx = x;
		float vy = pageHeight - 85;

		for (int i = 0; i < 20; i++) {
			String timeRange = dashaStartDate + " - " + anterDasaList.get(hCount).getDasaTimeStr();
			float textWidth = getTextWidth(anterDasaList.get(hCount).getPlanetSubPlaName(), krutiDevRegularFont, 15);
			drawShape.drawText(x + (114 - textWidth) / 2, ty,
					anterDasaList.get(hCount).getPlanetSubPlaName() + "  " + "" /* timeRange */, 15, krutiDevRegularFont);
			textWidth = getTextWidth(timeRange, poppinsRegularFont, 8);
			drawShape.drawText(x + (114 - textWidth) / 2, ty - 10, timeRange + "  " + "" /* timeRange */, 8,
					poppinsRegularFont);
			vy = ty - 28;
			for (int j = 0; j < 9; j++) {
				drawShape.drawText(vx + 5, vy, pratyantraDasaList.get(rCount).getPlanetName(), 12, krutiDevRegularFont);
				drawShape.drawText(vx + w / 2 - 15, vy, pratyantraDasaList.get(rCount).getDasaTimeStr(), 10,
						poppinsRegularFont);
				dashaStartDate = pratyantraDasaList.get(rCount).getDasaTimeStr();
				vy = vy - 18;
				rCount++;
			}
			if (i == 4 || i == 9 || i == 14 || i == 19) {
				x = 5;
				vx = 5;
				ty = ty - 195;
			} else {
				x = w + x + 3;
				vx = w + vx + 3;
			}
			hCount++;
		}

	}

	/*
	 * void printPratyantraDasha2(PDDocument document) throws Exception { float ty =
	 * pageHeight - 50; float hy = pageHeight - 73; float x = 5; float y =
	 * pageHeight - 235; float h = 190; float w = 114; for (int j = 0; j < 4; j++) {
	 * x = 5; for (int i = 0; i < 5; i++) { drawShape.drawTableHeader(x, hy, 28, w);
	 * drawShape.drawRoundedRectangle(x, y, h, w); drawShape.drawRowAndColoum(x, y,
	 * h, w, 9, 18); ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
	 * points.add(new AxisPoint(x + w / 2 - 20, y, x + w / 2 - 20, y + h - 28));
	 * drawShape.drawColumn(points); x = w + x + 3; } hy = y - 33; y = y - 195; }
	 * 
	 * x = 5; ty = pageHeight - 58; float vx = x; float vy = pageHeight - 85; for
	 * (int i = 0; i < 20; i++) { String timeRange = dashaStartDate + " - " +
	 * anterDasaList.get(hCount).getDasaTimeStr(); float textWidth =
	 * getTextWidth(anterDasaList.get(i).getPlanetName(), poppinsRegularFont, 12);
	 * drawShape.drawText(x + (114 - textWidth) / 2, ty,
	 * anterDasaList.get(hCount).getPlanetName() + "  " + "" timeRange , 12,
	 * poppinsRegularFont); textWidth = getTextWidth(timeRange, poppinsRegularFont,
	 * 8); drawShape.drawText(x + (114 - textWidth) / 2, ty - 10, timeRange + "  " +
	 * "" timeRange , 8, poppinsRegularFont); vy = ty - 28; for (int j = 0; j < 9;
	 * j++) { drawShape.drawText(vx + 5, vy,
	 * pratyantraDasaList.get(rCount).getPlanetName(), 12, poppinsRegularFont);
	 * drawShape.drawText(vx + w / 2 - 15, vy,
	 * pratyantraDasaList.get(rCount).getDasaTimeStr(), 10, poppinsRegularFont);
	 * dashaStartDate = pratyantraDasaList.get(rCount).getDasaTimeStr(); vy = vy -
	 * 18; rCount++; } if (i == 4 || i == 9 || i == 14 || i == 19) { x = 5; vx = 5;
	 * ty = ty - 195; } else { x = w + x + 3; vx = w + vx + 3; } hCount++; }
	 * 
	 * }
	 * 
	 * void printPratyantraDasha3(PDDocument document) throws Exception { float ty =
	 * pageHeight - 50; float hy = pageHeight - 73; float x = 5; float y =
	 * pageHeight - 235; float h = 190; float w = 114; for (int j = 0; j < 4; j++) {
	 * x = 5; for (int i = 0; i < 5; i++) { drawShape.drawTableHeader(x, hy, 28, w);
	 * drawShape.drawRoundedRectangle(x, y, h, w); drawShape.drawRowAndColoum(x, y,
	 * h, w, 9, 18); ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
	 * points.add(new AxisPoint(x + w / 2 - 20, y, x + w / 2 - 20, y + h - 28));
	 * drawShape.drawColumn(points); x = w + x + 3; } hy = y - 33; y = y - 195; }
	 * 
	 * x = 5; ty = pageHeight - 58; float vx = x; float vy = pageHeight - 85; for
	 * (int i = 0; i < 20; i++) { String timeRange = dashaStartDate + " - " +
	 * anterDasaList.get(hCount).getDasaTimeStr(); float textWidth =
	 * getTextWidth(anterDasaList.get(i).getPlanetName(), poppinsRegularFont, 12);
	 * drawShape.drawText(x + (114 - textWidth) / 2, ty,
	 * anterDasaList.get(hCount).getPlanetName() + "  " + "" timeRange , 12,
	 * poppinsRegularFont); textWidth = getTextWidth(timeRange, poppinsRegularFont,
	 * 8); drawShape.drawText(x + (114 - textWidth) / 2, ty - 10, timeRange + "  " +
	 * "" timeRange , 8, poppinsRegularFont); vy = ty - 28; for (int j = 0; j < 9;
	 * j++) { drawShape.drawText(vx + 5, vy,
	 * pratyantraDasaList.get(rCount).getPlanetName(), 12, poppinsRegularFont);
	 * drawShape.drawText(vx + w / 2 - 15, vy,
	 * pratyantraDasaList.get(rCount).getDasaTimeStr(), 10, poppinsRegularFont);
	 * dashaStartDate = pratyantraDasaList.get(rCount).getDasaTimeStr(); vy = vy -
	 * 18; rCount++; } if (i == 4 || i == 9 || i == 14 || i == 19) { x = 5; vx = 5;
	 * ty = ty - 195; } else { x = w + x + 3; vx = w + vx + 3; } hCount++; }
	 * 
	 * }
	 */

	public float getTextWidth(String text, PDType0Font font, float fontSize) throws IOException {

		float width = font.getStringWidth(text) / 1000 * fontSize;

		return width;
	}

	public static int getTextWidth(String text, Font font) {
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setFont(font);

		FontMetrics metrics = g.getFontMetrics();
		int width = metrics.stringWidth(text);

		g.dispose();
		return width;
	}
}
