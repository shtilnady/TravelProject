package com.example.travelproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class NotesFragment extends Fragment implements Serializable{
    int flag;
    FloatingActionButton add;
    List<Note> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        final RecyclerView recyclerNotes = getActivity().findViewById(R.id.recycler_notes);

        recyclerNotes.setLayoutManager(new LinearLayoutManager(getContext()));
        flag = 0;
        new Thread(){
            @Override
            public void run() {
                list = NoteManager
                        .getInstance(getContext())
                        .getNoteDao()
                        .getNotes(((ListActivity)getActivity()).getTrip().getTrip_id(), getActivity()
                                .getSharedPreferences("Authorisation", Context.MODE_PRIVATE)
                                .getString("Account_ID", ""));
                flag = 1;
            }
        }.start();

        while (flag == 0) {}
        add = getActivity().findViewById(R.id.b_add_note);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final EditText editText = new EditText(getContext());
                final EditText text = new EditText(getContext());
                LinearLayout.LayoutParams lp = new LinearLayout
                        .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                editText.setLayoutParams(lp);
                text.setLayoutParams(lp);
                text.setHint("Текст");
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(editText);
                layout.addView(text);
                editText.setHint("Заголовок");
                builder.setTitle("Новая заметка").setView(layout);
                builder.setPositiveButton("Готово", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Заметка создана", Toast.LENGTH_SHORT).show();
                        Note note = new Note(((ListActivity)getActivity()).getTrip().getTrip_id(),
                                getActivity()
                                        .getSharedPreferences("Authorisation", Context.MODE_PRIVATE)
                                        .getString("Account_ID", ""),
                                editText.getText().toString(),
                                text.getText().toString(),
                                new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date()),
                                new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                        NoteManager.getAdapter(list).addNote(note);
                        new Thread(){
                            @Override
                            public void run() {
                                NoteManager.getInstance(getContext())
                                        .getNoteDao()
                                        .insertNote(note);
                            }
                        }.start();
                    }
                });
                builder.create().show();
            }
        });
        recyclerNotes.setAdapter(NoteManager.getAdapter(list));
    }
}
