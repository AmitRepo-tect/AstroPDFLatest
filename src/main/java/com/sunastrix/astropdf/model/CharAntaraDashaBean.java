package com.sunastrix.astropdf.model;

import java.io.Serializable;

public class CharAntaraDashaBean implements Serializable {

    private String planetName;
    private String startDate;
    private String endDate;

    public CharAntaraDashaBean(String planetName, String startDate, String endDate) {
        this.planetName = planetName;
        this.startDate = startDate;
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