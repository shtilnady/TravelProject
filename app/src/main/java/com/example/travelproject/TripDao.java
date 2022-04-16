package com.example.travelproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TripDao {
    @Insert
    void insertTrip(Trip trip);
    @Delete
    void deleteTrip(Trip Trip);
    @Query("DELETE FROM trips")
    void deleteAll();
    @Query("SELECT * FROM trips WHERE trip_id = :id_")
    Trip getTrip(int id_);
    @Query("SELECT * FROM trips WHERE account_id = :id_")
    List<Trip> getAll(int id_);
    @Query("SELECT EXISTS(SELECT trip_id FROM trips WHERE trip_id = :id_)")
    boolean contains(int id_);
}
