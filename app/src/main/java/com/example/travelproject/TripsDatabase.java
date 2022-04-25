package com.example.travelproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = { Trip.class }, version = 1)
public abstract class TripsDatabase extends RoomDatabase {
    public abstract TripDao getTripDao();
}