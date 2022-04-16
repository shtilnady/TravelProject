package com.example.travelproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.travelproject.log.AccountManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.travelproject.databinding.ActivityMyTripsBinding;

public class MyTripsActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMyTripsBinding binding;
    SharedPreferences settings;
    TextView accountLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyTripsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMyTrips.toolbar);
        binding.appBarMyTrips.bAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        settings = getSharedPreferences("Authorisation", Context.MODE_PRIVATE);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        new Thread(){
            @Override
            public void run() {
                AccountManager
                        .logIn(AccountManager
                                .getInstance(getApplicationContext())
                                .getAccountDao()
                                .getAccount(settings.getString("Account_ID", ""))
                        );
            }
        }.start();
        while (AccountManager.getAccount() == null){}
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_my_trips);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
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
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_my_trips);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}