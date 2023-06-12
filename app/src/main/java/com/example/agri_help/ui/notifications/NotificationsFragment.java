package com.example.agri_help.ui.notifications;

import static android.os.Build.VERSION_CODES.R;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.agri_help.R;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.agri_help.controllers.PlantationMetricController;
import com.example.agri_help.databinding.FragmentNotificationsBinding;
import com.example.agri_help.models.PlantationMetrics;
import com.example.agri_help.models.RuntimeInfo;
import com.example.agri_help.props.Processor;
import com.example.agri_help.props.Querier;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONArray;
import org.json.JSONObject;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    TextView temperature;
    TextView rainProbability;
    TextView wind;
    TextView expectedRainFall;
    ShapeableImageView weatherIcon;
    TextView soilMoisture;
    PlantationMetrics pMetric;
    PlantationMetricController controller = new PlantationMetricController();
    CardView alertCard;
    Button alertCardDismissBtn;
    TextView weatherTitle;
    TextView waterReq;
    TextView pumpSpec;

    private LocationManager locationManager;
    private LocationListener locationListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        temperature = binding.currentTemperature;
        rainProbability = binding.rainProbability;
        wind = binding.wind;
        expectedRainFall = binding.expectedRainSum;
        weatherIcon = binding.weatherIcon;
        soilMoisture = binding.soilMoistureAvg;
        alertCard= binding.alertCard;
        weatherTitle = binding.weatherTitle;
        alertCardDismissBtn = binding.dismissBtn;
        waterReq = binding.waterReq;
        pumpSpec = binding.specificationField;

        alertCardDismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertCard.setVisibility(View.INVISIBLE);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) weatherTitle.getLayoutParams();
                layoutParams.removeRule(RelativeLayout.BELOW);
                layoutParams.addRule(RelativeLayout.BELOW, binding.titleCard.getId());
            }
        });


        //Handler new_handler = new Handler();
        Thread init_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pMetric = controller.GetMetricForPlantation(RuntimeInfo.plantationID);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        init_thread.start();

        /*final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/

        Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "https://api.open-meteo.com/v1/forecast?latitude=31.5204&longitude=74.3587&current_weather=true&daily=rain_sum,precipitation_probability_mean&timezone=GMT&hourly=precipitation_probability,rain,showers,soil_moisture_9_27cm,cloudcover";
                Querier weatherQuery = new Querier(url);
                try {
                    String response = weatherQuery.ExecuteQueryAndReturnResponse();
                    JSONObject obj = new JSONObject(response);
                    JSONObject hourlyUnits = obj.getJSONObject("hourly");
                    JSONObject dailyUnits = obj.getJSONObject("daily");
                    JSONObject current_weather = obj.getJSONObject("current_weather");
                    String currentTemp = current_weather.getString("temperature");
                    String windSpeed = current_weather.getString("windspeed");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //System.out.println(currentTemp);
                            System.out.println(hourlyUnits);
                            String temp = String.valueOf(Math.ceil(Float.parseFloat(currentTemp))) + " C";
                            try {
                                JSONArray hourlyUnits_time = hourlyUnits.getJSONArray("time");
                                JSONArray hourlyUnits_PrecipitationProb = hourlyUnits.getJSONArray("precipitation_probability");
                                JSONArray hourlyUnits_SoilMoisture = hourlyUnits.getJSONArray("soil_moisture_9_27cm");
                                JSONArray hourlyUnits_cloudcover = hourlyUnits.getJSONArray("cloudcover");
                                JSONArray hourlyUnits_showers = hourlyUnits.getJSONArray("showers");
                                JSONArray hourlyUnits_rain = hourlyUnits.getJSONArray("rain");


                                JSONArray dailyUnits_dates = dailyUnits.getJSONArray("time");
                                JSONArray dailyUnits_rainSum = dailyUnits.getJSONArray("rain_sum");

                                float sm_ret = Processor.getAverageOfJSONArrayByTime(hourlyUnits_time, hourlyUnits_SoilMoisture, "T.*");
                                float cloudyPerc = Processor.getAverageOfJSONArrayByTime(hourlyUnits_time, hourlyUnits_cloudcover, "T.*");
                                double sm_temp = sm_ret * 22.5 * 0.01*1000;
                                float timeNeeded = Processor.GetTimeNeededForWaterPump(Float.parseFloat(String.valueOf(sm_temp)), 5.0f, 5.0f, hourlyUnits_rain, hourlyUnits_showers);
                                System.out.println("SOUT:" + timeNeeded);

                                if (cloudyPerc >= 50){
                                    Drawable drawable = ContextCompat.getDrawable(requireContext(), com.example.agri_help.R.drawable.cloudy_day);
                                    weatherIcon.setImageDrawable(drawable);
                                }
                                else if (cloudyPerc >= 70) {
                                    weatherIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), com.example.agri_help.R.drawable.cloudy));
                                }
                                else  {
                                    weatherIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), com.example.agri_help.R.drawable.day));
                                }

                                rainProbability.setText(String.valueOf(Math.ceil(Processor.getAverageOfJSONArrayByTime(hourlyUnits_time, hourlyUnits_PrecipitationProb, "T.*"))) + "%");
                                System.out.println("Average Soil Moisture m³/m³:" + String.valueOf(Processor.getAverageOfJSONArrayByTime(hourlyUnits_time, hourlyUnits_SoilMoisture, "T.*")));
                                expectedRainFall.setText(String.valueOf(Processor.getAverageOfJSONArrayByTime(dailyUnits_dates, dailyUnits_rainSum, "")) + " mm");
                                wind.setText(windSpeed+" km/h");
                                soilMoisture.setTextColor(Color.RED);
                                sm_ret = sm_ret * 10;
                                if (sm_ret >= 20 && sm_ret <= 60) {
                                    soilMoisture.setTextColor(Color.GREEN);
                                    alertCard.setVisibility(View.INVISIBLE);
                                    RelativeLayout relativeLayout = binding.infoLayout;
                                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) weatherTitle.getLayoutParams();
                                    layoutParams.removeRule(RelativeLayout.BELOW);
                                    layoutParams.addRule(RelativeLayout.BELOW, weatherTitle.getId());
                                }

                                waterReq.setText(String.valueOf(timeNeeded) + " hours.");
                                pumpSpec.setText(String.valueOf(pMetric.getPumpSpec()) + " gallons per hour");
                                soilMoisture.setText(String.valueOf(sm_ret));
                                temperature.setText(temp);
                                UpdateMetrics(pMetric);
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }catch (Exception e){
                    System.out.println("EXCEPTION: " +  e.getMessage());
                }
            }
        });
        thread.start();

        return root;
    }

    public void UpdateMetrics (PlantationMetrics metric){
        metric.setAvgWeather((Float.parseFloat(temperature.getText().toString().replace(" C", "")) + metric.getAvgWeather())/2);
        metric.setAvgRainfallInWeek((Float.parseFloat(expectedRainFall.getText().toString().replace(" mm",""))+metric.getAvgRainfallInWeek())/2);
        metric.setCurrentSoilMoisture((Float.parseFloat(soilMoisture.getText().toString()) + metric.getCurrentSoilMoisture())/2);
        controller.UpdateMetricForPlantation(metric);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}