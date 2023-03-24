package com.example.agri_help;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.agri_help.controllers.DetectionController;
import com.example.agri_help.ui.login.LoginActivity;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private int progress = 0;
    TextView welcomeTxt;
    Button getStartedBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getStartedBtn = findViewById(R.id.btnGetStarted);
        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        welcomeTxt  = findViewById(R.id.welcomeTxt);
        String welcomeMsg = "Grow \n" +
                "your farming business..\n" +
                "earn more.. by utilising less ";
        int delayCounter = 300;
        welcomeTxt.setText("");
        for (int i=0; i<welcomeMsg.length(); i++) {
            final char x = welcomeMsg.charAt(i);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    welcomeTxt.append(String.valueOf(x));
                }
            }, delayCounter);
            delayCounter += 100;
        }
    }

    private ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        String[] projection = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String imagePath = cursor.getString(columnIndex);
                        cursor.close();
                        handleImageSelected(imagePath);
                    }
                }
            });

    private void handleImageSelected(String imagePath) {
        DetectionController controller = new DetectionController();
        try {
            controller.startDetectionLifecycle(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pickImageFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }
}