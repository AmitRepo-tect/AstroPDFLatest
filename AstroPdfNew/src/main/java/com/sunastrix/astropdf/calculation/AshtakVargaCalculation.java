package com.sunastrix.astropdf.calculation;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;

public class AshtakVargaCalculation {
	DesktopHoroNew desktopHoro;

	public AshtakVargaCalculation(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	public String[] getAshtakVargaData() {
		String[] ashtakvargaRashi = new String[12];
		for (int i = 0; i < 12; i++) {
			StringBuilder ashatakVarga = new StringBuilder();
			for (int j = 0; j < 7; j++) {
				ashatakVarga.append(desktopHoro.getAshtakvargaBinduForSignAndPlanet(j, i)).append(",");
			}
			ashtakvargaRashi[i] = ashatakVarga.toString();
		}
		return ashtakvargaRashi;
	}

}
