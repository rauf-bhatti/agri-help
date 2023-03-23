package com.example.agri_help.controllers;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetectionController {

    private String sendToModel(File imageFile) throws IOException {
        OkHttpClient client = new OkHttpClient();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        // create the request body
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", imageFile.getName(),
                        RequestBody.create(MediaType.parse("image/*"), imageFile))
                .build();

        // create the request
        Request request = new Request.Builder()
                .url("http://10.0.2.2:5000/upload")
                .post(requestBody)
                .build();

        // execute the request
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        } else {
            String responseBody = response.body().string();
            return responseBody;
        }
    }

    public String startDetectionLifecycle(File image) throws IOException {
        String response = sendToModel(image);
        if (!response.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(response);


                return jsonObject.getJSONObject("prediction").getString("predicted_label");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return "Error";
        // Parse the response here!
    }
}
