package com.sunastrix.astropdf.model;

import java.io.Serializable;

public class PlanetSignificationBean implements Serializable {
    private String planetName;
    private String houseSignified;

    public PlanetSignificationBean(String planetName, String houseSignified) {
        this.planetName = planetName;
        this.houseSignified = houseSignified;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public String getHouseSignified() {
        return houseSignified;
    }

    public void setHouseSignified(String houseSignified) {
        this.houseSignified = houseSignified;
    }
}
