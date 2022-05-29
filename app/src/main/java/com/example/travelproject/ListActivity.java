package com.example.travelproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.travelproject.log.EntryFragment;

public class ListActivity extends AppCompatActivity {
    Fragment fragment;
    Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null){
            fragment = (Fragment) arguments.getSerializable("fr");
            trip = (Trip) arguments.getSerializable("trip");
            getSupportActionBar().setTitle(arguments.get("title").toString());
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF82D296")));
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.l_list, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (android.R.id.home):
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public Trip getTrip(){
        return trip;
    }

    public ListActivity getAct(){
        return this;
    }
}