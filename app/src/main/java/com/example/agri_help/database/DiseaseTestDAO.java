package com.example.agri_help.database;

import android.content.ContentValues;

import com.example.agri_help.models.DiseaseTest;
import com.example.agri_help.models.Mitigation;
import com.example.agri_help.models.Plantation;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DiseaseTestDAO {
    private final CloudDAL mCloudDAL = new CloudDAL();

    public boolean insertDiseaseTestRecord (DiseaseTest record) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("diseasetest_result", record.getResult());
        contentValues.put("p_id", record.getPlantationID());

        return mCloudDAL.insert(contentValues, "diseasetest");
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

    public ArrayList<Mitigation> GetDiseaseMitigation (String disease) {
        ArrayList<Mitigation> diseaseMitigations = new ArrayList<>();

        try {
            ResultSet resultSet = mCloudDAL.get("SELECT m.* FROM diseases d JOIN disease_mitigations dm ON d.id = dm.disease_id JOIN mitigations m ON dm.mitigation_id = m.id WHERE d.disease = '" + disease + "';");

            if (resultSet != null) {
                while (resultSet.next()) {
                    diseaseMitigations.add(new Mitigation(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
                }

                return diseaseMitigations;
            }
        }
        catch (Exception exception) {
            return null;
        }

        return diseaseMitigations;
    }
}
