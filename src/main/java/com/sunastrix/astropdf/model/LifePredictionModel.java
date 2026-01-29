package com.sunastrix.astropdf.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LifePredictionModel {
	
	@JsonProperty("detail")
	private String detail;

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	
}
