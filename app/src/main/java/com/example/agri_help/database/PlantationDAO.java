package com.example.agri_help.database;

import android.content.ContentValues;

import com.example.agri_help.models.Plantation;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PlantationDAO implements IPlantationDAO{
    private final CloudDAL mCloudDAL = new CloudDAL();

    @Override
    public ArrayList<Plantation> getPlantations(String username) {
        ArrayList<Plantation> plantations = new ArrayList<>();

        try {
            ResultSet resultSet = mCloudDAL.get("SELECT * FROM Plantation WHERE registered_user = '" + username + "'");

            if (resultSet != null) {
                while (resultSet.next()) {
                    plantations.add(new Plantation
                            (resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3),
                                    resultSet.getString(4), resultSet.getString(5)
                            ));
                }

                return plantations;
            }
        }
        catch (Exception exception) {
            return null;
        }

        return plantations;
    }

    @Override
    public boolean insertPlantation(Plantation plantation) {
        ContentValues contentValues = new ContentValues();

        // Search for total number and then create new ID.


        contentValues.put("p_id", plantation.GetPlantationID());
        contentValues.put("registered_user", plantation.GetRegisteredUser());
        contentValues.put("area", plantation.GetArea());
        contentValues.put("sown_date", plantation.GetSownDate());
        contentValues.put("plantation_status", plantation.GetPlantationStatus());

        mCloudDAL.insert(contentValues, "Plantation");
        return mCloudDAL.insertAfterPlantation(String.valueOf(plantation.GetArea()),plantation.GetPlantationID(), "plantation_metrics");
    }
}
