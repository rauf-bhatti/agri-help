package com.example.agri_help.ui.plantation_management;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
;

import com.example.agri_help.MonitorPlantation;
import com.example.agri_help.R;
import com.example.agri_help.controllers.PlantationController;
import com.example.agri_help.models.Plantation;
import com.example.agri_help.models.RuntimeInfo;
import com.google.android.material.card.MaterialCardView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class PlantationManagementAdapter extends RecyclerView.Adapter<PlantationManagementAdapter.PlantationViewHolder> {
    public static ArrayList<Plantation> plantations;
    private RelativeLayout itemInPlantationList;
    private PlantationController plantationController;


    public PlantationManagementAdapter (ArrayList<Plantation> plantations) {
        this.plantations = plantations;
    }

    public PlantationManagementAdapter () {
        plantationController = new PlantationController();
        plantations = plantationController.GetPlantations(RuntimeInfo.username);
    }

    public class PlantationViewHolder extends RecyclerView.ViewHolder{
        public TextView location;
        public TextView area;
        public TextView currentSoilMoisture;

        public TextView status_tag;
        public TextView sown_date;
        public ProgressBar progressBar;


        public PlantationViewHolder(View v){
            super(v);
            /*location = (TextView) v.findViewById(R.id.locationField);
            area = (TextView)v.findViewById(R.id.areaField);
            currentSoilMoisture = (TextView) v.findViewById(R.id.currentSoilMoistureField);*/
            itemInPlantationList = (RelativeLayout) v.findViewById(R.id.itemInPlantationList);
           /* itemInPlantationList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.itemInPlantationList){
                        Intent intent = new Intent(v.getContext(), ManagementActivity.class);
                        v.getContext().startActivity(intent);
                    }
                }
            });*/
            sown_date = v.findViewById(R.id.txt_sownDate);
            status_tag = v.findViewById(R.id.txt_statusTag);
            progressBar = v.findViewById(R.id.progressBar);

            MaterialCardView cardView = itemView.findViewById(R.id.plantation_card);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Plantation ticketInstance = PlantationManagementAdapter.plantations.get(getAdapterPosition());
                    RuntimeInfo.plantationID = ticketInstance.GetPlantationID();

                    Intent intent = new Intent(view.getContext(), MonitorPlantation.class);
                    view.getContext().startActivity(intent);
                }
            });
        }

    }

    public PlantationManagementAdapter.PlantationViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plantation_card, parent, false);
        PlantationManagementAdapter.PlantationViewHolder ch = new PlantationManagementAdapter.PlantationViewHolder(v);
        return ch;
    }
    public void onBindViewHolder(PlantationManagementAdapter.PlantationViewHolder holder, int position) {
        Plantation obj = plantations.get(position);
        holder.sown_date.setText(obj.GetSownDate());
        holder.status_tag.setText(obj.GetPlantationStatus());

        String sownDate = obj.GetSownDate();
        String[] tokens = sownDate.split("/");

        int daysElapsed = Math.abs(Integer.parseInt(tokens[0]) - LocalDate.now().getMonthValue()) * 30;
        float progressIntervals = 160.0f / 100.0f;
        float progress = (float) daysElapsed / progressIntervals;

        holder.progressBar.setProgress((int) progress);
    }

    @Override
    public int getItemCount() {
        return plantations.size();
    }
}