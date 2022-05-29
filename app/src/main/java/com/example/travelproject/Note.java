package com.example.travelproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int note_id;

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

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
    private String text;
    @ColumnInfo
    @NotNull
    private String time;
    @ColumnInfo
    @NotNull
    private String date;

    public Note(@NotNull int trip_id, @NotNull String account_id, @NotNull String title, @NotNull String text, @NotNull String time, @NotNull String date) {
        this.trip_id = trip_id;
        this.title = title;
        this.account_id = account_id;
        this.text = text;
        this.time = time;
        this.date = date;
    }

    public Note(@NotNull int trip_id, @NotNull String account_id, @NotNull String title, @NotNull String time, @NotNull String date) {
        this.trip_id = trip_id;
        this.title = title;
        this.account_id = account_id;
        this.time = time;
        this.date = date;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    public Note() {

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
    public String getText() {
        return text;
    }

    public void setText(@NotNull String text) {
        this.text = text;
    }

    @NotNull
    public String getTime() {
        return time;
    }

    public void setTime(@NotNull String time) {
        this.time = time;
    }

    @NotNull
    public String getDate() {
        return date;
    }

    public void setDate(@NotNull String date) {
        this.date = date;
    }
}