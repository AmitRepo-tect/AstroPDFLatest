package com.sunastrix.astropdf.calculation;

import java.io.File;
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
		int lagna = desktopHoro.getPositionForShodasvarg(0)[0];
		ArrayList<PersonalityPrediction> list = getList();
		return list.get(lagna);
	}

	public String getLagna() {
		ConstantHindi constantHindi = new ConstantHindi();
		int lagna = desktopHoro.getPositionForShodasvarg(0)[0];
		return constantHindi.rashiName[lagna];
	}

	ArrayList<PersonalityPrediction> getList() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<PersonalityPrediction> list = mapper.readValue(
					new File("src/main/resources/json/lagna_prediction" + ".json"),
					new TypeReference<List<PersonalityPrediction>>() {
					});

			ArrayList<PersonalityPrediction> arrayList = new ArrayList<>(list);
			return arrayList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to read muhurat data", e);
		}
	}
	/*
	 * ArrayList<PersonalityPrediction> getList() { try { ObjectMapper mapper = new
	 * ObjectMapper();
	 * mapper.configure(com.fasterxml.jackson.databind.MapperFeature.
	 * ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
	 * 
	 * List<PersonalityPrediction> list = mapper.readValue( new
	 * File("src/main/resources/json/gemstone_report.json"), new
	 * TypeReference<List<PersonalityPrediction>>() { });
	 * 
	 * return new ArrayList<>(list);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); throw new
	 * RuntimeException("Failed to read muhurat data", e); } }
	 */
}
