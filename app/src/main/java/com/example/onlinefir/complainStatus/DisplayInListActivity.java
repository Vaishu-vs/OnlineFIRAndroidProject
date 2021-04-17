package com.example.onlinefir.complainStatus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.onlinefir.Complain;
import com.example.onlinefir.DisplayComplainActivity;
import com.example.onlinefir.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayInListActivity extends AppCompatActivity {
    private ListView lv;
    private FirebaseDatabase database;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_in_list);
        lv = (ListView)findViewById(R.id.listView);
        String s = getIntent().getStringExtra("uid");
        list = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference().child("COMPLAIN");

        table_user.child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Log.i("Tag",dataSnapshot.child("Crime_spot").getValue().toString());
                }  else {
                    Toast.makeText(DisplayInListActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DisplayInListActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}