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
}