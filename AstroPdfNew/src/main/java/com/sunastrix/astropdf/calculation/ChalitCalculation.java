package com.sunastrix.astropdf.calculation;

import java.util.ArrayList;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astropdf.model.KundliChalitTableModel;
import com.sunastrix.astropdf.util.ConstantHindi;


public class ChalitCalculation {
	DesktopHoroNew desktopHoro;

	public ChalitCalculation(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	public double[] getKpDegreeArray() {
		double[] kpCuspDegreeArray = { desktopHoro.getKPCuspLongitude(1), desktopHoro.getKPCuspLongitude(2),
				desktopHoro.getKPCuspLongitude(3), desktopHoro.getKPCuspLongitude(4), desktopHoro.getKPCuspLongitude(5),
				desktopHoro.getKPCuspLongitude(6), desktopHoro.getKPCuspLongitude(7), desktopHoro.getKPCuspLongitude(8),
				desktopHoro.getKPCuspLongitude(9), desktopHoro.getKPCuspLongitude(10),
				desktopHoro.getKPCuspLongitude(11), desktopHoro.getKPCuspLongitude(12) };

		return kpCuspDegreeArray;
	}

	private double checkDegree(double deg) {
		double temp = deg;
		if (temp < 0)
			temp += 360.00;
		if (temp > 360.0)
			temp -= 360.00;
		return temp;
	}

	private double[] getCuspsDegreeArray() {
		double[] tempDegree = new double[12];
		double diff2;
		double temp1;
		double[] cuspDegree = getCuspsMidDegreeArrayForChalit();

		// CLACULATE CUSP DEGREE
		for (int i = 0; i < 12; i++) {
			if (i == 0) {
				diff2 = cuspDegree[0] - cuspDegree[11];
				if (diff2 < 0) {
					diff2 = 360.0 - cuspDegree[11];
					diff2 += cuspDegree[0];
				}
			} else {
				diff2 = cuspDegree[i] - cuspDegree[i - 1];
				if (diff2 < 0) {
					diff2 = 360.0 - cuspDegree[i - 1];
					diff2 += cuspDegree[i];
				}
			}
			diff2 /= 2.0;
			temp1 = cuspDegree[i] - diff2;
			if (temp1 < 0.0)
				temp1 += 360.0;
			tempDegree[i] = temp1;
		}
		return tempDegree;
	}

	private double[] getCuspsMidDegreeArrayForChalit() {
		double[] cuspDegree = new double[12];
		double ayaDiff = desktopHoro.getKPAyanamsaLongitude() - desktopHoro.getAyanamsa();

		// CUSP -1
		cuspDegree[0] = getKpDegreeArray()[0] + ayaDiff;
		cuspDegree[0] = checkDegree(cuspDegree[0]);

		// CUSP -10
		cuspDegree[9] = getKpDegreeArray()[9] + ayaDiff;
		cuspDegree[9] = checkDegree(cuspDegree[9]);

		// CUSP -7
		cuspDegree[6] = cuspDegree[0] + 180;
		cuspDegree[6] = checkDegree(cuspDegree[6]);

		// CUSP -4
		cuspDegree[3] = cuspDegree[9] - 180;
		cuspDegree[3] = checkDegree(cuspDegree[3]);

		// CUSP -2,3
		double diff = cuspDegree[3] - cuspDegree[0];
		if (diff < 0)
			diff += 360.0;
		cuspDegree[1] = cuspDegree[0] + diff / 3;
		cuspDegree[1] = checkDegree(cuspDegree[1]);
		cuspDegree[2] = cuspDegree[1] + diff / 3;
		cuspDegree[2] = checkDegree(cuspDegree[2]);

		// CUSP -5,6
		diff = cuspDegree[6] - cuspDegree[3];
		if (diff < 0)
			diff += 360.0;
		cuspDegree[4] = cuspDegree[3] + diff / 3;
		cuspDegree[4] = checkDegree(cuspDegree[4]);
		cuspDegree[5] = cuspDegree[4] + diff / 3;
		cuspDegree[5] = checkDegree(cuspDegree[5]);

		// CUSP -8,9
		diff = cuspDegree[9] - cuspDegree[6];
		if (diff < 0)
			diff += 360.0;
		cuspDegree[7] = cuspDegree[6] + diff / 3;
		cuspDegree[7] = checkDegree(cuspDegree[7]);
		cuspDegree[8] = cuspDegree[7] + diff / 3;
		cuspDegree[8] = checkDegree(cuspDegree[8]);

		// CUSP -11,12
		diff = cuspDegree[0] - cuspDegree[9];
		if (diff < 0)
			diff += 360.0;
		cuspDegree[10] = cuspDegree[9] + diff / 3;
		cuspDegree[10] = checkDegree(cuspDegree[10]);
		cuspDegree[11] = cuspDegree[10] + diff / 3;
		cuspDegree[11] = checkDegree(cuspDegree[11]);
		return cuspDegree;
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

	public ArrayList<KundliChalitTableModel> getChalitTableData() {
		ArrayList<KundliChalitTableModel> arrayList = new ArrayList<KundliChalitTableModel>();
		double[] cuspDegArrayList = getCuspsDegreeArray();
		double[] midDegArrayList = getCuspsMidDegreeArrayForChalit();
		String degSign = "°";
		String minSign = "'";
		String secSign = "\"";
		String[] rashiShortNameArr = new ConstantHindi().rashiName;
		for (int i = 0; i < 12; i++) {
			int bhBegRashi = (int) (cuspDegArrayList[i] / 30.0);
			int midBhRashi = (int) (midDegArrayList[i] / 30.0);
			double bhBegRem = cuspDegArrayList[i] % 30.0;
			double midBhRem = midDegArrayList[i] % 30.0;
			KundliChalitTableModel kundliChalitTableModel = new KundliChalitTableModel();
			kundliChalitTableModel.setBhav(i + 1);
			kundliChalitTableModel.setBhBegSign(rashiShortNameArr[bhBegRashi]);
			kundliChalitTableModel.setBhBegDeg(formatDMSInStringWithSign(bhBegRem, degSign, minSign, secSign));
			kundliChalitTableModel.setBhMidSign(rashiShortNameArr[midBhRashi]);
			kundliChalitTableModel.setBhMidDeg(formatDMSInStringWithSign(midBhRem, degSign, minSign, secSign));
			arrayList.add(kundliChalitTableModel);
		}

		return arrayList;
	}

	public int[] getChalitChartArray() {
		int lagna = 0;
		int[] planetInRashi = new int[13];
		double[] cuspsDegreeArray = getCuspsDegreeArray();
		double[] planetDegreeArray = getPlanetDegreeArray();
		double lagnaDegree = planetDegreeArray[0];
		double lagnaVal = cuspsDegreeArray[0] + 1.00;
		if (lagnaVal > 360.00) {
			lagnaVal -= 360.00;
		}
		var count = 0;
		while (count < planetDegreeArray.length - 1) {
			planetDegreeArray[count] = planetDegreeArray[count + 1];
			count++;
		}
		planetDegreeArray[count] = lagnaDegree;
		boolean isLagnaAdded = false;
		double bhav1;
		double bhav2;
		for (int i = 0; i < 12; i++) {
			bhav1 = cuspsDegreeArray[i];
			if (i < 11) {
				bhav2 = cuspsDegreeArray[i + 1];
			} else {
				bhav2 = cuspsDegreeArray[0];
			}
			for (int j = 0; j < 12; j++) {
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

	private double[] getPlanetDegreeArray() {
		double[] planetDegreeArray = { desktopHoro.getAsc(), desktopHoro.getSun(), desktopHoro.getMoon(),
				desktopHoro.getMars(), desktopHoro.getMercury(), desktopHoro.getJupitor(), desktopHoro.getVenus(),
				desktopHoro.getSaturn(), desktopHoro.getRahu(), desktopHoro.getKetu(), desktopHoro.getUranus(),
				desktopHoro.getNeptune(), desktopHoro.getPluto() };
		return planetDegreeArray;
	}

	private boolean hasInHouse(double cusp2, double cusp1, double plntDegree) {
		var temp2 = cusp2;
		if (temp2 - cusp1 < 0)
			temp2 += 360.00;
		if (cusp1 < plntDegree + 360.0 && plntDegree + 360.0 < temp2) {
			return true;
		}
		return cusp1 < plntDegree && plntDegree < temp2;
	}
}
