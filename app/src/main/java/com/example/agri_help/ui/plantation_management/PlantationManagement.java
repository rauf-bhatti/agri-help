package com.example.agri_help.ui.plantation_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.example.agri_help.MonitorPlantation;
import com.example.agri_help.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class PlantationManagement extends AppCompatActivity {

    private EditText searchEditText;
    private RecyclerView recyclerView;
    private MyAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantation_management);

        // Initialize views
        searchEditText = findViewById(R.id.search_edit_text);
        recyclerView = findViewById(R.id.recycler_view);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter<>();

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Start a new activity to display the item description
                Intent intent = new Intent(PlantationManagement.this, MonitorPlantation.class);
                intent.putExtra("item", adapter.getItem(position));
                startActivity(intent);
                // Toast.makeText(adapter.getItem(position),"",)
            }
        });

        recyclerView.setAdapter(adapter);

        // Add sample data to adapter
        List<String> data = new ArrayList<>();
        data.add("Cotton");
        data.add("Tomato");
        data.add("Potato");
        data.add("Cotton");
        data.add("Tomato");
        data.add("Potato");
        data.add("Cotton");
        data.add("Tomato");
        data.add("Potato");
        data.add("Cotton");
        data.add("Tomato");
        data.add("Potato");
        data.add("Cotton");
        data.add("Tomato");
        data.add("Potato");
        adapter.setData(data);

        // Set up search bar
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch();
                return true;
            }
            return false;
        });
    }

    private void performSearch() {
        String query = searchEditText.getText().toString().trim();

        // Filter data in adapter
        adapter.getFilter().filter(query);
    }
}
