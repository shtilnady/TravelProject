package com.example.travelproject;

import android.content.Context;

import androidx.room.Room;


public class TripManager {
    private static TripsDatabase tripsDatabase;

    private TripManager(){

    }

    public static TripsDatabase getInstance(Context context){
        synchronized (TripManager.class){
            if (tripsDatabase == null){
                tripsDatabase = Room
                        .databaseBuilder(context, TripsDatabase.class, "trips.db")
                        .build();
            }
            return tripsDatabase;
        }
    }
}