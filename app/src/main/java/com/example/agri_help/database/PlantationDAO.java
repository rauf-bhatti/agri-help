package com.example.agri_help.database;

import android.content.ContentValues;

import com.example.agri_help.models.Plantation;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PlantationDAO implements IPlantationDAO{
    private final CloudDAL mCloudDAL = new CloudDAL();

    @Override
    public List<Plantation> getPlantations(String username) {
        ArrayList<Plantation> plantations = new ArrayList<>();

        try {
            ResultSet resultSet = mCloudDAL.get("SELECT * FROM Plantation WHERE registered_owner = '" + username + "'");
        }
        catch (Exception exception) {
            return null;
        }

        return plantations;
    }

    @Override
    public boolean insertPlantation(Plantation plantation) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("registered_user", plantation.GetArea());
        contentValues.put("area", plantation.GetArea());
        contentValues.put("sown_date", plantation.GetSownDate());

        return mCloudDAL.insert(contentValues, "Plantations");
    }
}
