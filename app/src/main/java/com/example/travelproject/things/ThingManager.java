package com.example.travelproject.things;

import android.content.Context;

import androidx.room.Room;

import com.example.travelproject.list.Category;

import java.util.HashMap;
import java.util.List;
public class ThingManager {
    private static ThingsDatabase thingsDatabase;
    private static HashMap<Category, ThingAdapter> thingAdapter;

    public static void setThingAdapter(Category category, ThingAdapter thingAdapter) {
        ThingManager.thingAdapter.put(category, thingAdapter);
    }

    private ThingManager(){

    }

    public static ThingsDatabase getInstance(Context context){
        synchronized (ThingManager.class){
            if (thingsDatabase == null){
                thingsDatabase = Room
                        .databaseBuilder(context, ThingsDatabase.class, "things.db")
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return thingsDatabase;
        }
    }
    public static ThingAdapter getAdapter(Category category, List<Thing> list){
        synchronized (ThingAdapter.class){
            if (thingAdapter == null) {
                thingAdapter = new HashMap<Category, ThingAdapter>();
            }
            if (thingAdapter.get(category) == null){
                thingAdapter.put(category, new ThingAdapter(list));
            }
            return thingAdapter.get(category);
        }
    }
}

