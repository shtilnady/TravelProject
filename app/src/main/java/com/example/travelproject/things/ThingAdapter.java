package com.example.travelproject.things;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelproject.R;

import java.util.List;

public class ThingAdapter extends RecyclerView.Adapter<ThingAdapter.ThingViewHolder> {
    private List<Thing> list;
    ThingsDatabase thingsDatabase;

    ThingAdapter (List<Thing> thingList){
        list = thingList;
    }

    @NonNull
    @Override
    public ThingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        thingsDatabase = ThingManager.getInstance(parent.getContext());
        return new ThingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.thing_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ThingViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.count.setText(list.get(position).getCount());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.title.setPaintFlags(holder.title.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addThing(Thing thing) {
        list.add(thing);
        this.notifyItemInserted(list.size() - 1);
    }

    public class ThingViewHolder extends RecyclerView.ViewHolder {
        TextView title, count;

        public ThingViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.thing_title_holder);
            count = itemView.findViewById(R.id.thing_count_holder);
        }
    }
}