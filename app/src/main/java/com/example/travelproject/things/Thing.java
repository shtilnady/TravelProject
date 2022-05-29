package com.example.travelproject.things;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "things")
public class Thing {
    @PrimaryKey(autoGenerate = true)
    private int thing_id;
    @ColumnInfo
    @NotNull
    private int category_id;
    @ColumnInfo
    @NotNull
    private int trip_id;
    @ColumnInfo
    @NotNull
    private String account_id;
    @ColumnInfo
    @NotNull
    private String title;
    @ColumnInfo
    @NotNull
    private String count;

    public Thing(){

    }

    public Thing(int category_id, int trip_id, @NotNull String account_id, @NotNull String title, @NotNull String count) {
        this.category_id = category_id;
        this.trip_id = trip_id;
        this.account_id = account_id;
        this.title = title;
        this.count = count;
    }

    public int getThing_id() {
        return thing_id;
    }

    public void setThing_id(int thing_id) {
        this.thing_id = thing_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    @NotNull
    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(@NotNull String account_id) {
        this.account_id = account_id;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
