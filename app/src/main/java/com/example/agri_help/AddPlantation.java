package com.example.agri_help;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;


public class AddPlantation extends AppCompatActivity implements LocationListener {
    Button pop;
    Button button_location;
    TextView textView_location;
    LocationManager locationManager;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_plantation);
        pop=findViewById(R.id.button5);
        textView_location = findViewById(R.id.txt_area2);
        button_location = findViewById(R.id.button2);
        button=findViewById(R.id.button);
        //pop=findViewById(R.id.button);
        //Runtime permissions
        if (ContextCompat.checkSelfPermission(AddPlantation.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddPlantation.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }



        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create method
                getLocation();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create method
                Intent intent=new Intent(AddPlantation.this, DateActivity.class);
                startActivity(intent);
            }
        });

        pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create method
                CreatepopUpwindow();
            }
        });






    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,AddPlantation.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(AddPlantation.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            textView_location.setText(address);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    private void CreatepopUpwindow() {
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpView=inflater.inflate(R.layout.popup,null);

        int width= ViewGroup.LayoutParams.MATCH_PARENT;
        int height=ViewGroup.LayoutParams.MATCH_PARENT;
        boolean focusable=true;
        final PopupWindow popupWindow=new PopupWindow(popUpView,width,height,focusable);
        pop.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(pop, Gravity.BOTTOM,0,0);

            }
        });
        final TextView Skip;
        final TextView description;
        final Button Gotit;
        Skip=popUpView.findViewById(R.id.Skip);
        Gotit=popUpView.findViewById(R.id.Next);
        description= popUpView.findViewById(R.id.d);
        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                //Skip.setText("vnerfbofenbvoievnefoivbnefoibvnefoibno");
            }
        });
        Gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CreatepopUpwindow(Gotit);

                //description.setText("Welcome to pop up 2");
                CreatepopUpwindow2();
                popupWindow.dismiss();
            }
        });
        // and if you want to close popup when touch Screen
        popUpView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }


    private void CreatepopUpwindow2() {
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpView=inflater.inflate(R.layout.popup,null);

        int width= ViewGroup.LayoutParams.MATCH_PARENT;
        int height=ViewGroup.LayoutParams.MATCH_PARENT;
        boolean focusable=true;
        final PopupWindow popupWindow=new PopupWindow(popUpView,width,height,focusable);
        pop.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(pop, Gravity.BOTTOM,0,0);

            }
        });
        final TextView Skip;
        final TextView description;
        final Button Gotit;
        Skip=popUpView.findViewById(R.id.Skip);
        Gotit=popUpView.findViewById(R.id.Next);
        description= popUpView.findViewById(R.id.d);
        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                //Skip.setText("vnerfbofenbvoievnefoivbnefoibvnefoibno");
            }
        });
        description.setText("Irrigation: Ensure that the plants are well watered throughout the growing season. Irrigation can be done using drip irrigation, furrow irrigation, or sprinkler irrigation, depending on the local conditions.\n" +
                "\n" +
                "Pest and disease control: Implement an effective pest and disease control strategy to prevent yield losses due to pests and diseases.\n" +
                "\n" +
                "Weed control: Control weeds by using appropriate herbicides or by manual weeding.\n" +
                "\n" +
                "Harvesting: Harvest the cotton at the right time to prevent yield losses due to weather damage or pest infestation.\n" +
                "\n" +
                "Post-harvest management: Properly store and handle the harvested cotton to prevent losses due to spoilage or pest infestation.");
        Gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CreatepopUpwindow(Gotit);
                CreatepopUpwindow3();
                popupWindow.dismiss();
                //description.setText("Welcome to pop up 2");

            }
        });
        // and if you want to close popup when touch Screen
        popUpView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }


    private void CreatepopUpwindow3() {
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpView=inflater.inflate(R.layout.popup,null);

        int width= ViewGroup.LayoutParams.MATCH_PARENT;
        int height=ViewGroup.LayoutParams.MATCH_PARENT;
        boolean focusable=true;
        final PopupWindow popupWindow=new PopupWindow(popUpView,width,height,focusable);
        pop.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(pop, Gravity.BOTTOM,0,0);

            }
        });
        final TextView Skip;
        final TextView description;
        final Button Gotit;
        final TextView heading;
        Skip=popUpView.findViewById(R.id.Skip);
        Gotit=popUpView.findViewById(R.id.Next);
        description= popUpView.findViewById(R.id.d);
        heading=popUpView.findViewById(R.id.head);
        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                //Skip.setText("vnerfbofenbvoievnefoivbnefoibvnefoibno");
            }
        });
        heading.setText("How to protect cotton from Diseases?");
        description.setText("Cotton can be protected from diseases by implementing the following practices:\n" +
                "\n" +
                "Crop rotation: Rotate cotton with non-cotton crops to reduce the buildup of disease-causing pathogens in the soil.\n" +
                "\n" +
                "Sanitation: Keep the field and equipment clean to prevent the spread of diseases.\n" +
                "\n" +
                "Use of disease-resistant varieties: Use cotton varieties that are resistant to common diseases.\n" +
                "\n" +
                "Fungicide applications: Apply fungicides to control fungal diseases such as Fusarium wilt, Verticillium wilt, and anthracnose.\n" +
                "\n" +
                "Timely planting: Plant cotton at the optimal time to avoid stress and reduce susceptibility to diseases.\n" +
                "\n" +
                "Proper irrigation: Avoid over-watering or under-watering, as both can increase the risk of disease development.");
        Gotit.setText("Finish");
        Gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CreatepopUpwindow(Gotit);
                // CreatepopUpwindow3();
                //description.setText("Welcome to pop up 2");
                popupWindow.dismiss();

            }
        });
        // and if you want to close popup when touch Screen
        popUpView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }



}
