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
    private String tripTitle;
    @ColumnInfo @NotNull
    private String placeFrom;
    @ColumnInfo @NotNull
    private String placeTo;
    @ColumnInfo @NotNull
    private String timeStart;
    @ColumnInfo @NotNull
    private String timeFinish;
    @ColumnInfo
    private String description;

    public Trip(String account_id, String tripTitle, String placeFrom, String placeTo, String timeStart, String timeFinish, String description){
        this.account_id = account_id;
        this.tripTitle = tripTitle;
        this.placeFrom = placeFrom;
        this.placeTo = placeTo;
        this.timeStart = timeStart;
        this.timeFinish = timeFinish;
        this.description = description;
    }

    public Trip(){

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
    public String getTripTitle() {
        return tripTitle;
    }

    public void setTripTitle(@NotNull String tripTitle) {
        this.tripTitle = tripTitle;
    }

    @NotNull
    public String getPlaceFrom() {
        return placeFrom;
    }

    public void setPlaceFrom(@NotNull String placeFrom) {
        this.placeFrom = placeFrom;
    }

    @NotNull
    public String getPlaceTo() {
        return placeTo;
    }

    public void setPlaceTo(@NotNull String placeTo) {
        this.placeTo = placeTo;
    }

    @NotNull
    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(@NotNull String timeStart) {
        this.timeStart = timeStart;
    }

    @NotNull
    public String getTimeFinish() {
        return timeFinish;
    }

    public void setTimeFinish(@NotNull String timeFinish) {
        this.timeFinish = timeFinish;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}