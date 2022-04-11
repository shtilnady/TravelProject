package com.example.travelproject;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

public class AccountManager {
    private static AccountsDatabase accountsDatabase;
    private static Account account;

    private AccountManager(){

    }

    public static AccountsDatabase getInstance(Context context){
        synchronized (AccountManager.class){
            if (accountsDatabase == null){
                accountsDatabase = Room
                        .databaseBuilder(context, AccountsDatabase.class, "accounts.db")
                        .build();
            }
            return accountsDatabase;
        }
    }

    public static void logIn(Account acc){
        account = acc;
    }
    public static void logOut(){
        account = null;
    }
}
