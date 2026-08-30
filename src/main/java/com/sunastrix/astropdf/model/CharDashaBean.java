package com.sunastrix.astropdf.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CharDashaBean implements Serializable {

    private String planetName;
    private int duration;
    private String startYear;
    private String endYear;
    private ArrayList<CharAntaraDashaBean> charAntaraDashaList;

    public CharDashaBean(String planetName, int duration, String startYear, String endYear,
                         ArrayList<CharAntaraDashaBean> charAntaraDashaList) {
        this.planetName = planetName;
        this.duration = duration;
        this.startYear = startYear;
        this.endYear = endYear;
        this.charAntaraDashaList = charAntaraDashaList;
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

    public ArrayList<CharAntaraDashaBean> getCharAntaraDashaList() {
        return charAntaraDashaList;
    }

    public void setCharAntaraDashaList(ArrayList<CharAntaraDashaBean> charAntaraDashaList) {
        this.charAntaraDashaList = charAntaraDashaList;
    }
}