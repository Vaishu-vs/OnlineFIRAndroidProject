package com.example.onlinefir.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.onlinefir.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewComplainActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    ComplainAdapter helperAdapter;
    List<ComplainData> fetchData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complain);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewComplin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchData = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("COMPLAIN");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //if(dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        ComplainData data = ds.getValue(ComplainData.class);
                        fetchData.add(data);
                    }
                    helperAdapter = new ComplainAdapter(fetchData);
                    recyclerView.setAdapter(helperAdapter);
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}