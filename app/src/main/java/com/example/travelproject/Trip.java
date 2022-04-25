package com.example.travelproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "trips")
public class Trip {
    @PrimaryKey (autoGenerate = true)
    private int trip_id;
    @ColumnInfo @NotNull
    private String account_id;
    @ColumnInfo @NotNull
    private String trip_title;
    @ColumnInfo
    private String description;
    @ColumnInfo @NotNull
    private String start;
    @ColumnInfo @NotNull
    private String finish;

    public Trip(String account_id, String trip_title, String start, String finish, String description){
        this.account_id = account_id;
        this.trip_title = trip_title;
        this.start = start;
        this.finish = finish;
        this.description = description;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    @NotNull
    public String getTrip_title() {
        return trip_title;
    }

    public void setTrip_title(@NotNull String trip_title) {
        this.trip_title = trip_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    public String getStart() {
        return start;
    }

    public void setStart(@NotNull String start) {
        this.start = start;
    }

    @NotNull
    public String getFinish() {
        return finish;
    }

    public void setFinish(@NotNull String finish) {
        this.finish = finish;
    }
}