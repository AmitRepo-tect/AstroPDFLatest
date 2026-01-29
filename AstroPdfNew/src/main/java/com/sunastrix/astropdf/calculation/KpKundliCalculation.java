package com.sunastrix.astropdf.calculation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astropdf.model.BasicKundliPlanetSubData;
import com.sunastrix.astropdf.model.HouseSignificatorsBean;
import com.sunastrix.astropdf.model.PlanetSignificationBean;
import com.sunastrix.astropdf.util.ConstantHindi;



public class KpKundliCalculation {
	DesktopHoroNew desktopHoro;

	public KpKundliCalculation(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	private double[] getPlanetDegreeArray() {
		double[] planetDegreeArray = { desktopHoro.getAsc(), desktopHoro.getSun(), desktopHoro.getMoon(),
				desktopHoro.getMars(), desktopHoro.getMercury(), desktopHoro.getJupitor(), desktopHoro.getVenus(),
				desktopHoro.getSaturn(), desktopHoro.getRahu(), desktopHoro.getKetu(), desktopHoro.getUranus(),
				desktopHoro.getNeptune(), desktopHoro.getPluto() };
		return planetDegreeArray;
	}

	private double[] getKPPlanetDegreeArray() {
		double[] planetDegree = getPlanetDegreeArray();
		double[] degree = new double[13];
		double tempCalculation;
		double ayanDiff = (double) desktopHoro.getAyanamsa() - desktopHoro.getKPAyanamsaLongitude();
		String str = "";
		for (int i = 0; i < planetDegree.length; i++) {
			tempCalculation = planetDegree[i] + ayanDiff;
			if (tempCalculation < 0) {
				tempCalculation += 360.00;
			} else if (tempCalculation >= 360) {
				tempCalculation -= 360.00;
			}
			degree[i] = tempCalculation;
			str = str + degree[i] + ", ";
		}
		//degree[12] = planetDegree[0];
		return degree;
	}

	public double[] getKpDegreeArray() {
		double[] kpCuspDegreeArray = { desktopHoro.getKPCuspLongitude(1), desktopHoro.getKPCuspLongitude(2),
				desktopHoro.getKPCuspLongitude(3), desktopHoro.getKPCuspLongitude(4), desktopHoro.getKPCuspLongitude(5),
				desktopHoro.getKPCuspLongitude(6), desktopHoro.getKPCuspLongitude(7), desktopHoro.getKPCuspLongitude(8),
				desktopHoro.getKPCuspLongitude(9), desktopHoro.getKPCuspLongitude(10),
				desktopHoro.getKPCuspLongitude(11), desktopHoro.getKPCuspLongitude(12) };

		return kpCuspDegreeArray;
	}

	public int[] getKPLagnaRashiKundliPlanetsRashiArray() {
		double[] obj = getKPPlanetDegreeArray();
		int[] planestRashi = new int[13];
		planestRashi[0] = (int) (obj[1] / 30.00) + 1;
		planestRashi[1] = (int) (obj[2] / 30.00) + 1;
		planestRashi[2] = (int) (obj[3] / 30.00) + 1;
		planestRashi[3] = (int) (obj[4] / 30.00) + 1;
		planestRashi[4] = (int) (obj[5] / 30.00) + 1;
		planestRashi[5] = (int) (obj[6] / 30.00) + 1;
		planestRashi[6] = (int) (obj[7] / 30.00) + 1;
		planestRashi[7] = (int) (obj[8] / 30.00) + 1;
		planestRashi[8] = (int) (obj[9] / 30.00) + 1;
		planestRashi[9] = (int) (obj[10] / 30.00) + 1;
		planestRashi[10] = (int) (obj[11] / 30.00) + 1;
		planestRashi[11] = (int) (obj[12] / 30.00) + 1;
		planestRashi[12] = (int) (obj[0] / 30.00) + 1;
		return planestRashi;
	}

	public int[] getKpChartArray() {
		int lagna = 0;
		int[] planetInRashi = new int[13];
		double[] cuspsDegreeArray = getKpDegreeArray();
		double[] planetDegreeArray = getKPPlanetDegreeArray();
		double lagnaDegree = planetDegreeArray[0];
		double lagnaVal = cuspsDegreeArray[0] + 1.00;
		if (lagnaVal > 360.00) {
			lagnaVal -= 360.00;
		}
		int count = 0;
		while (count < planetDegreeArray.length - 1) {
			planetDegreeArray[count] = planetDegreeArray[count + 1];
			count++;
		}
		planetDegreeArray[count] = lagnaDegree;
		boolean isLagnaAdded = false;
		double bhav1;
		double bhav2;
		for (int i = 0; i <= 11; i++) {
			bhav1 = cuspsDegreeArray[i];
			if (i < 11) {
				bhav2 = cuspsDegreeArray[i + 1];
			} else {
				bhav2 = cuspsDegreeArray[0];
			}
			for (int j = 0; j <= 11; j++) {
				if (hasInHouse(bhav2, bhav1, planetDegreeArray[j])) {
					planetInRashi[j] = i;
				}
				if (!isLagnaAdded && hasInHouse(bhav2, bhav1, lagnaVal)) {
					lagna = i;
					isLagnaAdded = true;
				}
			}
		}
		planetInRashi[12] = lagna;
		return planetInRashi;
	}

	public ArrayList<PlanetSignificationBean> getKPPlanetSignificationData() {
		ConstantHindi constantHindi = new ConstantHindi();
		ArrayList<PlanetSignificationBean> plaSigList = new ArrayList<PlanetSignificationBean>();
		String[] planetName = constantHindi.planetName;
		plaSigList.add(new PlanetSignificationBean(planetName[0], removeZeroFromPlanetSignificationStr(
				convertIntArrayToString(desktopHoro.getKPPlanetSignification(1)))));
		plaSigList.add(new PlanetSignificationBean(planetName[1], removeZeroFromPlanetSignificationStr(
				convertIntArrayToString(desktopHoro.getKPPlanetSignification(2)))));
		plaSigList.add(new PlanetSignificationBean(planetName[2], removeZeroFromPlanetSignificationStr(
				convertIntArrayToString(desktopHoro.getKPPlanetSignification(3)))));
		plaSigList.add(new PlanetSignificationBean(planetName[3], removeZeroFromPlanetSignificationStr(
				convertIntArrayToString(desktopHoro.getKPPlanetSignification(4)))));
		plaSigList.add(new PlanetSignificationBean(planetName[4], removeZeroFromPlanetSignificationStr(
				convertIntArrayToString(desktopHoro.getKPPlanetSignification(5)))));
		plaSigList.add(new PlanetSignificationBean(planetName[5], removeZeroFromPlanetSignificationStr(
				convertIntArrayToString(desktopHoro.getKPPlanetSignification(6)))));
		plaSigList.add(new PlanetSignificationBean(planetName[6], removeZeroFromPlanetSignificationStr(
				convertIntArrayToString(desktopHoro.getKPPlanetSignification(7)))));
		plaSigList.add(new PlanetSignificationBean(planetName[7], removeZeroFromPlanetSignificationStr(
				convertIntArrayToString(desktopHoro.getKPPlanetSignification(8)))));
		plaSigList.add(new PlanetSignificationBean(planetName[8], removeZeroFromPlanetSignificationStr(
				convertIntArrayToString(desktopHoro.getKPPlanetSignification(9)))));
		return plaSigList;
	}

	public ArrayList<HouseSignificatorsBean> getKPHouseSignificatorsData() {
		ConstantHindi constantHindi = new ConstantHindi();
		String[] planetName = constantHindi.plaNameShort;
		ArrayList<HouseSignificatorsBean> houseSignificatorList = new ArrayList<>();
		String[] strTempArr = new String[9];
		String[] bhavArr = new String[] { "", "", "", "", "", "", "", "", "", "", "", "" };

		try {
			strTempArr[0] = removeZeroFromPlanetSignificationStr(
					convertIntArrayToString(desktopHoro.getKPPlanetSignification(1)));
			strTempArr[1] = removeZeroFromPlanetSignificationStr(
					convertIntArrayToString(desktopHoro.getKPPlanetSignification(2)));
			strTempArr[2] = removeZeroFromPlanetSignificationStr(
					convertIntArrayToString(desktopHoro.getKPPlanetSignification(3)));
			strTempArr[3] = removeZeroFromPlanetSignificationStr(
					convertIntArrayToString(desktopHoro.getKPPlanetSignification(4)));
			strTempArr[4] = removeZeroFromPlanetSignificationStr(
					convertIntArrayToString(desktopHoro.getKPPlanetSignification(5)));
			strTempArr[5] = removeZeroFromPlanetSignificationStr(
					convertIntArrayToString(desktopHoro.getKPPlanetSignification(6)));
			strTempArr[6] = removeZeroFromPlanetSignificationStr(
					convertIntArrayToString(desktopHoro.getKPPlanetSignification(7)));
			strTempArr[7] = removeZeroFromPlanetSignificationStr(
					convertIntArrayToString(desktopHoro.getKPPlanetSignification(8)));
			strTempArr[8] = removeZeroFromPlanetSignificationStr(
					convertIntArrayToString(desktopHoro.getKPPlanetSignification(9)));

			for (int j = 0; j < strTempArr.length; j++) {
				if (strTempArr[j] != null) {
					String[] tArray = strTempArr[j].split(",");
					for (String s : tArray) {
						int houseNumber = Integer.parseInt(s.trim());
						if (houseNumber >= 1 && houseNumber <= 12)
							bhavArr[houseNumber - 1] += planetName[j] + "]";
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 12; i++) {
			houseSignificatorList.add(new HouseSignificatorsBean(i + 1, bhavArr[i]));
		}

		return houseSignificatorList;
	}

	public ArrayList<BasicKundliPlanetSubData> getKPPlanetsData() {
		ConstantHindi constantHindi = new ConstantHindi();
		double[] plaDeg = getKPPlanetDegreeArray(); // Implement this function
		String degSign = constantHindi.degSymbol;
		String minSign = constantHindi.minSymbol;
		String secSign = constantHindi.secSymbol;
		String[] plaName = constantHindi.planetAndLagnaNameList;
		String[] rashiLord = constantHindi.rasiLordShortNameList;
		String[] nakshLord = constantHindi.nakLordShortNameList;

		ArrayList<BasicKundliPlanetSubData> arrayList = new ArrayList<>();

		for (int i = 0; i <= 12; i++) {
			String rasiNakSubSub = getRashiNakSubSub(plaDeg[i], rashiLord, nakshLord); // Implement this function
			String[] arr = rasiNakSubSub.split("-");
			String rashi = arr[0];
			String naks = arr[1];
			String sub = arr[2];
			String subsub = arr[3];

			arrayList.add(new BasicKundliPlanetSubData(plaName[i],
					formatDMSInStringWithSign(plaDeg[i], degSign, minSign, secSign), // Implement this function
					rashi, naks, sub, subsub));
		}
		return arrayList;
	}

	public ArrayList<BasicKundliPlanetSubData> getKPCuspData() {
		ConstantHindi constantHindi = new ConstantHindi();
		double[] plaDeg = getKpDegreeArray();
		String degSign = constantHindi.degSymbol;
		String minSign = constantHindi.minSymbol;
		String secSign = constantHindi.secSymbol;
		String[] plaName = constantHindi.planetAndLagnaNameList;
		String[] rashiLord = constantHindi.rasiLordShortNameList;
		String[] nakshLord = constantHindi.nakLordShortNameList;

		ArrayList<BasicKundliPlanetSubData> arrayList = new ArrayList<>();

		for (int i = 0; i < 12; i++) {
			String rasiNakSubSub = getRashiNakSubSub(plaDeg[i], rashiLord, nakshLord);
			List<String> arr = Arrays.asList(rasiNakSubSub.split("-"));
			String rashi = arr.get(0);
			String naks = arr.get(1);
			String sub = arr.get(2);
			String subsub = arr.get(3);
			arrayList.add(new BasicKundliPlanetSubData(String.valueOf(i + 1),
					formatDMSInStringWithSign(plaDeg[i], degSign, minSign, secSign), rashi, naks, sub, subsub));

		}
		return arrayList;
	}

	private String removeZeroFromPlanetSignificationStr(String str) {
		String[] arr = str.split(",");
		StringBuilder result = new StringBuilder();

		for (String s : arr) {
			if (!s.equals("0")) {
				result.append(s).append(", ");
			}
		}

		if (result.length() > 2) {
			return result.substring(0, result.length() - 2); // Remove the last comma and space
		}

		return result.toString();
	}

	public String formatDMSInStringWithSign(double fDeg, String DegSign, String MinSign, String SecSign) {
		String strFormattedDeg;
		String sDeg, sMin, sSec;
		int min, sec;

		sDeg = Integer.toString((int) fDeg);

		switch (sDeg.trim().length()) {
		case 0:
			strFormattedDeg = "000" + sDeg;
			break;
		case 1:
			strFormattedDeg = "00" + sDeg;
			break;
		case 2:
			strFormattedDeg = "0" + sDeg;
			break;
		default:
			strFormattedDeg = sDeg;
		}

		strFormattedDeg += DegSign;
		double temp = fDeg - (int) fDeg;
		min = (int) (temp * 60);
		sMin = Integer.toString(min);

		strFormattedDeg += (sMin.trim().length() < 2) ? "0" + sMin : sMin;
		strFormattedDeg += MinSign;
		temp *= 60;
		temp -= (int) temp;
		sec = (int) (temp * 60);
		sSec = Integer.toString(sec);

		strFormattedDeg += (sSec.trim().length() < 2) ? "0" + sSec : sSec;
		strFormattedDeg += SecSign;

		return strFormattedDeg.trim();
	}

	public String getRashiNakSubSub(double d, String[] RasiLord, String[] NakLord) {
		int[] y1 = { 7, 20, 6, 10, 7, 18, 16, 19, 17 };

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
			if (i >= 9)
				i -= 9;
			if (y1[i] > d)
				break;
			d -= y1[i];
			b++;
		}
		b = i;
		d = d / y1[b] * (40.0 / 3.0);
		d *= 9.0;

		int c = 0;
		while (c < 9) {
			i = b + c;
			if (i >= 9)
				i -= 9;
			if (y1[i] > d)
				break;
			d -= y1[i];
			c++;
		}
		c = i;

		sb.append(RasiLord[f]).append("-").append(NakLord[a]).append("-").append(NakLord[b]).append("-")
				.append(NakLord[c]);

		return sb.toString().trim();
	}

	public boolean hasInHouse(double cusp2, double cusp1, double plntDegree) {
		double temp2 = cusp2;
		if (temp2 - cusp1 < 0)
			temp2 += 360.00;

		if (cusp1 < plntDegree + 360.0 && plntDegree + 360.0 < temp2) {
			return true;
		}
		return cusp1 < plntDegree && plntDegree < temp2;
	}

	String convertIntArrayToString(int[] array) {
		String str = "";
		for (int i = 0; i < array.length; i++) {
			if (i == array.length - 1) {
				str += array[i];
			} else {
				str += array[i] + ",";
			}
		}
		return str;
	}

}
