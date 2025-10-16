package com.sunastrix.astropdf.model;

import java.io.Serializable;

public class KPNakshatraNadiBean implements Serializable {
    private String planet;
    private String starLord;
    private String subLord;

    public KPNakshatraNadiBean(String planet, String starLord, String subLord) {
        this.planet = planet;
        this.starLord = starLord;
        this.subLord = subLord;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public String getStarLord() {
        return starLord;
    }

    public void setStarLord(String starLord) {
        this.starLord = starLord;
    }

    public String getSubLord() {
        return subLord;
    }

    public void setSubLord(String subLord) {
        this.subLord = subLord;
    }
}
