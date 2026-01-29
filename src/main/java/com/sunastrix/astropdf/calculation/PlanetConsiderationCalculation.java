package com.sunastrix.astropdf.calculation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astropdf.model.PlanetConsiderationBean;

public class PlanetConsiderationCalculation {
	DesktopHoroNew desktopHoro;

	public PlanetConsiderationCalculation(DesktopHoroNew desktopHoro) {
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

	ArrayList<PlanetConsiderationBean> getList(String url) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			InputStream inputStream = getClass().getResourceAsStream(url);
			List<PlanetConsiderationBean> list = mapper.readValue(inputStream,
					new TypeReference<List<PlanetConsiderationBean>>() {
					});

			ArrayList<PlanetConsiderationBean> arrayList = new ArrayList<>(list);
			return arrayList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to read muhurat data", e);
		}
	}

	public ArrayList<PlanetConsiderationBean> getPlaConsideration() {
		double[] degArray = getPlanetDegreeArray();
		ArrayList<PlanetConsiderationBean> list = new ArrayList<PlanetConsiderationBean>();

		int laganRashi = getRasiNumber(degArray[0]);
		int bhav = getBhavOfPlant(laganRashi, getRasiNumber(degArray[1])) - 1;
		list.add(getList("/json/pla_consideration_sun.json").get(bhav));
		bhav = getBhavOfPlant(laganRashi, getRasiNumber(degArray[2])) - 1;
		list.add(getList("/json/pla_consideration_moon.json").get(bhav));
		bhav = getBhavOfPlant(laganRashi, getRasiNumber(degArray[3])) - 1;
		list.add(getList("/json/pla_consideration_mars.json").get(bhav));
		bhav = getBhavOfPlant(laganRashi, getRasiNumber(degArray[4])) - 1;
		list.add(getList("/json/pla_consideration_mercury.json").get(bhav));
		bhav = getBhavOfPlant(laganRashi, getRasiNumber(degArray[5])) - 1;
		list.add(getList("/json/pla_consideration_jupiter.json").get(bhav));
		bhav = getBhavOfPlant(laganRashi, getRasiNumber(degArray[6])) - 1;
		list.add(getList("/json/pla_consideration_venus.json").get(bhav));
		bhav = getBhavOfPlant(laganRashi, getRasiNumber(degArray[7])) - 1;
		list.add(getList("/json/pla_consideration_saturn.json").get(bhav));
		bhav = getBhavOfPlant(laganRashi, getRasiNumber(degArray[8])) - 1;
		list.add(getList("/json/pla_consideration_rahu.json").get(bhav));
		bhav = getBhavOfPlant(laganRashi, getRasiNumber(degArray[9])) - 1;
		list.add(getList("/json/pla_consideration_ketu.json").get(bhav));
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
