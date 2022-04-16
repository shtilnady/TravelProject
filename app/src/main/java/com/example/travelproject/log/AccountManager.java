package com.example.travelproject.log;

import android.content.Context;

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
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return accountsDatabase;
        }
    }

    public static Account getAccount() {
        return account;
    }
    public static void logIn(Account acc){
        account = acc;
    }
    public static void logOut(){
        account = null;
    }
}
