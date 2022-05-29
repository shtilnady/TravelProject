package com.example.travelproject.list;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

public class CategoryManager {
    private static CategoriesDatabase categoriesDatabase;
    private static CategoryAdapter categoryAdapter;

    public static void setCategoryAdapter(CategoryAdapter categoryAdapter) {
        CategoryManager.categoryAdapter = categoryAdapter;
    }

    private CategoryManager(){

    }

    public static CategoriesDatabase getInstance(Context context){
        synchronized (CategoryManager.class){
            if (categoriesDatabase == null){
                categoriesDatabase = Room
                        .databaseBuilder(context, CategoriesDatabase.class, "categories.db")
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return categoriesDatabase;
        }
    }
    public static CategoryAdapter getAdapter(List<Category> list){
        synchronized (CategoryAdapter.class){
            if (categoryAdapter == null){
                categoryAdapter = new CategoryAdapter(list);
            }
            return categoryAdapter;
        }
    }
}
