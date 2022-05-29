package com.example.travelproject.things;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = { Thing.class }, version = 3)
public abstract class ThingsDatabase extends RoomDatabase {
    public abstract ThingDao getThingDao();
}
