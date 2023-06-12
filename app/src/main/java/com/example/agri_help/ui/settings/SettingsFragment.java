package com.example.agri_help.ui.settings;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.agri_help.controllers.PlantationMetricController;
import com.example.agri_help.databinding.FragmentSettingsBinding;
import com.example.agri_help.models.PlantationMetrics;
import com.example.agri_help.models.RuntimeInfo;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;
    EditText pumpSpec;
    EditText area;
    Button saveBtn;
    Button calibrateBtn;
    PlantationMetricController pmController = new PlantationMetricController();

    PlantationMetrics metric;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);


        //pumpSpec.setText(String.valueOf(metric.getPumpSpec()));
        View root = binding.getRoot();

        saveBtn = binding.SaveBtn;


        metric = pmController.GetMetricForPlantation(RuntimeInfo.plantationID);
        area = binding.TotalArea;
        pumpSpec = binding.TubeWellValue;

        area.setText(String.valueOf(metric.getArea()));
        pumpSpec.setText(String.valueOf(metric.getPumpSpec()));



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (metric.getArea() != Float.parseFloat(area.getText().toString()) || metric.getPumpSpec() != Float.parseFloat(pumpSpec.getText().toString())){
                        System.out.println(area.getText().toString());
                        metric.setArea(Float.parseFloat(area.getText().toString()));
                        metric.setPumpSpec(Float.parseFloat(pumpSpec.getText().toString()));
                        pmController.UpdateMetricForPlantation(metric);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void handleOnClickListener() {

    }
}
