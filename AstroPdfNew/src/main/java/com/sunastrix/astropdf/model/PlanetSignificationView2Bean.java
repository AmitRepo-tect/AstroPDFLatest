package com.sunastrix.astropdf.model;

import java.io.Serializable;

public class PlanetSignificationView2Bean implements Serializable {
    private String plaNo;
    private String l1;
    private String l2;
    private String l3;
    private String l4;
    private String strength;

    // Constructor
    public PlanetSignificationView2Bean(String plaNo, String l1, String l2, String l3, String l4, String strength) {
        this.plaNo = plaNo;
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
        this.l4 = l4;
        this.strength = strength;
    }

    // Getters
    public String getPlaNo() {
        return plaNo;
    }

    public String getL1() {
        return l1;
    }

    public String getL2() {
        return l2;
    }

    public String getL3() {
        return l3;
    }

    public String getL4() {
        return l4;
    }

    public String getStrength() {
        return strength;
    }

    // Setters
    public void setPlaNo(String plaNo) {
        this.plaNo = plaNo;
    }

    public void setL1(String l1) {
        this.l1 = l1;
    }

    public void setL2(String l2) {
        this.l2 = l2;
    }

    public void setL3(String l3) {
        this.l3 = l3;
    }

    public void setL4(String l4) {
        this.l4 = l4;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    // toString() method for easy printing
    @Override
    public String toString() {
        return "PlanetSignificationView2Bean{" +
                "plaNo='" + plaNo + '\'' +
                ", l1='" + l1 + '\'' +
                ", l2='" + l2 + '\'' +
                ", l3='" + l3 + '\'' +
                ", l4='" + l4 + '\'' +
                ", strength='" + strength + '\'' +
                '}';
    }
}
