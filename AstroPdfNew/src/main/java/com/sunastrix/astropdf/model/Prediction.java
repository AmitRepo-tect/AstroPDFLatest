package com.sunastrix.astropdf.model;

public class Prediction {
    private String prediction;

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    @Override
    public String toString() {
        return "Prediction{" +
                "prediction='" + prediction + '\'' +
                '}';
    }
}
