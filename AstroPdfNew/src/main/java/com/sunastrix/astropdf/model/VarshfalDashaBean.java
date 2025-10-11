package com.sunastrix.astropdf.model;
import java.io.Serializable;

public class VarshfalDashaBean implements Serializable {

    private int planetNo;
    private int planetRashiBhav = -1;
    private String startTime;
    private String endTime;
    private String result = "";

    public VarshfalDashaBean(int planetNo,int planetRashiBhav, String startTime, String endTime) {
        this.planetNo = planetNo;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Optional constructor to set all fields
    public VarshfalDashaBean(int planetNo, int planetRashiBhav, String startTime, String endTime, String result) {
        this.planetNo = planetNo;
        this.planetRashiBhav = planetRashiBhav;
        this.startTime = startTime;
        this.endTime = endTime;
        this.result = result;
    }

    // Getters and Setters
    public int getPlanetNo() {
        return planetNo;
    }

    public void setPlanetNo(int planetNo) {
        this.planetNo = planetNo;
    }

    public int getPlanetRashiBhav() {
        return planetRashiBhav;
    }

    public void setPlanetRashiBhav(int planetRashiBhav) {
        this.planetRashiBhav = planetRashiBhav;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "VarshfalDashaBean{" +
                "planetNo=" + planetNo +
                ", planetRashiBhav=" + planetRashiBhav +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
