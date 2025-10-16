package com.sunastrix.astropdf.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sunastrix.astroganitlib.model.BirthDetailBean;
import com.sunastrix.astroganitlib.model.DateTimeBean;
import com.sunastrix.astroganitlib.model.PlaceDetail;
import com.sunastrix.astropdf.service.PDFGenerateService;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PDFController {
	@Autowired(required = true)
	PDFGenerateService pdfGenerateService;

	@GetMapping("/generatepdf")
	public void generatePdf(HttpServletResponse response, @RequestParam String name, @RequestParam String sex,
			@RequestParam String day, @RequestParam String month, @RequestParam String year, @RequestParam String hrs,
			@RequestParam String min, @RequestParam String sec, @RequestParam String place, @RequestParam String latDeg,
			@RequestParam String latMin, @RequestParam String latNS, @RequestParam String longDeg,
			@RequestParam String longMin, @RequestParam String longEW, @RequestParam String state,
			@RequestParam String country, @RequestParam String timezone, @RequestParam String timezoneStr,
			@RequestParam String dst, @RequestParam String ayanamsa, @RequestParam String charting,
			@RequestParam String kphn, @RequestParam String button1, @RequestParam String languageCode

	) throws IOException {
		PlaceDetail placeDetail = new PlaceDetail(0, place, latDeg, latMin, latNS, longDeg, longMin, longEW, state,
				country, timezone, timezoneStr);
		DateTimeBean dateTimeBean = new DateTimeBean(day, month, year, hrs, min, sec);
		BirthDetailBean birthDetailBean = new BirthDetailBean(-1, name, sex, dateTimeBean, placeDetail, dst, ayanamsa,
				"", kphn, "", languageCode, -1L, -1L, -1L);
	

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"drawing.pdf\"");
		try {
			byte[] baos = pdfGenerateService.generatePDF(birthDetailBean);
			response.setContentLength(baos.length);
			response.getOutputStream().write(baos);
			response.getOutputStream().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
