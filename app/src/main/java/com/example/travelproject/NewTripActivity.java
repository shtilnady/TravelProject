package com.example.travelproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.travelproject.log.AccountManager;

import java.util.List;

public class NewTripActivity extends AppCompatActivity {
    Button createTrip;
    EditText title, from, to, dateFrom, dateTo, description;
    List<Trip> list;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);
        getSupportActionBar().hide();
        createTrip = findViewById(R.id.b_create_trip);
        title = findViewById(R.id.et_trip_title);
        from = findViewById(R.id.et_from);
        to = findViewById(R.id.et_to);
        dateFrom = findViewById(R.id.et_date_from);
        dateTo = findViewById(R.id.et_date_to);
        description = findViewById(R.id.et_description);
        createTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trip trip = new Trip(getSharedPreferences("Authorisation", Context.MODE_PRIVATE)
                        .getString("Account_ID", ""),
                        title.getText().toString(),
                        dateFrom.getText().toString(),
                        dateTo.getText().toString(),
                        description.getText().toString());
                new Thread(){
                    @Override
                    public void run() {
                        TripManager.getInstance(getApplicationContext())
                                .getTripDao().
                                insertTrip(trip);
//                        list = TripManager
//                                .getInstance(getApplicationContext())
//                                .getTripDao()
//                                .getAll(getSharedPreferences("Authorisation", Context.MODE_PRIVATE)
//                                        .getString("Account_ID", ""));
                        flag = 1;
                    }
                }.start();
                while (flag == 0) {}
                TripManager.getAdapter(list).addTrip(trip);
                Intent i = new Intent(getApplicationContext(), MyTripsActivity.class);
                startActivity(i);
            }
        });
    }
}