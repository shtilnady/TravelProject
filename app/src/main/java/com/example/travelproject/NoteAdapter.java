package com.example.travelproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yandex.mapkit.MapKitFactory;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> list;
    NotesDatabase notesDatabase;

    NoteAdapter (List<Note> noteList){
        list = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        notesDatabase = NoteManager.getInstance(parent.getContext());
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.time.setText(list.get(position).getTime()+", "+list.get(position).getDate());
        holder.text.setText(list.get(position).getText());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addNote(Note note) {
        list.add(note);
        this.notifyItemInserted(list.size() - 1);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView title, time, text;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title_holder);
            time = itemView.findViewById(R.id.note_time_holder);
            text = itemView.findViewById(R.id.note_text_holder);
        }
    }
}
