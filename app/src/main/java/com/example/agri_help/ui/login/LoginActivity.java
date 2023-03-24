package com.example.agri_help.ui.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agri_help.R;
import com.example.agri_help.controllers.LoginController;
import com.example.agri_help.models.RuntimeInfo;
import com.example.agri_help.ui.plantation_management.PlantationManagement;

public class LoginActivity extends AppCompatActivity {

    EditText emailField;
    EditText passwordField;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        loginBtn = (Button)findViewById(R.id.btnLogin);
        passwordField = (EditText)findViewById(R.id.passwordField);
        emailField = (EditText) findViewById(R.id.emailField);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //currently initial..just redirect.
                if (LoginController.CheckCredentials(emailField.getText().toString(), passwordField.getText().toString())){
                    RuntimeInfo.username = emailField.getText().toString();
                    startActivity(new Intent(v.getContext(), PlantationManagement.class));
                } else {
                    TextView feedback = (TextView) findViewById(R.id.loginFeedback);
                    feedback.setText("ERROR! Wrong Credentials");
                    feedback.setTextColor(getColor(R.color.red));
                }
            }
        });


    }


    public void showRegistrationModal(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.registration_modal);
        Button registerButton = dialog.findViewById(R.id.modal_register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) dialog.findViewById(R.id.modal_name)).getText().toString();
                String email = ((EditText) dialog.findViewById(R.id.modal_email)).getText().toString();
                String password = ((EditText) dialog.findViewById(R.id.modal_password)).getText().toString();

                // Perform registration logic here
                String msg = "Registration successful. Redirecting...";
                new CountDownTimer(3000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        TextView txView = (TextView) dialog.findViewById(R.id.feedback);
                        txView.setText(msg);
                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        dialog.dismiss();
                    }

                }.start();
            }
        });
        dialog.show();
    }
}
