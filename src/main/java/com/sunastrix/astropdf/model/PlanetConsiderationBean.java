package com.sunastrix.astropdf.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlanetConsiderationBean {
	@JsonProperty("consideration")
	private String consideration;

	public String getConsideration() {
		return consideration;
	}

	public void setConsideration(String consideration) {
		this.consideration = consideration;
	}

}
