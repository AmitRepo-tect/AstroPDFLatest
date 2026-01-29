package com.sunastrix.astropdf.calculation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astropdf.model.NakshtraReportBean;
import com.sunastrix.astropdf.model.PersonalityPrediction;
import com.sunastrix.astropdf.util.ConstantHindi;

public class NakshtraReportCalculation {
	DesktopHoroNew desktopHoro;

	public NakshtraReportCalculation(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	public NakshtraReportBean getReport() {
		int nakshtra = desktopHoro.getNakshatra();
		ArrayList<NakshtraReportBean> list = getList();
		return list.get(nakshtra);
	}

	public String getNakshtra() {
		ConstantHindi constantHindi = new ConstantHindi();
		int nakshtra = desktopHoro.getNakshatra();
		return constantHindi.nakshName[nakshtra];
	}

	ArrayList<NakshtraReportBean> getList() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			InputStream inputStream = getClass().getResourceAsStream("/json/nakshtra_report_hi.json");
			List<NakshtraReportBean> list = mapper.readValue(inputStream,
					new TypeReference<List<NakshtraReportBean>>() {
					});

			ArrayList<NakshtraReportBean> arrayList = new ArrayList<>(list);
			return arrayList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to read muhurat data", e);
		}
	}

}

