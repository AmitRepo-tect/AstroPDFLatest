package com.sunastrix.astropdf.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonalityPrediction {

    @JsonProperty("health_prediction")
    private String healthPrediction;

    @JsonProperty("temprament_and_personality_prediction")
    private String temperamentAndPersonalityPrediction;

    @JsonProperty("Physical_appearance_prediction")
    private String physicalAppearancePrediction;

    // Getters and Setters
    public String getHealthPrediction() {
        return healthPrediction;
    }

    public void setHealthPrediction(String healthPrediction) {
        this.healthPrediction = healthPrediction;
    }

    public String getTemperamentAndPersonalityPrediction() {
        return temperamentAndPersonalityPrediction;
    }

    public void setTemperamentAndPersonalityPrediction(String temperamentAndPersonalityPrediction) {
        this.temperamentAndPersonalityPrediction = temperamentAndPersonalityPrediction;
    }

    public String getPhysicalAppearancePrediction() {
        return physicalAppearancePrediction;
    }

    public void setPhysicalAppearancePrediction(String physicalAppearancePrediction) {
        this.physicalAppearancePrediction = physicalAppearancePrediction;
    }

    @Override
    public String toString() {
        return "PersonalityPrediction{" +
                "healthPrediction='" + healthPrediction + '\'' +
                ", temperamentAndPersonalityPrediction='" + temperamentAndPersonalityPrediction + '\'' +
                ", physicalAppearancePrediction='" + physicalAppearancePrediction + '\'' +
                '}';
    }
}
