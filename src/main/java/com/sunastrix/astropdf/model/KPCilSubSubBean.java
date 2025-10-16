package com.sunastrix.astropdf.model;
import java.io.Serializable;

public class KPCilSubSubBean implements Serializable {
    private final String planetName;
    private final String star;
    private final String sub;
    private final String subSub;
    private final String posStatus;

    public KPCilSubSubBean(String planetName, String star, String sub, String subSub, String posStatus) {
        this.planetName = planetName;
        this.star = star;
        this.sub = sub;
        this.subSub = subSub;
        this.posStatus = posStatus;
    }

    public String getPlanetName() {
        return planetName;
    }

    public String getStar() {
        return star;
    }

    public String getSub() {
        return sub;
    }

    public String getSubSub() {
        return subSub;
    }

    public String getPosStatus() {
        return posStatus;
    }

    @Override
    public String toString() {
        return "KPCilSubSubBean{" +
                "planetName='" + planetName + '\'' +
                ", star='" + star + '\'' +
                ", sub='" + sub + '\'' +
                ", subSub='" + subSub + '\'' +
                ", posStatus='" + posStatus + '\'' +
                '}';
    }
}
