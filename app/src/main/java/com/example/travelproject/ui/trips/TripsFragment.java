package com.example.travelproject.ui.trips;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelproject.Trip;
import com.example.travelproject.TripManager;
import com.example.travelproject.databinding.FragmentTripsBinding;

import java.util.List;

public class TripsFragment extends Fragment {
    private FragmentTripsBinding binding;
    List<Trip> list;
    int flag = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

    binding = FragmentTripsBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final RecyclerView recyclerTrips = binding.recyclerTrips;

        recyclerTrips.setLayoutManager(new LinearLayoutManager(getContext()));
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}