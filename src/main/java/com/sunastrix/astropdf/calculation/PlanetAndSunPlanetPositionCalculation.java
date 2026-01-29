package com.sunastrix.astropdf.calculation;

import java.util.ArrayList;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astropdf.model.BasicPlanetDataModel;
import com.sunastrix.astropdf.model.BasicPlanetSubDataModel;
import com.sunastrix.astropdf.util.ConstantHindi;

public class PlanetAndSunPlanetPositionCalculation {
	DesktopHoroNew desktopHoro;

	public PlanetAndSunPlanetPositionCalculation(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	private double[] getPlanetDegreeArray() {
		double[] planetDegreeArray = { desktopHoro.getAsc(), desktopHoro.getSun(), desktopHoro.getMoon(),
				desktopHoro.getMars(), desktopHoro.getMercury(), desktopHoro.getJupitor(), desktopHoro.getVenus(),
				desktopHoro.getSaturn(), desktopHoro.getRahu(), desktopHoro.getKetu(), desktopHoro.getUranus(),
				desktopHoro.getNeptune(), desktopHoro.getPluto() };
		return planetDegreeArray;
	}

	public ArrayList<BasicPlanetDataModel> getPlanetsData() {
		double[] plaDeg = getPlanetDegreeArray();
		/*
		 * String degSign = "°"; String minSign = "'"; String secSign = "\"";
		 */
		String degSign = "°";
		String minSign = "'";
		String secSign = "\"";
		ConstantHindi constantHindi = new ConstantHindi();

		String[] plaName = constantHindi.plaAndLagnaNames;
		String sign[] = constantHindi.rashiName;
		String naksh[] = constantHindi.nakshName;
		ArrayList<BasicPlanetDataModel> arrayList = new ArrayList<BasicPlanetDataModel>();
		BasicPlanetDataModel basicPlanetDataModel;
		for (int i = 0; i < 13; i++) {
			basicPlanetDataModel = new BasicPlanetDataModel();
			basicPlanetDataModel.setPlaName(plaName[i]);
			basicPlanetDataModel.setSign(sign[getRasiNumber(plaDeg[i])]);
			basicPlanetDataModel.setDegree(formatDMSInStringWithSign(plaDeg[i] % 30, degSign, minSign, secSign));
			basicPlanetDataModel.setNaks(naksh[getNakshatraNumber(plaDeg[i])].replace("\u200D", ""));
			basicPlanetDataModel.setPlaCharan("(" + getPlntCharan(plaDeg[i]) + ")");

			arrayList.add(basicPlanetDataModel);

		}
		return arrayList;
	}

	public ArrayList<BasicPlanetSubDataModel> getPlanetsSubData() {
		double[] plaDeg = getPlanetDegreeArray();
		String degSign = "°";
		String minSign = "'";
		String secSign = "\"";
		ConstantHindi constantHindi = new ConstantHindi();
		String[] plaName = constantHindi.plaAndLagnaNames;
		String[] rashiLord = constantHindi.rashiLordName;

		String[] nakshLord = constantHindi.nakshLordName;

		ArrayList<BasicPlanetSubDataModel> arrayList = new ArrayList<BasicPlanetSubDataModel>();
		for (int i = 0; i < 13; i++) {
			String rasiNakSubSub = getRasiNakSubSub(plaDeg[i], rashiLord, nakshLord);
			String[] arr = rasiNakSubSub.split("-");
			String rashi = arr[0];
			String naks = arr[1];
			String sub = arr[2];
			String subsub = arr[3];
			BasicPlanetSubDataModel BasicKundliPlanetSubData = new BasicPlanetSubDataModel();
			BasicKundliPlanetSubData.setPlaName(plaName[i]);
			BasicKundliPlanetSubData.setPlaDeg(formatDMSInStringWithSign(plaDeg[i], degSign, minSign, secSign));
			BasicKundliPlanetSubData.setNakshLord(naks);
			BasicKundliPlanetSubData.setSignLord(rashi);
			BasicKundliPlanetSubData.setSubLord(sub);
			BasicKundliPlanetSubData.setSubSubLord(subsub);
			arrayList.add(BasicKundliPlanetSubData);
		}
		return arrayList;
	}

	private int getRasiNumber(double deg) {
		return (int) (deg / 30);
	}

	private int getNakshatraNumber(double deg) {
		return (int) (deg * 0.075);
	}

	private int getPlntCharan(double pl) {
		double aa = pl;
		return (int) (4 * fract(aa * 3.0 / 40)) + 1;
	}

	private double fract(double x) {
		double y;
		long i = (long) x;
		y = x - i;
		return y;
	}

	private String formatDMSInStringWithSign(double deg, String DegSign, String MinSign, String SecSign) {
		String strFormattedDeg;
		String sDeg;
		String sMin;
		String sSec;
		int min;
		int sec;

		sDeg = String.valueOf((int) deg).trim();

		// Use an if-else chain to achieve the same functionality as the when expression
		if (sDeg.length() == 0) {
			strFormattedDeg = "000" + sDeg;
		} else if (sDeg.length() == 1) {
			strFormattedDeg = "00" + sDeg;
		} else if (sDeg.length() == 2) {
			strFormattedDeg = "0" + sDeg;
		} else {
			strFormattedDeg = sDeg;
		}

		strFormattedDeg += DegSign;
		double temp = deg - (double) ((int) deg);
		min = (int) (temp * 60);
		sMin = String.valueOf(min).trim();
		if (sMin.length() < 2) {
			strFormattedDeg = strFormattedDeg + "0" + sMin;
		} else {
			strFormattedDeg = strFormattedDeg + sMin;
		}

		strFormattedDeg += MinSign;
		temp *= 60;
		temp -= (double) ((int) temp);
		sec = (int) (temp * 60);

		sSec = String.valueOf(sec).trim();
		if (sSec.length() < 2) {
			strFormattedDeg = strFormattedDeg + "0" + sSec;
		} else {
			strFormattedDeg = strFormattedDeg + sSec;
		}

		strFormattedDeg += SecSign;
		return strFormattedDeg.trim();
	}

	private String getRasiNakSubSub(double deg, String[] RasiLord, String[] NakLord) {
		int[] y1 = new int[12];
		y1[0] = 7;
		y1[1] = 20;
		y1[2] = 6;
		y1[3] = 10;
		y1[4] = 7;
		y1[5] = 18;
		y1[6] = 16;
		y1[7] = 19;
		y1[8] = 17;

		double d = deg;
		StringBuilder sb = new StringBuilder();
		int i = 0;
		int f = (int) (d / 30.0);
		int a = (int) (d / 120.0);
		d -= a * 120.0;
		a = (int) (d * 3.0 / 40.0);
		d -= a * 40.0 / 3.0;
		d *= 9.0;
		int b = 0;

		while (b < 9) {
			i = a + b;
			if (i >= 9) {
				i -= 9;
			}
			if (y1[i] <= d) {
				d -= y1[i];
			} else {
				break;
			}
			b++;
		}
		b = i;
		d = d / y1[b] * (40.0 / 3.0);
		d *= 9.0;
		int c = 0;
		while (c < 9) {
			i = b + c; // Calculate i
			if (i >= 9) {
				i -= 9; // Adjust i if it is 9 or greater
			}

			// Use an if-else block instead of Kotlin's if expression
			if (y1[i] <= d) {
				d -= y1[i]; // Subtract y1[i] from d
			} else {
				break; // Exit the loop if the condition is not met
			}

			c++; // Increment c
		}

		c = i;
		/*
		 * sb.append(CGlobalVariables.rasiLord[f] + "-");
		 * sb.append(CGlobalVariables.nakLord[a] + "-");
		 * sb.append(CGlobalVariables.nakLord[b] + "-");
		 * sb.append(CGlobalVariables.nakLord[c]);
		 */sb.append(RasiLord[f] + "-");
		sb.append(NakLord[a] + "-");
		sb.append(NakLord[b] + "-");
		sb.append(NakLord[c]);
		return sb.toString().trim();
	}
}
