package com.example.travelproject.list;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = { Category.class }, version = 3)
public abstract class CategoriesDatabase extends RoomDatabase {
    public abstract CategoryDao getCategoryDao();
}