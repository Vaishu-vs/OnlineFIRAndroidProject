package com.example.onlinefir.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.onlinefir.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewComplainActivity extends AppCompatActivity {
    DatabaseReference mbase;
    ComplainAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complain);
        ComplainData[] myListData = new ComplainData[] {
                new ComplainData("Email", "aaa", "cs", "dsd"),
                new ComplainData("Email", "aaa", "cs", "dsd"),
                new ComplainData("Email", "aaa", "cs", "dsd"),
                new ComplainData("Email", "aaa", "cs", "dsd")
        };
        mbase = FirebaseDatabase.getInstance().getReference();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewComplin);
        ComplainAdapter adapter = new ComplainAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    // Function to tell the app to start getting
    // data from database on starting of the activity
    @Override protected void onStart()
    {
        super.onStart();
       // adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override protected void onStop()
    {
        super.onStop();
       // adapter.stopListening();
    }
}