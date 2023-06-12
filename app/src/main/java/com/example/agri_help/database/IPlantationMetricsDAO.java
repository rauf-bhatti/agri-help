package com.example.agri_help.database;

import com.example.agri_help.models.PlantationMetrics;

public interface IPlantationMetricsDAO {
    public PlantationMetrics getPlantationMetric(String plantationID);
    boolean InsertPlantationMetric (PlantationMetrics p_metric);
    boolean UpdatePlantationMetric (PlantationMetrics p_metric);
}
