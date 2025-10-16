package com.sunastrix.astropdf.model;

import java.io.Serializable;

public class KpRulingPlanetBean implements Serializable {
    private String label;
    private String value;

    public KpRulingPlanetBean(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "KpRulingPlanetBean{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
