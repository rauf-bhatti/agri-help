package com.example.agri_help.database;

import com.example.agri_help.models.Plantation;

import java.util.ArrayList;
import java.util.List;

public interface IPlantationDAO {
    public ArrayList<Plantation> getPlantations(String username);
    public boolean insertPlantation(Plantation plantation);
}
