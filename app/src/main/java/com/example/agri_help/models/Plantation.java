package com.example.agri_help.models;

import com.example.agri_help.database.IPlantationDAO;
import com.example.agri_help.database.PlantationDAO;

public class Plantation {
    private int mArea;
    private String mSownDate;
    private String mRegisteredUser;
    private IPlantationDAO mPlantationDAO = new PlantationDAO();
    // Add support for Longitude and Latitude

    public Plantation (int Area, String SownDate, String RegisteredUser)
    {
        this.mArea = Area;
        this.mSownDate = SownDate;
        this.mRegisteredUser = RegisteredUser;
    }

    public int GetArea () { return mArea; }
    public String GetSownDate () { return mSownDate; }
    public String GetRegisteredUser () { return mRegisteredUser; }

    public boolean AddPlantation() {
        return mPlantationDAO.insertPlantation(new Plantation(10, "01/01/2023", "test_user"));
    }
}
