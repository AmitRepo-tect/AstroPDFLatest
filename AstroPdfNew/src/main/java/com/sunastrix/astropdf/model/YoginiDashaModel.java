package com.sunastrix.astropdf.model;

import java.util.ArrayList;

public class YoginiDashaModel {
	String planetName;
	int duration;
	String startYear;
	String endYear;
	ArrayList<CharAntaraDashaModel> antaraDashaList;

	public YoginiDashaModel(String planetName, int duration, String startYear, String endYear,
			ArrayList<CharAntaraDashaModel> antaraDashaList) {
		this.planetName = planetName;
		this.duration = duration;
		this.startYear = startYear;
		this.endYear = endYear;
		this.antaraDashaList = antaraDashaList;

	}

	public String getPlanetName() {
		return planetName;
	}

	public void setPlanetName(String planetName) {
		this.planetName = planetName;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public ArrayList<CharAntaraDashaModel> getAntaraDashaList() {
		return antaraDashaList;
	}

	public void setAntaraDashaList(ArrayList<CharAntaraDashaModel> antaraDashaList) {
		this.antaraDashaList = antaraDashaList;
	}

}
