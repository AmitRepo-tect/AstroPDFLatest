package com.sunastrix.astropdf.model;


public class CharAntaraDashaModel {
	String planetName;
	String startDate;
	String endDate;

	public CharAntaraDashaModel(String planetName, String startDate2, String endDate) {
		this.planetName = planetName;
		this.startDate = startDate2;
		this.endDate = endDate;
	}

	public String getPlanetName() {
		return planetName;
	}

	public void setPlanetName(String planetName) {
		this.planetName = planetName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
