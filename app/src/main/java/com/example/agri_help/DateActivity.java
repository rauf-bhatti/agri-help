package com.example.agri_help;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.text.ParseException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateActivity extends AppCompatActivity {

    EditText day;
    EditText month;
    EditText year;
    Button enter;
    int d,m,y;
    public TextView textViewDate;
    LocalDate myDate = null;
    // int daysDifference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        day=findViewById(R.id.day);
        month=findViewById(R.id.month);
        year=findViewById(R.id.year);
        textViewDate = findViewById(R.id.display);
        enter=findViewById(R.id.button_sign_up);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d=Integer.parseInt(String.valueOf(day.getText()));
                m=Integer.parseInt(String.valueOf(month.getText()));
                y=Integer.parseInt(String.valueOf(year.getText()));

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    myDate = LocalDate.of(y, m, d);
                }
                int daysDifference = getDaysDifference(myDate);
                //int myNumber = 42;
                String myString = Integer.toString(daysDifference);
                textViewDate.setText(getCottonGrowthStage(daysDifference));

            }

            //TextView textViewDate = findViewById(R.id.display);


        });

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        //String myString = Integer.toString(daysDifference);
        //public TextView textViewDate = findViewById(R.id.display);
        //textViewDate.setText(myString);



    }




    public int getDaysDifference(LocalDate date) {
        LocalDate currentDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
        }
        long daysBetween = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            daysBetween = ChronoUnit.DAYS.between(date, currentDate);
        }
        return Math.toIntExact(daysBetween);
    }

    public String getCottonGrowthStage(int daysSinceSowing) {
        if (daysSinceSowing < 0) {
            return "Invalid number of days since sowing.";
        } else if (daysSinceSowing == 0) {
            return "Sowing Stage";
        } else if (daysSinceSowing <= 10) {
            return "Seedling Stage";
        } else if (daysSinceSowing <= 42) {
            return "Vegetative Stage";
        } else if (daysSinceSowing <= 70) {
            return "Flowering Stage";
        } else if (daysSinceSowing <= 98) {
            return "Fruiting Stage";
        } else if (daysSinceSowing <= 150) {
            return "Harvesting Stage";
        } else {
            return "Cotton growth cycle has ended.";
        }
    }


}

