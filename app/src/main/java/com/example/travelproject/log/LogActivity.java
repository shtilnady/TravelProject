package com.example.travelproject.log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.travelproject.MyTripsActivity;
import com.example.travelproject.R;

public class LogActivity extends AppCompatActivity {
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        getSupportActionBar().hide();
        settings = getSharedPreferences("Authorisation", Context.MODE_PRIVATE);
        if (settings.getBoolean("Registered", false)){
            Intent i = new Intent(this, MyTripsActivity.class);
            startActivity(i);
        } else {
//        settings.edit().putBoolean("Registered", false).apply();
//        settings.edit().putString("Account_ID", "").apply();
            Fragment entryFragment = new EntryFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.l_entry, entryFragment)
                    .commit();
        }
    }
}