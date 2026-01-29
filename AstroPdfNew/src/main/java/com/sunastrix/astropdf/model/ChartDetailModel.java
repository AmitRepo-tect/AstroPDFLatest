package com.sunastrix.astropdf.model;

public class ChartDetailModel {
	float startX;
	float startY;
	int[] planetArray;

	public ChartDetailModel(float startX, float startY, int[] planetArray) {
		this.startX = startX;
		this.startY = startY;
		this.planetArray = planetArray;
	}

	public float getStartX() {
		return startX;
	}

	public void setStartX(float startX) {
		this.startX = startX;
	}

	public float getStartY() {
		return startY;
	}

	public void setStartY(float startY) {
		this.startY = startY;
	}

	public int[] getPlanetArray() {
		return planetArray;
	}

	public void setPlanetArray(int[] planetArray) {
		this.planetArray = planetArray;
	}

}
