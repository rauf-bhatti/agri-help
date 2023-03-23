package com.example.agri_help.ui.plantation_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.agri_help.R;
import com.example.agri_help.models.Plantation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PlantationManagement extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    public PlantationManagementAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fab_addPlantation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantation_management);
        configureRecyclerView();

        fab_addPlantation = (FloatingActionButton) findViewById(R.id.fab_addPlantation);
        fab_addPlantation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(v.getContext(), AddPlantationActivity.class);
                startActivity(intent);
            }
        });

    }

    private void configureRecyclerView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        ArrayList<Plantation> plantations = new ArrayList<>();
        plantations.add(new Plantation(10, "test", "test"));
        plantations.add(new Plantation(10, "test", "test"));
        plantations.add(new Plantation(10, "test", "test"));
        plantations.add(new Plantation(10, "test", "test"));

        mAdapter = new PlantationManagementAdapter();

        mRecyclerView.setAdapter(mAdapter);
    }
}