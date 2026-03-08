package com.sunastrix.astropdf.calculation;

import java.util.ArrayList;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astroganitlib.model.BirthDetailBean;
import com.sunastrix.astroganitlib.model.DateTimeBean;
import com.sunastrix.astropdf.model.DasaBean;
import com.sunastrix.astropdf.util.ConstantHindi;

public class DashaCalculation {
	BirthDetailBean birthDetailBean;
	DesktopHoroNew desktopHoro;
	int[] duration = { 1, 2, 3, 4, 5, 6, 7, 8 };
	ConstantHindi constantHindi;
	private int[] yearOfVimPlanets = { 7, 20, 6, 10, 7, 18, 16, 19, 17 };
	private int nakshatra;
	private DasaBean[] mahaDasaArr;
	private DasaBean[] antaraDasaArr;
	private DasaBean[] pratyantraDasaArr;
	private DasaBean[] sookshamDasaArr;

	// private String[] arrVimDasa = { "Ke", "Ve", "Su", "Mo", "Ma", "Ra", "Ju",
	// "Sa", "Me" };
	private String[] arrVimDasa = { "dsrq", "'kqØ", "lw;Z", "paæ", "eaxy", "jkgq", "xq#", "'kfu", "cq/k" };

	public DashaCalculation(BirthDetailBean birthDetailBean, DesktopHoroNew desktopHoro) {
		this.birthDetailBean = birthDetailBean;
		this.desktopHoro = desktopHoro;
		constantHindi = new ConstantHindi();
	}

	public ArrayList<DasaBean> getVimDasaFormmattedData() {
		ArrayList<DasaBean> arrayList = new ArrayList<DasaBean>();
		DateTimeBean dateTimeBean = birthDetailBean.getDateTimeBean();
		double lastEndDate = Integer.parseInt(dateTimeBean.getYear())
				+ Integer.parseInt(dateTimeBean.getMonth()) / 12.00 + Integer.parseInt(dateTimeBean.getDay()) / 365.00;
		// _dDob = _year + _month / 12.00 + _day / 365.00;
		// double lDate=Double.parseDouble(lastEndDate);
		mahaDasaArr = getVimshttariMahaDasa(lastEndDate);
		for (int i = 0; i < mahaDasaArr.length; i++) {
			mahaDasaArr[i].setPlanetName(arrVimDasa[mahaDasaArr[i].getPlanetNo()]);
			mahaDasaArr[i].setDasaTimeStr(doubleToStringDateDDMMYY(mahaDasaArr[i].getDasaTime(), "/"));
			arrayList.add(mahaDasaArr[i]);
		}
		return arrayList;
	}

	public ArrayList<DasaBean> getAntaraDasaFormmattedData(int pos, int selectedVimPlanet) {
		ArrayList<DasaBean> arrayList = new ArrayList<DasaBean>();
		int plaNo = mahaDasaArr[selectedVimPlanet].getPlanetNo();
		antaraDasaArr = getAntaraDasa(pos, mahaDasaArr);
		for (int i = 0; i < antaraDasaArr.length; i++) {
			antaraDasaArr[i].setPlanetName(arrVimDasa[antaraDasaArr[i].getPlanetNo()]);
			antaraDasaArr[i]
					.setPlanetSubPlaName(arrVimDasa[plaNo] + " & " + arrVimDasa[antaraDasaArr[i].getPlanetNo()]);
			antaraDasaArr[i].setDasaTimeStr(doubleToStringDateDDMMYY(antaraDasaArr[i].getDasaTime(), "/"));
			arrayList.add(antaraDasaArr[i]);
		}
		return arrayList;
	}

	public ArrayList<DasaBean> getPratyantraDasaFormmattedData(int pos, int selectedVimPlanet) {
		ArrayList<DasaBean> arrayList = new ArrayList<DasaBean>();
		double lastEndDate = mahaDasaArr[selectedVimPlanet].getDasaTime();
		pratyantraDasaArr = getVimPratyantraDasa(pos, antaraDasaArr, lastEndDate);
		int plaNo = mahaDasaArr[selectedVimPlanet].getPlanetNo();
		int plaNo1 = antaraDasaArr[pos].getPlanetNo();
		for (int i = 0; i < pratyantraDasaArr.length; i++) {
			pratyantraDasaArr[i].setPlanetName(/*
												 * arrVimDasa[plaNo] + "/" + arrVimDasa[plaNo1] + "/" +
												 */ arrVimDasa[pratyantraDasaArr[i].getPlanetNo()]);
			pratyantraDasaArr[i].setDasaTimeStr(doubleToStringDateDDMMYY(pratyantraDasaArr[i].getDasaTime(), "/"));
			arrayList.add(pratyantraDasaArr[i]);
		}
		return arrayList;
	}

