package com.example.agri_help.models;

public class DiseaseTest {
    private int diseaseTestID;
    private String result;
    private String plantationID;

    public DiseaseTest(int diseaseTestID, String plantationID, String result) {
        this.diseaseTestID = diseaseTestID;
        this.result = result;
        this.plantationID = plantationID;
    }

    public DiseaseTest (String plantationID, String result) {
        this.result = result;
        this.plantationID = plantationID;
    }

    public String getPlantationID() {
        return plantationID;
    }

    public void setPlantationID(String plantationID) {
        this.plantationID = plantationID;
    }

    public int getDiseaseTestID() {
        return diseaseTestID;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
