package com.sunastrix.astropdf.calculation;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astropdf.model.PersonalityPrediction;
import com.sunastrix.astropdf.util.ConstantHindi;

public class LagnaCalculation {
	DesktopHoroNew desktopHoro;

	public LagnaCalculation(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	public PersonalityPrediction getLagnaReport() {
		int lagna = (int) (desktopHoro.getAsc() / 30.00);
		ArrayList<PersonalityPrediction> list = getList();
		return list.get(lagna);
	}

	public String getLagna() {
		ConstantHindi constantHindi = new ConstantHindi();
		int lagna = (int) (desktopHoro.getAsc() / 30.00);
		return constantHindi.rashiName[lagna];
	}

	ArrayList<PersonalityPrediction> getList() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			InputStream inputStream = getClass().getResourceAsStream("/json/lagna_prediction.json");
			List<PersonalityPrediction> list = mapper.readValue(inputStream,
					new TypeReference<List<PersonalityPrediction>>() {
					});

			ArrayList<PersonalityPrediction> arrayList = new ArrayList<>(list);
			return arrayList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to read muhurat data", e);
		}
	}

}
