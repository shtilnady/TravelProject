package com.example.travelproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class NewTripActivity extends AppCompatActivity {
    Button createTrip;
    ImageButton back;
    EditText title, from, to, dateFrom, dateTo, description;
    List<Trip> list;
    FirebaseAuth auth;
    DatabaseReference database;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        createTrip = findViewById(R.id.b_create_trip);
        title = findViewById(R.id.et_trip_title);
        from = findViewById(R.id.et_from);
        to = findViewById(R.id.et_to);
        dateFrom = findViewById(R.id.et_date_from);
        dateTo = findViewById(R.id.et_date_to);
        description = findViewById(R.id.et_description);
        back = findViewById(R.id.b_backtotrips);
        back.setOnClickListener(v -> finish());
        createTrip.setOnClickListener(v -> {
            Trip trip = new Trip(getSharedPreferences("Authorisation", Context.MODE_PRIVATE)
                    .getString("Account_ID", ""),
                    title.getText().toString(),
                    from.getText().toString(),
                    to.getText().toString(),
                    dateFrom.getText().toString(),
                    dateTo.getText().toString(),
                    description.getText().toString());
            TripManager.getUser().trips.add(trip);
            database.child("users").child(auth.getCurrentUser().getUid()).child("trips").push().setValue(trip);
            flag = 0;
            new Thread(){
                @Override
                public void run() {
                    TripManager.getInstance(getApplicationContext())
                            .getTripDao().
                            insertTrip(trip);
                    flag = 1;
                }
            }.start();
            while (flag == 0) {}
            TripManager.getAdapter(list).addTrip(trip);
            finish();
        });
    }
}