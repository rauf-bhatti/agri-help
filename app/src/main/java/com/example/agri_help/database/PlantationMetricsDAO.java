package com.example.agri_help.database;

import com.example.agri_help.models.Plantation;
import com.example.agri_help.models.PlantationMetrics;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlantationMetricsDAO implements IPlantationMetricsDAO {
    private final CloudDAL mCloudDAL = new CloudDAL();

    @Override
    public PlantationMetrics getPlantationMetric(int plantationID) {
        PlantationMetrics p_Metric = new PlantationMetrics(plantationID, -1f, -1f, -1f, -1f);
        try {
            ResultSet resultSet = mCloudDAL.get("SELECT * FROM PlantationMetrics WHERE plantation_id = " + plantationID + "");
            while ( resultSet != null && resultSet.next()) {
                p_Metric.setAvgWeather(resultSet.getFloat(1));
                p_Metric.setAvgRainfallInWeek(resultSet.getFloat(2));
                p_Metric.setCurrentSoilMoisture(resultSet.getFloat(3));
                p_Metric.setOptimumSoilMoisture(4);
            }
        } catch (Error e){

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return p_Metric;
    }

    public void UpdateMetrics(PlantationMetrics p_Metrics){

    }
}
