package com.example.agri_help.ui.plantation_management;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agri_help.R;
import com.example.agri_help.controllers.PlantationController;
import com.example.agri_help.models.Plantation;
import com.example.agri_help.models.SignedInUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddPlantationActivity extends AppCompatActivity {

    Button btn_addPlantation;
    Button btn_addSowingDate;
    Button btn_addLocation;
    final Calendar calendar = Calendar.getInstance();
    private PlantationController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_plantation);

        btn_addSowingDate = (Button) findViewById(R.id.btn_dateSown);

        btn_addPlantation = (Button) findViewById(R.id.btn_addPlantation);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                btn_addSowingDate.setText(updateLabel());
            }
        };

        btn_addSowingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddPlantationActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btn_addPlantation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_addSowingDate.getText().toString().toLowerCase().equals("get")) {
                    // Push notification that says that you need to set the date!
                    Toast.makeText(v.getContext(), "You must set the date first!", Toast.LENGTH_LONG).show();
                    return;
                }

                String datePlanted = (String) btn_addSowingDate.getText(); // Get the text e.g. the date.
                EditText txt_plantationArea = findViewById(R.id.txt_plantationArea);
                int area = Integer.parseInt(String.valueOf(txt_plantationArea.getText()));

                controller = new PlantationController();
                Plantation newPlantation = new Plantation(area, datePlanted, SignedInUser.username);

                if (!controller.AddPlantation(newPlantation, SignedInUser.username)) {
                    Toast.makeText(v.getContext(), "Added!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(v.getContext(), "Error inserting new plantation!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private String updateLabel() {
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        return dateFormat.format(calendar.getTime());
    }
}