package com.example.agri_help.controllers;

import com.example.agri_help.database.CloudDAL;
import com.example.agri_help.database.DiseaseTestDAO;
import com.example.agri_help.models.Disease;
import com.example.agri_help.models.DiseaseTest;
import com.example.agri_help.models.RuntimeInfo;

public class DiseaseTestController {
    private final DiseaseTestDAO diseaseTestDAO = new DiseaseTestDAO();

    public boolean recordDiseaseTestResult (String result) {
        return diseaseTestDAO.insertDiseaseTestRecord(new DiseaseTest(RuntimeInfo.plantationID, result));
    }
}
