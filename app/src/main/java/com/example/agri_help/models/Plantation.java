package com.example.agri_help.models;

import com.example.agri_help.database.IPlantationDAO;
import com.example.agri_help.database.PlantationDAO;

public class Plantation {
    private int mArea;
    private String mPlantationID;
    private String mSownDate;
    private String mRegisteredUser;
    private String mPlantationStatus;
    private int growthStage;

    private IPlantationDAO mPlantationDAO = new PlantationDAO();
    // Add support for Longitude and Latitude

    public Plantation (int Area, String SownDate, String RegisteredUser)
    {
        this.mArea = Area;
        this.mSownDate = SownDate;
        this.mRegisteredUser = RegisteredUser;
        this.mPlantationStatus = "Healthy";
    }

    public Plantation (String PlantationID, int Area, String RegisteredUser, String PlantationStatus, String SownDate)
    {
        this.mPlantationID = PlantationID;
        this.mArea = Area;
        this.mSownDate = SownDate;
        this.mRegisteredUser = RegisteredUser;
        this.mPlantationStatus = PlantationStatus;
    }

    public int GetArea () { return mArea; }
    public String GetSownDate () { return mSownDate; }
    public String GetRegisteredUser () { return mRegisteredUser; }
    public String GetPlantationID () { return mPlantationID; }
    public String GetPlantationStatus() { return mPlantationStatus; }

    public void SetPlantationID (String PlantationID) { this.mPlantationID = PlantationID; }
}
