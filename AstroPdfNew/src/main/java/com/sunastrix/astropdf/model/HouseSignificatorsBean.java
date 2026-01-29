package com.sunastrix.astropdf.model;

import java.io.Serializable;

public class HouseSignificatorsBean implements Serializable {
    private int houseNo;
    private String significator;

    public HouseSignificatorsBean(int houseNo, String significator) {
        this.houseNo = houseNo;
        this.significator = significator;
    }

    public int getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(int houseNo) {
        this.houseNo = houseNo;
    }

    public String getSignificator() {
        return significator;
    }

    public void setSignificator(String significator) {
        this.significator = significator;
    }

    @Override
    public String toString() {
        return "HouseSignificatorsBean{" +
                "houseNo=" + houseNo +
                ", significator='" + significator + '\'' +
                '}';
    }
}
