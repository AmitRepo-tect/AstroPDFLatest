package com.sunastrix.astropdf.calculation;

import java.util.ArrayList;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astropdf.model.KPCilSubSubBean;
import com.sunastrix.astropdf.util.ConstantHindi;
import com.sunastrix.astropdf.util.KpConstants;
import com.sunastrix.astropdf.util.Utility;


public class KpCilSubSubCalculation {
	DesktopHoroNew desktopHoro;
	double[] planetDegree;
	double[] kpCuspDegree;

	public KpCilSubSubCalculation(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;

	}

	public ArrayList<KPCilSubSubBean> getKPCilSubSubData() {
		setData();
		return getCilSubSubData();
	}

	void setData() {
		planetDegree = getKpPlanetArrayForNakshtraNadi();
		kpCuspDegree = getKpCuspArrayForNakshtraNadi();
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

	private double[] getKpCuspArrayForNakshtraNadi() {
		double[] cuspArray = getKpDegreeArray();
		double[] kpCuspArray = new double[12];

		for (int i = 0; i < 12; i++) {
			kpCuspArray[i] = cuspArray[i];
		}

		return kpCuspArray;
	}

	private double[] getKpPlanetArrayForNakshtraNadi() {
		double[] kpPlanetDegreeArray = getKPPlanetDegreeArray();
		double[] kpPlanetArray = new double[9];

		for (int i = 1; i <= 9; i++) {
			kpPlanetArray[i - 1] = kpPlanetDegreeArray[i];
		}

		return kpPlanetArray;
	}

	private int getStarLord(double deg) {
		double d = deg;
		int a = (int) (d / 120.0);
		d -= a * 120.0;
		a = (int) (d * 3.0 / 40.0);
		return a;
	}

	private int getSubLord(double deg) {
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

	private int getSubSubLord(double deg) {
		double d = deg;
		int i = 0;
		int a = getStarLord(d);
		int b = getSubLord(d);

		d -= ((int) (d / 120.0)) * 120.0;
		d -= a * (40.0 / 3.0);
		d *= 9.0;

		int f = 0;
		while (b < 9) {
			i = a + f;
			if (i >= 9) {
				i -= 9;
			}
			if (KpConstants.y1[i] <= d) {
				d -= KpConstants.y1[i];
			} else {
				break;
			}
			f++;
		}

		d = (d / KpConstants.y1[b]) * (40.0 / 3.0);
		d *= 9.0;

		int c = 0;
		while (c < 9) {
			i = b + c;
			if (i >= 9) {
				i -= 9;
			}
			if (KpConstants.y1[i] <= d) {
				d -= KpConstants.y1[i];
			} else {
				break;
			}
			c++;
		}
		c = i;

		// RETURN SUB SUB LORD
		return c;
	}

	private boolean hasNumberInIntegerArray(int[] arr, int number) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == number) {
				return true;
			}
		}
		return false;
	}

	private int getPlanetInBhav(double cusp2, double cusp1, int cuspIndex, double plntDegree) {
		double temp2 = cusp2;
		int plantInBhava = -1;

		if (temp2 - cusp1 < 0) {
			temp2 += 360.00;
		}

		for (int i = 0; i <= 11; i++) {
			if (cusp1 < plntDegree && plntDegree < temp2) {
				plantInBhava = cuspIndex;
			}
			if (cusp1 < plntDegree + 360.0 && plntDegree + 360.0 < temp2) {
				plantInBhava = cuspIndex;
			}
		}

		return plantInBhava + 1;
	}

	private int getHouseOccupied(double planetDegree) {
		int plntNakLordInBhava = -1;
		try {
			int j;
			for (int i = 0; i <= 11; i++) {
				j = i + 1;
				if (j > 11)
					j = 0;
				plntNakLordInBhava = getPlanetInBhav(kpCuspDegree[j], kpCuspDegree[i], i, planetDegree);
				if (plntNakLordInBhava > 0) {
					break;
				}
			}
		} catch (Exception e) {
			// Handle exception if needed
		}
		return plntNakLordInBhava;
	}

	private int getRashiLordOfThePlanetWhereItPlaced(double plntDeg) {
		int rashi = (int) (plntDeg / 30) + 1;
		return KpConstants.RASHI_TO_PLANET[rashi];
	}

	private boolean checkPlanetIsStarLordOfOtherPlanet(int plntNumber) {
		for (int i = 0; i <= 8; i++) {
			if (i != plntNumber && plntNumber == KpConstants.PLANET_NAKSHTRA_LORD[getStarLord(planetDegree[i])]) {
				return true; // Return immediately when condition is met
			}
		}
		return false;
	}

	private boolean isPlanetInItsOwnStar(int plntNumber) {
		boolean isPlanetInOwnStar = false;
		int plntStarLord = KpConstants.PLANET_NAKSHTRA_LORD[getStarLord(planetDegree[plntNumber])];

		if (plntNumber == plntStarLord) {
			isPlanetInOwnStar = true;
		}

		return isPlanetInOwnStar;
	}

	private int[] getKCILType1(int plntNumber) {
		int[] kCil = new int[12];
		int[] cuspStarLord = new int[12];
		int[] cuspSubLord = new int[12];
		int[] cuspSubSubLord = new int[12];
		int index = -1;
		int plntStarlord;
		int rahuKetuRashiLord;

		// Initialize kCil array with -1
		for (int ind = 0; ind < kCil.length; ind++) {
			kCil[ind] = -1;
		}

		// Populate cusp arrays
		for (int i = 0; i <= 11; i++) {
			cuspStarLord[i] = KpConstants.PLANET_NAKSHTRA_LORD[getStarLord(kpCuspDegree[i])];
			cuspSubLord[i] = KpConstants.PLANET_NAKSHTRA_LORD[getSubLord(kpCuspDegree[i])];
			cuspSubSubLord[i] = KpConstants.PLANET_NAKSHTRA_LORD[getSubSubLord(kpCuspDegree[i])];
		}

		if (plntNumber == KpConstants.RAHU || plntNumber == KpConstants.KETU) {
			plntStarlord = KpConstants.PLANET_NAKSHTRA_LORD[getStarLord(planetDegree[plntNumber])];

			for (int i = 0; i <= 11; i++) {
				if (plntNumber == cuspStarLord[i] || plntNumber == cuspSubLord[i] || plntNumber == cuspSubSubLord[i]) {
					kCil[++index] = i + 1;
				}
			}

			int cuspNum = getHouseOccupied(planetDegree[plntNumber]);
			if (!hasNumberInIntegerArray(kCil, cuspNum)) {
				kCil[++index] = cuspNum;
			}

			rahuKetuRashiLord = getRashiLordOfThePlanetWhereItPlaced(planetDegree[plntNumber]);
			int slofRashiLord = KpConstants.PLANET_NAKSHTRA_LORD[getStarLord(planetDegree[rahuKetuRashiLord])];

			if ((plntNumber == KpConstants.RAHU || plntNumber == KpConstants.KETU)
					&& (plntStarlord == KpConstants.RAHU || plntStarlord == KpConstants.KETU)) {

				for (int i = 0; i <= 11; i++) {
					if (rahuKetuRashiLord == cuspStarLord[i] || rahuKetuRashiLord == cuspSubLord[i]
							|| rahuKetuRashiLord == cuspSubSubLord[i]) {
						if (!hasNumberInIntegerArray(kCil, i + 1)) {
							kCil[++index] = i + 1;
						}
					}
				}
			} else {
				for (int i = 0; i <= 11; i++) {
					if (slofRashiLord == cuspStarLord[i] || slofRashiLord == cuspSubLord[i]
							|| slofRashiLord == cuspSubSubLord[i]) {
						if (!hasNumberInIntegerArray(kCil, i + 1)) {
							kCil[++index] = i + 1;
						}
					}
				}
			}

			for (int i = 0; i <= 11; i++) {
				if (plntStarlord == cuspStarLord[i] || plntStarlord == cuspSubLord[i]
						|| plntStarlord == cuspSubSubLord[i]) {
					if (!hasNumberInIntegerArray(kCil, i + 1)) {
						kCil[++index] = i + 1;
					}
				}
			}
		} else {
			plntStarlord = KpConstants.PLANET_NAKSHTRA_LORD[getStarLord(planetDegree[plntNumber])];

			for (int i = 0; i <= 11; i++) {
				if (plntStarlord == cuspStarLord[i] || plntStarlord == cuspSubLord[i]
						|| plntStarlord == cuspSubSubLord[i]) {
					if (!hasNumberInIntegerArray(kCil, i + 1)) {
						kCil[++index] = i + 1;
					}
				}
			}

			if (plntStarlord == KpConstants.RAHU || plntStarlord == KpConstants.KETU) {
				int cuspNum = getHouseOccupied(planetDegree[plntStarlord]);
				if (!hasNumberInIntegerArray(kCil, cuspNum)) {
					kCil[++index] = cuspNum;
				}

				rahuKetuRashiLord = getRashiLordOfThePlanetWhereItPlaced(planetDegree[plntStarlord]);

				// Check Rashi Lord
				for (int i = 0; i <= 11; i++) {
					if (rahuKetuRashiLord == cuspStarLord[i] || rahuKetuRashiLord == cuspSubLord[i]
							|| rahuKetuRashiLord == cuspSubSubLord[i]) {
						if (!hasNumberInIntegerArray(kCil, i + 1)) {
							kCil[++index] = i + 1;
						}
					}
				}

				// Star Lord of Rahu/Ketu
				int slofRahuKetu = KpConstants.PLANET_NAKSHTRA_LORD[getStarLord(planetDegree[plntStarlord])];

				for (int i = 0; i <= 11; i++) {
					if (slofRahuKetu == cuspStarLord[i] || slofRahuKetu == cuspSubLord[i]
							|| slofRahuKetu == cuspSubSubLord[i]) {
						if (!hasNumberInIntegerArray(kCil, i + 1)) {
							kCil[++index] = i + 1;
						}
					}
				}
			}
		}
		return Utility.removeValueFromIntArray(-1, kCil);
	}

	private int[] getKCILType2(int plntNumber) {
		int[] kCil = new int[12];
		int[] cuspStarLord = new int[12];
		int[] cuspSubLord = new int[12];
		int[] cuspSubSubLord = new int[12];
		int index = -1;
		int rahuKetuRashiLord;

		// Initialize kCil array with -1
		for (int ind = 0; ind < kCil.length; ind++) {
			kCil[ind] = -1;
		}

		// Populate cusp arrays
		for (int i = 0; i <= 11; i++) {
			cuspStarLord[i] = KpConstants.PLANET_NAKSHTRA_LORD[getStarLord(kpCuspDegree[i])];
			cuspSubLord[i] = KpConstants.PLANET_NAKSHTRA_LORD[getSubLord(kpCuspDegree[i])];
			cuspSubSubLord[i] = KpConstants.PLANET_NAKSHTRA_LORD[getSubSubLord(kpCuspDegree[i])];
		}

		int plntSublord = KpConstants.PLANET_NAKSHTRA_LORD[getSubLord(planetDegree[plntNumber])];

		// Check for matches with cusp arrays
		for (int i = 0; i <= 11; i++) {
			if (plntSublord == cuspStarLord[i] || plntSublord == cuspSubLord[i] || plntSublord == cuspSubSubLord[i]) {
				if (!hasNumberInIntegerArray(kCil, i + 1)) {
					kCil[++index] = i + 1;
				}
			}
		}

		// Special case for Rahu and Ketu
		if (plntSublord == KpConstants.RAHU || plntSublord == KpConstants.KETU) {
			int cuspNum = getHouseOccupied(planetDegree[plntSublord]);
			if (!hasNumberInIntegerArray(kCil, cuspNum)) {
				kCil[++index] = cuspNum;
			}

			rahuKetuRashiLord = getRashiLordOfThePlanetWhereItPlaced(planetDegree[plntSublord]);

			for (int i = 0; i <= 11; i++) {
				if (rahuKetuRashiLord == cuspStarLord[i] || rahuKetuRashiLord == cuspSubLord[i]
						|| rahuKetuRashiLord == cuspSubSubLord[i]) {
					if (!hasNumberInIntegerArray(kCil, i + 1)) {
						kCil[++index] = i + 1;
					}
				}
			}

			int sublofRahuKetu = KpConstants.PLANET_NAKSHTRA_LORD[getSubLord(planetDegree[plntSublord])];

			for (int i = 0; i <= 11; i++) {
				if (sublofRahuKetu == cuspStarLord[i] || sublofRahuKetu == cuspSubLord[i]
						|| sublofRahuKetu == cuspSubSubLord[i]) {
					if (!hasNumberInIntegerArray(kCil, i + 1)) {
						kCil[++index] = i + 1;
					}
				}
			}
		}
		return Utility.removeValueFromIntArray(-1, kCil);
	}

	private int[] getKCILType3(int plntNumber) {
		int[] kCil = new int[12];
		int[] cuspStarLord = new int[12];
		int[] cuspSubLord = new int[12];
		int[] cuspSubSubLord = new int[12];
		int index = -1;

		// Initialize kCil array with -1
		for (int ind = 0; ind < kCil.length; ind++) {
			kCil[ind] = -1;
		}

		// Populate cusp arrays
		for (int i = 0; i <= 11; i++) {
			cuspStarLord[i] = KpConstants.PLANET_NAKSHTRA_LORD[getStarLord(kpCuspDegree[i])];
			cuspSubLord[i] = KpConstants.PLANET_NAKSHTRA_LORD[getSubLord(kpCuspDegree[i])];
			cuspSubSubLord[i] = KpConstants.PLANET_NAKSHTRA_LORD[getSubSubLord(kpCuspDegree[i])];
		}

		int plntSSublord = KpConstants.PLANET_NAKSHTRA_LORD[getSubSubLord(planetDegree[plntNumber])];

		// Check for matches with cusp arrays
		for (int i = 0; i <= 11; i++) {
			if (plntSSublord == cuspStarLord[i] || plntSSublord == cuspSubLord[i]
					|| plntSSublord == cuspSubSubLord[i]) {
				if (!hasNumberInIntegerArray(kCil, i + 1)) {
					kCil[++index] = i + 1;
				}
			}
		}

		// Special case for Rahu and Ketu
		if (plntSSublord == KpConstants.RAHU || plntSSublord == KpConstants.KETU) {
			int slOfRahuKetu = KpConstants.PLANET_NAKSHTRA_LORD[getStarLord(planetDegree[plntSSublord])];
			int cuspNum = getHouseOccupied(planetDegree[plntSSublord]);

			if (!hasNumberInIntegerArray(kCil, cuspNum)) {
				kCil[++index] = cuspNum;
			}

			int sslOfRahuKetu = KpConstants.PLANET_NAKSHTRA_LORD[getSubSubLord(planetDegree[plntSSublord])];

			if (plntNumber != KpConstants.RAHU && plntNumber != KpConstants.KETU) {
				for (int i = 0; i <= 11; i++) {
					if (sslOfRahuKetu == cuspStarLord[i] || sslOfRahuKetu == cuspSubLord[i]
							|| sslOfRahuKetu == cuspSubSubLord[i]) {
						if (!hasNumberInIntegerArray(kCil, i + 1)) {
							kCil[++index] = i + 1;
						}
					}
				}
				for (int i = 0; i <= 11; i++) {
					if (slOfRahuKetu == cuspStarLord[i] || slOfRahuKetu == cuspSubLord[i]
							|| slOfRahuKetu == cuspSubSubLord[i]) {
						if (!hasNumberInIntegerArray(kCil, i + 1)) {
							kCil[++index] = i + 1;
						}
					}
				}
			}
		}
		return Utility.removeValueFromIntArray(-1, kCil);
	}

	private int[] getKCILType4(int plntNumbere) {
		int[] kCil = new int[12];
		int[] cuspStarLord = new int[12];
		int[] cuspSubLord = new int[12];
		int[] cuspSubSubLord = new int[12];
		int indexType4 = -1;
		boolean hasPositionalStatus = false;

		boolean nSL = checkPlanetIsStarLordOfOtherPlanet(plntNumbere);
		boolean ows = isPlanetInItsOwnStar(plntNumbere);

		if (ows || !nSL) {
			hasPositionalStatus = true;
		}

		if (!hasPositionalStatus) {
			return null;
		} else {
			// Initialize the array with -1
			for (int ind = 0; ind < kCil.length; ind++) {
				kCil[ind] = -1;
			}

			// Populate cusp arrays
			for (int i = 0; i <= 11; i++) {
				cuspStarLord[i] = KpConstants.PLANET_NAKSHTRA_LORD[getStarLord(kpCuspDegree[i])];
				cuspSubLord[i] = KpConstants.PLANET_NAKSHTRA_LORD[getSubLord(kpCuspDegree[i])];
				cuspSubSubLord[i] = KpConstants.PLANET_NAKSHTRA_LORD[getSubSubLord(kpCuspDegree[i])];
			}

			// Find planets in cusp positions
			for (int i = 0; i <= 11; i++) {
				if (plntNumbere == cuspStarLord[i] || plntNumbere == cuspSubLord[i]
						|| plntNumbere == cuspSubSubLord[i]) {
					kCil[++indexType4] = i + 1;
				}
			}

			// Special case for Rahu and Ketu
			if (plntNumbere == KpConstants.RAHU || plntNumbere == KpConstants.KETU) {
				int cuspNum = getHouseOccupied(planetDegree[plntNumbere]);
				if (!hasNumberInIntegerArray(kCil, cuspNum)) {
					kCil[++indexType4] = cuspNum;
				}
			}

			return Utility.removeValueFromIntArray(-1, kCil);
		}
	}

	public ArrayList<KPCilSubSubBean> getCilSubSubData() {
		ArrayList<KPCilSubSubBean> arrayList = new ArrayList<>();
		String[] planetName = new ConstantHindi().planetName;

		for (int i = 0; i <= 8; i++) {
			arrayList.add(new KPCilSubSubBean(planetName[i + 1],
					getFormattedStringForNakshNadi(getKCILType1(KpConstants.PLANET_INDEX[i])),
					getFormattedStringForNakshNadi(getKCILType2(KpConstants.PLANET_INDEX[i])),
					getFormattedStringForNakshNadi(getKCILType3(KpConstants.PLANET_INDEX[i])),
					getFormattedStringForNakshNadi(getKCILType4(KpConstants.PLANET_INDEX[i]))));
		}

		return arrayList;
	}

	private String getFormattedStringForNakshNadi(int[] plaNadi) {
		StringBuilder planetName = new StringBuilder();

		if (plaNadi != null) {
			for (int i = 0; i < plaNadi.length; i++) {
				planetName.append(plaNadi[i]).append(",");
			}
		}

		// Remove the last comma if the string is not empty
		if (planetName.length() > 0) {
			planetName.setLength(planetName.length() - 1);
		}

		return planetName.toString();
	}

}
