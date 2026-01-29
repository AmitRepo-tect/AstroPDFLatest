package com.sunastrix.astropdf.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GemstoneItem {

    @JsonProperty("LifeStone")
    private String LifeStone;

    @JsonProperty("Finger")
    private String Finger;

    @JsonProperty("Metal")
    private String Metal;

    @JsonProperty("Qualities")
    private String Qualities;

    @JsonProperty("Mantra")
    private String Mantra;

    @JsonProperty("Devotee")
    private String Devotee;

    // Getters and Setters
    public String getLifeStone() {
        return LifeStone;
    }

    public void setLifeStone(String lifeStone) {
        LifeStone = lifeStone;
    }

    public String getFinger() {
        return Finger;
    }

    public void setFinger(String finger) {
        Finger = finger;
    }

    public String getMetal() {
        return Metal;
    }

    public void setMetal(String metal) {
        Metal = metal;
    }

    public String getQualities() {
        return Qualities;
    }

    public void setQualities(String qualities) {
        Qualities = qualities;
    }

    public String getMantra() {
        return Mantra;
    }

    public void setMantra(String mantra) {
        Mantra = mantra;
    }

    public String getDevotee() {
        return Devotee;
    }

    public void setDevotee(String devotee) {
        Devotee = devotee;
    }

    @Override
    public String toString() {
        return "GemstoneItem{" +
                "LifeStone='" + LifeStone + '\'' +
                ", Finger='" + Finger + '\'' +
                ", Metal='" + Metal + '\'' +
                ", Qualities='" + Qualities + '\'' +
                ", Mantra='" + Mantra + '\'' +
                ", Devotee='" + Devotee + '\'' +
                '}';
    }
}
