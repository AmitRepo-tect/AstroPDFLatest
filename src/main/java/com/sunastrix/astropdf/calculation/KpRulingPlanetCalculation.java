package com.sunastrix.astropdf.calculation;

import java.util.ArrayList;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astropdf.model.KpRulingPlanetBean;
import com.sunastrix.astropdf.util.ConstantHindi;
import com.sunastrix.astropdf.util.KpConstants;


public class KpRulingPlanetCalculation {
	DesktopHoroNew desktopHoro;
	private int[] y1 = { 7, 20, 6, 10, 7, 18, 16, 19, 17 };
	private double[] planetDegree;
	private double[] kpCuspDegree;
	private double fortuna;

	public KpRulingPlanetCalculation(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;

	}

	public ArrayList<KpRulingPlanetBean> getKPRulingPlanetData() {
		setData();
		return getRulingPlanetData();
	}

	public ArrayList<KpRulingPlanetBean> getKPMiscData() {
		setData();
		return getMiscData();
	}

	void setData() {
		planetDegree = getKPPlanetDegreeArray();
		kpCuspDegree = getKpDegreeArray();
		fortuna = desktopHoro.getKPFortunaLongitude();
		if (fortuna < 0) {
			fortuna += 360;
		} else if (fortuna > 360) {
			fortuna -= 360;
		}
	}

	private double[] getPlanetDegreeArray() {
		double[] planetDegreeArray = { desktopHoro.getAsc(), desktopHoro.getSun(), desktopHoro.getMoon(),
				desktopHoro.getMars(), desktopHoro.getMercury(), desktopHoro.getJupitor(), desktopHoro.getVenus(),
				desktopHoro.getSaturn(), desktopHoro.getRahu(), desktopHoro.getKetu(), desktopHoro.getUranus(),
				desktopHoro.getNeptune(), desktopHoro.getPluto() };
		return planetDegreeArray;
	}

	public double[] getKpDegreeArray() {
		double[] kpCuspDegreeArray = { desktopHoro.getKPCuspLongitude(1), desktopHoro.getKPCuspLongitude(2),
				desktopHoro.getKPCuspLongitude(3), desktopHoro.getKPCuspLongitude(4), desktopHoro.getKPCuspLongitude(5),
				desktopHoro.getKPCuspLongitude(6), desktopHoro.getKPCuspLongitude(7), desktopHoro.getKPCuspLongitude(8),
				desktopHoro.getKPCuspLongitude(9), desktopHoro.getKPCuspLongitude(10),
				desktopHoro.getKPCuspLongitude(11), desktopHoro.getKPCuspLongitude(12) };

		return kpCuspDegreeArray;
	}

	private double[] getKPPlanetDegreeArray() {
		double[] planetDegree = getPlanetDegreeArray();
		double[] degree = new double[13];
		double tempCalculation;
		double ayanDiff = (double) desktopHoro.getAyanamsa() - desktopHoro.getKPAyanamsaLongitude();
		String str = "";
		for (int i = 1; i < planetDegree.length; i++) {
			tempCalculation = planetDegree[i] + ayanDiff;
			if (tempCalculation < 0) {
				tempCalculation += 360.00;
			} else if (tempCalculation >= 360) {
				tempCalculation -= 360.00;
			}
			degree[i] = tempCalculation;
			str = str + degree[i] + ", ";
		}
		degree[12] = planetDegree[0];
		return degree;
	}

	private int getRasiNumber(double deg) {
		return (int) (deg / 30);
	}

	private int getNakshatraNumber(double deg) {
		return (int) (deg * 0.075);
	}

	private int getStarLord(double deg) {
		double d = deg;
		int a = (int) (d / 120.0);
		d -= a * 120.0;
		a = (int) (d * 3.0 / 40.0);
		return a;
	}

