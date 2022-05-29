package com.example.travelproject;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

public class NoteManager {
    private static NotesDatabase notesDatabase;
    private static NoteAdapter noteAdapter;

    public static void setNoteAdapter(NoteAdapter noteAdapter) {
        NoteManager.noteAdapter = noteAdapter;
    }

    private NoteManager(){

    }

    public static NotesDatabase getInstance(Context context){
        synchronized (NoteManager.class){
            if (notesDatabase == null){
                notesDatabase = Room
                        .databaseBuilder(context, NotesDatabase.class, "notes.db")
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return notesDatabase;
        }
    }
    public static NoteAdapter getAdapter(List<Note> list){
        synchronized (NoteAdapter.class){
            if (noteAdapter == null){
                noteAdapter = new NoteAdapter(list);
            }
            return noteAdapter;
        }
    }
}
