package com.example.agri_help.database;

import android.content.ContentValues;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CloudDAL {
    //This class will be used to persist the connection to an online database server ((PostgreSQL))
    private final String host = "ec2-34-242-199-141.eu-west-1.compute.amazonaws.com";
    private final String database = "de873nboe0ijv7";
    private final int port = 5432;
    private final String user = "ceephnayfbwsxe";
    private final String pass = "d2b3ecd4dfe30f73c5ecfc5f841235603a0f7cfd43955ca2734ee35a6b15703c";
    private String url = "jdbc:postgresql://%s:%d/%s";

    private static Connection connection;


    private void establishConnection() {
        if (CloudDAL.connection != null) return;

        this.url = String.format(this.url, this.host, this.port, this.database);
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("org.postgresql.Driver");
            CloudDAL.connection = DriverManager.getConnection(url, user, pass);
        }
        catch (Exception ex) {
            Log.e("Connection_Issue", ex.getLocalizedMessage());
        }
    }

    private boolean getConnectionStatus() {
        try {
            return !CloudDAL.connection.isClosed();
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private StringBuilder parseContentValuesForInsert(ContentValues contentValues, String tableName) {
        List<String> keys = new ArrayList<>();
        List<Object> objects = new ArrayList<>();

        for (Map.Entry<String, Object> stringObjectEntry : contentValues.valueSet()) {
            Map.Entry mapEntry = (Map.Entry) stringObjectEntry;
            keys.add(mapEntry.getKey().toString());
            objects.add(mapEntry.getValue());
        }

        //Build the first part of the query here. Involving the table name and the associated keys.
        StringBuilder insertQuery = new StringBuilder(String.format("INSERT INTO %s(", tableName));

        for (int i = 0; i < keys.size(); i++) {
            if (i + 1 >= keys.size()) {
                insertQuery.append(String.format("%s)", keys.get(i)));
                continue;
            }
            insertQuery.append(String.format("%s,", keys.get(i)));
        }
        Log.e("INSERT_DAL", insertQuery.toString());

        //Build the second part involving the values

        insertQuery.append(" VALUES(");
        for (int i = 0; i < objects.size(); i++) {
            if (i + 1 >= keys.size()) {
                insertQuery.append(String.format("'%s');", objects.get(i)));
                continue;
            }
            insertQuery.append(String.format("'%s',", objects.get(i)));
        }
        Log.e("INSERT_DAL", insertQuery.toString());

        return insertQuery;
    }

    public boolean insert(ContentValues contentValues, String tableName) {
        StringBuilder queryToRun = parseContentValuesForInsert(contentValues, tableName);

        try {
            establishConnection();

            if (getConnectionStatus()) {
                Statement statement = connection.createStatement();
                boolean result = statement.execute(queryToRun.toString());

                return result;
            }
            return false;
        }
        catch (Exception exception) {
            Log.e("Insert_Failed", exception.getMessage());
            return false;
        }
    }

    public ResultSet get (String query) {
        try {
            establishConnection();

            if (getConnectionStatus()) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                return resultSet;
            }
            else {
                return null;
            }

        }
        catch (Exception exception) {
            Log.e("Cloud_DAL_Get", exception.getMessage());
            return null;
        }
    }

}
