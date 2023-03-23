package com.example.agri_help.controllers;

import com.example.agri_help.database.IPlantationDAO;
import com.example.agri_help.database.PlantationDAO;
import com.example.agri_help.models.Plantation;

import java.util.ArrayList;

public class PlantationController {

    private IPlantationDAO plantationDAO = new PlantationDAO();

    public boolean AddPlantation (Plantation plantation, String username) {
        try {
            int existingPlantationsNumber = plantationDAO.getPlantations(username).size();

            String plantationID = username + "-" + (existingPlantationsNumber + 1);

            plantation.SetPlantationID(plantationID);

            return plantationDAO.insertPlantation(plantation);
        }
        catch (Exception exception) {
            return false;
        }
    }

    public ArrayList<Plantation> GetPlantations (String username) {
        try {
            return plantationDAO.getPlantations(username);
        }
        catch (Exception exception) {
            return null;
        }
    }
}
