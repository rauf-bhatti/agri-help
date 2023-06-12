package com.example.agri_help.props;

import org.json.JSONArray;
import org.json.JSONException;

import java.time.LocalDate;

public class Processor {
    public static float getAverageOfJSONArrayByTime(JSONArray timeArr, JSONArray dataArr, String regexSeparator) throws Exception {
        int firstIndex = 0, lastIndex = 0;
        boolean flag = false;
        LocalDate dt = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dt = LocalDate.now();
        }
        for (int i=0; i<timeArr.length(); i++){
            //calculates todays avg probability of rain
            if (timeArr.get(i).toString().replaceAll(regexSeparator, "").equals(dt.toString()) && !flag){
                firstIndex  = i;
                flag = true;
            }
            if (flag && !timeArr.get(i+1).toString().replaceAll(regexSeparator, "").equals(dt.toString())) {
                lastIndex = i;
                break;
            }
        }
        float sum = 0;
        for (int i=firstIndex; i<=lastIndex; i++){
            sum += Float.parseFloat(dataArr.get(i).toString());
        }
        return sum/((lastIndex-firstIndex)+1);
    }
    public static float GetTimeNeededForWaterPump (float soilMoistureCurrent, float landSize, float flowRate, JSONArray dailyRainPredicted, JSONArray dailyShowersPredicted) throws JSONException {
        // ET rate - Water loss rate (in mm per hour)
        float rainSum = 0;
        float idealSM = 40;
        double landSizeSqM = landSize * 4046.86;
        for (int i=0; i<dailyRainPredicted.length(); i++){
            rainSum += Float.parseFloat(String.valueOf(dailyRainPredicted.get(i)));
        }
        for (int i=0; i<dailyShowersPredicted.length(); i++){
            rainSum += Float.parseFloat(String.valueOf(dailyShowersPredicted.get(i)));
        }
        double volume = landSizeSqM * ((idealSM - soilMoistureCurrent)-rainSum) * 0.0001;
        if (volume < 0 ) {volume *= -1;}
        double litersByGalon = 3.78541;
        double cubicMbyLiters = 0.001;
        double outputRate = litersByGalon * cubicMbyLiters * flowRate;
        System.out.printf("OUTPUT RATE" + outputRate + "\n");

        //5 gallons/hour * 3.78541 liters/gallon * 0.001 cubic meters/liter = 0.01892705 cubic meters/hour

        System.out.println("VOLUME: " + volume + "\n");
        double time = volume/outputRate;
        return Float.parseFloat(String.valueOf(Math.floor(time)));
    }
}