package com.example.agri_help.controllers;

import com.example.agri_help.database.CloudDAL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    private static final CloudDAL mCloudDAL = new CloudDAL();

    public static boolean CheckCredentials (String Username, String Password) {
        String query = "SELECT * FROM Registered_User WHERE username = '" + Username + "' AND " +
                "user_password = '" + Password + "';";
        try {
            ResultSet result = mCloudDAL.get(query);

            if (result != null) {
                return result.next();
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
