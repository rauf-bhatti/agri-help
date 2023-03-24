package com.example.agri_help.ui.notifications;

import static android.os.Build.VERSION_CODES.R;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.agri_help.R;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.agri_help.databinding.FragmentNotificationsBinding;
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
        /*final TextView textView = binding.textNotifications;




        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
        Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "https://api.open-meteo.com/v1/forecast?latitude=31.5204&longitude=74.3587&current_weather=true&daily=rain_sum,precipitation_probability_mean&timezone=GMT&hourly=precipitation_probability,soil_moisture_1_3cm,cloudcover";
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
                            System.out.println(currentTemp);
                            String temp = String.valueOf(Math.ceil(Float.parseFloat(currentTemp))) + " C";
                            try {
                                JSONArray hourlyUnits_time = hourlyUnits.getJSONArray("time");
                                JSONArray hourlyUnits_PrecipitationProb = hourlyUnits.getJSONArray("precipitation_probability");
                                JSONArray hourlyUnits_SoilMoisture = hourlyUnits.getJSONArray("soil_moisture_1_3cm");
                                JSONArray hourlyUnits_cloudcover = hourlyUnits.getJSONArray("cloudcover");

                                JSONArray dailyUnits_dates = dailyUnits.getJSONArray("time");
                                JSONArray dailyUnits_rainSum = dailyUnits.getJSONArray("rain_sum");
                                float cloudyPerc = Processor.getAverageOfJSONArrayByTime(hourlyUnits_time, hourlyUnits_cloudcover, "T.*");
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

                                temperature.setText(temp);
                            } catch (Exception e){
                                System.out.println("EXCEPTION INNER: " +  e.getMessage());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}