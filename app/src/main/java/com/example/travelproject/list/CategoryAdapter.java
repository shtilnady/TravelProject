package com.example.travelproject.list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelproject.ListActivity;
import com.example.travelproject.R;
import com.example.travelproject.things.Thing;
import com.example.travelproject.things.ThingManager;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> list;
    private List<Thing> thingList;
    int flag;
    CategoriesDatabase categoriesDatabase;

    CategoryAdapter (List<Category> categoryList){
        list = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        categoriesDatabase = CategoryManager.getInstance(parent.getContext());
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        flag = 0;
        new Thread(){
            @Override
            public void run() {
                thingList = ThingManager
                        .getInstance(holder.itemView.getContext())
                        .getThingDao()
                        .getThing(list.get(holder.getAdapterPosition()).getCategory_id(),
                                list.get(holder.getAdapterPosition()).getTrip_id(),
                                list.get(holder.getAdapterPosition()).getAccount_id());
                flag = 1;
            }
        }.start();
        while (flag == 0) {}
        if (thingList == null) {
            thingList = new ArrayList<Thing>();
        }
        holder.recyclerThings.setAdapter(ThingManager.getAdapter(list.get(position), thingList));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                final EditText editText = new EditText(v.getContext());
                final EditText text = new EditText(v.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout
                        .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                editText.setLayoutParams(lp);
                text.setLayoutParams(lp);
                text.setHint("Кол-во");
                LinearLayout layout = new LinearLayout(v.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(editText);
                layout.addView(text);
                editText.setLayoutParams(lp);
                editText.setHint("Вещь");
                builder.setTitle("Добавить вещь в " + list.get(holder.getAdapterPosition()).getTitle()).setView(layout);
                builder.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Thing thing = new Thing(list.get(holder.getAdapterPosition()).getCategory_id(),
                                list.get(holder.getAdapterPosition()).getTrip_id(),
                                list.get(holder.getAdapterPosition()).getAccount_id(),
                                editText.getText().toString(),
                                text.getText().toString());
                        ThingManager.getAdapter(list.get(holder.getAdapterPosition()), thingList).addThing(thing);
                        new Thread(){
                            @Override
                            public void run() {
                                ThingManager.getInstance(v.getContext())
                                        .getThingDao()
                                        .insertThing(thing);
                            }
                        }.start();
                        final TextView textView = v.findViewById(R.id.empty);
                        textView.setText(null);
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addCategory(Category category) {
        list.add(category);
        this.notifyItemInserted(list.size() - 1);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        final RecyclerView recyclerThings;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.category_title_holder);
            recyclerThings = itemView.findViewById(R.id.recycler_things);
            recyclerThings.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }
}