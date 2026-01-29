package com.sunastrix.astropdf.model;

import java.io.Serializable;

public class BasicKundliPlanetSubData implements Serializable {
    private final String plaName;
    private final String plaDeg;
    private final String signLord;
    private final String nakshLord;
    private final String subLord;
    private final String subSubLord;

    public BasicKundliPlanetSubData(String plaName, String plaDeg, String signLord, String nakshLord, String subLord, String subSubLord) {
        this.plaName = plaName;
        this.plaDeg = plaDeg;
        this.signLord = signLord;
        this.nakshLord = nakshLord;
        this.subLord = subLord;
        this.subSubLord = subSubLord;
    }

    public String getPlaName() {
        return plaName;
    }

    public String getPlaDeg() {
        return plaDeg;
    }

    public String getSignLord() {
        return signLord;
    }

    public String getNakshLord() {
        return nakshLord;
    }

    public String getSubLord() {
        return subLord;
    }

    public String getSubSubLord() {
        return subSubLord;
    }

    @Override
    public String toString() {
        return "BasicKundliPlanetSubData{" +
                "plaName='" + plaName + '\'' +
                ", plaDeg='" + plaDeg + '\'' +
                ", signLord='" + signLord + '\'' +
                ", nakshLord='" + nakshLord + '\'' +
                ", subLord='" + subLord + '\'' +
                ", subSubLord='" + subSubLord + '\'' +
                '}';
    }
}
