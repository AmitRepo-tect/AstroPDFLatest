package com.sunastrix.astropdf.calculation;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astropdf.model.GemstoneItem;

public class GemstoneCalculation {
	int[] raslrd = { 2, 5, 3, 1, 0, 3, 5, 2, 4, 6, 6, 4 };
	DesktopHoroNew desktopHoro;

	public GemstoneCalculation(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	int[] getLagnaArray() {
		return desktopHoro.getPositionForShodasvarg(0);
	}

	public GemstoneItem getLifeStone() {
		int[] lagna = getLagnaArray();
		int lord = raslrd[lagna[0] - 1];
		GemstoneItem gemstoneItem = getList().get(lord);
		return gemstoneItem;
	}

	public GemstoneItem getBeneficStone() {
		int[] lagna = getLagnaArray();
		var rashi = (lagna[0] + 4);
		if (rashi > 12) {
			rashi -= 12;
		}
		int lord = raslrd[rashi - 1];
		GemstoneItem gemstoneItem = getList().get(lord);
		return gemstoneItem;
	}

	public GemstoneItem getLuckyStone() {
		int[] lagna = getLagnaArray();
		var rashi = (lagna[0] + 8);
		if (rashi > 12) {
			rashi -= 12;
		}
		int lord = raslrd[rashi - 1];
		GemstoneItem gemstoneItem = getList().get(lord);
		return gemstoneItem;
	}

	ArrayList<GemstoneItem> getList() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			InputStream inputStream = getClass().getResourceAsStream("/json/gemstone_report.json");
			List<GemstoneItem> list = mapper.readValue(inputStream, new TypeReference<List<GemstoneItem>>() {
			});

			ArrayList<GemstoneItem> arrayList = new ArrayList<>(list);
			return arrayList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to read muhurat data", e);
		}
	}

}
