package com.example.travelproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        getSupportActionBar().hide();
        if (AccountManager.isRegistered()){
            Intent i = new Intent(this, MyTripsActivity.class);
            startActivity(i);
        } else {
            Fragment entryFragment = new EntryFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.l_entry, entryFragment)
                    .commit();
        }
    }
}