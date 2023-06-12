package com.example.agri_help.database;

import android.content.ContentValues;
import android.os.StrictMode;
import android.util.Log;

import com.example.agri_help.models.Plantation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CloudDAL {
    //This class will be used to persist the connection to an online database server ((PostgreSQL))
    private final String host = "ep-billowing-flower-443662.eu-central-1.aws.neon.tech";
    private final String database = "neondb";
    private final int port = 5432;
    private final String user = "rauf.bhatti.github";
    private final String pass = "Yfg9eRKvy3SB";
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

    private StringBuilder parseContentValuesForUpdate(String tableName, String keyField, Object key, ContentValues contentValues) {
        List<String> keys = new ArrayList<>();
        List<Object> objects = new ArrayList<>();

        Iterator iterator = contentValues.valueSet().iterator();

        while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry)iterator.next();
            keys.add(mapEntry.getKey().toString());
            objects.add(mapEntry.getValue());
        }

        //Build the first part of the query here. Involving the table name and the associated keys.
        StringBuilder updateQuery = new StringBuilder(String.format("UPDATE %s SET ", tableName));

        for (int i = 0; i < keys.size(); i++) {
            if (i + 1 >= keys.size()) {
                updateQuery.append(String.format("%s = '%s' WHERE %s = '%s'", keys.get(i), objects.get(i), keyField, key));
                continue;
            }
            updateQuery.append(String.format("%s = '%s',", keys.get(i), objects.get(i)));
        }

        Log.e("UPDATE_DAL", updateQuery.toString());
        return updateQuery;
    }

    public boolean update (String tableName, String keyField, Object key, ContentValues contentValues) {
        try {
            establishConnection();

            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(parseContentValuesForUpdate(tableName, keyField, key, contentValues).toString());

            if (result >= 1) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e)
        {
            Log.e("CLOUD_DAL", e.getMessage());
            return false;
        }
    }

    public boolean insertAfterPlantation (String area, String p_id, String tablename){
        ContentValues cv = new ContentValues();
        cv.put("area", Float.parseFloat(area));
        cv.put("plantation_id", p_id);
        cv.put("avg_weather_week",0.0f);
        cv.put("avg_rainfall_week", 0.0f);
        cv.put("avg_soil_moisture", 0.0f);
        cv.put("optimum_soil_moisture", 0.0f);
        cv.put("pump_spec", 0.0f);
        cv.put("longitude", 0.0f);
        cv.put("latitude", 0.0f);

        return insert(cv, tablename);
    }

    public boolean insert (ContentValues contentValues, String tableName) {
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
