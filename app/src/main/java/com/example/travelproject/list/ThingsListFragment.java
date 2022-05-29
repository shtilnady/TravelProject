package com.example.travelproject.list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelproject.ListActivity;
import com.example.travelproject.Note;
import com.example.travelproject.NoteManager;
import com.example.travelproject.R;
import com.example.travelproject.things.Thing;
import com.example.travelproject.things.ThingManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ThingsListFragment extends Fragment implements Serializable{
    int flag, flagThing;
    FloatingActionButton add;
    List<Category> list;
    List<Thing> thingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_things_list, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        final RecyclerView recyclerCategories = getActivity().findViewById(R.id.recycler_categories);

        recyclerCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        flag = 0;
        new Thread(){
            @Override
            public void run() {
                list = CategoryManager
                        .getInstance(getContext())
                        .getCategoryDao()
                        .getCategory(((ListActivity)getActivity()).getTrip().getTrip_id(), getActivity()
                                .getSharedPreferences("Authorisation", Context.MODE_PRIVATE)
                                .getString("Account_ID", ""));
                thingList = ThingManager
                        .getInstance(getContext())
                        .getThingDao()
                        .getThing(((ListActivity)getActivity()).getTrip().getTrip_id(), getActivity()
                                .getSharedPreferences("Authorisation", Context.MODE_PRIVATE)
                                .getString("Account_ID", ""));
                flag = 1;
            }
        }.start();

        while (flag == 0) {}
        add = getActivity().findViewById(R.id.b_add_category);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final EditText editText = new EditText(getContext());
                LinearLayout.LayoutParams lp = new LinearLayout
                        .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                editText.setLayoutParams(lp);
                editText.setHint("Название");
                builder.setTitle("Новая категория").setView(editText);
                builder.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Category category = new Category(((ListActivity)getActivity()).getTrip().getTrip_id(),
                                getActivity()
                                        .getSharedPreferences("Authorisation", Context.MODE_PRIVATE)
                                        .getString("Account_ID", ""),
                                editText.getText().toString());
                        CategoryManager.getAdapter(list).addCategory(category);
                        new Thread(){
                            @Override
                            public void run() {
                                CategoryManager.getInstance(getContext())
                                        .getCategoryDao()
                                        .insertCategory(category);
                            }
                        }.start();
                    }
                });
                builder.create().show();
            }
        });
        if (list == null || list.size() == 0){
            final TextView textView = getActivity().findViewById(R.id.empty);
            textView.setText("Список пуст");
        }
        recyclerCategories.setAdapter(CategoryManager.getAdapter(list));
    }
}