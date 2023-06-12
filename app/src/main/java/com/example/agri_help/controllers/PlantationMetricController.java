package com.example.agri_help.controllers;

import com.example.agri_help.database.IPlantationMetricsDAO;

import com.example.agri_help.database.PlantationMetricsDAO;
import com.example.agri_help.models.Plantation;
import com.example.agri_help.models.PlantationMetrics;

import java.util.ArrayList;

public class PlantationMetricController {
    private IPlantationMetricsDAO plantationMetricsDAO = new PlantationMetricsDAO();

    public PlantationMetrics GetMetricForPlantation (String plantationID) {
        try {
            return plantationMetricsDAO.getPlantationMetric(plantationID);
        }
        catch (Exception exception) {
            return null;
        }
    }
    public boolean UpdateMetricForPlantation (PlantationMetrics p_metric){
        try {
            return plantationMetricsDAO.UpdatePlantationMetric(p_metric);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return false;
    }
}

