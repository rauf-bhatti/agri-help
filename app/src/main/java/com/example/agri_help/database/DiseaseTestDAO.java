package com.example.agri_help.database;

import android.content.ContentValues;

import com.example.agri_help.models.DiseaseTest;
import com.example.agri_help.models.Plantation;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DiseaseTestDAO {
    private final CloudDAL mCloudDAL = new CloudDAL();

    public boolean insertDiseaseTestRecord (DiseaseTest record) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("diseasetest_result", record.getResult());
        contentValues.put("p_id", record.getPlantationID());

        return mCloudDAL.insert(contentValues, "DiseaseTest");
    }

    public ArrayList<DiseaseTest> getDiseaseTestRecord (String plantation_id) {
        ArrayList<DiseaseTest> diseaseTestHistory = new ArrayList<>();

        try {
            ResultSet resultSet = mCloudDAL.get("SELECT * FROM DiseaseTest WHERE p_id = '" + plantation_id + "'");

            if (resultSet != null) {
                while (resultSet.next()) {
                    diseaseTestHistory.add(new DiseaseTest(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
                }

                return diseaseTestHistory;
            }
        }
        catch (Exception exception) {
            return null;
        }

        return diseaseTestHistory;
    }
}
