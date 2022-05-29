package com.example.travelproject.list;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
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

    public Category(int trip_id, @NotNull String account_id, @NotNull String title) {
        this.trip_id = trip_id;
        this.account_id = account_id;
        this.title = title;
    }

    public Category(){

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
}
