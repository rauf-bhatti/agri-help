package com.example.agri_help.ui.dashboard;

import android.annotation.SuppressLint;
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
import com.example.agri_help.models.Mitigation;
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
        TextView engText;
        TextView urduText;

        public DiseaseTestViewHolder(@NonNull View itemView) {
            super(itemView);

            detectionTag = itemView.findViewById(R.id.txt_detectionTag);
            engText = itemView.findViewById(R.id.txtEng);
            urduText = itemView.findViewById(R.id.txtUrdu);

            MaterialCardView cardView = itemView.findViewById(R.id.history_card);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    DiseaseTest ticketInstance = DiseaseTestAdapter.diseaseTestHistory.get(getAdapterPosition());
//
//
//                    View popupView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.popup_layout, null);
//                    PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//                    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//                    TextView popupTitle = popupView.findViewById(R.id.mitigationDescription);
//                    Button popupButton = popupView.findViewById(R.id.popup_button);
//
//                    ArrayList<Mitigation> mitigations = new ArrayList<>();
//
//                    if (ticketInstance.getResult().contains("bacterial")) {
//                        mitigations = diseaseTestDAO.GetDiseaseMitigation("bacterial_blight");
//                        engText.setText(mitigations.get(0).GetEngMitigation());
//                    }
//                    else if (ticketInstance.getResult().contains("wilt")) {
//                        mitigations = diseaseTestDAO.GetDiseaseMitigation("fussarium_wilt");
//                        engText.setText(mitigations.get(0).GetEngMitigation());
//
//                    }
//                    else if (ticketInstance.getResult().contains("curl")) {
//                        mitigations = diseaseTestDAO.GetDiseaseMitigation("curl_virus");
//                        engText.setText(mitigations.get(0).GetEngMitigation());
//                    }
//
//                    popupButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            popupWindow.dismiss();
//                        }
//                    });
//
//                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
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

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(DiseaseTestAdapter.DiseaseTestViewHolder holder, int position) {
        DiseaseTest obj = diseaseTestHistory.get(position);
        ArrayList<Mitigation> mitigations = new ArrayList<>();
        mitigations = diseaseTestDAO.GetDiseaseMitigation(obj.getResult());

        holder.detectionTag.setText("Disease: " + obj.getResult());

        if (mitigations.size() > 0) {
            holder.engText.setText("English:\n" + mitigations.get(0).GetEngMitigation());
            holder.urduText.setText("Urdu:\n" + mitigations.get(0).GetUrduMitigation());
        }
    }

    @Override
    public int getItemCount() {
        return diseaseTestHistory.size();
    }
}