	private int getSubLordNumber(double deg) {
		double d = deg;
		int i = 0;
		int a = getStarLord(d);
		d -= ((int) (d / 120.0)) * 120.0;
		d -= a * (40.0 / 3.0);
		d *= 9.0;
		int b = 0;
		while (b < 9) {
			i = a + b;
			if (i >= 9) {
				i -= 9;
			}
			if (KpConstants.y1[i] <= d) {
				d -= KpConstants.y1[i];
			} else {
				break;
			}
			b++;
		}
		b = i;
		return b;
	}

	ArrayList<KpRulingPlanetBean> getRulingPlanetData() {
		// _OutKpRulingPlanets.setBirthDayLord(obj.getDayLord())
		ArrayList<KpRulingPlanetBean> rulingPlanetList = new ArrayList<KpRulingPlanetBean>();
		ConstantHindi constantHindi = new ConstantHindi();
		String[] rashiNameList = constantHindi.rasiLordFullNameList;
		String[] naksLordNameList = constantHindi.nakshatraLordFullNameList;
		String[] labelList = constantHindi.kpRulingPlanetsLabelList;
		rulingPlanetList.add(new KpRulingPlanetBean(labelList[0], ""));
		rulingPlanetList.add(new KpRulingPlanetBean(labelList[1], rashiNameList[getRasiNumber(planetDegree[12])]));
		rulingPlanetList
				.add(new KpRulingPlanetBean(labelList[2], naksLordNameList[getNakshatraNumber(planetDegree[12])]));
		rulingPlanetList
				.add(new KpRulingPlanetBean(labelList[3], naksLordNameList[getSubLordNumber(planetDegree[12])]));
		rulingPlanetList.add(new KpRulingPlanetBean(labelList[4], rashiNameList[getRasiNumber(planetDegree[2])]));
		rulingPlanetList
				.add(new KpRulingPlanetBean(labelList[5], naksLordNameList[getNakshatraNumber(planetDegree[2])]));
		rulingPlanetList.add(new KpRulingPlanetBean(labelList[6], naksLordNameList[getSubLordNumber(planetDegree[2])]));

		return rulingPlanetList;
	}

	ArrayList<KpRulingPlanetBean> getMiscData() {
		ConstantHindi constantHindi = new ConstantHindi();
		String degSign = constantHindi.degSymbol;
		String minSign = constantHindi.minSymbol;
		String secSign = constantHindi.secSymbol;
		ArrayList<KpRulingPlanetBean> rulingPlanetList = new ArrayList<KpRulingPlanetBean>();
		String[] rashiNameList = constantHindi.rasiLordFullNameList;
		String[] naksLordNameList = constantHindi.nakshatraLordFullNameList;
		String[] labelList = constantHindi.kpMiscLabelList;

		rulingPlanetList.add(new KpRulingPlanetBean(labelList[0], rashiNameList[getRasiNumber(fortuna)]));
		rulingPlanetList.add(
				new KpRulingPlanetBean(labelList[1], formatDMSInStringWithSign(fortuna, degSign, minSign, secSign)));
		rulingPlanetList.add(new KpRulingPlanetBean(labelList[2], naksLordNameList[getSubLordNumber(fortuna)]));
		rulingPlanetList.add(new KpRulingPlanetBean(labelList[3],
				formatDMSInStringWithSign(desktopHoro.getKPAyanamsaLongitude(), degSign, minSign, secSign)));
		return rulingPlanetList;
	}

	public String formatDMSInStringWithSign(double fDeg, String DegSign, String MinSign, String SecSign) {
		String strFormattedDeg;
		String sDeg, sMin, sSec;
		int min, sec;

		sDeg = String.valueOf((int) fDeg);

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
		sMin = String.valueOf(min);

		strFormattedDeg += (sMin.trim().length() < 2) ? "0" + sMin : sMin;
		strFormattedDeg += MinSign;

		temp *= 60;
		temp -= (int) temp;
		sec = (int) (temp * 60);
		sSec = String.valueOf(sec);

		strFormattedDeg += (sSec.trim().length() < 2) ? "0" + sSec : sSec;
		strFormattedDeg += SecSign;

		return strFormattedDeg.trim();
	}

}
