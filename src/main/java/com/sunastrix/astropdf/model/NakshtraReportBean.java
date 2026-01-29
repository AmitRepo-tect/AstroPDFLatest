package com.sunastrix.astropdf.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NakshtraReportBean {
	@JsonProperty("nakshname")
	private String nakshname;
	@JsonProperty("reports")
	private String reports;
	@JsonProperty("education")
	private String education;
	@JsonProperty("family")
	private String family;
	public String getNakshname() {
		return nakshname;
	}
	public void setNakshname(String nakshname) {
		this.nakshname = nakshname;
	}
	public String getReports() {
		return reports;
	}
	public void setReports(String reports) {
		this.reports = reports;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	
}
