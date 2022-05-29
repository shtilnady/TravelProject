package com.example.travelproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.travelproject.list.Category;
import com.example.travelproject.list.CategoryManager;
import com.example.travelproject.list.ThingsListFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

public class TripMapActivity extends AppCompatActivity {

    private MapView mapview;
    Trip trip;
    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_map);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF465E4C")));
        MapKitFactory.initialize(this);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null){
            trip = (Trip) arguments.getSerializable("trip");
            getSupportActionBar().setTitle(trip.getTripTitle());
        }
        mapview = findViewById(R.id.mapview);
        mapview.getMap().move(
                new CameraPosition(new Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        add = findViewById(R.id.b_add_memories);
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                final TextView ext = new EditText(v.getContext());
//                LinearLayout.LayoutParams lp = new LinearLayout
//                        .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT);
//                editText.setLayoutParams(lp);
//                editText.setHint("Название");
//                final TextView text = new EditText(v.getContext());
//                editText.setLayoutParams(lp);
//                text.setLayoutParams(lp);
//                text.setHint("Кол-во");
//                LinearLayout layout = new LinearLayout(v.getContext());
//                layout.setOrientation(LinearLayout.VERTICAL);
//                layout.addView(editText);
//                layout.addView(text);
//                editText.setLayoutParams(lp);
//                editText.setHint("Вещь");
//                builder.setTitle("Добавить воспоминание").setView(editText);
//                builder.create().show();
//            }
//        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_list):
                System.out.println("liiiiiiiiist");
                Intent action_list = new Intent(this, ListActivity.class);
                action_list.putExtra("fr", new ThingsListFragment());
                action_list.putExtra("title", "Список вещей");
                action_list.putExtra("trip", trip);
                startActivity(action_list);
                return true;
            case (R.id.action_notes):
                System.out.println("noootes");
                Intent action_notes = new Intent(this, ListActivity.class);
                action_notes.putExtra("fr", new NotesFragment());
                action_notes.putExtra("title", "Заметки");
                action_notes.putExtra("trip", trip);
                startActivity(action_notes);
                return true;
            case (android.R.id.home):
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapview.onStart();
    }
}