	private ArrayList<DasaBean> getSookshmaDasaFormmattedData(int pos, int selectedVimPlanet, int selectedAntPlanet) {
		ArrayList<DasaBean> arrayList = new ArrayList<DasaBean>();
		double lastEndDate;
		if (pos == 0) {
			if (selectedVimPlanet > 0) {
				lastEndDate = antaraDasaArr[selectedVimPlanet - 1].getDasaTime();
			} else {
				lastEndDate = mahaDasaArr[selectedVimPlanet].getDasaTime();
			}
		} else {
			lastEndDate = pratyantraDasaArr[pos - 1].getDasaTime();
		}
		sookshamDasaArr = getVimPratyantraDasa(pos, pratyantraDasaArr, lastEndDate);
		int plaNo = mahaDasaArr[selectedVimPlanet].getPlanetNo();
		int plaNo1 = antaraDasaArr[selectedAntPlanet].getPlanetNo();
		int plaNo2 = pratyantraDasaArr[pos].getPlanetNo();
		for (int i = 0; i < sookshamDasaArr.length; i++) {
			sookshamDasaArr[i].setPlanetName(arrVimDasa[plaNo] + "/" + arrVimDasa[plaNo1] + "/" + arrVimDasa[plaNo2]
					+ "/" + arrVimDasa[sookshamDasaArr[i].getPlanetNo()]);
			sookshamDasaArr[i].setDasaTimeStr(doubleToStringDateDDMMYY(sookshamDasaArr[i].getDasaTime(), "/"));
			arrayList.add(sookshamDasaArr[i]);
		}
		return arrayList;
	}

	private double[] getPlanetDegreeArray() {
		double[] planetDegreeArray = { desktopHoro.getAsc(), desktopHoro.getSun(), desktopHoro.getMoon(),
				desktopHoro.getMars(), desktopHoro.getMercury(), desktopHoro.getJupitor(), desktopHoro.getVenus(),
				desktopHoro.getSaturn(), desktopHoro.getRahu(), desktopHoro.getKetu(), desktopHoro.getUranus(),
				desktopHoro.getNeptune(), desktopHoro.getPluto() };
		return planetDegreeArray;
	}

	private double calculateBalance(double moonDeg) {
		double n0;
		double balance;
		int q;
		double d0 = moonDeg;
		d0 = 9.0 * fract(d0 / 120);
		n0 = fract(d0);
		q = (int) d0;
		balance = (1 - n0) * yearOfVimPlanets[q];
		return balance;
	}

	private double fract(double doubleValue) {
		return doubleValue - (int) doubleValue;
	}

	private int getNakshatra(double moon) {
		var nak = (int) (moon * 0.075);
		if (nak >= 18)
			nak -= 18;
		if (nak >= 9)
			nak -= 9;
		return nak;
	}

	DasaBean[] getVimshttariMahaDasa(double lastEndDate) {
		DasaBean[] vimshttariMahaDasa = new DasaBean[9];
		try {
			// val lastEndDate = 2020.8963470319634
			nakshatra = getNakshatra(getPlanetDegreeArray()[2]);
			vimshttariMahaDasa = getMahaDasa(lastEndDate);
		} catch (Exception e) {
		}
		return vimshttariMahaDasa;
	}

	private DasaBean[] getMahaDasa(double lastEndDate) {

		DasaBean[] dasaFirstLevel = new DasaBean[9];
		int index = 0;
		int plaNo = getNakshatra(getPlanetDegreeArray()[2]);
		double balance = calculateBalance(getPlanetDegreeArray()[2]);
		var endDate = lastEndDate + balance;
		do {
			dasaFirstLevel[index] = new DasaBean();
			dasaFirstLevel[index].setPlanetNo(plaNo);
			dasaFirstLevel[index].setDasaTime(endDate);
			plaNo++;
			if (plaNo >= 9)
				plaNo -= 9;
			index++;
			endDate += yearOfVimPlanets[plaNo];
		} while (index < 9);
		return dasaFirstLevel;
	}

