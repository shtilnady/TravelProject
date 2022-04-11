package com.example.travelproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = { Account.class }, version = 1)
public abstract class AccountsDatabase extends RoomDatabase {
    abstract AccountDao getAccountDao();
}
