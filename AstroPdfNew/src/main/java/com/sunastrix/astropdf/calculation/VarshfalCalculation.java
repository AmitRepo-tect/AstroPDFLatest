package com.sunastrix.astropdf.calculation;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astroganitlib.model.BirthDetailBean;
import com.sunastrix.astroganitlib.model.DateTimeBean;
import com.sunastrix.astropdf.model.MunthaDesc;
import com.sunastrix.astropdf.model.Prediction;
import com.sunastrix.astropdf.model.VarshfalDashaBean;
import com.sunastrix.astropdf.model.VarshfalPlanetData;
import com.sunastrix.astropdf.util.ConstantHindi;
import com.sunastrix.astropdf.util.Utility;

public class VarshfalCalculation {
	private final int[] planetArrayForDasha = new int[] { 8, 5, 0, 1, 2, 7, 4, 6, 3 };
	private String startYear;
	private String startMonth;
	public String startDay;
	public String lang = "0";
	Utility utility = new Utility();

	String[] monthNames;

	public VarshfalCalculation() {
		ConstantHindi constantHindi = new ConstantHindi();
		monthNames = constantHindi.monthShortName;
	}

	public DesktopHoroNew getDesktopHoro(BirthDetailBean birthDetailBean) {
		DesktopHoroNew args1 = new DesktopHoroNew();
		try {
			args1.setBirthDetail(birthDetailBean);
			args1.initialize();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return args1;
	}

	public ArrayList<VarshfalDashaBean> getBhavishyafal(BirthDetailBean birthDetailBean, int varshfalYear, int yearNo) {
		int[] planetRashiArray = getLagnaKundliPlanetsRashiArray(
				getPlanetForVarshfal(getVarshfalBirthDetail(yearNo, birthDetailBean, varshfalYear)));
		double[] planetDegArray = getPlanetForVarshfal(birthDetailBean);

		return getMuddaDasha(planetDegArray, birthDetailBean, varshfalYear, yearNo, planetRashiArray);
	}

	public int getMunthaBhav(BirthDetailBean birthDetailBean, int varshfalYear, int yearNo) {
		int[] planetRashiArray = getLagnaKundliPlanetsRashiArray(
				getPlanetForVarshfal(getVarshfalBirthDetail(yearNo, birthDetailBean, varshfalYear)));
		double[] planetDegArray = getPlanetForVarshfal(birthDetailBean);
		int lagnaRashi = getRasiNumber(planetDegArray[0]) + 1;
		int lagna = planetRashiArray[12];
		int munthaRashi = (lagnaRashi + yearNo) % 12;
		if (munthaRashi == 0) {
			munthaRashi = 12;
		}
		int munthaBhav = 1;
		while (lagna != munthaRashi) {
			lagna++;
			munthaBhav++;
			if (lagna > 12) {
				lagna = 1;
			}
		}
		return munthaBhav;
	}

	public MunthaDesc getMunthaDesc(int munth) {
		MunthaDesc munthaDesc = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<MunthaDesc> munthaList = mapper.readValue(
					new File("src/main/resources/json/varshfal_muntha_bhav_desc.json"),
					new TypeReference<List<MunthaDesc>>() {
					});
			munthaDesc = munthaList.get(munth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return munthaDesc;
	}

	public int[] getVarshfalKundliPlanetsRashiArray(BirthDetailBean birthDetailBean, int varshfalYear, int yearNo) {
		double[] degArr = getPlanetForVarshfal(getVarshfalBirthDetail(yearNo, birthDetailBean, varshfalYear));
		return getLagnaKundliPlanetsRashiArray(degArr);
	}

	public ArrayList<VarshfalPlanetData> getVarshfalPlanetsData(BirthDetailBean birthDetailBean, int varshfalYear,
			int yearNo) {
		return getPlanetsData(getPlanetForVarshfal(getVarshfalBirthDetail(yearNo, birthDetailBean, varshfalYear)));
	}

	public double[] getPlanetForVarshfal(BirthDetailBean birthDetailBean) {
		double[] planetDegreeArray = new double[13];
		try {
			DesktopHoroNew args1 = getDesktopHoro(birthDetailBean);
			planetDegreeArray = new double[] { args1.getAsc(), args1.getSun(), args1.getMoon(), args1.getMars(),
					args1.getMercury(), args1.getJupitor(), args1.getVenus(), args1.getSaturn(), args1.getRahu(),
					args1.getKetu(), args1.getUranus(), args1.getNeptune(), args1.getPluto() };
		} catch (Exception e) {

		}
		return planetDegreeArray;
	}

	int[] getLagnaKundliPlanetsRashiArray(double[] planetDegree) {
		int[] planetRashi = new int[13];
		for (int i = 0; i < planetDegree.length - 1; i++) {
			planetRashi[i] = (int) (planetDegree[i + 1] / 30.00) + 1;
		}
		planetRashi[12] = (int) (planetDegree[0] / 30.00) + 1;
		return planetRashi;
	}

	ArrayList<VarshfalPlanetData> getPlanetsData(

			double[] planetDegree) {
		ConstantHindi constantHindi = new ConstantHindi();
		String degSign = "°";
		String minSign = "'";
		String secSign = "\"";
		String[] plaName = constantHindi.plaAndLagnaNames;
		String[] sign = constantHindi.rashiName;
		ArrayList<VarshfalPlanetData> arrayList = new ArrayList<VarshfalPlanetData>();
		for (int i = 0; i <= 12; i++) {
			arrayList.add(new VarshfalPlanetData(plaName[i], sign[getRasiNumber(planetDegree[i])],
					formatDMSInStringWithSign(planetDegree[i] % 30, degSign, minSign, secSign)));
		}
		return arrayList;
	}

	int getRasiNumber(double deg) {
		return (int) (deg / 30);
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

	ArrayList<VarshfalDashaBean> getMuddaDasha(double[] planetDegArray, BirthDetailBean birthDetailBean,
			int varshfalYear, int yearNo, int[] planetRashiArray) {

		int[] planetBhavArray = getRashiBhavArray(planetRashiArray[12]);
		ArrayList<VarshfalDashaBean> arrayList = new ArrayList<VarshfalDashaBean>();
		int birthMahaDasha = getMahaDasaAtTimeOfBirth(planetDegArray);

		Calendar calendar = getCalenderFromBirthDetail(birthDetailBean.getDateTimeBean(), varshfalYear);
		int muddaDashaPlanet = (yearNo + birthMahaDasha) % 9;

		String[] dateArray = calculateMuddaDashDate(birthDetailBean, varshfalYear, (yearNo + birthMahaDasha - 1) % 9);
		for (int i = 0; i <= 8; i++) {

			String startTime;
			if (i == 0) {
				startTime = startDay + " " + monthNames[Integer.parseInt(startMonth)] + "] " + startYear;
			} else {
				startTime = dateArray[i - 1];
			}
			String endTime = dateArray[i];
			int planetRashi = planetRashiArray[planetArrayForDasha[muddaDashaPlanet]];
			int index = -1;
			for (int j = 0; j < planetBhavArray.length; j++) {
				if (planetBhavArray[j] == planetRashi) { // use .equals() for objects
					index = j;
					break;
				}
			}
			int planetRashiBhav = index + 1;
			ArrayList<ArrayList<Prediction>> dashaArray = getDashaResultArray();

			arrayList.add(new VarshfalDashaBean(muddaDashaPlanet, planetRashiBhav, startTime, endTime,
					dashaArray.get(muddaDashaPlanet).get(planetRashiBhav - 1).getPrediction()));
			muddaDashaPlanet++;
			if (muddaDashaPlanet >= 9) {
				muddaDashaPlanet -= 9;
			}
		}

		return arrayList;
	}

	private int[] getRashiBhavArray(int lagna) {
		int[] array = new int[12];
		int lagna1 = lagna;
		for (int i = 0; i <= 11; i++) {
			if (lagna1 > 12) {
				lagna1 = 1;
			}
			array[i] = lagna1;
			lagna1++;
		}
		return array;
	}

	private int getMahaDasaAtTimeOfBirth(double[] plaDegArr) {
		return getNakshatra(plaDegArr[2]);
	}

	private int getNakshatra(double moon) {
		var nak = (int) (moon * 0.075);
		if (nak >= 18) {
			nak -= 18;
		}
		if (nak >= 9) {
			nak -= 9;
		}
		return nak;
	}

	private Calendar getCalenderFromBirthDetail(DateTimeBean dateTimeBean, int varshfalYear) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, varshfalYear);
		calendar.set(Calendar.MONTH, Integer.parseInt(dateTimeBean.getMonth()));
		calendar.set(Calendar.DATE, Integer.parseInt(dateTimeBean.getDay()));
		return calendar;
	}

	private String[] calculateMuddaDashDate(BirthDetailBean birthDetailBean, int varshfalYear,
			int getMahaDasaAtTimeOfBirth) {
		long intJD = calculateJdForVarshphal(birthDetailBean, varshfalYear);
		int[] arrVarshPhal = getVarshphal(birthDetailBean, varshfalYear, intJD);
		startYear = String.valueOf(arrVarshPhal[0]);
		startMonth = String.valueOf(arrVarshPhal[1]);
		startDay = String.valueOf(arrVarshPhal[2]);
		var mahaDasaAtTimeOfBirth = getMahaDasaAtTimeOfBirth;
		if (mahaDasaAtTimeOfBirth < 0) {
			mahaDasaAtTimeOfBirth = 0;
		}
		return calculateIstLevelMuddaVimshottari(mahaDasaAtTimeOfBirth, arrVarshPhal);
	}

	private String[] calculateIstLevelMuddaVimshottari(int getMahaDasaAtTimeOfBirth, int[] arrVarshPhal) {
		int[] g = { 1461, 438, 730, 511, 1314, 1168, 1387, 1241, 511 };
		Calendar calendar = Calendar.getInstance();

		String[] var2 = new String[9];
		calendar.set(arrVarshPhal[0], arrVarshPhal[1] - 1, arrVarshPhal[2], arrVarshPhal[3], arrVarshPhal[4]);
		int var3 = 0;
		int var1;
		int var4;
		int var5;
		int var6;
		String var7;
		var4 = getMahaDasaAtTimeOfBirth;
		while (var4 <= 8) {
			var1 = g[var4];
			calendar.add(Calendar.HOUR, var1);
			var1 = calendar.get(1);
			var5 = calendar.get(2) /* + 1 */;
			var6 = calendar.get(5);
			var7 = Utility.makelength(String.valueOf(var6), 2) + " " +monthNames[var5]
					+ "] " + var1;
			var2[var3] = var7;
			++var3;
			++var4;
		}
		if (getMahaDasaAtTimeOfBirth != 0) {
			var4 = 0;
			while (var4 < getMahaDasaAtTimeOfBirth) {
				var1 = g[var4];
				calendar.add(Calendar.HOUR, var1);
				var1 = calendar.get(1);
				var5 = calendar.get(2) /* + 1 */;
				var6 = calendar.get(5);
				var7 = Utility.makelength(String.valueOf(var6), 2) + " " + monthNames[var5]
						+ "] " + var1;
				var2[var3] = var7;
				++var3;
				++var4;
			}
		}
		return var2;
	}

	long calculateJdForVarshphal(BirthDetailBean birthDetailBean, int varshfalYear) {
		DateTimeBean dateTimeBean = birthDetailBean.getDateTimeBean();
		int var1 = (int) Integer.parseInt(dateTimeBean.getMonth());
		int var2 = Integer.parseInt(dateTimeBean.getYear())
				+ Math.abs(varshfalYear - Integer.parseInt(dateTimeBean.getYear()));
		if (var1 < 3) {
			var1 += 12;
			--var2;
		}
		int var3 = var2 / 100;

		var1 = (int) (30.6f * (float) (var1 + 1));
		return (long) (var2 * 365 + var2 / 4 + var1 + 2 - var3 + var3 / 4 + Integer.parseInt(dateTimeBean.getDay()));
	}

	int[] getVarshphal(BirthDetailBean birthDetailBean, int varYear, long varjd) {
		DateTimeBean dateTimeBean = birthDetailBean.getDateTimeBean();
		var j = julianDate(Integer.parseInt(dateTimeBean.getDay()), Integer.parseInt(dateTimeBean.getMonth()) + 1,
				Integer.parseInt(dateTimeBean.getYear()));
		j = (j + 4) % 7;
		long vj = varjd;
		double var4;
		int diff = Math.abs(varYear - Integer.parseInt(dateTimeBean.getYear()));
		double constant = getConstantForYears(diff);
		var4 = constant;
		Utility.getDHMS(constant);

		double var6 = Utility.makeDHMStoDouble(j, Integer.parseInt(dateTimeBean.getHrs()),
				Integer.parseInt(dateTimeBean.getMin()), Integer.parseInt(dateTimeBean.getSec()));

		// int[] var12=new int[4];
		int[] var12 = Utility.getDHMS(var4 + var6);
		if (var12[3] >= 60) {
			var12[2]++;
			var12[3] %= 60;
		}
		if (var12[2] >= 60) {
			var12[1]++;
			var12[2] %= 60;
		}
		if (var12[1] >= 24) {
			var12[0]++;
			var12[1] %= 24;
		}
		if (var12[0] >= 7) {
			var12[0] %= 7;
		}
		vj = (vj + 4L) % 7L;
		if (var12[0] >= 7) {
			var12[0] %= 7;
		}
		long var10;
		if ((long) var12[0] < vj) {
			var10 = (long) (var12[0] + 7) - vj;
		} else {
			var10 = (long) var12[0] - vj;
		}
		if (var10 > 3L) {
			var10 -= 7L;
		}
		int day = (int) (Integer.parseInt(dateTimeBean.getDay()) + var10);
		int hr = var12[1];
		int min = var12[2];
		int sec = var12[3];
		int year = Integer.parseInt(dateTimeBean.getYear())
				+ Math.abs(varYear - Integer.parseInt(dateTimeBean.getYear()));
		int month = Integer.parseInt(dateTimeBean.getMonth()) + 1;
		int[] arr = { year, month, day, hr, min, sec };
		return arr;
	}

	private double getConstantForYears(int varYear) {
		return (double) varYear * 1.256365740740741;
	}

	private int julianDate(int day, int month, int year) {
		int m = month;
		int y = year;
		if (m < 3) {
			m += 12;
			--y;
		}
		int var4 = y / 100;

		m = (int) (30.6f * (float) (m + 1));
		return y * 365 + y / 4 + m + 2 - var4 + var4 / 4 + day;
	}

	private ArrayList<ArrayList<Prediction>> getDashaResultArray() {
		ArrayList<ArrayList<Prediction>> arrayList = new ArrayList<ArrayList<Prediction>>();
		arrayList.add(getList("src/main/resources/json/varshfal_prediction_ketu_new" + ".json"));
		arrayList.add(getList("src/main/resources/json/varshfal_prediction_venus_new" + ".json"));
		arrayList.add(getList("src/main/resources/json/varshfal_prediction_sun_new" + ".json"));
		arrayList.add(getList("src/main/resources/json/varshfal_prediction_moon_new" + ".json"));
		arrayList.add(getList("src/main/resources/json/varshfal_prediction_mars_new" + ".json"));
		arrayList.add(getList("src/main/resources/json/varshfal_prediction_rahu_new" + ".json"));
		arrayList.add(getList("src/main/resources/json/varshfal_prediction_jupiter_new" + ".json"));
		arrayList.add(getList("src/main/resources/json/varshfal_prediction_saturn_new" + ".json"));
		arrayList.add(getList("src/main/resources/json/varshfal_prediction_mercury_new" + ".json"));
		return arrayList;
	}

	ArrayList<Prediction> getList(String path) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<Prediction> list = mapper.readValue(new File(path), new TypeReference<List<Prediction>>() {
			});

			ArrayList<Prediction> arrayList = new ArrayList<>(list);
			return arrayList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to read muhurat data", e);
		}
	}

	private BirthDetailBean getVarshfalBirthDetail(int yearNO, BirthDetailBean birthDetailBean, int varshfalYear) {
		if (yearNO == 0) {
			return birthDetailBean;
		}

		long intJD = (int) calculateJdForVarshphal(birthDetailBean, varshfalYear);
		int[] arrVarshPhal = getVarshphal(birthDetailBean, varshfalYear, (long) intJD);
		DateTimeBean dateTimeBean = new DateTimeBean(String.valueOf(arrVarshPhal[2]),
				String.valueOf(arrVarshPhal[1] - 1), String.valueOf(arrVarshPhal[0]), String.valueOf(arrVarshPhal[3]),
				String.valueOf(arrVarshPhal[4]), String.valueOf(arrVarshPhal[5]));

		return new BirthDetailBean(-1L, birthDetailBean.getName(), birthDetailBean.getSex(), dateTimeBean,
				birthDetailBean.getPlaceDetail(), birthDetailBean.getDst(), birthDetailBean.getAyanamsa(),
				birthDetailBean.getCharting(), birthDetailBean.getKphn(), birthDetailBean.getButton1(),
				birthDetailBean.getLanguageCode(), Calendar.getInstance().getTimeInMillis(),
				Calendar.getInstance().getTimeInMillis(), Calendar.getInstance().getTimeInMillis());

	}
}
