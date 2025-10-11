package com.sunastrix.astropdf.service;

import java.io.IOException;

import org.springframework.stereotype.Service;


import com.sunastrix.astroganitlib.model.BirthDetailBean;
@Service
public interface PDFGenerateService {
	byte[] generatePDF(BirthDetailBean birthDetailBean) throws IOException;
}
