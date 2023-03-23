package com.example.agri_help;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agri_help.controllers.LoginController;
import com.example.agri_help.models.SignedInUser;
import com.example.agri_help.ui.plantation_management.AddPlantationActivity;
import com.example.agri_help.ui.plantation_management.PlantationManagement;

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
            }
        });

    }
}
