package com.example.travelproject;

import android.content.Context;

import androidx.room.Room;

import java.util.List;


public class TripManager {
    private static TripsDatabase tripsDatabase;
    private static TripAdapter tripAdapter;
    private static User user;

    public static void setTripAdapter(TripAdapter tripAdapter) {
        TripManager.tripAdapter = tripAdapter;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        TripManager.user = user;
    }

    private TripManager(){

    }

    public static TripsDatabase getInstance(Context context){
        synchronized (TripManager.class){
            if (tripsDatabase == null){
                tripsDatabase = Room
                        .databaseBuilder(context, TripsDatabase.class, "trips.db")
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return tripsDatabase;
        }
    }
    public static TripAdapter getAdapter(List<Trip> list){
        synchronized (TripAdapter.class){
            if (tripAdapter == null){
                tripAdapter = new TripAdapter(list);
            }
            return tripAdapter;
        }
    }
}