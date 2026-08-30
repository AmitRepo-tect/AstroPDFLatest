package com.sunastrix.astropdf.calculation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astroganitlib.model.BirthDetailBean;
import com.sunastrix.astroganitlib.model.DateTimeBean;
import com.sunastrix.astropdf.model.CharAntaraDashaBean;
import com.sunastrix.astropdf.model.CharDashaBean;
import com.sunastrix.astropdf.util.ConstantHindi;

public class CharDashaCalculation {
	BirthDetailBean birthDetailBean;
	DesktopHoroNew desktopHoro;
	ConstantHindi constantHindi;
	private Map<Integer, int[]> charDasaSeqMap = new HashMap<>();
	private List<Integer> planetRashiArray = new ArrayList<>();
	private Map<Integer, String> rashiLordMap = new HashMap<>();
	private Map<String, Integer> planetRashiMap = new HashMap<>();
	private Map<Integer, Integer> orderOfCalculationMap = new HashMap<>();
	private Integer lagnaRashi;
	private String[] planetNameArray = { "Sun", "Moon", "Mars", "Mercury", "Jupiter", "Venus", "Saturn", "Rahu", "Ketu",
			"Uranus", "Neptune", "Pluto" };
	private String[] horoscopeName = { "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio",
			"Sagittarius", "Capricorn", "Aquarius", "Pisces" };
	private int[] charDasaSeqForAries = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
	private int[] charDasaSeqForTaurus = { 2, 1, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3 };
	private int[] charDasaSeqForGemini = { 3, 2, 1, 12, 11, 10, 9, 8, 7, 6, 5, 4 };
	private int[] charDasaSeqForCancer = { 4, 3, 2, 1, 12, 11, 10, 9, 8, 7, 6, 5 };
	private int[] charDasaSeqForLeo = { 7, 8, 9, 10, 11, 12, 1, 2, 3, 4, 5, 6 };
	private int[] charDasaSeqForVirgo = { 6, 7, 8, 9, 10, 11, 12, 1, 2, 3, 4, 5 };
	private int[] charDasaSeqForLibra = { 6, 7, 8, 9, 11, 12, 1, 2, 3, 4, 5, 6 };
	private int[] charDasaSeqForScorpio = { 8, 7, 6, 5, 4, 3, 2, 1, 12, 11, 10, 9 };
	private int[] charDasaSeqForSagittarius = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 12, 11, 10 };
	private int[] charDasaSeqForCapricorn = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 12, 11 };
	private int[] charDasaSeqForAquarius = { 11, 12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	private int[] charDasaSeqForPisces = { 12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };

	public CharDashaCalculation(BirthDetailBean birthDetailBean, DesktopHoroNew desktopHoro) {
		this.birthDetailBean = birthDetailBean;
		this.desktopHoro = desktopHoro;
		constantHindi = new ConstantHindi();
	}

	public void initialize() {
		setRashiLord();
		setOrderOfCalculation();
		setCharDasaSeqMap();
	}

	private void setCharDasaSeqMap() {
		charDasaSeqMap.put(1, charDasaSeqForAries);
		charDasaSeqMap.put(2, charDasaSeqForTaurus);
		charDasaSeqMap.put(3, charDasaSeqForGemini);
		charDasaSeqMap.put(4, charDasaSeqForCancer);
		charDasaSeqMap.put(5, charDasaSeqForLeo);
		charDasaSeqMap.put(6, charDasaSeqForVirgo);
		charDasaSeqMap.put(7, charDasaSeqForLibra);
		charDasaSeqMap.put(8, charDasaSeqForScorpio);
		charDasaSeqMap.put(9, charDasaSeqForSagittarius);
		charDasaSeqMap.put(10, charDasaSeqForCapricorn);
		charDasaSeqMap.put(11, charDasaSeqForAquarius);
		charDasaSeqMap.put(12, charDasaSeqForPisces);
	}

	private void setRashiLord() {
		rashiLordMap = new HashMap();
		rashiLordMap.put(1, "Mars");
		rashiLordMap.put(2, "Venus");
		rashiLordMap.put(3, "Mercury");
		rashiLordMap.put(4, "Moon");
		rashiLordMap.put(5, "Sun");
		rashiLordMap.put(6, "Mercury");
		rashiLordMap.put(7, "Venus");
		rashiLordMap.put(8, "Ketu");
		rashiLordMap.put(9, "Jupiter");
		rashiLordMap.put(10, "Saturn");
		rashiLordMap.put(11, "Saturn");
		rashiLordMap.put(12, "Jupiter");
	}

	private void setOrderOfCalculation() {
		orderOfCalculationMap = new HashMap();
		orderOfCalculationMap.put(1, 0);
		orderOfCalculationMap.put(2, 0);
		orderOfCalculationMap.put(3, 0);
		orderOfCalculationMap.put(4, 1);
		orderOfCalculationMap.put(5, 1);
		orderOfCalculationMap.put(6, 1);
		orderOfCalculationMap.put(7, 0);
		orderOfCalculationMap.put(8, 0);
		orderOfCalculationMap.put(9, 0);
		orderOfCalculationMap.put(10, 1);
		orderOfCalculationMap.put(11, 1);
		orderOfCalculationMap.put(12, 1);
	}

	private double[] getPlanetDegreeArray() {
		double[] planetDegreeArray = { desktopHoro.getAsc(), desktopHoro.getSun(), desktopHoro.getMoon(),
				desktopHoro.getMars(), desktopHoro.getMercury(), desktopHoro.getJupitor(), desktopHoro.getVenus(),
				desktopHoro.getSaturn(), desktopHoro.getRahu(), desktopHoro.getKetu(), desktopHoro.getUranus(),
				desktopHoro.getNeptune(), desktopHoro.getPluto() };
		return planetDegreeArray;
	}

	public ArrayList<CharDashaBean> getCharDashaData() {
		calculatePlanetRashi();
		DateTimeBean dateTimeBean = birthDetailBean.getDateTimeBean();
		String dob = dateTimeBean.getDay() + "/" + (Integer.parseInt(dateTimeBean.getMonth()) + 1) + "/"
				+ dateTimeBean.getYear();
		return calculateCharDashaDuration(dob);
	}

	private void calculatePlanetRashi() {
		int[] rashiInpla = desktopHoro.getPositionForShodasvarg(0);
		lagnaRashi = rashiInpla[0];
		for (int i = 0; i < planetNameArray.length; i++) {
			planetRashiMap.put(planetNameArray[i], rashiInpla[i + 1]);
			planetRashiArray.add(rashiInpla[i + 1]);
		}
	}

	private ArrayList<CharDashaBean> calculateCharDashaDuration(String dob) {
		String startDate = dob;
		String endDate;
		ArrayList<CharDashaBean> arrayList = new ArrayList<CharDashaBean>();
		int[] charDasaSeqArray = charDasaSeqMap.get(lagnaRashi);
		for (int i = 0; i <= 11; i++) {
			int rashi = charDasaSeqArray[i];
			int calcOrder = orderOfCalculationMap.get(rashi);
			String rashiLord = rashiLordMap.get(rashi);
			int rashiOfLord = planetRashiMap.get(rashiLord);
			if (rashi == 8) {
				rashiOfLord = calculateRashiForScorpioLord();
			} else if (rashi == 11) {
				rashiOfLord = calculateRashiForAquariusLord();
			}
			int duration = 0;

			if (rashi == rashiOfLord) {
				duration = 12;
			} else if (calcOrder == 0) {

				duration = clockwiseMove(rashi, rashiOfLord);

			} else {

				duration = antiClockwiseMove(rashi, rashiOfLord);

			}
			endDate = getEndDate(startDate, duration);
			int planet = charDasaSeqArray[i];
			String planetName = horoscopeName[planet - 1];
			ArrayList<CharAntaraDashaBean> charAntaraDashaList = getCharAntaraDasha(charDasaSeqArray[i], startDate,
					duration);
			arrayList.add(new CharDashaBean(planetName, duration, startDate, endDate,
					charAntaraDashaList = charAntaraDashaList));

			// Log.i("calc-Duration", "=$planetName,$duration, $startDate-$endDate")
			startDate = endDate;

		}
		return arrayList;
	}
	/*
	 * ArrayList<CharDashaBean> calculateCharDashaDuration(String dob) { String
	 * startDate = dob; String endDate; ArrayList<CharDashaBean> arrayList = new
	 * ArrayList<CharDashaBean>(); int[] charDasaSeqArray =
	 * charDasaSeqMap.get(lagnaRashi); for (int i = 0; i < 12; i++) { Integer rashi
	 * = charDasaSeqArray[i]; Integer calcOrder = orderOfCalculationMap.get(rashi);
	 * String rashiLord = rashiLordMap.get(rashi); Integer rashiOfLord =
	 * planetRashiMap.get(rashiLord); if (rashi == 8) { rashiOfLord =
	 * calculateRashiForScorpioLord(); } else if (rashi == 11) { rashiOfLord =
	 * calculateRashiForAquariusLord(); } var duration = 0;
	 * 
	 * if (rashi == rashiOfLord) { duration = 12; } else if (calcOrder == 0) { if
	 * (rashiOfLord != null) { if (rashi != null) { duration = clockwiseMove(rashi,
	 * rashiOfLord); } } } else { if (rashiOfLord != null) { if (rashi != null) {
	 * duration = antiClockwiseMove(rashi, rashiOfLord); } } } endDate =
	 * getEndDate(startDate, duration); int planet = charDasaSeqArray[i]; String
	 * planetName = horoscopeName[planet - 1]; ArrayList<CharAntaraDashaBean>
	 * charAntaraDashaList = getCharAntaraDasha(charDasaSeqArray[i], startDate,
	 * duration);
	 * 
	 * arrayList.add(new CharDashaBean(planetName, duration, startDate, endDate,
	 * charAntaraDashaList));
	 * 
	 * // Log.i("calc-Duration", "=$planetName,$duration, $startDate-$endDate")
	 * startDate = endDate;
	 * 
	 * } return arrayList; }
	 */

	private int calculateRashiForScorpioLord() {
		int lordRashi = -1;
		String rashiLord1 = "Ketu";
		String rashiLord2 = "Mars";
		Integer rashiOfLord1 = planetRashiMap.get(rashiLord1);
		Integer rashiOfLord2 = planetRashiMap.get(rashiLord2);
		if (rashiOfLord1 == 8 && rashiOfLord2 == 8) {
			lordRashi = rashiOfLord1;
		} else if (rashiOfLord1 == 8) {
			if (rashiOfLord2 != null) {
				lordRashi = rashiOfLord2;
			}
		} else if (rashiOfLord2 == 8) {
			if (rashiOfLord1 != null) {
				lordRashi = rashiOfLord1;
			}
		} else {
			var noOfPlanetInKetuRahi = 0;
			var noOfPlanetInMarsRahi = 0;
			for (int i = 0; i < planetRashiArray.size(); i++) {
				if (planetRashiArray.get(i) == rashiOfLord1) {
					noOfPlanetInKetuRahi++;
				} else if (planetRashiArray.get(i) == rashiOfLord2) {
					noOfPlanetInMarsRahi++;
				}
			}
			if (noOfPlanetInKetuRahi > noOfPlanetInMarsRahi) {
				if (rashiOfLord1 != null) {
					lordRashi = rashiOfLord1;
				}
			} else if (noOfPlanetInKetuRahi < noOfPlanetInMarsRahi) {
				if (rashiOfLord2 != null) {
					lordRashi = rashiOfLord2;
				}
			} else {
				double[] degreeArray = getPlanetDegreeArray();
				if (degreeArray[2] > degreeArray[8]) {
					if (rashiOfLord2 != null) {
						lordRashi = rashiOfLord2;
					}
				} else {
					if (rashiOfLord1 != null) {
						lordRashi = rashiOfLord1;
					}
				}
			}

		}

		return lordRashi;
	}

	private int calculateRashiForAquariusLord() {
		int lordRashi = -1;
		String rashiLord1 = "Rahu";
		String rashiLord2 = "Saturn";
		Integer rashiOfLord1 = planetRashiMap.get(rashiLord1);
		Integer rashiOfLord2 = planetRashiMap.get(rashiLord2);
		if (rashiOfLord1 == 11 && rashiOfLord2 == 11) {
			lordRashi = rashiOfLord1;
		} else if (rashiOfLord1 == 11) {
			if (rashiOfLord2 != null) {
				lordRashi = rashiOfLord2;
			}
		} else if (rashiOfLord2 == 11) {
			if (rashiOfLord1 != null) {
				lordRashi = rashiOfLord1;
			}
		} else {
			var noOfPlanetInRahuRashi = 0;
			var noOfPlanetInSaturnRashi = 0;
			for (int i = 0; i < planetRashiArray.size(); i++) {
				if (planetRashiArray.get(i) == rashiOfLord1) {
					noOfPlanetInRahuRashi++;
				} else if (planetRashiArray.get(i) == rashiOfLord2) {
					noOfPlanetInSaturnRashi++;
				}
			}
			if (noOfPlanetInRahuRashi > noOfPlanetInSaturnRashi) {
				if (rashiOfLord1 != null) {
					lordRashi = rashiOfLord1;
				}
			} else if (noOfPlanetInRahuRashi < noOfPlanetInSaturnRashi) {
				if (rashiOfLord2 != null) {
					lordRashi = rashiOfLord2;
				}
			} else {
				double[] degreeArray = getPlanetDegreeArray();
				if (degreeArray[6] > degreeArray[7]) {
					if (rashiOfLord2 != null) {
						lordRashi = rashiOfLord2;
					}
				} else {
					if (rashiOfLord1 != null) {
						lordRashi = rashiOfLord1;
					}
				}
			}

		}

		return lordRashi;
	}

	private int clockwiseMove(int rashi, int lordRashi) {
		int count = 0;
		boolean boolVal = true;
		int rashiIndex = rashi;
		while (boolVal) {
			if (rashiIndex == 12) {
				rashiIndex = 1;
			} else {
				rashiIndex++;
			}
			if (lordRashi == rashiIndex) {
				boolVal = false;
			}
			count++;
		}
		return count;
	}

	private int antiClockwiseMove(int rashi, int lordRashi) {
		int count = 0;
		boolean boolVal = true;
		int rashiIndex = rashi;
		while (boolVal) {
			if (rashiIndex == 1) {
				rashiIndex = 12;
			} else {
				rashiIndex--;
			}
			if (lordRashi == rashiIndex) {
				boolVal = false;
			}
			count++;
		}
		return count;
	}

	private String getEndDate(String startDate, int duration) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		try {
			Date date = dateFormat.parse(startDate);
			calendar.setTime(date);
			calendar.add(Calendar.YEAR, duration);
		} catch (Exception e) {

		}
		return dateFormat.format(calendar.getTime());
	}

	ArrayList<CharAntaraDashaBean> getCharAntaraDasha(int pla, String sDate, int duration) {
		ArrayList<CharAntaraDashaBean> arrayList = new ArrayList<CharAntaraDashaBean>();
		int[] charDasaSeqArray = charDasaSeqMap.get(pla);
		String startDate = sDate;
		String endDate = "";
		if (charDasaSeqArray != null) {
			for (int i = 0; i < charDasaSeqArray.length; i++) {
				int planet = charDasaSeqArray[i];
				String planetName = horoscopeName[planet - 1];
				endDate = getEndDateForCharAntaraDasha(startDate, duration);
				arrayList.add(new CharAntaraDashaBean(planetName, startDate, endDate));
				startDate = endDate;
			}
		}
		int planet = charDasaSeqArray[0];
		String planetName = horoscopeName[planet - 1];
		endDate = getEndDateForCharAntaraDasha(startDate, duration);
		arrayList.add(new CharAntaraDashaBean(planetName, startDate, endDate));

		return arrayList;

	}

	String getEndDateForCharAntaraDasha(String startDate, int duration) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		try {
			Date date = dateFormat.parse(startDate);
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, duration);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return dateFormat.format(calendar.getTime());
	}
}
