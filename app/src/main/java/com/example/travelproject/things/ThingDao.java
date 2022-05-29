package com.example.travelproject.things;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ThingDao {
    @Insert
    void insertThing(Thing thing);
    @Delete
    void deleteThing(Thing thing);
    @Query("DELETE FROM things")
    void deleteAll();
    @Query("SELECT * FROM things WHERE trip_id = :tripId AND account_id = :accountId")
    List<Thing> getThing(int tripId, String accountId);
    @Query("SELECT * FROM things WHERE category_id = :categoryId AND trip_id = :tripId AND account_id = :accountId")
    List<Thing> getThing(int categoryId, int tripId, String accountId);
}
