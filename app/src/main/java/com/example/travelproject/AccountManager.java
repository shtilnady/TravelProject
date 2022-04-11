package com.example.travelproject;

import android.content.Context;

import androidx.room.Room;

public class AccountManager {
    private static AccountsDatabase accountsDatabase;
    private static boolean registered = false;
    private static Account account;

    private AccountManager(){

    }

    public static AccountsDatabase getInstance(Context context){
        synchronized (AccountManager.class){
            if (accountsDatabase == null){
                accountsDatabase = Room.databaseBuilder(context, AccountsDatabase.class, "accounts.db").build();
            }
            return accountsDatabase;
        }
    }

    public static void logIn(Account acc){
        registered = true;
        account = acc;
    }
    public static void logOut(){
        registered = false;
        account = null;
    }
    public static boolean isRegistered(){
        return registered;
    }
}
