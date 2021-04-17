package com.example.onlinefir.complainStatus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinefir.R;
import com.example.onlinefir.admin.ComplainData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyComplainActivity extends Fragment {
    DatabaseReference databaseReference;
    MyComplainAdapter myAdapter;
    List<ComplainData> fetchData;
    private RecyclerView recyclerViewMyComplain;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_complain, container, false);

        // Add the following lines to create RecyclerView
        recyclerViewMyComplain = view.findViewById(R.id.recyclerViewMyComplain);
        recyclerViewMyComplain.setHasFixedSize(true);
        recyclerViewMyComplain.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetchData = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("COMPLAIN");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ComplainData data = ds.getValue(ComplainData.class);
                    data.setKey(ds.getKey());
                    fetchData.add(data);
                }
                myAdapter = new MyComplainAdapter(fetchData,getActivity());
                recyclerViewMyComplain.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}