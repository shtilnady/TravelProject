package com.example.travelproject.log;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = { Account.class }, version = 2)
public abstract class AccountsDatabase extends RoomDatabase {
    public abstract AccountDao getAccountDao();
}
