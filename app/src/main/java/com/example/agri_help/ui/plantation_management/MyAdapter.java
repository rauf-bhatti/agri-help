package com.example.agri_help.ui.plantation_management;

import android.widget.Filter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agri_help.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter<T> extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {

    private List<T> dataList;
    private List<T> filteredList;
    private ItemFilter itemFilter;
    private static OnItemClickListener onItemClickListener;


    public MyAdapter() {
        dataList = new ArrayList<>();
        filteredList = new ArrayList<>();
        itemFilter = new ItemFilter();
    }

    public void setData(List<T> dataList) {
        this.dataList = dataList;
        this.filteredList = dataList;
        notifyDataSetChanged();
    }








    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item view layout and return a new ViewHolder
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plantation_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to the item view
        T data = filteredList.get(position);
        holder.textView.setText(data.toString());
        // holder.p_layout.setOnClickListener(new View.OnClickListener(){
        // @Override
        // public void onClick(View view) {
        //Toast.makeText(view.getContext(), "You clicked", Toast.LENGTH_SHORT).show();

        // }
        // });
    }


    public T getItem(int position) {
        return filteredList.get(position);
    }


    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView I;
        public RelativeLayout p_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            I= itemView.findViewById(R.id.image);
           // p_layout=itemView.findViewById(R.id.itemv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });

        }
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();
            List<T> filteredList = new ArrayList<>();

            for (T data : dataList) {
                if (data.toString().toLowerCase().contains(filterString)) {
                    filteredList.add(data);
                }
            }

            results.count = filteredList.size();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (List<T>) results.values;
            notifyDataSetChanged();
        }
    }

    //private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

