package com.sunastrix.astropdf.model;

import java.io.Serializable;

public class VarshfalPlanetData implements Serializable {

	private String plaName;
	private String sign;
	private String degree;

	public VarshfalPlanetData(String plaName, String sign, String degree) {
		this.plaName = plaName;
		this.sign = sign;
		this.degree = degree;
	}

	// Getters
	public String getPlaName() {
		return plaName;
	}

	public String getSign() {
		return sign;
	}

	public String getDegree() {
		return degree;
	}

	// Setters
	public void setPlaName(String plaName) {
		this.plaName = plaName;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@Override
	public String toString() {
		return "VarshfalPlanetData{" + "plaName='" + plaName + '\'' + ", sign='" + sign + '\'' + ", degree='" + degree
				+ '\'' + '}';
	}
}
