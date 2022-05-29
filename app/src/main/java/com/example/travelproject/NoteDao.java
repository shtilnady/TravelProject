package com.example.travelproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insertNote(Note note);
    @Delete
    void deleteNote(Note note);
    @Query("DELETE FROM notes")
    void deleteAll();
    @Query("SELECT * FROM notes WHERE trip_id = :tripId AND account_id = :accountId")
    List<Note> getNotes(int tripId, String accountId);
}
