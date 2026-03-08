package com.sunastrix.astropdf.model;

import java.io.Serializable;

public class DasaBean implements Serializable {

	private int planetNo;
	private String planetName;
	private String planetSubPlaName;
	private int day;
	private int month;
	private int year;
	private double dasaTime;
	private String dasaTimeStr;

	// Default Constructor
	public DasaBean() {
	}

	// Parameterized Constructor
	public DasaBean(int planetNo, String planetName, int day, int month, int year, double dasaTime,
			String dasaTimeStr) {
		this.planetNo = planetNo;
		this.planetName = planetName;
		this.day = day;
		this.month = month;
		this.year = year;
		this.dasaTime = dasaTime;
		this.dasaTimeStr = dasaTimeStr;
	}

	// Getters and Setters

	public int getPlanetNo() {
		return planetNo;
	}

	public void setPlanetNo(int planetNo) {
		this.planetNo = planetNo;
	}

	public String getPlanetName() {
		return planetName;
	}

	public void setPlanetName(String planetName) {
		this.planetName = planetName;
	}

	public String getPlanetSubPlaName() {
		return planetSubPlaName;
	}

	public void setPlanetSubPlaName(String planetSubPlaName) {
		this.planetSubPlaName = planetSubPlaName;
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public double getDasaTime() {
		return dasaTime;
	}

	public void setDasaTime(double dasaTime) {
		this.dasaTime = dasaTime;
	}

	public String getDasaTimeStr() {
		return dasaTimeStr;
	}

	public void setDasaTimeStr(String dasaTimeStr) {
		this.dasaTimeStr = dasaTimeStr;
	}
}