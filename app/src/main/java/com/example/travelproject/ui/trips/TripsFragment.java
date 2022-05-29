package com.example.travelproject.ui.trips;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

        final RecyclerView recyclerTrips = binding.recyclerTrips;

        recyclerTrips.setLayoutManager(new LinearLayoutManager(getContext()));
        database = FirebaseDatabase.getInstance().getReference();
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
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}