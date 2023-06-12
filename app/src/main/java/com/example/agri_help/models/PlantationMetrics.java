package com.example.agri_help.models;

public class PlantationMetrics {
    public String plantationID;
    private float avgWeather;
    private float avgRainfallInWeek;
    private float currentSoilMoisture;
    private float optimumSoilMoisture;
    private float Area;
    private float PumpSpec;
    private float longitude;
    private float latitude;

    public float getPumpSpec() {
        return PumpSpec;
    }

    public void setPumpSpec(float pumpSpec) {
        PumpSpec = pumpSpec;
    }

    public PlantationMetrics (String plantationID,
                              float avgWeather,
                              float avgRainFall,
                              float currentSoilMoisture,
                              float optimumSoilMoisture,
                              float Area,
                              float PumpSpec,
                              float longitude,
                              float latitude)
    {

        this.plantationID = plantationID;
        this.avgWeather = avgWeather;
        this.avgRainfallInWeek = avgRainFall;
        this.currentSoilMoisture = currentSoilMoisture;
        this.optimumSoilMoisture = optimumSoilMoisture;
        this.Area = Area;
        this.PumpSpec = PumpSpec;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public float getArea() {
        return Area;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setArea(float area) {
        Area = area;
    }

    public void setPlantationID(String plantationID) {
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

    public String getPlantationID() {
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
