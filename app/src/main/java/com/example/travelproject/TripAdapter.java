package com.example.travelproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {
    private List<Trip> list;
    TripsDatabase tripsDatabase;

    TripAdapter (List<Trip> tripList){
        list = tripList;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        tripsDatabase = TripManager.getInstance(parent.getContext());
        return new TripViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTrip_title());
        holder.from.setText(list.get(position).getStart());
        holder.to.setText(list.get(position).getFinish());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addTrip(Trip trip) {
        list.add(trip);
        this.notifyItemInserted(list.size() - 1);
    }

    public class TripViewHolder extends RecyclerView.ViewHolder {
        TextView title, from, to;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.trip_title_holder);
            from = itemView.findViewById(R.id.trip_from_holder);
            to = itemView.findViewById(R.id.trip_to_holder);
        }
    }
}
