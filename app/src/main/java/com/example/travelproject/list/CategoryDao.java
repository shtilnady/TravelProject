package com.example.travelproject.list;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.travelproject.Note;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void insertCategory(Category category);
    @Delete
    void deleteCategory(Category category);
    @Query("DELETE FROM categories")
    void deleteAll();
    @Query("SELECT * FROM categories WHERE trip_id = :tripId AND account_id = :accountId")
    List<Category> getCategory(int tripId, String accountId);
}
