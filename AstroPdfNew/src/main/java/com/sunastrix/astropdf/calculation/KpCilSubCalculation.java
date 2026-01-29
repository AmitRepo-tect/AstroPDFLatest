package com.sunastrix.astropdf.calculation;

import java.util.ArrayList;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astropdf.model.KPCilSubBean;
import com.sunastrix.astropdf.util.KpConstants;
import com.sunastrix.astropdf.util.Utility;



public class KpCilSubCalculation {
	DesktopHoroNew desktopHoro;
	double[] planetDegree;
	double[] kpCuspDegree;

	public KpCilSubCalculation(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;

	}

	void setData() {
		planetDegree = getKpPlanetArrayForNakshtraNadi();
		kpCuspDegree = getKpCuspArrayForNakshtraNadi();
	}

	public ArrayList<KPCilSubBean> getKPCilSubData() {
		setData();
		return getCilSubData();
	}

	private double[] getKpCuspArrayForNakshtraNadi() {
		double[] cuspArray = getKpDegreeArray();
		double[] kpCuspArray = new double[12];
		for (int i = 0; i <= 11; i++) {
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

	public double[] getKpDegreeArray() {
		double[] kpCuspDegreeArray = { desktopHoro.getKPCuspLongitude(1), desktopHoro.getKPCuspLongitude(2),
				desktopHoro.getKPCuspLongitude(3), desktopHoro.getKPCuspLongitude(4), desktopHoro.getKPCuspLongitude(5),
				desktopHoro.getKPCuspLongitude(6), desktopHoro.getKPCuspLongitude(7), desktopHoro.getKPCuspLongitude(8),
				desktopHoro.getKPCuspLongitude(9), desktopHoro.getKPCuspLongitude(10),
				desktopHoro.getKPCuspLongitude(11), desktopHoro.getKPCuspLongitude(12) };

		return kpCuspDegreeArray;
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

	int getStarLord(double deg) {
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
			if (i >= 9)
				i -= 9;
			if (KpConstants.y1[i] <= d) {
				d -= KpConstants.y1[i];
			} else {
				break;
			}
			b++;
		}
		b = i;
		d = d / KpConstants.y1[b] * (40.0 / 3.0);
		d *= 9.0;
		int c = 0;
		while (c < 9) {
			i = b + c;
			if (i >= 9)
				i -= 9;
			if (KpConstants.y1[i] <= d) {
				d -= KpConstants.y1[i];
			} else {
				break;
			}
			c++;
		}

		return KpConstants.PLANET_NAKSHTRA_LORD[a];
	}

	private int getSubLord(double deg) {
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
		d = d / KpConstants.y1[b] * (40.0 / 3.0);
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

		return KpConstants.PLANET_NAKSHTRA_LORD[b];
	}

	private int getSubSubLord(double deg) {
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
		d = d / KpConstants.y1[b] * (40.0 / 3.0);
		d *= 9.0;
		var c = 0;
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

		return KpConstants.PLANET_NAKSHTRA_LORD[c];
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
		var temp2 = cusp2;
		var plantInBhava = -1;
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
				if (j > 11) {
					j = 0;
				}
				plntNakLordInBhava = getPlanetInBhav(kpCuspDegree[j], kpCuspDegree[i], i, planetDegree);
				if (plntNakLordInBhava > 0) {
					break;
				}
			}
		} catch (Exception e) {
		}
		return plntNakLordInBhava;
	}

	private int[] getKPCilType1(int cuspNumber) {
		int[] type1 = new int[12];

		// STEP-1:GET CUSP SUB LORD(PLANET)
		int cuspSubLord = getSubLord(kpCuspDegree[cuspNumber]);

		// STEP-2:GET STAR LORD OF THE PLANET THAT COMES IN STEP 1
		int plntStarLord = getStarLord(planetDegree[cuspSubLord]);

		// STEP-3 IDENTIFY THE CUSP THAT HAVE THE SAME SUB LORD PLANET AS PLANET CAME IN
		// STEP 2
		int index = -1;
		int[] arrayCuspSubLord = new int[12];
		for (int j = 0; j <= 11; j++) {
			arrayCuspSubLord[j] = getSubLord(kpCuspDegree[j]);
		}
		for (int i = 0; i <= 11; i++) {
			if (arrayCuspSubLord[i] == plntStarLord)
				type1[++index] = i + 1;
		}

		// STEP-4:GET THE SUB LORD OF THE PLANET THAT HAVE COME IN STEP 1
		int plntSubLord = getSubLord(planetDegree[cuspSubLord]);

		// STEP-5:IDENTIFY THE CUSP THAT HAVE THE SAME SUB LORD PLANET AS PLANET CAME IN
		// STEP 4

		for (int k = 0; k <= 11; k++) {
			if (arrayCuspSubLord[k] == plntSubLord)
				if (!hasNumberInIntegerArray(type1, k + 1)) {
					type1[++index] = k + 1;
				}
		}

		return Utility.removeValueFromIntArray(0, type1);
	}