	DasaBean[] getAntaraDasa(int pos, DasaBean[] preDasaArray) {
		int index = 0;
		DasaBean[] antaraDasaArray = new DasaBean[9];
		double periodSpan;
		double lastEndDate;
		if (pos == 0) {
			periodSpan = (double) yearOfVimPlanets[preDasaArray[0].getPlanetNo()];
			lastEndDate = preDasaArray[0].getDasaTime() - yearOfVimPlanets[preDasaArray[0].getPlanetNo()];
		} else {
			periodSpan = preDasaArray[pos].getDasaTime() - preDasaArray[pos - 1].getDasaTime();
			lastEndDate = preDasaArray[pos - 1].getDasaTime();
		}
		int plaNo = preDasaArray[pos].getPlanetNo();
		int total = 120 * yearOfVimPlanets[plaNo] / 120;
		if (periodSpan < 0) {
			periodSpan += (double) total;
			lastEndDate -= (double) total;
		}

		do {
			lastEndDate += periodSpan * yearOfVimPlanets[plaNo] / 120;
			antaraDasaArray[index] = new DasaBean();
			antaraDasaArray[index].setPlanetNo(plaNo);
			antaraDasaArray[index].setDasaTime(lastEndDate);
			plaNo++;
			if (plaNo >= 9)
				plaNo -= 9;
			index++;
		} while (index < 9);
		return antaraDasaArray;
	}

	DasaBean[] getVimPratyantraDasa(int pos, DasaBean[] objArrPrDasa, double lastdashatime) {
		int index = 0;
		int plaNo;
		DasaBean[] pratyantraDasaArray = new DasaBean[9];
		DasaBean[] tempArrPrDasa;
		double periodSpan;
		double lastEndDate;
		try {
			tempArrPrDasa = objArrPrDasa;
			if (pos == 0) {
				periodSpan = tempArrPrDasa[0].getDasaTime() - lastdashatime;
				lastEndDate = lastdashatime;
			} else {
				periodSpan = tempArrPrDasa[pos].getDasaTime() - tempArrPrDasa[pos - 1].getDasaTime();
				lastEndDate = tempArrPrDasa[pos - 1].getDasaTime();
			}
			plaNo = tempArrPrDasa[pos].getPlanetNo();
			int total = 120 * yearOfVimPlanets[plaNo] / 120;
			if (periodSpan < 0) {
				periodSpan += (double) total;
				lastEndDate -= (double) total;
			}
			do {
				lastEndDate += periodSpan * yearOfVimPlanets[plaNo] / 120;
				pratyantraDasaArray[index] = new DasaBean();
				pratyantraDasaArray[index].setPlanetNo(plaNo);
				pratyantraDasaArray[index].setDasaTime(lastEndDate);
				plaNo++;
				if (plaNo >= 9)
					plaNo -= 9;
				index++;
			} while (index < 9);
		} catch (Exception e) {
			throw e;
		}
		return pratyantraDasaArray;
	}

	String doubleToStringDateDDMMYY(double doubleDate, String forwardSlash) {
		StringBuffer sb = new StringBuffer();
		String sMM;
		String sDD;
		int dd;
		int yy = (int) doubleDate;
		int mm = (int) (fract(doubleDate) * 12) + 1;
		dd = (int) (fract(fract(doubleDate) * 12) * 31);
		if (dd == 0)
			dd = 1;

		if (dd > 28 && mm == 2) {
			mm += 1;
			if (isLeapYear(yy))
				dd -= 29;
			else
				dd -= 28;
		}

		// END
		sDD = String.valueOf(dd);
		sMM = String.valueOf(mm);
		// FOR DAY
		if (sDD.trim().length() > 1) {
			sb.append(sDD);
		} else {
			sb.append("0" + sDD);
		}

		// sb.append('/');// for day
		// sb.append(CUtils.getForwardSlash());// for day
		sb.append(forwardSlash); // for day

		// FOR MONTH
		if (sMM.trim().length() > 1) {
			sb.append(sMM);
		} else {
			sb.append("0" + sMM);
		}

		// sb.append('/');// for day
		// sb.append(CUtils.getForwardSlash());
		sb.append(forwardSlash);

		// FOR YEAR
		// sb.append('/');
		sb.append(yy);

		// return (dd + '/' + mm + '/' + yy);
		return sb.toString();
	}

	boolean isLeapYear(int year) {
		return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
	}
}
