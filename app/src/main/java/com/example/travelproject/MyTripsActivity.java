package com.example.travelproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelproject.log.LogActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.travelproject.databinding.ActivityMyTripsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MyTripsActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMyTripsBinding binding;
    SharedPreferences settings;
    TextView accountLogin;
    FirebaseAuth auth;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyTripsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMyTrips.toolbar);
        binding.appBarMyTrips.bAddTrip.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), NewTripActivity.class);
            startActivity(i);
        });
        auth = FirebaseAuth.getInstance();
        settings = getSharedPreferences("Authorisation", Context.MODE_PRIVATE);
        database = FirebaseDatabase.getInstance().getReference();
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_my_trips);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        if (auth.getCurrentUser() == null){
            startActivity(new Intent(this, LogActivity.class));
        } else {
            database.child("users").child(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getBaseContext(), "Что-то пошло не так, повторите попытку позже", Toast.LENGTH_LONG).show();
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        TripManager.setUser(new User(
                                task.getResult().child("username").getValue().toString(),
                                task.getResult().child("email").getValue().toString()));
                        for (DataSnapshot snapshot: task.getResult().child("trips").getChildren()) {
                            Trip trip = snapshot.getValue(Trip.class);
                            TripManager.getUser().trips.add(trip);
                            Log.d("trip", trip.getTripTitle());
                        }
                        Log.d("firebase", "myTrips");
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        settings = getSharedPreferences("Authorisation", Context.MODE_PRIVATE);
        accountLogin = findViewById(R.id.t_account_login);
        accountLogin.setText(settings.getString("Account_ID", ""));
        getMenuInflater().inflate(R.menu.my_trips, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        settings = getSharedPreferences("Authorisation", Context.MODE_PRIVATE);
        switch (item.getItemId()) {
            case (R.id.action_exit):
                settings.edit().putBoolean("Registered", false);
                settings.edit().putString("Account_ID", "");
                new Thread(){
                    @Override
                    public void run() {
                        TripManager.getInstance(getApplicationContext()).getTripDao().deleteAll();
                    }
                }.start();
                TripManager.setTripAdapter(null);
                TripManager.setUser(null);
                auth.signOut();
                startActivity(new Intent(this, LogActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_my_trips);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}