package com.sunastrix.astropdf.calculation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astropdf.model.FavourablePointBean;
import com.sunastrix.astropdf.model.GhatChakarBean;

public class GhatakAndFavorableCalculation {
	DesktopHoroNew desktopHoro;

	public GhatakAndFavorableCalculation(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	private double[] getPlanetDegreeArray() {
		double[] planetDegreeArray = { desktopHoro.getAsc(), desktopHoro.getSun(), desktopHoro.getMoon(),
				desktopHoro.getMars(), desktopHoro.getMercury(), desktopHoro.getJupitor(), desktopHoro.getVenus(),
				desktopHoro.getSaturn(), desktopHoro.getRahu(), desktopHoro.getKetu(), desktopHoro.getUranus(),
				desktopHoro.getNeptune(), desktopHoro.getPluto() };
		return planetDegreeArray;
	}

	private int getMoonSign() {
		double[] plaDegArray = getPlanetDegreeArray();
		return ((int) plaDegArray[2] / 30) + 1;
	}

	public GhatChakarBean getGhatChakarData() {

		ArrayList<GhatChakarBean> list = getGatakList("/json/ghatak_point_hi.json");
		return list.get(getMoonSign());
	}

	public FavourablePointBean getFavourableData() {
		ArrayList<FavourablePointBean> list = getFavourableList("/json/favourable_point_hi.json");

		return list.get(getMoonSign());
	}

	ArrayList<GhatChakarBean> getGatakList(String url) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			InputStream inputStream = getClass().getResourceAsStream(url);
			List<GhatChakarBean> list = mapper.readValue(inputStream, new TypeReference<List<GhatChakarBean>>() {
			});

			ArrayList<GhatChakarBean> arrayList = new ArrayList<>(list);
			return arrayList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to read muhurat data", e);
		}
	}

	ArrayList<FavourablePointBean> getFavourableList(String url) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			InputStream inputStream = getClass().getResourceAsStream(url);
			List<FavourablePointBean> list = mapper.readValue(inputStream,
					new TypeReference<List<FavourablePointBean>>() {
					});

			ArrayList<FavourablePointBean> arrayList = new ArrayList<>(list);
			return arrayList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to read muhurat data", e);
		}
	}

}
