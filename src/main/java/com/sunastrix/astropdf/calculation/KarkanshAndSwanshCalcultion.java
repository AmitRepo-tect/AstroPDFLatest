package com.sunastrix.astropdf.calculation;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;

public class KarkanshAndSwanshCalcultion {
	DesktopHoroNew desktopHoro;

	public KarkanshAndSwanshCalcultion(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	public int getKarakanshLagna() {
		int karkanshLagna = calculateHigestDegree();
		return getNavmanshKundliArray()[karkanshLagna];
	}

	double[] getPlanetDegree() {
		double[] planetDegreeArray = { desktopHoro.getAsc(), desktopHoro.getSun(), desktopHoro.getMoon(),
				desktopHoro.getMars(), desktopHoro.getMercury(), desktopHoro.getJupitor(), desktopHoro.getVenus(),
				desktopHoro.getSaturn(), desktopHoro.getRahu(), desktopHoro.getKetu(), desktopHoro.getUranus(),
				desktopHoro.getNeptune(), desktopHoro.getPluto() };
		return planetDegreeArray;
	}

	private int calculateHigestDegree() {
		double[] planetsDegree = getPlanetDegree();
		double higestDegreeNumber;
		double higestValue = 0.0;
		int position = 0;
		for (int i = 1; i <= 7; i++) {
			higestDegreeNumber = planetsDegree[i] % 30;
			if (higestDegreeNumber > higestValue) {
				position = i - 1;
				higestValue = higestDegreeNumber;
			}
		}
		return position;
	}

	int[] getNavmanshKundliArray() {
		return getIntArray(desktopHoro.getPositionForShodasvarg(5), 0);
	}

	private int[] getIntArray(int[] planetPosition, int lagnaPos) {

		int[] intArray = new int[13];
		int lagna = planetPosition[lagnaPos];
		for (int i = 0; i < planetPosition.length - 1; i++) {
			intArray[i] = planetPosition[i + 1];
		}
		intArray[planetPosition.length - 1] = lagna;
		return intArray;
	}
}
