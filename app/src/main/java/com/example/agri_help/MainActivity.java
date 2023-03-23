package com.example.agri_help;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agri_help.controllers.DetectionController;
import com.example.agri_help.controllers.LoginController;
import com.example.agri_help.models.SignedInUser;
import com.example.agri_help.ui.plantation_management.AddPlantationActivity;
import com.example.agri_help.ui.plantation_management.PlantationManagement;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.btn_redirect);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlantationManagement.class);
                if (LoginController.CheckCredentials("rauf", "rauf")) {
                    SignedInUser.username = "rauf";
                    startActivity(intent);
                }
                else {
                    Toast.makeText(v.getContext(), "Wrong credentials!", Toast.LENGTH_LONG).show();
                }

               // pickImageFromGallery();
            }
        });
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