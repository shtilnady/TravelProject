package com.example.travelproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "accounts")
public class Account {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo @NotNull
    private String name = "";
    @ColumnInfo @NotNull
    private String password = "";

    public Account(String name, String password){
        this.name = name;
        this.password = password;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPasswordWithId() { return password; }
}