	private int[] getKPCilType2(int cuspNumber) {
		int[] type2 = new int[13];

		// STEP-1:FIND THE SUB LORD OF THE CUSP
		int cuspSubLord = getSubLord(kpCuspDegree[cuspNumber]);
		int index = -1;
		int[] arrayCuspSubLord = new int[12];
		for (int j = 0; j <= 11; j++) {
			arrayCuspSubLord[j] = getSubLord(kpCuspDegree[j]);
		}
		for (int i = 0; i < kpCuspDegree.length; i++) {
			if (i != cuspNumber)
				if (arrayCuspSubLord[i] == cuspSubLord)
					type2[++index] = i + 1;
		}
		// STEP:2
		int plntStarLord = getStarLord(planetDegree[cuspSubLord]);
		for (int i = 0; i < kpCuspDegree.length; i++) {
			if (i != cuspNumber)
				if (arrayCuspSubLord[i] == plntStarLord)
					type2[++index] = i + 1;
		}
		int[] arrayCuspSubSubLord = new int[12];
		for (int j = 0; j <= 11; j++)
			arrayCuspSubSubLord[j] = getSubSubLord(kpCuspDegree[j]);
		for (int i = 0; i < kpCuspDegree.length; i++) {
			if (i != cuspNumber)
				if (arrayCuspSubSubLord[i] == plntStarLord)
					type2[++index] = i + 1;
		}
		return Utility.removeValueFromIntArray(0, type2);
	}

	private int[] getKPCilType3(int cuspNumber) {
		// STEP-1: GET THE SUB LORD OF THE CUSP
		int cuspSubLord = getSubLord(kpCuspDegree[cuspNumber]);

		// STEP-2: GET THE PLANETS WHICH HAVE THE SAME STAR LORD AS THE PLANET FOUND IN
		// STEP-1
		int tempStep2Index = -1;
		int[] tempStep2 = new int[12];

		// INITIALIZE THE ARRAY WITH VALUE 0
		for (int fill = 0; fill < tempStep2.length; fill++) {
			tempStep2[fill] = 0;
		}
		for (int i = 0; i < planetDegree.length; i++) {
			if (getStarLord(planetDegree[i]) == cuspSubLord) {
				tempStep2[++tempStep2Index] = i;
			}
		}

		if (tempStep2Index == -1) {
			return null;
		}

		int[] planetsInStep2 = new int[tempStep2Index + 1];
		tempStep2Index = -1;
		for (int j = 0; j < tempStep2.length; j++) {
			if (tempStep2[j] > 0) {
				planetsInStep2[++tempStep2Index] = tempStep2[j];
			}
		}

		// STEP-3: FIND THE CUSP WHICH HAS THE SAME SUB LORD AS THE PLANETS FOUND IN
		// STEP-2
		int tempStep3Index = -1;
		int[] tempStep3 = new int[12];

		for (int k = 0; k < planetsInStep2.length; k++) {
			for (int l = 0; l < kpCuspDegree.length; l++) {
				if (getSubLord(kpCuspDegree[l]) == planetsInStep2[k]) {
					tempStep3[++tempStep3Index] = l + 1;
				}
			}
		}

		// THIS CODE IS FOR REMOVING 0 FROM THE ARRAY
		int[] retType3 = new int[tempStep3Index + 1];
		int retType3Index = -1;
		for (int m = 0; m < tempStep3.length; m++) {
			if (tempStep3[m] > 0) {
				retType3[++retType3Index] = tempStep3[m];
			}
		}

		return Utility.removeValueFromIntArray(0, retType3);
	}

	private int getKPCilType4(int cuspNumber) {
		// STEP-1-:GET THE SUB LORD OF THE CUSP
		int cuspLord = getSubLord(kpCuspDegree[cuspNumber]);
		// STEP-2-:GET THE STAR LORD OF THE PLANET FIND IN STEP-1
		int starLord = getStarLord(planetDegree[cuspLord]);
		// FIND THE CUSP IN WHICH THE PLANET FOUND IN STEP-2, IS PLACED.
		return getHouseOccupied(planetDegree[starLord]);
	}

	ArrayList<KPCilSubBean> getCilSubData() {
		ArrayList<KPCilSubBean> arrayList = new ArrayList<KPCilSubBean>();
		try {
			for (int i = 0; i <= 11; i++) {
				int[] t1 = getKPCilType1(i);
				int[] t2 = getKPCilType2(i);
				int[] t3 = getKPCilType3(i);
				int[] type1 = null;
				int[] type2 = null;
				int[] type3 = null;
				if (t1 != null) {
					type1 = getSortedWithoutDuplicate(t1);
				}
				if (t2 != null) {
					type2 = getSortedWithoutDuplicate(t2);
				}
				if (t3 != null) {
					type3 = getSortedWithoutDuplicate(t3);
				}

				int type4 = getKPCilType4(i);
				arrayList.add(new KPCilSubBean(i + 1, getFormattedStringForNakshNadi(type1),
						getFormattedStringForNakshNadi(type2), getFormattedStringForNakshNadi(type3),
						String.valueOf(type4)));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return arrayList;
	}

	private int[] getSortedWithoutDuplicate(int[] arr) {
		int[] nadiArray = Utility.assendingSort(arr);
		int arrSize = Utility.removeDuplicateElements(nadiArray, nadiArray.length);
		int[] arrayAfterRemoveDuplicate = new int[arrSize];
		for (int i = 0; i < arrSize; i++) {
			arrayAfterRemoveDuplicate[i] = nadiArray[i];
		}
		return arrayAfterRemoveDuplicate;
	}

	private String getFormattedStringForNakshNadi(int[] plaNadi) {
		String planetName = "";
		if (plaNadi != null) {
			for (int i = 0; i < plaNadi.length; i++) {
				planetName = planetName + plaNadi[i] + ",";
			}
		}
		if (planetName.length() > 1) {
			planetName = planetName.substring(0, planetName.length() - 1);
		}
		return planetName;
	}
}
