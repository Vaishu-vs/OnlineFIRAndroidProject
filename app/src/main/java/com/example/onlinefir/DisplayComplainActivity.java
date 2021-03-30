package com.example.onlinefir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayComplainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private TextView textViewId, textViewUsername, textViewEmail, textViewCrimeSpot, textViewPincode, textViewDescription, textViewCategory, textViewDateofIncident, textViewTimeofIncident, textViewStatus;
    Button buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_complain);
        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewCrimeSpot = (TextView) findViewById(R.id.textViewCrimeSpot);
        textViewPincode = (TextView) findViewById(R.id.textViewPincode);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewCategory = (TextView) findViewById(R.id.textViewCategory);
        textViewDateofIncident = (TextView) findViewById(R.id.textViewDateofIncident);
        textViewTimeofIncident = (TextView) findViewById(R.id.textViewTimeofIncident);
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("COMPLAIN");

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayComplainActivity.this, LayoutManagerActivity.class);
                startActivity(intent);
            }
        });
        getdata();
    }

    private void getdata() {
        // calling add value event listener method
        // for getting the values from database.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);

                textViewId.setText(value);
                textViewUsername.setText(value);
                textViewEmail.setText(value);
                textViewCrimeSpot.setText(value);
                textViewPincode.setText(value);
                textViewDescription.setText(value);
                textViewCategory.setText(value);
                textViewDateofIncident.setText(value);
                textViewTimeofIncident.setText(value);
                textViewStatus.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DisplayComplainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}