package com.sunastrix.astropdf.calculation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astropdf.model.KPNakshatraNadiBean;
import com.sunastrix.astropdf.util.ConstantHindi;
import com.sunastrix.astropdf.util.KpConstants;
import com.sunastrix.astropdf.util.Utility;

public class KpNakshtraNadiCalculation {
	DesktopHoroNew desktopHoro;
	double[] kpPlanetDegree;
	double[] kpCuspDegree;

	public KpNakshtraNadiCalculation(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;

	}

	public ArrayList<KPNakshatraNadiBean> getNakshtraNadiData() {
		setData();
		return getNakshtraNadi();
	}

	void setData() {
		kpPlanetDegree = getKpPlanetDegreeForSigniVIew();
		kpCuspDegree = getKpCuspDegreeForSigniVIew();
	}

	private double[] getPlanetDegreeArray() {
		double[] planetDegreeArray = { desktopHoro.getAsc(), desktopHoro.getSun(), desktopHoro.getMoon(),
				desktopHoro.getMars(), desktopHoro.getMercury(), desktopHoro.getJupitor(), desktopHoro.getVenus(),
				desktopHoro.getSaturn(), desktopHoro.getRahu(), desktopHoro.getKetu(), desktopHoro.getUranus(),
				desktopHoro.getNeptune(), desktopHoro.getPluto() };
		System.out.println("Sun" + desktopHoro.getSun());
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

	private double[] getKpCuspDegreeForSigniVIew() {
		double[] cuspArray = getKpDegreeArray();
		double[] kpCuspArray = new double[12];

		for (int i = 0; i < 12; i++) {
			kpCuspArray[i] = cuspArray[i];
		}

		return kpCuspArray;
	}

	private double[] getKpPlanetDegreeForSigniVIew() {
		double[] kpPlanetDegreeArray = getKPPlanetDegreeArray();
		double[] kpPlanetArray = new double[9];

		for (int i = 1; i <= 9; i++) {
			kpPlanetArray[i - 1] = kpPlanetDegreeArray[i];
		}

		return kpPlanetArray;
	}

	private int[] getHousesOfPlanetRashi(int plntNumber) {
		int cuspRashi;
		int plntNakLordInBhava = -1;
		int[] returnHouseArray = null;
		int[] cuspOwnedByPlanetItself = new int[4];

		try {
			for (int k = 0; k < cuspOwnedByPlanetItself.length; k++) {
				cuspOwnedByPlanetItself[k] = 0;
			}

			int[] plntNakLordRashi = new int[2];
			plntNakLordRashi[0] = KpConstants.PLANET_RASHI[plntNumber][0];
			plntNakLordRashi[1] = KpConstants.PLANET_RASHI[plntNumber][1];

			for (int ral = 0; ral < plntNakLordRashi.length; ral++) {
				if (plntNakLordRashi[ral] > 0) {
					for (int cuspIndex = 0; cuspIndex < kpCuspDegree.length; cuspIndex++) {
						cuspRashi = (int) (kpCuspDegree[cuspIndex] / 30 + 1);
						if (plntNakLordRashi[ral] == cuspRashi) {
							cuspOwnedByPlanetItself[++plntNakLordInBhava] = cuspIndex + 1;
						}
					}
				}
			}

			returnHouseArray = new int[plntNakLordInBhava + 1];
			int index = -1;
			for (int len = 0; len < cuspOwnedByPlanetItself.length; len++) {
				if (cuspOwnedByPlanetItself[len] > 0) {
					returnHouseArray[++index] = cuspOwnedByPlanetItself[len];
				}
			}
		} catch (Exception e) {
			// Handle exception if necessary
		}
		return returnHouseArray;
	}

	private ArrayList<KPNakshatraNadiBean> getNakshtraNadi() {
		ArrayList<KPNakshatraNadiBean> nakshtraNadiList = new ArrayList<>();
		HashMap<Integer, int[]> hashMap = new HashMap<>();
		int[] nadiArrayAfterRemoveDuplicate;

		for (int i = 0; i <= 8; i++) {
			int[] nadiArray = Utility.assendingSort(getPlanetNadi(KpConstants.PLANET_INDEX[i]));
			int arrSize = Utility.removeDuplicateElements(nadiArray, nadiArray.length);
			nadiArrayAfterRemoveDuplicate = new int[arrSize];

			for (int j = 0; j < arrSize; j++) {
				nadiArrayAfterRemoveDuplicate[j] = nadiArray[j];
			}
			hashMap.put(i, nadiArrayAfterRemoveDuplicate);
		}

		for (int i = 0; i < hashMap.size(); i++) {
			int starLord = getPlanetStarLordNadi(i);
			int subLord = getPlanetSubLordNadi(KpConstants.PLANET_INDEX[i]);

			nakshtraNadiList.add(
					new KPNakshatraNadiBean(getFormattedStringForNakshNadi(KpConstants.PLANET_INDEX[i], hashMap.get(i)),
							getFormattedStringForNakshNadi(starLord, hashMap.get(starLord)),
							getFormattedStringForNakshNadi(subLord, hashMap.get(subLord))));
		}

		return nakshtraNadiList;
	}

	private int[] getPlanetNadi(int plntNumber) {
		int planetNadiLength = 0;
		int index = 0;

		// In which house planet is
		int houseOccupiedByPlanet = getHouseOfPlanet(kpPlanetDegree[plntNumber]);

		// In which house the planet rashi are
		int[] houseOccupiedByPlanetRashi;

		if (plntNumber == KpConstants.RAHU || plntNumber == KpConstants.KETU) {
			return getRahuOrKetuNadi(plntNumber);
		} else {
			houseOccupiedByPlanetRashi = getHousesOfPlanetRashi(plntNumber);
		}

		// Get the length of array
		for (int i = 0; i < houseOccupiedByPlanetRashi.length; i++) {
			if (houseOccupiedByPlanetRashi[i] > 0) {
				++planetNadiLength;
			}
		}

		int[] planetNadi = new int[planetNadiLength + 1];
		planetNadi[0] = houseOccupiedByPlanet;

		for (int i = 0; i < houseOccupiedByPlanetRashi.length; i++) {
			if (houseOccupiedByPlanetRashi[i] > 0) {
				planetNadi[++index] = houseOccupiedByPlanetRashi[i];
			}
		}

		return planetNadi;
	}

	private int getHouseOfPlanet(double planetDegree) {
		int plntNakLordInBhava = -1;
		try {
			for (int i = 0; i <= 11; i++) {
				int j = i + 1;
				if (j > 11)
					j = 0;

				plntNakLordInBhava = getBhavOfPlanet(kpCuspDegree[j], kpCuspDegree[i], i, planetDegree);

				if (plntNakLordInBhava > 0)
					break;
			}
		} catch (Exception e) {
			// Handle exception if necessary
		}
		return plntNakLordInBhava;
	}

	private int[] getRahuOrKetuNadi(int plntNumber) {
		int[] collection = new int[20];
		Arrays.fill(collection, -1);
		int index = -1;

		int plntInWhichRashi = getRashiLordOfThePlanetWhereItPlaced(kpPlanetDegree[plntNumber]);
		int[] nadi1 = getPlanetNadi(plntInWhichRashi);

		for (int i = 0; i < nadi1.length; i++) {
			if (nadi1[i] > 0)
				collection[++index] = nadi1[i];
		}

		int[] plntCon = getConjuction(plntNumber);
		if (plntCon != null) {
			for (int i = 0; i < plntCon.length; i++) {
				int[] arrayTemp = getPlanetNadi(plntCon[i]);
				for (int j = 0; j < arrayTemp.length; j++) {
					if (arrayTemp[j] > 0)
						collection[++index] = arrayTemp[j];
				}
			}
		}

		int[] plntAspect = getPlanetsHaveAspectOnRahuKetu(kpPlanetDegree[plntNumber]);
		if (plntAspect != null) {
			for (int i = 0; i < plntAspect.length; i++) {
				int[] arrayTempAspect = getPlanetNadi(plntAspect[i]);
				for (int j = 0; j < arrayTempAspect.length; j++) {
					if (arrayTempAspect[j] > 0)
						collection[++index] = arrayTempAspect[j];
				}
			}
		}

		int houseOccupied = getHouseOfPlanet(kpPlanetDegree[plntNumber]);
		int[] retVal = new int[index + 2];
		int indexTemp = -1;

		for (int i = 0; i < collection.length; i++) {
			if (collection[i] > 0)
				retVal[++indexTemp] = collection[i];
		}

		retVal[retVal.length - 1] = houseOccupied;
		return retVal;
	}

	private int[] getPlanetsHaveAspectOnRahuKetu(double degRahuKetu) {
		int[] aspectedPlanets = null;
		int[] tempArray = new int[8];
		Arrays.fill(tempArray, -1);
		int index = -1;
		int houseNumber;

		for (int pn = 0; pn <= 6; pn++) {
			houseNumber = distanceOfHousePlanet2PositedFromPlanet1(kpPlanetDegree[pn], degRahuKetu);

			if ((pn == KpConstants.SUN || pn == KpConstants.MOON || pn == KpConstants.MERCURY
					|| pn == KpConstants.VENUS) && houseNumber == 7) {
				tempArray[++index] = pn;
			} else if (pn == KpConstants.MARS && (houseNumber == 4 || houseNumber == 7 || houseNumber == 8)) {
				tempArray[++index] = pn;
			} else if (pn == KpConstants.JUPITER && (houseNumber == 5 || houseNumber == 7 || houseNumber == 9)) {
				tempArray[++index] = pn;
			} else if (pn == KpConstants.SAT && (houseNumber == 3 || houseNumber == 7 || houseNumber == 10)) {
				tempArray[++index] = pn;
			}
		}

		if (index > -1) {
			aspectedPlanets = new int[index + 1];
			int indexN = 0;
			for (int i = 0; i < aspectedPlanets.length; i++) {
				if (tempArray[i] > -1) {
					aspectedPlanets[indexN++] = tempArray[i];
				}
			}
		}

		return aspectedPlanets;
	}

	private int distanceOfHousePlanet2PositedFromPlanet1(double planet1, double planet2) {
		int distance = getRashiInPlanetPlaced(planet2) - getRashiInPlanetPlaced(planet1);
		if (distance < 0) {
			distance += 12;
		}
		return distance + 1;
	}

	private int getRashiInPlanetPlaced(double value) {
		return (int) (value / 30.00 + 1);
	}

	private int[] getConjuction(int plntNu) {
		int[] con = new int[10];
		Arrays.fill(con, -1);
		int[] retValu = null;
		int tempRashi;
		int index = -1;

		int plntRashi = (int) (kpPlanetDegree[plntNu] / 30) + 1;

		for (int i = 0; i < kpPlanetDegree.length; i++) {
			if (plntNu != i) {
				tempRashi = (int) (kpPlanetDegree[i] / 30) + 1;
				if (plntRashi == tempRashi) {
					++index;
					con[index] = i;
				}
			}
		}

		if (index > -1) {
			retValu = new int[index + 1];
			int indexN = 0;
			for (int j = 0; j < retValu.length; j++) {
				if (con[j] > -1) {
					retValu[indexN] = con[j];
					indexN++;
				}
			}
		}

		return retValu;
	}

	private int getRashiLordOfThePlanetWhereItPlaced(double plntDeg) {
		int rashi = (int) (plntDeg / 30 + 1);
		return KpConstants.RASHI_TO_PLANET[rashi];
	}

	private int getPlanetStarLordNadi(int plntNumber) {

		int starLord = getStarLord(kpPlanetDegree[plntNumber]);

		return starLord;
	}

	private int getPlanetSubLordNadi(int plntNumber) {
		return getSubLord(kpPlanetDegree[plntNumber]);
	}

	private int getStarLord(double deg) {
		double d = deg;
		int a = (int) (d / 120.0);
		d -= a * 120.0;
		a = (int) (d * 3.0 / 40.0);
		// System.out.println("Startlord---"+d+"--"+a);
		return KpConstants.PLANET_NAKSHTRA_LORD[a];
	}

	int getSubLord(double deg) {
		double d = deg;
		int i = 0;
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
			if (KpConstants.y1[i] <= d) {
				d -= KpConstants.y1[i];
			} else {
				break;
			}
			b++;
		}
		b = i;

		return KpConstants.PLANET_NAKSHTRA_LORD[b];
	}

	private String getFormattedStringForNakshNadi(int planet, int[] plaNadi) {
		String[] planetShortNames = new ConstantHindi().planetName;
		StringBuilder planetName = new StringBuilder(planetShortNames[planet + 1] + "&");
		if (plaNadi != null) {
			for (int i = 0; i < plaNadi.length; i++) {
				planetName.append(plaNadi[i]).append("]");
			}
		}
		if (planetName.length() > 1) {
			planetName.setLength(planetName.length() - 1); // Remove trailing comma
		}

		return planetName.toString();
	}

	private int getBhavOfPlanet(double cusp2, double cusp1, int cuspIndex, double plntDegree) {
		double temp2 = cusp2;
		int plaBhava = -1;

		if (temp2 - cusp1 < 0) {
			temp2 += 360.00;
		}

		for (int i = 0; i < 12; i++) {
			if (cusp1 < plntDegree && plntDegree < temp2) {
				plaBhava = cuspIndex;
			}
			if (cusp1 < plntDegree + 360.0 && plntDegree + 360.0 < temp2) {
				plaBhava = cuspIndex;
			}
		}

		return plaBhava + 1;
	}

}
