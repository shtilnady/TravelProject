package com.example.travelproject.ui.trips;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelproject.Trip;
import com.example.travelproject.TripManager;
import com.example.travelproject.User;
import com.example.travelproject.databinding.FragmentTripsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TripsFragment extends Fragment {
    private FragmentTripsBinding binding;
    List<Trip> list;
    int flag = 0;

    DatabaseReference database;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTripsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        final RecyclerView recyclerTrips = binding.recyclerTrips;

        recyclerTrips.setLayoutManager(new LinearLayoutManager(getContext()));
        database = FirebaseDatabase.getInstance().getReference();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//                if (value.email.equals(TripManager.getUser().email)){
//                    TripManager.setUser(value);
//                    new Thread(){
//                        @Override
//                        public void run() {
//                            TripManager.getInstance(getContext()).getTripDao().deleteAll();
//                            for (Trip trip: TripManager.getUser().trips) {
//                                TripManager.getInstance(getContext()).getTripDao().insertTrip(trip);
//                            }
//                        }
//                    }.start();
//                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        database.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("trips")
                .get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
            }
        });
        flag = 0;
        new Thread(){
            @Override
            public void run() {
                list = TripManager
                        .getInstance(getContext())
                        .getTripDao()
                        .getAll(getActivity()
                                .getSharedPreferences("Authorisation", Context.MODE_PRIVATE)
                                .getString("Account_ID", ""));
                flag = 1;
            }
        }.start();

        while (flag == 0) {}
        recyclerTrips.setAdapter(TripManager.getAdapter(list));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}