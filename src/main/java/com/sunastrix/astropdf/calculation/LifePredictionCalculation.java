package com.sunastrix.astropdf.calculation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astropdf.model.LifePredictionModel;

public class LifePredictionCalculation {
	DesktopHoroNew desktopHoro;

	public LifePredictionCalculation(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	private double[] getPlanetDegreeArray() {
		double[] planetDegreeArray = { desktopHoro.getAsc(), desktopHoro.getSun(), desktopHoro.getMoon(),
				desktopHoro.getMars(), desktopHoro.getMercury(), desktopHoro.getJupitor(), desktopHoro.getVenus(),
				desktopHoro.getSaturn(), desktopHoro.getRahu(), desktopHoro.getKetu(), desktopHoro.getUranus(),
				desktopHoro.getNeptune(), desktopHoro.getPluto() };
		return planetDegreeArray;
	}

	private int getRasiNumber(double deg) {
		return (int) (deg / 30);
	}

	ArrayList<LifePredictionModel> getList(String url) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			InputStream inputStream = getClass().getResourceAsStream(url);
			List<LifePredictionModel> list = mapper.readValue(inputStream,
					new TypeReference<List<LifePredictionModel>>() {
					});

			ArrayList<LifePredictionModel> arrayList = new ArrayList<>(list);
			return arrayList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to read muhurat data", e);
		}
	}

	public ArrayList<LifePredictionModel> getLifePrediction() {
		double[] degArray = getPlanetDegreeArray();
		ArrayList<LifePredictionModel> list = new ArrayList<LifePredictionModel>();

		int laganRashi = getRasiNumber(degArray[0]);

		int bhav = getBhavOfPlant(laganRashi, getRasiNumber(degArray[1])) - 1;
		System.out.println(bhav);
		list.add(getList("/json/general_prediction_charater.json").get(bhav));
		bhav = getBhavOfPlant(laganRashi, getRasiNumber(degArray[4])) - 1;
		list.add(getList("/json/general_prediction_happiness_and_fulfillment.json").get(bhav));
		bhav = getBhavOfPlant(laganRashi, getRasiNumber(degArray[2])) - 1;
		list.add(getList("/json/general_prediction_life_style.json").get(bhav));
		bhav = laganRashi;// (lagna[12]-1)%12
		list.add(getList("/json/general_prediction_career.json").get(bhav));
		bhav = getBhavOfPlant(laganRashi, getRasiNumber(degArray[7])) - 1;
		list.add(getList("/json/general_prediction_occupation.json").get(bhav));
		bhav = getBhavOfPlant(laganRashi, getRasiNumber(degArray[3])) - 1;
		list.add(getList("/json/general_prediction_health.json").get(bhav));
		bhav = getBhavOfPlant(laganRashi, getRasiNumber(degArray[11])) - 1;
		list.add(getList("/json/general_prediction_hobbies.json").get(bhav));
		bhav = getBhavOfPlant(laganRashi, getRasiNumber(degArray[6])) - 1;
		list.add(getList("/json/general_prediction_love_matters.json").get(bhav));
		bhav = getBhavOfPlant(laganRashi, getRasiNumber(degArray[5])) - 1;
		list.add(getList("/json/general_prediction_finance.json").get(bhav));
		bhav = getBhavOfPlant(laganRashi, getRasiNumber(degArray[4])) - 1;
		list.add(getList("/json/general_prediction_eduction.json").get(bhav));
		return list;
	}

	private int getBhavOfPlant(int lagnaRashi, int plntRashi) {
		int bhavNumber = plntRashi - lagnaRashi;
		if (bhavNumber < 0) {
			bhavNumber += 12;
		}
		bhavNumber += 1;
		return bhavNumber;
	}
}
