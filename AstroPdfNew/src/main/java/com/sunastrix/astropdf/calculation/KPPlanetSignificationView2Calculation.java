package com.sunastrix.astropdf.calculation;

import java.util.ArrayList;
import java.util.Arrays;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astropdf.model.PlanetSignificationView2Bean;
import com.sunastrix.astropdf.util.KpConstants;
import com.sunastrix.astropdf.util.Utility;



public class KPPlanetSignificationView2Calculation {

	DesktopHoroNew desktopHoro;
	double[] kpPlanetDegree;
	double[] kpCuspDegree;

	public KPPlanetSignificationView2Calculation(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;

	}

	public ArrayList<PlanetSignificationView2Bean> getPlanetSignifiactionView2Data() {
		setData();
		return getPlanetSignifiactionView2();
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

	public int[] getHousesInPlanetRashi(int plntNumber) {
		int cuspRashi;
		int plntNakLordInBhava = -1;
		int[] returnHouseArray = null;
		int[] cuspOwnedByPlanetItself = new int[4];

		try {
			Arrays.fill(cuspOwnedByPlanetItself, 0);

			int[] plntNakLordRashi = new int[2];
			plntNakLordRashi[0] = KpConstants.PLANET_RASHI[plntNumber][0];
			plntNakLordRashi[1] = KpConstants.PLANET_RASHI[plntNumber][1];

			for (int ral = 0; ral < plntNakLordRashi.length; ral++) {
				if (plntNakLordRashi[ral] > 0) {
					for (int cuspIndex = 0; cuspIndex < kpCuspDegree.length; cuspIndex++) {
						cuspRashi = (int) (kpCuspDegree[cuspIndex] / 30) + 1;
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
			e.printStackTrace(); // Print exception for debugging
		}
		return returnHouseArray;
	}

	private int getPlanetSignificationLevel1(int plnt) {
		int plntNakLordInBhava = -1;
		try {
			int plntNakLord = getStarLord(kpPlanetDegree[plnt]);
			int j;
			for (int i = 0; i <= 11; i++) {
				j = i + 1;
				if (j > 11) {
					j = 0;
				}
				plntNakLordInBhava = getBhavOfPlanet(kpCuspDegree[j], kpCuspDegree[i], i, kpPlanetDegree[plntNakLord]);
				if (plntNakLordInBhava > 0) {
					break;
				}
			}
		} catch (Exception e) {
		}
		return plntNakLordInBhava;
	}

	private int getPlanetSignificationLevel2(double planetDegree) {
		var plntInBhava = -1;
		try {
			int j;
			for (int i = 0; i <= 11; i++) {
				j = i + 1;
				if (j > 11) {
					j = 0;
				}
				plntInBhava = getBhavOfPlanet(kpCuspDegree[j], kpCuspDegree[i], i, planetDegree);
				if (plntInBhava > 0) {
					break;
				}
			}
		} catch (Exception e) {
		}
		return plntInBhava;
	}

	private int[] getPlanetSignificationLevel3(double planetDegree) {
		int[] starLordInCusp = null;
		try {
			int plntNakLord = getStarLord(planetDegree);
			starLordInCusp = getHousesInPlanetRashi(plntNakLord);
		} catch (Exception e) {
		}
		return starLordInCusp;
	}

	private int[] getPlanetSignificationLevel4(int plntName) {
		int[] cuspOwnedByPlanetItself = null;
		try {
			cuspOwnedByPlanetItself = getHousesInPlanetRashi(plntName);
		} catch (Exception e) {
		}
		return cuspOwnedByPlanetItself;
	}

	ArrayList<PlanetSignificationView2Bean> getPlanetSignifiactionView2() {
		ArrayList<PlanetSignificationView2Bean> arrayList = new ArrayList<PlanetSignificationView2Bean>();
		int[] plntStrength = getPlanetStrength();
		for (int i = 0; i < KpConstants.PLANET_INDEX.length; i++) {
			arrayList.add(new PlanetSignificationView2Bean(String.valueOf(i + 1),
					String.valueOf(getPlanetSignificationLevel1(KpConstants.PLANET_INDEX[i])),
					String.valueOf(getPlanetSignificationLevel2(kpPlanetDegree[KpConstants.PLANET_INDEX[i]])),
					convertIntArrayIntoString(getSortedWithoutDuplicate(
							getPlanetSignificationLevel3(kpPlanetDegree[KpConstants.PLANET_INDEX[i]]))),
					convertIntArrayIntoString(
							getSortedWithoutDuplicate(getPlanetSignificationLevel4(KpConstants.PLANET_INDEX[i]))),
					String.valueOf(plntStrength[0])));
		}
		return arrayList;
	}

	private int getStarLord(double deg) {
		double d = deg;
		int a = (int) (d / 120.0);
		d -= a * 120.0;
		a = (int) (d * 3.0 / 40.0);
		return a;
	}

	public int getBhavOfPlanet(double cusp2, double cusp1, int cuspIndex, double plntDegree) {
		double temp2 = cusp2;
		int plaBhava = -1;

		if (temp2 - cusp1 < 0) {
			temp2 += 360.00;
		}

		for (int i = 0; i <= 11; i++) {
			if (cusp1 < plntDegree && plntDegree < temp2) {
				plaBhava = cuspIndex;
			}
			if (cusp1 < plntDegree + 360.0 && plntDegree + 360.0 < temp2) {
				plaBhava = cuspIndex;
			}
		}

		return plaBhava + 1;
	}

	public int[] getPlanetStrength() {
		int[] nakshtraLord = new int[9];
		int[] plntStrength = new int[9];

		for (int i = 0; i < 9; i++) {
			nakshtraLord[i] = getStarLord(kpPlanetDegree[i]);
		}

		for (int i = 0; i < 9; i++) {
			plntStrength[i] = 1;
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (i == j) {
					continue;
				} else if (KpConstants.PLANET_INDEX[i] == nakshtraLord[j]) {
					plntStrength[i] = 0;
				}
			}
		}
		return plntStrength;
	}

	public String convertIntArrayIntoString(int[] intArray) {
		StringBuilder str = new StringBuilder();

		if (intArray != null) {
			for (int element : intArray) {
				str.append(element).append(", ");
			}
			if (str.length() > 1) {
				str.setLength(str.length() - 2); // Remove the last comma and space
			}
		}

		return str.toString();
	}

	public int[] getSortedWithoutDuplicate(int[] arr) {
		int[] nadiArray = Utility.assendingSort(arr);
		int arrSize = Utility.removeDuplicateElements(nadiArray, nadiArray.length);
		int[] arrayAfterRemoveDuplicate = new int[arrSize];

		for (int i = 0; i < arrSize; i++) {
			arrayAfterRemoveDuplicate[i] = nadiArray[i];
		}

		return arrayAfterRemoveDuplicate;
	}

}
