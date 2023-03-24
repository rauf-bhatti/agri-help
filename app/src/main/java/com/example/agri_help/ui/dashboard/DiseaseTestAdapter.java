package com.example.agri_help.ui.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agri_help.MonitorPlantation;
import com.example.agri_help.R;
import com.example.agri_help.database.DiseaseTestDAO;
import com.example.agri_help.models.DiseaseTest;
import com.example.agri_help.models.Plantation;
import com.example.agri_help.models.RuntimeInfo;
import com.example.agri_help.ui.plantation_management.PlantationManagementAdapter;
import com.google.android.material.card.MaterialCardView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DiseaseTestAdapter extends RecyclerView.Adapter<DiseaseTestAdapter.DiseaseTestViewHolder> {

    public static ArrayList<DiseaseTest> diseaseTestHistory;
    private DiseaseTestDAO diseaseTestDAO = new DiseaseTestDAO();

    public class DiseaseTestViewHolder extends RecyclerView.ViewHolder {
        TextView detectionTag;

        public DiseaseTestViewHolder(@NonNull View itemView) {
            super(itemView);

            detectionTag = itemView.findViewById(R.id.txt_detectionTag);

            MaterialCardView cardView = itemView.findViewById(R.id.history_card);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DiseaseTest ticketInstance = DiseaseTestAdapter.diseaseTestHistory.get(getAdapterPosition());


                    View popupView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.popup_layout, null);
                    PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                    TextView popupTitle = popupView.findViewById(R.id.mitigationDescription);
                    Button popupButton = popupView.findViewById(R.id.popup_button);


                    if (ticketInstance.getResult().contains("bacterial")) {
                        popupTitle.setText(R.string.bacterial);
                    }
                    else if (ticketInstance.getResult().contains("fungal")) {
                        popupTitle.setText(R.string.fungal);

                    }
                    else if (ticketInstance.getResult().contains("curl")) {
                        popupTitle.setText(R.string.curl);
                    }

                    popupButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });

                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                }
            });



        }
    }

    public DiseaseTestAdapter (ArrayList<DiseaseTest> diseaseTestHistory) {
        this.diseaseTestHistory = diseaseTestHistory;
    }

    public DiseaseTestAdapter () {
        diseaseTestHistory = diseaseTestDAO.getDiseaseTestRecord(RuntimeInfo.plantationID);
    }

    public DiseaseTestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_card, parent, false);
        DiseaseTestAdapter.DiseaseTestViewHolder ch = new DiseaseTestAdapter.DiseaseTestViewHolder(v);
        return ch;
    }

    public void onBindViewHolder(DiseaseTestAdapter.DiseaseTestViewHolder holder, int position) {
        DiseaseTest obj = diseaseTestHistory.get(position);
        holder.detectionTag.setText(obj.getResult());
    }

    @Override
    public int getItemCount() {
        return diseaseTestHistory.size();
    }
}
