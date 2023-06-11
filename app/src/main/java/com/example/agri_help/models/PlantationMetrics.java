package com.example.agri_help.models;

public class PlantationMetrics {
    public int plantationID;
    private float avgWeather;
    private float avgRainfallInWeek;
    private float currentSoilMoisture;
    private float optimumSoilMoisture;

    public PlantationMetrics (int plantationID, float avgWeather, float avgRainFall, float currentSoilMoisture, float optimumSoilMoisture){
        this.plantationID = plantationID;
        this.avgWeather = avgWeather;
        this.avgRainfallInWeek = avgRainFall;
        this.currentSoilMoisture = currentSoilMoisture;
        this.optimumSoilMoisture = optimumSoilMoisture;
    }

    public void setPlantationID(int plantationID) {
        this.plantationID = plantationID;
    }

    public void setAvgWeather(float avgWeather) {
        this.avgWeather = avgWeather;
    }

    public void setAvgRainfallInWeek(float avgRainfallInWeek) {
        this.avgRainfallInWeek = avgRainfallInWeek;
    }

    public void setCurrentSoilMoisture(float currentSoilMoisture) {
        this.currentSoilMoisture = currentSoilMoisture;
    }

    public void setOptimumSoilMoisture(float optimumSoilMoisture) {
        this.optimumSoilMoisture = optimumSoilMoisture;
    }

    public int getPlantationID() {
        return plantationID;
    }

    public float getAvgWeather() {
        return avgWeather;
    }

    public float getAvgRainfallInWeek() {
        return avgRainfallInWeek;
    }

    public float getCurrentSoilMoisture() {
        return currentSoilMoisture;
    }

    public float getOptimumSoilMoisture() {
        return optimumSoilMoisture;
    }
}
