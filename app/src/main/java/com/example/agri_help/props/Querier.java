package com.example.agri_help.props;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import javax.net.ssl.HttpsURLConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Querier {
    private String url;

    public Querier(String url) {
        this.url = url;
    }

    public String ExecuteQueryAndReturnResponse() throws Exception {
        if (url == "") {
            throw new Exception("URL is not initialized");
        }
        String url = this.url;
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        // Optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print result
        return response.toString();
    }

    public void setURL(String url) {
        this.url = url;
    }

    public Location QueryLocation(Context context) {
        LocationManager locM = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details
            /* .
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                return locM.requestLocationUpdates(LocationManager.GPS_PROVIDER, new LocationRequest.Builder(5000), 5);
            }
            ;*/
        }
        return null;
    }

}
