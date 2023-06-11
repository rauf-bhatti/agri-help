package com.example.agri_help.database;

import com.example.agri_help.models.PlantationMetrics;

public interface IPlantationMetricsDAO {
    public PlantationMetrics getPlantationMetric(int plantationID);
}
