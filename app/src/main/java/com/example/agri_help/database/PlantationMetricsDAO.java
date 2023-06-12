package com.example.agri_help.database;

import android.content.ContentValues;

import com.example.agri_help.models.Plantation;
import com.example.agri_help.models.PlantationMetrics;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlantationMetricsDAO implements IPlantationMetricsDAO {
    private final CloudDAL mCloudDAL = new CloudDAL();

    @Override
    public PlantationMetrics getPlantationMetric(String plantationID) {
        PlantationMetrics p_Metric = new PlantationMetrics(plantationID, -1f, -1f, -1f, -1f, -1f, -1f, 1f, 1f);
        try {
            ResultSet resultSet = mCloudDAL.get("SELECT * FROM plantation_metrics WHERE plantation_id = '" + plantationID + "'");
            while ( resultSet != null && resultSet.next()) {
                p_Metric.setAvgWeather(resultSet.getFloat(2));
                p_Metric.setAvgRainfallInWeek(resultSet.getFloat(3));
                p_Metric.setCurrentSoilMoisture(resultSet.getFloat(4));
                p_Metric.setOptimumSoilMoisture(resultSet.getFloat(5));
                p_Metric.setArea(resultSet.getFloat(6));
                p_Metric.setPumpSpec(resultSet.getFloat(7));
                p_Metric.setLatitude(resultSet.getFloat(8));
                p_Metric.setLongitude(resultSet.getFloat(9));
            }
        } catch (Error e){
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return p_Metric;
    }

    @Override
    public boolean InsertPlantationMetric (PlantationMetrics p_Metrics){
        ContentValues cv = new ContentValues();
        cv.put("p_id", p_Metrics.plantationID);
        cv.put("avg_weather_week", p_Metrics.getAvgWeather());
        cv.put("avg_rainfall_week", p_Metrics.getAvgRainfallInWeek());
        cv.put("avg_soil_moisture", p_Metrics.getCurrentSoilMoisture());
        cv.put("optimum_soil_moisture", p_Metrics.getOptimumSoilMoisture());
        cv.put("area", p_Metrics.getOptimumSoilMoisture());
        cv.put("pump_spec", p_Metrics.getPumpSpec());
        cv.put("longitude", p_Metrics.getLongitude());
        cv.put("latitude", p_Metrics.getLatitude());

        return mCloudDAL.insert(cv, "plantation_metrics");
    }

    @Override
    public boolean UpdatePlantationMetric (PlantationMetrics p_Metrics){
        ContentValues cv = new ContentValues();
        cv.put("plantation_id", p_Metrics.plantationID);
        cv.put("avg_weather_week", p_Metrics.getAvgWeather());
        cv.put("avg_rainfall_week", p_Metrics.getAvgRainfallInWeek());
        cv.put("avg_soil_moisture", p_Metrics.getCurrentSoilMoisture());
        cv.put("optimum_soil_moisture", p_Metrics.getOptimumSoilMoisture());
        cv.put("area", p_Metrics.getArea());
        cv.put("pump_spec", p_Metrics.getPumpSpec());
        cv.put("longitude", p_Metrics.getLongitude());
        cv.put("latitude", p_Metrics.getLatitude());

        return mCloudDAL.update("plantation_metrics", "plantation_id", p_Metrics.plantationID, cv);
    }
}
